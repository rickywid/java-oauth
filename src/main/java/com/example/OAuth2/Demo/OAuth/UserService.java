package com.example.OAuth2.Demo.OAuth;

import com.example.OAuth2.Demo.appUser.AppUser;
import com.example.OAuth2.Demo.appUser.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private AppUserRepository userRepository;

    public void processOAuthPostLogin(String username) {

        System.out.println("processOAuthPostLogin");
        System.out.println("username: " + username);
        AppUser user = userRepository.findByUsername(username);
        System.out.println(user);
        System.out.println("/end");
        if(user == null) {
            System.out.println("=======================");
            System.out.println("user not found");
            System.out.println("=======================");
            AppUser newUser = new AppUser();
            newUser.setUsername(username);
            newUser.setProvider("github");

            userRepository.save(newUser);
        }
    }
}
