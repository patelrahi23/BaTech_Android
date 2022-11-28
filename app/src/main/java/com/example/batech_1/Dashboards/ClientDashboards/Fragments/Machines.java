package com.example.batech_1.Dashboards.ClientDashboards.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.example.batech_1.Adapters.MachineAdapter;
import com.example.batech_1.ModelClasses.MachineModelClass;
import com.example.batech_1.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Machines extends Fragment {

    RecyclerView machineRecyclerView;
    ArrayList<MachineModelClass> machineModelClasses;
    MachineAdapter machineAdapter;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    FloatingActionButton admin_fab;
    String uid = null, user = null, Product_name= null, product_desc= null, product_model = null;
    Animation card_animation_fade_in;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_machines, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapingViews(view);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        uid = Objects.requireNonNull(auth.getCurrentUser()).getUid();

        showFabs();
        machineRecyclerView.setHasFixedSize(true);
        machineRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
        machineModelClasses = new ArrayList<>();

        populateRVWithData();


       admin_fab.setOnClickListener(view1 -> {
           callBottomSheetAdmin(view1);
       });


    }

    private void populateRVWithData() {

        firestore.collection("Machines").get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot snap : queryDocumentSnapshots) {
                product_desc = Objects.requireNonNull(snap.get("Machine Description")).toString();
                Product_name = Objects.requireNonNull(snap.get("Machine Name")).toString();
                product_model = Objects.requireNonNull(snap.get("Machine Model")).toString();
                machineModelClasses.add(new MachineModelClass(product_model,product_desc,Product_name));
            }
            machineAdapter = new MachineAdapter(machineModelClasses,requireActivity());
            machineRecyclerView.setAdapter(machineAdapter);
        });

//
    }

    private void showFabs() {
        firestore.collection("Users").document(uid).get().addOnSuccessListener(documentSnapshot -> {
            user = documentSnapshot.getString("Admin");
            if(Objects.equals(user, "1")){
                admin_fab.setVisibility(View.VISIBLE);
            }else{
                admin_fab.setVisibility(View.INVISIBLE);
            }
        }).addOnFailureListener(e -> Toast.makeText(requireActivity(), "Cannot Add Machines, Something Went Wrong", Toast.LENGTH_SHORT).show());

    }



    private void callBottomSheetAdmin(View view1) {
        card_animation_fade_in = AnimationUtils.loadAnimation(requireContext(),R.anim.fade);

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext(), R.style.MyNewDialog);
        bottomSheetDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        View view = LayoutInflater.from(requireContext()).inflate(R.layout.add_admin_machines, view1.findViewById(R.id.cons_layout));
        view.startAnimation(card_animation_fade_in);

        Button btn = view.findViewById(R.id.btn_add);
        TextInputLayout et_product_name = view.findViewById(R.id.et_product_name);
        TextInputLayout et_product_model = view.findViewById(R.id.et_product_model);
        TextInputLayout et_product_desc = view.findViewById(R.id.et_product_desc);

        btn.setOnClickListener(view2 -> {
            product_desc = Objects.requireNonNull(et_product_desc.getEditText()).getText().toString();
            product_model = Objects.requireNonNull(et_product_model.getEditText()).getText().toString();
            Product_name = Objects.requireNonNull(et_product_name.getEditText()).getText().toString();
            machineModelClasses.add(new MachineModelClass(product_model,product_desc,Product_name));

            DocumentReference dref = firestore.collection("Machines").document();
            HashMap<String, Object> userinfo = new HashMap<>();
            userinfo.put("Machine Name",Product_name);
            userinfo.put("Machine Model",product_model);
            userinfo.put("Machine Description",product_desc);
            dref.set(userinfo);
            Toast.makeText(requireContext(), "You added Machine!.", Toast.LENGTH_LONG).show();

        });

        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();

    }



    private void mapingViews(View view) {
        machineRecyclerView = view.findViewById(R.id.machine_recyclerview);
        admin_fab = view.findViewById(R.id.admin_fab);


    }
}