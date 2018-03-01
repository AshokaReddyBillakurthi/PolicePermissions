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

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.StateViewHolder>  {

    private ArrayList<String> cityList;
    private StateAdapter.OnStateClickListener listener;

    public StateAdapter(ArrayList<String> cityList, StateAdapter.OnStateClickListener listener){
        this.cityList = cityList;
        this.listener = listener;
    }

    @Override
    public StateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.area_item_cell,parent,false);
        return new StateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StateViewHolder holder, int position) {
        holder.tvArea.setText(cityList.get(position).toString());
    }


    @Override
    public int getItemCount() {
        return cityList.size();
    }

    class StateViewHolder extends RecyclerView.ViewHolder{
        TextView tvArea;
        public StateViewHolder(View itemView) {
            super(itemView);
            tvArea = itemView.findViewById(R.id.tvArea);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String area = tvArea.getText().toString();
                    listener.onStateSelected(area);
                }
            });
        }
    }

    public interface OnStateClickListener{
        void onStateSelected(String state);
    }
}
