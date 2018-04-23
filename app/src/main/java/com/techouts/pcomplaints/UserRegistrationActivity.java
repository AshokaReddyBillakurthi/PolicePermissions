package com.techouts.pcomplaints;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.techouts.pcomplaints.custom.CustomDialog;
import com.techouts.pcomplaints.database.AppDataHelper;
import com.techouts.pcomplaints.database.UserDataHelper;
import com.techouts.pcomplaints.model.Area;
import com.techouts.pcomplaints.model.City;
import com.techouts.pcomplaints.model.District;
import com.techouts.pcomplaints.model.DivisionPoliceStation;
import com.techouts.pcomplaints.model.State;
import com.techouts.pcomplaints.model.SubDivision;
import com.techouts.pcomplaints.model.User;
import com.techouts.pcomplaints.utils.ApiServiceConstants;
import com.techouts.pcomplaints.utils.AppConstents;
import com.techouts.pcomplaints.utils.DataManager;
import com.techouts.pcomplaints.utils.DialogUtils;
import com.techouts.pcomplaints.utils.OkHttpUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserRegistrationActivity extends BaseActivity {

    private TextView tvTitle, tvState, tvTermsandConditions,
             tvDistrict, tvSubDivisions, tvDivisionPoliceStation;
    private ImageView ivBack;
    private EditText edtFirstName, edtLastName, edtMobileNumber,
            edtEmail, edtPassword;
    private CheckBox cbxTermsAndConditions;
    private LinearLayout llRegister;
    private ImageView ivCamera, ivUserImg;
    private static final int CAMERA_CAPTURE = 1;
    public static final String TAG = UserRegistrationActivity.class.getSimpleName();
    private String userType = "";
    private boolean isPosted = false;
    private CustomDialog customDialog;
    private List<DivisionPoliceStation> divisionPoliceStationList;
    private List<SubDivision> subDivisionList;
    private List<District> districtList;
    private List<State> stateList;
    private String stateCode = "";
    private String districtCode = "";
    private String subDivisionCode = "";
    private String divisionPoliceStationCode = "";

    @Override
    public int getRootLayout() {
        return R.layout.activity_user_registration;
    }

    @Override
    public void initGUI() {

        if (getIntent().getExtras() != null) {
            userType = getIntent().getExtras().getString(AppConstents.EXTRA_USER_TYPE);
        }
        tvTitle = findViewById(R.id.tvTitle);
        tvTermsandConditions = findViewById(R.id.tvTermsandConditions);
        ivBack = findViewById(R.id.ivBack);
        llRegister = findViewById(R.id.llRegister);
        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        edtMobileNumber = findViewById(R.id.edtMobileNumber);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        tvState = findViewById(R.id.tvState);
        tvDistrict = findViewById(R.id.tvDistrict);
        tvSubDivisions = findViewById(R.id.tvSubDivisions);
        tvDivisionPoliceStation = findViewById(R.id.tvDivisionPoliceStation);
        ivCamera = findViewById(R.id.ivCamera);
        ivUserImg = findViewById(R.id.ivUserImg);
        cbxTermsAndConditions = findViewById(R.id.cbxTermsAndConditions);

        if (userType.equalsIgnoreCase(AppConstents.USER_TYPE_CUSTOMER)) {
            tvTitle.setText("Customer Registration");
        }

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvTermsandConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserRegistrationActivity.this,
                        TermsAndConditionsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {

        llRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createServiceMan();
            }
        });

        tvDivisionPoliceStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = tvSubDivisions.getText().toString();
                if(TextUtils.isEmpty(city)){
                    showToast("Please select sub division first");
                }
                else{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            divisionPoliceStationList =  AppDataHelper.
                                    getAllDivisionPoliceStationByDistrictCode(UserRegistrationActivity.this,
                                            subDivisionCode);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    customDialog = new CustomDialog(UserRegistrationActivity.this,
                                            divisionPoliceStationList,"Select Sub Division",
                                            true, false, true,
                                            new CustomDialog.OnDivisionPoliceStation() {
                                                @Override
                                                public void onDivisionPoliceStation(DivisionPoliceStation divisionPoliceStation) {
                                                    if(null!=divisionPoliceStation){
                                                        tvDivisionPoliceStation.setText(divisionPoliceStation.divisionPoliceStationName+"");
                                                        divisionPoliceStationCode = divisionPoliceStation.divisionPoliceStationCode;
                                                    }
                                                    customDialog.dismiss();
                                                }
                                            });
                                    customDialog.show();
                                }
                            });
                        }
                    }).start();
                }
            }
        });

        tvSubDivisions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = tvDistrict.getText().toString();
                if(TextUtils.isEmpty(city)){
                    showToast("Please select District first");
                }
                else{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            subDivisionList = AppDataHelper.
                                    getAllSubDivisionsByDistrictCode(UserRegistrationActivity.this,districtCode);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(null!=subDivisionList&&!subDivisionList.isEmpty()){
                                        customDialog = new CustomDialog(UserRegistrationActivity.this,
                                                subDivisionList, true,"Select Sub Division",
                                                false, false,
                                                new CustomDialog.OnSubDivisionSelected() {
                                                    @Override
                                                    public void OnSubDivisionSelected(SubDivision subDivision) {
                                                        if(null!=subDivision){
                                                            tvSubDivisions.setText(subDivision.subDivisionName+"");
                                                            subDivisionCode = subDivision.subDivisionCode;
                                                        }
                                                        customDialog.dismiss();
                                                    }
                                                });
                                        customDialog.show();
                                    }
                                }
                            });
                        }
                    }).start();
                }
            }
        });

        tvDistrict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String state = tvState.getText().toString();
                if(TextUtils.isEmpty(state)){
                    showToast("Please select state first");
                }
                else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            districtList = AppDataHelper.getAllDistrictByStateCode(UserRegistrationActivity.this,
                                    stateCode);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(null!=districtList&&!districtList.isEmpty()){
                                        customDialog = new CustomDialog(UserRegistrationActivity.this,
                                                districtList, "Select District", true,
                                                false, true,
                                                new CustomDialog.OnDistrictSelected() {
                                                    @Override
                                                    public void onDistrictSelected(District district) {
                                                        if(null!=district){
                                                            tvDistrict.setText(district.districtName+"");
                                                            districtCode = district.districtCode;
                                                        }
                                                        customDialog.dismiss();
                                                    }
                                                });
                                        customDialog.show();
                                    }
                                }
                            });
                        }
                    }).start();
                }

            }
        });

        tvState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        stateList = AppDataHelper.getAllStates(UserRegistrationActivity.this);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(null!=stateList&&!stateList.isEmpty()){
                                    customDialog = new CustomDialog(UserRegistrationActivity.this,
                                            true, stateList ,"Select State",
                                            true,false,
                                            new CustomDialog.OnStateSelected() {
                                                @Override
                                                public void onStateSelected(State state) {
                                                    if(null!=state){
                                                        stateCode = state.stateCode;
                                                        tvState.setText(state.stateName+"");
                                                    }
                                                    customDialog.dismiss();
                                                }
                                            });
                                    customDialog.show();
                                }
                            }
                        });
                    }
                }).start();
            }
        });

        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });

    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_CAPTURE);
    }

    private void createServiceMan() {
        try {
            String firstName = edtFirstName.getText().toString().trim();
            String lastName = edtLastName.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();
            String mobileNo = edtMobileNumber.getText().toString().trim();
            String state = tvState.getText().toString().trim();
            String district = tvDistrict.getText().toString().trim();
            String subDivision = tvSubDivisions.getText().toString().trim();
            String divisionPoliceStation = tvDivisionPoliceStation.getText().toString().trim();
            if (validateData(firstName, lastName, email, password,
                    mobileNo, state, district, subDivision,divisionPoliceStation)) {
                ArrayList<User> arrayList = new ArrayList<>();
                User user = new User();
                user.firstName = firstName;
                user.lastName = lastName;
                user.email = email;
                user.password = password;
                user.mobileNo = mobileNo;
                user.state = state;
                user.city = "";
                user.area = "";
                user.userImg = userImg;
                user.userType = userType;
                user.district = district;
                user.subDivision = subDivision;
                user.circlePolicestation = divisionPoliceStation;
//                if(postDataToServer(user)){
////                    arrayList.add(user);
////                    new UserAsyncTask().execute(arrayList);
//                    showToast("Data posted Successfully");
//                }
//                else{
//                    showToast("Failed");
//                }
                arrayList.add(user);
                new UserAsyncTask().execute(arrayList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean postDataToServer(User user) {
        try {
            OkHttpClient client = OkHttpUtils.getOkHttpClient();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("firstName", user.firstName);
            jsonObject.put("lastName", user.lastName);
            jsonObject.put("email", user.email);
            jsonObject.put("password", user.password);
            jsonObject.put("mobileNumber", user.mobileNo);
            jsonObject.put("isActive", true);
            jsonObject.put("state", user.state);
            jsonObject.put("city", user.city);
            jsonObject.put("area", user.area);
            jsonObject.put("image", user.userImg);
            jsonObject.put("userType", user.userType);
            String body = jsonObject.toString();
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body);
            Request.Builder builder = new Request.Builder();
            builder.url(ApiServiceConstants.MAIN_URL + ApiServiceConstants.USER_REGISTRATION).addHeader("Content-Type", "application/json")
                    .addHeader("cache-control", "no-cache")
                    .post(requestBody);
            Request request = builder.build();
            client.newCall(request).enqueue(new  Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            isPosted = false;
                            Toast.makeText(UserRegistrationActivity.this, R.string.error_message, Toast.LENGTH_LONG).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    final String body = response.body().string().toString();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (response.message().equalsIgnoreCase("OK")) {
                                    isPosted = true;
//                                    JSONObject jsonObj = new JSONObject(body);
//                                    String message = jsonObj.getString("msg");
//                                    DialogUtils.showDialog(UserRegistrationActivity.this,message.toString(),AppConstents.FINISH,false);
//                                    Toast.makeText(UserRegistrationActivity.this,message.toString(),Toast.LENGTH_LONG).show();
                                } else {
                                    isPosted = false;
//                                    Toast.makeText(UserRegistrationActivity.this,R.string.error_message,Toast.LENGTH_LONG).show();
                                    DialogUtils.showDialog(UserRegistrationActivity.this, getResources().getString(R.string.error_message), AppConstents.FINISH, false);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean isValid = isPosted;
        return isValid;
    }


    class UserAsyncTask extends AsyncTask<ArrayList<User>, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Boolean doInBackground(ArrayList<User>[] arrayLists) {
            UserDataHelper.insertUserData(UserRegistrationActivity.this,arrayLists[0]);
//            DatabaseHandler.getInstance(getApplicationContext()).userDao().insertAll(arrayLists[0]);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            DialogUtils.showDialog(UserRegistrationActivity.this, "User Registered Successfully", AppConstents.FINISH, false);
        }
    }

    private boolean validateData(String firstName, String lastName,
                                 String email, String password, String mobileNo,
                                 String state,String district, String subDivision,
                                 String divisionPoliceStation) {
        boolean isValid = true;
        if (TextUtils.isEmpty(firstName)) {
            isValid = false;
            showToast("Please enter first name");
        } else if (TextUtils.isEmpty(lastName)) {
            isValid = false;
            showToast("Please enter last name");
        } else if (TextUtils.isEmpty(mobileNo)) {
            isValid = false;
            showToast("Please enter mobile number");
        } else if (!(mobileNo.length() == 10)) {
            isValid = false;
            showToast("Please enter 10 digit mobile number");
        } else if (TextUtils.isEmpty(email)) {
            isValid = false;
            showToast("Please enter email");
        } else if (!(Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
            isValid = false;
            showToast("Please enter valid email");
        } else if (TextUtils.isEmpty(password)) {
            isValid = false;
            showToast("Please enter password");
        } else if (TextUtils.isEmpty(state)) {
            isValid = false;
            showToast("Please select state");
        } else if (TextUtils.isEmpty(district)) {
            isValid = false;
            showToast("Please select district");
        } else if (TextUtils.isEmpty(subDivision)) {
            isValid = false;
            showToast("Please select sub division");
        } else if (TextUtils.isEmpty(divisionPoliceStation)) {
            isValid = false;
            showToast("Please select division with police station");
        } else if (TextUtils.isEmpty(userImg)) {
            isValid = false;
            showToast("Please capture Image");
        } else if(!cbxTermsAndConditions.isChecked()){
            isValid = false;
            showToast("Please accept the Terms and Conditions");
        }
        return isValid;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
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
