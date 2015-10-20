package com.example.dbykovskyy.sumofus.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dbykovskyy.sumofus.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class UserProfileActivity extends AppCompatActivity {
    final String userProfilePhotoUrl = "https://scontent.fsnc1-1.fna.fbcdn.net/hprofile-xfp1/v/t1.0-1/c0.0.480.480/p480x480/1470006_10202041396829567_1353019578_n.jpg?oh=2b848931575064640d24719cfd9a0eeb&oe=568F11E8";

    ImageView ivUserProfile;
    TextView userName;
    TextView userEmail;
    TextView userPhoneNumber;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        ivUserProfile = (ImageView) findViewById(R.id.ivProfilePic);
        userName = (TextView) findViewById(R.id.tv_user_name);
        userEmail = (TextView) findViewById(R.id.tv_userEmail);
        userPhoneNumber = (TextView) findViewById(R.id.tv_userPhone);


        //making user profile photo oval
       ivUserProfile.setImageResource(0);
        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(this.getResources().getColor(R.color.grey_200))
                .borderWidthDp(3)
                .cornerRadiusDp(100)
                .oval(false)
                .build();

        //setting user profile photo
        Picasso.with(this)
                .load(userProfilePhotoUrl).resize(400, 400)
                .transform(transformation)
                .into(ivUserProfile);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_profile, menu);
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
