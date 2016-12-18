//CSC 413 FALL 2016
//TEAM 18

package solution.eventfilter;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks {

    private static final int PERMISSION_REQUEST_FINE_LOCATION = 1;

    private MeetupClient mMeetupClient;
    private EventsAdapter mAdapter;
    private GoogleApiClient mGoogleApiClient; //Google Play Location Services used instead of low-level location manager to get location.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMeetupClient = MeetupClient.getInstance(getApplicationContext());
        mAdapter = new EventsAdapter(null);
        initUi();
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this) //developers.google.com/android/reference/com/google/android/gms/common/api/GoogleApiClient
                    .addConnectionCallbacks(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        mMeetupClient.getRequestQueue().cancelAll(getApplicationContext());
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (hasPermission()) {
            updateLocation();
        } else {
            requestPermission();
        }
    }

    @Override
    public void onConnectionSuspended(int cause) {
        switch (cause) {
            case CAUSE_NETWORK_LOST:
                Toast.makeText(this, "Unable to connect to location services", Toast.LENGTH_SHORT).show();
                break;
            case CAUSE_SERVICE_DISCONNECTED:
                Toast.makeText(this, "Location services unavailable", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_FINE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    updateLocation();
                }
                break;
        }
    }

    @SuppressWarnings("MissingPermission")
    private void updateLocation() {
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        mMeetupClient.setLocation(lastLocation);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_FINE_LOCATION);
    }

    private boolean hasPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    //RecyclerView UI
    private void initUi() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(mAdapter);

        SearchView searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mMeetupClient.fetchEvents(query, mAdapter);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                mMeetupClient.fetchEvents(query, mAdapter);
                return true;
            }
        });
    }
}
