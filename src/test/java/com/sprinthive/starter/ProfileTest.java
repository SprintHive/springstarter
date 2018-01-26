package com.sprinthive.starter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ProfileTest {

    @Autowired
    Environment environment;

    @Value("${starter.service.props.testValue:default.testValue}")
    private String testValue;

    @Test
    public void confirmActiveProfileIsTest() {
        assertThat(environment.getActiveProfiles().length).isEqualTo(1);
        assertThat(environment.getActiveProfiles()[0]).isEqualTo("test");
    }

    @Test
    public void props() {
        assertThat(testValue).isEqualTo("test.value");
    }

}
