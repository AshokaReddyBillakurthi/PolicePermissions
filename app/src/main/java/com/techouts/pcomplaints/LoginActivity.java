package com.techouts.pcomplaints;

import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.techouts.pcomplaints.custom.CustomDialog;
import com.techouts.pcomplaints.database.UserDataHelper;
import com.techouts.pcomplaints.database.XServiceManDataHelper;
import com.techouts.pcomplaints.utils.ApiServiceConstants;
import com.techouts.pcomplaints.utils.AppConstents;
import com.techouts.pcomplaints.utils.DataManager;
import com.techouts.pcomplaints.utils.OkHttpUtils;
import com.techouts.pcomplaints.utils.SharedPreferenceUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends BaseActivity {

    private TextView tvLoginType;
    private EditText edtEmail,edtPassword;
    private Button btnLogin,btnRegister;
    private String userType = "";
    private TextView tvSkipLogin;
    private CustomDialog customDialog;

    @Override
    public int getRootLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initGUI() {
        tvLoginType = findViewById(R.id.tvLoginType);
        tvSkipLogin = findViewById(R.id.tvSkipLogin);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        tvLoginType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginTypeDialog();
            }
        });
    }

    @Override
    public void initData() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                validateLogin(email,password,loginType);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(userType)){
                    showLoginTypeDialog();
                }
                else{
                    if(!TextUtils.isEmpty(userType)&&!(userType.equals(AppConstents.USER_TYPE_ADMIN))){
                        if(userType.equalsIgnoreCase(AppConstents.USER_TYPE_SERVICEMAN)){
                            Intent intent = new Intent(LoginActivity.this,ExServiceManRegistrationActivity.class);
                            intent.putExtra(AppConstents.EXTRA_USER_TYPE,userType);
                            startActivity(intent);
                        }
                        else if(userType.equalsIgnoreCase(AppConstents.USER_TYPE_CUSTOMER)){
                            Intent intent = new Intent(LoginActivity.this,UserRegistrationActivity.class);
                            intent.putExtra(AppConstents.EXTRA_USER_TYPE,userType);
                            startActivity(intent);
                        }
                    } else{
                        showToast("Please select login type as either service man or customer");
                    }
                }
            }
        });

        tvSkipLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                intent.putExtra(AppConstents.EXTRA_LOGIN_TYPE, AppConstents.LOGIN_TYPE_NONE);
                SharedPreferenceUtils.putBooleanValue(AppConstents.IS_LOGGEDIN,false);
                startActivity(intent);
            }
        });
    }

    private void showLoginTypeDialog(){
        try{
            List<String> userLoginTypes = DataManager.getUserLoginTypes();
            customDialog = new CustomDialog(LoginActivity.this, userLoginTypes,
                    "Select User Type",false,false,
                    new CustomDialog.NameSelectedListener() {
                        @Override
                        public void onNameSelected(String listName) {
                            if(listName.equalsIgnoreCase(AppConstents.LOGIN_ADMIN)){
                                btnRegister.setVisibility(View.GONE);
                                userType = AppConstents.USER_TYPE_ADMIN;
                                edtEmail.setText("");
                                edtPassword.setText("");
                            }
                            else if(listName.equalsIgnoreCase(AppConstents.LOGIN_SERVICE_MAN)){
                                userType = AppConstents.USER_TYPE_SERVICEMAN;
                                btnRegister.setVisibility(View.VISIBLE);
                                edtEmail.setText("");
                                edtPassword.setText("");
                            }
                            else if(listName.equalsIgnoreCase(AppConstents.LOGIN_CUSTOMER)){
                                userType = AppConstents.USER_TYPE_CUSTOMER;
                                btnRegister.setVisibility(View.VISIBLE);
                                edtEmail.setText("");
                                edtPassword.setText("");
                            }
                            loginType = listName;
                            tvLoginType.setText(listName);
                            customDialog.dismiss();
                        }
                    });
            customDialog.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    private void validateLogin(String email,String password,String loginType){
        try{
            if(isValidData(email,password,loginType)){
                if(email.equalsIgnoreCase("admin@gmail.com") && password.equalsIgnoreCase("password")){
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    SharedPreferenceUtils.putStaringValue(AppConstents.EMAIL_ID,"admin@gmail.com");
                    SharedPreferenceUtils.putStaringValue(AppConstents.PASSWORD,"password");
                    SharedPreferenceUtils.putStaringValue(AppConstents.USERTYPE,userType);
                    SharedPreferenceUtils.putStaringValue(AppConstents.LOGIN_TYPE,loginType);
                    SharedPreferenceUtils.putBooleanValue(AppConstents.IS_LOGGEDIN,true);
                    intent.putExtra(AppConstents.EXTRA_LOGIN_TYPE, loginType);
                    startActivity(intent);
                    finish();
                }
                else {
                    String args[] = new String[3];
                    args[0] = email;
                    args[1] = password;
                    args[2] = userType;
                    SharedPreferenceUtils.putStaringValue(AppConstents.EMAIL_ID,email);
                    SharedPreferenceUtils.putStaringValue(AppConstents.PASSWORD,password);
                    SharedPreferenceUtils.putStaringValue(AppConstents.USERTYPE,userType);
                    SharedPreferenceUtils.putStaringValue(AppConstents.LOGIN_TYPE,loginType);
                    new LoginAsyncTask().execute(args);
                }
            }
            else{
                showToast("Please enter proper details");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    class LoginAsyncTask extends AsyncTask<String,Void,Boolean>{

        @Override
        protected Boolean doInBackground(String... strings) {
            boolean isValidUser = false;
            if(userType.equalsIgnoreCase(AppConstents.USER_TYPE_SERVICEMAN)){
                isValidUser = XServiceManDataHelper.isValidXServiceMan(LoginActivity.this,strings[0],strings[1]);
            }
            else{
                isValidUser = UserDataHelper.isValidUser(LoginActivity.this,strings[0],strings[1]);
            }
            return  isValidUser;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean){
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                intent.putExtra(AppConstents.EXTRA_LOGIN_TYPE, SharedPreferenceUtils.getStringValue(AppConstents.LOGIN_TYPE));
                SharedPreferenceUtils.putBooleanValue(AppConstents.IS_LOGGEDIN,true);
                startActivity(intent);
                finish();
            }
            else{
                showToast("Please enter proper details");
            }
        }
    }

    private  boolean isValidData(String email,String password,String loginType){
        boolean isValid = true;
        if(TextUtils.isEmpty(email)){
            showToast("Please enter emild id");
            isValid = false;
        }
        else if(!(Patterns.EMAIL_ADDRESS.matcher(email).matches())){
            showToast("Please enter valid email id");
            isValid = false;
        }
        else if(TextUtils.isEmpty(password)){
            showToast("Please enter password");
            isValid = false;
        }
        else if(TextUtils.isEmpty(loginType)){
            showToast("Please select login type");
            isValid = false;
        }
        return isValid;
    }

    private void checkLogin(String email,String password){
        try{
            OkHttpClient client = OkHttpUtils.getOkHttpClient();
            Request.Builder builder = new Request.Builder();
            builder.url(ApiServiceConstants.MAIN_URL+ApiServiceConstants.USER_LOGIN+"email="+email+"&password="+password);
            builder.get();
            Request request = builder.build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this,getString(R.string.error_message),Toast.LENGTH_LONG).show();
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
                                JSONObject jsonObject = new JSONObject(body);
                                String message = jsonObject.getString("msg");
//                                boolean status = jsonObject.getBoolean("status");
                                showToast(message);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
