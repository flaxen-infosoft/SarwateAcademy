package com.flaxeninfosoft.sarwateAcademy.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.databinding.LayoutSingleBatchBinding;
import com.flaxeninfosoft.sarwateAcademy.models.Course;

import java.util.ArrayList;
import java.util.List;

public class AllLiveSessionForTeacherRecyclerAdapter extends RecyclerView.Adapter<AllLiveSessionForTeacherRecyclerAdapter.ViewHolder> {

    private  List<Course> courseList;
    private final LiveSessionBatchCardClickListener clickListener;

    public AllLiveSessionForTeacherRecyclerAdapter(List<Course> courseList, LiveSessionBatchCardClickListener clickListener) {
        this.courseList = courseList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public AllLiveSessionForTeacherRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutSingleBatchBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_single_batch, parent, false);
        return new AllLiveSessionForTeacherRecyclerAdapter.ViewHolder(binding, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AllLiveSessionForTeacherRecyclerAdapter.ViewHolder holder, int position) {
        holder.setData(courseList.get(position));
    }

    @Override
    public int getItemCount() {
        if (courseList == null) {
            return 0;
        }
        return courseList.size();
    }


    public void filterList(ArrayList<Course> courseArrayList) {
        courseList = courseArrayList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final LayoutSingleBatchBinding binding;
        private final LiveSessionBatchCardClickListener clickListener;


        public ViewHolder(@NonNull LayoutSingleBatchBinding binding, LiveSessionBatchCardClickListener clickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.clickListener = clickListener;
        }

        public void setData(Course course) {
            binding.setCourse(course);
            binding.getRoot().setOnClickListener(view -> clickListener.onCLickCard(course));
        }

    }

    public interface LiveSessionBatchCardClickListener {
        void onCLickCard(Course course);
    }
}
