package com.flaxeninfosoft.sarwateAcademy.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.databinding.LayoutSingleVideoBinding;
import com.flaxeninfosoft.sarwateAcademy.models.Video;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PurchasedCourseVideoAdapter extends RecyclerView.Adapter<PurchasedCourseVideoAdapter.VideoHolder> {

    private List<Video> videoList;
    private VideoCardClickListener videoCardClickListener;

    public PurchasedCourseVideoAdapter(List<Video> videoList, VideoCardClickListener videoCardClickListener) {
        this.videoList = videoList;
        this.videoCardClickListener = videoCardClickListener;
    }

    @NonNull
    @Override
    public PurchasedCourseVideoAdapter.VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutSingleVideoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_single_video, parent, false);
        return new VideoHolder(binding, videoCardClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchasedCourseVideoAdapter.VideoHolder holder, int position) {
        holder.setData(videoList.get(position));
    }

    @Override
    public int getItemCount() {
        if (videoList == null) {
            return 0;
        } else {
            return videoList.size();
        }
    }

    public class VideoHolder extends RecyclerView.ViewHolder {

        LayoutSingleVideoBinding binding;
        VideoCardClickListener videoCardClickListener;

        public VideoHolder(LayoutSingleVideoBinding binding, VideoCardClickListener videoCardClickListener) {
            super(binding.getRoot());

            this.binding = binding;
            this.videoCardClickListener = videoCardClickListener;
        }

        public void setData(Video video) {
            binding.setVideo(video);
            Picasso.get().load("http://103.118.17.202/~mkeducation/MK_API/User/" + video.getImage()).placeholder(R.drawable.sarwate_logo).into(binding.videoImageView);
            binding.getRoot().setOnClickListener(view -> {
                videoCardClickListener.onClickVideo(video);
            });
        }
    }

    public interface VideoCardClickListener {
        void onClickVideo(Video video);
    }
}
