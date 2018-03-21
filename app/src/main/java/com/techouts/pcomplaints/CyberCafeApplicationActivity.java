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

import com.techouts.pcomplaints.custom.CustomDialog;
import com.techouts.pcomplaints.datahandler.DatabaseHandler;
import com.techouts.pcomplaints.entities.PermissionApplication;
import com.techouts.pcomplaints.utils.AppConstents;
import com.techouts.pcomplaints.utils.DataManager;
import com.techouts.pcomplaints.utils.DialogUtils;
import com.techouts.pcomplaints.utils.SharedPreferenceUtils;
import com.techouts.pcomplaints.adapters.AreaAdapter;

import java.util.ArrayList;
import java.util.List;

public class CyberCafeApplicationActivity extends BaseActivity {
    private EditText edtFullName, edtOccupation, edtParentage, edtNationality,
            edtOwnerEmail, edtTelephone, edtCyberCafeEmail, edtNoOfBranchs, edtAreaOfPremise,
            edtNoOfTerminals;
    private RadioGroup rbGroup;
    private LinearLayout llApply;
    private TextView tvTitle,tvArea;
    private ImageView ivBack, ivCamera, ivUserImg;
    private static final int CAMERA_CAPTURE = 1;
    private static final String TAG = CyberCafeApplicationActivity.class.getSimpleName();
    private String premise = "";
    private PermissionApplication permissionApplication;
    private PopupWindow popupWindow;
    private String applicationType = "";
    private CustomDialog customDialog;

    @Override
    public int getRootLayout() {
        return R.layout.activity_cyber_cafe_application_form;
    }

    @Override
    public void initGUI() {
        if(getIntent().getExtras()!=null){
            applicationType = getIntent().getExtras().getString(AppConstents.EXTRA_APPLICATION_TYPE);
        }
        edtFullName = findViewById(R.id.edtFullName);
        edtOccupation = findViewById(R.id.edtOccupation);
        edtParentage = findViewById(R.id.edtParentage);
        edtNationality = findViewById(R.id.edtNationality);
        edtOwnerEmail = findViewById(R.id.edtOwnerEmail);
        edtTelephone = findViewById(R.id.edtTelephone);
        edtCyberCafeEmail = findViewById(R.id.edtCyberCafeEmail);
        edtNoOfBranchs = findViewById(R.id.edtNoOfBranchs);
        edtAreaOfPremise = findViewById(R.id.edtAreaOfPremise);
        edtNoOfTerminals = findViewById(R.id.edtNoOfTerminals);
        rbGroup = findViewById(R.id.rbGroup);
        llApply = findViewById(R.id.llApply);
        tvTitle = findViewById(R.id.tvTitle);
        tvArea = findViewById(R.id.tvListName);
        ivBack = findViewById(R.id.ivBack);
        ivCamera = findViewById(R.id.ivCamera);
        ivUserImg = findViewById(R.id.ivUserImg);

        tvTitle.setText(applicationType+" Application");

    }

    @Override
    public void initData() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

        tvArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvArea.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<String> areaList = DataManager.getList(AppConstents.TYPE_AREA);
                        customDialog = new CustomDialog(CyberCafeApplicationActivity.this, areaList,
                                "Select Area",true,
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

            }
        });
    }


    private void applyPermissionForCyberCafe() {
        try {
            String fullName = edtFullName.getText().toString().trim();
            String occupation = edtOccupation.getText().toString().trim();
            String parentage = edtParentage.getText().toString().trim();
            String nationality = edtNationality.getText().toString().trim();
            String owneremail = edtOwnerEmail.getText().toString().trim();
            String telephoneNo = edtTelephone.getText().toString().trim();
            String cyberCafeEmail = edtCyberCafeEmail.getText().toString().trim();
            String noOfBranchs = edtNoOfBranchs.getText().toString().trim();
            String areaOfPremise = edtAreaOfPremise.getText().toString().trim();
            String noOfTerminals = edtNoOfTerminals.getText().toString().trim();
            String area = tvArea.getText().toString().trim();
            int selectedId = rbGroup.getCheckedRadioButtonId();
            RadioButton radioButton = findViewById(selectedId);
            premise = radioButton.getText().toString();
            if (validateData(fullName, occupation, parentage, nationality, owneremail,
                    telephoneNo, cyberCafeEmail, noOfBranchs, areaOfPremise, noOfTerminals,area)) {
                ArrayList<PermissionApplication> listPermissionApplications = new ArrayList<>();
                permissionApplication = new PermissionApplication();
                long application_no = SharedPreferenceUtils.getLongValue(SharedPreferenceUtils.APPLICATION_NO);
                if(application_no == 0){
                    application_no = 101;
                    SharedPreferenceUtils.putLongValue(SharedPreferenceUtils.APPLICATION_NO,application_no);
                }
                else{
                    application_no = application_no+1;
                    SharedPreferenceUtils.putLongValue(SharedPreferenceUtils.APPLICATION_NO,application_no);
                }
                permissionApplication.applicationNo = application_no+"";
                permissionApplication.fullName = fullName;
                permissionApplication.occupation = occupation;
                permissionApplication.parentage = parentage;
                permissionApplication.nationality = nationality;
                permissionApplication.owneremail = owneremail;
                permissionApplication.telephoneNo = telephoneNo;
                permissionApplication.cyberCafeEmail = cyberCafeEmail;
                permissionApplication.noOfBranchs = noOfBranchs;
                permissionApplication.areaOfPremise = areaOfPremise;
                permissionApplication.noOfTerminals = noOfTerminals;
                permissionApplication.applicantImg = userImg;
                permissionApplication.area = area;
                permissionApplication.applicationType = AppConstents.INTERNET_CAFES;
                listPermissionApplications.add(permissionApplication);
                new ApplyPermissionAsyncTask().execute(listPermissionApplications);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private boolean validateData(String fullName, String occupation, String parentage,
                                 String nationality, String owneremail, String telephoneNo,
                                 String cyberCafeEmail, String noOfBranchs,
                                 String areaOfPremise, String noOfTerminals,String area) {
        boolean isValid = true;
        if (TextUtils.isEmpty(fullName)) {
            isValid = false;
            showToast("Please enter full name");
        } else if (TextUtils.isEmpty(occupation)) {
            isValid = false;
            showToast("Please enter occupation");
        } else if (TextUtils.isEmpty(parentage)) {
            isValid = false;
            showToast("Please enter Father's/Husband Name");
        } else if (TextUtils.isEmpty(telephoneNo)) {
            isValid = false;
            showToast("Please enter telephone number");
        } else if (!(telephoneNo.length() == 10)) {
            isValid = false;
            showToast("Please enter 10 digit telephone number");
        } else if (TextUtils.isEmpty(nationality)) {
            isValid = false;
            showToast("Please enter nationality");
        } else if (TextUtils.isEmpty(owneremail)) {
            isValid = false;
            showToast("Please enter owner email");
        } else if (!(Patterns.EMAIL_ADDRESS.matcher(owneremail).matches())) {
            isValid = false;
            showToast("Please enter valid owner email");
        } else if (TextUtils.isEmpty(cyberCafeEmail)) {
            isValid = false;
            showToast("Please enter Cyber Cafe Email");
        } else if (TextUtils.isEmpty(noOfBranchs)) {
            isValid = false;
            showToast("Please enter No. of Branchs");
        } else if(TextUtils.isEmpty(area)){
            isValid = false;
            showToast("Please select area");
        } else if (TextUtils.isEmpty(areaOfPremise)) {
            isValid = false;
            showToast("Please enter area of premise");
        } else if (TextUtils.isEmpty(noOfTerminals)) {
            isValid = false;
            showToast("Please enter No. of Terminals");
        } else if(TextUtils.isEmpty(userImg)){
            isValid = false;
            showToast("Please capture applicant picture");
        }
        return isValid;
    }

    private class ApplyPermissionAsyncTask extends AsyncTask<ArrayList<PermissionApplication>, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Boolean doInBackground(ArrayList<PermissionApplication>[] arrayLists) {
            DatabaseHandler.getInstance(getApplicationContext()).permissionApplicationDao().insertAll(arrayLists[0]);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            DialogUtils.showDialog(CyberCafeApplicationActivity.this,"Application Submitted Successfully", AppConstents.FINISH,false);
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
}
