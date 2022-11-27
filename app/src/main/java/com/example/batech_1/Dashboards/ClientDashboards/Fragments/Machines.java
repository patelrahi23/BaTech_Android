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
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

public class Machines extends Fragment {

    RecyclerView machineRecyclerView;
    ArrayList<MachineModelClass> machineModelClasses;
    MachineAdapter machineAdapter;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    FloatingActionButton admin_fab, client_fab;
    String uid = null, user = null, Product_name= null, product_desc= null, product_model = null;
    Animation card_animation_fade_in,recyc_anim;

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

        machineModelClasses = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        uid = Objects.requireNonNull(auth.getCurrentUser()).getUid();
        firestore.collection("Users").document(uid).get().addOnSuccessListener(documentSnapshot -> {
            user = documentSnapshot.getString("Admin");
            if(Objects.equals(user, "1")){
                admin_fab.setVisibility(View.VISIBLE);
                client_fab.setVisibility(View.INVISIBLE);
            }else{
                admin_fab.setVisibility(View.INVISIBLE);
                client_fab.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(e -> Toast.makeText(requireActivity(), "Cannot Add Machines, Something Went Wrong", Toast.LENGTH_SHORT).show());

        machineRecyclerView.setHasFixedSize(true);
        machineRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
        machineModelClasses = new ArrayList<>();

       admin_fab.setOnClickListener(view1 -> {
           callBottomSheetAdmin(view1);
       });

       client_fab.setOnClickListener(view12 -> {
            callBottomSheetClient();
       });
    }

    private void callBottomSheetClient() {


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
            machineAdapter = new MachineAdapter(machineModelClasses, requireActivity());
            machineRecyclerView.setAdapter(machineAdapter);
        });

        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();

    }



    private void mapingViews(View view) {
        machineRecyclerView = view.findViewById(R.id.machine_recyclerview);
        admin_fab = view.findViewById(R.id.admin_fab);
        client_fab = view.findViewById(R.id.client_fab);

    }
}