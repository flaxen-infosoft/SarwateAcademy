package com.flaxeninfosoft.sarwateAcademy.views.teacherFragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.api.ApiEndpoints;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentEditMyCourseDescriptionBinding;
import com.flaxeninfosoft.sarwateAcademy.models.Course;
import com.flaxeninfosoft.sarwateAcademy.models.CourseCategory;
import com.flaxeninfosoft.sarwateAcademy.models.User;
import com.flaxeninfosoft.sarwateAcademy.utils.FileEncoder;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.paperdb.Paper;


public class EditMyCourseDescriptionFragment extends Fragment {


    public EditMyCourseDescriptionFragment() {
        // Required empty public constructor
    }


    FragmentEditMyCourseDescriptionBinding binding;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    Gson gson;
    Uri imageUri;
    String encodedImage = " ";
    String[] freeOrPaidOptions = {"Please Select Free or Paid", "Free", "Paid"};
    Course course;
    public static final String TAG = "EDIT-MYCOURSE-DESCRIPTION-LOG";
    List<CourseCategory> courseCategoryList;
    List<String> categoryList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_my_course_description, container, false);
        course = Paper.book().read("CurrentMyCourse");
        binding.setCourse(course);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Updating");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading please wait..");
        requestQueue = Volley.newRequestQueue(getContext());
        gson = new Gson();
        categoryList = new ArrayList<>();
        courseCategoryList = new ArrayList<>();
        getAllCategory();
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, freeOrPaidOptions);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.freeOrPaidSpinner.setAdapter(spinnerAdapter);

        CourseCategory courseCategory = new CourseCategory();
        courseCategory.setID(0L);
        courseCategory.setProduct_category_name("Select Category");
        courseCategoryList.add(0, courseCategory);
        categoryList.add(0, courseCategory.getProduct_category_name());
        ArrayAdapter<String> categoryArraySpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, categoryList);
        categoryArraySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.courseCategorySpinner.setAdapter(categoryArraySpinnerAdapter);
        binding.updateCourseButton.setOnClickListener(this::onClickUpdateCourse);
        binding.addImageIcon.setOnClickListener(this::onClickAddCourseImage);
        binding.backImageView.setOnClickListener(this::onClickBack);
        Log.i(TAG, course.getCourseName());
        if (course.isFree().equals("Free")) {
            binding.freeOrPaidSpinner.setSelection(1);
        } else if (course.isFree().equals("Paid")) {
            binding.freeOrPaidSpinner.setSelection(2);
        } else {
           binding.freeOrPaidSpinner.setSelection(0);
        }



        return binding.getRoot();
    }

    private void onClickBack(View view) {
        Navigation.findNavController(view).navigateUp();
    }

    private void onClickAddCourseImage(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, 100);
    }

    private void onClickUpdateCourse(View view) {
        String selectedFreeOrPaid = binding.freeOrPaidSpinner.getSelectedItem().toString();
        String selecetedCategory = binding.courseCategorySpinner.getSelectedItem().toString();
        Long selectedCategoryId = 0L;
        String freeOrPaid = "";

        if (binding.titleTextInput.getText().toString().isEmpty()) {
            binding.titleTextInput.setError("Required Field");
        } else if (binding.descriptionTextInput.getText().toString().isEmpty()) {
            binding.descriptionTextInput.setError("Required Field");
        } else if (binding.startDateTextInput.getText().toString().isEmpty()) {
            binding.startDateTextInput.setError("Required Field");
        } else if (binding.endDateTextInput.getText().toString().isEmpty()) {
            binding.endDateTextInput.setError("Required Field");
        } else if (binding.languageTextInput.getText().toString().isEmpty()) {
            binding.languageTextInput.setError("Required Field");
        } else if (binding.durationTextInput.getText().toString().isEmpty()) {
            binding.durationTextInput.setError("Required Field");
        } else if (binding.validityTextInput.getText().toString().isEmpty()) {
            binding.validityTextInput.setError("Required Field");
        } else if (binding.priceTextInput.getText().toString().isEmpty()) {
            binding.priceTextInput.setError("Required Field");
        } else if (encodedImage.isEmpty()) {
            Toast.makeText(getContext(), "Course Image Required.", Toast.LENGTH_SHORT).show();
        } else if (selecetedCategory.equals("Select Category")) {
            Toast.makeText(getContext(), "Please Select Category", Toast.LENGTH_SHORT).show();
        } else {
            if (selectedFreeOrPaid.equals("Free")) {
                freeOrPaid = "Free";
            } else if (selectedFreeOrPaid.equals("Paid")) {
                freeOrPaid = "Paid";
            } else {
                Toast.makeText(getContext(), "Please Select Notes are free or not.", Toast.LENGTH_SHORT).show();
            }
        }


        for (int i = 0; i < courseCategoryList.size() ; i++) {
            if (selecetedCategory.equals(courseCategoryList.get(i).getProduct_category_name())){
                selectedCategoryId = courseCategoryList.get(i).getID();
            }
        }

        updateCourse(freeOrPaid,selectedCategoryId);


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                imageUri = data.getData();
                encodeImage(imageUri);

            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Cancelled !", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateCourse(String freeOrPaid, Long selectedCategoryId) {
        User user = Paper.book().read("User");
        progressDialog.show();
        String url = ApiEndpoints.BASE_URL + ApiEndpoints.UPDATE_COURSE;
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("courseId",course.getCourseId());
        hashMap.put("teacherId", user.getId());
        hashMap.put("courseName", binding.titleTextInput.getText().toString());
        if (encodedImage.isEmpty()){
            hashMap.put("imageurl", " ");
        }
        else {
            hashMap.put("imageurl",encodedImage);
        }

        hashMap.put("description", binding.descriptionTextInput.getText().toString());
//        hashMap.put("syllabusPdf", " ");
        hashMap.put("categoryId", selectedCategoryId);
        hashMap.put("price", binding.priceTextInput.getText().toString());
        hashMap.put("start_date", binding.startDateTextInput.getText().toString());
        hashMap.put("end_date", binding.endDateTextInput.getText().toString());
        hashMap.put("course_duration", binding.durationTextInput.getText().toString());
        hashMap.put("language", binding.languageTextInput.getText().toString());
        hashMap.put("course_validity", binding.validityTextInput.getText().toString());
        hashMap.put("isFree", freeOrPaid);

        Log.i(TAG, hashMap.toString());
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap), response -> {

            progressDialog.dismiss();
            try {
                if (response != null) {
                    Log.i(TAG, String.valueOf(response));
                    if (response.getString("success").equals("1")) {
                        Course course = gson.fromJson(response.getString("data"), Course.class);
                        Toast.makeText(getContext(), course.getCourseName() + " Updated Successfully.", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(binding.getRoot()).navigateUp();

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

    private void encodeImage(Uri imageUri) {
        if (imageUri != null || !imageUri.isAbsolute()) {
            try {
                encodedImage = FileEncoder.encodeImage(getContext().getContentResolver(), imageUri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void getAllCategory() {
        String url = ApiEndpoints.BASE_URL + ApiEndpoints.GET_ALL_CATEGORY;
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {

            try {
                if (response != null) {
                    Log.i(TAG, String.valueOf(response));
                    if (response.getString("success").equals("1")) {
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0 + 1; i < jsonArray.length() + 1; i++) {
                            CourseCategory courseCategory = gson.fromJson(jsonArray.getJSONObject(i).toString(), CourseCategory.class);
                            courseCategoryList.add(courseCategory);
                            categoryList.add(courseCategory.getProduct_category_name());

                        }
                        for (int i = 1; i < courseCategoryList.size()+1; i++) {
                            if (course.getCategoryId().equals(courseCategoryList.get(i).getID())){
                                binding.courseCategorySpinner.setSelection(i);
                            }
                        }



                    } else {
                        Toast.makeText(getContext(), response.getString("Category Not Found."), Toast.LENGTH_SHORT).show();

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            Log.i(TAG, String.valueOf(error));
        });
        int timeout = 10000; // 10 seconds
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArrayRequest);


    }
}