package solution.eventfilter;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.util.LruCache;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MeetupClient {

    private static MeetupClient mInstance;

    private Context mContext;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private Location mLocation;

    private MeetupClient(Context context) {//https://developer.android.com/training/volley/request.html
        mContext = context;
        mRequestQueue = Volley.newRequestQueue(context);
        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<>(20);

            @Override
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
        });
    }

    public static synchronized MeetupClient getInstance(Context context) {
        if (mInstance == null) mInstance = new MeetupClient(context);
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    public void fetchEvents(String query, final EventsAdapter adapter) {
        if (query == null || query.length() == 0) return;
        JsonObjectRequest request = new JsonObjectRequest(buildUrl(query), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            adapter.changeData(parseResponse(response));
                        } catch (JSONException e) {
                            Toast.makeText(mContext, "Server error", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext, "Volley error", Toast.LENGTH_SHORT).show();
                    }
                });
        request.setTag(mContext);
        mRequestQueue.cancelAll(mContext);
        mRequestQueue.add(request);
    }

    public void setLocation(Location location) {
        if (location != null) {
            mLocation = location;
        }
    }

    private String buildUrl(String query) {
        Uri.Builder builder = new Uri.Builder();
        if (mLocation != null) {
            builder.appendQueryParameter("lon", String.valueOf(mLocation.getLongitude())); //request parameters
            builder.appendQueryParameter("lat", String.valueOf(mLocation.getLatitude()));
        } else {
            builder.appendQueryParameter("zip", "94035"); //Mountain View, default location
        }
        return builder.scheme("https")
                .authority("api.meetup.com")
                .appendPath("2")
                .appendPath("open_events") //https://www.meetup.com/meetup_api/docs/2/open_events/
                .appendQueryParameter("key", BuildConfig.MEETUP_API_KEY)
                .appendQueryParameter("sign", "true")
                .appendQueryParameter("fields", "group_photo,short_link") //Request that additional fields (separated by commas) be included in the output
                .appendQueryParameter("only", "id,name,short_link,description,time,yes_rsvp_count,group.group_photo.photo_link")
                .appendQueryParameter("radius", "smart") //Radius, in miles for geographic requests, default 25.0
                .appendQueryParameter("text", query)
                .build()
                .toString();
    }

    private List<Event> parseResponse(JSONObject response) throws JSONException {
        JSONArray results = response.getJSONArray("results");
        List<Event> events = new ArrayList<>(results.length());
        for (int i = 0; i < results.length(); ++i) {
            JSONObject jsonEvent = (JSONObject) results.get(i);
            Event event = new Event();

            String id = jsonEvent.getString("id");
            event.setId(id);

            String name = jsonEvent.getString("name");
            event.setName(name);

            String description = jsonEvent.getString("description");
            event.setDescription(description);

            String link = jsonEvent.getString("short_link");
            event.setLink(link);

            if (jsonEvent.has("group") && jsonEvent.getJSONObject("group").has("group_photo")) {
                String imageUrl = jsonEvent.getJSONObject("group").getJSONObject("group_photo").getString("photo_link");
                event.setImageUrl(imageUrl);
            }

            int yesRsvpCount = jsonEvent.getInt("yes_rsvp_count");
            event.setYesRsvpCount(yesRsvpCount);

            long time = jsonEvent.getLong("time");
            event.setTime(time);

            events.add(event);
        }
        return events;
    }
}
