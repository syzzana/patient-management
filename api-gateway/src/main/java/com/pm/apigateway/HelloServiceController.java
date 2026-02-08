package com.pm.apigateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloServiceController {

    @GetMapping("/hello")
    public String hello() {
        return "hello 08 feb 20:59";
    }
}

