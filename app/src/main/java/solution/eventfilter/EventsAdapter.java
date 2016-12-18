package solution.eventfilter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventViewHolder> implements View.OnClickListener {

    private List<Event> mStories;

    public EventsAdapter(List<Event> stories) {
        mStories = stories;
    }

    @Override
    public int getItemCount() {
        if (mStories == null) return 0;
        return mStories.size();
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        return new EventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        Event event = mStories.get(position);
        holder.itemView.setTag(event);
        holder.itemView.setOnClickListener(this);
        holder.image.setDefaultImageResId(R.mipmap.ic_launcher);
        if (event.getImageUrl() != null) {
            holder.image.setImageUrl(event.getImageUrl(), MeetupClient.getInstance(null).getImageLoader());
        }
        holder.title.setText(event.getName());
        holder.time.setText(event.getTime());
        holder.count.setText("Attendance: " + event.getYesRsvpCount());
    }

    public void changeData(List<Event> stories) {
        mStories = stories;
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        Event event = (Event) view.getTag();
        Intent intent = new Intent(view.getContext(), DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_KEY_NAME, event.getName());
        intent.putExtra(DetailActivity.EXTRA_KEY_DESCRIPTION, event.getDescription());
        intent.putExtra(DetailActivity.EXTRA_KEY_LINK, event.getLink());
        intent.putExtra(DetailActivity.EXTRA_KEY_YES_COUNT, event.getYesRsvpCount());
        intent.putExtra(DetailActivity.EXTRA_KEY_ID, event.getId());
        intent.putExtra(DetailActivity.EXTRA_KEY_TIME, event.getTime());
        intent.putExtra(DetailActivity.EXTRA_KEY_IMAGE_URL, event.getImageUrl());
        view.getContext().startActivity(intent);
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView time;
        public TextView count;
        public NetworkImageView image;

        public EventViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.card_title);
            time = (TextView) view.findViewById(R.id.card_time);
            count = (TextView) view.findViewById(R.id.card_count);
            image = (NetworkImageView) view.findViewById(R.id.card_image);
        }
    }
}
