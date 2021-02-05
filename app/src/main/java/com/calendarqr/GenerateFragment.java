package com.calendarqr;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.Calendar;


public class GenerateFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_generate, container, false);
    }

    public void onViewCreated(@NonNull View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final View thisView = view;
        view.findViewById(R.id.event_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(GenerateFragment.this)
                        .navigate(R.id.action_GenerateFragment_to_HomeFragment);
            }
        });
        ((EventForm)getActivity()).resetEventDateTime();

        Button eventDatePicker = (Button)view.findViewById(R.id.eventDatePicker);
        eventDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getActivity().getSupportFragmentManager(), "date picker");
            }
        });

        Button eventTimePicker = (Button)view.findViewById(R.id.eventTimePicker);
        eventTimePicker.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getActivity().getSupportFragmentManager(), "time picker");
            }
        });
        view.findViewById(R.id.event_generate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validFormInput(thisView)) {

                    String eventTitle = ((EditText)thisView.findViewById(R.id.eventDetailsName)).getText().toString();
                    String eventLocation = ((EditText)thisView.findViewById(R.id.eventDetailsLocation)).getText().toString();
                    String eventDate = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.SHORT).format(((EventForm)getActivity()).getEventTimestamp());
                    String eventDescription = ((EditText)thisView.findViewById(R.id.eventDetailsDescription)).getText().toString();

                    // TEMPORARY passing data by bundle
                    Bundle bundle = new Bundle();
                    bundle.putString("eventTitle", eventTitle);
                    bundle.putString("eventLocation", eventLocation);
                    bundle.putString("eventDate", eventDate);
                    bundle.putString("eventDescription", eventDescription);

                    JSONObject json = new JSONObject();
                    try {
                        json.put("title", eventTitle);
                        json.put("location", eventLocation);
                        json.put("datetime", ((EventForm)getActivity()).getEventTimestamp());
                        json.put("description", eventDescription);
                        String urlString = "https://api.qrserver.com/v1/create-qr-code/?size=150x150&data="
                                + URLEncoder.encode(json.toString(), "UTF-8");
                        json.put("qr_url", urlString);
                        bundle.putString("qrURL", urlString);
                        // TODO send event data to backend
                        // QR can be used from http://goqr.me/api/

                    } catch (JSONException | UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    NavHostFragment.findNavController(GenerateFragment.this)
                            .navigate(R.id.action_GenerateFragment_to_CreatedQRFragment, bundle);
                    ////
                }

            }
        });
    }

    /**
     * Checks to see if all entries in the form are valid
     */
    private boolean validFormInput(View view) {
        boolean retval = true;

        EditText eventTitle = (EditText)view.findViewById(R.id.eventDetailsName);
        EditText eventLocation = (EditText)view.findViewById(R.id.eventDetailsLocation);
        long eventDate = ((EventForm)getActivity()).getEventTimestamp();
        // Event description is optional

        // No given event title
        if (eventTitle.getText().toString().equals("")) {
            TextView label = (TextView)view.findViewById(R.id.labelEventName);
            label.setTextColor(Color.RED);
            label.setText(getString(R.string.event_name_required));
            retval = false;
        }

        // No given event location
        if (eventLocation.getText().toString().equals("")) {
            TextView label = (TextView)view.findViewById(R.id.labelLocation);
            label.setTextColor(Color.RED);
            label.setText(getString(R.string.event_location_required));
            retval = false;
        }

        // Date is invalid (past current time)
        if (eventDate <= Calendar.getInstance().getTimeInMillis()) {
            TextView label = (TextView)view.findViewById(R.id.labelDate);
            label.setTextColor(Color.RED);
            label.setText(getString(R.string.event_date_invalid));
            ((TextView)view.findViewById(R.id.labelDate)).setTextColor(Color.RED);
            retval = false;
        }

        return retval;
    }

}
