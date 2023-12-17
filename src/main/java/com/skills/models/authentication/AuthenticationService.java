package com.skills.models.authentication;

import com.skills.data.UserRepository;
import com.skills.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean authenticate(String username, String password) {
        // Retrieve user by username from the database
        User user = userRepository.findByUsername(username).getUser();

        // Check if the user exists and the password matches
        return user != null && passwordEncoder.matches(password, user.getPwHash());
    }
}
