package com.calendarqr;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.graphics.Color;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

// Main Activity
public class EventForm extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private Calendar createEventDateTime; // to be used when creating a new event.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_form);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        createEventDateTime = Calendar.getInstance();

    }

    /**
     * To be used mainly when creating an event and setting its date
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        createEventDateTime.set(Calendar.YEAR, year);
        createEventDateTime.set(Calendar.MONTH, month);
        createEventDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String dateString = DateFormat.getDateInstance().format(createEventDateTime.getTime());
        Button eventDatePicker = (Button)findViewById(R.id.eventDatePicker);
        eventDatePicker.setText(dateString);
    }

    /**
     * To be used mainly when creating an event and setting its time
     */
    @Override
    public void onTimeSet(TimePicker view, int hour, int minute) {
        createEventDateTime.set(Calendar.HOUR_OF_DAY, hour);
        createEventDateTime.set(Calendar.MINUTE, minute);
        String timeString = DateFormat.getTimeInstance(DateFormat.SHORT).format(createEventDateTime.getTime());
        Button eventTimePicker = (Button)findViewById(R.id.eventTimePicker);
        eventTimePicker.setBackgroundColor(Color.RED);
        eventTimePicker.setText(timeString);
    }

    public long getEventTimestamp() {
        return createEventDateTime.getTimeInMillis();
    }

    public void resetEventDateTime() {
        createEventDateTime = Calendar.getInstance();
        createEventDateTime.set(Calendar.MILLISECOND, 0);
        createEventDateTime.set(Calendar.SECOND, 0);
        createEventDateTime.add(Calendar.HOUR_OF_DAY, 1);
        String timeString = DateFormat.getTimeInstance(DateFormat.SHORT).format(createEventDateTime.getTime());
        Button eventTimePicker = (Button)findViewById(R.id.eventTimePicker);
        eventTimePicker.setText(timeString);
        eventTimePicker.setBackgroundColor(Color.RED);
        String dateString = DateFormat.getDateInstance().format(createEventDateTime.getTime());
        Button eventDatePicker = (Button)findViewById(R.id.eventDatePicker);
        eventDatePicker.setBackgroundColor(Color.RED);
        eventDatePicker.setText(dateString);
    }
}
