package com.deiser.jira.connect.service.i18n;

import java.util.Locale;
import java.util.Map;

public interface I18nService {
    String getText(String key, Object... params);

    String getText(Locale locale, String key, Object... params);

    Map<String, String> get(String... prefixes);

    Map<String, String> get(Locale locale, String... prefixes);
}
