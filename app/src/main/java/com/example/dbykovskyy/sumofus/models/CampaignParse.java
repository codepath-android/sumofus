package com.example.dbykovskyy.sumofus.models;

import com.parse.GetCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;


@ParseClassName("CampaignParse")
public class CampaignParse extends ParseObject {
    public String getTitle() {
        String text = getString("title");
        return str(text);
    }

    public String getCampaignUrl() {
        String text = getString("url");
        return strUrl(text);
    }


    public String getOverview() {
        String text = getString("overview");
        return str(text);
    }

    public String getDescription() {
        String text = getString("description");
        return str(text);
    }

    public String getSignMessage() {
        String text = getString("signMessage");
        return str(text);
    }

    public int getGoal() {
        int goal = getInt("goalAmount");
        return goal;
    }

    public int getCurrentSignatureCount() {
        int currentSignatureCount = getInt("currentSignatureCount");
        return currentSignatureCount;
    }

    public List<CampaignMedia> getMedia() {
        return getList("media");
    }

    public static void findInBackground(int order,
                                        final GetCallback<User> callback) {
        ParseQuery<User> roomQuery = ParseQuery.getQuery(User.class);
        roomQuery.whereEqualTo("order", order);
        roomQuery.getFirstInBackground(new GetCallback<User>() {
            @Override
            public void done(User user, ParseException e) {
                if (e == null) {
                    User status = new User();
                    callback.done(status, null);
                } else {
                    callback.done(null, e);
                }
            }
        });
    }

    // Method to check if string is empty, return '';
    private String str(String text) {
        if (text == null) {
            text = "";
        }
        return text;
    }

    // Method to check if string is empty, return '';
    private String strUrl(String text) {
        if (text == null) {
            text = "";
        }
        return text;
    }

}
