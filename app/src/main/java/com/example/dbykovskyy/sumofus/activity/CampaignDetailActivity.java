package com.example.dbykovskyy.sumofus.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dbykovskyy.sumofus.R;
import com.example.dbykovskyy.sumofus.models.Campaign;
import com.example.dbykovskyy.sumofus.utils.CustomProgress;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseCrashReporting;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

public class CampaignDetailActivity extends AppCompatActivity {
    TextView tvCampaignText;
    ImageView ivCampaignImage;
    CustomProgress customProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_campaign_detail);
        //finding views
        tvCampaignText = (TextView)findViewById(R.id.tvCampaignTextDetail);
        ivCampaignImage = (ImageView)findViewById(R.id.ivCampaignDetail);
        customProgress = (CustomProgress)findViewById(R.id.pbGoal);

        //getting intent
        Campaign campaign = (Campaign) getIntent().getSerializableExtra("camp");

       //setting ip the views

        ivCampaignImage.setImageResource(0);
        Picasso.with(this).load(campaign.getImageUrl()).into(ivCampaignImage);

        tvCampaignText.setMovementMethod(new ScrollingMovementMethod());
        tvCampaignText.setText(campaign.getLongDescription());
        //setting up progress bar

        customProgress.setProgressColor(getResources().getColor(R.color.green_500));
        customProgress.setProgressBackgroundColor(getResources().getColor(R.color.green_200));
        customProgress.setMaximumPercentage(1.0f);
        customProgress.useRoundedRectangleShape(30.0f);
        customProgress.setShowingPercentage(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_campaign_detail, menu);
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
}
