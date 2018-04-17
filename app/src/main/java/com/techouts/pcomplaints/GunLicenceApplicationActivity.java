package com.techouts.pcomplaints;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.payumoney.sdkui.ui.utils.ResultModel;
import com.techouts.pcomplaints.custom.CustomDialog;
import com.techouts.pcomplaints.model.Area;
import com.techouts.pcomplaints.model.PermissionApplication;
import com.techouts.pcomplaints.utils.AppConstents;
import com.techouts.pcomplaints.utils.DataManager;
import com.techouts.pcomplaints.utils.DialogUtils;
import com.techouts.pcomplaints.utils.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.List;

public class GunLicenceApplicationActivity extends BaseActivity{

    private EditText edtFullName,edtOccupation,edtParentage,edtNationality,edtHeadForLicense,
            edtTelephone,edtPurposeOfArms,edtDescriptionOfArms,edtQtyAmmunition,edtOldLicenseNo;
    private RadioGroup rgSocialStatus;
    private TextView tvArea,tvTitle,tvEmail;
    private ImageView ivBack,ivCamera,ivUserImg;
    private String socialStatus="";
    private LinearLayout llApply;
    private static final int CAMERA_CAPTURE = 1;
    private static final String TAG = CyberCafeApplicationActivity.class.getSimpleName();
    private CustomDialog customDialog;


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
        tvEmail = findViewById(R.id.tvEmail);
        edtTelephone = findViewById(R.id.edtTelephone);
        edtPurposeOfArms = findViewById(R.id.edtPurposeOfArms);
        edtDescriptionOfArms = findViewById(R.id.edtDescriptionOfArms);
        edtQtyAmmunition = findViewById(R.id.edtQtyAmmunition);
        edtOldLicenseNo = findViewById(R.id.edtOldLicenseNo);
        rgSocialStatus = findViewById(R.id.rgSocialStatus);
        tvTitle = findViewById(R.id.tvTitle);
        tvArea = findViewById(R.id.tvListName);
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
        tvEmail.setText(SharedPreferenceUtils.getStringValue(AppConstents.EMAIL_ID)+"");
    }

    @Override
    public void initData() {

        tvArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Area> areaList = DataManager.getAreaList();
                customDialog = new CustomDialog(GunLicenceApplicationActivity.this, areaList,
                        "Select Area",true,true,false,
                        new CustomDialog.NameSelectedListener() {
                            @Override
                            public void onNameSelected(String listName) {
                                tvArea.setText(listName);
                                customDialog.dismiss();
                            }
                        });
                customDialog.show();
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
            String email = tvEmail.getText().toString().trim();
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
                permissionApplication.applicationType =AppConstents.GUN_LICENCES;
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


    class GunLicenceAsyncTask extends AsyncTask<List<PermissionApplication>,Void,Void>{

        @Override
        protected Void doInBackground(List<PermissionApplication>... lists) {
//            DatabaseHandler.getInstance(getApplicationContext()).permissionApplicationDao().insertAll(lists[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            launchPayUMoneyFlow();
//            DialogUtils.showDialog(GunLicenceApplicationActivity.this,"Application Submitted Successfully", AppConstents.FINISH,false);
        }
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_CAPTURE) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                storeImage(bitmap);
                ivUserImg.setImageBitmap(bitmap);
            }
            else if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && null != data) {
                TransactionResponse transactionResponse = data.getParcelableExtra(PayUmoneyFlowManager
                        .INTENT_EXTRA_TRANSACTION_RESPONSE);

                ResultModel resultModel = data.getParcelableExtra(PayUmoneyFlowManager.ARG_RESULT);

                // Check which object is non-null
                if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {
                    if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.SUCCESSFUL)) {
                        //Success Transaction
                            DialogUtils.showDialog(GunLicenceApplicationActivity.this,"Payment Successful", AppConstents.FINISH,false);
                    } else {
                        //Failure Transaction
                            DialogUtils.showDialog(GunLicenceApplicationActivity.this,getResources().getString(R.string.error_message), AppConstents.FINISH,false);
                    }

                    // Response from Payumoney
                    String payuResponse = transactionResponse.getPayuResponse();

                    // Response from SURl and FURL
                    String merchantResponse = transactionResponse.getTransactionDetails();

//                    DialogUtils.showDialog(GunLicenceApplicationActivity.this, "Payment Successful", AppConstents.FINISH, false);


//                    new AlertDialog.Builder(this)
//                            .setCancelable(false)
//                            .setMessage("Payment Successful")
//                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int whichButton) {
//                                    dialog.dismiss();
//                                    finish();
//                                }
//                            }).show();

                } else if (resultModel != null && resultModel.getError() != null) {
                    Log.d(TAG, "Error response : " + resultModel.getError().getTransactionResponse());
                } else {
                    Log.d(TAG, "Both objects are null!");
                }

            }
        }
    }
}
