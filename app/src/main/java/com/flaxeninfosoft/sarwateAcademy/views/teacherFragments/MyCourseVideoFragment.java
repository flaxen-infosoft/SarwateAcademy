package com.flaxeninfosoft.sarwateAcademy.views.teacherFragments;

import android.app.ProgressDialog;
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
import com.flaxeninfosoft.sarwateAcademy.adapters.CourseVideosAdapter;
import com.flaxeninfosoft.sarwateAcademy.api.ApiEndpoints;
import com.flaxeninfosoft.sarwateAcademy.api.Constants;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentMyCourseVideoBinding;
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


    public MyCourseVideoFragment() {
        // Required empty public constructor
    }


    FragmentMyCourseVideoBinding binding;
    public static final String TAG = "MY-COURSE_VIDEO-LOG";
    RequestQueue requestQueue;
    Gson gson;
    ProgressDialog progressDialog;

    List<Video> videoList;
    CourseVideosAdapter adapter;
    Course course;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_course_video, container, false);
        binding.addVideoButton.setOnClickListener(this::onClickAddVideo);
        progressDialog = new ProgressDialog(getContext());
        course = Paper.book().read("CurrentMyCourse");
        Log.i(TAG, "CourseId " + course.getCourseId());
        progressDialog.setTitle("Loading");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("please wait..");
        requestQueue = Volley.newRequestQueue(getContext());
        gson = new Gson();
        videoList = new ArrayList<>();

        myCourses();
        adapter = new CourseVideosAdapter(videoList, this::onVideoClick);
        binding.videoRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        return binding.getRoot();
    }

    private void onVideoClick(Video video) {
        Paper.book().write("Current_Video",video);
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_singleMyCourseHomeFragment_to_videoDetailsFragment);
    }

    private void onClickAddVideo(View view) {
        Log.i(TAG, "ONClickCourseID " + course.getCourseId());
        Navigation.findNavController(view).navigate(R.id.action_singleMyCourseHomeFragment_to_teacherAddLiveSessionFragment);
    }

    private void myCourses() {
        User user = Paper.book().read("User");

        progressDialog.show();
        String url = ApiEndpoints.BASE_URL + ApiEndpoints.GET_VIDEOS_BY_COURSE_ID;
        Log.i(TAG, String.valueOf(user.getId()));
        HashMap<String, Object> hashMap = new HashMap<>();
        if (course.getCourseId() != null) {
            hashMap.put(Constants.COURSE_ID, course.getCourseId());
        }

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
                        if (videoList.size()==0) {
                            binding.noVideoFound.setVisibility(View.VISIBLE);
                            binding.videoRecycler.setVisibility(View.GONE);
                        } else {
                            binding.videoRecycler.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            binding.noVideoFound.setVisibility(View.GONE);
                            binding.videoRecycler.setVisibility(View.VISIBLE);

                        }

                    } else {
                        Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                        if (videoList.size()==0) {
                            binding.noVideoFound.setVisibility(View.VISIBLE);
                            binding.videoRecycler.setVisibility(View.GONE);
                        } else {
                            binding.videoRecycler.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            binding.noVideoFound.setVisibility(View.GONE);
                            binding.videoRecycler.setVisibility(View.VISIBLE);

                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            progressDialog.dismiss();
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            if (videoList.size()==0) {
                binding.noVideoFound.setVisibility(View.VISIBLE);
                binding.videoRecycler.setVisibility(View.GONE);
            } else {
                binding.videoRecycler.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                binding.noVideoFound.setVisibility(View.GONE);
                binding.videoRecycler.setVisibility(View.VISIBLE);

            }
            Log.i(TAG, String.valueOf(error));
        });
        int timeout = 25000; // 25 seconds
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArrayRequest);



    }
}