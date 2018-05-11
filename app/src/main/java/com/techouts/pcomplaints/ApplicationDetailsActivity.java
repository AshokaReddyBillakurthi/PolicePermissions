package com.techouts.pcomplaints;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techouts.pcomplaints.database.XServiceManDataHelper;
import com.techouts.pcomplaints.model.Application;
import com.techouts.pcomplaints.model.ExServiceMan;
import com.techouts.pcomplaints.utils.AppConstents;

import java.util.List;

public class ApplicationDetailsActivity extends BaseActivity {

    private TextView tvTitle,tvFullName,tvEmail,tvMobileNo,tvApplicationType,
            tvArea,tvOccupation,tvStatus;
    private ImageView ivBack,ivUserImage;
    private Application application;
    private LinearLayout llAssign;

    @Override
    public int getRootLayout() {
        return R.layout.activity_application_details;
    }

    @Override
    public void initGUI() {
        if(getIntent().getExtras()!=null){
            application = (Application)getIntent().getExtras()
                    .getSerializable(AppConstents.EXTRA_PERMISSION_APPLICATION);

//            userType = getIntent().getStringExtra(AppConstents.EXTRA_USER_TYPE);
        }
        tvTitle = findViewById(R.id.tvTitle);
        tvFullName = findViewById(R.id.tvFullName);
        tvEmail = findViewById(R.id.tvEmail);
        tvOccupation = findViewById(R.id.tvOccupation);
        tvMobileNo = findViewById(R.id.tvMobileNo);
        tvApplicationType = findViewById(R.id.tvApplicationType);
        tvArea = findViewById(R.id.tvListName);
        tvStatus = findViewById(R.id.tvStatus);
//        tvCity = findViewById(R.id.tvCity);
//        tvState = findViewById(R.id.tvState);
        ivBack = findViewById(R.id.ivBack);
        ivUserImage = findViewById(R.id.ivUserImage);
        llAssign = findViewById(R.id.llAssign);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initData() {
        if(null!=application){
            tvFullName.setText(application.firstName+"");
            tvOccupation.setText(application.lastName+"");
            tvEmail.setText(application.email+"");
            tvMobileNo.setText(application.mobileNo+"");
            tvApplicationType.setText(application.applicationType+"");
            tvArea.setText(application.circlePolicestation+"");
            tvTitle.setText(application.applicationType+"");
            if(application.status == 0) {
               tvStatus.setText(AppConstents.PENDING);
               tvStatus.setTextColor(getColor(R.color.error_strip));
            }
            else if(application.status == 1){
                tvStatus.setText(AppConstents.INPROGRESS);
                tvStatus.setTextColor(getColor(R.color.orange));
            }
            else if(application.status == 2){
               tvStatus.setText(AppConstents.COMPLETED);
               tvStatus.setTextColor(getColor(R.color.button_green));
            }
            Bitmap bitmap = getUserImageBitMap(application.userImg);
            if(bitmap!=null){
                ivUserImage.setImageBitmap(bitmap);
            }
        }

        llAssign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(application!=null){
                    new GetAllServiceMansRelatedToDivision().execute(application.circlePolicestation);
                }
            }
        });
    }


    class GetAllServiceMansRelatedToDivision extends AsyncTask<String,Void,List<ExServiceMan>>{

        @Override
        protected List<ExServiceMan> doInBackground(String... strings) {
            return XServiceManDataHelper.getAllXServiceMansBasedOnDivision(ApplicationDetailsActivity.this,
                    strings[0]);
        }

        @Override
        protected void onPostExecute(List<ExServiceMan> exServiceMEN) {
            super.onPostExecute(exServiceMEN);
            if(null!=exServiceMEN){

            }
        }
    }
}
