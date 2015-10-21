package com.example.dbykovskyy.sumofus.application;

import android.app.Application;

import com.example.dbykovskyy.sumofus.R;
import com.example.dbykovskyy.sumofus.models.User;
import com.parse.Parse;
import com.parse.ParseCrashReporting;
import com.parse.ParseObject;

/**
 * Created by andres on 10/20/15.
 */
public class User_Parse extends Application {
    public static final String YOUR_APPLICATION_ID = "LCPA3eHqb1CyiP2pmZGdDXw6I54pZKNCoxTGAsTt";
    public static final String YOUR_CLIENT_KEY = "hweFBlma45UrDKmcRNSlcdG7Fidp6lST0Siu8tcx";
    /*
    public static final String YOUR_APPLICATION_ID = "yvDL7S0lQ80ftgduGQ9zTK27Elqlvi3YtQftdKcG";
    public static final String YOUR_CLIENT_KEY = "lZ7kls44MmqEN80L9nu10xy3IcAKPVdKJim7ay0y";
     */

    @Override
    public void onCreate() {
        super.onCreate();

        //if we don't have this statement it will crash the APP
        if (!ParseCrashReporting.isCrashReportingEnabled()) {

            setupParse();

        }}

    public void setupParse() {
        ParseCrashReporting.enable(this);
        // Register your parse models here
        ParseObject.registerSubclass(User.class);

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, getApplicationContext().getString(R.string.parse_app_id), getApplicationContext().getString(R.string.parse_client_key));
        //Parse.initialize(this);





    }
}