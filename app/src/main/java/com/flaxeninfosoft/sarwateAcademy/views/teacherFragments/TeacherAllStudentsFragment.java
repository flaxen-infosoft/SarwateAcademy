package com.flaxeninfosoft.sarwateAcademy.views.teacherFragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.flaxeninfosoft.sarwateAcademy.adapters.AllStudentsRecyclerAdapter;
import com.flaxeninfosoft.sarwateAcademy.api.ApiEndpoints;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentTeacherAllStudentsBinding;
import com.flaxeninfosoft.sarwateAcademy.models.User;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class TeacherAllStudentsFragment extends Fragment {

    private FragmentTeacherAllStudentsBinding binding;
    public static final String TAG = "ALL-STUDENT-LOG";
    List<User> userList;
    AllStudentsRecyclerAdapter adapter;
    RequestQueue requestQueue;
    Gson gson;
    ProgressDialog progressDialog;

    public TeacherAllStudentsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_teacher_all_students, container, false);

        binding.allStudentTeacherRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.backImageView.setOnClickListener(this::onClickBack);
        binding.allStudentTeacherSwipeRefresh.setOnRefreshListener(this::onClickSwipe);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Updating");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading please wait..");
        requestQueue = Volley.newRequestQueue(getContext());
        gson = new Gson();
        userList = new ArrayList<>();
        getAllStudents();
        adapter = new AllStudentsRecyclerAdapter(userList, this::onClickStudentCard);
        if (userList.isEmpty()) {
            binding.noDataFoundLayout.setVisibility(View.VISIBLE);
            binding.allStudentTeacherRecycler.setVisibility(View.GONE);
        } else {
            binding.allStudentTeacherRecycler.setAdapter(adapter);
            binding.noDataFoundLayout.setVisibility(View.GONE);
            binding.allStudentTeacherRecycler.setVisibility(View.VISIBLE);
        }

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

    private void onClickSwipe() {
        getAllStudents();
    }

    private void onClickBack(View view) {
        Navigation.findNavController(view).navigateUp();
    }

    private void filter(String text) {
        ArrayList<User> userArrayList = new ArrayList<>();
        for (User user : userList) {
            if (user.getName().toLowerCase().trim().contains(text.toLowerCase())
                    ||user.getEmail().toLowerCase().trim().contains(text.toLowerCase())
                    ||user.getCity().toLowerCase().trim().contains(text.toLowerCase())
                    ||user.getPhone().toLowerCase().trim().contains(text.toLowerCase())
                    ||user.getState().toLowerCase().trim().contains(text.toLowerCase())
                    ||user.getGender().toLowerCase().trim().contains(text.toLowerCase())){
                userArrayList.add(user);
            }
        }
        if (userArrayList.isEmpty()) {
            binding.noDataFoundLayout.setVisibility(View.VISIBLE);
            binding.allStudentTeacherRecycler.setVisibility(View.GONE);
        } else {
            adapter.filterList(userArrayList);
            binding.noDataFoundLayout.setVisibility(View.GONE);
            binding.allStudentTeacherRecycler.setVisibility(View.VISIBLE);
        }
    }

    private void onClickStudentCard(User user) {
        Paper.book().write("SelectedStudent",user);
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_teacherAllStudentsFragment_to_teacherStudentProfileFragment);
    }

    private void getAllStudents() {
        binding.allStudentTeacherSwipeRefresh.setRefreshing(false);
        userList.clear();
        User user = Paper.book().read("User");

        progressDialog.show();
        String url = ApiEndpoints.BASE_URL + ApiEndpoints.GET_ALL_STUDENT;
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url,null, response -> {

            progressDialog.dismiss();
            try {
                if (response != null) {
                    Log.i(TAG, String.valueOf(response));
                    if (response.getString("success").equals("1")) {
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            User user1 = gson.fromJson(jsonArray.getJSONObject(i).toString(), User.class);
                            Log.i(TAG, String.valueOf(user1.getId()));
                            userList.add(user1);
                            Log.i(TAG, user1.getName());
                        }
                        if (userList.size()==0) {
                            binding.noDataFoundLayout.setVisibility(View.VISIBLE);
                            binding.allStudentTeacherRecycler.setVisibility(View.GONE);
                        } else {
                            binding.allStudentTeacherRecycler.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            binding.noDataFoundLayout.setVisibility(View.GONE);
                            binding.allStudentTeacherRecycler.setVisibility(View.VISIBLE);
                        }

                    } else {
                        Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                        if (userList.size()==0) {
                            binding.noDataFoundLayout.setVisibility(View.VISIBLE);
                            binding.allStudentTeacherRecycler.setVisibility(View.GONE);
                        } else {
                            binding.allStudentTeacherRecycler.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            binding.noDataFoundLayout.setVisibility(View.GONE);
                            binding.allStudentTeacherRecycler.setVisibility(View.VISIBLE);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            progressDialog.dismiss();
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            if (userList.size()==0) {
                binding.noDataFoundLayout.setVisibility(View.VISIBLE);
                binding.allStudentTeacherRecycler.setVisibility(View.GONE);
            } else {
                binding.allStudentTeacherRecycler.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                binding.noDataFoundLayout.setVisibility(View.GONE);
                binding.allStudentTeacherRecycler.setVisibility(View.VISIBLE);
            }
            Log.i(TAG, String.valueOf(error));
        });
        int timeout = 10000; // 10 seconds
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArrayRequest);



    }
}