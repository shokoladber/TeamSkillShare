package com.skills.skills.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
public class User extends AbstractEntity {

    //@Column(name = "username")
    @NotNull
    private String username;

    //@Column(name = "password")
    @NotNull
    private String pwHash;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @OneToOne(cascade = CascadeType.ALL)
    @Valid
    @NotNull
    private UserProfile userProfile;

    public User(String username, String password) {
        this.username = username;
        this.pwHash = encoder.encode(password);
    }

    public User(String username, String password, UserProfile userProfile) {
        this(username, password);
        this.userProfile = userProfile;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

<<<<<<< HEAD
//    public String getPwHash() {
//        return pwHash;
//    }
=======
    public String getPwHash() {
        return pwHash;
    }
>>>>>>> 520f72b3fe0b4c938ffcf2b186f422c52aba6950

}
