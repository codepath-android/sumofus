package com.example.dbykovskyy.sumofus.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dbykovskyy.sumofus.R;
import com.example.dbykovskyy.sumofus.activity.NewCampaignActivity;
import com.example.dbykovskyy.sumofus.models.CampaignParse;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class NewCampaignFragment extends Fragment {
    private ImageButton photoButton;
    private Button saveButton;
    private Button cancelButton;
    private TextView campaignName;
    private Spinner campaignCategory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle SavedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_campaign, parent, false);

        campaignName = ((EditText) v.findViewById(R.id.campaign_name));

        // The campaignRating spinner lets people assign favorites of meals they've
        // eaten.
        // Meals with 4 or 5 ratings will appear in the Favorites view.
        campaignCategory = ((Spinner) v.findViewById(R.id.categories_spinner));
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter
                .createFromResource(getActivity(), R.array.category_array,
                        android.R.layout.simple_spinner_dropdown_item);
        campaignCategory.setAdapter(spinnerAdapter);

        photoButton = ((ImageButton) v.findViewById(R.id.photo_button));
        photoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(campaignName.getWindowToken(), 0);

            }
        });

        saveButton = ((Button) v.findViewById(R.id.save_button));
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                CampaignParse campaign = ((NewCampaignActivity) getActivity()).getCurrentCampaign();

                // When the user clicks "Save," upload the campaign to Parse
                // Add data to the campaign object:
                campaign.setTitle(campaignName.getText().toString());

                // Associate the campaign with the current user
              //  campaign.setAuthor(ParseUser.getCurrentUser());

                // Add the rating
                campaign.setSignMessage(campaignCategory.getSelectedItem().toString());

                // If the user added a photo, that data will be
                // added in the CameraFragment

                // Save the campaign and return
                campaign.saveInBackground(new SaveCallback() {

                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            getActivity().setResult(Activity.RESULT_OK);
                            getActivity().finish();
                        } else {
                            Toast.makeText(
                                    getActivity().getApplicationContext(),
                                    "Error saving: " + e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                });

            }
        });

        cancelButton = ((Button) v.findViewById(R.id.cancel_button));
        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        });


        return v;
    }


}
