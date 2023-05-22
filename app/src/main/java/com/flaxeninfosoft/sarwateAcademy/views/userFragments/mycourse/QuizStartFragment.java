package com.flaxeninfosoft.sarwateAcademy.views.userFragments.mycourse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.flaxeninfosoft.sarwateAcademy.R;

public class QuizStartFragment extends Fragment {




    public QuizStartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz_start, container, false);
    }
}