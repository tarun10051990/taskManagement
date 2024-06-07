package com.taskmanagement.demo.config;

import com.taskmanagement.demo.entity.User;
import com.taskmanagement.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailService {

    @Autowired
    private UserRepository userRepository;

    public UserDetails loadByUsername(String username){
        Optional<User> userInfo = userRepository.findByUsername(username);
        return userInfo.map(UserDetailImpl::new)
                .orElseThrow(()-> new UsernameNotFoundException("User not found....."));

    }
}
