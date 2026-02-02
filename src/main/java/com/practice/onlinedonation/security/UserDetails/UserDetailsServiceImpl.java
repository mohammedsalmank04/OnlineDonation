package com.practice.onlinedonation.security.UserDetails;

import com.practice.onlinedonation.Exception.ResourceNotFoundException;
import com.practice.onlinedonation.Model.User;
import com.practice.onlinedonation.Repository.UserRepository;
import com.practice.onlinedonation.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //System.out.println("User NAME -------------------- " + userName);
        User user = userRepository.findByEmail(userName).orElseThrow(
                () -> new ResourceNotFoundException("User","Email: ", userName)
        );
        return UserDetailsImpl.build(user);
    }
}
