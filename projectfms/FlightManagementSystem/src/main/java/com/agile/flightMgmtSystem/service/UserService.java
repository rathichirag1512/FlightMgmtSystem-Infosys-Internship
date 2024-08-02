package com.agile.flightMgmtSystem.service;

import com.agile.flightMgmtSystem.bean.FlightUser;
import com.agile.flightMgmtSystem.repository.UserRepository;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


/*
@Service
public class UserService {

	@Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

   

        public void saveUser(FlightUser user) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }

        public boolean validateUser(String email, String password) {
            FlightUser user = userRepository.findByEmail(email);
            return user != null && bCryptPasswordEncoder.matches(password, user.getPassword());
        }
        

        public boolean emailExists(String email) {
            return userRepository.findByEmail(email) != null;
        }
        
        public String getType() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
                String email = ((UserDetails) authentication.getPrincipal()).getUsername();
                FlightUser user = userRepository.findByEmail(email);
                if (user != null) {
                    return user.getUserType();
                }
            }
            return null;
        }
        */
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository repository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    

    
    public void save(FlightUser user) {
    	user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
    }
    
    public boolean emailExists(String email) {
        return repository.findByEmail(email) != null;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        FlightUser user = repository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(), user.getPassword(), new ArrayList<>()
        );
    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public String getType(String email) {
        FlightUser user = repository.findByEmail(email);
        if (user != null) {
            return user.getUserType();
        }
        return null;
    }
    
    
}