package com.flaxeninfosoft.sarwateAcademy.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.databinding.LayoutSingleQuizBinding;
import com.flaxeninfosoft.sarwateAcademy.models.Quiz;
import com.squareup.picasso.Picasso;

import java.util.List;

public class QuizRecyclerAdapter extends RecyclerView.Adapter<QuizRecyclerAdapter.ViewHolder> {


    private List<Quiz> quizList;

    private QuizCardClickListener quizCardClickListener;

    public QuizRecyclerAdapter(List<Quiz> quiuzList, QuizCardClickListener quizCardClickListener){
        this.quizList = quiuzList;
        this.quizCardClickListener = quizCardClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutSingleQuizBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),  R.layout.layout_single_quiz, parent, false);
        return new ViewHolder(binding,quizCardClickListener);

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

    public void filterList(List<Quiz> quizList){
        this.quizList = quizList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final LayoutSingleQuizBinding binding;
        private final QuizCardClickListener quizCardClickListener;

        public ViewHolder(LayoutSingleQuizBinding binding, QuizCardClickListener quizCardClickListener) {
            super(binding.getRoot());
            this.binding=binding;
            this.quizCardClickListener= quizCardClickListener;
        }

        public void setData(Quiz quiz) {
            binding.setQuiz(quiz);
            Picasso.get().load("http://103.118.17.202/~mkeducation/MK_API/User/"+quiz.getBanner()).placeholder(R.drawable.sarwate_logo).into(binding.batchImageView);
            binding.getRoot().setOnClickListener(view->quizCardClickListener.onCLickCard(quiz));
        }
    }
    public interface QuizCardClickListener {
        void onCLickCard(Quiz quiz);
    }
}
