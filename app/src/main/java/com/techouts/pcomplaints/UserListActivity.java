package com.techouts.pcomplaints;

import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.techouts.pcomplaints.adapters.UserListAdpter;
import com.techouts.pcomplaints.datahandler.DatabaseHandler;
import com.techouts.pcomplaints.entities.ExServiceMan;
import com.techouts.pcomplaints.entities.User;
import com.techouts.pcomplaints.utils.AppConstents;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends BaseActivity {

    private RecyclerView rvServiceManList;
    private UserListAdpter serviceManListAdpter;
    private TextView tvTitle;
    private ImageView ivBack, ivCross;
    private String userListType = "";
    private String userType = "";
    private EditText edtSearch;
    private List<User> tempUserList;
    private List<User> userList;
    private List<ExServiceMan> exServiceManList;

    @Override
    public int getRootLayout() {
        return R.layout.activity_service_man;
    }

    @Override
    public void initGUI() {

        if (getIntent().getExtras() != null) {
            userListType = getIntent().getExtras().getString(AppConstents.EXTRA_USER_LIST);
        }

        rvServiceManList = findViewById(R.id.rvServiceManList);
        tvTitle = findViewById(R.id.tvTitle);
        ivBack = findViewById(R.id.ivBack);
        ivCross = findViewById(R.id.ivCross);
        edtSearch = findViewById(R.id.edtSearch);
        rvServiceManList.setLayoutManager(new LinearLayoutManager(UserListActivity.this));
        serviceManListAdpter = new UserListAdpter(UserListActivity.this);
        rvServiceManList.setAdapter(serviceManListAdpter);

        edtSearch.setHint("Search with area, city, first name, last name...");

        if (userListType.equalsIgnoreCase(AppConstents.SERVICE_MAN)) {
            tvTitle.setText("ServiceMan List");
            userType = AppConstents.USER_TYPE_SERVICEMAN;
        } else if (userListType.equalsIgnoreCase(AppConstents.CUSTOMER)) {
            tvTitle.setText("Customer List");
            userType = AppConstents.USER_TYPE_CUSTOMER;
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

        new GetUserListTask().execute();
    }


    private void searchText(String searchText) {
        try {
            tempUserList = new ArrayList<>();
            if (!TextUtils.isEmpty(searchText)) {
                for (User user : userList) {
                    if ((user.area.contains(searchText))
                            || (user.city.contains(searchText))
                            || (user.firstName.contains(searchText))
                            || (user.lastName.contains(searchText))) {
                        tempUserList.add(user);
                    }
                }
                if (tempUserList != null && tempUserList.size() > 0) {
                    serviceManListAdpter.refresh(tempUserList);
                }
            } else {
                serviceManListAdpter.refresh(userList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class GetUserListTask extends AsyncTask<Void, Void, List<User>> {

        @Override
        protected List<User> doInBackground(Void... voids) {
            if(userListType.equalsIgnoreCase(AppConstents.SERVICE_MAN)){
                exServiceManList = DatabaseHandler.getInstance(getApplicationContext()).exServiceManDao().getAll();
            }
            else{
                userList = DatabaseHandler.getInstance(getApplicationContext()).userDao().getAll(userType);
            }

            return userList;
        }

        @Override
        protected void onPostExecute(List<User> userList) {
            super.onPostExecute(userList);
            if (userList != null && userList.size() > 0) {
                serviceManListAdpter.refresh(userList);
            }
        }
    }
}
