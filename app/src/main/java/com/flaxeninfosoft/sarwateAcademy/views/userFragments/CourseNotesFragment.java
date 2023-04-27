package com.flaxeninfosoft.sarwateAcademy.views.userFragments;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Environment;
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
import com.flaxeninfosoft.sarwateAcademy.adapters.CourseVideosAdapter;
import com.flaxeninfosoft.sarwateAcademy.adapters.NonPurchasedNotesAdapter;
import com.flaxeninfosoft.sarwateAcademy.api.ApiEndpoints;
import com.flaxeninfosoft.sarwateAcademy.api.Constants;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentBatchNotesBinding;
import com.flaxeninfosoft.sarwateAcademy.models.Course;
import com.flaxeninfosoft.sarwateAcademy.models.StudyMaterial;
import com.flaxeninfosoft.sarwateAcademy.models.User;
import com.flaxeninfosoft.sarwateAcademy.models.Video;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.paperdb.Paper;


public class CourseNotesFragment extends Fragment {


    public CourseNotesFragment() {
        // Required empty public constructor
    }

    FragmentBatchNotesBinding binding;
    public static final String TAG = "STUDENT-COURSE_NOTES-LOG";
    RequestQueue requestQueue;
    Gson gson;
    ProgressDialog progressDialog;
    Course course;
    List<StudyMaterial> studyMaterialList;
    NonPurchasedNotesAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        course = Paper.book().read("Current_Course");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_batch_notes, container, false);
        binding.notesCourseRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressDialog = new ProgressDialog(getContext());
        course = Paper.book().read("Current_Course");
        progressDialog.setTitle("Wait");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading please wait..");
        requestQueue = Volley.newRequestQueue(getContext());
        gson = new Gson();
        studyMaterialList = new ArrayList<>();
        adapter = new NonPurchasedNotesAdapter(studyMaterialList, this::onClickNotes);
        getNotes();

        if (studyMaterialList.isEmpty()) {
            binding.noNotesFound.setVisibility(View.VISIBLE);
            binding.notesCourseRecycler.setVisibility(View.GONE);
        } else {
            binding.notesCourseRecycler.setAdapter(adapter);
            binding.noNotesFound.setVisibility(View.GONE);
            binding.notesCourseRecycler.setVisibility(View.VISIBLE);
        }

        return binding.getRoot();
    }

    private void onClickNotes(StudyMaterial studyMaterial) {
        if (studyMaterial.getIsFree().equals("Free")){
            downloadFile(studyMaterial);
        }
        else {

        }
    }

    private void downloadFile(StudyMaterial studyMaterial) {
        Toast.makeText(getContext(), "Downloading please check in download folder.", Toast.LENGTH_SHORT).show();
        String url = "http://103.118.17.202/~mkeducation/MK_API/User/" + studyMaterial.getFilePath(); // Replace this with the URL of the file
        String folderName = "SarwateAcademy"; // Replace this with the desired name of the new folder
        String fileName = studyMaterial.getFileName(); // Replace this with the desired name of the downloaded file
        File folder = new File(Environment.getExternalStorageDirectory() + "/" + folderName); // Create a new directory in the external storage
        if (!folder.exists()) {
            folder.mkdir();
        }
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle(studyMaterial.getFileName()); // Set the title of the download notification
        request.setDescription("Downloading"); // Set the description of the download notification
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); // Show a notification when the download is complete
        if (getExtension(url).equals(".pdf")) {
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName + ".pdf"); // Save the file to the Downloads folder with the name "filename.pdf"
        } else if (getExtension(url).equals(".png")) {
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName + ".png"); // Save the file to the Downloads folder with the name "filename.pdf"
        } else if (getExtension(url).equals(".jpeg")) {
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName + ".jpeg"); // Save the file to the Downloads folder with the name "filename.pdf"
        } else if (getExtension(url).equals(".txt")) {
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName + ".txt"); // Save the file to the Downloads folder with the name "filename.pdf"
        } else if (getExtension(url).equals(".jpg")) {
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName + ".jpg"); // Save the file to the Downloads folder with the name "filename.pdf"
        } else {
            Toast.makeText(getContext(), "File Not Found.", Toast.LENGTH_SHORT).show();
        }
        DownloadManager manager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);

    }

    public String getExtension(String urlString) {
        String fileExtension = "";
        try {
            URL url1 = new URL(urlString);
            String fileName = url1.getFile();
            fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1);
            System.out.println("File extension: " + fileExtension);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileExtension;
    }


    private void getNotes() {
        User user = Paper.book().read("User");
        studyMaterialList.clear();
        progressDialog.show();
        String url = ApiEndpoints.BASE_URL + ApiEndpoints.GET_STUDY_MATERIAL_BY_COURSE_ID;
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("courseId", course.getId());
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
                        if (studyMaterialList.size() == 0) {
                            binding.noNotesFound.setVisibility(View.VISIBLE);
                            binding.notesCourseRecycler.setVisibility(View.GONE);
                        } else {
                            binding.notesCourseRecycler.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            binding.noNotesFound.setVisibility(View.GONE);
                            binding.notesCourseRecycler.setVisibility(View.VISIBLE);
                        }

                    } else {
                        Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                        if (studyMaterialList.size() == 0) {
                            binding.noNotesFound.setVisibility(View.VISIBLE);
                            binding.notesCourseRecycler.setVisibility(View.GONE);
                        } else {
                            binding.notesCourseRecycler.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            binding.noNotesFound.setVisibility(View.GONE);
                            binding.notesCourseRecycler.setVisibility(View.VISIBLE);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            progressDialog.dismiss();
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            if (studyMaterialList.size() == 0) {
                binding.noNotesFound.setVisibility(View.VISIBLE);
                binding.notesCourseRecycler.setVisibility(View.GONE);
            } else {
                binding.notesCourseRecycler.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                binding.noNotesFound.setVisibility(View.GONE);
                binding.notesCourseRecycler.setVisibility(View.VISIBLE);
            }
            Log.i(TAG, String.valueOf(error));
        });
        int timeout = 10000; // 10 seconds
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);


    }
}