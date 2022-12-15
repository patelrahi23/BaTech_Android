package com.example.batech_1.Dashboards;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.batech_1.FilePathUtil;
import com.example.batech_1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hbb20.CountryCodePicker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileSetting extends AppCompatActivity {

    MaterialButton btn_update;
    TextInputLayout et_fname, et_uname, et_pass, et_con_pass,  et_phone, et_designation;
    CountryCodePicker ccp;
    ImageView img, add_image;
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    String fname, uid;
    StorageReference storageReference;
    Uri uri = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);

        btn_update = findViewById(R.id.btn_update);
        et_fname = findViewById(R.id.et_fname);
        et_uname = findViewById(R.id.et_uname);
        et_pass = findViewById(R.id.et_pass);
        et_con_pass = findViewById(R.id.et_con_pass);
        ccp = findViewById(R.id.ccp);
        et_phone = findViewById(R.id.et_phone);
        et_designation = findViewById(R.id.et_designation);
        img = findViewById(R.id.img);
        add_image = findViewById(R.id.add_image);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        uid = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        getData();

        add_image.setOnClickListener(v->{
            getImage();
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                    byte[] thumb = byteArrayOutputStream.toByteArray();
                    UploadTask imageP =  storageReference.child("Profile Pic").child(fname + ".jpg").putBytes(thumb);

                    imageP.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()) {
                                EditData(task);
                            } else {
                                Toast.makeText(ProfileSetting.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }catch (Exception ex){
                    Log.e("Exception", "getImage: "+ex.getMessage());
                }
            }
        });

    }

    private void getImage() {
        if(ActivityCompat.checkSelfPermission(ProfileSetting.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ProfileSetting.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
            return;
        }
        Intent intent = new Intent();
        //intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);


    }

    private void EditData(Task<UploadTask.TaskSnapshot> task) {

        if(isValidPassword(Objects.requireNonNull(et_pass.getEditText()).getText().toString())){
            if(et_con_pass.getEditText().getText().toString().equals(et_pass.getEditText().getText().toString())){

                Task<Uri> imguri = null;
                if(task!=null) {
                    imguri = Objects.requireNonNull(Objects.requireNonNull(task.getResult().getMetadata()).getReference()).getDownloadUrl();
                    imguri.addOnSuccessListener(uri -> {
                        HashMap<String, Object> userinfo = new HashMap<>();
                        userinfo.put("FullName", Objects.requireNonNull(et_fname.getEditText()).getText().toString());
                        userinfo.put("Email", Objects.requireNonNull(et_uname.getEditText()).getText().toString());
                        userinfo.put("Password", Objects.requireNonNull(et_pass.getEditText()).getText().toString());
                        userinfo.put("Phone", Objects.requireNonNull(et_phone.getEditText()).getText().toString());
                        userinfo.put("Designation", Objects.requireNonNull(et_designation.getEditText()).getText().toString());
                        userinfo.put("Image", uri.toString());

                        firestore.collection("Users").document(uid).set(userinfo).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                Toast.makeText(ProfileSetting.this, "Updated", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ProfileSetting.this, "Try again", Toast.LENGTH_SHORT).show();
                            }
                        });
                    });
                }
            }else{
                et_con_pass.setError("Passwords dosen't match");
            }
        }else{
            et_pass.setError("Password too weak");
        }
    }

    private void getData() {

        firestore.collection("Users").document(Objects.requireNonNull(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                fname = Objects.requireNonNull(task.getResult().get("FullName")).toString();
                Objects.requireNonNull(et_fname.getEditText()).setText(task.getResult().get("FullName").toString());
                Objects.requireNonNull(et_uname.getEditText()).setText(task.getResult().get("Email").toString());
                Objects.requireNonNull(et_pass.getEditText()).setText(task.getResult().get("Password").toString());
                Objects.requireNonNull(et_phone.getEditText()).setText(task.getResult().get("Phone").toString());
                Objects.requireNonNull(et_designation.getEditText()).setText(task.getResult().get("Designation").toString());
                Objects.requireNonNull(et_fname.getEditText()).setText(task.getResult().get("FullName").toString());
            }else{
                Toast.makeText(this, "Cannot Find User, Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static boolean isValidPassword(String password) {
        Pattern p;
        Matcher m;
        String PASSWORD_PATTERN
                =
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
        p = Pattern.compile(PASSWORD_PATTERN);
        m = p.matcher(password);
        return m.matches();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK) {
            assert data != null;
            uri = data.getData();

            try {
                String path = FilePathUtil.getPath(this, uri);
                Glide.with(this).load(new File(Objects.requireNonNull(path))).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(img);

            } catch (/*IOException | */URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }
}