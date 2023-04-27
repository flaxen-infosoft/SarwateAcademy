package com.flaxeninfosoft.sarwateAcademy.views.teacherFragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.bumptech.glide.Glide;
import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.api.ApiEndpoints;
import com.flaxeninfosoft.sarwateAcademy.api.Constants;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentTeacherHomeBinding;
import com.flaxeninfosoft.sarwateAcademy.models.User;
import com.flaxeninfosoft.sarwateAcademy.utils.SharedPrefs;
import com.flaxeninfosoft.sarwateAcademy.views.AuthenticationActivity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import io.paperdb.Paper;

public class TeacherHomeFragment extends Fragment {

    private FragmentTeacherHomeBinding binding;
    public static final String TAG = "TEACHER-HOME-LOG";
    TextView headerText;
    ImageView headerImageView;
    User user;
    RequestQueue requestQueue;
    Gson gson;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue = Volley.newRequestQueue(getContext());
        gson = new Gson();
        Paper.init(getActivity().getApplicationContext());
        user = Paper.book().read("User");
        getTeacherById();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_teacher_home, container, false);

        binding.menuImageView.setOnClickListener(this::onClickMenu);
        binding.teacherImageViewHome.setOnClickListener(this::onClickUserImage);
        binding.teacherHomeAddcoursesView.setOnClickListener(this::onClickAddCourse);
        binding.teacherHomeLiveBatchesView.setOnClickListener(this::onClickLiveBatches);
        binding.teacherHomeStudyMaterial.setOnClickListener(this::onClickStudyMaterial);
        binding.teacherHomeAllstudentsView.setOnClickListener(this::onClickAllStudent);
        binding.teacherHomeMycourseView.setOnClickListener(this::onClickMyCourse);
        binding.shareTextView.setOnClickListener(this::onClickShare);
        binding.teacherNavigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        binding.teacherNavigationView.getHeaderView(0).findViewById(R.id.header_edit_icon).setOnClickListener(this::onClickEdit);
        if (user != null) {
            binding.teacherNameTextviewHome.setText("Hii, " + user.getName());
        }
        getTeacherById();
        headerText = binding.teacherNavigationView.getHeaderView(0).findViewById(R.id.headerNameTV);
        headerImageView = binding.teacherNavigationView.getHeaderView(0).findViewById(R.id.hederImageView);
        if (user.getProfileImg() != null) {
            Glide.with(getActivity()).load("http://103.118.17.202/~mkeducation/MK_API/User/" + user.getProfileImg()).placeholder(R.drawable.avatar_profile_icon).into(headerImageView);
        }
        headerText.setText(user.getName());


        return binding.getRoot();
    }

    private void onClickEdit(View view) {
        Navigation.findNavController(view).navigate(R.id.action_teacherHomeFragment_to_teacherProfileFragment);
    }

    private void onClickMyCourse(View view) {
        Navigation.findNavController(view).navigate(R.id.action_teacherHomeFragment_to_myCourseTeacherFragment);
    }

    private void onClickAddCourse(View view) {
        Navigation.findNavController(view).navigate(R.id.action_teacherHomeFragment_to_addCourseFragment);
    }

    private boolean onNavigationItemSelected(MenuItem menuItem) {
        int itemID = menuItem.getItemId();
        switch (itemID){
            case R.id.nav_teacher_share:
                Toast.makeText(getActivity(), "Wait", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String shareBody = "Learn from Sarwate Academy download now ";
                intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(intent, "Share via"));
                break;
            case R.id.nav_teacher_about_us:
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_teacherHomeFragment_to_aboutUsFragment2);
                break;
            case R.id.nav_teacher_contact_us:
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_teacherHomeFragment_to_contactUsFragment2);
                break;
            case R.id.nav_teacher_terms_and_condition:
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_teacherHomeFragment_to_termsAndConditionsFragment2);
                break;
            case R.id.nav_teacher_logout:
                SharedPrefs.getInstance(getActivity().getApplicationContext()).clearCredentials();
                startActivity(new Intent(getActivity(), AuthenticationActivity.class));
                getActivity().finish();
                break;
            default:
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }
        binding.drawerLayoutTeacher.close();
        return false;
    }

    private void onClickShare(View view) {
        Toast.makeText(getActivity(), "Wait", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String shareBody = "Learn from Sarwate Academy download now ";
        intent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(intent, "Share via"));
    }


    private void onClickAllStudent(View view) {
        Navigation.findNavController(view).navigate(R.id.action_teacherHomeFragment_to_teacherAllStudentsFragment);
    }
    private void onClickStudyMaterial(View view) {
        Navigation.findNavController(view).navigate(R.id.action_teacherHomeFragment_to_teacherAllNotesFragment);
    }
    private void onClickLiveBatches(View view) {
        Navigation.findNavController(view).navigate(R.id.action_teacherHomeFragment_to_teacherAllLiveSessionsFragment);
    }
    private void onClickUserImage(View view) {
        Navigation.findNavController(view).navigate(R.id.action_teacherHomeFragment_to_teacherProfileFragment);
    }
    private void onClickMenu(View view) {
        binding.drawerLayoutTeacher.openDrawer(Gravity.LEFT);
    }


    private void getTeacherById() {
        String url = ApiEndpoints.BASE_URL + ApiEndpoints.GET_TEACHER_BY_ID;
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(Constants.ID, user.getId());
        Log.i(TAG, hashMap.toString());
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap), response -> {
            try {
                if (response != null) {
                    if (response.getString("success").equals("1")) {
                        Log.i(TAG, String.valueOf(response));
                        User user1 = gson.fromJson(response.getString("data"), User.class);
                        Paper.book().write("User_Updated", user1);
                        binding.teacherNameTextviewHome.setText("Hii, " + user1.getName());
                        headerText.setText(user1.getName());
                        if (user.getProfileImg() != null) {
                            Glide.with(getActivity()).load("http://103.118.17.202/~mkeducation/MK_API/User/" + user1.getProfileImg()).placeholder(R.drawable.avatar_profile_icon).into(headerImageView);
                        }

                    } else {
                        Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(getActivity(),error.toString() , Toast.LENGTH_SHORT).show();
            Log.i(TAG, String.valueOf(error));
        });
        int timeout = 10000; // 10 seconds
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArrayRequest);
    }
}