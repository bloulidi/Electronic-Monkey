package com.stackroute.userservice.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1")
@Api(tags = { com.stackroute.userservice.swagger.SpringFoxConfig.USER_TAG })
public class UserController {

}