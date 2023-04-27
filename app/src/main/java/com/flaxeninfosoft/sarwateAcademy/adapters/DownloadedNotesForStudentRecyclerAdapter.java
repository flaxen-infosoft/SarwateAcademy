package com.flaxeninfosoft.sarwateAcademy.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.databinding.LayoutSingleNotesBinding;
import com.flaxeninfosoft.sarwateAcademy.models.StudyMaterial;

import java.util.List;

public class DownloadedNotesForStudentRecyclerAdapter extends RecyclerView.Adapter<DownloadedNotesForStudentRecyclerAdapter.ViewHolder> {

    private List<StudyMaterial> studyMaterialList;
    private AllNotesForTeacherRecyclerAdapter.NotesCardClickListener notesCardClickListener;

    public DownloadedNotesForStudentRecyclerAdapter(List<StudyMaterial> studyMaterialList, AllNotesForTeacherRecyclerAdapter.NotesCardClickListener notesCardClickListener) {
        this.studyMaterialList = studyMaterialList;
        this.notesCardClickListener = notesCardClickListener;
    }

    @NonNull
    @Override
    public DownloadedNotesForStudentRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutSingleNotesBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_single_notes, parent, false);
        return new ViewHolder(binding,notesCardClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DownloadedNotesForStudentRecyclerAdapter.ViewHolder holder, int position) {
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LayoutSingleNotesBinding binding;
        private AllNotesForTeacherRecyclerAdapter.NotesCardClickListener cardClickListener;

        public ViewHolder(@NonNull LayoutSingleNotesBinding binding, AllNotesForTeacherRecyclerAdapter.NotesCardClickListener cardClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.cardClickListener = cardClickListener;
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
