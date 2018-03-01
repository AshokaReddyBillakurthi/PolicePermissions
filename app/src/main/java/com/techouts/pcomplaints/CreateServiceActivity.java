package com.techouts.pcomplaints;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.techouts.pcomplaints.R;

public class CreateServiceActivity extends BaseActivity {

    private TextView tvTitle;
    private ImageView ivBack;

    @Override
    public int getRootLayout() {
        return R.layout.activity_create_service;
    }

    @Override
    public void initGUI() {
        tvTitle = findViewById(R.id.tvTitle);
        ivBack = findViewById(R.id.ivBack);
        tvTitle.setText("Create Service");

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initData() {

    }
}
