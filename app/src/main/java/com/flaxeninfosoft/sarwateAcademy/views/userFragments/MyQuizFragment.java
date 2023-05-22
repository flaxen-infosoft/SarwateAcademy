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
import com.flaxeninfosoft.sarwateAcademy.adapters.MyQuizRecyclerAdapter;
import com.flaxeninfosoft.sarwateAcademy.adapters.QuizQuestionsAdapter;
import com.flaxeninfosoft.sarwateAcademy.api.ApiEndpoints;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentMyCourseBinding;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentMyQuizBinding;
import com.flaxeninfosoft.sarwateAcademy.models.Course;
import com.flaxeninfosoft.sarwateAcademy.models.MyQuiz;
import com.flaxeninfosoft.sarwateAcademy.models.QuizQuestions;
import com.flaxeninfosoft.sarwateAcademy.models.User;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.paperdb.Paper;


public class MyQuizFragment extends Fragment {

    public MyQuizFragment() {
        // Required empty public constructor
    }
    FragmentMyQuizBinding binding;
    MyQuizRecyclerAdapter adapter;
    List<MyQuiz> quiz;
    RequestQueue requestQueue;
    QuizQuestionsAdapter adapterquestion;
    List<QuizQuestions> quizQuestions;
    Gson gson;
    Uri image;
    ProgressDialog progressDialog;
    public static final String TAG = "MY-STUDENT-QUIZ-LOG";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_quiz, container, false);

        binding.myQuizRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.backImageView.setOnClickListener(this::onClickBack);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Loading please wait..");
        progressDialog.setCancelable(false);
        requestQueue = Volley.newRequestQueue(getContext());
        gson = new Gson();
        quiz = new ArrayList<>();
        myQuiz();
        quizQuestionget();
        adapter = new MyQuizRecyclerAdapter(quiz, this::onClickMyQuiz);
        if (quiz.isEmpty()) {
            binding.noMyQuizFound.setVisibility(View.VISIBLE);
            binding.myQuizRecycler.setVisibility(View.GONE);
        } else {
            binding.myQuizRecycler.setAdapter(adapter);
            binding.noMyQuizFound.setVisibility(View.GONE);
            binding.myQuizRecycler.setVisibility(View.VISIBLE);
        }

        return binding.getRoot();
    }

    private void onClickMyQuiz(MyQuiz course) {
        Paper.book().write("My_Current_Quiz",course);
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_myQuizFragment_to_startQuizFragment);
//        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_myCourseFragment_to_myCoursePurchasedHomeFragment);
    }

    private void onClickBack(View view) {
        Navigation.findNavController(view).navigateUp();
    }

    private void myQuiz() {

        User user = Paper.book().read("User");
        quiz.clear();
        progressDialog.show();
        String url =  ApiEndpoints.BASE_URL + ApiEndpoints.GET_MY_QUIZ;
        Log.e("Success", String.valueOf(user.getId()));
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("studentId", user.getId());


        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(hashMap), response -> {

            Log.e("Success:", url);
            progressDialog.dismiss();
            try {
                if (response != null) {
                    Log.i(TAG, String.valueOf(response));
                    if (response.getString("success").equals("1")) {
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < 4; i++) {
                            Log.e("Success1", url);
                            MyQuiz course = gson.fromJson(jsonArray.getJSONObject(i).toString(),MyQuiz.class);
                            Log.i(TAG, "" + course.getCatId());
                            quiz.add(course);
                        }

                        if (quiz.size() == 0) {
                            binding.noMyQuizFound.setVisibility(View.VISIBLE);
                            binding.myQuizRecycler.setVisibility(View.GONE);
                        } else {
                            binding.myQuizRecycler.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            binding.noMyQuizFound.setVisibility(View.GONE);
                            binding.myQuizRecycler.setVisibility(View.VISIBLE);
                        }
                        Log.i(TAG, "List size: " + quiz.size());
                    } else {
                        Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                        Log.e("Response", response.getString("message"));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            progressDialog.dismiss();
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            if (quiz.size() == 0) {
                binding.noMyQuizFound.setVisibility(View.VISIBLE);
                binding.myQuizRecycler.setVisibility(View.GONE);
            } else {
                binding.myQuizRecycler.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                binding.noMyQuizFound.setVisibility(View.GONE);
                binding.myQuizRecycler.setVisibility(View.VISIBLE);
            }

        });
        int timeout = 10000; // 10 seconds
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArrayRequest);
    }

    private void quizQuestionget() {
        User user = Paper.book().read("User");
        progressDialog.show();
        String url = ApiEndpoints.BASE_URL+ApiEndpoints.GET_ALL_QUIZ_QUESTIONS;
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
                            QuizQuestions course = gson.fromJson(jsonArray.getJSONObject(i).toString(), QuizQuestions.class);
                            Log.i(TAG, "" + course.getQuizCatid());
                            quizQuestions.add(course);

                        }

                        if (quizQuestions.size() == 0) {
                            binding.noMyQuizFound.setVisibility(View.VISIBLE);
                            binding.myQuizRecycler.setVisibility(View.GONE);
                        } else {
                            binding.myQuizRecycler.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            binding.noMyQuizFound.setVisibility(View.GONE);
                            binding.myQuizRecycler.setVisibility(View.VISIBLE);
                        }
                        Log.i(TAG, "List size: " + quizQuestions.size());
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
            if (quizQuestions.size() == 0) {
                binding.noMyQuizFound.setVisibility(View.VISIBLE);
                binding.myQuizRecycler.setVisibility(View.GONE);
            } else {
                binding.myQuizRecycler.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                binding.noMyQuizFound.setVisibility(View.GONE);
                binding.myQuizRecycler.setVisibility(View.VISIBLE);
            }

        });
        int timeout = 10000; // 10 seconds
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArrayRequest);
    }


}