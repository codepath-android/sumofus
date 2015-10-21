package com.example.dbykovskyy.sumofus.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.dbykovskyy.sumofus.models.Campaign;
import com.example.dbykovskyy.sumofus.R;
import com.example.dbykovskyy.sumofus.adapter.CampaignItemAdapter;
import com.example.dbykovskyy.sumofus.models.CampaignParse;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

public class CampaignsActivity extends AppCompatActivity {
    String imageUrl; // = "http://sumofus.org/wp-content/uploads/2015/10/38b73ede-6a13-433c-9c76-a567ccfea8b1.jpg";
    String shortDescriptoin; // = "The third-largest palm oil corporation in the world is exploiting refugees and clearing rainforests";
    String longDescriptoin; // = "Standard Chartered, a massive international bank, is about to bankroll a Malaysian palm oil producer responsible for horrific slave-labour conditions and widespread environmental destruction.";

    private ArrayList<Campaign> campaigns;
    private CampaignItemAdapter adapterCampaigns;
    private ListView lvCampaigns;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaigns);
        //bind together Collection, Adapter and ListView
        campaigns = new ArrayList<Campaign>();
        adapterCampaigns = new CampaignItemAdapter(this, campaigns);
        lvCampaigns = (ListView) findViewById(R.id.lvCampaigns);
        lvCampaigns.setAdapter(adapterCampaigns);



        //fetching Campaigns from Parse to our empty Collection
        if (!ParseCrashReporting.isCrashReportingEnabled()) {
            setupParse();
            populateCampaignsParse();
        }

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

            switch(item.getItemId()) {

                case R.id.action_account:
                {
                    Intent i = new Intent(this, UserProfileActivity.class);
                    startActivity(i);
                }
                break;
                case  R.id.action_donate:
                {
                    onScanPress();
                }
                break;
            }


        return super.onOptionsItemSelected(item);
    }

    //This is a helper method when backend is not working
/*    public ArrayList<Campaign> populateCampaigns() {
        //campaigns = new ArrayList<Campaign>();
        for (int i = 0; i <= 10; i++) {
            Campaign camp = new Campaign(imageUrl, shortDescriptoin, longDescriptoin);
            campaigns.add(camp);
        }
        return campaigns;
    }*/


    public void populateCampaignsParse() {

        ParseQuery<CampaignParse> query = ParseQuery.getQuery(CampaignParse.class);
        query.findInBackground(new FindCallback<CampaignParse>() {
            @Override
            public void done(List<CampaignParse> list, ParseException e) {
                for (CampaignParse c : list) {
                    Campaign camp = new Campaign(c.getCampaignUrl(), c.getOverview(), c.getDescription());
                    campaigns.add(camp);
                    Log.d("DEBUG:", c.getDescription());
                }
                adapterCampaigns.addAll();
            }
        });
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
        Intent i = new Intent(this, NewCampaignActivity.class);
        startActivityForResult(i, 0);

    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityResult(resultCode, resultCode, data);

        if (resultCode == 777) {
            String resultDisplayStr;
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);

                // Never log a raw card number. Avoid displaying it, but if necessary use getFormattedCardNumber()
                resultDisplayStr = "Card Number: " + scanResult.getRedactedCardNumber() + "\n";

                // Do something with the raw number, e.g.:
                // myService.setCardNumber( scanResult.cardNumber );

                if (scanResult.isExpiryValid()) {
                    resultDisplayStr += "Expiration Date: " + scanResult.expiryMonth + "/" + scanResult.expiryYear + "\n";
                }

                if (scanResult.cvv != null) {
                    // Never log or display a CVV
                    resultDisplayStr += "CVV has " + scanResult.cvv.length() + " digits.\n";
                }

                if (scanResult.postalCode != null) {
                    resultDisplayStr += "Postal Code: " + scanResult.postalCode + "\n";
                }
            }
            else {
                resultDisplayStr = "Scan was canceled.";
            }
            // do something with resultDisplayStr, maybe display it in a textView
            // resultTextView.setText(resultStr);
        }
        // else handle other activity results
    }

    public void onScanPress() {
        Intent scanIntent = new Intent(this, CardIOActivity.class);

        // customize these values to suit your needs.
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // default: false

        // MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.
        startActivityForResult(scanIntent, 777);
    }

}
