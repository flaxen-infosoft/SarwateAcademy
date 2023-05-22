package com.flaxeninfosoft.sarwateAcademy.views.userFragments.mycourse;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.adapters.QuizCategoryAdapter;
import com.flaxeninfosoft.sarwateAcademy.adapters.QuizRecyclerAdapter;
import com.flaxeninfosoft.sarwateAcademy.api.ApiEndpoints;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentAllQuizBinding;
import com.flaxeninfosoft.sarwateAcademy.models.Quiz;
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

    public static final String TAG = "ALL-QUIZ-LOG";

    public AllQuizFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    QuizRecyclerAdapter adapter;
    QuizCategoryAdapter quizCategoryAdapter;
    List<Quiz> quizList;

    List<Quiz> categorisedCourseList;
    List<QuizCategory> quizCategoryList;
    RequestQueue requestQueue;
    Gson gson;
    Quiz quiz;
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
        quizList = new LinkedList<>();
        quizCategoryList = new ArrayList<>();

//        getQuizById();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.categoryRecyclerview.setLayoutManager(layoutManager);
        quizCategoryAdapter = new QuizCategoryAdapter(quizCategoryList, this::onClickCategory);
        binding.categoryRecyclerview.setAdapter(quizCategoryAdapter);
        adapter = new QuizRecyclerAdapter(quizList, this::onClickBatch);
        if (quizList.isEmpty()) {
            binding.noDataFoundLayout.setVisibility(View.VISIBLE);
            binding.liveQuizRecycler.setVisibility(View.GONE);
        } else {
            binding.liveQuizRecycler.setAdapter(adapter);
            binding.noDataFoundLayout.setVisibility(View.GONE);
            binding.liveQuizRecycler.setVisibility(View.VISIBLE);
        }

        getAllCategory();
        getAllQuiz(0L);

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
    private void onClickCategory(QuizCategory quizCategory) {
        Toast.makeText(getContext(), quizCategory.getId() + "", Toast.LENGTH_SHORT).show();
        if (quizCategory.getId() == 0) {
            getAllQuiz(0L);
        } else {
            getAllQuiz(quizCategory.getId());
        }
    }

    private void onSwipeRefresh() {
        getAllQuiz(0L);
    }

    private void onClickBack(View view) {
        Navigation.findNavController(view).navigateUp();
    }
    private void filter(String text) {
        List<Quiz> quizList1 = new ArrayList<>();
        for (Quiz quiz : quizList) {
            if (quiz.getName().toLowerCase().contains(text.toLowerCase())
                    || quiz.getPrice().toLowerCase().contains(text.toLowerCase())
            ) {
                quizList1.add(quiz);
            }
        }

        if (quizList1.isEmpty()) {
            binding.noDataFoundLayout.setVisibility(View.VISIBLE);
            binding.liveQuizRecycler.setVisibility(View.GONE);
        } else {
            adapter.filterList(quizList1);
            binding.noDataFoundLayout.setVisibility(View.GONE);
            binding.liveQuizRecycler.setVisibility(View.VISIBLE);
        }
    }
    private void onClickBatch(Quiz quiz) {
        Paper.book().write("Current_Quiz", quiz);
        Navigation.findNavController(binding.getRoot()).navigate(R.id.quizDescriptionFragment);
    }

    private void getAllQuiz(Long category_id) {
        binding.liveBatchesSwipeRefresh.setRefreshing(false);
        quizList.clear();

        progressDialog.show();
        String url = ApiEndpoints.BASE_URL + ApiEndpoints.GET_ALL_QUIZ_DATA;
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {

            progressDialog.dismiss();
            try {
                if (response != null) {
                    Log.i(TAG, String.valueOf(response));
                    if (response.getString("success").equals("1")) {
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Quiz quiz = gson.fromJson(jsonArray.getJSONObject(i).toString(), Quiz.class);
                            if (category_id!=0) {
                                if (category_id.equals(quiz.getCatId())) {
                                    quizList.add(quiz);
                                }
                            }
                            else {
                                quizList.add(quiz);
                            }

                        }
                        if (quizList.size() == 0) {
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
                        if (quizList.size() == 0) {
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
            if (quizList.size() == 0) {
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
        @SuppressLint("NotifyDataSetChanged") JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                if (response != null) {
                    Log.i(TAG, String.valueOf(response));
                    if (response.getString("success").equals("1")) {
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            QuizCategory quizCategory = gson.fromJson(jsonArray.getJSONObject(i).toString(), QuizCategory.class);
                            quizCategoryList.add(quizCategory);
                        }
                        QuizCategory quizCategory = new QuizCategory();
                        quizCategory.setId(0L);
                        quizCategory.setQuiz_category_name("All");
                        quizCategoryList.add(0,quizCategory);
                        quizCategoryAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getContext(), response.getString("Category Not Found."), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            catch (JSONException e) {
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
//    private void getQuizCatID() {
//        User user = Paper.book().read("User");
//
//
//        String url = ApiEndpoints.BASE_URL + ApiEndpoints.GET_ALL_QUIZ_BY_CATEGORY_ID;
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("quizId", quiz.getCatId());
//
//        Log.i(TAG, "myQuiz" + hashMap);
//
//        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap), response -> {
//
//            progressDialog.dismiss();
//            try {
//                if (response != null) {
//                    Log.i(TAG, String.valueOf(response));
//                    if (response.getString("success").equals("1")) {
//                        JSONArray jsonArray = response.getJSONArray("data");
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            Quiz quiz1 = gson.fromJson(jsonArray.getJSONObject(i).toString(), Quiz.class);
//                            Log.i(TAG, String.valueOf(quiz1.getId()));
//                            quizList.add(quiz1);
//                            Log.i(TAG, quiz1.getName());
//                        }
//                        if (quizList.size() == 0) {
//                            binding.noDataFoundLayout.setVisibility(View.VISIBLE);
//                            binding.liveQuizRecycler.setVisibility(View.GONE);
//                        } else {
//                            binding.liveQuizRecycler.setAdapter(adapter);
//                            adapter.notifyDataSetChanged();
//                            binding.noDataFoundLayout.setVisibility(View.GONE);
//                            binding.liveQuizRecycler.setVisibility(View.VISIBLE);
//
//                        }
//
//                    } else {
//                        Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
//                        if (quizList.size() == 0) {
//                            binding.noDataFoundLayout.setVisibility(View.VISIBLE);
//                            binding.liveQuizRecycler.setVisibility(View.GONE);
//                        } else {
//                            binding.liveQuizRecycler.setAdapter(adapter);
//                            adapter.notifyDataSetChanged();
//                            binding.noDataFoundLayout.setVisibility(View.GONE);
//                            binding.liveQuizRecycler.setVisibility(View.VISIBLE);
//
//                        }
//                    }
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }, error -> {
//            progressDialog.dismiss();
//            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
//            if (quizList.size() == 0) {
//                binding.noDataFoundLayout.setVisibility(View.VISIBLE);
//                binding.liveQuizRecycler.setVisibility(View.GONE);
//            } else {
//                binding.liveQuizRecycler.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//                binding.noDataFoundLayout.setVisibility(View.GONE);
//                binding.liveQuizRecycler.setVisibility(View.VISIBLE);
//
//            }
//            Log.i(TAG, String.valueOf(error));
//        });
//        int timeout = 10000; // 10 seconds
//        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        requestQueue.add(jsonArrayRequest);
//
//
//    }

    }