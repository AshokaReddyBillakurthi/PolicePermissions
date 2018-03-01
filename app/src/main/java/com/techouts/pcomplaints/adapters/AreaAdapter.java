package com.techouts.pcomplaints.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techouts.pcomplaints.R;

import java.util.ArrayList;

/**
 * Created by TO-OW109 on 09-02-2018.
 */

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.AreaViewHolder>{
    private ArrayList<String> areaList;
    private OnAreaClickListener listener;

    public AreaAdapter(ArrayList<String> areaList, OnAreaClickListener listener){
        this.areaList = areaList;
        this.listener = listener;
    }

    @Override
    public AreaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.area_item_cell,parent,false);
        return new AreaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AreaViewHolder holder, int position) {
        holder.tvArea.setText(areaList.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return areaList.size();
    }

    class AreaViewHolder extends RecyclerView.ViewHolder{
        TextView tvArea;
        public AreaViewHolder(View itemView) {
            super(itemView);
            tvArea = itemView.findViewById(R.id.tvArea);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String area = tvArea.getText().toString();
                    listener.onAreaSelected(area);
                }
            });
        }
    }

    public interface OnAreaClickListener {
        void onAreaSelected(String area);
    }
}
