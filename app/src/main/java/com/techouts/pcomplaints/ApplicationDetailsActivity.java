package com.techouts.pcomplaints;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.techouts.pcomplaints.entities.PermissionApplication;
import com.techouts.pcomplaints.utils.AppConstents;

public class ApplicationDetailsActivity extends BaseActivity {

    private TextView tvTitle,tvFullName,tvEmail,tvMobileNo,tvApplicationType,
            tvArea,tvOccupation,tvStatus;
    private ImageView ivBack,ivUserImage;
    private PermissionApplication permissionApplication;

    @Override
    public int getRootLayout() {
        return R.layout.activity_application_details;
    }

    @Override
    public void initGUI() {
        if(getIntent().getExtras()!=null){
            permissionApplication = (PermissionApplication)getIntent().getExtras()
                    .getSerializable(AppConstents.EXTRA_PERMISSION_APPLICATION);
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

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initData() {
        if(permissionApplication!=null){
            tvFullName.setText(permissionApplication.fullName+"");
            tvOccupation.setText(permissionApplication.occupation+"");
            tvEmail.setText(permissionApplication.owneremail+"");
            tvMobileNo.setText(permissionApplication.telephoneNo+"");
            tvApplicationType.setText(permissionApplication.applicationType+"");
            tvArea.setText(permissionApplication.area+"");
            tvTitle.setText(permissionApplication.applicationType+"");
            if(permissionApplication.status == 0) {
               tvStatus.setText(AppConstents.PENDING);
               tvStatus.setTextColor(getColor(R.color.error_strip));
            }
            else if(permissionApplication.status == 1){
                tvStatus.setText(AppConstents.INPROGRESS);
                tvStatus.setTextColor(getColor(R.color.orange));
            }
            else if(permissionApplication.status == 2){
               tvStatus.setText(AppConstents.COMPLETED);
               tvStatus.setTextColor(getColor(R.color.button_green));
            }
            Bitmap bitmap = getUserImageBitMap(permissionApplication.applicantImg);
            if(bitmap!=null){
                ivUserImage.setImageBitmap(bitmap);
            }
        }
    }
}
