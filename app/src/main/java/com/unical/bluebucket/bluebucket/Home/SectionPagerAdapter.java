package com.unical.bluebucket.bluebucket.Home;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SectionPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "SectionPagerAdapter";
    private final List<Fragment> mFragList = new ArrayList<>();

    public SectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragList.get(position);
    }

    @Override
    public int getCount() {
        return mFragList.size();
    }



    public void addFrag(Fragment frag){
        mFragList.add(frag);
    }
}
