package com.techouts.pcomplaints.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.techouts.pcomplaints.fragments.ExServiceManListFragment;

/**
 * Created by AshokaReddy on 3/5/2018.
 */

public class ExServiceManPagerAdapter extends FragmentPagerAdapter {


    public ExServiceManPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return ExServiceManListFragment.getInstance(0);
        } else if (position == 1){
            return ExServiceManListFragment.getInstance(1);
        } else
            return ExServiceManListFragment.getInstance(-1);
    }

    // This determines the number of tabs
    @Override
    public int getCount() {
        return 3;
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return "Pending";
            case 1:
                return "Approved";
            case 2:
                return "Rejected";
            default:
                return null;
        }
    }

}