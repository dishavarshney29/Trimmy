/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dishavarshney.trimmy.service.impls;

import com.github.dishavarshney.trimmy.models.Users;
import com.github.dishavarshney.trimmy.repositories.UserRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @author Disha Varshney
 */
@Service
public class LoginServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public boolean registerNewUser(String username, String password, String Id) {
        boolean lReturn = false;
        Optional<Users> findByUsername = userRepository.findByUsername(username.toLowerCase());
        if (!findByUsername.isPresent()) {
            Users users = new Users();
            users.setUsername(username.toLowerCase());
            users.setToken(UUID.randomUUID().toString());
            if (ObjectUtils.isEmpty(password) && ObjectUtils.isEmpty(Id)) {
                lReturn = false;
            } else {
                if (!ObjectUtils.isEmpty(password)) {
                    users.setPassword(passwordEncoder.encode(password));
                } else if (!ObjectUtils.isEmpty(Id)) {
                    users.setPassword(Id);
                }
                userRepository.save(users);
                lReturn = true;
            }
        } else if (findByUsername.isPresent()) {
            Users users = findByUsername.get();
            try {
                passwordEncoder.upgradeEncoding(users.getPassword());
            } catch (IllegalArgumentException e) {
                users.setPassword(passwordEncoder.encode(password));
                userRepository.save(users);
                lReturn = true;
            }
        }
        return lReturn;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> findByUsername = userRepository.findByUsername(username.toLowerCase());
        if (findByUsername.isPresent()) {
            return findByUsername.get();
        }
        return null;
    }
}
