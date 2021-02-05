package com.calendarqr;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class fragment_event_details_ extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_event_details_, container, false);
        Bundle appData = getActivity().getIntent().getExtras();
        String data = appData.getString("RESULTS");
        System.out.println(data);
        return rootView;
    }
//
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        if(getArguments() != null){
//            String title = getArguments().getString("title");
//            String date = getArguments().getString("date");
//            String location = getArguments().getString("location");
//            String description = getArguments().getString("description");
//
//            System.out.println(title);
//            TextView eventDtitle = null;
//            eventDtitle.findViewById(R.id.eventDetailsName).setText(title);
//
//            ((TextView)view.findViewById(R.id.eventDetailsName)).setText(title);
//            ((TextView)view.findViewById(R.id.eventDetailsLocation)).setText(date);
//            ((TextView)view.findViewById(R.id.eventDetailsDescription)).setText(location);
//            ((TextView)view.findViewById(R.id.eventDetailsDate)).setText(description);
//        }
//        return inflater.inflate(R.layout.fragment_event_details_, container, false);
//    }

//    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        Intent i = getIntent();
//        startActivity(i);
//
//        // parse string
//        try {
//            final JSONObject obj = new JSONObject(this.textView);
//
//            ((TextView)view.findViewById(R.id.eventDetailsName)).setText(itle);
//            ((TextView)view.findViewById(R.id.eventDetailsLocation)).setText(eventDate);
//            ((TextView)view.findViewById(R.id.eventDetailsDescription)).setText(eventLocation);
//            ((TextView)view.findViewById(R.id.eventDetailsDate)).setText(eventDescription);
////
////            // we have textView Now update the event details page
////            String eventTitle = obj.getString("title");
////            Integer eventDate = obj.getInt("datetime");
////            String eventLocation = obj.getString("location");
////            String eventDescription = obj.getString("description");
////            ((TextView)view.findViewById(R.id.eventDetailsName)).setText(eventTitle);
////            ((TextView)view.findViewById(R.id.eventDetailsLocation)).setText(eventDate);
////            ((TextView)view.findViewById(R.id.eventDetailsDescription)).setText(eventLocation);
////            ((TextView)view.findViewById(R.id.eventDetailsDate)).setText(eventDescription);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
}