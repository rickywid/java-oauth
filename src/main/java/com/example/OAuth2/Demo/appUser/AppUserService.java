package com.example.OAuth2.Demo.appUser;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserService  implements UserDetailsService {

    private AppUserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AppUserService(AppUserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        AppUser user = userRepository.findByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException("user not found");
        }
        AppUserPrincipal userPrincipal = new AppUserPrincipal(user);
        return userPrincipal;
    }

    public void createUser(AppUser user) {
        AppUser newUser = new AppUser();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(newUser);
    }
}
