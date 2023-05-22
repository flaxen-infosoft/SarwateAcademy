package com.flaxeninfosoft.sarwateAcademy.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.databinding.LayoutNonpurchaseNotesBinding;
import com.flaxeninfosoft.sarwateAcademy.models.StudyMaterial;

import java.util.List;

public class NonPurchasedNotesAdapter extends RecyclerView.Adapter<NonPurchasedNotesAdapter.ViewHolder> {


    private List<StudyMaterial> studyMaterialList;
    private NotesCardClickListener notesCardClickListener;

    public NonPurchasedNotesAdapter(List<StudyMaterial> studyMaterialList, NotesCardClickListener notesCardClickListener) {
        this.studyMaterialList = studyMaterialList;
        this.notesCardClickListener = notesCardClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutNonpurchaseNotesBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_nonpurchase_notes, parent, false);
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        private LayoutNonpurchaseNotesBinding binding;
        private NotesCardClickListener notesCardClickListener;

        public ViewHolder(LayoutNonpurchaseNotesBinding binding, NotesCardClickListener notesCardClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.notesCardClickListener = notesCardClickListener;

        }

        public void setData(StudyMaterial studyMaterial) {
            binding.setStudyMaterial(studyMaterial);
            if (studyMaterial.getIsFree().equals("Paid")){
               binding.lockNotesLayout.setVisibility(View.VISIBLE);
               binding.getRoot().setClickable(false);
            }
            else {
                binding.lockNotesLayout.setVisibility(View.GONE);
                binding.getRoot().setClickable(true);
            }
            binding.layoutRightImageView.setImageResource(R.drawable.right_arrow);
            binding.getRoot().setOnClickListener(view -> {
                notesCardClickListener.onClickNotes(studyMaterial);
            });

        }
    }

    public interface NotesCardClickListener {
        void onClickNotes(StudyMaterial studyMaterial);
    }
}
