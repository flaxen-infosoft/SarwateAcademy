package com.flaxeninfosoft.sarwateAcademy.views.userFragments.mycourse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.adapters.CourseNotesAdapter;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentStudyMaterialBinding;
import com.flaxeninfosoft.sarwateAcademy.models.StudyMaterial;

import java.util.ArrayList;
import java.util.List;

public class StudyMaterialFragment extends Fragment {

    public StudyMaterialFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    FragmentStudyMaterialBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_study_material, container, false);
        binding.downloadNotesRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<StudyMaterial> studyMaterialList = new ArrayList<>();
        studyMaterialList.add(new StudyMaterial("Course 1"));
        studyMaterialList.add(new StudyMaterial("Course 2"));
        studyMaterialList.add(new StudyMaterial("Course 3"));
        studyMaterialList.add(new StudyMaterial("Course 4"));
        studyMaterialList.add(new StudyMaterial("Course 5"));
        studyMaterialList.add(new StudyMaterial("Course 6"));
        studyMaterialList.add(new StudyMaterial("Course 7"));
        studyMaterialList.add(new StudyMaterial("Course 8"));
        studyMaterialList.add(new StudyMaterial("Course 9"));
        studyMaterialList.add(new StudyMaterial("Course 10"));
        studyMaterialList.add(new StudyMaterial("Course 11"));
        studyMaterialList.add(new StudyMaterial("Course 12"));

        CourseNotesAdapter adapter = new CourseNotesAdapter(studyMaterialList,this::onClickNotes);
        if (studyMaterialList.isEmpty()) {
            binding.noVideoFoundLayout.setVisibility(View.VISIBLE);
            binding.downloadNotesRecycler.setVisibility(View.GONE);
        } else {
            binding.downloadNotesRecycler.setAdapter(adapter);
            binding.downloadNotesRecycler.setVisibility(View.GONE);
            binding.downloadNotesRecycler.setVisibility(View.VISIBLE);
        }
        return binding.getRoot();
    }

    private void onClickNotes(StudyMaterial studyMaterial) {


    }
}