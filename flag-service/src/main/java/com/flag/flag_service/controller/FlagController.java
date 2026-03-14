package com.flag.flag_service.controller;

import com.flag.flag_service.entity.UserValidationRequestDTO;
import com.flag.flag_service.service.FlagService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flag-service")
public class FlagController {
    private final FlagService flagService;
    public FlagController(FlagService flagService) {
        this.flagService = flagService;
    }

    @PostMapping("/validate-user")
    public Boolean validateUser(@RequestBody UserValidationRequestDTO userValidationRequestDTO){
    return  flagService.validateUser(userValidationRequestDTO);
    }

}
