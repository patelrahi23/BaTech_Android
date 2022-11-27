package com.example.batech_1.Dashboards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.batech_1.Dashboards.ClientDashboards.Fragments.About_Us;
import com.example.batech_1.Dashboards.ClientDashboards.Fragments.Client_Forms;
import com.example.batech_1.Dashboards.ClientDashboards.Fragments.Fragment_Home;
import com.example.batech_1.Dashboards.ClientDashboards.Fragments.Machines;
import com.example.batech_1.R;
import com.google.android.material.navigation.NavigationView;

public class MainDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    TextView tv_toolbar;
    ImageView img_back,img_profile;
    NavigationView client_navigation_view;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_dash_board);

        tv_toolbar = findViewById(R.id.tv_toolbar);
        img_back = findViewById(R.id.btn_back);
        img_profile = findViewById(R.id.btn_person);
        client_navigation_view = findViewById(R.id.client_navigation_view);
        drawerLayout = findViewById(R.id.client_drawerLayout);

        Fragment_Home home = new Fragment_Home();
        FragmentManager home_Transaction = getSupportFragmentManager();
        home_Transaction.beginTransaction().replace(R.id.client_framelayout,home).addToBackStack("Home").commit();

        navigationDrawer();

        img_back.setOnClickListener(view->{
            drawerLayout.openDrawer(GravityCompat.START);
        });


    }
    private void navigationDrawer() {
        client_navigation_view.bringToFront();
        client_navigation_view.setNavigationItemSelectedListener(this);
        client_navigation_view.setCheckedItem(R.id.nav_home);
        if(drawerLayout.isDrawerVisible(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Fragment_Home home = new Fragment_Home();
                FragmentManager home_Transaction = getSupportFragmentManager();
                home_Transaction.beginTransaction().replace(R.id.client_framelayout,home).commit();
                drawerLayout.closeDrawers();
                tv_toolbar.setText("Home");
                break;

            case R.id.nav_aboutus:
                About_Us about_us = new About_Us();
                FragmentManager about_us_transaction = getSupportFragmentManager();
                about_us_transaction.beginTransaction().replace(R.id.client_framelayout,about_us).commit();
                drawerLayout.closeDrawers();
                tv_toolbar.setText("About Us");
                break;

            case R.id.nav_machines:
                Machines machines = new Machines();
                FragmentManager machine_transaction = getSupportFragmentManager();
                machine_transaction.beginTransaction().replace(R.id.client_framelayout,machines).commit();
                drawerLayout.closeDrawers();
                tv_toolbar.setText("Machines");
                break;
            case R.id.nav_forms:
                Client_Forms client_forms = new Client_Forms();
                FragmentManager client_forms_transaction = getSupportFragmentManager();
                client_forms_transaction.beginTransaction().replace(R.id.client_framelayout,client_forms).commit();
                drawerLayout.closeDrawers();
                tv_toolbar.setText("Forms");
                break;
        }
        return true;
    }
}