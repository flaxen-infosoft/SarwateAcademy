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
import com.flaxeninfosoft.sarwateAcademy.adapters.CourseNotesAdapter;
import com.flaxeninfosoft.sarwateAcademy.api.ApiEndpoints;
import com.flaxeninfosoft.sarwateAcademy.api.Constants;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentMyCourseNotesBinding;
import com.flaxeninfosoft.sarwateAcademy.models.Course;
import com.flaxeninfosoft.sarwateAcademy.models.StudyMaterial;
import com.flaxeninfosoft.sarwateAcademy.models.User;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.paperdb.Paper;


public class MyCourseNotesFragment extends Fragment {


    public MyCourseNotesFragment() {
        // Required empty public constructor
    }

    FragmentMyCourseNotesBinding binding;
    RequestQueue requestQueue;
    Gson gson;
    ProgressDialog progressDialog;
    public static final String TAG = "MYCOURSE-NOTES-LOG";
    CourseNotesAdapter adapter;
    List<StudyMaterial> studyMaterialList;
    Course course;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_course_notes, container, false);
        binding.addNotesButton.setOnClickListener(this::onClickAddNotes);
        course = Paper.book().read("CurrentMyCourse");
        binding.notesRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        studyMaterialList = new ArrayList<>();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("please wait..");
        requestQueue = Volley.newRequestQueue(getContext());
        gson = new Gson();
        getNotes();
        adapter = new CourseNotesAdapter(studyMaterialList, this::onClickNotes);

        return binding.getRoot();
    }

    private void onClickNotes(StudyMaterial studyMaterial) {
        Paper.book().write("Current_StudyMaterial",studyMaterial);
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_singleMyCourseHomeFragment_to_studyMaterialDetailsFragment);
    }

    private void onClickAddNotes(View view) {
        Log.i(TAG, "ONClickCourseID " + course.getCourseId());
        Navigation.findNavController(view).navigate(R.id.action_singleMyCourseHomeFragment_to_teacherAddNoteFragment);
    }


    private void getNotes() {
        User user = Paper.book().read("User");
        studyMaterialList.clear();
        progressDialog.show();
        String url = ApiEndpoints.BASE_URL + ApiEndpoints.GET_STUDY_MATERIAL_BY_COURSE_ID;
        Log.i(TAG, String.valueOf(user.getId()));
        HashMap<String, Object> hashMap = new HashMap<>();
        if (course.getCourseId() != null) {
            hashMap.put(Constants.COURSE_ID, course.getCourseId());
        }
        Log.i(TAG, "getNotes " + hashMap);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap), response -> {

            progressDialog.dismiss();
            try {
                if (response != null) {
                    Log.i(TAG, String.valueOf(response));
                    if (response.getString("success").equals("1")) {
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            StudyMaterial studyMaterial = gson.fromJson(String.valueOf(jsonArray.getJSONObject(i)), StudyMaterial.class);
                            Log.i(TAG, studyMaterial.getFileName());
                            studyMaterialList.add(studyMaterial);
                        }
                        if (studyMaterialList.size()==0) {
                            binding.noNotesFound.setVisibility(View.VISIBLE);
                            binding.notesRecycler.setVisibility(View.GONE);
                        } else {
                            binding.notesRecycler.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            binding.noNotesFound.setVisibility(View.GONE);
                            binding.notesRecycler.setVisibility(View.VISIBLE);
                        }

                    } else {
                        Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                        if (studyMaterialList.size()==0) {
                            binding.noNotesFound.setVisibility(View.VISIBLE);
                            binding.notesRecycler.setVisibility(View.GONE);
                        } else {
                            binding.notesRecycler.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            binding.noNotesFound.setVisibility(View.GONE);
                            binding.notesRecycler.setVisibility(View.VISIBLE);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            progressDialog.dismiss();
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            if (studyMaterialList.size()==0) {
                binding.noNotesFound.setVisibility(View.VISIBLE);
                binding.notesRecycler.setVisibility(View.GONE);
            } else {
                binding.notesRecycler.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                binding.noNotesFound.setVisibility(View.GONE);
                binding.notesRecycler.setVisibility(View.VISIBLE);
            }
            Log.i(TAG, String.valueOf(error));
        });
        int timeout = 25000; // 25 seconds
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);


    }
}