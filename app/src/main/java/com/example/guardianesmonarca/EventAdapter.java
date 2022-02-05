package com.example.guardianesmonarca;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    TextView eventName, eventDate,eventPlace,eventDescription,eventLink;
    Context mCtx;
    List<Event> eventList;

    static final String TAG = HomeActivity.class.getName();
    public EventAdapter (Context mCtx, List<Event> eventList){
        this.mCtx=mCtx;
        this.eventList=eventList;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EventViewHolder(
                LayoutInflater.from(mCtx).inflate(R.layout.events_fragment,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = eventList.get(position);

        holder.eventName.setText(event.getName());
        holder.eventDate.setText(event.getDate());
        holder.eventPlace.setText("Lugar: "+event.getPlace());
        holder.eventDescription.setText(event.getDescription());
        holder.eventLink.setText("Link: "+event.getLink());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    class EventViewHolder extends RecyclerView.ViewHolder {

        TextView eventName, eventDate,eventPlace,eventDescription,eventLink;


        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.tittle_event);
            eventDate = itemView.findViewById(R.id.date);
            eventPlace = itemView.findViewById(R.id.place_event);
            eventDescription = itemView.findViewById(R.id.description);
            eventLink = itemView.findViewById(R.id.link);

        }
    }
}
