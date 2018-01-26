package com.sprinthive.starter;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProfileDefaultTest {

    @Autowired
    Environment environment;

    @Value("${starter.service.props.testValue:default.testValue}")
    private String testValue;

    @Test
    public void confirmActiveProfileIsTest() {
        assertThat(environment.getActiveProfiles().length).isEqualTo(0);
    }

    @Test
    public void props() {
        log.debug(testValue);
        assertThat(testValue).isEqualTo("dev.value");
    }

}
