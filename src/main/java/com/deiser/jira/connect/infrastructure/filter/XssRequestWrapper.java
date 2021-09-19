package com.deiser.jira.connect.infrastructure.filter;

import com.google.common.collect.Lists;
import org.apache.commons.codec.Charsets;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.owasp.esapi.Encoder;
import org.owasp.esapi.reference.DefaultEncoder;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class XssRequestWrapper extends HttpServletRequestWrapper {

    public static final String HTML_ESAPI_CODEC = "HTMLEntityCodec";
    public static final String URL_ESAPI_CODEC = "PercentCodec";
    private byte[] rawData;
    private final HttpServletRequest request;
    private final ResettableServletInputStream servletStream;

    public XssRequestWrapper(HttpServletRequest request) {
        super(request);
        this.request = request;
        this.servletStream = new ResettableServletInputStream();
    }

    public void resetInputStream(byte[] newRawData) {
        rawData = newRawData;
        servletStream.stream = new ByteArrayInputStream(newRawData);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (rawData == null) {
            rawData = IOUtils.toByteArray(this.request.getReader(), Charsets.UTF_8);
            servletStream.stream = new ByteArrayInputStream(rawData);
        }
        return servletStream;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        if (rawData == null) {
            rawData = IOUtils.toByteArray(this.request.getReader(), Charsets.UTF_8);
            servletStream.stream = new ByteArrayInputStream(rawData);
        }
        return new BufferedReader(new InputStreamReader(servletStream));
    }

    private static class ResettableServletInputStream extends ServletInputStream {

        private InputStream stream;

        @Override
        public int read() throws IOException {
            return stream.read();
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setReadListener(ReadListener readListener) {

        }
    }

    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = stripXss(values[i]);
        }
        return encodedValues;
    }

    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        return stripXss(value);
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        return stripXss(value);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        List<String> result = new ArrayList<>();
        Enumeration<String> headers = super.getHeaders(name);
        while (headers.hasMoreElements()) {
            String header = headers.nextElement();
            String[] tokens = header.split(",");
            for (String token : tokens) {
                result.add(stripXss(token));
            }
        }
        return Collections.enumeration(result);
    }

    public String stripXss(String value) {
        if (value == null) return null;

        boolean isJsonValue = isValidJson(value);
        String normalizedValue = getNormalizedStringFromEncodings(value);
        String cleanedValue = getCleanedStringFromXss(normalizedValue);

        return !isJsonValue || isValidJson(cleanedValue)
                ? cleanedValue
                : value;
    }

    private String getNormalizedStringFromEncodings(String value) {
        //ESAPI library will normalize a string based on different encodings
        try {
            Encoder encoder = new DefaultEncoder(Lists.newArrayList(HTML_ESAPI_CODEC, URL_ESAPI_CODEC));
            return encoder
                    .canonicalize(value)
                    .replaceAll("\0", "")
                    .replaceAll("\n", "\\\n");
        } catch (Exception ex) {
            return value;
        }
    }

    private String getCleanedStringFromXss(String value) {
        //Jsoup library will clean the string to remove the xss attack
        Document.OutputSettings outputSettings = new Document.OutputSettings()
                .charset(Charsets.UTF_8)
                .prettyPrint(false);
        return Jsoup.clean(value, "", Whitelist.basic(), outputSettings)
                .replaceAll("&lt;", "<")
                .replaceAll("&gt;", ">")
                .replaceAll("&amp;", "&");
    }

    private boolean isValidJson(String value) {
        try {
            new JSONObject(value);
        } catch (JSONException ex) {
            try {
                new JSONArray(value);
            } catch (JSONException ex2) {
                return false;
            }
        }
        return true;
    }
}
