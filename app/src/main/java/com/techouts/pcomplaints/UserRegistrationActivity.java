package com.techouts.pcomplaints;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.techouts.pcomplaints.custom.CustomDialog;
import com.techouts.pcomplaints.datahandler.DatabaseHandler;
import com.techouts.pcomplaints.entities.Area;
import com.techouts.pcomplaints.entities.City;
import com.techouts.pcomplaints.entities.State;
import com.techouts.pcomplaints.entities.User;
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

    private TextView tvTitle, tvState, tvCity, tvArea;
    private ImageView ivBack;
    private EditText edtFirstName, edtLastName, edtMobileNumber,
            edtEmail, edtPassword;
    private LinearLayout llRegister;
    private ImageView ivCamera, ivUserImg;
    private static final int CAMERA_CAPTURE = 1;
    public static final String TAG = UserRegistrationActivity.class.getSimpleName();
    private String userType = "";
    private boolean isPosted = false;
    private CustomDialog customDialog;

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
        ivBack = findViewById(R.id.ivBack);
        llRegister = findViewById(R.id.llRegister);
        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        edtMobileNumber = findViewById(R.id.edtMobileNumber);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        tvState = findViewById(R.id.tvState);
        tvCity = findViewById(R.id.tvCity);
        tvArea = findViewById(R.id.tvArea);
        ivCamera = findViewById(R.id.ivCamera);
        ivUserImg = findViewById(R.id.ivUserImg);

        if (userType.equalsIgnoreCase(AppConstents.USER_TYPE_CUSTOMER)) {
            tvTitle.setText("Customer Registration");
        }

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

        tvArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = tvCity.getText().toString();
                if(TextUtils.isEmpty(city)){
                    showToast("Please select city first");
                }
                else{
                    List<Area> areaList = DataManager.getAreaList();
                    customDialog = new CustomDialog(UserRegistrationActivity.this, areaList,
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
                    List<City> cityList = DataManager.getCityList();
                    customDialog = new CustomDialog(UserRegistrationActivity.this,
                            cityList,true,"Select City",true,false,
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
                List<State> stateList = DataManager.getStateList();
                customDialog = new CustomDialog(UserRegistrationActivity.this,true, stateList ,
                        "Select State",true,false,
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
            String city = tvCity.getText().toString().trim();
            String area = tvArea.getText().toString().trim();
            if (validateData(firstName, lastName, email, password,
                    mobileNo, state, city, area)) {
                ArrayList<User> arrayList = new ArrayList<>();
                User user = new User();
                user.firstName = firstName;
                user.lastName = lastName;
                user.email = email;
                user.password = password;
                user.mobileNo = mobileNo;
                user.state = state;
                user.city = city;
                user.area = area;
                user.userImg = userImg;
                user.userType = userType;
//                if(postDataToServer(user)){
//                    arrayList.add(user);
//                    new UserAsyncTask().execute(arrayList);
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
            jsonObject.put("exPoliceId", "");
            jsonObject.put("email", user.email);
            jsonObject.put("password", user.password);
            jsonObject.put("mobile", user.mobileNo);
            jsonObject.put("state", user.state);
            jsonObject.put("city", user.city);
            jsonObject.put("area", user.area);
            jsonObject.put("userImg", user.userImg);
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
            DatabaseHandler.getInstance(getApplicationContext()).userDao().insertAll(arrayLists[0]);
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
                                 String state, String city, String area) {
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
            showToast("Please enter state name");
        } else if (TextUtils.isEmpty(city)) {
            isValid = false;
            showToast("Please enter city");
        } else if (TextUtils.isEmpty(area)) {
            isValid = false;
            showToast("Please enter area");
        } else if (TextUtils.isEmpty(userImg)) {
            isValid = false;
            showToast("Please capture Image");
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
