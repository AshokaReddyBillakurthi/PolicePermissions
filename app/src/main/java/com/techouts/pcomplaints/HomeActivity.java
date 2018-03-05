package com.techouts.pcomplaints;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techouts.pcomplaints.adapters.MenuListAdaper;
import com.techouts.pcomplaints.fragments.HomeFragment;
import com.techouts.pcomplaints.utils.AppConstents;
import com.techouts.pcomplaints.utils.DataManager;
import com.techouts.pcomplaints.utils.DialogUtils;

import java.util.List;

public class HomeActivity extends BaseActivity {

    private DrawerLayout drawer;
    private LinearLayout llMenu;
    private RecyclerView rvMenuList;
    public String loginType = "";
    private List<String> menuList;
    private TextView tvScreenTitle;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public int getRootLayout() {
        return R.layout.activity_home;
    }

    @Override
    public void initGUI() {

        if (getIntent().getExtras() != null) {
            loginType = getIntent().getExtras().getString(AppConstents.EXTRA_LOGIN_TYPE);
        }
        drawer = findViewById(R.id.drawer);
        llMenu = findViewById(R.id.llMenu);
        tvScreenTitle = findViewById(R.id.tvScreenTitle);
        rvMenuList = findViewById(R.id.rvMenuList);
        rvMenuList.setLayoutManager(new LinearLayoutManager(HomeActivity.this));

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, null, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };

        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        llMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(Gravity.LEFT);
                } else {
                    drawer.openDrawer(Gravity.LEFT);
                }
            }
        });
    }

    @Override
    public void initData() {

        if (loginType.equalsIgnoreCase(AppConstents.LOGIN_ADMIN)) {
            tvScreenTitle.setText(AppConstents.ADMIN);
            menuList = DataManager.getMenuList(AppConstents.LOGIN_ADMIN);
        } else if (loginType.equalsIgnoreCase(AppConstents.LOGIN_SERVICE_MAN)) {
            tvScreenTitle.setText(AppConstents.SERVICE_MAN);
            menuList = DataManager.getMenuList(AppConstents.LOGIN_SERVICE_MAN);
        } else if (loginType.equalsIgnoreCase(AppConstents.LOGIN_CUSTOMER)) {
            tvScreenTitle.setText(AppConstents.CUSTOMER);
            menuList = DataManager.getMenuList(AppConstents.LOGIN_CUSTOMER);
        } else if(loginType.equalsIgnoreCase(AppConstents.LOGIN_TYPE_NONE)){
            tvScreenTitle.setText(AppConstents.HOME);
            menuList = DataManager.getMenuList(AppConstents.LOGIN_TYPE_NONE);
        }

        rvMenuList.setAdapter(new MenuListAdaper(menuList));
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, new HomeFragment());
        fragmentTransaction.commit();
    }


    @Override
    public void onBackPressed() {
//        if (doubleBackToExitPressedOnce) {
//            super.onBackPressed();
//            return;
//        }
//        this.doubleBackToExitPressedOnce = true;
//        showToast("Please click BACK again to exit");
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce = false;
//            }
//        }, 2000);

        if(!(loginType.equalsIgnoreCase(AppConstents.LOGIN_TYPE_NONE)))
            showLogoutPopup();
        else
            super.onBackPressed();
    }

    public void openDrawer(){
        drawer.openDrawer(Gravity.LEFT);
    }

    public void closeDrawer(){
        drawer.closeDrawer(Gravity.LEFT);
    }


    public void showLogoutPopup(){
        DialogUtils.showDialog(HomeActivity.this,"Do you want to Logout?",AppConstents.LOGOUT,true);
    }
}
