package com.flaxeninfosoft.sarwateAcademy.views.userFragments.mycourse;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.api.ApiEndpoints;
import com.flaxeninfosoft.sarwateAcademy.api.Constants;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentProfileBinding;
import com.flaxeninfosoft.sarwateAcademy.models.User;
import com.flaxeninfosoft.sarwateAcademy.utils.FileEncoder;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import io.paperdb.Paper;


public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }


    FragmentProfileBinding binding;
    String[] genderOptions = {"Please Select Gender", "Male", "Female", "Other"};
    String selectedGender;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    Gson gson;
    Uri imageUri;
    User user;
    String encodedImage = " ";
    public static final String TAG = "STUDENT_PROFILE-LOG";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        binding.backImageView.setOnClickListener(this::onClickBack);

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, genderOptions);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.genderSpinner.setAdapter(genderAdapter);
        binding.genderSpinner.setSelection(0);
        String selectedGender = binding.genderSpinner.getSelectedItem().toString();
        binding.genderSpinner.setSelection(binding.genderSpinner.getSelectedItemPosition());
        binding.userImageAddImageView.setOnClickListener(this::onClickAddImage);
        binding.userContinueButton.setOnClickListener(this::onClickUpdate);
        user = Paper.book().read("User_Updated");
        setDataOnEditText(user);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Updating");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading please wait..");
        requestQueue = Volley.newRequestQueue(getContext());
        gson = new Gson();


        return binding.getRoot();
    }

    private void onClickUpdate(View view) {
        if (binding.userNameTextInput.getText().toString().isEmpty()) {
            binding.userNameTextInput.setError("Required field");
        } else if (binding.userMobileNumTextInput.getText().toString().isEmpty()) {
            binding.userMobileNumTextInput.setError("Required field");
        } else if (binding.genderSpinner.getSelectedItem().toString().equals("Please Select Gender")) {
            Toast.makeText(getContext(), "Please Select Gender.", Toast.LENGTH_SHORT).show();
        } else {
            updateStudentProfile();
        }
    }

    private void onClickBack(View view) {
        Navigation.findNavController(view).navigateUp();
    }

    private void onClickAddImage(View view) {
        ImagePicker.with(this)
                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            binding.userImageViewProfile.setImageURI(data.getData());
            imageUri = data.getData();
            encodeImage(imageUri);

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Cancelled !", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateStudentProfile() {
        progressDialog.show();
        String url = ApiEndpoints.BASE_URL + ApiEndpoints.STUDENT_DETAILS_UPDATE;
        selectedGender = binding.genderSpinner.getSelectedItem().toString();
        Log.i(TAG, selectedGender);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(Constants.ROLE, user.getRole());
        hashMap.put(Constants.ID, user.getId());
        hashMap.put(Constants.NAME, binding.userNameTextInput.getText().toString().trim());
        hashMap.put(Constants.PHONE, binding.userMobileNumTextInput.getText().toString().trim());
        hashMap.put(Constants.EMAIL, user.getEmail());
        if (encodedImage != null || !encodedImage.isEmpty()) {
            hashMap.put(Constants.PROFILE_IMAGE, encodedImage);
        }
        hashMap.put(Constants.CITY, binding.userCityTextInput.getText().toString().trim());
        hashMap.put(Constants.STATE, binding.userStateTextInput.getText().toString().trim());
        hashMap.put("token", "0");
        hashMap.put("course_id", "0");
        hashMap.put(Constants.GENDER, selectedGender);


        Log.i(TAG, hashMap.toString());
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap), response -> {

            progressDialog.dismiss();
            try {
                if (response != null) {
                    if (response.getString("success").equals("1")) {
                        Log.i(TAG, String.valueOf(response));
                        User user1 = gson.fromJson(response.getString("data"), User.class);
                        setDataOnEditText(user1);

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


    private void setDataOnEditText(User user) {
        binding.UserNameTV1.setText(user.getName());
        binding.userNameTextInput.setText(user.getName());
        binding.userEmailTextInput.setText(user.getEmail());
        binding.userMobileNumTextInput.setText(user.getPhone());
        if (user.getCity() != null) {
            binding.userCityTextInput.setText(user.getCity());
        }
        if (user.getState() != null) {
            binding.userStateTextInput.setText(user.getState());
        }
        if (user.getProfileImg() != null) {
            Glide.with(getActivity()).load("http://103.118.17.202/~mkeducation/MK_API/User/" + user.getProfileImg()).placeholder(R.drawable.avatar_profile_icon).into(binding.userImageViewProfile);
        }
        if (user.getGender() != null) {
            Log.i(TAG, user.getGender());
            if (user.getGender().equalsIgnoreCase("Male")) {
                binding.genderSpinner.setSelection(1);
            } else if (user.getGender().equalsIgnoreCase("Female")) {
                binding.genderSpinner.setSelection(2);
            } else if (user.getGender().equalsIgnoreCase("Other")) {
                binding.genderSpinner.setSelection(3);
            } else {
                binding.genderSpinner.setSelection(0);
            }

        } else {
            binding.genderSpinner.setSelection(0);
        }
    }


    private void encodeImage(Uri imageUri) {
        if (imageUri != null || !imageUri.isAbsolute()) {
            try {
                encodedImage = FileEncoder.encodeImage(getContext().getContentResolver(), imageUri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}