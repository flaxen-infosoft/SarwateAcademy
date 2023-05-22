package com.flaxeninfosoft.sarwateAcademy.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.databinding.LayoutSingleBatchBinding;
import com.flaxeninfosoft.sarwateAcademy.models.Course;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyCourseRecyclerAdapter extends RecyclerView.Adapter<MyCourseRecyclerAdapter.ViewHolder> {

    private List<Course> courseList;
    private MyCourseCardClick myCourseCardClick;

    public MyCourseRecyclerAdapter(List<Course> courseList, MyCourseCardClick myCourseCardClick) {
        this.courseList = courseList;
        this.myCourseCardClick = myCourseCardClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutSingleBatchBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_single_batch, parent, false);
        return new ViewHolder(binding, myCourseCardClick);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(courseList.get(position));
    }

    @Override
    public int getItemCount() {
        if (courseList == null) {
            return 0;
        }
        return courseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private LayoutSingleBatchBinding binding;
        MyCourseCardClick courseCardClick;

        public ViewHolder(@NonNull LayoutSingleBatchBinding binding, MyCourseCardClick courseCardClick) {
            super(binding.getRoot());
            this.binding = binding;
            this.courseCardClick = courseCardClick;
            binding.priceTextView.setVisibility(View.GONE);
        }

        public void setData(Course course) {
            binding.setCourse(course);
            Picasso.get().load("http://103.118.17.202/~mkeducation/MK_API/User/"+course.getImageUrl()).placeholder(R.drawable.sarwate_logo).into(binding.batchImageView);
            binding.getRoot().setOnClickListener(view -> courseCardClick.onCLickCard(course));
        }

    }

    public interface MyCourseCardClick {
        void onCLickCard(Course course);
    }
}
