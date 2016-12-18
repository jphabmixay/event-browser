package solution.eventfilter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_KEY_NAME = "name";
    public static final String EXTRA_KEY_DESCRIPTION = "description";
    public static final String EXTRA_KEY_IMAGE_URL = "image_url";
    public static final String EXTRA_KEY_YES_COUNT = "yes_count";
    public static final String EXTRA_KEY_TIME = "time";
    public static final String EXTRA_KEY_ID = "id";
    public static final String EXTRA_KEY_LINK = "link";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();

        String title = intent.getStringExtra(EXTRA_KEY_NAME);
        TextView titleTextView = (TextView) findViewById(R.id.detail_title);
        titleTextView.setText(title);

        String description = intent.getStringExtra(EXTRA_KEY_DESCRIPTION);
        TextView descriptionTextView = (TextView) findViewById(R.id.detail_description);
        descriptionTextView.setText(Html.fromHtml(description.replaceAll("<img.+/(img)*>", "")));

        String time = intent.getStringExtra(EXTRA_KEY_TIME);
        TextView timeTextView = (TextView) findViewById(R.id.detail_time);
        timeTextView.setText(time);

        String yesCount = intent.getStringExtra(EXTRA_KEY_YES_COUNT);
        TextView yesCountTextView = (TextView) findViewById(R.id.detail_yes_count);
        yesCountTextView.setText("Attendance: " + yesCount);

        String id = intent.getStringExtra(EXTRA_KEY_ID);
        TextView idTextView = (TextView) findViewById(R.id.detail_id);
        idTextView.setText("Meetup ID: " + id);

        String link = intent.getStringExtra(EXTRA_KEY_LINK);
        TextView linkTextView = (TextView) findViewById(R.id.detail_link);
        linkTextView.setText(link);

        if (intent.getStringExtra(EXTRA_KEY_IMAGE_URL) != null) {
            NetworkImageView imageView = (NetworkImageView) findViewById(R.id.detail_image);
            String groupId = intent.getStringExtra(EXTRA_KEY_IMAGE_URL);
            imageView.setImageUrl(groupId, MeetupClient.getInstance(this).getImageLoader());
        }
    }
}
