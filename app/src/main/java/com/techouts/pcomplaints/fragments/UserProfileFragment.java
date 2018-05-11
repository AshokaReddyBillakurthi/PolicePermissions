package com.techouts.pcomplaints.fragments;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techouts.pcomplaints.BaseActivity;
import com.techouts.pcomplaints.R;
import com.techouts.pcomplaints.database.UserDataHelper;
import com.techouts.pcomplaints.model.User;
import com.techouts.pcomplaints.utils.AppConstents;
import com.techouts.pcomplaints.utils.SharedPreferenceUtils;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileFragment extends BaseFragment {

    private TextView  tvFirstName, tvLastName, tvArea,
            tvCity, tvState,tvEmail,tvMobileNo;
    private CircleImageView ivUserImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_userprofile,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvFirstName = view.findViewById(R.id.tvFirstName);
        tvLastName = view.findViewById(R.id.tvLastName);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvMobileNo = view.findViewById(R.id.tvMobileNo);
        tvArea = view.findViewById(R.id.tvListName);
        tvCity = view.findViewById(R.id.tvCity);
        tvState = view.findViewById(R.id.tvState);
        ivUserImage = view.findViewById(R.id.ivUserImage);

        new GetUserDetailsTask().execute();
    }

    class GetUserDetailsTask extends AsyncTask<String, Void, User> {

        @Override
        protected User doInBackground(String... strings) {
           return UserDataHelper.getUserByEmailId(getContext(), SharedPreferenceUtils.getStringValue(AppConstents.EMAIL_ID));
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            setUserData(user);
        }
    }

    private void setUserData(User user){
        if (null!=user) {
            tvFirstName.setText(user.firstName+ "");
            tvLastName.setText(user.lastName + "");
            tvEmail.setText(user.email+"");
            tvMobileNo.setText(user.mobileNo+"");
            tvArea.setText(user.circlePolicestation + "");
            tvCity.setText(user.subDivision + "");
            tvState.setText(user.state + "");
            Bitmap bitmap = ((BaseActivity)getContext()).getUserImageBitMap(user.userImg);
            if(null!=bitmap)
                ivUserImage.setImageBitmap(bitmap);
        }
    }
}
