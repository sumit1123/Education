package com.example.education.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.education.R;


public class MyChapterAdapter extends RecyclerView.Adapter<MyChapterAdapter.ViewHolder> {
    private String[] mDataset;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvWatch;
        TextView tvTest;
        LinearLayout llTestExercise, llCollapsingLayout;
        ImageButton btnCollapse;
        boolean collpased = true;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public MyChapterAdapter(String[] myDataset, Context context) {
        mDataset = myDataset;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chapter_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Shader textShader=new LinearGradient(0, 0, 100, 20,
                new int[]{Color.BLUE, Color.parseColor("#00BCD4")},
                new float[]{0, 1}, Shader.TileMode.CLAMP);
        holder.tvWatch.getPaint().setShader(textShader);
        holder.tvTest.getPaint().setShader(textShader);
        holder.llTestExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  mContext.startActivity(new Intent(mContext, TestOptionsActivity.class));
            }
        });

        holder.btnCollapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.collpased){
                    holder.collpased = false;
                    holder.llCollapsingLayout.setVisibility(View.VISIBLE);
                    holder.btnCollapse.setImageResource(R.drawable.ic_up_arrow);
                } else {
                    holder.collpased = true;
                    holder.llCollapsingLayout.setVisibility(View.GONE);
                    holder.btnCollapse.setImageResource(R.drawable.ic_down_arrow);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}