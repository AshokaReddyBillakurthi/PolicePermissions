package com.techouts.pcomplaints;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techouts.pcomplaints.utils.AppConstents;
import com.techouts.pcomplaints.utils.DialogUtils;
import com.techouts.pcomplaints.utils.SharedPreferenceUtils;

import java.io.IOException;

public class MatrimonialVerificationApplicationActivity extends BaseActivity {

    private EditText edtFullName, edtOccupation, edtMobileNo, edtRequiredInfo,
            edtAddress;
    private Button btnUploadImage;
    private ImageView ivCounterPersonImg, ivBack;
    private LinearLayout llSubmitEnquiry;
    private TextView tvTitle,tvEmail;
    private static final int SELECT_FILE = 100;

    @Override
    public int getRootLayout() {
        return R.layout.activity_matrimonial_verification_application;
    }

    @Override
    public void initGUI() {
        edtFullName = findViewById(R.id.edtFullName);
        edtOccupation = findViewById(R.id.edtOccupation);
        tvEmail = findViewById(R.id.tvEmail);
        edtMobileNo = findViewById(R.id.edtMobileNo);
        edtRequiredInfo = findViewById(R.id.edtRequiredInfo);
        edtAddress = findViewById(R.id.edtAddress);
        btnUploadImage = findViewById(R.id.btnUploadImage);
        ivCounterPersonImg = findViewById(R.id.ivCounterPersonImg);
        llSubmitEnquiry = findViewById(R.id.llSubmitEnquiry);
        ivBack = findViewById(R.id.ivBack);
        tvTitle = findViewById(R.id.tvTitle);

        tvTitle.setText("Matrimonial Verification Application");

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

        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImageFromGallery();
            }
        });

        llSubmitEnquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyForMatrimonialVerification();
            }
        });
    }

    private void applyForMatrimonialVerification() {
        try {
            String fullName = edtFullName.getText().toString().trim();
            String occupation = edtOccupation.getText().toString().trim();
            String email = tvEmail.getText().toString().trim();
            String mobileNo = edtMobileNo.getText().toString().trim();
            String requiredInfo = edtRequiredInfo.getText().toString().trim();
            String address = edtAddress.getText().toString().trim();
            if(isValidData(fullName,occupation,email,mobileNo,requiredInfo,address)){
                DialogUtils.showDialog(MatrimonialVerificationApplicationActivity.this,"Applied Successfully", AppConstents.FINISH,false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private boolean isValidData(String fullName, String occupation, String email, String mobileNo,
                                String requredInfo, String address) {
        boolean isValid = true;
        if (TextUtils.isEmpty(fullName)) {
            showToast("Please enter full name");
            isValid = false;
        } else if (TextUtils.isEmpty(occupation)) {
            showToast("Please enter occupation");
            isValid = false;
        } else if (TextUtils.isEmpty(email)) {
            showToast("Please enter email");
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("Please enter valid email id");
            isValid = false;
        } else if (TextUtils.isEmpty(mobileNo)) {
            showToast("Please enter mobile number");
            isValid = false;
        } else if (mobileNo.length()!= 10) {
            showToast("Please enter 10 digit mobile number");
            isValid = false;
        } else if (TextUtils.isEmpty(requredInfo)) {
            showToast("Please enter requirement indetail");
            isValid = false;
        } else if (TextUtils.isEmpty(address)) {
            showToast("Please enter address of counter person");
            isValid = false;
        } else if(TextUtils.isEmpty(userImg)){
            showToast("Please upload counter person image");
            isValid = false;
        }
        return isValid;
    }


    private void uploadImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                Bitmap bm = null;
                if (data != null) {
                    try {
                        bm = MediaStore.Images.Media.getBitmap(getApplicationContext()
                                .getContentResolver(), data.getData());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                storeImage(bm);
                ivCounterPersonImg.setImageBitmap(bm);
            }
        }
    }
}
