package com.flaxeninfosoft.sarwateAcademy.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.flaxeninfosoft.sarwateAcademy.views.teacherFragments.MyCourseDescriptionFragment;
import com.flaxeninfosoft.sarwateAcademy.views.userFragments.CourseDescriptionFragment;
import com.flaxeninfosoft.sarwateAcademy.views.userFragments.CourseNotesFragment;
import com.flaxeninfosoft.sarwateAcademy.views.userFragments.CourseVideosFragment;

public class SingleBatchViewPagerAdapter extends FragmentPagerAdapter {
    Bundle bundle;
    public SingleBatchViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        this.bundle = bundle;
    }

    public SingleBatchViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new CourseDescriptionFragment();
        }
        else if (position == 1) {
            return new CourseVideosFragment();
        }
        else {
            return new CourseNotesFragment();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        if (position == 0){
            return "Description";
        }
        else if (position == 1){
            return "Videos";
        }
        else {
            return "Notes";
        }

    }

    @Override
    public int getCount() {
        return 3;
    }
}
