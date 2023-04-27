package com.flaxeninfosoft.sarwateAcademy.views.teacherFragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentTeacherStudentProfileBinding;
import com.flaxeninfosoft.sarwateAcademy.models.User;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import io.paperdb.Paper;

public class TeacherStudentProfileFragment extends Fragment {

    private FragmentTeacherStudentProfileBinding binding;
    User user;
    RequestQueue requestQueue;
    Gson gson;
    ProgressDialog progressDialog;
    public static final String TAG = "STUDENT-DETAILS-LOG";

    public TeacherStudentProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_teacher_student_profile, container, false);
        binding.backImageView.setOnClickListener(this::onClickBack);
        user = Paper.book().read("SelectedStudent");
        binding.setUser(user);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Getting Details");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading please wait..");
        requestQueue = Volley.newRequestQueue(getContext());
        gson = new Gson();

        return binding.getRoot();
    }

    private void onClickBack(View view) {
        Navigation.findNavController(view).navigateUp();
    }

    private void getStudentDetails() {
        progressDialog.show();
        String url = ApiEndpoints.BASE_URL + ApiEndpoints.GET_STUDENT_BY_ID;
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(Constants.ROLE, user.getId());

        Log.i(TAG, hashMap.toString());
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap), response -> {

            progressDialog.dismiss();
            try {
                if (response != null) {
                    if (response.getString("success").equals("1")) {
                        Log.i(TAG, String.valueOf(response));
                        User user1 = gson.fromJson(response.getString("data"), User.class);
                        binding.setUser(user1);

                    } else {
                        Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            progressDialog.dismiss();
            Toast.makeText(getContext(), error+"", Toast.LENGTH_SHORT).show();
            Log.i(TAG, String.valueOf(error));
        });
        int timeout = 10000; // 10 seconds
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArrayRequest);
    }
}