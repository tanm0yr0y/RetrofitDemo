package com.example.royta.retrofitdemo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import com.example.royta.retrofitdemo.R;
import com.example.royta.retrofitdemo.Storage.SharedPrefManager;
import com.example.royta.retrofitdemo.fragments.HomeFragments;
import com.example.royta.retrofitdemo.fragments.SettingFragments;
import com.example.royta.retrofitdemo.fragments.UsersFragments;

public class ProfileActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation_view);
        navigationView.setOnNavigationItemSelectedListener(this);

        displayFragment(new HomeFragments());
    }

    private void displayFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.relative_layout, fragment).commit();
    }

    protected void onStart() {
        super.onStart();

        if(!SharedPrefManager.getSharedPrefManager(this).isLoggedIn()) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Fragment fragment = null;

        if(menuItem.getItemId() == R.id.menu_home) {
            fragment = new HomeFragments();
        }
        else if(menuItem.getItemId() == R.id.menu_users) {
            fragment = new UsersFragments();
        }
        else if(menuItem.getItemId() == R.id.menu_settings) {
            fragment = new SettingFragments();
        }

        if(fragment != null) {
            displayFragment(fragment);
        }
        return false;
    }
}
