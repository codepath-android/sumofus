package com.example.dbykovskyy.sumofus.adapter;


import android.content.Context;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dbykovskyy.sumofus.activity.CampaignDetailActivity;
import com.example.dbykovskyy.sumofus.models.Campaign;
import com.example.dbykovskyy.sumofus.R;
import com.example.dbykovskyy.sumofus.utils.BitmapScaler;
import com.example.dbykovskyy.sumofus.utils.Config;
import com.example.dbykovskyy.sumofus.utils.DeviceDimensionsHelper;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import java.lang.annotation.Target;
import java.util.ArrayList;


public class CampaignItemAdapter extends ArrayAdapter<Campaign> {

/*
        YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_REQUEST = 1;

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {
            youTubePlayer.cueVideo("fhWaJi1Hsfo"); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
        }

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

        if (youTubeInitializationResult.isUserRecoverableError()) {
            //youTubeInitializationResult.getErrorDialog(, RECOVERY_REQUEST).show();
        } else {
            //String error = String.format(getString(R.string.player_error), youTubeInitializationResult.toString());
            Toast.makeText(getContext(), "Some youtube error", Toast.LENGTH_LONG).show();
        }

    }*/

    final int screenSize = DeviceDimensionsHelper.getDisplayWidth(getContext());

    static class ViewHolder  {

        TextView tvShortCampaignDescription;
        ImageView ivCampaign;
        Button bvTakeAction;
        ProgressBar pb;
        YouTubePlayerView you;

    }

    public CampaignItemAdapter(Context context, ArrayList<Campaign> objects) {
        super(context,android.R.layout.simple_list_item_1,objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Campaign campaign = getItem(position);
        final ViewHolder viewHolder;

        if(convertView==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.campaign_item, parent, false);

            viewHolder.pb = (ProgressBar) convertView.findViewById(R.id.pbLoading);
            viewHolder.tvShortCampaignDescription = (TextView)convertView.findViewById(R.id.tvShortDescription);
            viewHolder.ivCampaign = (ImageView)convertView.findViewById(R.id.ivCampaign);


            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //setting up views
        viewHolder.tvShortCampaignDescription.setText(campaign.getShortDescription());

        viewHolder.ivCampaign.setImageResource(0);
        Picasso.with(getContext()).load(campaign.getImageUrl()).resize(screenSize, 0).into(viewHolder.ivCampaign);


/*        Picasso.with(getContext()).load(campaign.getImageUrl()).into(new com.squareup.picasso.Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                //int width = bitmap.getWidth();
                //int height = bitmap.getHeight();

                viewHolder.pb.setVisibility(ProgressBar.INVISIBLE);
                viewHolder.ivCampaign.setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

                viewHolder.pb.setVisibility(ProgressBar.VISIBLE);

            }
        });*/
        //setting onclick listener of the image

        viewHolder.ivCampaign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), CampaignDetailActivity.class);
                i.putExtra("camp",campaign);
                getContext().startActivity(i);

            }
        });

        return convertView;
    }


    //this is to strech photo to a screen size and calculate a new ratio
    public double [] calculateNewDimentions(double picWidth, double picHeight){
        double displayWidth = DeviceDimensionsHelper.getDisplayWidth(getContext());
        double aspectRatio =picWidth/picHeight;
        double newHeight = displayWidth/aspectRatio;
        return new double[] {displayWidth, newHeight};
    }


}
