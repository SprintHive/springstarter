package com.sprinthive.starter.health;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.core.env.Environment;
import javax.annotation.PostConstruct;

@Slf4j
public class PropsHealthCheck implements HealthIndicator {

    @Autowired
    private Environment environment;

    @Value("${starter.service.props.testValue:default.testValue}")
    private String testValue;

    @PostConstruct
    private void init() {
      log.info("Health check: " + health().getStatus().getDescription() );
    }

    @Override
    public Health health() {
        String[] activeProfiles = environment.getActiveProfiles();
        if (activeProfiles.length > 0) {
            String profile = activeProfiles[0];
            log.debug("Health check profile: {} testValue: {}", profile, testValue);
            if ("test".equals(profile) && !testValue.equals("test.value")) {
                return Health.down(new IllegalStateException("Expected test.value Actual " + testValue)).build();
            }
            if ("preprod".equals(profile) && !testValue.equals("preprod.value")) {
                return Health.down(new IllegalStateException("Expected preprod.value, actual: " + testValue)).build();
            }
            if ("production".equals(profile) && !testValue.equals("prod.value")) {
                return Health.down(new IllegalStateException("Expected prod.value, actual: " + testValue)).build();
            }
        } else {
            if (!testValue.equals("dev.value")) {
                return Health.down(new IllegalStateException("Expected dev.value, actual: " + testValue)).build();
            }
        }

        return Health.up().build();
    }
}
