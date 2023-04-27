package com.flaxeninfosoft.sarwateAcademy.views.userFragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.adapters.SingleBatchViewPagerAdapter;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentBatchHomeBinding;
import com.flaxeninfosoft.sarwateAcademy.models.Course;

import io.paperdb.Paper;


public class CourseHomeFragment extends Fragment {



    public CourseHomeFragment() {
        // Required empty public constructor
    }

    Course course;
    FragmentBatchHomeBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_batch_home,container,false);
        binding.backImageView.setOnClickListener(this::onClickBack);
        FragmentManager fragmentManager = getChildFragmentManager();
        course = Paper.book().read("Current_Course");
        SingleBatchViewPagerAdapter viewPagerAdapter = new SingleBatchViewPagerAdapter(fragmentManager);
        binding.viewPager.setAdapter(viewPagerAdapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);


        return binding.getRoot();
    }

    private void onClickBack(View view) {
        Navigation.findNavController(view).navigateUp();
    }
}