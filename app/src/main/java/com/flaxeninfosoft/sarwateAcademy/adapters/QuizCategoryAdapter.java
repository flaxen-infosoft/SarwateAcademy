package com.flaxeninfosoft.sarwateAcademy.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.databinding.LayoutCourseCategoryBinding;
import com.flaxeninfosoft.sarwateAcademy.databinding.LayoutQuizCategoryBinding;
import com.flaxeninfosoft.sarwateAcademy.models.CourseCategory;
import com.flaxeninfosoft.sarwateAcademy.models.QuizCategory;

import java.util.List;

public class QuizCategoryAdapter extends RecyclerView.Adapter<QuizCategoryAdapter.ViewHolder> {

    private List<QuizCategory> quizCategoryList;

    private QuizCategoryListener  quizCategoryListener;

    public QuizCategoryAdapter(List<QuizCategory> quizCategoryList, QuizCategoryListener quizCategoryListener){
        this.quizCategoryList = quizCategoryList;
        this.quizCategoryListener = quizCategoryListener;
    }

    @NonNull
    @Override
    public QuizCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutQuizCategoryBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_quiz_category, parent, false);
        return new ViewHolder(binding, quizCategoryListener);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizCategoryAdapter.ViewHolder holder, int position) {
        holder.setData(quizCategoryList.get(position));

    }

    @Override
    public int getItemCount() {
        if (quizCategoryList == null) {
            return 0;
        } else {
            return quizCategoryList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private LayoutQuizCategoryBinding binding;

       private QuizCategoryListener quizCategoryListener;

       public ViewHolder(LayoutQuizCategoryBinding binding, QuizCategoryListener quizCategoryListener){
           super(binding.getRoot());
           this.binding = binding;
           this.quizCategoryListener = quizCategoryListener;
       }

        public void setData(QuizCategory quizCategory) {
           binding.setQuizCategory(quizCategory);
           binding.getRoot().setOnClickListener(view ->{
               quizCategoryListener.onCLickCard(quizCategory);
           });
        }
    }

    public interface QuizCategoryListener {
        void onCLickCard(QuizCategory course);
    }
}
