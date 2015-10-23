package com.example.dbykovskyy.sumofus.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/*
* This is the 'current' app user.
* An user can be a standard user or an administrator
*
* Sum of Us defines a Super-user, as one that can add pictures to campaings. We'll eventually implement that feature.
* */

@ParseClassName("User")
public class User extends ParseObject {

    public String getName() {
        return "";
    }
    public String getUserId() {
        return getString("userId");
    }

    public String getBody() {
        return getString("body");
    }

    public void setUserId(String userId) {
        put("userId", userId);
    }

    public void setBody(String body) {
        put("body", body);
    }

}