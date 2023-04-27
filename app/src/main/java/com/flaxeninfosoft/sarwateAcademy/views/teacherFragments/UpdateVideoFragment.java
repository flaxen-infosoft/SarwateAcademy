package com.flaxeninfosoft.sarwateAcademy.views.teacherFragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.api.ApiEndpoints;
import com.flaxeninfosoft.sarwateAcademy.api.Constants;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentUpdateVideoBinding;
import com.flaxeninfosoft.sarwateAcademy.models.Course;
import com.flaxeninfosoft.sarwateAcademy.models.User;
import com.flaxeninfosoft.sarwateAcademy.models.Video;
import com.flaxeninfosoft.sarwateAcademy.utils.FileEncoder;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.gson.Gson;

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


public class UpdateVideoFragment extends Fragment {


    public UpdateVideoFragment() {
        // Required empty public constructor
    }


    Video video;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    Gson gson;
    Uri imageUri;
    String encodedImage = " ";
    String videoDate = "", videoTime = "";
    public static final String TAG = "UPDATE-VIDEO-LOG";
    String[] freeOrPaidOptions = {"Please Select Free or Paid", "Free", "Paid"};
    Course course;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentUpdateVideoBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_update_video, container, false);
        video = Paper.book().read("Current_Video");
        binding.updateVideoButton.setOnClickListener(this::onClickUpdate);
        binding.setVideo(video);
        course = Paper.book().read("CurrentMyCourse");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, freeOrPaidOptions);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.freeOrPaidSpinner.setAdapter(spinnerAdapter);
        binding.addImageIcon.setOnClickListener(this::onCLickAddImage);
        binding.timeTextInput.setOnFocusChangeListener(this::getTime);
        binding.timeTextInput.setOnClickListener(this::getTime);
        binding.dateTextInput.setOnFocusChangeListener(this::getDate);
        binding.dateTextInput.setOnClickListener(this::getDate);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Updating Video");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading please wait..");
        requestQueue = Volley.newRequestQueue(getContext());
        gson = new Gson();
        if (video.getIsFree().equals("Free")) {
            binding.freeOrPaidSpinner.setSelection(1);
        } else if (video.getIsFree().equals("Paid")) {
            binding.freeOrPaidSpinner.setSelection(2);
        } else {
            binding.freeOrPaidSpinner.setSelection(0);
        }

        return binding.getRoot();
    }

    private void onCLickAddImage(View view) {
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

    private void onClickUpdate(View view) {
        String selectedFreeOrPaid = binding.freeOrPaidSpinner.getSelectedItem().toString();
        if (binding.titleTextInput.getText().toString().isEmpty()) {
            binding.titleTextInput.setError("Required Field");
        } else if (binding.descriptionTextInput.getText().toString().isEmpty()) {
            binding.descriptionTextInput.setError("Required Field");
        } else if (binding.dateTextInput.getText().toString().isEmpty()) {
            binding.dateTextInput.setError("Required Field");
        } else if (binding.languageTextInput.getText().toString().isEmpty()) {
            binding.languageTextInput.setError("Required Field");
        } else if (binding.languageTextInput.getText().toString().isEmpty()) {
            binding.languageTextInput.setError("Required Field");
        } else if (binding.durationTextInput.getText().toString().isEmpty()) {
            binding.durationTextInput.setError("Required Field");
        } else if (binding.durationTextInput.getText().toString().isEmpty()) {
            binding.durationTextInput.setError("Required Field");
        } else if (encodedImage.isEmpty()) {
            Toast.makeText(getContext(), "Course Image Required.", Toast.LENGTH_SHORT).show();
        } else {
            if (selectedFreeOrPaid.equals("Free")) {
                updateVideo("Free");
            } else if (selectedFreeOrPaid.equals("Paid")) {
                updateVideo("Paid");
            } else {
                Toast.makeText(getContext(), "Please Select Notes are free or not.", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void updateVideo(String freeOrPaid) {
        User user = Paper.book().read("User");
        progressDialog.show();
        String url = ApiEndpoints.BASE_URL + ApiEndpoints.UPDATE_VIDEO;
        Log.i(TAG, String.valueOf(user.getId()));
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(Constants.COURSE_ID, course.getCourseId());
        hashMap.put(Constants.ID, video.getId());
        hashMap.put("title", binding.titleTextInput.getText().toString());
        hashMap.put("description", binding.descriptionTextInput.getText().toString());
        hashMap.put("videoLink", binding.videolinkTextInput.getText().toString());
        if (encodedImage.isEmpty()) {
            hashMap.put("image", " ");
        } else {
            hashMap.put("image", encodedImage);
        }
        if (videoDate.isEmpty()) {
            hashMap.put("date", video.getDate());
        } else {
            hashMap.put("date", videoDate);
        }

        if (videoTime.isEmpty()) {
            hashMap.put("time", videoTime);
        } else {
            hashMap.put("time", video.getTime());
        }

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
        int timeout = 15000; // 15 seconds
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }

    private void encodeImage(Uri imageUri) {
        if (imageUri != null || !imageUri.isAbsolute()) {
            try {
                encodedImage = FileEncoder.encodeImage(getContext().getContentResolver(), imageUri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                imageUri = data.getData();
                encodeImage(imageUri);

            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Cancelled !", Toast.LENGTH_SHORT).show();
            }
        }
    }


}