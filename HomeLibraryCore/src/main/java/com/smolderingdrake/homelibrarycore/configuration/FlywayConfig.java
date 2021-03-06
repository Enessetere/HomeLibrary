package com.smolderingdrake.homelibrarycore.configuration;

import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayConfigurationCustomizer;
import org.springframework.stereotype.Component;

@Component
public class FlywayConfig implements FlywayConfigurationCustomizer {

    @Override
    public void customize(final FluentConfiguration configuration) {
        configuration.baselineOnMigrate(true).outOfOrder(true);
    }
}
