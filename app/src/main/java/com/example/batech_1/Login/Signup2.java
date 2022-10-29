package com.example.batech_1.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.batech_1.ModelClasses.UserClass;
import com.example.batech_1.R;
import com.example.batech_1.SharedPrefHelper;


public class Signup2 extends AppCompatActivity {
    Button btn_next, btn_login;
    UserClass user;
    RadioGroup radioSexGroup;
    String gender = null, dob = null;
    RadioButton radio_sex_button;
    int radio_id;
    DatePicker datepicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_signup2);

        btn_next = findViewById(R.id.btn_next);
        btn_login = findViewById(R.id.btn_login);
        radioSexGroup = (RadioGroup) findViewById(R.id.radiosexgroup);
        datepicker = (DatePicker) findViewById(R.id.datepicker1);


        btn_login.setOnClickListener(view -> {
            startActivity(new Intent(Signup2.this,Login.class));
        });

        btn_next.setOnClickListener(view -> {
            radio_id = radioSexGroup.getCheckedRadioButtonId();
            radio_sex_button = findViewById(radio_id);
            gender = radio_sex_button.getText().toString();


            int dd = datepicker.getDayOfMonth();
            int mm = datepicker.getMonth()+1;
            int yy = datepicker.getYear();

            int birth_age = 2022-yy;
            dob = dd+"-"+mm+"-"+yy;
            if(birth_age>18){

                user = new UserClass();
                user.setDOB(dob);
                user.setGender(gender);
                SharedPrefHelper.setSharedPrefrences(Signup2.this,"GenderDob",user);
                Intent intent = new Intent(Signup2.this, Signup3.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(getApplicationContext(), "Sorry You are not Above 18", Toast.LENGTH_LONG).show();
            }

        });
    }
}