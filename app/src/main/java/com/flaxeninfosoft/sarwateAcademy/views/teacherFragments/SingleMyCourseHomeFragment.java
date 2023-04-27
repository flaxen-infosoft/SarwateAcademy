package com.flaxeninfosoft.sarwateAcademy.views.teacherFragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.adapters.SingleMyCourseTeacherPagerAdapter;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentSingleMyCourseHomeBinding;


public class SingleMyCourseHomeFragment extends Fragment {



    public SingleMyCourseHomeFragment() {
        // Required empty public constructor
    }




    FragmentSingleMyCourseHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_single_my_course_home, container, false);
        binding.backImageView.setOnClickListener(this::onClickBack);
        Bundle bundle = new Bundle();
        if (getArguments()!=null) {
            bundle.putLong("id",getArguments().getLong("id"));
            bundle.putString("imageUrl",getArguments().getString("imageUrl"));
            bundle.putString("courseName",getArguments().getString("courseName"));
            bundle.putString("teacherName",getArguments().getString("teacherName"));
            bundle.putString("syllabusPdf",getArguments().getString("syllabusPdf"));
            bundle.putString("description",getArguments().getString("description"));
            bundle.putLong("categoryId",getArguments().getLong("categoryId"));
            bundle.putString("price",getArguments().getString("price"));
            bundle.putString("startDate",getArguments().getString("startDate"));
            bundle.putString("endDate",getArguments().getString("endDate"));
        }
        SingleMyCourseTeacherPagerAdapter adapter = new SingleMyCourseTeacherPagerAdapter(getChildFragmentManager(),bundle);//bundle is for pass data to view pager's fragment
        MyCourseDescriptionFragment descriptionFragment = new MyCourseDescriptionFragment();
        descriptionFragment.setArguments(bundle);
        binding.viewPager.setAdapter(adapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);


        return binding.getRoot();
    }

    private void onClickBack(View view) {
        Navigation.findNavController(view).navigateUp();
    }
}