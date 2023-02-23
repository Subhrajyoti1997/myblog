package com.blog.myblog5.controller;

import com.blog.myblog5.entity.Role;
import com.blog.myblog5.entity.User;
import com.blog.myblog5.payload.JwtAuthResponse;
import com.blog.myblog5.payload.LoginDto;
import com.blog.myblog5.payload.SignUpDto;
import com.blog.myblog5.repository.RoleRepository;
import com.blog.myblog5.repository.UserRepository;
import com.blog.myblog5.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    //authenticationManager object not automatically created so to do that first go to securityConfig and make method with annoted @Bean
    @PostMapping("/signin")
    public ResponseEntity<JwtAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));
        //if above line true(valid(username,password)) then below line run else authentication fails it shows bad credentials
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token= jwtTokenProvider.generateToken(authentication);
        //return new ResponseEntity<>(new JwtAuthResponse(token), HttpStatus.OK);
         return ResponseEntity.ok(new JwtAuthResponse(token));
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){

        if(userRepository.existsByUsername(signUpDto.getUsername())){
            return new ResponseEntity<> ("Username is already taken",HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<> ("email already taken ",HttpStatus.BAD_REQUEST);
        }
        User user =new User();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword() ));
      //will set admin role for user by default
        Role roles =roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));
        //here set of roles so store in collections.singleton()
          userRepository.save(user);
          return new ResponseEntity<>("User registered successfully",HttpStatus.OK);

    }

}
