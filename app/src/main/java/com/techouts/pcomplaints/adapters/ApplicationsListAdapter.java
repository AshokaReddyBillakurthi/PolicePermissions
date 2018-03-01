package com.techouts.pcomplaints.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.techouts.pcomplaints.ApplicationDetailsActivity;
import com.techouts.pcomplaints.entities.PermissionApplication;
import com.techouts.pcomplaints.R;
import com.techouts.pcomplaints.utils.AppConstents;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TO-OW109 on 08-02-2018.
 */

public class ApplicationsListAdapter extends RecyclerView.Adapter<ApplicationsListAdapter.ApplicationViewHolder>{

    private List<PermissionApplication> permissionApplicationList;
    private Context mContext;

    public ApplicationsListAdapter(){
        this.permissionApplicationList = new ArrayList<>();
    }

    @Override
    public ApplicationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.application_item_cell,parent,false);
        return new ApplicationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ApplicationViewHolder holder, int position) {
        holder.tvName.setText(permissionApplicationList.get(position).fullName+"");
        holder.tvEmail.setText(permissionApplicationList.get(position).owneremail+"");
        holder.tvMobileNo.setText(permissionApplicationList.get(position).telephoneNo+"");
        holder.tvApplicationType.setText(permissionApplicationList.get(position).applicationType+"");
        getImageOfServiceMan(permissionApplicationList.get(position).applicantImg,holder.ivApplicantImg);
    }

    public void refresh(List<PermissionApplication> permissionApplicationList){
        this.permissionApplicationList = permissionApplicationList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return permissionApplicationList.size();
    }

    class ApplicationViewHolder extends RecyclerView.ViewHolder{

        ImageView ivApplicantImg;
        TextView tvName,tvEmail,tvMobileNo,tvApplicationType;

        public ApplicationViewHolder(View itemView) {
            super(itemView);

            ivApplicantImg = itemView.findViewById(R.id.ivApplicantImg);
            tvName = itemView.findViewById(R.id.tvName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvMobileNo = itemView.findViewById(R.id.tvMobileNo);
            tvApplicationType = itemView.findViewById(R.id.tvApplicationType);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ApplicationDetailsActivity.class);
                    intent.putExtra(AppConstents.EXTRA_PERMISSION_APPLICATION,permissionApplicationList.get(getAdapterPosition()));
                    mContext.startActivity(intent);
                }
            });
        }
    }


    private void getImageOfServiceMan(String serviceManImg,ImageView ivServiceManImg){
        try{
            File mediaStorageDir = new File(
                    "/data/data/"
                            + mContext.getPackageName()
                            + "/Files");

            if (! mediaStorageDir.exists()){
                if (! mediaStorageDir.mkdirs()){
                    return;
                }
            }

            File mediaFile = new File(mediaStorageDir.getPath() + File.separator + serviceManImg);
            if(mediaFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(mediaFile.getAbsolutePath());
                ivServiceManImg.setImageBitmap(myBitmap);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
