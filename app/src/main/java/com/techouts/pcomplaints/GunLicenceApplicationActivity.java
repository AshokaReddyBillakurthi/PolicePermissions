package com.techouts.pcomplaints;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.techouts.pcomplaints.datahandler.DatabaseHandler;
import com.techouts.pcomplaints.utils.AppConstents;
import com.techouts.pcomplaints.R;
import com.techouts.pcomplaints.adapters.AreaAdapter;
import com.techouts.pcomplaints.entities.PermissionApplication;
import com.techouts.pcomplaints.utils.DialogUtils;
import com.techouts.pcomplaints.utils.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.List;

public class GunLicenceApplicationActivity extends BaseActivity implements AreaAdapter.OnAreaClickListener{

    private EditText edtFullName,edtOccupation,edtParentage,edtNationality,edtHeadForLicense,
            edtTelephone,edtPurposeOfArms,edtDescriptionOfArms,edtQtyAmmunition,edtOldLicenseNo,edtEmail;
    private RadioGroup rgSocialStatus;
    private TextView tvArea,tvTitle;
    private ImageView ivBack,ivCamera,ivUserImg;
    private String socialStatus="";
    private LinearLayout llApply;
    private static final int CAMERA_CAPTURE = 1;
    private static final String TAG = CyberCafeApplicationActivity.class.getSimpleName();
    private PopupWindow popupWindow;

    @Override
    public int getRootLayout() {
        return R.layout.activity_gun_licence_application;
    }

    @Override
    public void initGUI() {
        edtFullName = findViewById(R.id.edtFullName);
        edtOccupation = findViewById(R.id.edtOccupation);
        edtParentage = findViewById(R.id.edtParentage);
        edtNationality = findViewById(R.id.edtNationality);
        edtHeadForLicense = findViewById(R.id.edtHeadForLicense);
        edtEmail = findViewById(R.id.edtEmail);
        edtTelephone = findViewById(R.id.edtTelephone);
        edtPurposeOfArms = findViewById(R.id.edtPurposeOfArms);
        edtDescriptionOfArms = findViewById(R.id.edtDescriptionOfArms);
        edtQtyAmmunition = findViewById(R.id.edtQtyAmmunition);
        edtOldLicenseNo = findViewById(R.id.edtOldLicenseNo);
        rgSocialStatus = findViewById(R.id.rgSocialStatus);
        tvTitle = findViewById(R.id.tvTitle);
        tvArea = findViewById(R.id.tvArea);
        llApply = findViewById(R.id.llApply);
        ivCamera = findViewById(R.id.ivCamera);
        ivUserImg = findViewById(R.id.ivUserImg);

        ivBack = findViewById(R.id.ivBack);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvTitle.setText("Gun Licence Application");


    }

    @Override
    public void initData() {

        tvArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiatePopupWindow(v);
            }
        });

        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });

        llApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyPermissionForCyberCafe();
            }
        });
    }

    private void applyPermissionForCyberCafe() {
        try {
            String fullName = edtFullName.getText().toString().trim();
            String occupation = edtOccupation.getText().toString().trim();
            String parentage = edtParentage.getText().toString().trim();
            String nationality = edtNationality.getText().toString().trim();
            String headForLicense = edtHeadForLicense.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String telephoneNo = edtTelephone.getText().toString().trim();
            String qtyAmmuition = edtQtyAmmunition.getText().toString().trim();
            String oldLicenseNo = edtOldLicenseNo.getText().toString().trim();
            String purposeOfArms  = edtPurposeOfArms.getText().toString().trim();
            String descriptionOfArms = edtDescriptionOfArms.getText().toString().trim();
            String area = tvArea.getText().toString().trim();
            int selectedId = rgSocialStatus.getCheckedRadioButtonId();
            RadioButton radioButton = findViewById(selectedId);
            socialStatus = radioButton.getText().toString();
            if (validateData(fullName, occupation, parentage, nationality, headForLicense,email,
                    telephoneNo,purposeOfArms,descriptionOfArms, qtyAmmuition, oldLicenseNo,area)) {
                ArrayList<PermissionApplication> listPermissionApplications = new ArrayList<>();
                long application_no = SharedPreferenceUtils.getLongValue(SharedPreferenceUtils.APPLICATION_NO);
                if(application_no == 0){
                    application_no = 101;
                    SharedPreferenceUtils.putLongValue(SharedPreferenceUtils.APPLICATION_NO,application_no);
                }
                else{
                    application_no = application_no+1;
                    SharedPreferenceUtils.putLongValue(SharedPreferenceUtils.APPLICATION_NO,application_no);
                }
                PermissionApplication permissionApplication = new PermissionApplication();
                permissionApplication.applicationNo = application_no+"";
                permissionApplication.fullName = fullName;
                permissionApplication.occupation = occupation;
                permissionApplication.parentage = parentage;
                permissionApplication.nationality = nationality;
                permissionApplication.telephoneNo = telephoneNo;
                permissionApplication.socialStatus = socialStatus;
                permissionApplication.headForLicense = headForLicense;
                permissionApplication.owneremail = email;
                permissionApplication.qtyAmmuition = qtyAmmuition;
                permissionApplication.oldLicenseNo = oldLicenseNo;
                permissionApplication.applicantImg = userImg;
                permissionApplication.area = area;
                permissionApplication.applicationType = "Gun Licence";
                listPermissionApplications.add(permissionApplication);
                new GunLicenceAsyncTask().execute(listPermissionApplications);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validateData(String fullName, String occupation, String parentage,String nationality,
                            String headForLicense,String email, String telephoneNo,String purposeOfArms,
                                 String descriptionOfArms, String qtyAmmuition, String oldLicenseNo,String area){

        boolean isValid = true;
        if(TextUtils.isEmpty(fullName)){
            showToast("Please enter full name");
            isValid = false;
        }
        else if(TextUtils.isEmpty(occupation)){
            showToast("Please enter occupation");
            isValid = false;
        }
        else if(TextUtils.isEmpty(parentage)){
            showToast("Please enter parentage");
            isValid = false;
        }
        else if(TextUtils.isEmpty(nationality)){
            showToast("Please enter nationality");
            isValid = false;
        }
        else if(TextUtils.isEmpty(headForLicense)){
            showToast("Please enter Head for License");
            isValid = false;
        }
        else if(TextUtils.isEmpty(email)){
            showToast("Please enter email id");
            isValid = false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            showToast("Please enter valid email id");
            isValid = false;
        }
        else if(TextUtils.isEmpty(telephoneNo)){
            showToast("Please enter Telephone Number");
            isValid = false;
        }
        else if(telephoneNo.length()!=10){
            showToast("Please enter 10 digit mobile number");
            isValid = false;
        }
        else if(TextUtils.isEmpty(purposeOfArms)){
            showToast("Please enter purpose of arms");
            isValid = false;
        }
        else if(TextUtils.isEmpty(descriptionOfArms)){
            showToast("Please enter description of arms");
            isValid = false;
        }
        else if(TextUtils.isEmpty(qtyAmmuition)){
            showToast("Please enter Quantity of Ammuition");
            isValid = false;
        }
        else if(TextUtils.isEmpty(oldLicenseNo)){
            showToast("Please enter Old License Number");
            isValid = false;
        }
        else if(TextUtils.isEmpty(area)){
            showToast("Please enter Area");
            isValid = false;
        }
        return isValid;

    }

    @Override
    public void onAreaSelected(String area) {
        if(popupWindow!=null)
            popupWindow.dismiss();

        tvArea.setText(area+"");
    }

    class GunLicenceAsyncTask extends AsyncTask<List<PermissionApplication>,Void,Void>{

        @Override
        protected Void doInBackground(List<PermissionApplication>... lists) {
            DatabaseHandler.getInstance(getApplicationContext()).permissionApplicationDao().insertAll(lists[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            DialogUtils.showDialog(GunLicenceApplicationActivity.this,"Application Submitted Successfully", AppConstents.FINISH,false);
        }
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_CAPTURE) {
            if (resultCode == RESULT_OK) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                storeImage(bitmap);
                ivUserImg.setImageBitmap(bitmap);
            } else if (resultCode == RESULT_CANCELED) {
                showToast("User cancelled image capture");
            } else {
                showToast("Failed to capture image");
            }
        }
    }

    private void initiatePopupWindow(View v) {
        try {
            popupWindow  = new PopupWindow(this);
            popupWindow.setFocusable(true);
            View view = LayoutInflater.from(GunLicenceApplicationActivity.this).inflate(R.layout.popup_window_area_list,null);
            RecyclerView rvAreas = view.findViewById(R.id.rvAreas);
            ArrayList<String> areaList = new ArrayList<>();
            areaList.add("Domalguda");
            areaList.add("Secunderabad");
            areaList.add("Bansilapet");
            areaList.add("Liberty");
            areaList.add("Hyderguda");
            areaList.add("Shyamalal");
            areaList.add("Ligampalli");
            rvAreas.setLayoutManager(new LinearLayoutManager(GunLicenceApplicationActivity.this));
            rvAreas.setAdapter(new AreaAdapter(areaList,GunLicenceApplicationActivity.this));
            popupWindow.setContentView(view);
            popupWindow.showAsDropDown(v, Gravity.CENTER,0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
