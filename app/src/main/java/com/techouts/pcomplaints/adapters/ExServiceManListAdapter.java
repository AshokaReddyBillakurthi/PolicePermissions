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

import com.techouts.pcomplaints.ExServiceManDetailsActivity;
import com.techouts.pcomplaints.ExServiceManListActivity;
import com.techouts.pcomplaints.R;
import com.techouts.pcomplaints.model.ExServiceMan;
import com.techouts.pcomplaints.utils.AppConstents;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TO-OW109 on 27-02-2018.
 */

public class ExServiceManListAdapter extends RecyclerView.Adapter<ExServiceManListAdapter.ExServiceManViewHolder>{

    private List<ExServiceMan> listExServiceMan;
    private Context mContext;

    public ExServiceManListAdapter(Context mContext){
        this.listExServiceMan = new ArrayList<>();
        this.mContext = mContext;
    }

    @Override
    public ExServiceManViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_cell, parent, false);
        return new ExServiceManViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExServiceManViewHolder holder, int position) {
        holder.tvFullName.setText(listExServiceMan.get(position).firstName + " "+listExServiceMan.get(position).lastName + "");
        holder.tvEmail.setText(listExServiceMan.get(position).email + "");
        holder.tvMobileNumber.setText(listExServiceMan.get(position).mobileNo + "");
        holder.tvArea.setText(listExServiceMan.get(position).area+"");
        holder.tvCity.setText(listExServiceMan.get(position).city+"");
        getImageOfServiceMan(listExServiceMan.get(position).userImg,holder.ivServiceManImg);
    }

    public void refresh(List<ExServiceMan> listExServiceMan){
        this.listExServiceMan = listExServiceMan;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listExServiceMan.size();
    }

    class ExServiceManViewHolder extends RecyclerView.ViewHolder{

        TextView tvFullName, tvEmail, tvMobileNumber,tvArea,tvCity;
        ImageView ivServiceManImg;

        public ExServiceManViewHolder(final View itemView) {
            super(itemView);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvMobileNumber = itemView.findViewById(R.id.tvMobileNumber);
            ivServiceManImg = itemView.findViewById(R.id.ivServiceManImg);
            tvArea = itemView.findViewById(R.id.tvArea);
            tvCity = itemView.findViewById(R.id.tvCity);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ExServiceManDetailsActivity.class);
                    intent.putExtra(AppConstents.EXTRA_USER,listExServiceMan.get(getAdapterPosition()));
                    intent.putExtra(AppConstents.EXTRA_LOGIN_TYPE,((ExServiceManListActivity)(itemView.getContext())).loginType);
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
