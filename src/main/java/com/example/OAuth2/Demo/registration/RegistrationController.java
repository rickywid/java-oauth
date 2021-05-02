package com.example.OAuth2.Demo.registration;

import com.example.OAuth2.Demo.appUser.AppUser;
import com.example.OAuth2.Demo.appUser.AppUserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class RegistrationController {

    private RegistrationService registrationService;
    private AppUserService appUserService;

    public RegistrationController(RegistrationService registrationService, AppUserService userappUserServiceService) {
        this.registrationService = registrationService;
        this.appUserService = appUserService;
    }

    @PostMapping(path = "/register")
    public String register(@RequestBody AppUser user) {
        System.out.println("caled");
        registrationService.register(user);
        return "user created";
    }
}

