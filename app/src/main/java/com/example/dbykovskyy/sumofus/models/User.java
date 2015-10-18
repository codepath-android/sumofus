package com.example.dbykovskyy.sumofus.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
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
        return getString("name");
    }

    public String getScreenName() {
        return getString("screenname");
    }

    public String getEmail() {
        return getString("email");
    }

    public ParseFile getProfileImg() {
        return getParseFile("profileImg");
    }

    public boolean isAdmin() {
        return getBoolean("isAdmin");
    }

}
