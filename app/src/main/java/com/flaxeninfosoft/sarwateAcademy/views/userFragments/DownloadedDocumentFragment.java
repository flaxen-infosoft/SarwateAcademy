package com.flaxeninfosoft.sarwateAcademy.views.userFragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.adapters.AllNotesForTeacherRecyclerAdapter;
import com.flaxeninfosoft.sarwateAcademy.adapters.DownloadedNotesForStudentRecyclerAdapter;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentDownloadedDocumentBinding;
import com.flaxeninfosoft.sarwateAcademy.models.StudyMaterial;

import java.util.ArrayList;
import java.util.List;


public class DownloadedDocumentFragment extends Fragment {





    public DownloadedDocumentFragment() {
        // Required empty public constructor
    }


    FragmentDownloadedDocumentBinding binding;
    DownloadedNotesForStudentRecyclerAdapter adapter;
    List<StudyMaterial> studyMaterialList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_downloaded_document, container, false);
        binding.downloadDocumentRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
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

        adapter = new DownloadedNotesForStudentRecyclerAdapter(studyMaterialList, this::onNotesCardClick);
        if (studyMaterialList.isEmpty()) {
            binding.noDocumentFoundLayout.setVisibility(View.VISIBLE);
            binding.downloadDocumentRecycler.setVisibility(View.GONE);
        } else {
            binding.downloadDocumentRecycler.setAdapter(adapter);
            binding.noDocumentFoundLayout.setVisibility(View.GONE);
            binding.downloadDocumentRecycler.setVisibility(View.VISIBLE);
        }

        return binding.getRoot();
    }

    private void onNotesCardClick(StudyMaterial studyMaterial) {
        Toast.makeText(getContext(),studyMaterial.getFileName(), Toast.LENGTH_SHORT).show();
    }
}