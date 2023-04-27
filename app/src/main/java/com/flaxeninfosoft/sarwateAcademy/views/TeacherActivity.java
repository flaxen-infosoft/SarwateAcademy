package com.flaxeninfosoft.sarwateAcademy.views;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.databinding.ActivityTeacherBinding;

public class TeacherActivity extends AppCompatActivity {

    private ActivityTeacherBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_teacher);

        NavHostFragment hostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.teacher_activity_host_fragment);

        navController = hostFragment.getNavController();

    }
}