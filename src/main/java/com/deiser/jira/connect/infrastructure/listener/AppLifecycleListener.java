package com.deiser.jira.connect.infrastructure.listener;

import com.atlassian.connect.spring.AddonInstalledEvent;
import com.atlassian.connect.spring.AddonUninstalledEvent;
import org.apache.log4j.Logger;
import org.springframework.context.event.EventListener;

public class AppLifecycleListener {

    private static final Logger LOGGER = Logger.getLogger(AppLifecycleListener.class);

    @EventListener
    public void addonInstalled(AddonInstalledEvent event) {
        LOGGER.warn("Installed instance " + event.getHost().getBaseUrl());
    }

    @EventListener
    public void addonUninstalled(AddonUninstalledEvent event) {
        LOGGER.warn("Uninstalled instance " + event.getHost().getBaseUrl());
    }
}
