package com.example.newsfeed;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.newsfeed.FragManager.FragAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frag);


        ViewPager viewpager = (ViewPager) findViewById(R.id.viewpager);
        FragAdapter adapter = new FragAdapter(this, getSupportFragmentManager());

        viewpager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewpager);


   }


}