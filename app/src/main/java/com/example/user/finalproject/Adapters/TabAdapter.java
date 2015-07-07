package com.example.user.finalproject.Adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.user.finalproject.Fragments.Basket_Fragment;
import com.example.user.finalproject.Fragments.Menu_Fragment;
import com.example.user.finalproject.Fragments.News_Fragment;
import com.example.user.finalproject.Fragments.Product_Fragment;
import com.example.user.finalproject.Fragments.Profile_Fragment;



public class TabAdapter extends FragmentPagerAdapter {

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                return new Product_Fragment();
            case 1:
                return new Menu_Fragment();
            case 2:
                return new News_Fragment();
            case 3:
                return new Profile_Fragment();
            case 4:
                return new Basket_Fragment();
            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return 5;
    }
}
