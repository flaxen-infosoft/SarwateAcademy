package com.flaxeninfosoft.sarwateAcademy.views.teacherFragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.api.ApiEndpoints;
import com.flaxeninfosoft.sarwateAcademy.api.Constants;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentUpdateStudyMaterialBinding;
import com.flaxeninfosoft.sarwateAcademy.models.Course;
import com.flaxeninfosoft.sarwateAcademy.models.StudyMaterial;
import com.flaxeninfosoft.sarwateAcademy.models.User;
import com.flaxeninfosoft.sarwateAcademy.utils.FileEncoder;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import io.paperdb.Paper;


public class UpdateStudyMaterialFragment extends Fragment {
    public UpdateStudyMaterialFragment() {
        // Required empty public constructor
    }


    FragmentUpdateStudyMaterialBinding binding;
    StudyMaterial studyMaterial;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    Gson gson;
    Uri imageUri;
    String encodedImage = " ";
    String studyMaterialDate="";
    Course course;
    public static final String TAG = "UPDATE-STUDYMATERIAL-LOG";
    String[] freeOrPaidOptions = {"Please Select Free or Paid", "Free", "Paid"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_update_study_material, container, false);
        studyMaterial = Paper.book().read("Current_StudyMaterial");
        course = Paper.book().read("CurrentMyCourse");
        binding.setStudyMaterial(studyMaterial);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, freeOrPaidOptions);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.freeOrPaidSpinner.setAdapter(spinnerAdapter);
        binding.dateTextInput.setOnFocusChangeListener(this::getDate);
        binding.dateTextInput.setOnClickListener(this::getDate);
        binding.addNotesIcon.setOnClickListener(this::onClickAddNotes);
        binding.teacherDoneButton.setOnClickListener(this::onClickUpdate);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Updating StudyMaterial");
        progressDialog.setMessage("Loading please wait..");
        progressDialog.setCancelable(false);
        requestQueue = Volley.newRequestQueue(getContext());
        gson = new Gson();
        if (studyMaterial.getIsFree().equals("Free")) {
            binding.freeOrPaidSpinner.setSelection(1);
        } else if (studyMaterial.getIsFree().equals("Paid")) {
            binding.freeOrPaidSpinner.setSelection(2);
        } else {
            binding.freeOrPaidSpinner.setSelection(0);
        }

        return binding.getRoot();


    }

    private void onClickAddNotes(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, 100);
    }

    private void onClickUpdate(View view) {
        String selectedFreeOrPaid = binding.freeOrPaidSpinner.getSelectedItem().toString();
        if (binding.notesTitleTextInput.getText().toString().isEmpty()) {
            binding.notesTitleTextInput.setError("Required Field");
        } else if (binding.notesDescriptionTextInput.getText().toString().isEmpty()) {
            binding.notesDescriptionTextInput.setError("Required Field");
        }
        else if (encodedImage.isEmpty()) {
            Toast.makeText(getContext(), "Please Add Any File.", Toast.LENGTH_SHORT).show();
        }
        else {
            if (selectedFreeOrPaid.equals("Free")) {
                updateNotes("Free");
            } else if (selectedFreeOrPaid.equals("Paid")) {
                updateNotes("Paid");
            } else {
                Toast.makeText(getContext(), "Please Select Notes are free or not.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getDate(View view) {
        getDate(view, true);
    }

    private void getDate(View view, boolean b) {
        if (b) {
            final Calendar c = Calendar.getInstance();
            int y = c.get(Calendar.YEAR);
            int m = c.get(Calendar.MONTH);
            int d = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    Date date = new Date(year, month, day);
                    Format format = new SimpleDateFormat("dd/MM/20yy");
                    binding.dateTextInput.setText(format.format(date));
                    studyMaterialDate = format.format(date);
                }
            }, y, m, d);
            datePickerDialog.show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            imageUri = data.getData();
            encodedFile(imageUri);

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Cancelled !", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateNotes(String freeOrPaid) {
        User user = Paper.book().read("User");
        progressDialog.show();

        String url = ApiEndpoints.BASE_URL + ApiEndpoints.UPDATE_NOTES;
        Log.i(TAG, String.valueOf(user.getId()));
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(Constants.COURSE_ID, course.getCourseId());
        hashMap.put(Constants.ID, studyMaterial.getId());
        hashMap.put("fileName", binding.notesTitleTextInput.getText().toString());
        hashMap.put("description", binding.notesDescriptionTextInput.getText().toString());
        if (encodedImage.isEmpty()) {
            hashMap.put("filePath", " ");
        } else {
            hashMap.put("filePath", encodedImage);
        }
        if (studyMaterialDate.isEmpty()) {
            hashMap.put("date", studyMaterial.getDate());
        } else {
            hashMap.put("date", studyMaterialDate);
        }
        hashMap.put("teacherId", user.getId());
        hashMap.put("isFree", freeOrPaid);

        Log.i(TAG, hashMap.toString());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap), response -> {

            progressDialog.dismiss();
            try {
                if (response != null) {
                    Log.i(TAG, String.valueOf(response));
                    if (response.getString("success").equals("1")) {
                        StudyMaterial studyMaterial = gson.fromJson(response.getString("data"), StudyMaterial.class);
                        Toast.makeText(getContext(), studyMaterial.getFileName() + " Successfully Updated.", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(binding.getRoot()).navigateUp();

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
            Log.i(TAG, String.valueOf(error));
        });
        int timeout = 25000; // 25 seconds
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }

    private void encodedFile(Uri imageUri) {
        if (imageUri != null || !imageUri.isAbsolute()) {
            try {
                encodedImage = FileEncoder.encodeImage(getContext().getContentResolver(), imageUri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}