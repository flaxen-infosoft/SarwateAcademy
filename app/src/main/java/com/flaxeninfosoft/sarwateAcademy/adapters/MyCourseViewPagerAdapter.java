package com.flaxeninfosoft.sarwateAcademy.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.flaxeninfosoft.sarwateAcademy.views.userFragments.mycourse.MyCourseDescriptionFragment;
import com.flaxeninfosoft.sarwateAcademy.views.userFragments.mycourse.MyCourseNotesFragment;
import com.flaxeninfosoft.sarwateAcademy.views.userFragments.mycourse.MyCourseVideoFragment;

public class MyCourseViewPagerAdapter extends FragmentPagerAdapter {
    public MyCourseViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public MyCourseViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new MyCourseDescriptionFragment();
        } else if (position == 1) {
            return new MyCourseVideoFragment();

        } else {
            return new MyCourseNotesFragment();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        if (position == 0) {
            return "Description";
        } else if (position == 1) {
            return "Videos";
        } else {
            return "Notes";
        }

    }

    @Override
    public int getCount() {
        return 3;
    }
}
