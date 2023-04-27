package com.flaxeninfosoft.sarwateAcademy.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.databinding.ActivityAuthenticationBinding;

public class AuthenticationActivity extends AppCompatActivity {


    private ActivityAuthenticationBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_authentication);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
    }

}