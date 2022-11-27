package com.example.batech_1.Dashboards.ClientDashboards.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.batech_1.R;

public class Fragment_Home extends Fragment {

    public Fragment_Home() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btn_screen_print, btn_pad_print, btn_contact_us, btn_print, btn_laser_print;

        btn_screen_print = view.findViewById(R.id.btn_screen_print);
        btn_pad_print = view.findViewById(R.id.btn_pad_print);
        btn_contact_us = view.findViewById(R.id.btn_contact_us);
        btn_print = view.findViewById(R.id.btn_print);
        btn_laser_print = view.findViewById(R.id.btn_laser_print);

        btn_screen_print.setOnClickListener(v-> homeBrowser());
        btn_pad_print.setOnClickListener(v-> homeBrowser());

        btn_contact_us.setOnClickListener(v->{
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://www.batechindia.com/contact-us/"));
            requireActivity().startActivity(i);
        });

        btn_print.setOnClickListener(v-> homeBrowser());

        btn_laser_print.setOnClickListener(v-> homeBrowser());

    }

    private void homeBrowser() {

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("https://www.batechindia.com/"));
        requireActivity().startActivity(i);
    }
}