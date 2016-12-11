package csc413team18.finalproject.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import csc413team18.finalproject.model.Meetup;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Meetup> mMeetupList;


    public RecyclerViewAdapter(List<Meetup> meetupList) {
        mMeetupList = meetupList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
