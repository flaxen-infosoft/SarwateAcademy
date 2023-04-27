package com.flaxeninfosoft.sarwateAcademy.views.authFragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.api.ApiEndpoints;
import com.flaxeninfosoft.sarwateAcademy.api.AuthApiInterface;
import com.flaxeninfosoft.sarwateAcademy.api.Constants;
import com.flaxeninfosoft.sarwateAcademy.api.RetrofitClient;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentSignInBinding;
import com.flaxeninfosoft.sarwateAcademy.models.LoginModel;
import com.flaxeninfosoft.sarwateAcademy.models.User;
import com.flaxeninfosoft.sarwateAcademy.utils.SharedPrefs;
import com.flaxeninfosoft.sarwateAcademy.views.SplashActivity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class SignInFragment extends Fragment {

    public static final String ROLE = "ROLE";
    public static final String TAG = "SIGNIN-LOG";
    private FragmentSignInBinding binding;
    AuthApiInterface authApiInterface;
    ProgressDialog progressDialog;

    String getRole;
    RequestQueue requestQueue;
    Gson gson;

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false);
        binding.setLoginModel(new LoginModel());

        binding.signUpTextView.setOnClickListener(this::onClickSignUp);
        binding.forgotPasswordTextView.setOnClickListener(this::onClickForgotPassword);
        binding.continueButton.setOnClickListener(this::onClickContinueButton);
        if (getArguments() != null) {
            getRole = getArguments().getString(ROLE);
        }
        authApiInterface = RetrofitClient.getClient().create(AuthApiInterface.class);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Signing");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading please wait..");
        requestQueue = Volley.newRequestQueue(getContext());
        gson = new Gson();
        return binding.getRoot();
    }

    private void onClickContinueButton(View view) {
        inputValidation();
//        startActivity(new Intent(getActivity(), UserActivity.class));
    }

    private void onClickForgotPassword(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_signInFragment_to_forgotPasswordFragment);
    }

    private void onClickSignUp(View view) {
        Bundle bundle = new Bundle();
        if (getArguments() != null) {
            bundle.putString(ROLE, getArguments().getString(ROLE));
            Log.i("SignUpclick", getArguments().getString(ROLE));
        }
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_signInFragment_to_signUpFragment, bundle);
    }


    public void inputValidation() {

        if (binding.getLoginModel().getPhone() != null && binding.getLoginModel().getPhone().trim().isEmpty()) {
            binding.mobileNumTextInput.setError("Mobile Number Required");
            binding.mobileNumTextInput.requestFocus();
        } else if (binding.getLoginModel().getPassword() != null && binding.getLoginModel().getPassword().trim().isEmpty()) {
            binding.passwordTextInput.setError("Password Required");
            binding.passwordTextInput.requestFocus();
        } else {
            if (getRole.equals(Constants.ROLE_TEACHER)) {
                teacherLogin();
            } else if (getRole.equals(Constants.ROLE_STUDENT)) {
                studentLogin();
            } else {
                Toast.makeText(getContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void loginSuccess(User user) {
//        SharedPrefs.getInstance(getActivity().getApplicationContext()).setCredentials(user);
//        SharedPrefs.getInstance(getActivity().getApplicationContext()).setCurrentUser(user);
        SharedPrefs sharedPrefs1 = new SharedPrefs(getActivity().getApplicationContext());
        sharedPrefs1.setCredentials(user);
        Intent intent = new Intent(getActivity(), SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }


    public void teacherLogin() {
        progressDialog.show();
        String url = ApiEndpoints.BASE_URL + ApiEndpoints.TEACHER_LOGIN;

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(Constants.EMAIL, binding.mobileNumTextInput.getText().toString().trim());
        hashMap.put(Constants.PASSWORD, binding.passwordTextInput.getText().toString().trim());


        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap), response -> {
            progressDialog.dismiss();
            try {
                if (response != null) {
                    if (response.getString("success").equals("1")) {
                        Log.i(TAG, String.valueOf(response));
                        JSONObject jsonObject = response.getJSONObject("data");
                        User user = gson.fromJson(jsonObject.toString(), User.class);
                        Log.i(TAG, user.getEmail());
                        Log.i(TAG, user.getRole());
                        Log.i(TAG, String.valueOf(user.getId()));
                        loginSuccess(user);
                    } else {

                        Toast.makeText(getContext(), response.getString("message").toString(), Toast.LENGTH_SHORT).show();

                    }
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }, error -> {
            progressDialog.dismiss();
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();

            Log.i(TAG, String.valueOf(error));
        });

        int timeout = 10000; // 10 seconds
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArrayRequest);
    }

    private void studentLogin() {

        progressDialog.show();
        String url = ApiEndpoints.BASE_URL + ApiEndpoints.STUDENT_LOGIN;

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(Constants.EMAIL, binding.mobileNumTextInput.getText().toString().trim());
        hashMap.put(Constants.PASSWORD, binding.passwordTextInput.getText().toString().trim());


        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap), response -> {
            progressDialog.dismiss();
            try {

                if (response != null) {
                    if (response.getString("success").equals("1")) {
                        Log.i(TAG, String.valueOf(response));
                        JSONObject jsonObject = response.getJSONObject("data");
                        User user = gson.fromJson(jsonObject.toString(), User.class);

                        Toast.makeText(getContext(), user.getRole(), Toast.LENGTH_SHORT).show();
                        Log.i(TAG, user.getEmail());
                        Log.i(TAG, user.getRole());
                        Log.i(TAG, String.valueOf(user.getId()));
                        loginSuccess(user);
                    }
                    else {
                        Toast.makeText(getContext(), response.getString("message").toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, error -> {
            progressDialog.dismiss();
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            Log.i(TAG, String.valueOf(error));
        });

        int timeout = 10000; // 10 seconds
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArrayRequest);
    }
}