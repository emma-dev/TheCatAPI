package com.example.catapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;


import com.example.catapi.fragment.HomeFragment;
import com.example.catapi.fragment.LoveFragment;
import com.example.catapi.fragment.SearchFragment;
import com.example.catapi.fragment.UploadFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    //Create Xml View Links
      BottomNavigationView bottomNavigationView;

    //Create Fragment Managers and Transactions
      FragmentManager fragmentManager;
      FragmentTransaction fragmentTransaction;
    //Create Fragments
      HomeFragment homeFragment;
      SearchFragment searchFragment;
      LoveFragment favouritesFragment;
      UploadFragment uploadFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialise Views
        bottomNavigationView = findViewById(R.id.bottom_nav);

        //Initialise Fragments
        homeFragment = new HomeFragment();
        searchFragment = new SearchFragment();
        favouritesFragment = new LoveFragment();
        uploadFragment = new UploadFragment();
        //Initialise Managers and Transactions
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainFrameLayout, homeFragment);
        fragmentTransaction.commit();

        //Bottom Navigation - create onClick Listeners
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.bottombaritem_home:
                                FragmentTransaction fragmentTransactionHome = fragmentManager.beginTransaction();
                                fragmentTransactionHome.replace(R.id.mainFrameLayout, homeFragment);
                                fragmentTransactionHome.commit();
                                return true;

                            case R.id.bottombaritem_camera:
                                FragmentTransaction fragmentTransactionUpl = fragmentManager.beginTransaction();
                                fragmentTransactionUpl.replace(R.id.mainFrameLayout, uploadFragment);
                                fragmentTransactionUpl.commit();
                                return true;

                            case R.id.bottombaritem_search:
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.mainFrameLayout, searchFragment);
                                fragmentTransaction.commit();
                                return true;
                            case R.id.bottombaritem_favourite:
                                FragmentTransaction fragmentTransactionFav = fragmentManager.beginTransaction();
                                fragmentTransactionFav.replace(R.id.mainFrameLayout, favouritesFragment);
                                fragmentTransactionFav.commit();
                                return true;
                        }
                        return false;
                    }
                });
    }
}