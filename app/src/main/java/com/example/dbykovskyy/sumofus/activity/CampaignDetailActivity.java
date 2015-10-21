package com.example.dbykovskyy.sumofus.activity;

import android.app.Activity;

import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;

import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dbykovskyy.sumofus.R;
import com.example.dbykovskyy.sumofus.models.Campaign;
import com.example.dbykovskyy.sumofus.utils.BitmapScaler;
import com.example.dbykovskyy.sumofus.utils.CustomProgress;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CampaignDetailActivity extends AppCompatActivity {

    private static final int TAKE_PHOTO_CODE = 1;
    private static final int PICK_PHOTO_CODE = 2;
    private static final int CROP_PHOTO_CODE = 3;
    private static final int POST_PHOTO_CODE = 4;

    private Uri photoUri;
    private Bitmap photoBitmap;

    TextView tvCampaignText;
    ImageView ivCampaignImage;
    CustomProgress customProgress;
    Button btSignPetition;
    TextView tvOurGoalIs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_campaign_detail);
        //finding views
        tvCampaignText = (TextView)findViewById(R.id.tvCampaignTextDetail);
        ivCampaignImage = (ImageView)findViewById(R.id.ivCampaignDetail);
        customProgress = (CustomProgress)findViewById(R.id.pbGoal);
        btSignPetition = (Button)findViewById(R.id.btTakeActionDetail);
        tvOurGoalIs = (TextView)findViewById(R.id.tvOurGoalIs);
        //getting intent
        Campaign campaign = (Campaign) getIntent().getSerializableExtra("camp");

       //setting ip the views
        ivCampaignImage.setImageResource(0);
        Picasso.with(this).load(campaign.getImageUrl()).into(ivCampaignImage);



        //make text of campaign srollable
        tvCampaignText.setMovementMethod(new ScrollingMovementMethod());
        tvCampaignText.setText(campaign.getLongDescription());

        //set text above progress
        tvOurGoalIs.setText("Signed 75,000/10,000");

        //setting up progress bar
        customProgress.setProgressColor(getResources().getColor(R.color.green_500));
        customProgress.setProgressBackgroundColor(getResources().getColor(R.color.green_200));
        customProgress.setMaximumPercentage(0.75f);
        customProgress.useRoundedRectangleShape(30.0f);
        customProgress.setShowingPercentage(true);


        btSignPetition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(CampaignDetailActivity.this, SignPetitionActivity.class);
                CampaignDetailActivity.this.startActivity(i);


            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_campaign_detail, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_take_photo:
            {
                // create Intent to take a picture and return control to the calling application
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                photoUri = Uri.fromFile(getOutputMediaFile()); // create a file to save the image
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri); // set the image file name
                // Start the image capture intent to take photo
                startActivityForResult(intent, TAKE_PHOTO_CODE);
            }
            break;
            case R.id.action_use_existing:
            {
                // Take the user to the gallery app
                Intent photoGalleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(photoGalleryIntent , PICK_PHOTO_CODE);
            }
            break;
            case  R.id.action_share:
            {
                setupShareIntent();
            }
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == TAKE_PHOTO_CODE) {
                 //cropPhoto(photoUri);

                photoBitmap = BitmapFactory.decodeFile(photoUri.getPath());
                Bitmap resizedImage =  BitmapScaler.scaleToFitWidth(photoBitmap, 300);
                ivCampaignImage.getAdjustViewBounds();
                ivCampaignImage.setScaleType(ImageView.ScaleType.FIT_XY);
                ivCampaignImage.setImageBitmap(resizedImage);

            } else if (requestCode == PICK_PHOTO_CODE) {

                photoUri = data.getData();
                try {
                    photoBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Bitmap resizedImage =  BitmapScaler.scaleToFitWidth(photoBitmap, 300);
                ivCampaignImage.getAdjustViewBounds();
                ivCampaignImage.setScaleType(ImageView.ScaleType.FIT_XY);
                ivCampaignImage.setImageBitmap(resizedImage);

                //cropPhoto(photoUri);
            } else if (requestCode == CROP_PHOTO_CODE) {
                photoBitmap = data.getParcelableExtra("data");

                ivCampaignImage.getAdjustViewBounds();
                ivCampaignImage.setScaleType(ImageView.ScaleType.FIT_XY);
                ivCampaignImage.setImageBitmap(photoBitmap);

                Toast.makeText(this, "I just took a picture", Toast.LENGTH_LONG).show();

            }
        }
    }

    //this is to crop after taking a pic
    private void cropPhoto(Uri photoUri) {
        //call the standard crop action intent (the user device may not support it)
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        //indicate image type and Uri
        cropIntent.setDataAndType(photoUri, "image/*");
        //set crop properties
        cropIntent.putExtra("crop", "true");
        //indicate aspect of desired crop
        cropIntent.putExtra("aspectX", 1.5);
        cropIntent.putExtra("aspectY", 1);
        //indicate output X and Y
        cropIntent.putExtra("outputX", 300);
        cropIntent.putExtra("outputY", 200);
        //retrieve data on return
        cropIntent.putExtra("return-data", true);
        //start the activity - we handle returning in onActivityResult
        startActivityForResult(cropIntent, CROP_PHOTO_CODE);
    }


    //this is to create picture filename
    private static File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "sumofus");
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            return null;
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");

        return mediaFile;
    }


    // Gets the image URI and setup the associated share intent to hook into the provider

    public void setupShareIntent() {
        // Fetch Bitmap Uri locally
        ImageView ivImage = (ImageView)findViewById(R.id.ivCampaignDetail);
        // Get access to the URI for the bitmap
        Uri bmpUri = getLocalBitmapUri(ivImage);
        if (bmpUri != null) {
            // Construct a ShareIntent with link to image
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Title Of Test Post");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "https://www.sumofus.com");
            shareIntent.setType("image/*");
            // Launch sharing dialog for image
            startActivity(Intent.createChooser(shareIntent, "send"));
        } else {
            Toast.makeText(this, "Some error occured during sharing", Toast.LENGTH_LONG).show();

        }

    }

    // Returns the URI path to the Bitmap displayed in specified ImageView

    public Uri getLocalBitmapUri(ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable){
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }

        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            File file =  new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".png");
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;

    }



}
