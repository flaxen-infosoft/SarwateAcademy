package com.flaxeninfosoft.sarwateAcademy.views.teacherFragments;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.api.ApiEndpoints;
import com.flaxeninfosoft.sarwateAcademy.api.Constants;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentTeacherProfileBinding;
import com.flaxeninfosoft.sarwateAcademy.models.User;
import com.flaxeninfosoft.sarwateAcademy.utils.FileEncoder;
import com.flaxeninfosoft.sarwateAcademy.utils.SharedPrefs;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import io.paperdb.Paper;

public class TeacherProfileFragment extends Fragment {

    private FragmentTeacherProfileBinding binding;
    String[] genderOptions = {"Please Select Gender", "Male", "Female", "Other"};
    String selectedGender;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    Gson gson;
    Uri imageUri;
    String encodedImage = " ";
    User user;
    public static final String TAG = "TEACHER_PROFILE-LOG";

    public TeacherProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = Paper.book().read("User_Updated");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_teacher_profile, container, false);
        binding.backImageView.setOnClickListener(this::onClickBack);


        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, genderOptions);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.genderSpinner.setAdapter(genderAdapter);
        user = Paper.book().read("User_Updated");
        binding.setUser(user);
        String selectedGender = binding.genderSpinner.getSelectedItem().toString();
        binding.genderSpinner.setSelection(binding.genderSpinner.getSelectedItemPosition());
        binding.teacherImageAddImageView.setOnClickListener(this::onClickAddImage);
        binding.continueButton.setOnClickListener(this::onClickUpdate);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Updating");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading please wait..");
        requestQueue = Volley.newRequestQueue(getContext());
        gson = new Gson();
        setDataOnEditText(user);
        return binding.getRoot();
    }

    private void setDataOnEditText(User user) {
        binding.teacherNameTV1.setText(user.getName());
        binding.teacherNameTextInput.setText(user.getName());
        binding.teacherEmailTextInput.setText(user.getEmail());
        binding.teacherMobileNumTextInput.setText(user.getPhone());
        if (user.getCity() != null) {
            binding.teacherCityTextInput.setText(user.getCity());
        }
        if (user.getState() != null) {
            binding.teacherStateTextInput.setText(user.getState());
        }
        if (user.getProfileImg() != null) {
            Glide.with(getActivity()).load(user.getProfileImg()).placeholder(R.drawable.avatar_profile_icon).into(binding.teacherImageViewProfile);
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

    private void onClickUpdate(View view) {
        updateTeacherProfile();
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
            binding.teacherImageViewProfile.setImageURI(data.getData());
            imageUri = data.getData();
            binding.teacherImageViewProfile.setImageURI(imageUri);
            encodeImage(imageUri);

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Cancelled !", Toast.LENGTH_SHORT).show();
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


    private void updateTeacherProfile() {

        progressDialog.show();
        String url = ApiEndpoints.BASE_URL + ApiEndpoints.TEACHER_DETAILS_UPDATE;
        selectedGender = binding.genderSpinner.getSelectedItem().toString();
        Log.i(TAG, selectedGender);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(Constants.ROLE, user.getRole());
        hashMap.put(Constants.ID, user.getId());
        hashMap.put(Constants.NAME, binding.teacherNameTextInput.getText().toString().trim());
        hashMap.put(Constants.PHONE, binding.teacherMobileNumTextInput.getText().toString().trim());
        hashMap.put(Constants.EMAIL, user.getEmail());
        if (encodedImage != null || !encodedImage.isEmpty()) {
            hashMap.put(Constants.PROFILE_IMAGE, encodedImage);
        }
        hashMap.put(Constants.CITY, binding.teacherCityTextInput.getText().toString().trim());
        hashMap.put(Constants.STATE, binding.teacherStateTextInput.getText().toString().trim());
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
            Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            Log.i(TAG, String.valueOf(error));
        });
        int timeout = 10000; // 10 seconds
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArrayRequest);
    }


}