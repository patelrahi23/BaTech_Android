package com.example.batech_1.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.batech_1.Dashboards.ClientDashboards.ClientDashBoard;
import com.example.batech_1.Dashboards.MainDashboard;
import com.example.batech_1.ModelClasses.UserClass;
import com.example.batech_1.R;
import com.example.batech_1.SharedPrefHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class OTPVerify extends AppCompatActivity {

    String codeBySystem = null;
    PinView otp_pin;
    FirebaseFirestore firestore;
    Button btn_verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverify);

        otp_pin = findViewById(R.id.otp_pin);
        btn_verify = findViewById(R.id.btn_verify);
        firestore = FirebaseFirestore.getInstance();

        UserClass user;
        user =(UserClass) SharedPrefHelper.getSharedPrefrences(this,"PhoneDesignation");
        String phone = user.getPh_number();
        sendVerificationCodetoUser(phone);
        btn_verify.setOnClickListener(view->{
            String userCode = otp_pin.getText().toString();
            if(!userCode.isEmpty()){
                verifyCode(userCode);
            }
        });


    }

    private void sendVerificationCodetoUser(String phone) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                        .setPhoneNumber(phone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeBySystem = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code =  phoneAuthCredential.getSmsCode();
            if(code != null){
                otp_pin.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(OTPVerify.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem,code);
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(task -> {
            if(task.isSuccessful()){

                        startActivity(new Intent(OTPVerify.this, ClientDashBoard.class));
                        finish();
            }
        });
    }
}