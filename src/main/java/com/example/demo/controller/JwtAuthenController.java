package com.example.demo.controller;

import com.example.demo.config.JwtTokenUtil;
import com.example.demo.model.*;
import com.example.demo.service.JwtUserDetailsService;
import com.example.demo.service.UserLearnService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class JwtAuthenController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserLearnService userLearnService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        User user = userService.findByUsername(authenticationRequest.getUsername());
        user.setToken(token);
        userService.updateUser(user);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody RequestUser user) throws Exception {
        List<User> users = userService.findAll();
        boolean isExit = false;
        for (User userFor : users) {
            if (userFor.getUsername().equals(user.getUsername())) isExit = true;
        }
        if (isExit == false) {
            User userSave = new User(user.getUsername(), user.getPassword());
            userService.createUser(userSave);
            userLearnService.create(new UserLearn(userSave));
            return ResponseEntity.ok(userDetailsService.save(user));
        } else {
            return ResponseEntity.ok(null);
        }
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
