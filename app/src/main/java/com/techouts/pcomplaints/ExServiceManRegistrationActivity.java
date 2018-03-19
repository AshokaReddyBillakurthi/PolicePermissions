package com.techouts.pcomplaints;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.techouts.pcomplaints.custom.CustomDialog;
import com.techouts.pcomplaints.datahandler.DatabaseHandler;
import com.techouts.pcomplaints.entities.ExServiceMan;
import com.techouts.pcomplaints.utils.AppConstents;
import com.techouts.pcomplaints.utils.DataManager;
import com.techouts.pcomplaints.utils.DialogUtils;
import com.techouts.pcomplaints.utils.FilePathUtils;

import java.util.ArrayList;
import java.util.List;


public class ExServiceManRegistrationActivity extends BaseActivity {

    private EditText edtFirstName, edtLastName, edtExPoliceId, edtMobileNumber, edtEmail, edtPassword;
    private TextView tvState, tvCity, tvArea, tvTitle;
    private CheckBox cbxPolicePermissions, cbxPIdAddressTrace, cbxMatrimonialVerifications, cbxDraftingComplaints;
    private Button btnUploadDocs;
    private LinearLayout llDocuments, llRegister;
    private ImageView ivCamera, ivUserImg, ivBack;
    private static final int PICK_FILE_REQUEST = 100;
    private static final int CAMERA_CAPTURE = 101;
    private static final String TAG = ExServiceManRegistrationActivity.class.getSimpleName();
    private String selectedFilePath;
    private ArrayList<String> docList;
    private boolean isServiceSelectd = false;
    private CustomDialog customDialog = null;

    @Override
    public int getRootLayout() {
        return R.layout.activity_ex_service_man_registration;
    }

    @Override
    public void initGUI() {
        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        edtExPoliceId = findViewById(R.id.edtExPoliceId);
        edtMobileNumber = findViewById(R.id.edtMobileNumber);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        tvState = findViewById(R.id.tvState);
        tvCity = findViewById(R.id.tvCity);
        tvArea = findViewById(R.id.tvArea);
        tvTitle = findViewById(R.id.tvTitle);
        btnUploadDocs = findViewById(R.id.btnUploadDocs);
        llDocuments = findViewById(R.id.llDocuments);
        llRegister = findViewById(R.id.llRegister);
        ivCamera = findViewById(R.id.ivCamera);
        ivUserImg = findViewById(R.id.ivUserImg);
        ivBack = findViewById(R.id.ivBack);
        cbxPolicePermissions = findViewById(R.id.cbxPolicePermissions);
        cbxPIdAddressTrace = findViewById(R.id.cbxPIdAddressTrace);
        cbxMatrimonialVerifications = findViewById(R.id.cbxMatrimonialVerifications);
        cbxDraftingComplaints = findViewById(R.id.cbxDraftingComplaints);
        docList = new ArrayList<>();

        tvTitle.setText("Ex-Service Man Registration");
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
                String city = tvCity.getText().toString();
                if(TextUtils.isEmpty(city)){
                    showToast("Please select city first");
                }
                else{
                    List<String> areaList = DataManager.getList(AppConstents.TYPE_AREA);
                    customDialog = new CustomDialog(ExServiceManRegistrationActivity.this, areaList,
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
            }
        });

        tvCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String state = tvState.getText().toString();
                if(TextUtils.isEmpty(state)){
                    showToast("Please select state first");
                }
                else {
                    List<String> areaList = DataManager.getList(AppConstents.TYPE_CITY);
                    customDialog = new CustomDialog(ExServiceManRegistrationActivity.this, areaList,
                            "Select City",true,
                            new CustomDialog.NameSelectedListener() {
                                @Override
                                public void onNameSelected(String listName) {
                                    tvCity.setText(listName);
                                    customDialog.dismiss();
                                }
                            });
                    customDialog.show();
                }

            }
        });

        tvState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> areaList = DataManager.getList(AppConstents.TYPE_STATE);
                customDialog = new CustomDialog(ExServiceManRegistrationActivity.this, areaList,
                        "Select State",true,
                        new CustomDialog.NameSelectedListener() {
                            @Override
                            public void onNameSelected(String listName) {
                                tvState.setText(listName);
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

        btnUploadDocs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        llRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerExServiceMan();
            }
        });
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_CAPTURE);
    }


    private void registerExServiceMan() {
        try {
            String firstName = edtFirstName.getText().toString();
            String lastName = edtLastName.getText().toString();
            String exPoliceId = edtExPoliceId.getText().toString();
            String mobileNo = edtMobileNumber.getText().toString();
            String email = edtEmail.getText().toString();
            String password = edtPassword.getText().toString();
            String state = tvState.getText().toString();
            String city = tvCity.getText().toString();
            String area = tvArea.getText().toString();
            StringBuilder strDoc = new StringBuilder();
            StringBuilder strServices = new StringBuilder();
            if (cbxPolicePermissions.isChecked()
                    || cbxDraftingComplaints.isChecked()
                    || cbxMatrimonialVerifications.isChecked()
                    || cbxPIdAddressTrace.isChecked()) {
                isServiceSelectd = true;

                if (cbxPolicePermissions.isChecked()) {
                    strServices.append(cbxPolicePermissions.getText().toString()).append(",");
                }

                if (cbxDraftingComplaints.isChecked()) {
                    strServices.append(cbxDraftingComplaints.getText().toString()).append(",");
                }

                if (cbxMatrimonialVerifications.isChecked()) {
                    strServices.append(cbxMatrimonialVerifications.getText().toString()).append(",");
                }

                if (cbxPIdAddressTrace.isChecked()) {
                    strServices.append(cbxPIdAddressTrace.getText().toString()).append(",");
                }
            }
            if (validateData(firstName, lastName, exPoliceId, mobileNo, email, password, state, city, area)) {
                ArrayList<ExServiceMan> arrayList = new ArrayList<>();
                ExServiceMan exServiceMan = new ExServiceMan();
                exServiceMan.firstName = firstName;
                exServiceMan.lastName = lastName;
                exServiceMan.email = email;
                exServiceMan.password = password;
                exServiceMan.mobileNo = mobileNo;
                exServiceMan.state = state;
                exServiceMan.city = city;
                exServiceMan.area = area;
                exServiceMan.userImg = userImg;
                exServiceMan.status = 0;
                if (docList != null && docList.size() > 0) {
                    for (String str : docList) {
                        strDoc.append(str).append(",");
                    }
                }
                exServiceMan.reqDocs = strDoc.toString();
                exServiceMan.services = strServices.toString();
                arrayList.add(exServiceMan);
                new ExServiceManRegistrationAsyncTask().execute(arrayList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    class ExServiceManRegistrationAsyncTask extends AsyncTask<ArrayList<ExServiceMan>, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Boolean doInBackground(ArrayList<ExServiceMan>[] arrayLists) {
            DatabaseHandler.getInstance(getApplicationContext()).exServiceManDao().insertAll(arrayLists[0]);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            DialogUtils.showDialog(ExServiceManRegistrationActivity.this, "Service Man Registered Successfully", AppConstents.FINISH, false);
        }
    }

    private boolean validateData(String firstName, String lastName, String exPoliceId, String mobNo,
                                 String email, String password, String state, String city, String area) {
        boolean isValid = true;

        if (TextUtils.isEmpty(firstName)) {
            showToast("Please enter first name");
            isValid = false;
        } else if (TextUtils.isEmpty(lastName)) {
            showToast("Please enter last name");
            isValid = false;
        } else if (TextUtils.isEmpty(exPoliceId)) {
            showToast("Please enter Ex. Police Id");
            isValid = false;
        } else if (TextUtils.isEmpty(mobNo)) {
            showToast("Please enter mobile number");
            isValid = false;
        } else if (mobNo.length() != 10) {
            showToast("Mobile number should be 10 digits");
            isValid = false;
        } else if (TextUtils.isEmpty(email)) {
            showToast("Please enter email");
            isValid = false;
        } else if (!(Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
            showToast("Please enter valid email id");
            isValid = false;
        } else if (TextUtils.isEmpty(password)) {
            showToast("Please enter password");
            isValid = false;
        } else if (TextUtils.isEmpty(state)) {
            showToast("Please select state");
            isValid = false;
        } else if (TextUtils.isEmpty(city)) {
            showToast("Please select city");
            isValid = false;
        } else if (TextUtils.isEmpty(area)) {
            showToast("Please select area");
            isValid = false;
        } else if (!isServiceSelectd) {
            showToast("Please select atleast one service");
            isValid = false;
        }
        return isValid;
    }


    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Choose File to Upload.."), PICK_FILE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_FILE_REQUEST&&data!=null) {
                Uri selectedFileUri = data.getData();
                selectedFilePath = FilePathUtils.getFilePathByUriString(this, selectedFileUri);
                Log.i(TAG, "Selected File Path:" + selectedFilePath);

                if (!TextUtils.isEmpty(selectedFilePath)) {
                    final View view = LayoutInflater.from(ExServiceManRegistrationActivity.this)
                            .inflate(R.layout.layout_documents, null);
                    final TextView tvUploadFineName = view.findViewById(R.id.tvUploadedFile);
                    ImageView ivRemove = view.findViewById(R.id.ivRemove);
                    tvUploadFineName.setText(selectedFilePath);
                    docList.add(selectedFilePath);
                    llDocuments.addView(view);
                    ivRemove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String text = tvUploadFineName.getText().toString();
                            llDocuments.removeView(view);
                            docList.remove(text);
                        }
                    });
                } else {
                    Toast.makeText(this, "Cannot upload file to server", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == CAMERA_CAPTURE) {
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
}
