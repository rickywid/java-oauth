package com.example.OAuth2.Demo.OAuth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        /*
        * Returns an OAuth2User after obtaining the user attributes of the End-User from the UserInfo Endpoint.
         */
        System.out.println("loadUser: " + userRequest);
        OAuth2User user = super.loadUser(userRequest);
        return new CustomOAuth2User(user);
    }
}
