package com.flaxeninfosoft.sarwateAcademy.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.databinding.LayoutSingleNotesBinding;
import com.flaxeninfosoft.sarwateAcademy.models.StudyMaterial;

import java.util.ArrayList;
import java.util.List;

public class AllNotesForTeacherRecyclerAdapter extends RecyclerView.Adapter<AllNotesForTeacherRecyclerAdapter.ViewHolder> {

    private List<StudyMaterial> studyMaterialList;
    private NotesCardClickListener notesCardClickListener;

    public AllNotesForTeacherRecyclerAdapter(List<StudyMaterial> studyMaterialList, NotesCardClickListener notesCardClickListener) {
        this.studyMaterialList = studyMaterialList;
        this.notesCardClickListener = notesCardClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutSingleNotesBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_single_notes, parent, false);
        return new ViewHolder(binding, notesCardClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(studyMaterialList.get(position));
    }

    @Override
    public int getItemCount() {
        if (studyMaterialList == null) {
            return 0;
        } else {
            return studyMaterialList.size();
        }
    }

    public void filterList(ArrayList<StudyMaterial> studyMaterialArrayList) {
        studyMaterialList = studyMaterialArrayList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private LayoutSingleNotesBinding binding;
        private NotesCardClickListener notesCardClickListener;

        public ViewHolder(LayoutSingleNotesBinding binding, NotesCardClickListener notesCardClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.notesCardClickListener = notesCardClickListener;
        }

        public void setData(StudyMaterial studyMaterial) {
            binding.setStudyMaterial(studyMaterial);
            binding.layoutRightImageView.setImageResource(R.drawable.right_arrow);
            binding.getRoot().setOnClickListener(view -> {
                notesCardClickListener.onNotesCardClick(studyMaterial);
            });

        }
    }

    public interface NotesCardClickListener {
        void onNotesCardClick(StudyMaterial studyMaterial);
    }
}
