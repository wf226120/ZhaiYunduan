package com.jlgproject.adapter;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import java.util.List;

/**
 * Created by sunbeibei on 2017/5/22.
 */

public class PagerAdapter extends FragmentPagerAdapter {
   private   List<Fragment> fragments;
    private  String[]titles;
    private FragmentManager fm;


    public PagerAdapter(FragmentManager fm, List<Fragment>fragments, String[]titles) {
        super(fm);
        this.titles=titles;
        this.fragments=fragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}

