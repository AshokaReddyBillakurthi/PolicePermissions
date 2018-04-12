package com.techouts.pcomplaints;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.payumoney.sdkui.ui.utils.ResultModel;
import com.techouts.pcomplaints.adapters.ComplaintInstructionAdapter;
import com.techouts.pcomplaints.database.UserDataHelper;
import com.techouts.pcomplaints.entities.Application;
import com.techouts.pcomplaints.entities.User;
import com.techouts.pcomplaints.utils.AppConstents;
import com.techouts.pcomplaints.utils.DataManager;
import com.techouts.pcomplaints.utils.DialogUtils;
import com.techouts.pcomplaints.utils.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PermissionInstructionActivity extends BaseActivity {

    private RecyclerView rvComplaintInstructions;
    private ImageView ivBack, ivCamera, ivUserImg;
    private TextView tvTitle,tvTermsandConditions;
    private CheckBox cbxTermsAndConditions;
    private LinearLayout llApply;
    private static final int CAMERA_CAPTURE = 1;
    private ComplaintInstructionAdapter complaintInstructionAdapter;
    private String applicationType;
    private String TAG = PermissionInstructionActivity.class.getSimpleName();
    private User user = null;
    private  ArrayList<Application> listApplications;

    @Override
    public int getRootLayout() {
        return R.layout.activity_permission_instruction;
    }

    @Override
    public void initGUI() {

        if (getIntent().getExtras() != null) {
            applicationType = getIntent().getStringExtra(AppConstents.EXTRA_APPLICATION_TYPE);
        }

        rvComplaintInstructions = findViewById(R.id.rvComplaintInstructions);
        ivBack = findViewById(R.id.ivBack);
        ivCamera = findViewById(R.id.ivCamera);
        ivUserImg = findViewById(R.id.ivUserImg);
        tvTitle = findViewById(R.id.tvTitle);
        tvTermsandConditions = findViewById(R.id.tvTermsandConditions);
        llApply = findViewById(R.id.llApply);
        cbxTermsAndConditions = findViewById(R.id.cbxTermsAndConditions);

        rvComplaintInstructions.setLayoutManager(new LinearLayoutManager(PermissionInstructionActivity.this));
        complaintInstructionAdapter = new ComplaintInstructionAdapter();
        rvComplaintInstructions.setAdapter(complaintInstructionAdapter);
        switch (applicationType) {
            case AppConstents.INTERNET_CAFES:
                tvTitle.setText(AppConstents.INTERNET_CAFES);
                break;
            case AppConstents.GUN_LICENCES:
                tvTitle.setText(AppConstents.GUN_LICENCES);
                break;
            default:
                break;
        }

        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        llApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValidData()&&null!=user) {
                    long application_no = SharedPreferenceUtils.getLongValue(SharedPreferenceUtils.APPLICATION_NO);
                    if (application_no == 0) {
                        application_no = 101;
                        SharedPreferenceUtils.putLongValue(SharedPreferenceUtils.APPLICATION_NO, application_no);
                    } else {
                        application_no = application_no + 1;
                        SharedPreferenceUtils.putLongValue(SharedPreferenceUtils.APPLICATION_NO, application_no);
                    }
                    listApplications = new ArrayList<>();
                    String uniqueID = UUID.randomUUID().toString();
                    Application application = new Application();
                    application.applicationNo = uniqueID;
                    application.firstName = user.firstName;
                    application.lastName = user.lastName;
                    application.applicationType = applicationType;
                    application.mobileNo = user.mobileNo;
                    application.email = user.email;
                    application.area = user.area;
                    application.city = user.city;
                    application.state = user.state;
                    application.status = 0;
                    if(cbxTermsAndConditions.isChecked())
                        application.isAccepted = 1;
                    application.userImg = userImg;
                    listApplications.add(application);
                    launchPayUMoneyFlow();
                }
            }
        });

        tvTermsandConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PermissionInstructionActivity.this,TermsAndConditionsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_CAPTURE);
    }

    private boolean isValidData(){
        boolean isValid = true;
        if(TextUtils.isEmpty(userImg)){
            isValid = false;
            showToast("Please capture photo");
        } else if(!cbxTermsAndConditions.isChecked()){
            isValid = false;
            showToast("Please accept terms and conditions");
        }
        return isValid;
    }

    @Override
    public void initData() {

       new GetUserDataAsyncTask().execute();

        List<String> permissionInstructionList = null;
        try {
            switch (applicationType) {
                case AppConstents.INTERNET_CAFES:
                    permissionInstructionList = DataManager.getInstructionsForCyberCafesPermission();
                    break;
                case AppConstents.GUN_LICENCES:
                    permissionInstructionList = DataManager.getInstructionForArmsLicense();
                    break;
                default:
                    break;
            }
            if (permissionInstructionList != null && !permissionInstructionList.isEmpty())
                complaintInstructionAdapter.refresh(permissionInstructionList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class GetUserDataAsyncTask extends AsyncTask<Void,Void,User>{

        @Override
        protected User doInBackground(Void... voids) {
//            User user = DatabaseHandler.getInstance(PermissionInstructionActivity.this).userDao().
//                    getUserDetailsByEmailId(SharedPreferenceUtils.getStringValue(AppConstents.EMAIL_ID));
            User user = UserDataHelper.getUserByEmailId(PermissionInstructionActivity.this,
                    SharedPreferenceUtils.getStringValue(AppConstents.EMAIL_ID));

            return user;
        }

        @Override
        protected void onPostExecute(User userdata) {
            super.onPostExecute(userdata);
            if(null!= userdata)
                user = userdata;
        }
    }

    class SendApplicationAsyncTask extends AsyncTask<ArrayList<Application>,Void,Boolean> {

        @Override
        protected Boolean doInBackground(ArrayList<Application>[] arrayLists) {
//            DatabaseHandler.getInstance(getApplicationContext()).applicationDao().insertAll(arrayLists[0]);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_CAPTURE && null!=data) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                storeImage(bitmap);
                ivUserImg.setImageBitmap(bitmap);
            } else if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && null != data) {
                TransactionResponse transactionResponse = data.getParcelableExtra(PayUmoneyFlowManager
                        .INTENT_EXTRA_TRANSACTION_RESPONSE);

                ResultModel resultModel = data.getParcelableExtra(PayUmoneyFlowManager.ARG_RESULT);

                // Check which object is non-null
                if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {
                    if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.SUCCESSFUL)) {
                        new SendApplicationAsyncTask().execute(listApplications);
                        //Success Transaction
                        DialogUtils.showDialog(PermissionInstructionActivity.this,"Payment Successful", AppConstents.FINISH,false);
                    } else {
                        //Failure Transaction
                        DialogUtils.showDialog(PermissionInstructionActivity.this, "Payment Failed, Please try again after sometime.", AppConstents.FINISH, false);
                    }

//                    // Response from Payumoney
//                    String payuResponse = transactionResponse.getPayuResponse();
//
//                    // Response from SURl and FURL
//                    String merchantResponse = transactionResponse.getTransactionDetails();

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
