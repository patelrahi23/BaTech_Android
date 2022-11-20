package com.example.batech_1.Dashboards.ClientDashboards.Fragments.Forms;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.batech_1.R;
import com.google.android.material.textfield.TextInputLayout;


public class SiteIntervention extends Fragment {
        TextInputLayout et_form_fname, et_form_email, et_form_address, et_form_phone, et_form_product_name, et_form_product_model,
                et_form_specs, et_form_report, et_form_action;
        TextView tv_complaint_no, tv_complaint_date, tv_date_of_visit;
        CheckBox cb_warranty_yes, cb_warranty_no, cb_machine_complain, cb_machine_installation, cb_print_problem;
        RatingBar rb_forms_ratingBar;
        Button btn_submit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_site_intervention, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        et_form_fname = view.findViewById(R.id.et_form_fname);
        et_form_email = view.findViewById(R.id.et_form_email);
        et_form_address = view.findViewById(R.id.et_form_address);
        et_form_phone = view.findViewById(R.id.et_form_phone);
        et_form_product_name = view.findViewById(R.id.et_form_product_name);
        et_form_product_model = view.findViewById(R.id.et_form_product_model);
        et_form_specs = view.findViewById(R.id.et_form_specs);
        et_form_report = view.findViewById(R.id.et_form_report);
        et_form_action = view.findViewById(R.id.et_form_action);
        tv_complaint_no = view.findViewById(R.id.tv_complaint_no);
        tv_complaint_date = view.findViewById(R.id.tv_complaint_date);
        tv_date_of_visit = view.findViewById(R.id.tv_date_of_visit);
        cb_warranty_yes = view.findViewById(R.id.cb_warranty_yes);
        cb_warranty_no = view.findViewById(R.id.cb_warranty_no);
        cb_machine_complain = view.findViewById(R.id.cb_machine_complain);
        cb_machine_installation = view.findViewById(R.id.cb_machine_installation);
        cb_print_problem = view.findViewById(R.id.cb_print_problem);
        rb_forms_ratingBar = view.findViewById(R.id.rb_forms_ratingBar);
        btn_submit = view.findViewById(R.id.btn_submit);
    }
}