package com.techouts.pcomplaints;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.payumoney.sdkui.ui.utils.ResultModel;
import com.techouts.pcomplaints.adapters.ComplaintInstructionAdapter;
import com.techouts.pcomplaints.utils.AppConstents;
import com.techouts.pcomplaints.utils.DataManager;
import com.techouts.pcomplaints.utils.DialogUtils;

import java.util.List;

public class PermissionInstructionActivity extends BaseActivity {

    private RecyclerView rvComplaintInstructions;
    private ImageView ivBack;
    private TextView tvTitle;
    private LinearLayout llApply;
    private ComplaintInstructionAdapter complaintInstructionAdapter;
    private String applicationType;
    private String TAG = PermissionInstructionActivity.class.getSimpleName();

    @Override
    public int getRootLayout() {
        return R.layout.activity_permission_instruction;
    }

    @Override
    public void initGUI() {

        if (getIntent().getExtras() != null) {
            applicationType = getIntent().getStringExtra(AppConstents.EXTRA_APPLICATION_TYPE);
        }

        rvComplaintInstructions = findViewById(R.id.rvComplaintInstructions);
        ivBack = findViewById(R.id.ivBack);
        tvTitle = findViewById(R.id.tvTitle);
        llApply = findViewById(R.id.llApply);

        rvComplaintInstructions.setLayoutManager(new LinearLayoutManager(PermissionInstructionActivity.this));
        complaintInstructionAdapter = new ComplaintInstructionAdapter();
        rvComplaintInstructions.setAdapter(complaintInstructionAdapter);
        switch (applicationType) {
            case AppConstents.INTERNET_CAFES:
                tvTitle.setText(AppConstents.INTERNET_CAFES);
                break;
            case AppConstents.GUN_LICENCES:
                tvTitle.setText(AppConstents.INTERNET_CAFES);
                break;
            default:
                break;
        }

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        llApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchPayUMoneyFlow();
            }
        });
    }

    @Override
    public void initData() {
        List<String> permissionInstructionList = null;
        try {
            switch (applicationType) {
                case AppConstents.INTERNET_CAFES:
                    permissionInstructionList = DataManager.getInstructionsForCyberCafesPermission();
                    break;
                case AppConstents.GUN_LICENCES:
                    permissionInstructionList = DataManager.getInstructionForArmsLicense();
                    break;
                default:
                    break;
            }
            if(permissionInstructionList!=null&&!permissionInstructionList.isEmpty())
                complaintInstructionAdapter.refresh(permissionInstructionList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {
             if(requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT&&null!=data){
                TransactionResponse transactionResponse = data.getParcelableExtra(PayUmoneyFlowManager
                        .INTENT_EXTRA_TRANSACTION_RESPONSE);

                ResultModel resultModel = data.getParcelableExtra(PayUmoneyFlowManager.ARG_RESULT);

                // Check which object is non-null
                if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {
                    if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.SUCCESSFUL)) {
                        //Success Transaction
//                    DialogUtils.showDialog(CyberCafeApplicationActivity.this,"Payment Successful", AppConstents.FINISH,false);
                    } else {
                        //Failure Transaction
//                    DialogUtils.showDialog(CyberCafeApplicationActivity.this,getResources().getString(R.string.error_message), AppConstents.FINISH,false);
                    }

                    // Response from Payumoney
                    String payuResponse = transactionResponse.getPayuResponse();

                    // Response from SURl and FURL
                    String merchantResponse = transactionResponse.getTransactionDetails();

                    DialogUtils.showDialog(PermissionInstructionActivity.this,"Payment Successful", AppConstents.FINISH,false);

//                    new AlertDialog.Builder(this)
//                            .setCancelable(false)
//                            .setMessage("Payment Successful")
//                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int whichButton) {
//                                    dialog.dismiss();
//                                    finish();
//                                }
//                            }).show();

                } else if (resultModel != null && resultModel.getError() != null) {
                    Log.d(TAG, "Error response : " + resultModel.getError().getTransactionResponse());
                } else {
                    Log.d(TAG, "Both objects are null!");
                }

            }
        }
    }
}
