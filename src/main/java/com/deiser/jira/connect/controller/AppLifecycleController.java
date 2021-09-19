package com.deiser.jira.connect.controller;

import com.deiser.jira.connect.model.LifecycleInput;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppLifecycleController {

    private static final Logger LOGGER = Logger.getLogger(AppLifecycleController.class);

    @RequestMapping(value = "/enabled", method = RequestMethod.POST)
    public ResponseEntity<String> enabled(@RequestBody LifecycleInput lifecycleInput) {
        LOGGER.warn("Enabled instance " + lifecycleInput.getBaseUrl());
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/disabled", method = RequestMethod.POST)
    public ResponseEntity<String> disabled(@RequestBody LifecycleInput lifecycleInput) {
        LOGGER.warn("Disabled instance " + lifecycleInput.getBaseUrl());
        return ResponseEntity.ok().build();
    }
}
