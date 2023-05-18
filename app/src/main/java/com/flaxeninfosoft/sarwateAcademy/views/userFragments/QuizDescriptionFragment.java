package com.flaxeninfosoft.sarwateAcademy.views.userFragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.adapters.SingleBatchViewPagerAdapter;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentBatchHomeBinding;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentQuizDescriptionBinding;
import com.flaxeninfosoft.sarwateAcademy.models.Course;

import io.paperdb.Paper;


public class QuizDescriptionFragment extends Fragment {

    FragmentQuizDescriptionBinding binding;

    public QuizDescriptionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_quiz_description,container,false);

        binding.buyNowButton.setOnClickListener(this::buynow);
        binding.backImageView.setOnClickListener(this::clickback);




        return binding.getRoot();
    }

    private void clickback(View view) {
        Navigation.findNavController(view).navigateUp();
    }

    private void buynow(View view) {
        Navigation.findNavController(view).navigate(R.id.choosePaymentFragment);
    }

}