package com.flaxeninfosoft.sarwateAcademy.views.authFragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.api.Constants;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentSelectRoleBinding;


public class SelectRoleFragment extends Fragment {

    public static final String STUDENT = "STUDENT";
    public static final String KEY = "ROLE";
    private FragmentSelectRoleBinding binding;

    public SelectRoleFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_select_role, container, false);

        binding.txtStudent.setOnClickListener(this::onClickStudent);
        binding.txtTeacher.setOnClickListener(this::onClickTeacher);

        return binding.getRoot();
    }

    private void onClickTeacher(View view) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY, Constants.ROLE_TEACHER);
        Log.i("role",Constants.ROLE_TEACHER);
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_selectRoleFragment_to_signInFragment,bundle);
    }

    private void onClickStudent(View view) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY,Constants.ROLE_STUDENT);
        Log.i("role",Constants.ROLE_STUDENT);
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_selectRoleFragment_to_signInFragment,bundle);
    }
}