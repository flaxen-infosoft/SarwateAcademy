package com.flaxeninfosoft.sarwateAcademy.views.userFragments;

import android.content.Intent;
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
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.api.ApiEndpoints;
import com.flaxeninfosoft.sarwateAcademy.api.Constants;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentUserHomeBinding;
import com.flaxeninfosoft.sarwateAcademy.models.User;
import com.flaxeninfosoft.sarwateAcademy.utils.SharedPrefs;
import com.flaxeninfosoft.sarwateAcademy.views.AuthenticationActivity;
import com.flaxeninfosoft.sarwateAcademy.views.UserActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import io.paperdb.Paper;

public class UserHomeFragment extends Fragment {

    private FragmentUserHomeBinding binding;
    public static final String TAG = "USER-HOME-LOG";
    User user;
    RequestQueue requestQueue;
    Gson gson;
    TextView headerText;
    ImageView headerImageView;

    public UserHomeFragment() {
    }

    private static void onClick(View view) {
        Navigation.findNavController(view).navigate(R.id.action_userHomeFragment_to_profileFragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue = Volley.newRequestQueue(getContext());
        gson = new Gson();
        Paper.init(getActivity().getApplicationContext());
        user = Paper.book().read("User");
        getStudentById();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_home, container, false);

        binding.userHomeQuizSeries.setOnClickListener(this::allquiz);
        binding.userHomeMyQuiz.setOnClickListener(this::myquiz);

        binding.userHomeLiveBatchesView.setOnClickListener(this::navigateToBatches);
        binding.userHomeStudyMaterial.setOnClickListener(this::navigateToStudyMaterial);
        binding.userHomeDownloadsView.setOnClickListener(this::navigateToDownloads);
        binding.userHomeMyCourse.setOnClickListener(this::navigateToMyCourse);
        binding.userImageViewHome.setOnClickListener(this::onClick5);
        binding.menuImageView.setOnClickListener(this::onClick4);
        binding.navigationView.getHeaderView(0).findViewById(R.id.header_edit_icon).setOnClickListener(this::onClick3);
        binding.shareTextView.setOnClickListener(this::onClick2);
        binding.navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        if (user != null) {
            binding.userNameTextView.setText("Hii, " + user.getName());
        }
        getStudentById();
        setImagesOnSlider();


        headerText = binding.navigationView.getHeaderView(0).findViewById(R.id.headerNameTV);
        headerImageView = binding.navigationView.getHeaderView(0).findViewById(R.id.hederImageView);
        if (user.getProfileImg() != null) {
            Glide.with(getActivity()).load("http://103.118.17.202/~mkeducation/MK_API/User/" + user.getProfileImg()).placeholder(R.drawable.avatar_profile_icon).into(headerImageView);
        }
        headerText.setText(user.getName());

        return binding.getRoot();
    }

    private void myquiz(View view) {
        Navigation.findNavController(view).navigate(R.id.myQuizFragment);
    }

    private void allquiz(View view) {
        Navigation.findNavController(view).navigate(R.id.allQuizFragment);
    }

    private void setImagesOnSlider() {
        ArrayList<SlideModel> slideModelArrayList = new ArrayList<>();
        slideModelArrayList.add(new SlideModel(R.drawable.books_image, ScaleTypes.FIT));
        slideModelArrayList.add(new SlideModel(R.drawable.books_image_applejpg, ScaleTypes.FIT));
        slideModelArrayList.add(new SlideModel(R.drawable.eucation_imagejpg, ScaleTypes.FIT));
        binding.imageSlider.setImageList(slideModelArrayList, ScaleTypes.FIT);
    }

    private void navigateToMyCourse(View view) {
        Navigation.findNavController(view).navigate(R.id.action_userHomeFragment_to_myCourseFragment);
    }

    private void navigateToDownloads(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_userHomeFragment_to_downloadsFragment);
    }

    private void navigateToStudyMaterial(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_userHomeFragment_to_studyMaterialFragment);
    }


    private void navigateToBatches(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_userHomeFragment_to_liveBatchesFragment);
    }

    private void onClick2(View view) {
        Intent intent2 = new Intent();
        intent2.setAction(Intent.ACTION_SEND);
        intent2.setType("text/plain");
        String shareBody = "Learn from Sarwate Academy download now ";
        intent2.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(intent2, "Share via"));
    }

    private boolean onNavigationItemSelected(MenuItem item) {

        int itemClickedId = item.getItemId();

        switch (itemClickedId) {
//            case R.id.nav_mydownalod:
//                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_userHomeFragment_to_downloadsFragment);
//                break;
            case R.id.nav_share:
                Intent intent2 = new Intent();
                intent2.setAction(Intent.ACTION_SEND);
                intent2.setType("text/plain");
                String shareBody = "Learn from Sarwate Academy download now ";
                intent2.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(intent2, "Share via"));
                break;
            case R.id.nav_my_course:
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_userHomeFragment_to_myCourseFragment);
                break;
            case R.id.nav_about_us:
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_userHomeFragment_to_aboutUsFragment);
                break;
            case R.id.nav_contact_us:
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_userHomeFragment_to_contactUsFragment);
                break;
            case R.id.nav_terms_and_condition:
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_userHomeFragment_to_termsAndConditionsFragment);
                break;
            case R.id.nav_logout:
                SharedPrefs.getInstance(getActivity().getApplicationContext()).clearCredentials();
                startActivity(new Intent(getActivity(), AuthenticationActivity.class));
                getActivity().finish();
                break;
            default:
                Toast.makeText(getContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                return false;
        }
        binding.drawerLayout.close();
        return true;
    }

    private void onClick3(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_userHomeFragment_to_profileFragment);
    }

    private void onClick4(View view) {
        binding.drawerLayout.openDrawer(Gravity.LEFT);
    }

    private void onClick5(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_userHomeFragment_to_profileFragment);
    }


    private void getStudentById() {
        String url = ApiEndpoints.BASE_URL + ApiEndpoints.GET_STUDENT_BY_ID;
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
                        binding.userNameTextView.setText("Hii, " + user1.getName());
                        headerText.setText(user1.getName());
                        if (user.getProfileImg() != null) {
                            Glide.with(getActivity()).load("http://103.118.17.202/~mkeducation/MK_API/User/" + user.getProfileImg()).placeholder(R.drawable.avatar_profile_icon).into(headerImageView);
                        }

                    } else {
                        Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            Log.i(TAG, String.valueOf(error));
        });
        int timeout = 10000; // 10 seconds
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArrayRequest);
    }
}