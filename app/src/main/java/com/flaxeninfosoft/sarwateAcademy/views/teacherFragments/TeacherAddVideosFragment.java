package com.flaxeninfosoft.sarwateAcademy.views.teacherFragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.api.ApiEndpoints;
import com.flaxeninfosoft.sarwateAcademy.api.Constants;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentTeacherAddVideosBinding;
import com.flaxeninfosoft.sarwateAcademy.models.Course;
import com.flaxeninfosoft.sarwateAcademy.models.User;
import com.flaxeninfosoft.sarwateAcademy.models.Video;
import com.flaxeninfosoft.sarwateAcademy.utils.FileEncoder;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Time;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import io.paperdb.Paper;

public class TeacherAddVideosFragment extends Fragment {

    private FragmentTeacherAddVideosBinding binding;
    RequestQueue requestQueue;
    Gson gson;
    ProgressDialog progressDialog;
    Course course;
    Uri videoThumbnailUri;
    String videoDate,videoTime;
    String encodedVideoThumbnail = " ";
    public static final String TAG = "ADD-VIDEO-LOG";
    String[] freeOrPaidOptions = {"Please Select Free or Paid", "Free", "Paid"};

    public TeacherAddVideosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_teacher_add_videos, container, false);
        course = Paper.book().read("CurrentMyCourse");
        binding.timeTextInput.setOnFocusChangeListener(this::getTime);
        binding.timeTextInput.setOnClickListener(this::getTime);
        binding.dateTextInput.setOnFocusChangeListener(this::getDate);
        binding.dateTextInput.setOnClickListener(this::getDate);
        binding.backImageView.setOnClickListener(this::onClickBack);
        binding.addImageIcon.setOnClickListener(this::onClickAddImage);
        binding.teacherDoneButton.setOnClickListener(this::onClickDone);
        binding.languageTextInput.setText(course.getLanguage());
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, freeOrPaidOptions);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.freeOrPaidSpinner.setAdapter(spinnerAdapter);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Adding Video");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading please wait..");
        requestQueue = Volley.newRequestQueue(getContext());
        gson = new Gson();
        return binding.getRoot();
    }

    private void onClickAddImage(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                videoThumbnailUri = data.getData();
                encodedFile(videoThumbnailUri);
                binding.imageNameTextView.setText(getFileNameFromUri(videoThumbnailUri));


            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Cancelled !", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void onClickDone(View view) {
        String selectedFreeOrPaid = binding.freeOrPaidSpinner.getSelectedItem().toString();
        if (binding.titleTextInput.getText().toString().isEmpty()) {
            binding.titleTextInput.setError("Required Field");
        } else if (binding.descriptionTextInput.getText().toString().isEmpty()) {
            binding.descriptionTextInput.setError("Required Field");
        } else if (binding.videolinkTextInput.getText().toString().isEmpty()) {
            binding.videolinkTextInput.setError("Required Field");
        } else if (binding.durationTextInput.getText().toString().isEmpty()) {
            binding.durationTextInput.setError("Required Field");
        } else if (encodedVideoThumbnail.isEmpty()) {
            Toast.makeText(getContext(), "Please Add Video Image", Toast.LENGTH_SHORT).show();
        } else {
            if (selectedFreeOrPaid.equals("Free")) {
                addVideo("Free");
            } else if (selectedFreeOrPaid.equals("Paid")) {
                addVideo("Paid");
            } else {
                Toast.makeText(getContext(), "Please Select Notes are free or not.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void onClickBack(View view) {
        Navigation.findNavController(view).navigateUp();
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
                    videoDate = format.format(date);

                }
            }, y, m, d);
            datePickerDialog.show();
        }
    }

    private void getTime(View view) {
        getTime(view, true);
    }

    private void getTime(View view, boolean b) {
        if (b) {
            final Calendar c = Calendar.getInstance();
            // on below line we are getting our hour, minute.
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                    Time tme = new Time(hour, minute, 0);//seconds by default set to zero
                    Format formatter = new SimpleDateFormat("hh:mm a");
                    binding.timeTextInput.setText(String.valueOf(formatter.format(tme)));
                    videoTime = formatter.format(tme);
                }
            }, hour, minute, false);

            timePickerDialog.show();
        }
    }

    private void addVideo(String freeOrPaid) {
        User user = Paper.book().read("User");
        progressDialog.show();
        String url = ApiEndpoints.BASE_URL + ApiEndpoints.ADD_VIDEO;
        Log.i(TAG, String.valueOf(user.getId()));
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(Constants.COURSE_ID, course.getCourseId());
        hashMap.put("title", binding.titleTextInput.getText().toString());
        hashMap.put("description", binding.descriptionTextInput.getText().toString());
        hashMap.put("videoLink", binding.videolinkTextInput.getText().toString());
        hashMap.put("image", encodedVideoThumbnail);
        hashMap.put("date", videoDate);
        hashMap.put("time", videoTime);
        hashMap.put("duration", binding.durationTextInput.getText().toString());
        hashMap.put("language", binding.languageTextInput.getText().toString());
        hashMap.put("teacherId", user.getId());
        hashMap.put("isFree", freeOrPaid);

        Log.i(TAG, hashMap.toString());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap), response -> {

            progressDialog.dismiss();
            try {
                if (response != null) {
                    Log.i(TAG, String.valueOf(response));
                    if (response.getString("success").equals("1")) {
                        Video video = gson.fromJson(response.getString("data"), Video.class);
                        Toast.makeText(getContext(), video.getTitle() + " Added successfully.", Toast.LENGTH_SHORT).show();
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
        int timeout = 10000; // 10 seconds
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }

    private void encodedFile(Uri imageUri) {
        if (imageUri != null || !imageUri.isAbsolute()) {
            try {
                encodedVideoThumbnail = FileEncoder.encodeImage(getContext().getContentResolver(), imageUri);
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