package csc413team18.finalproject.request;


import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import csc413team18.finalproject.model.Meetup;

public class JsonRequest extends Request<List<Meetup>> {
    //listener for JsonController that processes a request
    private Response.Listener<List<Meetup>> listener;

    public JsonRequest( int method,
                        String url,
                        Response.Listener<List<Meetup>> successListener,
                        Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = listener;
    }


    @Override
    protected Response<List<Meetup>> parseNetworkResponse(NetworkResponse response) {
        String jsonString = new String(response.data);
        List<Meetup> meetupList;
        JSONObject jsonObject;

        /*
            Json equivalent to if/else statements
            Attempt to convert received jsonString to a meetupList
         */
        try {
            jsonObject = new JSONObject(jsonString);
            meetupList = Meetup.parseJson(jsonObject);
        }
        catch (JSONException e) {
            e.printStackTrace();
            return Response.error(new VolleyError("Error"));
        }

        return Response.success(meetupList, getCacheEntry());
    }

    @Override
    protected void deliverResponse(List<Meetup> response) {
        listener.onResponse(response);
    }
}
