package com.flaxeninfosoft.sarwateAcademy.views.teacherFragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentVideoDetailsBinding;
import com.flaxeninfosoft.sarwateAcademy.models.Video;

import io.paperdb.Paper;


public class VideoDetailsFragment extends Fragment {


    public VideoDetailsFragment() {
        // Required empty public constructor
    }

    public static final String TAG = "VIDEO-DETAILS-LOG";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentVideoDetailsBinding binding;
    Video video;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_video_details, container, false);
        video = Paper.book().read("Current_Video");
        binding.setVideo(video);
        binding.editFloatButton.setOnClickListener(this::onClickEditVideo);
        binding.playVideoButton.setOnClickListener(this::onClickPlayVideo);
        binding.backImageView.setOnClickListener(this::onClickBack);
        return binding.getRoot();
    }

    private void onClickBack(View view) {
        Navigation.findNavController(view).navigateUp();
    }

    private void onClickPlayVideo(View view) {
        String videoUrl = video.getVideoLink();
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));
            intent.putExtra("VIDEO_ID", getVideoIdFromUrl(videoUrl));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));
            startActivity(intent);

        }

    }

    private String getVideoIdFromUrl(String videoUrl) {
        String videoId = "";
        if (videoUrl != null && videoUrl.trim().length() > 0) {
            String[] urlParts = videoUrl.split("v=");
            if (urlParts.length == 2) {
                videoId = urlParts[1];
            }
        }
        return videoId;
    }


    private void onClickEditVideo(View view) {
        Navigation.findNavController(view).navigate(R.id.action_videoDetailsFragment_to_updateVideoFragment2);
    }
}