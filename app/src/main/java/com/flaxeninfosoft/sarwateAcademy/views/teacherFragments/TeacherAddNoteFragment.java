package com.flaxeninfosoft.sarwateAcademy.views.teacherFragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.api.ApiEndpoints;
import com.flaxeninfosoft.sarwateAcademy.api.Constants;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentTeacherAddNoteBinding;
import com.flaxeninfosoft.sarwateAcademy.models.Course;
import com.flaxeninfosoft.sarwateAcademy.models.StudyMaterial;
import com.flaxeninfosoft.sarwateAcademy.models.User;
import com.flaxeninfosoft.sarwateAcademy.utils.FileEncoder;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import io.paperdb.Paper;

public class TeacherAddNoteFragment extends Fragment {

    private FragmentTeacherAddNoteBinding binding;
    RequestQueue requestQueue;
    Gson gson;
    ProgressDialog progressDialog;
    Uri studyMaterialURI;
    String encodedStudyMaterial = " ";
    public static final String TAG = "ADD-NOTE-LOG";
    Course course;
    String[] freeOrPaidOptions = {"Please Select Free or Paid", "Free", "Paid"};
    String notesAddDate;

    public TeacherAddNoteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_teacher_add_note, container, false);
        course = Paper.book().read("CurrentMyCourse");
        binding.dateTextInput.setOnFocusChangeListener(this::getDate);
        binding.dateTextInput.setOnClickListener(this::getDate);
        binding.addNotesIcon.setOnClickListener(this::onClickAddNotes);
        binding.backImageView.setOnClickListener(this::onClickBack);
        binding.teacherDoneButton.setOnClickListener(this::onClickDone);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, freeOrPaidOptions);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.freeOrPaidSpinner.setAdapter(spinnerAdapter);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Adding Notes");
        progressDialog.setMessage("Loading please wait..");
        requestQueue = Volley.newRequestQueue(getContext());
        gson = new Gson();

        return binding.getRoot();
    }

    private void onClickDone(View view) {
        String selectedFreeOrPaid = binding.freeOrPaidSpinner.getSelectedItem().toString();
        if (binding.notesTitleTextInput.getText().toString().isEmpty()) {
            binding.notesTitleTextInput.setError("Required Field");
        } else if (binding.notesDescriptionTextInput.getText().toString().isEmpty()) {
            binding.notesDescriptionTextInput.setError("Required Field");
        }
        else if (encodedStudyMaterial.isEmpty()) {
            Toast.makeText(getContext(), "Please Add Any File.", Toast.LENGTH_SHORT).show();
        }
        else {
            if (selectedFreeOrPaid.equals("Free")) {
                addNotes("Free");
            } else if (selectedFreeOrPaid.equals("Paid")) {
                addNotes("Paid");
            } else {
                Toast.makeText(getContext(), "Please Select Notes are free or not.", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void onClickBack(View view) {
        Navigation.findNavController(view).navigateUp();
    }

    private void onClickAddNotes(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, 100);

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
                    notesAddDate = format.format(date);
                }
            }, y, m, d);
            datePickerDialog.show();
        }
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        ProgressDialog progressDialog;
//        if (requestCode == 100) {
//
//            progressDialog = new ProgressDialog(getContext());
//            progressDialog.setCancelable(false);
//            progressDialog.setMessage("Uploading");
//
//            if (data != null) {
//                progressDialog.show();
//                Uri uri = data.getData();
//                binding.noteFileName.setText(uri.toString());
//                progressDialog.dismiss();
//            }
//
//        }
//    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            studyMaterialURI = data.getData();
            binding.noteFileName.setText(getFileNameFromUri(studyMaterialURI));
            encodedFile(studyMaterialURI);

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Cancelled !", Toast.LENGTH_SHORT).show();
        }
    }


    private void addNotes(String freeOrPaid) {
        User user = Paper.book().read("User");
        progressDialog.show();

        String url = ApiEndpoints.BASE_URL + ApiEndpoints.ADD_STUDY_MATERIAL;
        Log.i(TAG, String.valueOf(user.getId()));
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(Constants.COURSE_ID, course.getCourseId());
        hashMap.put("fileName", binding.notesTitleTextInput.getText().toString());
        hashMap.put("description", binding.notesDescriptionTextInput.getText().toString());
        hashMap.put("filePath", encodedStudyMaterial);
        hashMap.put("date", notesAddDate);
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
                        Toast.makeText(getContext(), studyMaterial.getFileName() + " Successfully Uploaded.", Toast.LENGTH_SHORT).show();
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
            Log.i(TAG, String.valueOf(error));
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void encodedFile(Uri imageUri) {
        if (imageUri != null || !imageUri.isAbsolute()) {
            try {
                encodedStudyMaterial = FileEncoder.encodeImage(getContext().getContentResolver(), imageUri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String getFileNameFromUri(Uri uri) {
        String fileName = null;
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int displayNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            if (displayNameIndex != -1) {
                fileName = cursor.getString(displayNameIndex);
            }
            cursor.close();
        }
        return fileName;
    }
}