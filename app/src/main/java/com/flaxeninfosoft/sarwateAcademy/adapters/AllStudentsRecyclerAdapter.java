package com.flaxeninfosoft.sarwateAcademy.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.databinding.LayoutSingleStudentBinding;
import com.flaxeninfosoft.sarwateAcademy.models.User;

import java.util.ArrayList;
import java.util.List;


public class AllStudentsRecyclerAdapter extends RecyclerView.Adapter<AllStudentsRecyclerAdapter.AllStudentViewHolder> {


    private List<User> userList;
    private final StudentCardClickListener studentCardClickListener;

    public AllStudentsRecyclerAdapter(List<User> userList, StudentCardClickListener studentCardClickListener) {
        this.userList = userList;
        this.studentCardClickListener = studentCardClickListener;
    }

    @NonNull
    @Override
    public AllStudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutSingleStudentBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_single_student,parent,false);
        return new AllStudentViewHolder(binding,studentCardClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AllStudentViewHolder holder, int position) {
        holder.setData(userList.get(position));
    }

    @Override
    public int getItemCount() {
        if (userList==null){
            return 0;
        }
        else {
            return userList.size();
        }
    }

    public void filterList(ArrayList<User> userArrayList) {
        userList = userArrayList;
        notifyDataSetChanged();
    }

    public class AllStudentViewHolder extends RecyclerView.ViewHolder{

      private LayoutSingleStudentBinding binding;
      private StudentCardClickListener studentCardClickListener;
        public AllStudentViewHolder(LayoutSingleStudentBinding binding, StudentCardClickListener studentCardClickListener) {
            super(binding.getRoot());
            this.binding = binding ;
            this.studentCardClickListener = studentCardClickListener;
        }

        public void setData(User user){
            binding.setUser(user);
            binding.getRoot().setOnClickListener(view -> {studentCardClickListener.onClickStudentCard(user);});
        }
    }

    public interface StudentCardClickListener{

        void onClickStudentCard(User user);
    }
}
