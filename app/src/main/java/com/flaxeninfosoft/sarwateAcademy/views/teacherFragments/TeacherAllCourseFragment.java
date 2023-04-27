package com.flaxeninfosoft.sarwateAcademy.views.teacherFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.adapters.AllLiveSessionForTeacherRecyclerAdapter;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentTeacherAllCoursesBinding;
import com.flaxeninfosoft.sarwateAcademy.models.Course;

import java.util.ArrayList;
import java.util.List;

public class TeacherAllCourseFragment extends Fragment {

    private FragmentTeacherAllCoursesBinding binding;
    private AllLiveSessionForTeacherRecyclerAdapter.LiveSessionBatchCardClickListener liveSessionBatchCardClickListener;
    private AllLiveSessionForTeacherRecyclerAdapter adapter;

    public TeacherAllCourseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    List<Course> courseList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_teacher_all_courses, container, false);

        binding.liveBatchesTeacherRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.backImageView.setOnClickListener(this::onClickBack);
        courseList = new ArrayList<>();
        courseList.add(new Course("https://picsum.photos/400/250.jpg", "teacher name", "Course 1", "999"));
        courseList.add(new Course("https://picsum.photos/400/251.jpg", "akash mehta", "Hindi", "199"));
        courseList.add(new Course("https://picsum.photos/400/252.jpg", "Teacher 3", "Course 3", "2000"));
        courseList.add(new Course("https://picsum.photos/400/253.jpg", "anjali sharma 4", "Course 4", "2999"));
        courseList.add(new Course("https://picsum.photos/400/254.jpg", "narendra kumar", "History", "382"));
        courseList.add(new Course("https://picsum.photos/400/255.jpg", "Teacher 6", "Geography", "124"));
        courseList.add(new Course("https://picsum.photos/400/256.jpg", "anil sharma", "Course 7", "544"));
        courseList.add(new Course("https://picsum.photos/400/257.jpg", "Teacher 8", "civics", "600"));
        courseList.add(new Course("https://picsum.photos/400/258.jpg", "mayur sing", "Course 9", "99"));
        courseList.add(new Course("https://picsum.photos/400/259.jpg", "Teacher 10", "Theory of computation", "890"));
        courseList.add(new Course("https://picsum.photos/400/260.jpg", "Harsh verma", "Internet of things", "7789"));
        courseList.add(new Course("https://picsum.photos/400/261.jpg", "amit sir", "Course 12", "638"));
        courseList.add(new Course("https://picsum.photos/400/250.jpg", "ram singh", "maths", "287"));
        courseList.add(new Course("https://picsum.photos/400/251.jpg", "akash ji", "physics", "9834"));
        courseList.add(new Course("https://picsum.photos/400/252.jpg", "rahul sahu", "biology", "3983"));
        courseList.add(new Course("https://picsum.photos/400/253.jpg", "vishal mehta", "computer network", "388"));
        courseList.add(new Course("https://picsum.photos/400/254.jpg", "teacher", "Theoru of computation", "3883"));
        courseList.add(new Course("https://picsum.photos/400/255.jpg", "rohan ", "Machine learning", "3838"));
        courseList.add(new Course("https://picsum.photos/400/256.jpg", "aniket", "Project management", "2627"));
        courseList.add(new Course("https://picsum.photos/400/257.jpg", "akshat", "chemistry", "8433"));
        courseList.add(new Course("https://picsum.photos/400/258.jpg", "jeevan", "Web design", "34243"));
        courseList.add(new Course("https://picsum.photos/400/259.jpg", "teacher ", "Course 10", "883"));
        courseList.add(new Course("https://picsum.photos/400/260.jpg", "rahul ", "commerce", "899"));
        courseList.add(new Course("https://picsum.photos/400/261.jpg", "piyush", "General Knowledge", "2500"));

        adapter = new AllLiveSessionForTeacherRecyclerAdapter(courseList, this::onClickLiveSession);
        if (courseList.isEmpty()) {
            binding.noVideoFoundLayout.setVisibility(View.VISIBLE);
            binding.liveBatchesTeacherRecycler.setVisibility(View.GONE);
        } else {
            binding.liveBatchesTeacherRecycler.setAdapter(adapter);
            binding.noVideoFoundLayout.setVisibility(View.GONE);
            binding.liveBatchesTeacherRecycler.setVisibility(View.VISIBLE);
        }

        binding.searchBarEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable != null) {
                    filter(editable.toString());
                }
            }
        });
        return binding.getRoot();
    }

    private void onClickBack(View view) {
        Navigation.findNavController(view).navigateUp();
    }

    private void filter(String text) {
        ArrayList<Course> courseArrayList = new ArrayList<>();
        for (Course course : courseList) {
            if (course.getCourseName().toLowerCase().trim().contains(text.toLowerCase())
                    || course.getTeacherName().toLowerCase().trim().contains(text.toLowerCase())
                    || course.getPrice().toLowerCase().trim().contains(text.toLowerCase())) {
                courseArrayList.add(course);
            }
        }
        if (courseArrayList.isEmpty()) {
            binding.noVideoFoundLayout.setVisibility(View.VISIBLE);
            binding.liveBatchesTeacherRecycler.setVisibility(View.GONE);
        } else {
            adapter.filterList(courseArrayList);
            binding.noVideoFoundLayout.setVisibility(View.GONE);
            binding.liveBatchesTeacherRecycler.setVisibility(View.VISIBLE);
        }

    }

    private void onClickLiveSession(Course course) {
        Toast.makeText(getContext(), course.getCourseName(), Toast.LENGTH_SHORT).show();
    }
}