package com.example.ramannada.mymiwok;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by ramannada on 10/27/2017.
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    Context mContext;
    public FragmentAdapter(Context c, FragmentManager fm) {
        super(fm);
        mContext = c;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new NumberFragment();
            case 1:
                return new FamilyFragment();
            case 2:
                return new ColorFragment();
            case 3:
                return new PhraseFragment();
            default:
                return new NumberFragment();

        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.number);
            case 1:
                return mContext.getString(R.string.family_member);
            case 2:
                return mContext.getString(R.string.color);
            case 3:
                return mContext.getString(R.string.phrase);
            default:
                return mContext.getString(R.string.number);
        }
    }
}
