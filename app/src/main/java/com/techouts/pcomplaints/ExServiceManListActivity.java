package com.techouts.pcomplaints;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.techouts.pcomplaints.adapters.ExServiceManListAdapter;
import com.techouts.pcomplaints.adapters.UserListAdpter;
import com.techouts.pcomplaints.datahandler.DatabaseHandler;
import com.techouts.pcomplaints.entities.ExServiceMan;
import com.techouts.pcomplaints.entities.User;
import com.techouts.pcomplaints.utils.AppConstents;

import java.util.ArrayList;
import java.util.List;

public class ExServiceManListActivity extends BaseActivity {

    private RecyclerView rvServiceManList;
    private ExServiceManListAdapter serviceManListAdpter;
    private TextView tvTitle;
    private ImageView ivBack, ivCross;
    private String userListType = "";
    private String userType = "";
    private EditText edtSearch;
    private List<ExServiceMan> tempExServiceManList;
    private List<ExServiceMan> exServiceManList;
    public String loginType="";

    @Override
    public int getRootLayout() {
        return R.layout.activity_ex_service_man_list;
    }

    @Override
    public void initGUI() {

        if (getIntent().getExtras() != null) {
            userListType = getIntent().getExtras().getString(AppConstents.EXTRA_USER_LIST,"");
            loginType = getIntent().getExtras().getString(AppConstents.EXTRA_LOGIN_TYPE,"");
        }

        rvServiceManList = findViewById(R.id.rvServiceManList);
        tvTitle = findViewById(R.id.tvTitle);
        ivBack = findViewById(R.id.ivBack);
        ivCross = findViewById(R.id.ivCross);
        edtSearch = findViewById(R.id.edtSearch);
        rvServiceManList.setLayoutManager(new LinearLayoutManager(ExServiceManListActivity.this));
        serviceManListAdpter = new ExServiceManListAdapter(ExServiceManListActivity.this);
        rvServiceManList.setAdapter(serviceManListAdpter);

        edtSearch.setHint("Search with area, city, first name, last name...");



        if (userListType.equalsIgnoreCase(AppConstents.SERVICE_MAN)) {
            tvTitle.setText("ServiceMan List");
            userType = AppConstents.USER_TYPE_SERVICEMAN;
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

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && s.length() > 0)
                    ivCross.setVisibility(View.VISIBLE);
                else
                    ivCross.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && s.length() >= 3) {
                    searchText(s.toString());
                } else if (s.length() == 0) {
                    ivCross.setVisibility(View.GONE);
                    searchText("");
                }
            }
        });

        ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtSearch.setText("");
                edtSearch.setHint("Search with area, city, first name, last name...");
                ivCross.setVisibility(View.GONE);
                searchText("");
            }
        });

        new GetExserviceManListTask().execute();
    }


    private void searchText(String searchText) {
        try {
            tempExServiceManList = new ArrayList<>();
            if (!TextUtils.isEmpty(searchText)) {
                for (ExServiceMan exServiceMan : tempExServiceManList) {
                    if ((exServiceMan.area.contains(searchText))
                            || (exServiceMan.city.contains(searchText))
                            || (exServiceMan.firstName.contains(searchText))
                            || (exServiceMan.lastName.contains(searchText))) {
                        tempExServiceManList.add(exServiceMan);
                    }
                }
                if (tempExServiceManList != null && tempExServiceManList.size() > 0) {
                    serviceManListAdpter.refresh(tempExServiceManList);
                }
            } else {
                serviceManListAdpter.refresh(exServiceManList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class GetExserviceManListTask extends AsyncTask<Void, Void, List<ExServiceMan>> {

        @Override
        protected List<ExServiceMan> doInBackground(Void... voids) {
            exServiceManList = DatabaseHandler.getInstance(getApplicationContext()).exServiceManDao().getAll();
            return exServiceManList;
        }

        @Override
        protected void onPostExecute(List<ExServiceMan> exServiceManList) {
            super.onPostExecute(exServiceManList);
            if (exServiceManList != null && exServiceManList.size() > 0) {
                serviceManListAdpter.refresh(exServiceManList);
            }
        }
    }
}
