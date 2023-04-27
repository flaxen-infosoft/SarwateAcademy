package com.flaxeninfosoft.sarwateAcademy.views.userFragments;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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
import com.flaxeninfosoft.sarwateAcademy.api.ApiEndpoints;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentBuyCourseBinding;
import com.flaxeninfosoft.sarwateAcademy.models.Course;
import com.flaxeninfosoft.sarwateAcademy.models.CoursePurchaseRequest;
import com.flaxeninfosoft.sarwateAcademy.models.StudyMaterial;
import com.flaxeninfosoft.sarwateAcademy.models.User;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import io.paperdb.Paper;


public class BuyCourseFragment extends Fragment {


    public BuyCourseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentBuyCourseBinding binding;
    Course course;
    RequestQueue requestQueue;
    Gson gson;
    ProgressDialog progressDialog;
    public static final String TAG = "MY-STUDENT-COURSE-LOG";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_buy_course, container, false);
        binding.continueButton.setOnClickListener(this::onClickBuyCourse);
        course = Paper.book().read("Current_Course");
        Picasso.get().load("http://103.118.17.202/~mkeducation/MK_API/User/" + course.getImageUrl()).placeholder(R.drawable.sarwate_logo).into(binding.courseImageView);
        binding.setCourse(course);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Loading please wait..");
        progressDialog.setCancelable(false);
        requestQueue = Volley.newRequestQueue(getContext());
        gson = new Gson();

        return binding.getRoot();
    }

    private void onClickBuyCourse(View view) {
        buyCourse();

    }

    private void buyCourse() {
        User user = Paper.book().read("User");
        progressDialog.show();
        String url = ApiEndpoints.BASE_URL + ApiEndpoints.PURCHASE_COURSE_REQUEST;
        Log.i(TAG, String.valueOf(user.getId()));
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("courseId", course.getId());
        hashMap.put("categoryId", course.getCategoryId());
        hashMap.put("id", user.getId());//STUDENT ID
        Log.i(TAG, hashMap.toString());
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap), response -> {


            try {
                if (response != null) {
                    Log.i(TAG, String.valueOf(response));
                    if (response.getString("success").equals("1")) {
                        progressDialog.dismiss();
                        CoursePurchaseRequest coursePurchaseRequest = gson.fromJson(response.getString("data"), CoursePurchaseRequest.class);
                        Paper.book().write("CoursePurchaseRequest",coursePurchaseRequest);
                        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_buyCourseFragment_to_choosePaymentFragment);

                    } else {
//                        Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                        Log.i(TAG, String.valueOf(response));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            progressDialog.dismiss();
            Log.i(TAG, String.valueOf(error));
        });
        int timeout = 15000; // 25 seconds
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArrayRequest);
    }

}