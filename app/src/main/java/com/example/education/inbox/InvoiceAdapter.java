package com.example.education.inbox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.education.R;
import com.example.education.databinding.AdapterInvoiceBinding;
import com.example.education.repo.request.Exam;
import com.example.education.response.InvoiceResonse;

import java.util.ArrayList;
import java.util.List;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.ViewHolder> {

    private Context mContext;
    List<InvoiceResonse> invoiceResonseArrayList = new ArrayList<>();
    ViewHolder holder;

    public void setData(List<InvoiceResonse> body) {
          this.invoiceResonseArrayList = body;
          notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        AdapterInvoiceBinding adapterInvoiceBinding;

        public ViewHolder(AdapterInvoiceBinding adapterInvoiceBinding) {
            super(adapterInvoiceBinding.getRoot());
            this.adapterInvoiceBinding = adapterInvoiceBinding;

        }
    }

    public InvoiceAdapter(Context context) {
        mContext = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AdapterInvoiceBinding adapterInvoiceBinding = AdapterInvoiceBinding.inflate(LayoutInflater.from(mContext), parent, false);
          return new ViewHolder(adapterInvoiceBinding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        this.holder = holder;
        holder.adapterInvoiceBinding.invoiceNo.setText("Invoice No:- " +invoiceResonseArrayList.get(position).order_id);
        holder.adapterInvoiceBinding.courseName.setText(invoiceResonseArrayList.get(position).course_name);
        holder.adapterInvoiceBinding.courseDate.setText("Invoice Date: - "  +invoiceResonseArrayList.get(position).purchase_date);
        holder.adapterInvoiceBinding.coursePrice.setText("Price:- " +invoiceResonseArrayList.get(position).course_price);
        holder.adapterInvoiceBinding.number.setText("Phone Number:- " + invoiceResonseArrayList.get(position).phone);
    }

    @Override
    public int getItemCount() {
        return invoiceResonseArrayList.size();
    }
}