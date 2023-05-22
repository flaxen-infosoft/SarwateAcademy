package com.flaxeninfosoft.sarwateAcademy.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.databinding.LayoutNonpurchaseVideoBinding;
import com.flaxeninfosoft.sarwateAcademy.models.Video;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NonPurchasedVideoAdapter extends RecyclerView.Adapter<NonPurchasedVideoAdapter.VideoHolder> {

    private List<Video> videoList;
    private VideoCardClickListener videoCardClickListener;


    public NonPurchasedVideoAdapter(List<Video> videoList, VideoCardClickListener videoCardClickListener) {
        this.videoList = videoList;
        this.videoCardClickListener = videoCardClickListener;
    }

    @NonNull
    @Override
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutNonpurchaseVideoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_nonpurchase_video, parent, false);
        return new VideoHolder(binding, videoCardClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoHolder holder, int position) {
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

        LayoutNonpurchaseVideoBinding binding;
        VideoCardClickListener videoCardClickListener;

        public VideoHolder(LayoutNonpurchaseVideoBinding binding, VideoCardClickListener videoCardClickListener) {
            super(binding.getRoot());

            this.binding = binding;
            this.videoCardClickListener = videoCardClickListener;
        }

        public void setData(Video video) {
            binding.setVideo(video);
            if (video.getIsFree().equals("Paid")) {
                binding.lockVideoLayout.setVisibility(View.VISIBLE);
                binding.getRoot().setClickable(false);
            } else {
                binding.lockVideoLayout.setVisibility(View.GONE);
                binding.getRoot().setClickable(true);
            }
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
