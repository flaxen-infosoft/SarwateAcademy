package com.flaxeninfosoft.sarwateAcademy.views.userFragments.mycourse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentChoosePaymentBinding;
import com.flaxeninfosoft.sarwateAcademy.models.CoursePurchaseRequest;
import com.squareup.picasso.Picasso;

import io.paperdb.Paper;


public class ChoosePaymentFragment extends Fragment {


    public ChoosePaymentFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentChoosePaymentBinding binding;
    CoursePurchaseRequest coursePurchaseRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_choose_payment, container, false);
        coursePurchaseRequest = Paper.book().read("CoursePurchaseRequest");
        binding.backImageView.setOnClickListener(this::onClickBack);
        binding.upiIdTextView.setText(coursePurchaseRequest.getUPI());
        Picasso.get().load("http://103.118.17.202/~mkeducation/MK_API/User/" +
                coursePurchaseRequest.getBarcode_image()).placeholder(R.drawable.sarwate_logo).into(binding.qrCodeImageview);


        return binding.getRoot();
    }

    private void onClickBack(View view) {
        Navigation.findNavController(view).navigateUp();
    }
}