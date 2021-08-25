package com.stackroute.apigateway.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class JwtController {

    @Value("${app.validationConfirmationMessage}")
    private String validationConfirmationMessage;

    @GetMapping("data")
    public String getSensitiveData() {
        return validationConfirmationMessage;
    }

}
