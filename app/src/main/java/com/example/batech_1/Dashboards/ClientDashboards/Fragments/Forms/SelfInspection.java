package com.example.batech_1.Dashboards.ClientDashboards.Fragments.Forms;

import android.graphics.Bitmap;
import android.net.Uri;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.batech_1.Adapters.CheckBoxAdapter;
import com.example.batech_1.R;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class SelfInspection extends Fragment {

    RecyclerView listv;
    List<String> cb_text;
    List<String> cb_selected_text;
    CheckBoxAdapter adapter;
    TextView tv_date;
    String itemSelected = null;
    MaterialButton btn_submit;
    String model_name, full_name, inspection_head_name;
    TextInputLayout et_name, et_model, et_head_name;
    FirebaseAuth firebaseAuth;
    StorageReference storageReference;
    FirebaseFirestore firestore;
    FirebaseUser user;
    boolean valid = true;
    Bitmap bitmap_image;
    SignaturePad signature_pad;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_self_inspection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_submit = view.findViewById(R.id.btn_submittt);
        listv = view.findViewById(R.id.listv);
        tv_date = view.findViewById(R.id.date);
        et_name = view.findViewById(R.id.et_form_buyer_name);
        et_model = view.findViewById(R.id.et_form_model_name);
        et_head_name = view.findViewById(R.id.et_form_head_name);
        signature_pad = view.findViewById(R.id.signature_pad);

        setDate();

        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        firestore = FirebaseFirestore.getInstance();
        user = firebaseAuth.getCurrentUser();
        assert user != null;

        recyclerViewInit();

        btn_submit.setOnClickListener(v -> {

            getFormData();
        });

    }

    private void UploadImageURL() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap_image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask iamgeurl = storageReference.child("Head Signature").child(this.full_name + ".jpg").putBytes(data);
        iamgeurl.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {

                    addFileToFirestore(full_name, model_name, inspection_head_name, task);
                } else {
                    Toast.makeText(requireContext(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void getFormData() {
        if (CheckFields(et_name) && CheckFields(et_model) && CheckFields(et_head_name)) {
            getRecyclerData();
            bitmap_image = signature_pad.getSignatureBitmap();
            full_name = Objects.requireNonNull(et_name.getEditText()).getText().toString();
            model_name = Objects.requireNonNull(et_model.getEditText()).getText().toString();
            inspection_head_name = Objects.requireNonNull(et_head_name.getEditText()).getText().toString();

            UploadImageURL();
        }
    }

    private void addFileToFirestore(String full_name, String model_name, String inspection_head_name, Task<UploadTask.TaskSnapshot> task) {
        if (user != null) {
            Task<Uri> downloadURI;
            if (task != null) {
                downloadURI = task.getResult().getMetadata().getReference().getDownloadUrl();
                downloadURI.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        HashMap<String, Object> userinfo = new HashMap<>();
                        userinfo.put("FullName", full_name);
                        userinfo.put("Model", model_name);
                        userinfo.put("Inspection Head", inspection_head_name);
                        userinfo.put("Signature", uri.toString());
                        userinfo.put("Date", tv_date.getText());
                        userinfo.put("Checked Items", cb_selected_text.toString());

                        firestore.collection("Self Inspection Checklist").document(full_name).set(userinfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(requireContext(), "Success For Filing a Complain", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(requireContext(), "User Not Found", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });
            }
        } else {
            Toast.makeText(requireContext(), "User Not Found", Toast.LENGTH_LONG).show();
        }
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

    private void setDate() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String date_d = sdf.format(date);
        tv_date.setText(date_d);
    }

    private void recyclerViewInit() {
        cb_text = new ArrayList<>();
        cb_text.add("Manufacturing Test");
        cb_text.add("Machine Testing");
        cb_text.add("Finishing");
        cb_text.add("Cup");
        cb_text.add("Ink 250g / Solvent 500ml");
        cb_text.add("Tested Sample");
        cb_text.add("Tool kit");
        cb_text.add("Accessories");
        cb_text.add("Bolt Loosening Check");
        cb_text.add("Wire Loosening Check");
        cb_text.add("Wiring Thimbal Check");
        cb_text.add("Fuse fitting Check");
        cb_text.add("Stopper Adjustment Check");
        cb_text.add("Head Rod Locking Screw Check");
        cb_text.add("Body Welding Check");
        cb_text.add("608 No. Bearing Fitting Check");
        cb_text.add("Pneumatic Pipe Fitting Foldage / Leakage");
        cb_text.add("Sensor Fitting / Adjustment");
        cb_text.add("Drive Check");
        cb_text.add("Cup Movement check (2.5mm less than both side)");
        cb_text.add("Plate Lapping / Etching");
        cb_text.add("Cup Lapping");
        cb_text.add("Stickering");
        cb_text.add("Paint");
        cb_text.add("Main Wire");
        cb_text.add("Foot Switch");
        cb_text.add("Zig - Fixture ( T and Bolt )");
        cb_text.add("Pad");
        cb_text.add("Plate");
        listv.setHasFixedSize(true);
        listv.setItemViewCacheSize(50);
        listv.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false));
        adapter = new CheckBoxAdapter(requireContext(), cb_text);
        listv.setAdapter(adapter);

    }

    public void getRecyclerData() {
        cb_selected_text = new ArrayList<>();
        cb_selected_text = adapter.getSelectedValues();
        Log.d("Item Selected", "onViewCreated:" + cb_selected_text.toString());
//    Toast.makeText(requireContext(), itemSelected, Toast.LENGTH_SHORT).show();
    }

}