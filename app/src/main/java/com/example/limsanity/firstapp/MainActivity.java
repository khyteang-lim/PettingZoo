package com.example.limsanity.firstapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.limsanity.firstapp.API.AlertService;
import com.example.limsanity.firstapp.API.ProductService;
import com.example.limsanity.firstapp.API.SettingsService;
import com.example.limsanity.firstapp.Fragments.AlertFragment;
import com.example.limsanity.firstapp.Fragments.ConnectedDeviceFragment;
import com.example.limsanity.firstapp.Fragments.ProductFragment;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // Services
    AlertService alertService;
    ProductService productService;
    SettingsService settingsService;

    // Fragments
    AlertFragment alertFragment;
    ProductFragment productFragment;
    ConnectedDeviceFragment settingsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Make the Services
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        alertService = new AlertService(requestQueue);
        productService = new ProductService(requestQueue);
        settingsService = new SettingsService(requestQueue);

        // Instantiate the fragments
        alertFragment = AlertFragment.newInstance(alertService);
        productFragment = ProductFragment.newInstance(productService);
        settingsFragment = ConnectedDeviceFragment.newInstance(settingsService);

        // Show the AlertFragment
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.fragment_container, productFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.alerts) {
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if(fragmentManager.findFragmentByTag(alertFragment.getClass().getName()) != null) {
                fragmentTransaction.remove(alertFragment);
            }
            alertFragment = AlertFragment.newInstance(alertService);
            fragmentTransaction.add(R.id.fragment_container, alertFragment);
            fragmentTransaction.commit();
        } else if(id == R.id.products){
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if(fragmentManager.findFragmentByTag(productFragment.getClass().getName()) != null) {
                fragmentTransaction.remove(productFragment);
            }
            productFragment = ProductFragment.newInstance(productService);
            fragmentTransaction.add(R.id.fragment_container, productFragment);
            fragmentTransaction.commit();
        } else if(id == R.id.settings){
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if(fragmentManager.findFragmentByTag(settingsFragment.getClass().getName()) != null) {
                fragmentTransaction.remove(settingsFragment);
            }
            settingsFragment = ConnectedDeviceFragment.newInstance(settingsService);
            fragmentTransaction.add(R.id.fragment_container, settingsFragment);
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
