package com.deiser.jira.connect.controller.api;


import com.atlassian.connect.spring.AtlassianHostUser;
import com.atlassian.connect.spring.ContextJwt;
import com.deiser.jira.connect.model.Configuration;
import com.deiser.jira.connect.model.ConfigurationOut;
import com.deiser.jira.connect.service.configuration.ConfigurationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/configuration")
public class ConfigurationController {

    private ConfigurationService configurationService;

    public ConfigurationController(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @GetMapping(produces = {"application/json"})
    @ContextJwt
    public ResponseEntity<List<ConfigurationOut>> get(@AuthenticationPrincipal AtlassianHostUser hostUser) {
        if (!isValidHost(hostUser)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        List<ConfigurationOut> configurationOuts = configurationService.get(hostUser.getHost().getClientKey()).stream()
                .map(ConfigurationOut::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(configurationOuts);
    }

    @GetMapping("/{key}")
    @ContextJwt
    public ResponseEntity<ConfigurationOut> get(@PathVariable String key, @AuthenticationPrincipal AtlassianHostUser hostUser) {
        if (!isValidHost(hostUser)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Configuration configuration = configurationService.get(key, hostUser.getHost().getClientKey());
        return configuration == null
                //TODO decide between noContent/notFound
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(new ConfigurationOut(configuration));
    }

    @PostMapping("/{key}")
    @ContextJwt
    public ResponseEntity<ConfigurationOut> create(@PathVariable String key, @RequestBody String value, @AuthenticationPrincipal AtlassianHostUser hostUser) {
        if (!isValidHost(hostUser)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Configuration configuration = configurationService.create(key, value, hostUser.getHost().getClientKey());
        return configuration == null
                //TODO decide between noContent/notFound
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(new ConfigurationOut(configuration));
    }

    @PutMapping("/{key}")
    @ContextJwt
    public ResponseEntity<ConfigurationOut> update(@PathVariable String key, @RequestBody String value, @AuthenticationPrincipal AtlassianHostUser hostUser) {
        if (!isValidHost(hostUser)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Configuration configuration = configurationService.update(key, value, hostUser.getHost().getClientKey());
        return configuration == null
                //TODO decide between noContent/notFound
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(new ConfigurationOut(configuration));
    }

    @DeleteMapping("/{key}")
    @ContextJwt
    public ResponseEntity delete(@PathVariable("key") String key, @AuthenticationPrincipal AtlassianHostUser hostUser) {
        if (!isValidHost(hostUser)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        boolean deleted = configurationService.delete(key, hostUser.getHost().getClientKey());
        return deleted
                //TODO decide between noContent/notFound
                ? ResponseEntity.ok().build()
                : ResponseEntity.noContent().build();
    }

    private boolean isValidHost(AtlassianHostUser hostUser) {
        return hostUser != null && hostUser.getHost() != null;
    }
}
