package com.ssafy.backend.domain.util.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * alb에서 health 체크하는 용도의 컨트롤러
 */

@Slf4j
@RestController
@RequestMapping("/health")
public class HealthCheckController {

    @GetMapping
    public String health(){
        log.info("health check");
        return "ok";
    }
}
