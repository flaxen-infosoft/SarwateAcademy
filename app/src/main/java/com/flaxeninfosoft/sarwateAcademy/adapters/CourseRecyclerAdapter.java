package com.flaxeninfosoft.sarwateAcademy.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.databinding.LayoutSingleBatchBinding;
import com.flaxeninfosoft.sarwateAcademy.models.Course;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CourseRecyclerAdapter extends RecyclerView.Adapter<CourseRecyclerAdapter.BatchesRecyclerViewHolder> {

    private  List<Course> courseList;
    private final BatchCardClickListener clickListener;

    public CourseRecyclerAdapter(List<Course> courseList, BatchCardClickListener clickListener) {
        this.courseList = courseList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public BatchesRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutSingleBatchBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_single_batch, parent, false);
        return new BatchesRecyclerViewHolder(binding, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BatchesRecyclerViewHolder holder, int position) {
        holder.setData(courseList.get(position));
    }

    @Override
    public int getItemCount() {
        if (courseList == null) {
            return 0;
        }
        return courseList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filterList(List<Course> courseList) {
        this.courseList = courseList;
        notifyDataSetChanged();
    }

    public static class BatchesRecyclerViewHolder extends RecyclerView.ViewHolder {

        private final LayoutSingleBatchBinding binding;
        private final BatchCardClickListener clickListener;

        public BatchesRecyclerViewHolder(@NonNull LayoutSingleBatchBinding binding, BatchCardClickListener clickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.clickListener = clickListener;
        }

        public void setData(Course course) {
            binding.setCourse(course);
            Picasso.get().load("http://103.118.17.202/~mkeducation/MK_API/User/"+course.getImageUrl()).placeholder(R.drawable.sarwate_logo).into(binding.batchImageView);
            binding.getRoot().setOnClickListener(view -> clickListener.onCLickCard(course));
        }
    }

    public interface BatchCardClickListener {
        void onCLickCard(Course course);
    }
}
