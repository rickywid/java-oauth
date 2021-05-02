package com.example.OAuth2.Demo.registration;

import com.example.OAuth2.Demo.appUser.AppUser;
import com.example.OAuth2.Demo.appUser.AppUserService;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    private AppUserService appUserService;

    public RegistrationService(AppUserService userService) {
        this.appUserService = userService;
    }

    public void register(AppUser appUser) {
        System.out.println(appUser);
        appUserService.createUser(appUser);
    }
}




