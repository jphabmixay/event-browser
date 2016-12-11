package csc413team18.finalproject.request;


import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

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
        return null;
    }

    @Override
    protected void deliverResponse(List<Meetup> response) {

        //listener.onResponse();
    }
}
