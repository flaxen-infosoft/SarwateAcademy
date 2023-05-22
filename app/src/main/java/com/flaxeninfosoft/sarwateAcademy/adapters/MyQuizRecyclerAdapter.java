package com.flaxeninfosoft.sarwateAcademy.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.databinding.LayoutSingleMyquizBinding;
import com.flaxeninfosoft.sarwateAcademy.models.MyQuiz;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyQuizRecyclerAdapter extends RecyclerView.Adapter<MyQuizRecyclerAdapter.ViewHolder> {
    private List<MyQuiz> quizList;
    private MyQuizCardClick myQuizCardClick;

    public MyQuizRecyclerAdapter(List<MyQuiz> quizList, MyQuizCardClick myQuizCardClick){
        this.quizList = quizList;
        this.myQuizCardClick = myQuizCardClick;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutSingleMyquizBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_single_myquiz, parent, false);
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
        private LayoutSingleMyquizBinding binding;
        MyQuizCardClick myQuizCardClick;

        public ViewHolder(@NonNull LayoutSingleMyquizBinding binding, MyQuizCardClick myQuizCardClick) {
            super(binding.getRoot());
            this.binding = binding;
            this.myQuizCardClick = myQuizCardClick;
        }

        public void setData(MyQuiz quiz) {
            binding.setMyquiz(quiz);
            Picasso.get().load("http://103.118.17.202/~mkeducation/MK_API/User/"+quiz.getBanner()).placeholder(R.drawable.sarwate_logo).into(binding.batchImageView);
            binding.getRoot().setOnClickListener(view ->myQuizCardClick.onCLickCard(quiz));
        }
    }
    public interface MyQuizCardClick {
        void onCLickCard(MyQuiz quiz);
    }
}
