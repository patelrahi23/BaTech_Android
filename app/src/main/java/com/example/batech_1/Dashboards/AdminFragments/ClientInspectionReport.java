package com.example.batech_1.Dashboards.AdminFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.batech_1.Adapters.CheckAdapterClient;
import com.example.batech_1.Adapters.CheckBoxAdapter;
import com.example.batech_1.R;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClientInspectionReport extends Fragment {
    TextInputLayout et_name, et_model, et_head_name;
    RecyclerView listv;
    CheckAdapterClient adapter;
    ImageView signature_pad;
    TextView tv_date;
    String uid = null, user_name = null;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    List<String> services =null;

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
        return inflater.inflate(R.layout.fragment_client_inspection_report, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listv = view.findViewById(R.id.listv);
        tv_date = view.findViewById(R.id.date);
        et_name = view.findViewById(R.id.et_form_buyer_name);
        et_model = view.findViewById(R.id.et_form_model_name);
        et_head_name = view.findViewById(R.id.et_form_head_name);
        signature_pad = view.findViewById(R.id.signature_pad);

        uid = Objects.requireNonNull(auth.getCurrentUser()).getUid();
        getData();
    }

    @SuppressLint("CheckResult")
    private void getData() {


        try {
            firestore.collection("Users").document(uid).get().addOnSuccessListener(documentSnapshot -> {
                user_name = documentSnapshot.getString("FullName");
            }).addOnFailureListener(e -> Toast.makeText(requireActivity(), "Cannot Fetch Data, Something Went Wrong", Toast.LENGTH_SHORT).show());

            firestore.collection("Self Inspection Checklist").document(user_name).get().addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();

                        if(documentSnapshot.exists()){
                            if(task.getResult().exists()){
                                Objects.requireNonNull(et_name.getEditText()).setText(task.getResult().getString("FullName"));
                                Objects.requireNonNull(et_model.getEditText()).setText(task.getResult().getString("Model"));
                                Objects.requireNonNull(et_head_name.getEditText()).setText(task.getResult().getString("Inspection Head"));
                                Objects.requireNonNull(tv_date).setText(task.getResult().getString("Date"));
                                Glide.with(requireContext()).load(task.getResult().get("Head Signature")).into(signature_pad);
                            services = (List<String>) documentSnapshot.get("Checked Items");
                            populateRecyc(services);
                        }else {
                                Log.e("TAG", "Document is not exist");
                            }
                    }
                }
            })
              .addOnFailureListener(e-> Toast.makeText(requireActivity(), "Cannot Fetch Data, Something Went Wrong.\n"+e.getMessage(), Toast.LENGTH_SHORT).show());
        }catch (Exception ex){
            Toast.makeText(requireContext(), "Exception:\n"+ex.getMessage(), Toast.LENGTH_SHORT).show();
            Objects.requireNonNull(et_name.getEditText()).setText("N/A");
            Objects.requireNonNull(et_model.getEditText()).setText("N/A");
            Objects.requireNonNull(et_head_name.getEditText()).setText("N/A");
            Objects.requireNonNull(tv_date).setText("Date: N/A");

        }
    }

    private void populateRecyc(List<String> services) {
        listv.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false));
        adapter = new CheckAdapterClient(requireContext(), services);
        listv.setAdapter(adapter);
    }
}