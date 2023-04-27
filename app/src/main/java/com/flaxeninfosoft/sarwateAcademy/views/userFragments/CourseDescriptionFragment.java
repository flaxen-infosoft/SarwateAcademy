package com.flaxeninfosoft.sarwateAcademy.views.userFragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flaxeninfosoft.sarwateAcademy.R;

import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentBatchDescriptionBinding;
import com.flaxeninfosoft.sarwateAcademy.models.Course;
import com.squareup.picasso.Picasso;

import io.paperdb.Paper;


public class CourseDescriptionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match


    public CourseDescriptionFragment() {
        // Required empty public constructor
    }


    FragmentBatchDescriptionBinding binding;
    Course course;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        course = Paper.book().read("Current_Course");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_batch_description, container, false);
        course = Paper.book().read("Current_Course");
        binding.buyNowButton.setOnClickListener(this::onClickBuyNow);
        binding.setCourse(course);
        Picasso.get().load("http://103.118.17.202/~mkeducation/MK_API/User/"+course.getImageUrl()).placeholder(R.drawable.sarwate_logo).into(binding.courseImageView);



        return binding.getRoot();
    }

    private void onClickBuyNow(View view) {
        Navigation.findNavController(view).navigate(R.id.action_batchHomeFragment_to_buyCourseFragment);
    }
}