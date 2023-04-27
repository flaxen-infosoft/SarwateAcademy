package com.flaxeninfosoft.sarwateAcademy.views.teacherFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentTeacherNoteOverviewBinding;
import com.flaxeninfosoft.sarwateAcademy.models.StudyMaterial;

public class TeacherNoteOverviewFragment extends Fragment {

    private FragmentTeacherNoteOverviewBinding binding;

    public TeacherNoteOverviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_teacher_note_overview, container, false);
        String fileName = getArguments().getString("fileName","File Name NA");
        StudyMaterial studyMaterial = new StudyMaterial();
        studyMaterial.setFileName(fileName);
        binding.setStudyMaterial(studyMaterial);
        binding.backImageView.setOnClickListener(this::onClickBack);

        return binding.getRoot();
    }

    private void onClickBack(View view) {
        Navigation.findNavController(view).navigateUp();
    }
}