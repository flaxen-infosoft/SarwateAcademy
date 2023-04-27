package com.flaxeninfosoft.sarwateAcademy.views.userFragments.mycourse;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentBatchDescriptionBinding;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentMyCourseDescription2Binding;
import com.flaxeninfosoft.sarwateAcademy.models.Course;
import com.squareup.picasso.Picasso;

import io.paperdb.Paper;


public class MyCourseDescriptionFragment extends Fragment {


    FragmentMyCourseDescription2Binding binding;
    Course course;
    public MyCourseDescriptionFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_course_description2, container, false);
        course = Paper.book().read("My_Current_Course");
        binding.setCourse(course);
        Picasso.get().load("http://103.118.17.202/~mkeducation/MK_API/User/"+course.getImageUrl()).placeholder(R.drawable.sarwate_logo).into(binding.courseImageView);



        return binding.getRoot();
    }
}