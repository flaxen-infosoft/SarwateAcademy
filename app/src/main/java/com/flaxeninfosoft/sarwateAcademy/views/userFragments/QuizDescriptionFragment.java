package com.flaxeninfosoft.sarwateAcademy.views.userFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentQuizDescriptionBinding;
import com.flaxeninfosoft.sarwateAcademy.models.Quiz;
import com.squareup.picasso.Picasso;

import io.paperdb.Paper;


public class QuizDescriptionFragment extends Fragment {

    FragmentQuizDescriptionBinding binding;

    Quiz quiz;

    public QuizDescriptionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        quiz = Paper.book().read("Current_Quiz");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz_description, container, false);
        quiz = Paper.book().read("Current_Quiz");
       binding.buyNowButton.setOnClickListener(this::onClickBuyNow);
        binding.setQuiz(quiz);
        Picasso.get().load("http://103.118.17.202/~mkeducation/MK_API/User/"+quiz.getBanner()).placeholder(R.drawable.sarwate_logo).into(binding.courseImageView);

        return binding.getRoot();
    }

    private void onClickBuyNow(View view) {
        Navigation.findNavController(view).navigate(R.id.action_quizDescriptionFragment_to_buyQuizFragment);
    }



}