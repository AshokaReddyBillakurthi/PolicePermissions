package com.techouts.pcomplaints;

import android.app.Activity;
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
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.payumoney.core.PayUmoneySdkInitializer;
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

public class CrimeReportApplicationActivity extends BaseActivity{

    private EditText edtFirstName, edtLastName, edtOccupation,
            edtMobileNo, edtAddress, edtValueOfProperty;
    private RadioGroup rgSex, rgPlace, rgConditionOfProperty;
    private TextView tvTitle, tvArea,tvEmail;
    private ImageView ivBack, ivCamera, ivUserImg;
    private LinearLayout llApply;
    private String applicationType = "";
    private static final int CAMERA_CAPTURE = 1;
    private static final String TAG = CyberCafeApplicationActivity.class.getSimpleName();
    private PopupWindow popupWindow;
    private PermissionApplication permissionApplication;
    private CustomDialog customDialog;
    private PayUmoneySdkInitializer.PaymentParam mPaymentParams;


    @Override
    public int getRootLayout() {
        return R.layout.activity_crime_report_application;
    }

    @Override
    public void initGUI() {
        if (getIntent().getExtras() != null) {
            applicationType = getIntent().getExtras().getString(AppConstents.EXTRA_APPLICATION_TYPE);
        }
        tvTitle = findViewById(R.id.tvTitle);
        ivBack = findViewById(R.id.ivBack);
        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        edtOccupation = findViewById(R.id.edtOccupation);
        tvEmail = findViewById(R.id.tvEmail);
        edtMobileNo = findViewById(R.id.edtMobileNo);
        edtAddress = findViewById(R.id.edtAddress);
        edtValueOfProperty = findViewById(R.id.edtValueOfProperty);
        tvArea = findViewById(R.id.tvListName);
        ivCamera = findViewById(R.id.ivCamera);
        ivUserImg = findViewById(R.id.ivUserImg);
        rgSex = findViewById(R.id.rgSex);
        rgPlace = findViewById(R.id.rgPlace);
        rgConditionOfProperty = findViewById(R.id.rgConditionOfProperty);
        llApply = findViewById(R.id.llApply);

        tvTitle.setText(applicationType + " Application");
        tvEmail.setText(SharedPreferenceUtils.getStringValue(AppConstents.EMAIL_ID)+"");

    }

    @Override
    public void initData() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Area> areaList = DataManager.getAreaList();
                customDialog = new CustomDialog(CrimeReportApplicationActivity.this, areaList,
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
                applyCrimeReport();
            }
        });
    }

    private void applyCrimeReport() {
        try {
            String firstName = edtFirstName.getText().toString().trim();
            String occupation = edtOccupation.getText().toString().trim();
            String lastName = edtLastName.getText().toString().trim();
            String email = tvEmail.getText().toString().trim();
            String telephoneNo = edtMobileNo.getText().toString().trim();
            String address = edtAddress.getText().toString().trim();
            String valueOfProperty = edtValueOfProperty.getText().toString().trim();
            String area = tvArea.getText().toString().trim();
            int sexId = rgSex.getCheckedRadioButtonId();
            RadioButton rbSex = findViewById(sexId);
            String sex = rbSex.getText().toString();
            int placeId = rgPlace.getCheckedRadioButtonId();
            RadioButton rbPlace = findViewById(placeId);
            String place = rbPlace.getText().toString();
            int condPropId = rgConditionOfProperty.getCheckedRadioButtonId();
            RadioButton rbCondPropId = findViewById(condPropId);
            String condOfProp = rbCondPropId.getText().toString();
            if (validateData(firstName, lastName,occupation,email,telephoneNo,address,valueOfProperty,area)) {
                ArrayList<PermissionApplication> listPermissionApplications = new ArrayList<>();
                permissionApplication = new PermissionApplication();
                long application_no = SharedPreferenceUtils.getLongValue(SharedPreferenceUtils.APPLICATION_NO);
                if (application_no == 0) {
                    application_no = 101;
                    SharedPreferenceUtils.putLongValue(SharedPreferenceUtils.APPLICATION_NO, application_no);
                } else {
                    application_no = application_no + 1;
                    SharedPreferenceUtils.putLongValue(SharedPreferenceUtils.APPLICATION_NO, application_no);
                }
                permissionApplication.applicationNo = application_no + "";
                permissionApplication.fullName = firstName+" "+lastName;
                permissionApplication.occupation = occupation;
                permissionApplication.owneremail = email;
                permissionApplication.telephoneNo = telephoneNo;
                permissionApplication.applicantImg = userImg;
                permissionApplication.area = area;
                permissionApplication.applicationType = applicationType;
                permissionApplication.sex = sex;
                permissionApplication.placeOfCrime = place;
                permissionApplication.address = address;
                permissionApplication.condOfProp =condOfProp;
                permissionApplication.applicationType = AppConstents.CRIME_REPORT;
                listPermissionApplications.add(permissionApplication);
                new CrimeReportAsyncTask().execute(listPermissionApplications);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class CrimeReportAsyncTask extends AsyncTask<ArrayList<PermissionApplication>,Void,Boolean>{

        @Override
        protected Boolean doInBackground(ArrayList<PermissionApplication>[] arrayLists) {
//            DatabaseHandler.getInstance(getApplicationContext()).permissionApplicationDao().insertAll(arrayLists[0]);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            launchPayUMoneyFlow();
//            DialogUtils.showDialog(CrimeReportApplicationActivity.this,"Application Submitted Successfully", AppConstents.FINISH,false);
        }
    }


    private boolean validateData(String firstName, String lastName, String occupation, String email,
                                 String telephoneNo, String address, String valueOfProperty, String area) {
        boolean isValid = true;
        if (TextUtils.isEmpty(firstName)) {
            isValid = false;
            showToast("Please enter first name");
        } else if (TextUtils.isEmpty(lastName)) {
            isValid = false;
            showToast("Please enter last name");
        } else if (TextUtils.isEmpty(occupation)) {
            isValid = false;
            showToast("Please enter occupation");
        } else if (TextUtils.isEmpty(email)) {
            isValid = false;
            showToast("Please enter email");
        } else if (!(Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
            isValid = false;
            showToast("Please enter valid email");
        } else if (TextUtils.isEmpty(telephoneNo)) {
            isValid = false;
            showToast("Please enter telephone number");
        } else if (!(telephoneNo.length() == 10)) {
            isValid = false;
            showToast("Please enter 10 digit telephone number");
        } else if (TextUtils.isEmpty(address)) {
            isValid = false;
            showToast("Please enter address");
        } else if (TextUtils.isEmpty(valueOfProperty)) {
            isValid = false;
            showToast("Please enter value of property");
        } else if (TextUtils.isEmpty(area)) {
            isValid = false;
            showToast("Please select area");
        } else if (TextUtils.isEmpty(userImg)) {
            isValid = false;
            showToast("Please capture applicant picture");
        }
        return isValid;
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK){
            if (requestCode == CAMERA_CAPTURE&&null!=data) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                storeImage(bitmap);
                ivUserImg.setImageBitmap(bitmap);
            }
            else if(requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT&&null!=data){
                TransactionResponse transactionResponse = data.getParcelableExtra(PayUmoneyFlowManager
                        .INTENT_EXTRA_TRANSACTION_RESPONSE);

                ResultModel resultModel = data.getParcelableExtra(PayUmoneyFlowManager.ARG_RESULT);

                // Check which object is non-null
                if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {
                    if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.SUCCESSFUL)) {
                        //Success Transaction
//                        DialogUtils.showDialog(CrimeReportApplicationActivity.this,"Payment Successful", AppConstents.FINISH,false);
                    } else {
                        //Failure Transaction
//                        DialogUtils.showDialog(CrimeReportApplicationActivity.this,getResources().getString(R.string.error_message), AppConstents.FINISH,false);
                    }

                    // Response from Payumoney
                    String payuResponse = transactionResponse.getPayuResponse();

                    // Response from SURl and FURL
                    String merchantResponse = transactionResponse.getTransactionDetails();


                    DialogUtils.showDialog(CrimeReportApplicationActivity.this,"Payment Successful", AppConstents.FINISH,false);
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
