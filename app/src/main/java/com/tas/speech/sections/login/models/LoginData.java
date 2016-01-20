package com.tas.speech.sections.login.models;

import java.io.Serializable;


public class LoginData implements Serializable{

    private UserDetails UserDetails;

    public LoginData() {

    }

    public UserDetails getUserDetails() {
        return UserDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.UserDetails = userDetails;
    }
}
