package com.techouts.pcomplaints;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.techouts.pcomplaints.entities.User;
import com.techouts.pcomplaints.utils.AppConstents;

import java.io.File;

public class UserDetailsActivity extends BaseActivity {

    private TextView tvTitle, tvFirstName, tvLastName, tvArea,tvLocation,
            tvCity, tvState,tvEmail,tvMobileNo;
    private ImageView ivBack, ivUserImage;
    private User user;

    @Override
    public int getRootLayout() {
        return R.layout.activity_user_details;
    }

    @Override
    public void initGUI() {
        if (getIntent().getExtras() != null) {
            user = (User) getIntent().getExtras().getSerializable(AppConstents.EXTRA_USER);
        }
        tvTitle = findViewById(R.id.tvTitle);
        tvFirstName = findViewById(R.id.tvFirstName);
        tvLastName = findViewById(R.id.tvLastName);
        tvEmail = findViewById(R.id.tvEmail);
        tvMobileNo = findViewById(R.id.tvMobileNo);
        tvArea = findViewById(R.id.tvArea);
        tvCity = findViewById(R.id.tvCity);
        tvLocation = findViewById(R.id.tvLocation);
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
            tvTitle.setText(user.firstName+" "+user.lastName);
            tvFirstName.setText(user.firstName+ "");
            tvLastName.setText(user.lastName + "");
            tvEmail.setText(user.email+"");
            tvMobileNo.setText(user.mobileNo+"");
            tvArea.setText(user.area + "");
            tvCity.setText(user.city + "");
            tvState.setText(user.state + "");
            tvLocation.setText(user.location+"");
            Bitmap bitmap = getUserImageBitMap(user.userImg);
            if(bitmap!=null)
                ivUserImage.setImageBitmap(bitmap);
        }
    }



}
