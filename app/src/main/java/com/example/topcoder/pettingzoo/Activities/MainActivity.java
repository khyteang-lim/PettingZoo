package com.example.topcoder.pettingzoo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.topcoder.pettingzoo.API.AlertService;
import com.example.topcoder.pettingzoo.API.ProductService;
import com.example.topcoder.pettingzoo.API.SettingsService;
import com.example.topcoder.pettingzoo.Fragments.AlertDropdownDialogFragment;
import com.example.topcoder.pettingzoo.Fragments.AlertFragment;
import com.example.topcoder.pettingzoo.Fragments.AlertOptionsDialogFragment;
import com.example.topcoder.pettingzoo.Fragments.EditMadeDialogFragment;
import com.example.topcoder.pettingzoo.Fragments.ProductFragment;
import com.example.topcoder.pettingzoo.Fragments.UserSettingsDialogFragment;
import com.example.topcoder.pettingzoo.R;


public class MainActivity extends AppCompatActivity
        implements OnNavigationItemSelectedListener, ProductFragment.ProductFragmentInterface, View.OnClickListener, AlertFragment.AlertCallback, AlertDropdownDialogFragment.AlertDropdownInterface, AlertOptionsDialogFragment.OptionsSelectedItem {

    private static final int FIXTURE_RESULT = 0;
    // Services
    AlertService alertService;
    ProductService productService;
    SettingsService settingsService;
    // Fragments
    AlertFragment alertFragment;
    ProductFragment productFragment;
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
        alertService.ENDPOINT = getString(R.string.SERVER_IP) + alertService.ENDPOINT;
        productService = new ProductService(requestQueue);
        productService.ENDPOINT = getString(R.string.SERVER_IP) + productService.ENDPOINT;
        settingsService = new SettingsService(requestQueue);
        settingsService.ENDPOINT = getString(R.string.SERVER_IP) + settingsService.ENDPOINT;


        // Instantiate the fragment
        productFragment = ProductFragment.newInstance(productService, this);
        currentFragment = productFragment;

        // Show the ProductFragment
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.fragment_container, productFragment);
        fragmentTransaction.commit();
        ((TextView) findViewById(R.id.title)).setText(R.string.products);

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

        if (id == R.id.alerts) {
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (alertFragment != null && fragmentManager.findFragmentByTag(AlertFragment.class.getName()) != null) {
                fragmentTransaction.remove(alertFragment);
            }
            alertFragment = AlertFragment.newInstance(alertService, this);
            currentFragment = alertFragment;
            fragmentTransaction.add(R.id.fragment_container, alertFragment);
            fragmentTransaction.commit();
            ((TextView) findViewById(R.id.title)).setText(R.string.alerts);
        } else if (id == R.id.products) {
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (productFragment != null && fragmentManager.findFragmentByTag(ProductFragment.class.getName()) != null) {
                fragmentTransaction.remove(productFragment);
            }
            productFragment = ProductFragment.newInstance(productService, this);
            currentFragment = productFragment;
            fragmentTransaction.add(R.id.fragment_container, productFragment);
            fragmentTransaction.commit();
            ((TextView) findViewById(R.id.title)).setText(R.string.products);
        } else if (id == R.id.settings) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFixtureClicked(ProductService.Fixture fixture) {
        Intent intent = new Intent(this, FixtureActivity.class);
        startActivityForResult(intent, FIXTURE_RESULT);
    }

    @Override
    public void onProductMenu(ProductService.Product product) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FIXTURE_RESULT) {
            if (resultCode == 0) {
                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                EditMadeDialogFragment fragment = EditMadeDialogFragment
                        .newInstance(data.getStringExtra("title"));
                fragment.show(fragmentManager, null);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.threeBotsIB) {
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            if (currentFragment.getClass() == ProductFragment.class) {
                UserSettingsDialogFragment settingsDialog = new UserSettingsDialogFragment();
                settingsDialog.show(fragmentManager, null);
            } else if (currentFragment.getClass() == AlertFragment.class) {
                AlertDropdownDialogFragment settingsDialog = AlertDropdownDialogFragment.newInstance(this);
                settingsDialog.show(fragmentManager, null);
            }
        }
    }


    @Override
    public void onAlertOptions(AlertService.Alert alert) {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        AlertOptionsDialogFragment dialog = AlertOptionsDialogFragment
                .newInstance(alert.name + getString(R.string.selected), this);
        dialog.show(fragmentManager, null);
    }

    @Override
    public void onAlertItemClick(AlertService.Alert alert) {
        Intent intent = new Intent(this, AlertItemActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClickConnectedDevices() {
        Intent intent = new Intent(this, ConnectedDevicesActivity.class);
        startActivity(intent);
    }

    @Override
    public void onEditItem() {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        EditMadeDialogFragment fragment = EditMadeDialogFragment.newInstance(getString(R.string.items_updated));
        fragment.show(fragmentManager, null);
    }
}
