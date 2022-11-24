package com.example.batech_1.Dashboards.ClientDashboards.Fragments.Forms;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.batech_1.ModelClasses.UserClass;
import com.example.batech_1.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.TimeZone;


public class SiteIntervention extends Fragment {

    boolean valid = true;
    TextInputLayout et_form_fname, et_form_email, et_form_address, et_form_phone, et_form_product_name, et_form_product_model,
            et_form_specs, et_form_report, et_form_action;
    TextView tv_complaint_no, tv_complaint_date;
    TextView tv_date_of_visit;
    CheckBox cb_warranty_yes, cb_warranty_no, cb_machine_complain, cb_machine_installation, cb_print_problem;
    RatingBar rb_forms_ratingBar;
    String warranty, fname, email, phone, address, product_name, product_model, product_specs, product_report, product_action_wanted;
    String nature_of_call_machine_prob, machine_complain, print_problem;

    DatePickerDialog.OnDateSetListener datelistener;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    FirebaseUser user;
    FirebaseAuth.AuthStateListener authStateListener;
    UserClass userCalss;
    String uid;

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

        Button btn_submit;

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
        btn_submit = view.findViewById(R.id.btn_submittt);





        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String date_d = sdf.format(date);
        tv_complaint_date.setText(date_d);


        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        user = firebaseAuth.getCurrentUser();
        assert user != null;
        uid = user.getUid();

        Random random = new Random();
                int complaint_number1 = random.nextInt(1000-1)+1;
        tv_complaint_no.setText(String.valueOf(complaint_number1));

        DocumentReference dref = firestore.collection("Users").document(uid);
        dref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                fname = value.getString("FullName");
                email = value.getString("Email");
                phone = value.getString("Phone");
//                complain_number =  value.getLong("Complain Number").intValue();
                et_form_email.getEditText().setText(email);
                et_form_phone.getEditText().setText(phone);
                et_form_fname.getEditText().setText(fname);
//                Random random = new Random();
//                int complaint_number1 = random.nextInt(1000-1)+1;
//
//                if(complaint_number1 == complain_number){
//                    tv_complaint_no.setText(complain_number);
//                }else{
//                    tv_complaint_no.setText(String.valueOf(complaint_number1));
//                }

            }
        });
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        c.clear();
        long Today  = MaterialDatePicker.todayInUtcMilliseconds();
        CalendarConstraints.Builder constraintbuilder = new CalendarConstraints.Builder();
        constraintbuilder.setValidator(DateValidatorPointForward.now());
        String dateOfVisit;
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select Visit Date");
        builder.setSelection(Today);
        builder.setCalendarConstraints(constraintbuilder.build());
        final MaterialDatePicker<Long> materialDatePicker = builder.build();

        tv_date_of_visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            materialDatePicker.show(requireActivity().getSupportFragmentManager(),"Date Picker");
            materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                @Override
                public void onPositiveButtonClick(Object selection) {
                    tv_date_of_visit.setText(materialDatePicker.getHeaderText());

                }
            });

            }
        });


      cb_warranty_yes.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              if (cb_warranty_yes.isChecked()) {
                  cb_warranty_no.setChecked(false);
                  warranty = cb_warranty_yes.getText().toString();
              }
          }
      });
      cb_warranty_no.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
               if(cb_warranty_no.isChecked()){
                  cb_warranty_yes.setChecked(false);
                  warranty = cb_warranty_no.getText().toString();
              }
          }
      });

      cb_print_problem.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              if(cb_print_problem.isChecked()){
                  cb_machine_complain.setChecked(false);
                  cb_machine_installation.setChecked(false);
                  nature_of_call_machine_prob = cb_print_problem.getText().toString();
              }
          }
      });

        cb_machine_complain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cb_machine_complain.isChecked()){
                    cb_print_problem.setChecked(false);
                    cb_machine_installation.setChecked(false);
                    nature_of_call_machine_prob = cb_machine_complain.getText().toString();
                }
            }
        });

        cb_machine_installation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cb_machine_installation.isChecked()){
                    cb_machine_complain.setChecked(false);
                    cb_print_problem.setChecked(false);
                    nature_of_call_machine_prob = cb_machine_installation.getText().toString();
                }
            }
        });

     btn_submit.setOnClickListener(view1 -> {
         if(CheckFields(et_form_fname) && CheckFields(et_form_action) && CheckFields(et_form_address) && CheckFields(et_form_email) && CheckFields(et_form_phone)
                 && CheckFields(et_form_product_model) && CheckFields(et_form_product_name) && CheckFields(et_form_report) && CheckFields(et_form_action)){
                 if(user !=null){

                     userCalss = new UserClass();
                     address = et_form_address.getEditText().getText().toString();
                     product_name = et_form_product_name.getEditText().getText().toString();
                     product_model = et_form_product_model.getEditText().getText().toString();
                     product_specs = et_form_specs.getEditText().getText().toString();
                     product_report = et_form_report.getEditText().getText().toString();
                     product_action_wanted = et_form_action.getEditText().getText().toString();

                    userCalss.setAddress(address);
                    userCalss.setProduct_name(product_name);
                    userCalss.setProduct_Model(product_model);
                    userCalss.setProduct_specs(product_specs);
                    userCalss.setProduct_report(product_report);
                    userCalss.setForm_action(product_action_wanted);
                    userCalss.setMachine_complain(nature_of_call_machine_prob);
                    userCalss.setWarranty_status(warranty);

                     userCalss.setDate_visit(tv_date_of_visit.getText().toString());
                    userCalss.setComplain_date(tv_complaint_date.getText().toString());
                    userCalss.setComplain_number(tv_complaint_no.getText().toString());
                    userCalss.setRating(rb_forms_ratingBar.getRating());

                     HashMap<String, Object> userinfo = new HashMap<>();
                     userinfo.put("FullName",fname);
                     userinfo.put("Email",email);
                     userinfo.put("Phone",phone);
                     userinfo.put("Address",address);
                     userinfo.put("Product Name",product_name);
                     userinfo.put("Product Model",product_model);
                     userinfo.put("Product Specs",product_specs);
                     userinfo.put("Product Report",product_report);
                     userinfo.put("Action Required",product_action_wanted);
                     userinfo.put("Nature For Problem",nature_of_call_machine_prob);
                     userinfo.put("Warranty Status",warranty);
                     userinfo.put("Date of Visit", tv_date_of_visit.getText().toString());
                     userinfo.put("Complaint Filed",userCalss.getComplain_date());
                     userinfo.put("Complaint Number",userCalss.getComplain_number());
                     userinfo.put("Rating",userCalss.getRating());

                     CollectionReference cref = firestore.collection("Users/"+uid+"/Site Intervention");

                     cref.add(userinfo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                         @Override
                         public void onSuccess(DocumentReference documentReference) {
                             Toast.makeText(requireContext(),"Success For Filing a Complain",Toast.LENGTH_LONG).show();
                             String id = documentReference.getId();
                             Log.d("Document Id", "onViewCreated: "+id);
                         }
                     }).addOnFailureListener(new OnFailureListener() {
                         @Override
                         public void onFailure(@NonNull Exception e) {
                             Toast.makeText(requireContext(),"Failed Due to:"+e.getMessage(),Toast.LENGTH_LONG).show();
                         }
                     });

//                     dref.update(userinfo).addOnSuccessListener(new OnSuccessListener<Void>() {
//                         @Override
//                         public void onSuccess(Void unused) {
//                             Toast.makeText(requireContext(),"Success For Filing a Complain",Toast.LENGTH_LONG).show();
//                         }
//                     }).addOnFailureListener(new OnFailureListener() {
//                         @Override
//                         public void onFailure(@NonNull Exception e) {
//                             Toast.makeText(requireContext(),"Failed Due to:"+e.getMessage(),Toast.LENGTH_LONG).show();
//                         }
//                     });

                 }else{
                     Toast.makeText(requireContext(),"User Not Found",Toast.LENGTH_LONG).show();
                 }
         }
     });

    }



    private boolean CheckFields(TextInputLayout et) {
        if (Objects.requireNonNull(et.getEditText()).getText().toString().isEmpty()) {

            et.setError("Please Fill Up the Field");
            valid = false;
        } else {
            valid = true;
        }
        return valid;
    }
}