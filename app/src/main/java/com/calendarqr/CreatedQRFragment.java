package com.calendarqr;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.navigation.fragment.NavHostFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.google.android.material.snackbar.Snackbar;

public class CreatedQRFragment extends Fragment {
    Bitmap QRBitmap = null;
    Boolean saveQR = true;
    Button saveQrCode;
    Button createAnother;
    Button exit;

    // Getter/Setter for image bitmap and toggle saveQR button
    public void setBitmap(Bitmap bmp) {
        this.QRBitmap = bmp;
    }

    public Bitmap getBitmap() {
        return this.QRBitmap;
    }

    public Boolean toggleSaveQRButton() {
        this.saveQR = !this.saveQR;
        return this.saveQR;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_created_qr, container, false);
        saveQrCode = (Button) rootView.findViewById(R.id.save_qr_code);
        saveQrCode.setEnabled(toggleSaveQRButton());

        createAnother = (Button) rootView.findViewById(R.id.createAnotherEvent);
        createAnother.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Add action from CreatedQRFragment to GenerateFragment
                NavHostFragment.findNavController(CreatedQRFragment.this).navigate(R.id.action_CreatedQRFragment_to_GenerateFragment);
            }
        });

        exit = (Button) rootView.findViewById(R.id.exitButton);
        exit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Add action from CreatedQRFragment to HomeFragment
                NavHostFragment.findNavController(CreatedQRFragment.this).navigate(R.id.action_CreatedQRFragment_to_HomeFragment);
            }
        });

        return rootView;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TODO get data from new event upon successful event creation (including QR code and text code)

        // TEMPORARY get event data from bundle
        String eventTitle = getArguments().getString("eventTitle");
        String eventDate = getArguments().getString("eventDate");
        String eventLocation = getArguments().getString("eventLocation");
        String eventDescription = getArguments().getString("eventDescription");
        String qrUrl = getArguments().getString("qrURL");
        ((TextView)view.findViewById(R.id.eventTitle)).setText(eventTitle);
        ((TextView)view.findViewById(R.id.editEventDate)).setText(eventDate);
        ((TextView)view.findViewById(R.id.eventLocation)).setText(eventLocation);
        ((TextView)view.findViewById(R.id.eventDescription)).setText(eventDescription);

        ImageView qrView = (ImageView) view.findViewById(R.id.qrCode);
        qrView.setVisibility(View.GONE);
        LoadQR loadqr = new LoadQR(qrView);
        loadqr.execute(qrUrl);
    }

    private class LoadQR extends AsyncTask<String, Void, Bitmap> {
        ImageView qrView;

        public LoadQR(ImageView qrView) {
            this.qrView = qrView;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String urlLink = strings[0];
            Bitmap bitmap = null;
            try {
                InputStream is = new URL(urlLink).openStream();
                bitmap = BitmapFactory.decodeStream(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            qrView.setVisibility(View.VISIBLE);
            getActivity().findViewById(R.id.loadingSpinner).setVisibility(View.GONE);
            qrView.setImageBitmap(bitmap);

            // set the qr bitmap, enable save button and allow saving the bitmap as image
            saveQrCode.setEnabled(toggleSaveQRButton());
            setBitmap(bitmap);
            saveQrCode.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // getActivity(), returns the activity associated with a fragment.
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    SaveImageToLocal sv = new SaveImageToLocal();
                    if (sv.saveImageToGallery(getActivity(), getBitmap())) {
                        // notify user to say image is saved
                        Snackbar.make(v, "Image Saved", Snackbar.LENGTH_SHORT).show();
                        saveQrCode.setEnabled(toggleSaveQRButton());
                    } else {
                        // notify user to say image is not saved
                        Snackbar.make(v, "Image not saved", Snackbar.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
