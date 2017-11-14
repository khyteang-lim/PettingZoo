package com.example.limsanity.firstapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.limsanity.firstapp.API.AlertService;
import com.example.limsanity.firstapp.API.ProductService;
import com.example.limsanity.firstapp.API.SettingsService;
import com.example.limsanity.firstapp.Fragments.AlertDropdownDialogFragment;
import com.example.limsanity.firstapp.Fragments.AlertFragment;
import com.example.limsanity.firstapp.Fragments.AlertOptionsDialogFragment;
import com.example.limsanity.firstapp.Fragments.ConnectedDeviceFragment;
import com.example.limsanity.firstapp.Fragments.IndividualProductFragment;
import com.example.limsanity.firstapp.Fragments.ProductFragment;
import com.example.limsanity.firstapp.Fragments.ProductOptionsDialogFragment;
import com.example.limsanity.firstapp.Fragments.UserSettingsDialogFragment;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ProductFragment.ProductFragmentInterface, View.OnClickListener, AlertFragment.AlertCallback {

    // Services
    AlertService alertService;
    ProductService productService;
    SettingsService settingsService;

    // Fragments
    AlertFragment alertFragment;
    ProductFragment productFragment;
    ConnectedDeviceFragment settingsFragment;
    IndividualProductFragment fixtureFragment;

    Fragment currentFragment = null;


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

        // Instantiate the fragment
        productFragment = ProductFragment.newInstance(productService, this);
        currentFragment = productFragment;

        // Show the ProductFragment
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.fragment_container, productFragment);
        fragmentTransaction.commit();

        // Set the onclick for the top nav items
        findViewById(R.id.threeBotsIB).setOnClickListener(this);
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
            if(alertFragment != null && fragmentManager.findFragmentByTag(AlertFragment.class.getName()) != null) {
                fragmentTransaction.remove(alertFragment);
            }
            alertFragment = AlertFragment.newInstance(alertService, this);
            currentFragment = alertFragment;
            fragmentTransaction.add(R.id.fragment_container, alertFragment);
            fragmentTransaction.commit();
            getSupportActionBar().setTitle("Alerts");
        } else if(id == R.id.products){
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if(productFragment != null && fragmentManager.findFragmentByTag(ProductFragment.class.getName()) != null) {
                fragmentTransaction.remove(productFragment);
            }
            productFragment = ProductFragment.newInstance(productService, this);
            currentFragment = productFragment;
            fragmentTransaction.add(R.id.fragment_container, productFragment);
            fragmentTransaction.commit();
            getSupportActionBar().setTitle("Products");
        } else if(id == R.id.settings){
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if(settingsFragment != null && fragmentManager.findFragmentByTag(ConnectedDeviceFragment.class.getName()) != null) {
                fragmentTransaction.remove(settingsFragment);
            }
            settingsFragment = ConnectedDeviceFragment.newInstance(settingsService);
            currentFragment = settingsFragment;
            fragmentTransaction.add(R.id.fragment_container, settingsFragment);
            fragmentTransaction.commit();
            getSupportActionBar().setTitle("Setting");
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFixtureClicked(ProductService.Fixture fixture) {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(fixtureFragment != null && fragmentManager.findFragmentByTag(IndividualProductFragment.class.getName()) != null) {
            fragmentTransaction.remove(fixtureFragment);
        }
        fixtureFragment = IndividualProductFragment.newInstance();
        currentFragment = fixtureFragment;
        fragmentTransaction.add(R.id.fragment_container, fixtureFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onProductMenu(ProductService.Product product) {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        ProductOptionsDialogFragment dialog = new ProductOptionsDialogFragment();
        dialog.show(fragmentManager, null);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.threeBotsIB) {
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            if(currentFragment.getClass() == ProductFragment.class) {
                UserSettingsDialogFragment settingsDialog = new UserSettingsDialogFragment();
                settingsDialog.show(fragmentManager, null);
            } else if(currentFragment.getClass() == AlertFragment.class) {
                AlertDropdownDialogFragment settingsDialog = new AlertDropdownDialogFragment();
                settingsDialog.show(fragmentManager, null);
            }
        }
    }

    @Override
    public void onAlertOptions(AlertService.Alert alert) {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        ProductOptionsDialogFragment dialog = new ProductOptionsDialogFragment();
        dialog.show(fragmentManager, null);
    }
}
