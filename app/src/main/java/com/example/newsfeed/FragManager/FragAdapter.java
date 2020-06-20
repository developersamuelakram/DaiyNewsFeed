package com.example.newsfeed.FragManager;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.newsfeed.R;

public class FragAdapter extends FragmentPagerAdapter {

    private Context mContext;


    public FragAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;

    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            return new PoliticsFrag();
        } else if (i==1) {
            return new SportsFrag();
        } else {
            return new TechFrag();
        }


    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.politics_frag);
        } else if (position == 1) {
            return mContext.getString(R.string.sports_frag);
        } else {
            return mContext.getString(R.string.tech_frag);
        }
    }
}



