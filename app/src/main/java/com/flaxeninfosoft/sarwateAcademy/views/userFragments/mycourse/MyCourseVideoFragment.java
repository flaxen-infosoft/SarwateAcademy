package com.flaxeninfosoft.sarwateAcademy.views.userFragments.mycourse;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
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
import com.flaxeninfosoft.sarwateAcademy.adapters.PurchasedCourseVideoAdapter;
import com.flaxeninfosoft.sarwateAcademy.api.ApiEndpoints;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentMyCourseVideo2Binding;
import com.flaxeninfosoft.sarwateAcademy.models.Course;
import com.flaxeninfosoft.sarwateAcademy.models.User;
import com.flaxeninfosoft.sarwateAcademy.models.Video;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.paperdb.Paper;


public class MyCourseVideoFragment extends Fragment {


    FragmentMyCourseVideo2Binding binding;
    public static final String TAG = "STUDENT-COURSE_VIDEO-LOG";
    RequestQueue requestQueue;
    Gson gson;
    ProgressDialog progressDialog;
    Course course;
    List<Video> videoList;
    PurchasedCourseVideoAdapter adapter;


    public MyCourseVideoFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_course_video2, container, false);
        binding.videoCourseRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        course = Paper.book().read("My_Current_Course");
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Wait");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading please wait..");
        requestQueue = Volley.newRequestQueue(getContext());
        gson = new Gson();
        videoList = new ArrayList<>();
        adapter = new PurchasedCourseVideoAdapter(videoList, this::onVideoClick);
        getVideos();


        if (videoList.isEmpty()) {
            binding.noMyCourseFound.setVisibility(View.VISIBLE);
            binding.videoCourseRecycler.setVisibility(View.GONE);
        } else {
            binding.videoCourseRecycler.setAdapter(adapter);
            binding.noMyCourseFound.setVisibility(View.GONE);
            binding.videoCourseRecycler.setVisibility(View.VISIBLE);
        }


        return binding.getRoot();
    }

    private void onVideoClick(Video video) {
        Intent intent = null;
        try {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse(video.getVideoLink()));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(video.getVideoLink()));
            startActivity(intent);
        }
    }


    private void getVideos() {
        User user = Paper.book().read("User");

        progressDialog.show();
        String url = ApiEndpoints.BASE_URL + ApiEndpoints.GET_VIDEOS_BY_COURSE_ID;
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("courseId", course.getId());

        Log.i(TAG, "myCourses " + hashMap);

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap), response -> {

            progressDialog.dismiss();
            try {
                if (response != null) {
                    Log.i(TAG, String.valueOf(response));
                    if (response.getString("success").equals("1")) {
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Video video = gson.fromJson(jsonArray.getJSONObject(i).toString(), Video.class);
                            Log.i(TAG, String.valueOf(video.getId()));
                            videoList.add(video);
                            Log.i(TAG, video.getTitle());
                        }
                        if (videoList.size() == 0) {
                            binding.noMyCourseFound.setVisibility(View.VISIBLE);
                            binding.videoCourseRecycler.setVisibility(View.GONE);
                        } else {
                            binding.videoCourseRecycler.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            binding.noMyCourseFound.setVisibility(View.GONE);
                            binding.videoCourseRecycler.setVisibility(View.VISIBLE);

                        }

                    } else {
                        Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                        if (videoList.size() == 0) {
                            binding.noMyCourseFound.setVisibility(View.VISIBLE);
                            binding.videoCourseRecycler.setVisibility(View.GONE);
                        } else {
                            binding.videoCourseRecycler.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            binding.noMyCourseFound.setVisibility(View.GONE);
                            binding.videoCourseRecycler.setVisibility(View.VISIBLE);

                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            progressDialog.dismiss();
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            if (videoList.size() == 0) {
                binding.noMyCourseFound.setVisibility(View.VISIBLE);
                binding.videoCourseRecycler.setVisibility(View.GONE);
            } else {
                binding.videoCourseRecycler.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                binding.noMyCourseFound.setVisibility(View.GONE);
                binding.videoCourseRecycler.setVisibility(View.VISIBLE);

            }
            Log.i(TAG, String.valueOf(error));
        });
        int timeout = 10000; // 10 seconds
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArrayRequest);


    }
}