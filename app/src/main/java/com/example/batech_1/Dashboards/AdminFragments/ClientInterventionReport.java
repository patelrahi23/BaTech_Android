package com.example.batech_1.Dashboards.AdminFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.batech_1.ModelClasses.UserClass;
import com.example.batech_1.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class ClientInterventionReport extends Fragment {

    FirebaseFirestore firestore;
    FirebaseAuth auth;
    String uid = null;
    UserClass user_class;
    TextInputLayout et_form_fname, et_form_email, et_form_address, et_form_phone, et_form_product_name, et_form_product_model,
            et_form_specs, et_form_report, et_form_action;
    TextView tv_complaint_no, tv_complaint_date;
    TextView tv_date_of_visit;
    CheckBox cb_warranty_yes, cb_warranty_no, cb_machine_complain, cb_machine_installation, cb_print_problem;
    RatingBar rb_forms_ratingBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        user_class = new UserClass();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_client_intervention_report, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mappingIDs(view);

        uid = Objects.requireNonNull(auth.getCurrentUser()).getUid();

        firestore.collection("Users").document(uid).get().addOnSuccessListener(documentSnapshot -> {
            String user_name = documentSnapshot.getString("FullName");
            getData(user_name);
        }).addOnFailureListener(e -> Toast.makeText(requireActivity(), "Cannot Fetch Data, Something Went Wrong", Toast.LENGTH_SHORT).show());



    }

    private void setData() {
        Objects.requireNonNull(et_form_fname.getEditText()).setText(user_class.getFname());
        Objects.requireNonNull(et_form_email.getEditText()).setText(user_class.getEmail());
        Objects.requireNonNull(et_form_action.getEditText()).setText(user_class.getForm_action());
        Objects.requireNonNull(et_form_address.getEditText()).setText(user_class.getAddress());
        Objects.requireNonNull(et_form_report.getEditText()).setText(user_class.getProduct_report());
        Objects.requireNonNull(et_form_phone.getEditText()).setText(user_class.getPh_number());
        Objects.requireNonNull(et_form_product_model.getEditText()).setText(user_class.getProduct_Model());
        Objects.requireNonNull(et_form_product_name.getEditText()).setText(user_class.getProduct_name());
        Objects.requireNonNull(et_form_specs.getEditText()).setText(user_class.getProduct_specs());
        Objects.requireNonNull(et_form_product_model.getEditText()).setText(user_class.getProduct_Model());
        Objects.requireNonNull(tv_complaint_no).setText(user_class.getComplain_number());
        Objects.requireNonNull(tv_complaint_date).setText(user_class.getComplain_date());
        Objects.requireNonNull(tv_date_of_visit).setText(user_class.getDate_visit());

        set_MachineComplains();
        setwarranty();
    }

    private void set_MachineComplains() {
        if(user_class.getMachine_complain().equals("Machine Complaint")){
            cb_machine_complain.setChecked(true);
        }else if(user_class.getMachine_complain().equals("Machine Installation")){
            cb_machine_installation.setChecked(true);
        }else if(user_class.getMachine_complain().equals("Print Distortion")){
            cb_print_problem.setChecked(true);
        }
    }

    private void setwarranty() {
        if(user_class.getWarranty_status().equals("yes")){
            cb_warranty_yes.setChecked(true);
        }
        else{
            cb_warranty_no.setChecked(true);
        }
    }


    private void getData(String user_name) {


       try {
           firestore.collection("Site Intervention Report").document(user_name).get().addOnSuccessListener(documentSnapshot -> {
               user_class.setRating((Double) documentSnapshot.get("Rating"));
               user_class.setComplain_number(Objects.requireNonNull(documentSnapshot.getString("Complaint Number")));
               user_class.setComplain_date(Objects.requireNonNull(documentSnapshot.getString("Complaint Filed")));
               user_class.setDate_visit(Objects.requireNonNull(documentSnapshot.getString("Date of Visit")));
               user_class.setWarranty_status(documentSnapshot.getString("Warranty Status"));
               user_class.setMachine_complain(documentSnapshot.getString("Nature For Problem"));
               user_class.setForm_action(documentSnapshot.getString("Action Required"));
               user_class.setProduct_specs(documentSnapshot.getString("Product Specs"));
               user_class.setProduct_Model(documentSnapshot.getString("Product Model"));
               user_class.setAddress(documentSnapshot.getString("Address"));
               user_class.setPh_number(documentSnapshot.getString("Phone"));
               user_class.setProduct_name(documentSnapshot.getString("Product Name"));
               user_class.setEmail(documentSnapshot.getString("Email"));
               user_class.setProduct_report(documentSnapshot.getString("Product Report"));
               user_class.setFname(documentSnapshot.getString("FullName"));
               setData();
           }).addOnFailureListener(e-> Toast.makeText(requireActivity(), "Cannot Fetch Data, Something Went Wrong.\n"+e.getMessage(), Toast.LENGTH_SHORT).show());
       }catch (Exception ex){
           Toast.makeText(requireContext(), "Exception:\n"+ex.getMessage(), Toast.LENGTH_SHORT).show();
           Objects.requireNonNull(et_form_fname.getEditText()).setText("N/A");
           Objects.requireNonNull(et_form_email.getEditText()).setText("N/A");
           Objects.requireNonNull(et_form_action.getEditText()).setText("N/A");
           Objects.requireNonNull(et_form_address.getEditText()).setText("N/A");
           Objects.requireNonNull(et_form_report.getEditText()).setText("N/A");
           Objects.requireNonNull(et_form_phone.getEditText()).setText("N/A");
           Objects.requireNonNull(et_form_product_model.getEditText()).setText("N/A");
           Objects.requireNonNull(et_form_product_name.getEditText()).setText("N/A");
           Objects.requireNonNull(et_form_specs.getEditText()).setText("N/A");
           Objects.requireNonNull(et_form_product_model.getEditText()).setText("N/A");
           Objects.requireNonNull(tv_complaint_no).setText("N/A");
           Objects.requireNonNull(tv_complaint_date).setText("N/A");
           Objects.requireNonNull(tv_date_of_visit).setText("N/A");
       }
    }

    private void mappingIDs(View view) {
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
    }

}