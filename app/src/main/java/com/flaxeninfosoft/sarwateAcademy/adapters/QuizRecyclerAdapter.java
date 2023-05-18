package com.flaxeninfosoft.sarwateAcademy.adapters;

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

public class QuizRecyclerAdapter extends RecyclerView.Adapter<QuizRecyclerAdapter.ViewHolder> {


    private List<Course>  quizList;

    private QuizCardClickListener quizCardClickListener;

    private QuizRecyclerAdapter(List<Course> courseList,QuizCardClickListener quizCardClickListener){
        this.quizList = courseList;
        this.quizCardClickListener = quizCardClickListener;
    }


    @NonNull
    @Override
    public QuizRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutSingleBatchBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),  R.layout.layout_single_batch, parent, false);
        return new ViewHolder(binding,quizCardClickListener);

    }

    @Override
    public void onBindViewHolder(@NonNull QuizRecyclerAdapter.ViewHolder holder, int position) {
        holder.setData(quizList.get(position));

    }

    @Override
    public int getItemCount() {
        if (quizList == null) {
            return 0;
        }
        return quizList.size();

    }

    public void filterList(List<Course> quizList){
        this.quizList = quizList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final LayoutSingleBatchBinding binding;
        private final QuizCardClickListener quizCardClickListener;

        public ViewHolder(LayoutSingleBatchBinding binding, QuizCardClickListener quizCardClickListener) {
            super(binding.getRoot());
            this.binding=binding;
            this.quizCardClickListener = quizCardClickListener;
        }

        public void setData(Course course) {
            binding.setCourse(course);
            Picasso.get().load("http://103.118.17.202/~mkeducation/MK_API/User/"+course.getImageUrl()).placeholder(R.drawable.sarwate_logo).into(binding.batchImageView);
            binding.getRoot().setOnClickListener(view->quizCardClickListener.onCLickCard(course));
        }
    }
    public interface QuizCardClickListener {
        void onCLickCard(Course course);
    }
}
