package com.stackroute.userservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authenticate")
public class JwtController {

    @Value("${app.validationConfirmationMessage}")
    private String validationConfirmationMessage;

    @GetMapping("data")
    public String getSensitiveData() {
        return validationConfirmationMessage;
    }

}
