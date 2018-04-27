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

import com.techouts.pcomplaints.R;
import com.techouts.pcomplaints.UserDetailsActivity;
import com.techouts.pcomplaints.model.User;
import com.techouts.pcomplaints.utils.AppConstents;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TO-OW109 on 05-02-2018.
 */

public class UserListAdpter extends RecyclerView.Adapter<UserListAdpter.ServiceManViewHolder> {

    private List<User> userList;
    private Context mContext;

    public UserListAdpter(Context context) {
        this.userList = new ArrayList<>();
        this.mContext = context;
    }

    @Override
    public ServiceManViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_cell, parent, false);
        return new ServiceManViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ServiceManViewHolder holder, int position) {
        holder.tvFullName.setText(userList.get(position).firstName + " "+userList.get(position).lastName + "");
        holder.tvEmail.setText(userList.get(position).email + "");
        holder.tvMobileNumber.setText(userList.get(position).mobileNo + "");
        holder.tvArea.setText(userList.get(position).circlePolicestation+"");
        holder.tvCity.setText(userList.get(position).subDivision+"");
        holder.tvState.setText(userList.get(position).state+"");
        getImageOfServiceMan(userList.get(position).userImg,holder.ivServiceManImg);
    }

    public void refresh(List<User> userList){
        this.userList = userList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class ServiceManViewHolder extends RecyclerView.ViewHolder {
        TextView tvFullName, tvEmail, tvMobileNumber,tvArea,tvCity,tvState;
        ImageView ivServiceManImg;

        public ServiceManViewHolder(final View itemView) {
            super(itemView);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvArea = itemView.findViewById(R.id.tvArea);
            tvCity = itemView.findViewById(R.id.tvCity);
            tvState = itemView.findViewById(R.id.tvState);
            tvMobileNumber = itemView.findViewById(R.id.tvMobileNumber);
            ivServiceManImg = itemView.findViewById(R.id.ivServiceManImg);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, UserDetailsActivity.class);
                    intent.putExtra(AppConstents.EXTRA_USER,userList.get(getAdapterPosition()));
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
