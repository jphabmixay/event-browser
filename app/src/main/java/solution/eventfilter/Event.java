package solution.eventfilter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Event {

    private String mId;
    private String mName;
    private String mDescription;
    private String mLink;
    private String mImageUrl;
    private int mYesRsvpCount;
    private String mTime;

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(long time) {
        Date date = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a, MMM/d/yyyy", Locale.US);
        mTime = dateFormat.format(date);
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        mLink = link;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getYesRsvpCount() {
        return String.valueOf(mYesRsvpCount);
    }

    public void setYesRsvpCount(int yesRsvpCount) {
        mYesRsvpCount = yesRsvpCount;
    }
}
