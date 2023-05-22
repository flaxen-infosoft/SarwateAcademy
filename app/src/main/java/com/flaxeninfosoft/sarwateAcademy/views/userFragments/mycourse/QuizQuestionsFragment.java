package com.flaxeninfosoft.sarwateAcademy.views.userFragments.mycourse;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.adapters.QuizQuestionsAdapter;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentQuizQuestionsBinding;
import com.flaxeninfosoft.sarwateAcademy.models.QuizQuestions;
import com.google.gson.Gson;

import java.util.List;

public class QuizQuestionsFragment extends Fragment {


    public QuizQuestionsFragment() {
        // Required empty public constructor
    }

    FragmentQuizQuestionsBinding binding;
    QuizQuestionsAdapter adapter;
    List<QuizQuestions> quizQuestions;
    RequestQueue requestQueue;
    Gson gson;
    Uri image;
    ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz_questions, container, false);
        return binding.getRoot();
    }
}