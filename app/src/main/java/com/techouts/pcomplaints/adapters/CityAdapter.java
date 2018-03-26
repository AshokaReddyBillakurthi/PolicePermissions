package com.techouts.pcomplaints.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techouts.pcomplaints.R;
import com.techouts.pcomplaints.custom.CustomDialog;
import com.techouts.pcomplaints.entities.City;

import java.util.List;

/**
 * Created by TO-OW109 on 09-02-2018.
 */

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {

    private List<City> cityList;
    private CustomDialog.NameSelectedListener nameSelectedListener;

    public CityAdapter(List<City> cityList, CustomDialog.NameSelectedListener nameSelectedListener){
        this.cityList = cityList;
        this.nameSelectedListener = nameSelectedListener;
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_cell,parent,false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {
        holder.tvArea.setText(cityList.get(position).cityName.toString());
    }

    public void refresh(List<City> cityList){
        this.cityList = cityList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return cityList.size();
    }

    class CityViewHolder extends RecyclerView.ViewHolder{
        TextView tvArea;
        public CityViewHolder(View itemView) {
            super(itemView);
            tvArea = itemView.findViewById(R.id.tvListName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String area = tvArea.getText().toString();
                    nameSelectedListener.onNameSelected(area);
                }
            });
        }
    }
}
