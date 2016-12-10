package csc413team18.finalproject.model;

/**
 * Model class for Meetup
 */

public class Meetup {

    private String mMeetupId;     // obtained via Meetup API
    private String mName;
    private String mAttendies;    // number of attendies
    private String mdescription;
    private String mLink;         // url for meetup


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