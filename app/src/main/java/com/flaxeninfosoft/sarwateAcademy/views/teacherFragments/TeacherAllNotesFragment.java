package com.flaxeninfosoft.sarwateAcademy.views.teacherFragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.adapters.AllNotesForTeacherRecyclerAdapter;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentTeacherAllNotesBinding;
import com.flaxeninfosoft.sarwateAcademy.models.StudyMaterial;

import java.util.ArrayList;
import java.util.List;

public class TeacherAllNotesFragment extends Fragment {

    private FragmentTeacherAllNotesBinding binding;
    List<StudyMaterial> studyMaterialList;
    AllNotesForTeacherRecyclerAdapter adapter;

    public TeacherAllNotesFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_teacher_all_notes, container, false);
        binding.allNotesTeacherRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.backImageView.setOnClickListener(this::onClickBack);
        studyMaterialList = new ArrayList<>();
        studyMaterialList.add(new StudyMaterial("History notes"));
        studyMaterialList.add(new StudyMaterial("Geography notes 2"));
        studyMaterialList.add(new StudyMaterial("Artificial Intelligence notes"));
        studyMaterialList.add(new StudyMaterial("Physics Unit 5 notes"));
        studyMaterialList.add(new StudyMaterial("Chemistry unit 2 reactions notes"));
        studyMaterialList.add(new StudyMaterial("Economics notes"));
        studyMaterialList.add(new StudyMaterial("Android Development notes"));
        studyMaterialList.add(new StudyMaterial("Java notes"));
        studyMaterialList.add(new StudyMaterial("Computer Networks notes"));
        studyMaterialList.add(new StudyMaterial("Machine Learning notes"));
        studyMaterialList.add(new StudyMaterial("Notes 2 notes"));
        studyMaterialList.add(new StudyMaterial("Science notes"));
        studyMaterialList.add(new StudyMaterial("History notes"));
        studyMaterialList.add(new StudyMaterial("Geography notes 2"));
        studyMaterialList.add(new StudyMaterial("Artificial Intelligence notes"));
        studyMaterialList.add(new StudyMaterial("Physics Unit 5 notes"));
        studyMaterialList.add(new StudyMaterial("Chemistry unit 2 reactions notes"));
        studyMaterialList.add(new StudyMaterial("Economics notes"));
        studyMaterialList.add(new StudyMaterial("Android Development notes"));
        studyMaterialList.add(new StudyMaterial("Java notes"));

        adapter = new AllNotesForTeacherRecyclerAdapter(studyMaterialList, this::onNotesCardClick);
        if (studyMaterialList.isEmpty()) {
            binding.noDataFoundLayout.setVisibility(View.VISIBLE);
            binding.allNotesTeacherRecycler.setVisibility(View.GONE);
        } else {
            binding.allNotesTeacherRecycler.setAdapter(adapter);
            binding.noDataFoundLayout.setVisibility(View.GONE);
            binding.allNotesTeacherRecycler.setVisibility(View.VISIBLE);
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
        ArrayList<StudyMaterial> studyMaterialArrayList = new ArrayList<>();

        for (StudyMaterial studyMaterial : studyMaterialList) {
            if (studyMaterial.getFileName().toLowerCase().contains(text.toLowerCase())) {
                studyMaterialArrayList.add(studyMaterial);
            }
        }

        if (studyMaterialArrayList.isEmpty()) {
            binding.noDataFoundLayout.setVisibility(View.VISIBLE);
            binding.allNotesTeacherRecycler.setVisibility(View.GONE);
        } else {
            adapter.filterList(studyMaterialArrayList);
            binding.noDataFoundLayout.setVisibility(View.GONE);
            binding.allNotesTeacherRecycler.setVisibility(View.VISIBLE);
        }
    }

    private void onNotesCardClick(StudyMaterial studyMaterial) {
        Bundle bundle = new Bundle();
        bundle.putString("fileName", studyMaterial.getFileName());
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_teacherAllNotesFragment_to_teacherNoteOverviewFragment, bundle);
    }
}