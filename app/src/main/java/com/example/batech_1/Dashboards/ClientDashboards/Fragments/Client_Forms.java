package com.example.batech_1.Dashboards.ClientDashboards.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.batech_1.Dashboards.AdminFragments.ClientInspectionReport;
import com.example.batech_1.Dashboards.AdminFragments.ClientInterventionReport;
import com.example.batech_1.Dashboards.ClientDashboards.Fragments.Forms.SelfInspection;
import com.example.batech_1.Dashboards.ClientDashboards.Fragments.Forms.SiteIntervention;
import com.example.batech_1.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;


public class Client_Forms extends Fragment {

    Fragment  fragment = null;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    TabLayout client_form_tab;
    FrameLayout frameLayout;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    String uid = null, user = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_client__forms, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        uid = Objects.requireNonNull(auth.getCurrentUser()).getUid();

        client_form_tab = view.findViewById(R.id.client_form_tab);
        frameLayout = view.findViewById(R.id.frameLayout);




        int position = client_form_tab.getSelectedTabPosition();
        if(position == 0){
            firestore.collection("Users").document(uid).get().addOnSuccessListener(documentSnapshot -> {
                user = documentSnapshot.getString("Admin");
                if(Objects.equals(user, "1")){
                    fragment = new SiteIntervention();
                    fragmentManager = requireActivity().getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout,fragment).commit();
                }else{
                    fragment = new ClientInterventionReport();
                    fragmentManager = requireActivity().getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout,fragment).commit();
                }
            }).addOnFailureListener(e -> Toast.makeText(requireActivity(), "Cannot Find User, Something Went Wrong", Toast.LENGTH_SHORT).show());

        }else if(position == 1){
            firestore.collection("Users").document(uid).get().addOnSuccessListener(documentSnapshot -> {
                user = documentSnapshot.getString("Admin");
                if(Objects.equals(user, "1")){
                    fragment = new SelfInspection();
                    fragmentManager = requireActivity().getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout,fragment).commit();
                }else{
                    fragment = new ClientInspectionReport();
                    fragmentManager = requireActivity().getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout,fragment).commit();
                }
            }).addOnFailureListener(e -> Toast.makeText(requireActivity(), "Cannot Find User, Something Went Wrong", Toast.LENGTH_SHORT).show());
        }


        client_form_tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()){
                    case 0:
                        firestore.collection("Users").document(uid).get().addOnSuccessListener(documentSnapshot -> {
                            user = documentSnapshot.getString("Admin");
                            if(Objects.equals(user, "1")){
                                fragment = new SiteIntervention();
                                fragmentManager = requireActivity().getSupportFragmentManager();
                                fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frameLayout,fragment).commit();
                            }else{
                                fragment = new ClientInterventionReport();
                                fragmentManager = requireActivity().getSupportFragmentManager();
                                fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frameLayout,fragment).commit();
                            }
                        }).addOnFailureListener(e -> Toast.makeText(requireActivity(), "Cannot Find User, Something Went Wrong", Toast.LENGTH_SHORT).show());
                        break;
                    case 1:
                        firestore.collection("Users").document(uid).get().addOnSuccessListener(documentSnapshot -> {
                            user = documentSnapshot.getString("Admin");
                            if(Objects.equals(user, "1")){
                                fragment = new SelfInspection();
                                fragmentManager = requireActivity().getSupportFragmentManager();
                                fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frameLayout,fragment).commit();
                            }else{
                                fragment = new ClientInspectionReport();
                                fragmentManager = requireActivity().getSupportFragmentManager();
                                fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frameLayout,fragment).commit();
                            }
                        }).addOnFailureListener(e -> Toast.makeText(requireActivity(), "Cannot Find User, Something Went Wrong", Toast.LENGTH_SHORT).show());
                        break;
                }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                switch(tab.getPosition()){
                    case 0:
                        firestore.collection("Users").document(uid).get().addOnSuccessListener(documentSnapshot -> {
                            user = documentSnapshot.getString("Admin");
                            if(Objects.equals(user, "1")){
                                fragment = new SiteIntervention();
                                fragmentManager = requireActivity().getSupportFragmentManager();
                                fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frameLayout,fragment).commit();
                            }else{
                                fragment = new ClientInterventionReport();
                                fragmentManager = requireActivity().getSupportFragmentManager();
                                fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frameLayout,fragment).commit();
                            }
                        }).addOnFailureListener(e -> Toast.makeText(requireActivity(), "Cannot Find User, Something Went Wrong", Toast.LENGTH_SHORT).show());
                        break;
                    case 1:
                        firestore.collection("Users").document(uid).get().addOnSuccessListener(documentSnapshot -> {
                            user = documentSnapshot.getString("Admin");
                            if(Objects.equals(user, "1")){
                                fragment = new SelfInspection();
                                fragmentManager = requireActivity().getSupportFragmentManager();
                                fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frameLayout,fragment).commit();
                            }else{
                                fragment = new ClientInspectionReport();
                                fragmentManager = requireActivity().getSupportFragmentManager();
                                fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frameLayout,fragment).commit();
                            }
                        }).addOnFailureListener(e -> Toast.makeText(requireActivity(), "Cannot Find User, Something Went Wrong", Toast.LENGTH_SHORT).show());
                        break;
                }

            }
        });
    }
}