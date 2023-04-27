package com.flaxeninfosoft.sarwateAcademy.views.teacherFragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentMyCourseDescriptionBinding;
import com.flaxeninfosoft.sarwateAcademy.models.Course;

import io.paperdb.Paper;


public class MyCourseDescriptionFragment extends Fragment {



    public MyCourseDescriptionFragment() {
        // Required empty public constructor
    }

    FragmentMyCourseDescriptionBinding binding;
    Course course;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_my_course_description, container, false);
        course = Paper.book().read("CurrentMyCourse");
        binding.editFloatButton.setOnClickListener(view -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_singleMyCourseHomeFragment_to_editMyCourseDescriptionFragment);
        });


        binding.setCourse(course);




        return binding.getRoot();
    }


}