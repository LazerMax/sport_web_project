package org.example.dto;


import lombok.Data;

@Data
public class UserInfoDto {
    private String username;
    //private String password;
    private String profile;
    private String fullName;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName){
        this.fullName = fullName;
    }
}
