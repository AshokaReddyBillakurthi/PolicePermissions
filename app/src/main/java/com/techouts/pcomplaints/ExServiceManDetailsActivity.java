package com.techouts.pcomplaints;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techouts.pcomplaints.database.XServiceManDataHelper;
import com.techouts.pcomplaints.model.ExServiceMan;
import com.techouts.pcomplaints.utils.AppConstents;

public class ExServiceManDetailsActivity extends BaseActivity {

    private TextView tvTitle, tvFirstName, tvLastName, tvArea,
            tvAccept, tvReject, tvDocList, tvCity, tvState,
            tvEmail, tvMobileNo, tvServices;
    private ImageView ivBack, ivUserImage;
    private ExServiceMan exServiceMan;
    private String loginType;
    private LinearLayout llActions;

    @Override
    public int getRootLayout() {
        return R.layout.activity_ex_service_man_details;
    }

    @Override
    public void initGUI() {
        if (getIntent().getExtras() != null) {
            exServiceMan = (ExServiceMan) getIntent().getExtras()
                    .getSerializable(AppConstents.EXTRA_USER);
            loginType = getIntent().getExtras()
                    .getString(AppConstents.EXTRA_LOGIN_TYPE, "");
        }
        tvTitle = findViewById(R.id.tvTitle);
        tvFirstName = findViewById(R.id.tvFirstName);
        tvLastName = findViewById(R.id.tvLastName);
        tvEmail = findViewById(R.id.tvEmail);
        tvMobileNo = findViewById(R.id.tvMobileNo);
        tvServices = findViewById(R.id.tvServices);
        tvArea = findViewById(R.id.tvListName);
        tvCity = findViewById(R.id.tvCity);
        tvState = findViewById(R.id.tvState);
        tvAccept = findViewById(R.id.tvAccept);
        tvReject = findViewById(R.id.tvReject);
        tvDocList = findViewById(R.id.tvDocList);
        ivBack = findViewById(R.id.ivBack);
        llActions = findViewById(R.id.llActions);
        ivUserImage = findViewById(R.id.ivUserImage);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exServiceMan.status = 1;
                new UpdateExserviceManStatus().execute(exServiceMan);
            }
        });

        tvReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exServiceMan.status = -1;
                new UpdateExserviceManStatus().execute(exServiceMan);
            }
        });

    }

    @Override
    public void initData() {
        if (exServiceMan != null) {
            tvTitle.setText(exServiceMan.firstName + " " + exServiceMan.lastName);
            tvFirstName.setText(exServiceMan.firstName + "");
            tvLastName.setText(exServiceMan.lastName + "");
            tvEmail.setText(exServiceMan.email + "");
            tvMobileNo.setText(exServiceMan.mobileNo + "");
            tvServices.setText(exServiceMan.services + "");
            tvDocList.setText(exServiceMan.reqDocs + "");
            tvArea.setText(exServiceMan.area + "");
            tvCity.setText(exServiceMan.city + "");
            tvState.setText(exServiceMan.state + "");
            Bitmap bitmap = getUserImageBitMap(exServiceMan.userImg);
            if (bitmap != null)
                ivUserImage.setImageBitmap(bitmap);

            if((exServiceMan.status == 0)&&!(loginType.equalsIgnoreCase(AppConstents.LOGIN_TYPE_NONE))){
                llActions.setVisibility(View.VISIBLE);
            }
            else{
                llActions.setVisibility(View.GONE);
            }
        }
    }

    private class UpdateExserviceManStatus extends AsyncTask<ExServiceMan, Void, Boolean> {

        @Override
        protected Boolean doInBackground(ExServiceMan... exServiceMEN) {
            boolean isUpdated = XServiceManDataHelper.updateStatus(ExServiceManDetailsActivity.this,
                    exServiceMEN[0].status+"",exServiceMEN[0].email);
            return isUpdated;
        }

        @Override
        protected void onPostExecute(Boolean isUpdated) {
            super.onPostExecute(isUpdated);
            if (isUpdated) {
                showToast(exServiceMan.firstName + " " + exServiceMan.lastName + " " +
                        "Application Accepted Successfully");
                finish();
            } else {
                showToast(exServiceMan.firstName + " " + exServiceMan.lastName + " " +
                        "Application Rejected Successfully");
                finish();
            }
        }
    }
}
