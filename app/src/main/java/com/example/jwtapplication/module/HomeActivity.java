package com.example.jwtapplication.module;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSON;
import com.example.jwtapplication.R;
import com.example.jwtapplication.dao.LoginDao;
import com.example.jwtapplication.dao.Token;
import com.example.jwtapplication.util.OkHttpUtils;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {


    TabLayout tabLayout;
    ViewPager viewPager;

    private String[] mMoudleName = {"Home", "Dashboard", "Notifications"};

    ArrayList<String> mName = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewPager=findViewById(R.id.viewpager_content_view);
        tabLayout=findViewById(R.id.tab_layout_view);
        //tabLayout.setTabTextColors(R.color.snow);
        tabLayout.setTabTextColors(R.color.t4,R.color.b1);
        viewPager.setAdapter(new CustomAdapter(getSupportFragmentManager(),getApplicationContext()));
        tabLayout.setupWithViewPager(viewPager);

    }
    private class CustomAdapter extends FragmentPagerAdapter {
        private String fragments [] ={"Home", "Dashboard", "Notifications"};

        public CustomAdapter(FragmentManager supportFragmentManager, Context applicationContext) {
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new Fragment1();
                case 1:
                    return new Fragment2();
                case 2:
                    return new Fragment1();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragments[position];
        }

    }


}