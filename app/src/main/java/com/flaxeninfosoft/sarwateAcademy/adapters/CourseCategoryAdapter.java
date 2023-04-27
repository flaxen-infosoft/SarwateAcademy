package com.flaxeninfosoft.sarwateAcademy.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.databinding.LayoutCourseCategoryBinding;
import com.flaxeninfosoft.sarwateAcademy.models.CourseCategory;

import java.util.List;

public class CourseCategoryAdapter extends RecyclerView.Adapter<CourseCategoryAdapter.ViewHolder> {

    private List<CourseCategory> courseCategoryList;
    private CategoryCardListener categoryCardListener;

    public CourseCategoryAdapter(List<CourseCategory> courseCategoryList, CategoryCardListener categoryCardListener) {
        this.courseCategoryList = courseCategoryList;
        this.categoryCardListener = categoryCardListener;
    }

    @NonNull
    @Override
    public CourseCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutCourseCategoryBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_course_category, parent, false);
        return new ViewHolder(binding, categoryCardListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseCategoryAdapter.ViewHolder holder, int position) {
        holder.setData(courseCategoryList.get(position));
    }

    @Override
    public int getItemCount() {
        if (courseCategoryList == null) {
            return 0;
        } else {
            return courseCategoryList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private LayoutCourseCategoryBinding binding;
        private CategoryCardListener categoryCardListener;

        public ViewHolder(LayoutCourseCategoryBinding binding, CategoryCardListener categoryCardListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.categoryCardListener = categoryCardListener;

        }

        public void setData(CourseCategory courseCategory) {
            binding.setCourseCategory(courseCategory);

            binding.getRoot().setOnClickListener(view -> {
                categoryCardListener.onClickCategory(courseCategory);
            });

        }
    }


    public interface CategoryCardListener {
        void onClickCategory(CourseCategory courseCategory);
    }
}
