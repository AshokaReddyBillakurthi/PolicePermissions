package com.techouts.pcomplaints;

import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.techouts.pcomplaints.datahandler.DatabaseHandler;
import com.techouts.pcomplaints.utils.ApiServiceConstants;
import com.techouts.pcomplaints.utils.AppConstents;
import com.techouts.pcomplaints.utils.OkHttpUtils;
import com.techouts.pcomplaints.utils.SharedPreferenceUtils;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends BaseActivity {

    private TextView tvLoginType;
    private EditText edtEmail,edtPassword;
    private Button btnLogin,btnRegister;
    private String loginType = "";
    private String userType = "";
    private TextView tvSkipLogin;

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
                initiatePopupWindow(v);
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
                }
                else{
                    showToast("Please select login type as either service man or customer");
                }
            }
        });

        tvSkipLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                intent.putExtra(AppConstents.EXTRA_LOGIN_TYPE, AppConstents.LOGIN_TYPE_NONE);
                SharedPreferenceUtils.putBooleanValue(SharedPreferenceUtils.IS_LOGGEDIN,false);
                startActivity(intent);
            }
        });
    }


    private void validateLogin(String email,String password,String loginType){
        try{
            if(isValidData(email,password,loginType)){
                if(email.equalsIgnoreCase("admin@gmail.com") &&password.equalsIgnoreCase("password")){
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    SharedPreferenceUtils.putBooleanValue(SharedPreferenceUtils.IS_LOGGEDIN,true);
                    intent.putExtra(AppConstents.EXTRA_LOGIN_TYPE, loginType);
                    startActivity(intent);
                    finish();
                }
                else {
                    String args[] = new String[3];
                    args[0] = email;
                    args[1] = password;
                    args[2] = userType;
                    new LoginAsyncTask().execute(args);
                }
                checkLogin(email,password);
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
            int count = 0;
            if(userType.equalsIgnoreCase(AppConstents.USER_TYPE_SERVICEMAN)){
                count = DatabaseHandler.getInstance(getApplicationContext()).exServiceManDao()
                        .findExServiceManByEmailAndPassword(strings[0],strings[1]);
            }
            else{
                count = DatabaseHandler.getInstance(getApplicationContext()).userDao()
                        .findUserByEmailAndPassword(strings[0],strings[1]);
            }
            if(count>0)
                return true;
            else
                return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean){
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                intent.putExtra(AppConstents.EXTRA_LOGIN_TYPE, loginType);
                SharedPreferenceUtils.putBooleanValue(SharedPreferenceUtils.IS_LOGGEDIN,true);
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

    private void initiatePopupWindow(View v) {
        try {
            final PopupWindow popupWindow = new PopupWindow(this);
            popupWindow.setFocusable(true);
            View view = LayoutInflater.from(LoginActivity.this).inflate(R.layout.popup_window_logintype,null);
            final TextView tvAdminLogin = view.findViewById(R.id.tvAdminLogin);
            final TextView tvServiceManLogin = view.findViewById(R.id.tvServiceManLogin);
            final TextView tvCustomerLogin = view.findViewById(R.id.tvCustomerLogin);
            tvAdminLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loginType = tvAdminLogin.getText().toString();
                    btnRegister.setVisibility(View.GONE);
                    userType = AppConstents.USER_TYPE_ADMIN;
                    tvLoginType.setText(loginType+"");
                    popupWindow.dismiss();
                }
            });
            tvServiceManLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loginType = tvServiceManLogin.getText().toString();
                    userType = AppConstents.USER_TYPE_SERVICEMAN;
                    btnRegister.setVisibility(View.VISIBLE);
                    tvLoginType.setText(loginType+"");
                    popupWindow.dismiss();
                }
            });
            tvCustomerLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loginType = tvCustomerLogin.getText().toString();
                    userType = AppConstents.USER_TYPE_CUSTOMER;
                    btnRegister.setVisibility(View.VISIBLE);
                    tvLoginType.setText(loginType+"");
                    popupWindow.dismiss();
                }
            });
            popupWindow.setContentView(view);
            popupWindow.showAsDropDown(v, Gravity.CENTER,0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                                boolean status = jsonObject.getBoolean("status");
                                if(status) {
                                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                                }
                                else {
                                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                                }
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
