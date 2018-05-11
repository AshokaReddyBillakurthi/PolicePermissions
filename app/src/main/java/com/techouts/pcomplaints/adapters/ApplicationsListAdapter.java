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
import com.techouts.pcomplaints.ChatActivity;
import com.techouts.pcomplaints.R;
import com.techouts.pcomplaints.model.Application;
import com.techouts.pcomplaints.utils.AppConstents;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TO-OW109 on 08-02-2018.
 */

public class ApplicationsListAdapter extends RecyclerView.Adapter<ApplicationsListAdapter.ApplicationViewHolder>{

    private List<Application> applicationList;
    private Context mContext;

    public ApplicationsListAdapter(){
        this.applicationList = new ArrayList<>();
    }

    @Override
    public ApplicationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.application_list_item_cell,parent,false);
        return new ApplicationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ApplicationViewHolder holder, int position) {
        holder.tvFullName.setText(applicationList.get(position).firstName+" "+applicationList.get(position).lastName);
        holder.tvApplicationName.setText(applicationList.get(position).applicationType+"");
        holder.tvDivisionPoliceStation.setText(applicationList.get(position).circlePolicestation+"");
//        holder.tvEmail.setText(applicationList.get(position).email+"");
//        holder.tvMobileNo.setText(applicationList.get(position).mobileNo+"");
//        holder.tvApplicationType.setText(applicationList.get(position).applicationType+"");
//        holder.tvDivisionPoliceStation.setText(applicationList.get(position).circlePolicestation+"");

        if(applicationList.get(position).status == 0) {
            holder.tvStatus.setText(AppConstents.PENDING);
            holder.tvStatus.setTextColor(mContext.getColor(R.color.error_strip));
        }
        else if(applicationList.get(position).status == 1){
            holder.tvStatus.setText(AppConstents.INPROGRESS);
            holder.tvStatus.setTextColor(mContext.getColor(R.color.orange));
        }
        else if(applicationList.get(position).status == 2){
            holder.tvStatus.setText(AppConstents.COMPLETED);
            holder.tvStatus.setTextColor(mContext.getColor(R.color.button_green));
        }

//        holder.ivMessage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, ChatActivity.class);
//                intent.putExtra(AppConstents.EXTRA_EMAIL_ID,"ashok.billakurthi@gmail.com");
//                mContext.startActivity(intent);
//            }
//        });

//        getImageOfServiceMan(applicationList.get(position).userImg,holder.ivApplicantImg);
    }

    public void refresh(List<Application> applicationList){
        this.applicationList = applicationList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return applicationList.size();
    }

    class ApplicationViewHolder extends RecyclerView.ViewHolder{

       // ImageView ivApplicantImg,ivMessage;
        TextView tvFullName,tvApplicationName,tvStatus,tvDivisionPoliceStation;//tvEmail,tvMobileNo,tvApplicationType,
               // tvArea;

        public ApplicationViewHolder(View itemView) {
            super(itemView);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvApplicationName = itemView.findViewById(R.id.tvApplicationName);
            tvDivisionPoliceStation = itemView.findViewById(R.id.tvDivisionPoliceStation);

//            ivApplicantImg = itemView.findViewById(R.id.ivApplicantImg);
//            tvEmail = itemView.findViewById(R.id.tvEmail);
//            tvMobileNo = itemView.findViewById(R.id.tvMobileNo);
//            ivMessage = itemView.findViewById(R.id.ivMessage);
//            tvArea = itemView.findViewById(R.id.tvArea);
//            tvApplicationType = itemView.findViewById(R.id.tvApplicationType);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ApplicationDetailsActivity.class);
                    intent.putExtra(AppConstents.EXTRA_PERMISSION_APPLICATION,
                            applicationList.get(getAdapterPosition()));
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
