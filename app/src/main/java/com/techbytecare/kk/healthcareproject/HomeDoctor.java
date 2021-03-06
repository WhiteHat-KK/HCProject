package com.techbytecare.kk.healthcareproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.techbytecare.kk.healthcareproject.Common.Common;

import info.hoang8f.widget.FButton;
import io.paperdb.Paper;

public class HomeDoctor extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView txtFullName,txtHospital,txtPhone,txtAddress,txtEmail;

    FButton btnGetPatient;

    FirebaseDatabase database;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_doctor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Health Management");
        setSupportActionBar(toolbar);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("UserDoctor");

        btnGetPatient = findViewById(R.id.btnGetPatient);

        Paper.init(this);

        txtPhone = findViewById(R.id.txtPhone);
        txtEmail = findViewById(R.id.txtEmail);
        txtAddress = findViewById(R.id.txtAddress);
        txtHospital = findViewById(R.id.txtHospital);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        txtFullName = (TextView)headerView.findViewById(R.id.txtFullName);
        txtFullName.setText(Common.currentDoctor.getHospitalName());

        btnGetPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeDoctor.this,GetPatient.class));
            }
        });

        getDetails();
    }

    private void getDetails() {
        txtPhone.setText(Common.currentDoctor.getPhone());
        txtEmail.setText(Common.currentDoctor.getEmail());
        txtAddress.setText(Common.currentDoctor.getAddress());
        txtHospital.setText(Common.currentDoctor.getHospitalName());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the home action
        }
        else if (id == R.id.nav_hospital) {
            startActivity(new Intent(HomeDoctor.this,HospitalActivity.class));
        }
        else if (id == R.id.nav_chat) {

        }
        else if (id == R.id.nav_signOut) {
            Paper.book().destroy();

            Intent signOutIntent = new Intent(HomeDoctor.this,MainActivity.class);
            signOutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(signOutIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
