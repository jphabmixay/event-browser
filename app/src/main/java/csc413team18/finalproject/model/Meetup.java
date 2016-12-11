package csc413team18.finalproject.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Model class for Meetup
 */

public class Meetup {

    private String mMeetupId;     // obtained via Meetup API
    private String mName;
    private String mAttendies;    // number of attendies
    private String mdescription;
    private String mLink;         // url for meetup

    //Handles list of Meetups as an arraylist for searching
    public static List<Meetup> parseJson(JSONObject jsonObject) throws JSONException{

        List<Meetup> meetups = new ArrayList<>();

        // Check if the JSONObject has object with key "Search"
        if(jsonObject.has("Search")){

            // Get JSONArray from JSONObject
            JSONArray jsonArray = jsonObject.getJSONArray("Search");

            for(int i = 0; i < jsonArray.length(); i++){
                // Create new Meetup object from each JSONObject in the JSONArray
                meetups.add(new Meetup(jsonArray.getJSONObject(i)));
            }
        }

        //returns list of meetups
        return meetups;
    }

    // Json Class constructor
    private Meetup(JSONObject jsonObject) throws JSONException {
        if(jsonObject.has("mName")) this.setName(jsonObject.getString("mName"));
        if(jsonObject.has("mMeetupID")) this.setMeetupId(jsonObject.getString("mMeetupID"));
        if(jsonObject.has("mAttendies")) this.setAttendies(jsonObject.getString("mAttendies"));
        if(jsonObject.has("mdescription")) this.setMdescription(jsonObject.getString("mdescription"));
        if(jsonObject.has("mLink")) this.setLink(jsonObject.getString("mLink"));
    }


    public String getMeetupId() {
        return mMeetupId;
    }

    public void setMeetupId(String meetupId) {
        mMeetupId = meetupId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getAttendies() {
        return mAttendies;
    }

    public void setAttendies(String attendies) {
        mAttendies = attendies;
    }

    public String getMdescription() {
        return mdescription;
    }

    public void setMdescription(String mdescription) {
        this.mdescription = mdescription;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        mLink = link;
    }
}