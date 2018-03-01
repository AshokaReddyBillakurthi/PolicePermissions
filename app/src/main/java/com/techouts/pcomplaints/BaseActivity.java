package com.techouts.pcomplaints;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.techouts.pcomplaints.utils.SharedPreferenceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class BaseActivity extends AppCompatActivity {
    private SharedPreferenceUtils sharedPreferenceUtils;
    public String userImg = "";

    private static final String TAG = BaseActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getRootLayout());
        sharedPreferenceUtils  = new SharedPreferenceUtils(BaseActivity.this);
        initGUI();
        initData();
    }

    public abstract int getRootLayout();
    public abstract void initGUI();
    public abstract void initData();

    public void showToast(String message){
        Toast.makeText(BaseActivity.this,message,Toast.LENGTH_LONG).show();
    }

    public void storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d(TAG,"Error creating media file, check storage permissions: ");
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d(TAG, "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "Error accessing file: " + e.getMessage());
        }
    }

    /**
     * Create a File for saving an image or video
     */
    private File getOutputMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(
                "/data/data/"
                        + getApplicationContext().getPackageName()
                        + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        userImg = "MI_" + timeStamp + ".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + userImg);
        return mediaFile;
    }

    public Bitmap getUserImageBitMap(String serviceManImg) {
        Bitmap myBitmap = null;
        try {
            File mediaStorageDir = new File(
                    "/data/data/"
                            + getPackageName()
                            + "/Files");

            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    return null;
                }
            }

            File mediaFile = new File(mediaStorageDir.getPath() + File.separator + serviceManImg);
            if (mediaFile.exists()) {
                myBitmap = BitmapFactory.decodeFile(mediaFile.getAbsolutePath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  myBitmap;
    }


}
