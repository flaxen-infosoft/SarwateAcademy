package com.flaxeninfosoft.sarwateAcademy.views.userFragments.mycourse;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.adapters.MyCourseViewPagerAdapter;
import com.flaxeninfosoft.sarwateAcademy.adapters.SingleBatchViewPagerAdapter;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentBatchHomeBinding;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentMyCoursePurchasedHomeBinding;
import com.flaxeninfosoft.sarwateAcademy.models.Course;

import io.paperdb.Paper;


public class MyCoursePurchasedHomeFragment extends Fragment {


    Course course;
    FragmentMyCoursePurchasedHomeBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_my_course_purchased_home,container,false);
        binding.backImageView.setOnClickListener(this::onClickBack);
        FragmentManager fragmentManager = getChildFragmentManager();
        course = Paper.book().read("My_Current_Course");
        MyCourseViewPagerAdapter viewPagerAdapter = new MyCourseViewPagerAdapter(fragmentManager);
        binding.viewPager.setAdapter(viewPagerAdapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);


        return binding.getRoot();
    }

    private void onClickBack(View view) {
        Navigation.findNavController(view).navigateUp();
    }
}