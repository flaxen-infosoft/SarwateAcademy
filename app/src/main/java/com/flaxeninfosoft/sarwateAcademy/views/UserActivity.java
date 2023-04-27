package com.flaxeninfosoft.sarwateAcademy.views;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.databinding.ActivityUserBinding;
import com.google.android.material.navigation.NavigationView;

public class UserActivity extends AppCompatActivity {

    private ActivityUserBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user);

        NavHostFragment hostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.user_activity_host_fragment);

        navController = hostFragment.getNavController();


    }


}