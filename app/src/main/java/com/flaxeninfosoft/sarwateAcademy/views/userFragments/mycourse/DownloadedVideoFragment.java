package com.flaxeninfosoft.sarwateAcademy.views.userFragments.mycourse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.adapters.DownloadedVideosRecyclerAdapter;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentDownloadedVideoBinding;
import com.flaxeninfosoft.sarwateAcademy.models.Video;

import java.util.ArrayList;
import java.util.List;


public class DownloadedVideoFragment extends Fragment {



    public DownloadedVideoFragment() {
        // Required empty public constructor
    }



    FragmentDownloadedVideoBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_downloaded_video, container, false);
        binding.downloadVideoRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<Video> videoList = new ArrayList<>();
        videoList.add(new Video("Video 1", "https://picsum.photos/400/280.jpg", "https://youtu.be/gTj2OWd5YnM"));
        videoList.add(new Video("Video 2", "https://picsum.photos/400/281.jpg", "https://youtu.be/f00Kxt-PFYA"));
        videoList.add(new Video("Video 3", "https://picsum.photos/400/282.jpg", "https://youtu.be/NvjKXwt7n48"));
        videoList.add(new Video("Video 4", "https://picsum.photos/400/283.jpg", "https://youtu.be/fC9XYyqjY2Y"));
        videoList.add(new Video("Video 5", "https://picsum.photos/400/289.jpg", "https://youtu.be/EMjRSJDJJ90"));
        DownloadedVideosRecyclerAdapter adapter = new DownloadedVideosRecyclerAdapter(videoList, this::onVideoClick);

        if (videoList.isEmpty()) {
            binding.noVideoFoundLayout.setVisibility(View.VISIBLE);
            binding.downloadVideoRecycler.setVisibility(View.GONE);
        } else {
            binding.downloadVideoRecycler.setAdapter(adapter);
            binding.noVideoFoundLayout.setVisibility(View.GONE);
            binding.downloadVideoRecycler.setVisibility(View.VISIBLE);
        }

        return binding.getRoot();
    }

    private void onVideoClick(Video video) {

    }
}