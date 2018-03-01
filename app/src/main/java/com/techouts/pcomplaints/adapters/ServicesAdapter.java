package com.techouts.pcomplaints.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techouts.pcomplaints.utils.AppConstents;
import com.techouts.pcomplaints.PermissionsAndVerificationActivity;
import com.techouts.pcomplaints.R;

import java.util.ArrayList;

/**
 * Created by TO-OW109 on 02-02-2018.
 */

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ServiceHolder>{

    private ArrayList<String> listServices;

    public ServicesAdapter(ArrayList<String> listServices){
        this.listServices = listServices;
    }


    @Override
    public ServiceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_item_cell,parent,false);
        return new ServiceHolder(view);
    }

    @Override
    public void onBindViewHolder(ServiceHolder holder, int position) {
        holder.tvServiceName.setText(listServices.get(position).toString()+"");
    }

    @Override
    public int getItemCount() {
        return listServices.size();
    }

    class ServiceHolder extends RecyclerView.ViewHolder{

        TextView tvServiceName;
        public ServiceHolder(final View itemView) {
            super(itemView);
            tvServiceName = itemView.findViewById(R.id.tvServiceName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String serviceName = tvServiceName.getText().toString();
                    Intent intent = new Intent(itemView.getContext(),PermissionsAndVerificationActivity.class);
                    intent.putExtra(AppConstents.EXTRA_SEARVICE_TYPE,serviceName);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
