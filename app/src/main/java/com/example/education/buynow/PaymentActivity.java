package com.example.education.buynow;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ct7ct7ct7.androidvimeoplayer.view.VimeoPlayer;
import com.ct7ct7ct7.androidvimeoplayer.view.VimeoPlayerActivity;
import com.example.education.EducationApplication;
import com.example.education.R;
import com.example.education.base.BaseActivity;
import com.example.education.databinding.ActivityLoginBinding;
import com.example.education.databinding.ActivityPaymentBinding;
import com.example.education.home.DashBoardActivity;
import com.example.education.login.LoginInterface;
import com.example.education.login.LoginViewModel;
import com.example.education.login.LoginViewModelFactory;
import com.example.education.mycourse.MyCourseViewModel;
import com.example.education.mycourse.MyCourseViewModelFactory;
import com.example.education.mycourse.PointListAdapter;
import com.example.education.response.ChapterResponse;
import com.example.education.response.CoupanResponse;
import com.example.education.response.CourseResponse;
import com.razorpay.Checkout;
import com.razorpay.Order;
import com.razorpay.PaymentResultListener;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Response;

public class PaymentActivity extends BaseActivity implements PaymentInterface, PaymentResultListener {

    ActivityPaymentBinding activityPaymentBinding;
    PaymentViewModel paymentViewModel;
    CourseResponse courseResponse;
    float total_price;
    int position;
    List<CoupanResponse> coupnaresponse;
    PointListAdapter pointListAdapter;
    String coupan_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityPaymentBinding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(activityPaymentBinding.getRoot());
        paymentViewModel = new ViewModelProvider(this, new PaymentViewModelFactory(this)).get(PaymentViewModel.class);
        if (getIntent() != null) {
            courseResponse = getIntent().getParcelableExtra("courseDetail");
            position = getIntent().getIntExtra("position" ,0);
        }
        Checkout.preload(getApplicationContext());
        setRecyclerView();
        activityPaymentBinding.vimeoPlayer.initialize(true, Integer.parseInt(courseResponse.video_id), courseResponse.hash_key ,courseResponse.video_url);
        activityPaymentBinding.btBuynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityPaymentBinding.vimeoPlayer.onDestroy();
                getOrderID();
            }
        });

        activityPaymentBinding.btBuynowOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityPaymentBinding.vimeoPlayer.onDestroy();
                getOrderID();
            }
        });

//        activityPaymentBinding.btApply.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    total_price = Float.parseFloat(activityPaymentBinding.tvTotalPrice.getText().toString()) - Float.parseFloat(activityPaymentBinding.tvCoupan.getText().toString());
//                    activityPaymentBinding.tvTotalPrice.setText(total_price + " Rs");
//                } catch (Exception e) {
//                    Toast.makeText(PaymentActivity.this, "NO Coupan Available", Toast.LENGTH_SHORT).show();
//                    e.printStackTrace();
//                }
//
//            }
//        });

    }

    private void getOrderID() {
        new AsyncTask<Void, Void, Order>()
        {
            @Override
            protected Order doInBackground(Void... voids) {
                Order order = null;
                RazorpayClient razorpay = null;
                try {
                    razorpay = new RazorpayClient("rzp_live_WElpDuw4livKPc", "g03fbKRVWjcFIINi4M1ixbLt");
                    JSONObject orderRequest = new JSONObject();
                    orderRequest.put("amount", total_price*100);
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
                startPayment(order.get("id"));
            }
        }.execute();

    }

    private void setRecyclerView() {
        RecyclerView recyclerView = activityPaymentBinding.pointRecyclerview;
        pointListAdapter = new PointListAdapter(this);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(pointListAdapter);
    }


    public void startPayment(String order_id) {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
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
          //  options.put("send_sms_hash", true);
           // options.put("allow_rotation", true);
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://onlinepathasala.com/eEducation/assets/SupportFile/LOGOrazorpay.png");
            options.put("currency", "INR");
            options.put("amount", total_price + "");
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
    public void setData(List<CourseResponse> body) {
        pointListAdapter.setData(body.get(position));
    }

    @Override
    public void setCoupanData(List<CoupanResponse> response) {
        coupnaresponse = response;
        try {
            activityPaymentBinding.rlCoupan.removeAllViews();
            for (int i = 0; i < response.size(); i++) {
                View childview = getLayoutInflater().inflate(R.layout.layout_coupan, null, false);
                EditText et_coupan = childview.findViewById(R.id.et_coupan);
                TextView tv_coupan = childview.findViewById(R.id.tv_coupan);
                Button bt_apply = childview.findViewById(R.id.bt_apply);
                et_coupan.setText(response.get(i).couponCode);
                tv_coupan.setText("Discount: " +response.get(i).couponPrice);
                bt_apply.setTag(i);
                bt_apply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            int position  = (Integer) v.getTag();
                            coupan_id = response.get(position).coupon_id;
                            total_price = (total_price - Float.parseFloat(response.get(position).couponPrice));
                            activityPaymentBinding.tvTotalPrice.setText(total_price + " Rs");
                            bt_apply.setText("Applied");
                            bt_apply.setEnabled(false);
                            for (int j = 0; j <activityPaymentBinding.rlCoupan.getChildCount(); j++) {
                                View nextChild = ((ViewGroup)activityPaymentBinding.rlCoupan).getChildAt(j);
                                if(j != position)
                                {
                                    nextChild.setVisibility(View.GONE);
                                }

                            }
                        } catch (Exception e) {
                            Toast.makeText(PaymentActivity.this, "NO Coupan Available", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                });

                activityPaymentBinding.rlCoupan.addView(childview);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //activityPaymentBinding.tvCoupan.setText(response.get(0).couponPrice);
    }

    @Override
    public void finishResult() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        paymentViewModel.courseApi(this);
        activityPaymentBinding.tvTotalAmount.setText("" + courseResponse.course_price + " Rs");
        activityPaymentBinding.tvTitle.setText(courseResponse.course_name);
        activityPaymentBinding.tvDescription.setText(courseResponse.course_details);
        activityPaymentBinding.tvGst.setText("" + courseResponse.gst + "%");
        activityPaymentBinding.tvCourseTime.setText(courseResponse.course_duration + " Min");
        // activityPaymentBinding.tvCoupan.setText("");
        total_price = (Float.parseFloat(courseResponse.course_price) + (Float.parseFloat(courseResponse.course_price) * Float.parseFloat(courseResponse.gst) / 100));
        activityPaymentBinding.tvTotalPrice.setText(total_price + "");
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String date = year + "-" + month + "-" + day;
        paymentViewModel.copanApi(this, courseResponse.course_id, date);
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            Date dt = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(dt);
            c.add(Calendar.DATE, Integer.parseInt(courseResponse.course_duration));
            dt = c.getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            String end_date = dateFormat.format(dt);
            paymentViewModel.purchaseApi(this, razorpayPaymentID, coupan_id, activityPaymentBinding.tvTotalPrice.getText().toString(), courseResponse.course_id, courseResponse.course_price, courseResponse.gst, courseResponse.course_duration,
                    courseResponse.course_name ,end_date);
            Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("onPaymentSuccess", "Exception in onPaymentSuccess", e);
        }
    }

    @Override
    public void onPaymentError(int code, String response) {
        try {
            Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("onPaymentError", "Exception in onPaymentError", e);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            activityPaymentBinding.vimeoPlayer.onDestroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}