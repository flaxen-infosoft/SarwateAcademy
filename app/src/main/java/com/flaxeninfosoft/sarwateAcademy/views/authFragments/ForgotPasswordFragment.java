package com.flaxeninfosoft.sarwateAcademy.views.authFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentForgotPasswordBinding;


public class ForgotPasswordFragment extends Fragment {

    private FragmentForgotPasswordBinding binding;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forgot_password, container, false);

        binding.continueButton.setOnClickListener(view1 -> {
            if (binding.mobileNumTextInput.getText().toString().isEmpty()) {
                binding.mobileNumTextInput.setError("Mobile Number Required");
                binding.mobileNumTextInput.requestFocus();
            } else if (binding.mobileNumTextInput.getText().toString().length() != 10) {
                binding.mobileNumTextInput.setError("10 Digits Required");
                binding.mobileNumTextInput.requestFocus();
            } else {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_forgotPasswordFragment_to_otpVerificationFragment);
            }

        });

        return binding.getRoot();
    }

}