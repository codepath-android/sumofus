package com.example.dbykovskyy.sumofus.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dbykovskyy.sumofus.models.Campaign;
import com.example.dbykovskyy.sumofus.R;
import com.example.dbykovskyy.sumofus.adapter.CampaignItemAdapter;
import com.example.dbykovskyy.sumofus.models.CampaignParse;
import com.example.dbykovskyy.sumofus.models.Supporter;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseCrashReporting;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;

public class CampaignsActivity extends YouTubeBaseActivity {
    String imageUrl = "http://sumofus.org/wp-content/uploads/2015/10/38b73ede-6a13-433c-9c76-a567ccfea8b1.jpg";
    String shortDescriptoin = "The third-largest palm oil corporation in the world is exploiting refugees and clearing rainforests";
    String longDescriptoin = "Standard Chartered, a massive international bank, is about to bankroll a Malaysian palm oil producer responsible for horrific slave-labour conditions and widespread environmental destruction.";

    private ArrayList<Campaign> campaigns;
    private ArrayList<CampaignParse> campaignParse;
    private CampaignItemAdapter adapterCampaigns;
    private ListView lvCampaigns;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_campaigns);

        //if we don't have this statement it will crash the APP
        if (!ParseCrashReporting.isCrashReportingEnabled()) {
            setupParse();

        }

        populateCampaigns();

        adapterCampaigns = new CampaignItemAdapter(this, campaigns);
        lvCampaigns = (ListView) findViewById(R.id.lvCampaigns);
        lvCampaigns.setAdapter(adapterCampaigns);

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

    public ArrayList<Campaign> populateCampaigns() {
        campaigns = new ArrayList<Campaign>();
        for (int i = 0; i <= 10; i++) {
            Campaign camp = new Campaign(imageUrl, shortDescriptoin, longDescriptoin);
            campaigns.add(camp);
        }
        return campaigns;
    }

    public ArrayList<CampaignParse> populateCampaignsParse() {
        campaignParse = new ArrayList<CampaignParse>();
        for (int i = 0; i <= 1; i++) {
            CampaignParse camp = new CampaignParse();
            campaignParse.add(camp);
        }
        return campaignParse;
    }



    public void setupParse() {
        // Initializing Crash Reporting.
        ParseCrashReporting.enable(this);

        // Local Datastore.
        Parse.enableLocalDatastore(this);

        // Initialization code
        //ParseObject.registerSubclass(Supporter.class);
        ParseObject.registerSubclass(CampaignParse.class);
        Parse.initialize(this);
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        //  Public read access.
        // defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
        // ParseAnalytics.trackAppOpenedInBackground(getIntent());


    }

    public void createCampaign(View view) {
       // populateCampaignsParse();

        Toast.makeText(getApplicationContext(), "Create", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, NewCampaignActivity.class);
        startActivityForResult(i, 0);

    }
}
