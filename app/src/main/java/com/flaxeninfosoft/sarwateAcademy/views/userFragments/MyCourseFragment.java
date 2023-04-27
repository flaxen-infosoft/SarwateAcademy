package com.flaxeninfosoft.sarwateAcademy.views.userFragments;

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
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentMyCourseBinding;
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


    FragmentMyCourseBinding binding;
    MyCourseRecyclerAdapter adapter;
    List<Course> courseList;
    RequestQueue requestQueue;
    Gson gson;
    Uri image;
    ProgressDialog progressDialog;
    public static final String TAG = "MY-STUDENT-COURSE-LOG";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_course, container, false);
        binding.myCourseRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.backImageView.setOnClickListener(this::onClickBack);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Loading please wait..");
        progressDialog.setCancelable(false);
        requestQueue = Volley.newRequestQueue(getContext());
        gson = new Gson();
        courseList = new ArrayList<>();
        myCourses();
        adapter = new MyCourseRecyclerAdapter(courseList, this::onClickMyCourse);
        if (courseList.isEmpty()) {
            binding.noMyCourseFound.setVisibility(View.VISIBLE);
            binding.myCourseRecycler.setVisibility(View.GONE);
        } else {
            binding.myCourseRecycler.setAdapter(adapter);
            binding.noMyCourseFound.setVisibility(View.GONE);
            binding.myCourseRecycler.setVisibility(View.VISIBLE);
        }

        return binding.getRoot();
    }

    private void onClickMyCourse(Course course) {
        Paper.book().write("My_Current_Course",course);
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_myCourseFragment_to_myCoursePurchasedHomeFragment);
    }

    private void onClickBack(View view) {
        Navigation.findNavController(view).navigateUp();
    }


    private void myCourses() {
        User user = Paper.book().read("User");
        courseList.clear();
        progressDialog.show();
        String url = "http://103.118.17.202/~mkeducation/MK_API/User/myCourseStudent.php";
        Log.i(TAG, String.valueOf(user.getId()));
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("studentId", user.getId());

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
                            binding.myCourseRecycler.setVisibility(View.GONE);
                        } else {
                            binding.myCourseRecycler.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            binding.noMyCourseFound.setVisibility(View.GONE);
                            binding.myCourseRecycler.setVisibility(View.VISIBLE);
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
                binding.myCourseRecycler.setVisibility(View.GONE);
            } else {
                binding.myCourseRecycler.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                binding.noMyCourseFound.setVisibility(View.GONE);
                binding.myCourseRecycler.setVisibility(View.VISIBLE);
            }

        });
        int timeout = 10000; // 10 seconds
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArrayRequest);
    }

}