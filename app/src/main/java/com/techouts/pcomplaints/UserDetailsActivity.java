package com.techouts.pcomplaints;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.techouts.pcomplaints.database.UserDataHelper;
import com.techouts.pcomplaints.model.User;
import com.techouts.pcomplaints.utils.AppConstents;

public class UserDetailsActivity extends BaseActivity {
    private TextView tvTitle, tvFirstName, tvLastName, tvArea,
            tvCity, tvState,tvEmail,tvMobileNo;
    private ImageView ivBack, ivUserImage;
    private User user;
    private String email;

    @Override
    public int getRootLayout() {
        return R.layout.activity_user_details;
    }

    @Override
    public void initGUI() {
        if (getIntent().getExtras() != null) {
            if(getIntent().hasExtra(AppConstents.EXTRA_EMAIL_ID))
                email = getIntent().getStringExtra(AppConstents.EXTRA_EMAIL_ID);
            if(getIntent().hasExtra(AppConstents.EXTRA_USER))
                user = (User) getIntent().getExtras().getSerializable(AppConstents.EXTRA_USER);
        }
        tvTitle = findViewById(R.id.tvTitle);
        tvFirstName = findViewById(R.id.tvFirstName);
        tvLastName = findViewById(R.id.tvLastName);
        tvEmail = findViewById(R.id.tvEmail);
        tvMobileNo = findViewById(R.id.tvMobileNo);
        tvArea = findViewById(R.id.tvListName);
        tvCity = findViewById(R.id.tvCity);
        tvState = findViewById(R.id.tvState);
        ivBack = findViewById(R.id.ivBack);
        ivUserImage = findViewById(R.id.ivUserImage);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initData() {
        if (user != null) {
            setUserData(user);
        }
        else if(!TextUtils.isEmpty(email)){
            new GetUserDetailsTask().execute(email);
        }
    }


    class GetUserDetailsTask extends AsyncTask<String, Void, User> {

        @Override
        protected User doInBackground(String... strings) {
            user = UserDataHelper.getUserByEmailId(UserDetailsActivity.this,strings[0]);
//            user = DatabaseHandler.getInstance(getApplicationContext()).userDao().getUserDetailsByEmailId(strings[0]);
            return user;
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            setUserData(user);
        }
    }

    private void setUserData(User user){
        if (null!=user) {
            tvTitle.setText(user.firstName+" "+user.lastName);
            tvFirstName.setText(user.firstName+ "");
            tvLastName.setText(user.lastName + "");
            tvEmail.setText(user.email+"");
            tvMobileNo.setText(user.mobileNo+"");
            tvArea.setText(user.area + "");
            tvCity.setText(user.city + "");
            tvState.setText(user.state + "");
            Bitmap bitmap = getUserImageBitMap(user.userImg);
            if(bitmap!=null)
                ivUserImage.setImageBitmap(bitmap);
        }
    }
}
