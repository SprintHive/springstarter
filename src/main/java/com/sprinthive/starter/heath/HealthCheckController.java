package com.sprinthive.starter.heath;

import com.sprinthive.starter.PropsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@Slf4j
@RestController
public class HealthCheckController {

    @Autowired
    PropsService propsService;

    @PostConstruct
    private void init() {
      log.info("Health check "+ heathCheck() );
    }

    @RequestMapping(value = "/ping")
    private String ping() {
        return "OK";
    }

    @RequestMapping(value = "/health/check")
    private String heathCheck() {
        return propsService.heathCheck();
    }
}
