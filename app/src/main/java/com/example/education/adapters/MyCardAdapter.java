package com.example.education.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.education.R;
import com.example.education.utils.CommonUtils;


public class MyCardAdapter extends RecyclerView.Adapter<MyCardAdapter.ViewHolder> {
    private String[] mDataset;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        ImageView cardIcon;
        public ViewHolder(View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.ll_card_bacground);
            cardIcon = itemView.findViewById(R.id.card_icon);
        }
    }

    public MyCardAdapter(String[] myDataset, Context context) {
        mDataset = myDataset;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int randomDrawable = CommonUtils.getRandomDrawable();
        holder.linearLayout.setBackgroundResource(randomDrawable);
        if (randomDrawable == R.drawable.ic_card_gradient_1){
            holder.cardIcon.setImageResource(R.drawable.bulb_brain);
        } else if (randomDrawable == R.drawable.ic_card_gradient_3){
            holder.cardIcon.setImageResource(R.drawable.bulb_brain);
        }
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // mContext.startActivity(new Intent(mContext, ChapterActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}