package com.flaxeninfosoft.sarwateAcademy.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.databinding.LayoutSingleVideoBinding;
import com.flaxeninfosoft.sarwateAcademy.models.Video;

import java.util.List;

public class DownloadedVideosRecyclerAdapter extends RecyclerView.Adapter<DownloadedVideosRecyclerAdapter.ViewHolder> {

    private List<Video> videoList;
    private VideoCardClickListener videoCardClickListener;

    public DownloadedVideosRecyclerAdapter(List<Video> videoList, VideoCardClickListener videoCardClickListener) {
        this.videoList = videoList;
        this.videoCardClickListener = videoCardClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutSingleVideoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_single_video,parent,false);
        return new ViewHolder(binding,videoCardClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(videoList.get(position));
    }

    @Override
    public int getItemCount() {
        if (videoList == null){
            return 0;
        }
        else {
            return videoList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LayoutSingleVideoBinding binding;
        VideoCardClickListener videoCardClickListener;

        public ViewHolder(LayoutSingleVideoBinding binding,VideoCardClickListener videoCardClickListener) {
            super(binding.getRoot());

            this.binding = binding;
            this.videoCardClickListener = videoCardClickListener;
        }

        public void setData(Video video){
            binding.setVideo(video);
            binding.getRoot().setOnClickListener(view -> {videoCardClickListener.onClickVideo(video);});
        }
    }

    public interface VideoCardClickListener{
        void onClickVideo(Video video);
    }
}
