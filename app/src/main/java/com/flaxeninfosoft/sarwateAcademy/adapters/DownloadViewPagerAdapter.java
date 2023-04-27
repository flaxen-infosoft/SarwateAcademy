package com.flaxeninfosoft.sarwateAcademy.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.flaxeninfosoft.sarwateAcademy.views.userFragments.DownloadedDocumentFragment;
import com.flaxeninfosoft.sarwateAcademy.views.userFragments.DownloadedVideoFragment;

public class DownloadViewPagerAdapter extends FragmentPagerAdapter {
    public DownloadViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            return new DownloadedVideoFragment();
        } else
            return new DownloadedDocumentFragment();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        if (position == 0){
            return "Video";
        }
        else {
            return "Notes";
        }

    }


    @Override
    public int getCount() {
        return 2;
    }


}