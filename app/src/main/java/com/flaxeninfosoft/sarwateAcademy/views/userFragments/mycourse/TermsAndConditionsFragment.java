package com.flaxeninfosoft.sarwateAcademy.views.userFragments.mycourse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentTermsAndConditionsBinding;

public class TermsAndConditionsFragment extends Fragment {

    private FragmentTermsAndConditionsBinding binding;

    public TermsAndConditionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_terms_and_conditions, container, false);
        binding.backImageView.setOnClickListener(this::onClickBack);
        binding.tAndCWebview.loadUrl("https://www.google.com/");

        return binding.getRoot();
    }

    private void onClickBack(View view) {
        Navigation.findNavController(view).navigateUp();
    }
}