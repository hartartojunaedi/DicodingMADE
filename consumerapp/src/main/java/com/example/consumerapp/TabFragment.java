package com.example.consumerapp;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment extends FragmentPagerAdapter {

    private final Context mContext;
    public TabFragment(Context context, FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
    }
    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.tab_text_3,
            R.string.tab_text_4
    };
    @Override
    public int getCount() {
        return 2;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FavoriteMovie();
                break;
            case 1:
                fragment = new TVShowFavorite();
                break;
        }
        return fragment;
    }
}
