package com.techouts.pcomplaints.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techouts.pcomplaints.HomeActivity;
import com.techouts.pcomplaints.MatrimonialVerificationApplicationActivity;
import com.techouts.pcomplaints.CrimeReportApplicationActivity;
import com.techouts.pcomplaints.CyberCafeApplicationActivity;
import com.techouts.pcomplaints.GunLicenceApplicationActivity;
import com.techouts.pcomplaints.PermissionInstructionActivity;
import com.techouts.pcomplaints.R;
import com.techouts.pcomplaints.utils.AppConstents;
import com.techouts.pcomplaints.utils.DialogUtils;
import com.techouts.pcomplaints.utils.PermissionInstruConstents;
import com.techouts.pcomplaints.utils.SharedPreferenceUtils;

import java.util.List;

/**
 * Created by TO-OW109 on 12-02-2018.
 */

public class PermissionListAdapter extends RecyclerView.Adapter<PermissionListAdapter.PermissionViewHolder>{

    private List<String> listPermissions;

    public PermissionListAdapter(List<String> listPermissions){
        this.listPermissions = listPermissions;
    }

    @Override
    public PermissionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_permission_list_item_cell,parent,false);
        return new PermissionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PermissionViewHolder holder, int position) {
        holder.tvPermissionName.setText(listPermissions.get(position));
    }

    public void refresh(List<String> listPermissions){
        this.listPermissions = listPermissions;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listPermissions.size();
    }

    class PermissionViewHolder extends RecyclerView.ViewHolder{
        TextView tvPermissionName;

        public PermissionViewHolder(final View itemView) {
            super(itemView);
            tvPermissionName = itemView.findViewById(R.id.tvPermissionName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(SharedPreferenceUtils.getBoolValue(SharedPreferenceUtils.IS_LOGGEDIN)){
                        String permissionName = tvPermissionName.getText().toString();
                        if(permissionName.equalsIgnoreCase(AppConstents.INTERNET_CAFES)){
                            Intent intent = new Intent(itemView.getContext(), PermissionInstructionActivity.class);
                            intent.putExtra(AppConstents.EXTRA_APPLICATION_TYPE,AppConstents.INTERNET_CAFES);
                            (itemView.getContext()).startActivity(intent);
                        }
                        else if (permissionName.equalsIgnoreCase(AppConstents.GUN_LICENCES)) {
                            Intent intent = new Intent(itemView.getContext(), PermissionInstructionActivity.class);
                            intent.putExtra(AppConstents.EXTRA_APPLICATION_TYPE,AppConstents.GUN_LICENCES);
                            (itemView.getContext()).startActivity(intent);
                        }
                        else if(permissionName.equalsIgnoreCase(AppConstents.CRIME_REPORT)){
                            Intent intent = new Intent(itemView.getContext(), CrimeReportApplicationActivity.class);
                            intent.putExtra(AppConstents.EXTRA_APPLICATION_TYPE,AppConstents.CRIME_REPORT);
                            (itemView.getContext()).startActivity(intent);
                        }
                        else if(permissionName.equalsIgnoreCase(AppConstents.MARTIMONIAL_VERIFICATION)){
                            Intent intent = new Intent(itemView.getContext(), MatrimonialVerificationApplicationActivity.class);
                            intent.putExtra(AppConstents.EXTRA_APPLICATION_TYPE,AppConstents.CRIME_REPORT);
                            (itemView.getContext()).startActivity(intent);
                        }
                    }
                    else{
                        DialogUtils.showDialog(itemView.getContext(),"Please login to apply",AppConstents.LOGOUT,true);
                    }

                }
            });

        }
    }
}
