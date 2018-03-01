package com.techouts.pcomplaints;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.techouts.pcomplaints.adapters.AreaAdapter;
import com.techouts.pcomplaints.adapters.CityAdapter;
import com.techouts.pcomplaints.adapters.LocationAdapter;
import com.techouts.pcomplaints.adapters.StateAdapter;
import com.techouts.pcomplaints.datahandler.DatabaseHandler;
import com.techouts.pcomplaints.entities.ExServiceMan;
import com.techouts.pcomplaints.utils.AppConstents;
import com.techouts.pcomplaints.utils.DialogUtils;
import com.techouts.pcomplaints.utils.FilePathUtils;

import java.io.File;
import java.util.ArrayList;

public class ExServiceManRegistrationActivity extends BaseActivity implements AreaAdapter.OnAreaClickListener,
        CityAdapter.OnCityClickListener,StateAdapter.OnStateClickListener,LocationAdapter.OnLocationClickListener{

    private EditText edtFirstName,edtLastName,edtExPoliceId,edtMobileNumber,edtEmail,edtPassword;
    private TextView tvState,tvCity,tvArea,tvLocation,tvTitle;
    private CheckBox cbxPolicePermissions,cbxPIdAddressTrace,cbxMatrimonialVerifications,cbxDraftingComplaints;
    private Button btnUploadDocs;
    private LinearLayout llDocuments,llRegister;
    private ImageView ivCamera,ivUserImg,ivBack;
    private static final int PICK_FILE_REQUEST = 100;
    private static final int CAMERA_CAPTURE = 101;
    private static final String TAG = ExServiceManRegistrationActivity.class.getSimpleName();
    private String selectedFilePath;
    private PopupWindow popupWindow;
    private ArrayList<String> docList;
    private boolean isServiceSelectd = false;

    @Override
    public int getRootLayout() {
        return R.layout.activity_ex_service_man_registration;
    }

    @Override
    public void initGUI() {
        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        edtExPoliceId = findViewById(R.id.edtExPoliceId);
        edtMobileNumber = findViewById(R.id.edtMobileNumber);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        tvState = findViewById(R.id.tvState);
        tvCity = findViewById(R.id.tvCity);
        tvArea = findViewById(R.id.tvArea);
        tvTitle = findViewById(R.id.tvTitle);
        tvLocation = findViewById(R.id.tvLocation);
        btnUploadDocs = findViewById(R.id.btnUploadDocs);
        llDocuments = findViewById(R.id.llDocuments);
        llRegister = findViewById(R.id.llRegister);
        ivCamera = findViewById(R.id.ivCamera);
        ivUserImg = findViewById(R.id.ivUserImg);
        ivBack = findViewById(R.id.ivBack);
        cbxPolicePermissions = findViewById(R.id.cbxPolicePermissions);
        cbxPIdAddressTrace = findViewById(R.id.cbxPIdAddressTrace);
        cbxMatrimonialVerifications = findViewById(R.id.cbxMatrimonialVerifications);
        cbxDraftingComplaints = findViewById(R.id.cbxDraftingComplaints);
        docList = new ArrayList<>();

        tvTitle.setText("Ex-Service Man Registration");
    }

    @Override
    public void initData() {

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiatePopupWindowForArea(v);
            }
        });

        tvCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiatePopupWindowForCity(v);
            }
        });

        tvState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiatePopupWindowForState(v);
            }
        });
        tvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiatePopupWindowForLocation(v);
            }
        });

        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });

        btnUploadDocs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        llRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerExServiceMan();
            }
        });
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_CAPTURE);
    }


    private void registerExServiceMan(){
        try{
            String firstName = edtFirstName.getText().toString();
            String lastName = edtLastName.getText().toString();
            String exPoliceId = edtExPoliceId.getText().toString();
            String mobileNo = edtMobileNumber.getText().toString();
            String email = edtEmail.getText().toString();
            String password = edtPassword.getText().toString();
            String state = tvState.getText().toString();
            String city = tvCity.getText().toString();
            String area = tvArea.getText().toString();
            String location = tvLocation.getText().toString();
            StringBuilder strDoc = new StringBuilder();
            StringBuilder strServices = new StringBuilder();
            if(cbxPolicePermissions.isChecked()
                    || cbxDraftingComplaints.isChecked()
                    || cbxMatrimonialVerifications.isChecked()
                    || cbxPIdAddressTrace.isChecked()){
                isServiceSelectd = true;

                if(cbxPolicePermissions.isChecked()){
                    strServices.append(cbxPolicePermissions.getText().toString()).append(",");
                }

                if(cbxDraftingComplaints.isChecked()){
                    strServices.append(cbxDraftingComplaints.getText().toString()).append(",");
                }

                if(cbxMatrimonialVerifications.isChecked()){
                    strServices.append(cbxMatrimonialVerifications.getText().toString()).append(",");
                }

                if(cbxPIdAddressTrace.isChecked()){
                    strServices.append(cbxPIdAddressTrace.getText().toString()).append(",");
                }
            }
            if(validateData(firstName,lastName,exPoliceId,mobileNo,email,password,state,city,area,location)){
                ArrayList<ExServiceMan> arrayList = new ArrayList<>();
                ExServiceMan exServiceMan = new ExServiceMan();
                exServiceMan.firstName = firstName;
                exServiceMan.lastName = lastName;
                exServiceMan.email = email;
                exServiceMan.password = password;
                exServiceMan.mobileNo = mobileNo;
                exServiceMan.state = state;
                exServiceMan.city = city;
                exServiceMan.area = area;
                exServiceMan.location = location;
                exServiceMan.userImg = userImg;
                if(docList!=null&&docList.size()>0){
                    for(String str: docList){
                        strDoc.append(str).append(",");
                    }
                }
                exServiceMan.reqDocs = strDoc.toString();
                exServiceMan.services = strServices.toString();
                arrayList.add(exServiceMan);
                new ExServiceManRegistrationAsyncTask().execute(arrayList);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    class ExServiceManRegistrationAsyncTask extends AsyncTask<ArrayList<ExServiceMan>, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Boolean doInBackground(ArrayList<ExServiceMan>[] arrayLists) {
            DatabaseHandler.getInstance(getApplicationContext()).exServiceManDao().insertAll(arrayLists[0]);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            DialogUtils.showDialog(ExServiceManRegistrationActivity.this,"Service Man Registered Successfully",AppConstents.FINISH,false);
        }
    }

    private boolean validateData(String firstName,String lastName,String exPoliceId,String mobNo,
                              String email,String password,String state,String city,String area,String location){
        boolean isValid = true;

        if(TextUtils.isEmpty(firstName)){
            showToast("Please enter first name");
            isValid = false;
        }
        else if(TextUtils.isEmpty(lastName)){
            showToast("Please enter last name");
            isValid = false;
        }
        else if(TextUtils.isEmpty(exPoliceId)){
            showToast("Please enter Ex. Police Id");
            isValid = false;
        }
        else if(TextUtils.isEmpty(mobNo)){
            showToast("Please enter mobile number");
            isValid = false;
        }
        else if(mobNo.length()!=10){
            showToast("Mobile number should be 10 digits");
            isValid = false;
        }
        else if(TextUtils.isEmpty(email)){
            showToast("Please enter email");
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
        else if(TextUtils.isEmpty(state)){
            showToast("Please select state");
            isValid = false;
        }
        else if(TextUtils.isEmpty(city)){
            showToast("Please select city");
            isValid = false;
        }
        else if(TextUtils.isEmpty(area)){
            showToast("Please select area");
            isValid = false;
        }
        else if(TextUtils.isEmpty(location)){
            showToast("Please select location");
            isValid = false;
        }
        else if(!isServiceSelectd){
            showToast("Please select atleast one service");
            isValid = false;
        }
        return isValid;
    }


    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Choose File to Upload.."),PICK_FILE_REQUEST);

//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("application/msword,application/pdf");
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        // Only the system receives the ACTION_OPEN_DOCUMENT, so no need to test.
//        startActivityForResult(Intent.createChooser(intent,"Choose File to Upload.."),PICK_FILE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == PICK_FILE_REQUEST){
                if(data == null){
                    //no data present
                    return;
                }
                Uri uri = data.getData();
                String uriString = uri.toString();
                File myFile = new File(uriString);
                String path = myFile.getAbsolutePath();
                String displayName = null;
                Uri selectedFileUri = data.getData();
                selectedFilePath = FilePathUtils.getFilePathByUriString(this,selectedFileUri);
                Log.i(TAG,"Selected File Path:" + selectedFilePath);

                if(selectedFilePath != null && !selectedFilePath.equals("")){
                    final View view =  LayoutInflater.from(ExServiceManRegistrationActivity.this)
                            .inflate(R.layout.layout_documents,null);
                    final TextView tvUploadFineName = view.findViewById(R.id.tvUploadedFile);
                    ImageView ivRemove = view.findViewById(R.id.ivRemove);
                    tvUploadFineName.setText(selectedFilePath);
                    docList.add(selectedFilePath);
                    llDocuments.addView(view);
                    ivRemove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String text = tvUploadFineName.getText().toString();
                            llDocuments.removeView(view);
                            docList.remove(text);
                        }
                    });
                }else{
                    Toast.makeText(this,"Cannot upload file to server",Toast.LENGTH_SHORT).show();
                }
            }
            else if(requestCode == CAMERA_CAPTURE){
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    storeImage(bitmap);
                    ivUserImg.setImageBitmap(bitmap);
                } else if (resultCode == RESULT_CANCELED) {
                    showToast("User cancelled image capture");
                } else {
                    showToast("Failed to capture image");
                }
            }
        }
    }

    private void initiatePopupWindowForState(View v) {
        try {
            popupWindow  = new PopupWindow(this);
            popupWindow.setFocusable(true);
            View view = LayoutInflater.from(ExServiceManRegistrationActivity.this).inflate(R.layout.popup_window_area_list,null);
            RecyclerView rvAreas = view.findViewById(R.id.rvAreas);
            ArrayList<String> stateList = new ArrayList<>();
            stateList.add(AppConstents.TELANGANA);
            rvAreas.setLayoutManager(new LinearLayoutManager(ExServiceManRegistrationActivity.this));
            rvAreas.setAdapter(new StateAdapter(stateList,ExServiceManRegistrationActivity.this));
            popupWindow.setContentView(view);
            popupWindow.showAsDropDown(v, Gravity.CENTER,0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initiatePopupWindowForCity(View v) {
        try {
            popupWindow  = new PopupWindow(this);
            popupWindow.setFocusable(true);
            View view = LayoutInflater.from(ExServiceManRegistrationActivity.this).inflate(R.layout.popup_window_area_list,null);
            RecyclerView rvAreas = view.findViewById(R.id.rvAreas);
            ArrayList<String> cityList = new ArrayList<>();
            cityList.add(AppConstents.HYDERABAD);
            rvAreas.setLayoutManager(new LinearLayoutManager(ExServiceManRegistrationActivity.this));
            rvAreas.setAdapter(new CityAdapter(cityList,ExServiceManRegistrationActivity.this));
            popupWindow.setContentView(view);
            popupWindow.showAsDropDown(v, Gravity.CENTER,0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initiatePopupWindowForArea(View v) {
        try {
            popupWindow  = new PopupWindow(this);
            popupWindow.setFocusable(true);
            View view = LayoutInflater.from(ExServiceManRegistrationActivity.this).inflate(R.layout.popup_window_area_list,null);
            RecyclerView rvAreas = view.findViewById(R.id.rvAreas);
            ArrayList<String> areaList = new ArrayList<>();
            areaList.add("Domalguda");
            areaList.add("Secunderabad");
            areaList.add("Bansilapet");
            areaList.add("Liberty");
            areaList.add("Hyderguda");
            areaList.add("Shyamalal");
            areaList.add("Ligampalli");
            rvAreas.setLayoutManager(new LinearLayoutManager(ExServiceManRegistrationActivity.this));
            rvAreas.setAdapter(new AreaAdapter(areaList,ExServiceManRegistrationActivity.this));
            popupWindow.setContentView(view);
            popupWindow.showAsDropDown(v, Gravity.CENTER,0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void initiatePopupWindowForLocation(View v) {
        try {
            popupWindow  = new PopupWindow(this);
            popupWindow.setFocusable(true);
            View view = LayoutInflater.from(ExServiceManRegistrationActivity.this).inflate(R.layout.popup_window_area_list,null);
            RecyclerView rvAreas = view.findViewById(R.id.rvAreas);
            ArrayList<String> locationList = new ArrayList<>();
            locationList.add("Chikkadpally");
            locationList.add("Gandhinagar");
            locationList.add("Narayanaguda");
            locationList.add("Saifbad");
            locationList.add("Begumpet");
            locationList.add("Mahankali");
            rvAreas.setLayoutManager(new LinearLayoutManager(ExServiceManRegistrationActivity.this));
            rvAreas.setAdapter(new LocationAdapter(locationList,ExServiceManRegistrationActivity.this));
            popupWindow.setContentView(view);
            popupWindow.showAsDropDown(v, Gravity.CENTER,0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAreaSelected(String area) {
        if(popupWindow!=null)
            popupWindow.dismiss();
        tvArea.setText(area);
    }

    @Override
    public void onCitySelected(String city) {
        if(popupWindow!=null)
            popupWindow.dismiss();

        tvCity.setText(city);
    }

    @Override
    public void onStateSelected(String state) {
        if(popupWindow!=null)
            popupWindow.dismiss();

        tvState.setText(state);
    }

    @Override
    public void onLocationSelected(String location) {
        if(popupWindow!=null)
            popupWindow.dismiss();
        tvLocation.setText(location);
    }

}
