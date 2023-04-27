package com.flaxeninfosoft.sarwateAcademy.views.teacherFragments;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.adapters.MyCourseRecyclerAdapter;
import com.flaxeninfosoft.sarwateAcademy.api.ApiEndpoints;
import com.flaxeninfosoft.sarwateAcademy.api.Constants;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentMyCourseTeacherBinding;
import com.flaxeninfosoft.sarwateAcademy.models.Course;
import com.flaxeninfosoft.sarwateAcademy.models.User;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.paperdb.Paper;


public class MyCourseFragment extends Fragment {


    public MyCourseFragment() {
        // Required empty public constructor
    }

    FragmentMyCourseTeacherBinding binding;
    MyCourseRecyclerAdapter adapter;
    List<Course> courseList;
    RequestQueue requestQueue;
    Gson gson;
    Uri image;
    ProgressDialog progressDialog;
    public static final String TAG = "MY-COURSE-LOG";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_course_teacher, container, false);
        binding.backImageView.setOnClickListener(this::onClickBack);
        binding.myCourseTeacherRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        courseList = new ArrayList<>();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading please wait..");
        requestQueue = Volley.newRequestQueue(getContext());
        gson = new Gson();
        myCourses();
        adapter = new MyCourseRecyclerAdapter(courseList, this::onClickMyCourse);
        return binding.getRoot();
    }

    private void onClickMyCourse(Course course) {
        Log.i(TAG, "ONClickCourseID " + course.getCourseId());
        Paper.init(getActivity());
        Paper.book().write("CurrentMyCourse", course);

        Bundle bundle = new Bundle();
        if (course.getId() == null) {
            bundle.putLong("id", 0);
        } else {
            bundle.putLong("id", course.getCourseId());
        }
        bundle.putString("imageUrl", course.getImageUrl());
        bundle.putString("courseName", course.getCourseName());
        bundle.putString("teacherName", course.getTeacherName());
        bundle.putString("syllabusPdf", course.getSyllabusPdf());
        bundle.putString("description", course.getDescription());
        if (course.getId() == null) {
            bundle.putLong("categoryId", 0);
        } else {
            bundle.putLong("categoryId", course.getCategoryId());
        }
        bundle.putString("price", course.getPrice());
        bundle.putString("category", String.valueOf(course.getCategory()));
        bundle.putString("startDate", course.getStartDate());
        bundle.putString("endDate", course.getEndDate());
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_myCourseTeacherFragment_to_singleMyCourseHomeFragment, bundle);
    }

    private void onClickBack(View view) {
        Navigation.findNavController(view).navigateUp();
    }


    private void myCourses() {
        User user = Paper.book().read("User");
        courseList.clear();
        progressDialog.show();
        String url = ApiEndpoints.BASE_URL + ApiEndpoints.MY_COURSE_TEACHER;
        Log.i(TAG, String.valueOf(user.getId()));
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(Constants.ID, user.getId());

        Log.i(TAG, "myCourses " + hashMap);

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap), response -> {

            progressDialog.dismiss();
            try {
                if (response != null) {
                    Log.i(TAG, String.valueOf(response));
                    if (response.getString("success").equals("1")) {
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Course course = gson.fromJson(jsonArray.getJSONObject(i).toString(), Course.class);
                            Log.i(TAG, "" + course.getCourseId());
                            courseList.add(course);

                        }

                        if (courseList.size() == 0) {
                            binding.noMyCourseFound.setVisibility(View.VISIBLE);
                            binding.myCourseTeacherRecycler.setVisibility(View.GONE);
                        } else {
                            binding.myCourseTeacherRecycler.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            binding.noMyCourseFound.setVisibility(View.GONE);
                            binding.myCourseTeacherRecycler.setVisibility(View.VISIBLE);
                        }
                        Log.i(TAG, "List size: " + courseList.size());
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
            if (courseList.size() == 0) {
                binding.noMyCourseFound.setVisibility(View.VISIBLE);
                binding.myCourseTeacherRecycler.setVisibility(View.GONE);
            } else {
                binding.myCourseTeacherRecycler.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                binding.noMyCourseFound.setVisibility(View.GONE);
                binding.myCourseTeacherRecycler.setVisibility(View.VISIBLE);
            }

        });
        int timeout = 25000; // 25 seconds
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArrayRequest);
    }

}