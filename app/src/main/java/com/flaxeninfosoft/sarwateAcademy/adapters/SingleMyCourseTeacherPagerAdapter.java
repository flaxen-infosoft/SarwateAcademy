package com.flaxeninfosoft.sarwateAcademy.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.flaxeninfosoft.sarwateAcademy.views.teacherFragments.MyCourseDescriptionFragment;
import com.flaxeninfosoft.sarwateAcademy.views.teacherFragments.MyCourseNotesFragment;
import com.flaxeninfosoft.sarwateAcademy.views.teacherFragments.MyCourseVideoFragment;

public class SingleMyCourseTeacherPagerAdapter extends FragmentPagerAdapter {

    Bundle bundle;
    public SingleMyCourseTeacherPagerAdapter(@NonNull FragmentManager fm,Bundle bundle) {
        super(fm);

        this.bundle = bundle;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
//            MyCourseFragment -->  SingleMyCourseHomeFragment ---> MyCourseDescriptionFragment
            MyCourseDescriptionFragment descriptionFragment = new MyCourseDescriptionFragment();
            descriptionFragment.setArguments(bundle);
            return descriptionFragment;
        }
        else if (position == 1) {
            return new MyCourseVideoFragment();
        }
        else {
            return new MyCourseNotesFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
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
}
