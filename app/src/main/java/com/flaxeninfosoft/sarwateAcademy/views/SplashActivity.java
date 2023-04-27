package com.flaxeninfosoft.sarwateAcademy.views;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.api.ApiEndpoints;
import com.flaxeninfosoft.sarwateAcademy.api.AuthApiInterface;
import com.flaxeninfosoft.sarwateAcademy.api.Constants;
import com.flaxeninfosoft.sarwateAcademy.api.RetrofitClient;
import com.flaxeninfosoft.sarwateAcademy.databinding.ActivitySplashBinding;
import com.flaxeninfosoft.sarwateAcademy.models.LoginModel;
import com.flaxeninfosoft.sarwateAcademy.models.User;
import com.flaxeninfosoft.sarwateAcademy.utils.SharedPrefs;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;
    private SharedPrefs sharedPrefs;
    RequestQueue requestQueue;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        requestQueue = Volley.newRequestQueue(this);
        gson = new Gson();


        sharedPrefs = new SharedPrefs(SplashActivity.this);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sharedPrefs.isLoggedIn()) {
                    User user = sharedPrefs.getCredentials();
                    Paper.init(SplashActivity.this);
                    Paper.book().write("User",user);
                    if (user.getRole().equals(Constants.ROLE_STUDENT)) {
                        startActivity(new Intent(SplashActivity.this, UserActivity.class));
                        finish();
                    }
                    else if (user.getRole().equals(Constants.ROLE_TEACHER)){
                        startActivity(new Intent(SplashActivity.this, TeacherActivity.class));
                        finish();
                    }
                    else {
                        startActivity(new Intent(SplashActivity.this, AuthenticationActivity.class));
                        finish();
                    }
                } else {
                    startActivity(new Intent(SplashActivity.this, AuthenticationActivity.class));
                    finish();
                }
            }
        }, 2000);

//        if (!sharedPrefs.isLoggedIn()) {
//            new Handler().postDelayed(() -> {
//                startLogin();
//            }, 2000);
//        } else {
//            AuthApiInterface authApiInterface = RetrofitClient.getClient().create(AuthApiInterface.class);
//            LoginModel loginModel = sharedPrefs.getCredentials();

//            Call<User> userLoginCall = authApiInterface.(loginModel);

//            userLoginCall.enqueue(new Callback<User>() {
//                @Override
//                public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
//                    if (response != null && response.isSuccessful()) {
//
//                        if (response.body() != null) {
//                            loginSuccess(response.body());
//                        } else {
//                            Toast.makeText(SplashActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
//                            Log.e("SIGNIN-LOG", "Response body null");
//                            startLogin();
//                        }
//
//                    } else if (response.code() == 404) {
//                        Toast.makeText(SplashActivity.this, "Inavalid Credentials", Toast.LENGTH_SHORT).show();
//                        startLogin();
//                    } else {
//                        Toast.makeText(SplashActivity.this, "Server error", Toast.LENGTH_SHORT).show();
//                        Log.e("SIGNIN-LOG", response.code() + " " + response.message());
//                        startLogin();
//                    }
//                }
//
//                @Override
//                public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
//                    Toast.makeText(SplashActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
//                    t.printStackTrace();
//                    startLogin();
//                }
//            });
    }


    private void loginSuccess(User user) {
        sharedPrefs.setCurrentUser(user);

        try {
            switch (user.getRole()) {
                case Constants.ROLE_STUDENT:
                    Intent studentIntent = new Intent(this, UserActivity.class);
                    studentIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(studentIntent);
                    finish();
                    break;
                case Constants.ROLE_TEACHER:
                    Intent teacherIntent = new Intent(this, TeacherActivity.class);
                    teacherIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(teacherIntent);
                    finish();
                    break;
                default:
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    startLogin();
            }
        } catch (Exception e) {
            e.printStackTrace();
            startLogin();
        }
    }

    private void startLogin() {
        startActivity(new Intent(SplashActivity.this, AuthenticationActivity.class));
        finish();
    }
}