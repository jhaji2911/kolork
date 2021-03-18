package com.app.kolork.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.app.kolork.fragment.ColorDetectionFragment;




public class DashboardPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    public DashboardPagerAdapter(@NonNull FragmentManager fm,Context context) {
        super(fm);
        this.context=context;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            //Color Detection
            case 0:
                return ColorDetectionFragment.newInstance();
            default:
                return ColorDetectionFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 1;
    }
}
