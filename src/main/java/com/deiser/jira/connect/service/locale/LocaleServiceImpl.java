package com.deiser.jira.connect.service.locale;

import java.util.Locale;

public class LocaleServiceImpl implements LocaleService {
    @Override
    public Locale get() {
        return Locale.forLanguageTag("es");
    }
}
