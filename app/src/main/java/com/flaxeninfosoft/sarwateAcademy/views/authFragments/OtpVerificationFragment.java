package com.flaxeninfosoft.sarwateAcademy.views.authFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentOtpVerificationBinding;


public class OtpVerificationFragment extends Fragment {

    private FragmentOtpVerificationBinding binding;

    public OtpVerificationFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_otp_verification, container, false);

        EditText[] otpArray;
        otpArray = new EditText[6];
        otpArray[0] = binding.otpFirsEditText;
        otpArray[1] = binding.otpSecondEditText;
        otpArray[2] = binding.otpThirdEditText;
        otpArray[3] = binding.otpFourEditText;
        otpArray[4] = binding.otpFiveEditText;
        otpArray[5] = binding.otpSixEditText;
        setOtpEditTextHandler(otpArray);

        binding.continueButton.setOnClickListener(view1 -> {
            if (binding.otpFirsEditText.getText().toString().isEmpty() ||
                    binding.otpSecondEditText.getText().toString().isEmpty() ||
                    binding.otpThirdEditText.getText().toString().isEmpty() ||
                    binding.otpFourEditText.getText().toString().isEmpty() ||
                    binding.otpFiveEditText.getText().toString().isEmpty() ||
                    binding.otpSixEditText.getText().toString().isEmpty()){

                Toast.makeText(getContext(), "Otp Required", Toast.LENGTH_SHORT).show();
            }
            else {

            }
        });
        return binding.getRoot();
    }


    private void setOtpEditTextHandler(EditText[] otpEditText) {
        for (int i = 0; i < otpEditText.length; i++) { //Its designed for 6 digit OTP
            final int iVal = i;

            otpEditText[iVal].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (iVal != otpEditText.length - 1 && !otpEditText[iVal].getText().toString().isEmpty()) {
                        otpEditText[iVal + 1].requestFocus(); //focuses on the next edittext after a digit is entered.
                    }
                }
            });

            otpEditText[iVal].setOnKeyListener((v, keyCode, event) -> {
                if (event.getAction() != KeyEvent.ACTION_DOWN) {
                    return false; //Don't get confused by this, it is because onKeyListener is called twice and this condition is to avoid it.
                }
                if (keyCode == KeyEvent.KEYCODE_DEL &&
                        otpEditText[iVal].getText().toString().isEmpty() && iVal != 0) {
                    //this condition is to handel the delete input by users.
                    otpEditText[iVal - 1].setText("");//Deletes the digit of OTP
                    otpEditText[iVal - 1].requestFocus();//and sets the focus on previous digit
                }
                return false;
            });
        }
    }
}