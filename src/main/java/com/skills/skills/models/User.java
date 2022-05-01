package com.skills.skills.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
public class User extends AbstractEntity {

   // @Column(name = "username")
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

    public String getPwHash() {
        return pwHash;
    }

}






























//    public UserProfile getUserProfile() {
//        return userProfile;
//    }

//    public void setUserProfile(UserProfile userProfile) {
//        this.userProfile = userProfile;
//    }

//    public User(String username, String password) {}

    /* constructor takes a parameter named password
    and uses it to set the value of pwHash
    >> use encoder to create a hash from the given password
    */
//    public User(String username, String firstName, String lastName,String password, String email,
//                 int phoneNumber, String zipCode) {
//        this.username = username;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.pwHash = encoder.encode(password);
//        this.email = email;
//        this.phoneNumber=phoneNumber;
//        this.zipCode=zipCode;



//    public String getUsername() {
//        return username;
//    }
//    public void setUsername(String username){
//        this.username=username;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//    public void setFirstName(String firstName){
//        this.firstName=firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//    public void setLastName(String lastName){
//        this.lastName=lastName;
//    }
//
//    public String getEmail(){
//        return email;
//    }
//    public void setEmail(String email){
//        this.email=email;
//    }
//
//    public int getPhoneNumber(){
//        return phoneNumber;
//    }
//    public void setPhoneNumber(int phoneNumber){
//        this.phoneNumber=phoneNumber;
//    }
//
//    public String getZipCode() {
//        return zipCode;
//    }
//
//    public void setZipCode(String zipCode) {
//        this.zipCode = zipCode;
//    }
//
//    public String getPwHash() {
//        return pwHash;
//    }
//
//    public void setPwHash(String pwHash) {
//        this.pwHash = pwHash;
//    }
//
//    /*User objects should also be responsible for determining
//        if a given password is a match for the hash stored by the object*/
//    public boolean isMatchingPassword(String password) {
//        return encoder.matches(password, pwHash);
//    }
//
//}
//























//    @Column(name = "first_name")
//    private String firstName;
//
//    @Column(name = "last_name")
//    private String lastName;

//    @Column(name = "email")
//    private String email;
//
//    @Column(name = "phone_number")
//    private int phoneNumber;
//
//    @Column(name= "zipcode")
//    private String zipCode;


//@NotNull
//private String email;
//
////        this.email = email;
//
//    public String getEmail() {
//        return email;
//    }





//    public void setUsername(String username) {
//        this.username = username;
//    }


//    public void setEmail(String email) {
//        this.email = email;
//    }

//    public String getPWHash() {
//        return PWHash;
//    }

//    public void setPWHash(String PWHash) {
//        this.PWHash = PWHash;
//    }






















































//    private String firstName;
//
//    private String lastName;
//
//    private String email;
//
//    public User(String firstName, String lastName, String email) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.email = email;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }