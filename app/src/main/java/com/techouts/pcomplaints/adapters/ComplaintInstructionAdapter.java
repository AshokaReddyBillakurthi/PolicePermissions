package com.techouts.pcomplaints.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techouts.pcomplaints.R;

import java.util.ArrayList;
import java.util.List;

public class ComplaintInstructionAdapter extends
        RecyclerView.Adapter<ComplaintInstructionAdapter.ComplaintInstructionHolder>{

    private List<String> complaintInstructionsList;

    public ComplaintInstructionAdapter(){
        this.complaintInstructionsList = new ArrayList<>();
    }

    @Override
    public ComplaintInstructionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.complaint_instruction_item_cell,parent,false);
        return new ComplaintInstructionHolder(view);
    }

    @Override
    public void onBindViewHolder(ComplaintInstructionHolder holder, int position) {
        holder.tvComplaintInstruction.setText(complaintInstructionsList.get(position));
    }

    public void refresh(List<String> complaintInstructionsList){
        this.complaintInstructionsList = complaintInstructionsList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return complaintInstructionsList.size();
    }

    class ComplaintInstructionHolder extends RecyclerView.ViewHolder{

        public TextView tvComplaintInstruction;

        public ComplaintInstructionHolder(View itemView) {
            super(itemView);
            tvComplaintInstruction = itemView.findViewById(R.id.tvComplaintInstruction);
        }
    }
}
