package com.flaxeninfosoft.sarwateAcademy.views.userFragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
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
import com.flaxeninfosoft.sarwateAcademy.adapters.CourseCategoryAdapter;
import com.flaxeninfosoft.sarwateAcademy.adapters.CourseRecyclerAdapter;
import com.flaxeninfosoft.sarwateAcademy.adapters.QuizCategoryAdapter;
import com.flaxeninfosoft.sarwateAcademy.api.ApiEndpoints;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentAllQuizBinding;
import com.flaxeninfosoft.sarwateAcademy.models.Course;
import com.flaxeninfosoft.sarwateAcademy.models.CourseCategory;
import com.flaxeninfosoft.sarwateAcademy.models.QuizCategory;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.paperdb.Paper;

public class AllQuizFragment extends Fragment {
    FragmentAllQuizBinding binding;

    public static final String TAG = "ALL-COURSE-LOG";

    public AllQuizFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    CourseRecyclerAdapter adapter;
    QuizCategoryAdapter courseCategoryAdapter;
    List<Course> courseList;

    List<Course> categorisedCourseList;
    List<QuizCategory> courseCategoryList;
    RequestQueue requestQueue;
    Gson gson;
    ProgressDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_all_quiz, container, false);


        binding.liveQuizRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.liveBatchesSwipeRefresh.setOnRefreshListener(this::onSwipeRefresh);

        binding.backImageView.setOnClickListener(this::onClickBack);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Wait");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading please wait..");
        requestQueue = Volley.newRequestQueue(getContext());
        gson = new Gson();
        courseList = new LinkedList<>();
        courseCategoryList = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.categoryRecyclerview.setLayoutManager(layoutManager);
        courseCategoryAdapter = new QuizCategoryAdapter(courseCategoryList, this::onClickCategory);
        binding.categoryRecyclerview.setAdapter(courseCategoryAdapter);
        adapter = new CourseRecyclerAdapter(courseList, this::onClickBatch);
        if (courseList.isEmpty()) {
            binding.noDataFoundLayout.setVisibility(View.VISIBLE);
            binding.liveQuizRecycler.setVisibility(View.GONE);
        } else {
            binding.liveQuizRecycler.setAdapter(adapter);
            binding.noDataFoundLayout.setVisibility(View.GONE);
            binding.liveQuizRecycler.setVisibility(View.VISIBLE);
        }

        getAllCategory();
        getAllCourse(0L);
        binding.searchBarEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable != null) {
                    filter(editable.toString());
                }
            }
        });

        return binding.getRoot();
    }
    private void onClickCategory(QuizCategory courseCategory) {
        Toast.makeText(getContext(), courseCategory.getId() + "", Toast.LENGTH_SHORT).show();
        if (courseCategory.getId() == 0) {
            getAllCourse(0L);
        } else {
            getAllCourse(courseCategory.getId());
        }

    }

    private void onSwipeRefresh() {
        getAllCourse(0L);
    }

    private void onClickBack(View view) {
        Navigation.findNavController(view).navigateUp();
    }
    private void filter(String text) {
        List<Course> courseList1 = new ArrayList<>();
        for (Course course : courseList) {
            if (course.getCourseName().toLowerCase().contains(text.toLowerCase())
                    || course.getPrice().toLowerCase().contains(text.toLowerCase())
//                    || course.getTeacherName().toLowerCase().contains(text.toLowerCase()
            ) {

                courseList1.add(course);
            }
        }

        if (courseList1.isEmpty()) {
            binding.noDataFoundLayout.setVisibility(View.VISIBLE);
            binding.liveQuizRecycler.setVisibility(View.GONE);
        } else {
            adapter.filterList(courseList1);
            binding.noDataFoundLayout.setVisibility(View.GONE);
            binding.liveQuizRecycler.setVisibility(View.VISIBLE);
        }
    }
    private void onClickBatch(Course course) {
        Paper.book().write("Current_Course", course);
        Navigation.findNavController(binding.getRoot()).navigate(R.id.quizDescriptionFragment);
    }

    private void getAllCourse(Long category_id) {
        binding.liveBatchesSwipeRefresh.setRefreshing(false);
        courseList.clear();

        progressDialog.show();
        String url = ApiEndpoints.BASE_URL + ApiEndpoints.GET_ALL_COURSE;
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {

            progressDialog.dismiss();
            try {
                if (response != null) {
                    Log.i(TAG, String.valueOf(response));
                    if (response.getString("success").equals("1")) {
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Course course = gson.fromJson(jsonArray.getJSONObject(i).toString(), Course.class);
                            if (category_id!=0) {
                                if (category_id.equals(course.getCategoryId())) {
                                    courseList.add(course);
                                }
                            }
                            else {
                                courseList.add(course);
                            }

                        }
                        if (courseList.size() == 0) {
                            binding.noDataFoundLayout.setVisibility(View.VISIBLE);
                            binding.liveQuizRecycler.setVisibility(View.GONE);
                        } else {
                            binding.liveQuizRecycler.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            binding.noDataFoundLayout.setVisibility(View.GONE);
                            binding.liveQuizRecycler.setVisibility(View.VISIBLE);
                        }

                    } else {
                        Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                        if (courseList.size() == 0) {
                            binding.noDataFoundLayout.setVisibility(View.VISIBLE);
                            binding.liveQuizRecycler.setVisibility(View.GONE);
                        } else {
                            binding.liveQuizRecycler.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            binding.noDataFoundLayout.setVisibility(View.GONE);
                            binding.liveQuizRecycler.setVisibility(View.VISIBLE);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            progressDialog.dismiss();
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            if (courseList.size() == 0) {
                binding.noDataFoundLayout.setVisibility(View.VISIBLE);
                binding.liveQuizRecycler.setVisibility(View.GONE);
            } else {
                binding.liveQuizRecycler.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                binding.noDataFoundLayout.setVisibility(View.GONE);
                binding.liveQuizRecycler.setVisibility(View.VISIBLE);
            }
            Log.i(TAG, String.valueOf(error));
        });
        int timeout = 10000; // 10 seconds
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArrayRequest);


    }


    private void getAllCategory() {
//        courseCategoryList.clear();


        String url = ApiEndpoints.BASE_URL + ApiEndpoints.GET_ALL_QUIZ_CATEGORY;
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {

            try {
                if (response != null) {
                    Log.i(TAG, String.valueOf(response));
                    if (response.getString("success").equals("1")) {
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            QuizCategory courseCategory = gson.fromJson(jsonArray.getJSONObject(i).toString(), QuizCategory.class);
                            courseCategoryList.add(courseCategory);

                        }
                        QuizCategory courseCategory = new QuizCategory();
                        courseCategory.setId(0L);
                        courseCategory.setQuiz_category_name("All");
                        courseCategoryList.add(0,courseCategory);
                        courseCategoryAdapter.notifyDataSetChanged();

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