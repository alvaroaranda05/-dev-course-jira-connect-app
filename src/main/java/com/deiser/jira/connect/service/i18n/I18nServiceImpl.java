package com.deiser.jira.connect.service.i18n;

import com.deiser.jira.connect.service.locale.LocaleService;
import org.apache.log4j.Logger;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class I18nServiceImpl extends ReloadableResourceBundleMessageSource implements I18nService {
    private static final Logger LOGGER = Logger.getLogger(I18nServiceImpl.class);

    private static final Locale DEFAULT_LOCALE = Locale.US;

    private final LocaleService localeService;

    public I18nServiceImpl(LocaleService localeService) {
        this.localeService = localeService;
    }

    public static final String[] I18N_RESOURCES = {
            "classpath:i18n/connect"
    };

    @Override
    public String getText(String key, Object... params) {
        try {
            return getText(localeService.get(), key, params);
        } catch (Exception ex) {
            return getText(DEFAULT_LOCALE, key, params);
        }
    }

    @Override
    public String getText(Locale locale, String key, Object... params) {
        try {
            return getMessage(key, params, locale);
        } catch (NoSuchMessageException ex) {
            LOGGER.debug("Resource not found: " + key, ex);
            return key;
        }
    }

    @Override
    public Map<String, String> get(String... prefixes) {
        return get(localeService.get(), prefixes);
    }

    @Override
    public Map<String, String> get(Locale locale, String... prefixes) {
        return getAllProperties(locale)
                .entrySet()
                .stream()
                .filter(entry -> entry != null && entry.getKey() != null)
                .filter(entry -> filterKeyByPrefixes(entry.getKey().toString(), prefixes))
                .collect(getPropertyCollector());
    }

    private Properties getAllProperties(Locale locale) {
        clearCacheIncludingAncestors();
        return getMergedProperties(locale).getProperties();
    }

    private Collector<Map.Entry<Object, Object>, ?, Map<String, String>> getPropertyCollector() {
        return Collectors.toMap(
                entry -> entry.getKey().toString(),
                entry -> entry.getValue().toString()
        );
    }

    private boolean filterKeyByPrefixes(String key, String[] prefixes) {
        return Arrays.stream(prefixes)
                .anyMatch(key::startsWith);
    }
}
