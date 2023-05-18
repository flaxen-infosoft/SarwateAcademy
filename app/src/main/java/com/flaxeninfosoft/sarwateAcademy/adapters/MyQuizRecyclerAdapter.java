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

public class MyQuizRecyclerAdapter extends RecyclerView.Adapter<MyQuizRecyclerAdapter.ViewHolder> {
    private List<Course> quizList;
    private MyQuizCardClick myQuizCardClick;

    public MyQuizRecyclerAdapter(List<Course> quizList,MyQuizCardClick myQuizCardClick){
        this.quizList = quizList;
        this.myQuizCardClick = myQuizCardClick;
    }
    @NonNull
    @Override
    public MyQuizRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutSingleBatchBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_single_batch, parent, false);
        return new ViewHolder(binding, myQuizCardClick);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(quizList.get(position));

    }


    @Override
    public int getItemCount() {
        if (quizList == null) {
            return 0;
        }
        return quizList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private LayoutSingleBatchBinding binding;
        MyQuizCardClick myQuizCardClick;

        public ViewHolder(@NonNull LayoutSingleBatchBinding binding, MyQuizCardClick myQuizCardClick) {
            super(binding.getRoot());
            this.binding = binding;
            this.myQuizCardClick = myQuizCardClick;
        }

        public void setData(Course course) {
            binding.setCourse(course);
            Picasso.get().load("http://103.118.17.202/~mkeducation/MK_API/User/"+course.getImageUrl()).placeholder(R.drawable.sarwate_logo).into(binding.batchImageView);
            binding.getRoot().setOnClickListener(view ->myQuizCardClick.onCLickCard(course));
        }
    }
    public interface MyQuizCardClick {
        void onCLickCard(Course course);
    }
}
