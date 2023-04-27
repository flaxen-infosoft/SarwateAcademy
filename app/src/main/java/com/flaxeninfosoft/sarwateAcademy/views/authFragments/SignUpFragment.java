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
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.api.ApiEndpoints;
import com.flaxeninfosoft.sarwateAcademy.api.AuthApiInterface;
import com.flaxeninfosoft.sarwateAcademy.api.Constants;
import com.flaxeninfosoft.sarwateAcademy.api.RetrofitClient;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentSignUpBinding;
import com.flaxeninfosoft.sarwateAcademy.models.User;
import com.flaxeninfosoft.sarwateAcademy.views.TeacherActivity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUpFragment extends Fragment {

    public static final String ROLE = "ROLE";
    public static final String TAG = "SIGNUP-LOG";
    String getRole;
    String name, email, password, phone;
    ProgressDialog progressDialog;

    public SignUpFragment() {
        // Required empty public constructor
    }

    private FragmentSignUpBinding binding;
    AuthApiInterface authApiInterface;
    RequestQueue requestQueue;
    Gson gson;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false);

        assert getArguments() != null;
        getRole = getArguments().getString(ROLE);
        binding.signInTextView.setOnClickListener(this::onClickSignIn);
        binding.continueButton.setOnClickListener(this::onClickContinue);
        authApiInterface = RetrofitClient.getClient().create(AuthApiInterface.class);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Signing up");
        progressDialog.setMessage("Loading please wait...");
        requestQueue = Volley.newRequestQueue(getContext());
        gson = new Gson();
        return binding.getRoot();
    }

    public boolean inputValidation() {
        String MobilePattern = "[0-9]{10}";
        if (Objects.requireNonNull(binding.mobileNumTextInput.getText()).toString().isEmpty()) {
            binding.mobileNumTextInput.setError("Mobile Number Required");
            binding.mobileNumTextInput.requestFocus();
            return false;
        } else if (!Objects.requireNonNull(binding.mobileNumTextInput.getText()).toString().matches(MobilePattern)) {
            binding.mobileNumTextInput.setError("Invalid Mobile Number");
            binding.mobileNumTextInput.requestFocus();
            return false;
        } else if (Objects.requireNonNull(binding.nameTextInput.getText()).toString().isEmpty()) {
            binding.nameTextInput.setError("User Name Required");
            binding.nameTextInput.requestFocus();
            return false;
        } else if (Objects.requireNonNull(binding.passwordTextInput.getText()).toString().isEmpty()) {
            binding.passwordTextInput.setError("Password Required");
            binding.passwordTextInput.requestFocus();
            return false;
        } else if (Objects.requireNonNull(binding.confirmPasswordTextInput.getText()).toString().isEmpty()) {
            binding.confirmPasswordTextInput.setError("Confirm Password Required");
            binding.confirmPasswordTextInput.requestFocus();
            return false;
        } else if (!isValidEmailId(binding.emailTextInput.getText().toString().trim())) {
            Log.i("Sign Up", email);
            return false;
        } else if (!isValidMobileNo(binding.mobileNumTextInput.getText().toString())) {
            Log.i("Sign Up", phone);
            return false;
        } else if (!binding.passwordTextInput.getText().toString().equals(binding.confirmPasswordTextInput.getText().toString())) {
            Toast.makeText(getContext(), "Wrong Password", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            registerUser();
            return true;
        }


    }


    private void registerUser() {

        if (getRole.equals(Constants.ROLE_STUDENT)) {
            studentRegister();
        } else if (getRole.equals(Constants.ROLE_TEACHER)) {
            teacherRegister();
        } else {
            Toast.makeText(getContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
        }
    }


    private void onClickSignIn(View view) {
        Bundle bundle = new Bundle();
        if (getArguments() != null) {
            bundle.putString(ROLE, getArguments().getString(ROLE));
            Log.i(TAG, getArguments().getString(ROLE));
        }

        Navigation.findNavController(binding.getRoot()).navigateUp();
    }

    private void onClickContinue(View view) {
        inputValidation();
//        startActivity(new Intent(getActivity(), TeacherActivity.class));
    }


    private boolean isValidEmailId(String email) {
        String regex = "^[\\w\\.-]+@([\\da-zA-Z-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidMobileNo(String phone) {
        // Regular expression to match a 10-digit mobile number
        String regex = "^[1-9]\\d{9}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }


    public void teacherRegister() {
        progressDialog.show();
        String url = ApiEndpoints.BASE_URL + ApiEndpoints.REGISTER_TEACHER;

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(Constants.ROLE, Constants.ROLE_TEACHER);
        hashMap.put(Constants.NAME, binding.nameTextInput.getText().toString().trim());
        hashMap.put(Constants.EMAIL, binding.emailTextInput.getText().toString().trim());
        hashMap.put(Constants.PASSWORD, binding.passwordTextInput.getText().toString().trim());
        hashMap.put(Constants.PHONE, binding.mobileNumTextInput.getText().toString().trim());


        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap), new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
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

                            registerSuccess(user);

                        } else {
                            Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getContext(),error.toString() , Toast.LENGTH_SHORT).show();
                Log.i(TAG, String.valueOf(error));
            }
        });

        int timeout = 10000; // 10 seconds
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArrayRequest);
    }

    private void registerSuccess(User user) {
        Bundle bundle = new Bundle();
        bundle.putString(ROLE, user.getRole());
        Navigation.findNavController(binding.getRoot()).navigate(R.id.signInFragment, bundle);
    }

    private void studentRegister() {

        progressDialog.show();
        String url = ApiEndpoints.BASE_URL + ApiEndpoints.REGISTER_USER;

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(Constants.ROLE, Constants.ROLE_STUDENT);
        hashMap.put(Constants.NAME, binding.nameTextInput.getText().toString().trim());
        hashMap.put(Constants.EMAIL, binding.emailTextInput.getText().toString().trim());
        hashMap.put(Constants.PASSWORD, binding.passwordTextInput.getText().toString().trim());
        hashMap.put(Constants.PHONE, binding.mobileNumTextInput.getText().toString().trim());


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
                        registerSuccess(user);
                    } else {
                        Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
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