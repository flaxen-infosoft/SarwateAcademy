package com.flaxeninfosoft.sarwateAcademy.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.databinding.LayoutSingleQuizquestionsBinding;
import com.flaxeninfosoft.sarwateAcademy.models.QuizQuestions;

import java.util.List;

public class QuizQuestionsAdapter extends RecyclerView.Adapter<QuizQuestionsAdapter.ViewHolder> {

    private List<QuizQuestions> quizQuestions;
    private MyQuestionsCardClick myQuestionsCardClick;

    public QuizQuestionsAdapter(List<QuizQuestions> quizQuestions, MyQuestionsCardClick myQuestionsCardClick) {
        this.quizQuestions = quizQuestions;
        this.myQuestionsCardClick = myQuestionsCardClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutSingleQuizquestionsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_single_quizquestions, parent, false);
        return new ViewHolder(binding, myQuestionsCardClick);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(quizQuestions.get(position));
    }

    @Override
    public int getItemCount() {
        if (quizQuestions == null) {
            return 0;
        }
        return quizQuestions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private LayoutSingleQuizquestionsBinding binding;
        MyQuestionsCardClick questionsCardClick;

        public ViewHolder(@NonNull LayoutSingleQuizquestionsBinding binding, MyQuestionsCardClick myQuestionsCardClick) {
            super(binding.getRoot());
            this.binding = binding;
            this.questionsCardClick = myQuestionsCardClick;
        }

        public void setData(QuizQuestions quizQuestions) {
            binding.setQuizquestions(quizQuestions);
            binding.getRoot().setOnClickListener(view -> questionsCardClick.onCLickCard(quizQuestions));
        }

    }

    public interface MyQuestionsCardClick {
        void onCLickCard(QuizQuestions quizQuestions1);
    }
}
