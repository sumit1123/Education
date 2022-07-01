package com.example.education.certificate;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.example.education.EducationApplication;
import com.example.education.base.BaseActivity;
import com.example.education.databinding.ActivityFinalExamBinding;
import com.example.education.mcq.MCQActivity;
import com.example.education.response.CourseResponse;
import com.razorpay.Checkout;
import com.razorpay.Order;
import com.razorpay.PaymentResultListener;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FinalExamFormActivity extends BaseActivity implements CertificateInterface, PaymentResultListener {

    ActivityFinalExamBinding activityFinalExamBinding;
    String courseid;
    List<BuyExamResponse> buyResponse = new ArrayList<>();
    private CertificateViewModel certificateViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityFinalExamBinding = ActivityFinalExamBinding.inflate(getLayoutInflater());
        setContentView(activityFinalExamBinding.getRoot());
        certificateViewModel = new ViewModelProvider(this, new CertificateViewModelFactory(this)).get(CertificateViewModel.class);
        courseid = getIntent().getStringExtra("courseid");
        activityFinalExamBinding.btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!activityFinalExamBinding.name.getText().toString().equalsIgnoreCase("")) {
                    if (!activityFinalExamBinding.fatherName.getText().toString().equalsIgnoreCase("")) {
                        if (!activityFinalExamBinding.dob.getText().toString().equalsIgnoreCase("")) {
                            if (!activityFinalExamBinding.phone.getText().toString().equalsIgnoreCase("")) {
                                if (!activityFinalExamBinding.address.getText().toString().equalsIgnoreCase("")) {
                                    if (!activityFinalExamBinding.landmark.getText().toString().equalsIgnoreCase("")) {
                                        if (!activityFinalExamBinding.pincode.getText().toString().equalsIgnoreCase("")) {
                                            if (!activityFinalExamBinding.state.getText().toString().equalsIgnoreCase("")) {
                                                if (!activityFinalExamBinding.city.getText().toString().equalsIgnoreCase("")) {
                                                    showLoading(true);
                                                    certificateViewModel.examformApi(FinalExamFormActivity.this, activityFinalExamBinding.phone.getText().toString(),
                                                            activityFinalExamBinding.fatherName.getText().toString(),
                                                            activityFinalExamBinding.address.getText().toString(),
                                                            activityFinalExamBinding.landmark.getText().toString(),
                                                            activityFinalExamBinding.pincode.getText().toString(), activityFinalExamBinding.state.getText().toString(), activityFinalExamBinding.city.getText().toString(), activityFinalExamBinding.dob.getText().toString(), courseid, activityFinalExamBinding.name.getText().toString(), buyResponse.get(0).exam_id);
                                                } else {
                                                    showToast("Enter city");
                                                }
                                            } else {
                                                showToast("Enter state");
                                            }
                                        } else {
                                            showToast("Enter pin code");
                                        }
                                    } else {
                                        showToast("Enter landmark");
                                    }
                                } else {
                                    showToast("Enter address");
                                }

                            } else {
                                showToast("Enter DOB");
                            }
                        } else {
                            showToast("Enter DOB");
                        }
                    } else {
                        showToast("Enter father name");
                    }
                } else {
                    showToast("Enter name");
                }
            }
        });
    }

//    rzp_live_WElpDuw4livKPc
//    g03fbKRVWjcFIINi4M1ixbLt

   // $key_id = rzp_test_u2Tvvx9bVBoGHa
         //   $key_secret = KCUKJRguTkRrXohgOV02UY9X

    private void getOrderID(String total_price) {
        new AsyncTask<Void, Void, Order>()
        {
            @Override
            protected Order doInBackground(Void... voids) {
                RazorpayClient razorpay = null;
                Order order = null;
                try {
                    razorpay = new RazorpayClient("rzp_live_WElpDuw4livKPc", "g03fbKRVWjcFIINi4M1ixbLt");
                    JSONObject orderRequest = new JSONObject();
                    orderRequest.put("amount", Integer.parseInt(total_price)*100);
                    orderRequest.put("currency","INR");
                    orderRequest.put("receipt", "receipt#1");
                    orderRequest.put("payment_capture", "1");
                    JSONObject notes = new JSONObject();
                    notes.put("notes_key_1","Tea, Earl Grey, Hot");
                    notes.put("notes_key_1","Tea, Earl Grey, Hot");
                    orderRequest.put("notes",notes);
                    order = razorpay.orders.create(orderRequest);
                    Log.e("order" ,order+"");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return order;
            }
            @Override
            protected void onPostExecute(Order order) {
                super.onPostExecute(order);
                startPayment(total_price , order.get("id"));
            }
        }.execute();

    }


    public void startPayment(String price , String order_id) {
//        rzp_test_BIovYo97GanHZr
        final Activity activity = this;
        final Checkout co = new Checkout();
        co.setKeyID("rzp_live_WElpDuw4livKPc");
//        rzp_live_WElpDuw4livKPc
        try {
            JSONObject options = new JSONObject();
            options.put("name", "eLearner Sathi");
            options.put("description", "Demoing Charges");
            options.put("order_id", order_id);
            options.put("send_sms_hash", true);
            options.put("allow_rotation", true);
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "http://onlinepathasala.com/eEducation/assets/SupportFile/LOGOrazorpay.png");
            options.put("currency", "INR");
            options.put("amount", price);
            JSONObject preFill = new JSONObject();
            preFill.put("email", EducationApplication.sharedPreferences.getString("email_id", ""));
            preFill.put("contact", EducationApplication.sharedPreferences.getString("phone", ""));
            options.put("prefill", preFill);
            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    @Override
    public void dismissProgressbar() {
        showLoading(false);
    }

    @Override
    public void setData(CourseResponse body) {
        showBuyDialog(buyResponse.get(0).exam_price);
    }

    @Override
    public void setBuyData(List<BuyExamResponse> body) {
        this.buyResponse = body;
        try {
            if (buyResponse.get(0).form_status.equalsIgnoreCase("1") && buyResponse.get(0).purchase_status.equalsIgnoreCase("0")) {
                showBuyDialog(buyResponse.get(0).exam_price);
            } else if (buyResponse.get(0).form_status.equalsIgnoreCase("1") && buyResponse.get(0).purchase_status.equalsIgnoreCase("1")) {
                if (buyResponse.get(0).exam_staus.equalsIgnoreCase("")) {
                    startExamDialog(buyResponse.get(0).exam_id);
                } else if (buyResponse.get(0).exam_staus.equalsIgnoreCase("fail")) {
                            showBuyDialog(buyResponse.get(0).reexam_price);
                } else {
                    finish();
                    Toast.makeText(FinalExamFormActivity.this, "You have already pass that exam", Toast.LENGTH_SHORT).show();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        certificateViewModel.dobuyExamName(this, courseid);

    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            certificateViewModel.exampurchaseApi(FinalExamFormActivity.this, courseid, razorpayPaymentID, buyResponse.get(0));
            finish();
        } catch (Exception e) {
            Log.e("onPaymentSuccess", "Exception in onPaymentSuccess", e);
        }
    }

    @Override
    public void onPaymentError(int i, String s) {
        try {
            finish();
            Toast.makeText(this, "Payment failed:", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("onPaymentError", "Exception in onPaymentError", e);
        }
    }

    public void showBuyDialog(String price) {
        AlertDialog.Builder builder = new AlertDialog.Builder(FinalExamFormActivity.this);
        builder.setMessage("Your registration is successfull now you need to buy this exam. By pressing OK you will navigate to purchase screen");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getOrderID(price);

            }
        });

//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//            }
//        });
        Dialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    public void startExamDialog(String exam_id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(FinalExamFormActivity.this);
        builder.setMessage("Do you want to start exam");
        builder.setPositiveButton("START", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                certificateViewModel.dobuyExamStart(FinalExamFormActivity.this , exam_id);
                Intent exam = new Intent(FinalExamFormActivity.this, MCQActivity.class);
                exam.putExtra("examid", buyResponse.get(0).exam_id);
                exam.putExtra("examduration", buyResponse.get(0).exam_duration);
                exam.putExtra("title", buyResponse.get(0).exam_name);
                exam.putExtra("courseid", courseid);
                exam.putExtra("examtype", "2");
                startActivity(exam);
                finish();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                finish();
            }
        });
        Dialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }


}