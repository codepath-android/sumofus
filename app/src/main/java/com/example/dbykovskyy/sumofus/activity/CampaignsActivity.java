package com.example.dbykovskyy.sumofus.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dbykovskyy.sumofus.models.Campaign;
import com.example.dbykovskyy.sumofus.R;
import com.example.dbykovskyy.sumofus.adapter.CampaignItemAdapter;
import com.example.dbykovskyy.sumofus.models.User;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseCrashReporting;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.ParseException;
import java.util.ArrayList;

public class CampaignsActivity extends AppCompatActivity {
    String imageUrl = "http://sumofus.org/wp-content/uploads/2015/10/38b73ede-6a13-433c-9c76-a567ccfea8b1.jpg";
    String shortDescriptoin = "The third-largest palm oil corporation in the world is exploiting refugees and clearing rainforests";
    String longDescriptoin = "Standard Chartered, a massive international bank, is about to bankroll a Malaysian palm oil producer responsible for horrific slave-labour conditions and widespread environmental destruction.";

    private ArrayList<Campaign> campaigns;
    private CampaignItemAdapter adapterCampaigns;
    private ListView lvCampaigns;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_campaigns);

        populateCampaigns();

        adapterCampaigns = new CampaignItemAdapter(this, campaigns);
        lvCampaigns = (ListView) findViewById(R.id.lvCampaigns);
        lvCampaigns.setAdapter(adapterCampaigns);

        initializeParse();
        createParseObject();
        readParseObject();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_campaigns, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public ArrayList<Campaign> populateCampaigns(){
        campaigns = new ArrayList<Campaign>();
        for(int i=0; i<=10; i++){
            Campaign camp = new Campaign(imageUrl, shortDescriptoin, longDescriptoin);
            campaigns.add(camp);
        }
     return campaigns;
    }

    public void initializeParse() {
        // Initializing Crash Reporting.
        ParseCrashReporting.enable(this);

        // Local Datastore.
        Parse.enableLocalDatastore(this);

        // Initialization code
        Parse.initialize(this);

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        //  Public read access.
        // defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

        ParseAnalytics.trackAppOpenedInBackground(getIntent());

    }

    public void createParseObject() {

        ParseObject user = new ParseObject("User");
        user.put("name", "Alberto C");
        user.put("screenname", "xopmac");
        user.put("email", "xopmac@gmail.com");
        user.put("isAdmin", true);
        user.saveInBackground();

        ParseObject gameScore = new ParseObject("GameScore");
        gameScore.put("score", 1337);
        gameScore.put("playerName", "Sean Plott");
        gameScore.put("cheatMode", false);
        gameScore.saveInBackground();


        String myUser = user.getString("username");

    }

    public void readParseObject() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
        query.getInBackground("xWMyZ4YEGZ", new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, com.parse.ParseException e) {
                if (e == null) {
                    // Success
                    Toast.makeText(getApplicationContext(), "Welcome" + parseObject.getString("name"), Toast.LENGTH_LONG).show();


                } else {
                    // something went wrong
                }
            }
        });
    }

}
