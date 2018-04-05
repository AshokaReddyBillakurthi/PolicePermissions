package com.techouts.pcomplaints;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TermsAndConditionsActivity extends BaseActivity {

    private TextView tvTitle;
    private ImageView ivBack;
    @Override
    public int getRootLayout() {
        return R.layout.activity_terms_and_conditions;
    }

    @Override
    public void initGUI() {
        tvTitle = findViewById(R.id.tvTitle);
        ivBack = findViewById(R.id.ivBack);

        tvTitle.setText(getString(R.string.termsandconditions));

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
