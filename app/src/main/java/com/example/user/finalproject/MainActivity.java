package com.example.user.finalproject;

import android.content.res.Resources;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.user.finalproject.Adapters.TabAdapter;
import com.example.user.finalproject.database.DBHelper;
import com.example.user.finalproject.model.News;
import com.example.user.finalproject.model.Product;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements  ActionBar.TabListener{
    private ActionBar actionBar;
    private ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView( R.layout.bar_tabs_activity);

        Resources resources = getResources();

        actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);


//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//
//        getSupportActionBar().setCustomView(R.layout.actionbar);

        viewPager = (ViewPager) findViewById(R.id.pager);
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(5);


        //Add Tabs
        actionBar.addTab(actionBar.newTab().setIcon(resources.getDrawable(R.drawable.products))
                .setTabListener(this));
        actionBar.addTab(actionBar.newTab().setIcon(resources.getDrawable(R.drawable.menu))
                .setTabListener(this));
        actionBar.addTab(actionBar.newTab().setIcon(resources.getDrawable(R.drawable.news))
                .setTabListener(this));
        actionBar.addTab(actionBar.newTab().setIcon(resources.getDrawable(R.drawable.profile))
                .setTabListener(this));
        actionBar.addTab(actionBar.newTab().setIcon(resources.getDrawable(R.drawable.basket))
                .setTabListener(this));


        //Add ViewPager listener for changing tabs
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }
}
