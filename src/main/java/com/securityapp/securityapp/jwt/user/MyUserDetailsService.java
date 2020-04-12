package com.securityapp.securityapp.jwt.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
 
    @Autowired
    private UserRepository userRepository;
 
    @Override
    public UserDetails loadUserByUsername(String username) {
        MyUser user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new MyUserPrincipal(user);//knote: notice we create and return MyUserPrincipal
    }
    
    public void saveUser(MyUser user) {
    	userRepository.saveAndFlush(user);
    }
}