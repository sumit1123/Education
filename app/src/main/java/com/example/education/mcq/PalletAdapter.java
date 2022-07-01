package com.example.education.mcq;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.education.EducationApplication;
import com.example.education.R;
import com.example.education.repo.request.Exam;
import com.example.education.response.MCQResponse;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PalletAdapter extends RecyclerView.Adapter<PalletAdapter.ViewHolder> {

    private Context mContext;
    int number;
    List<MCQResponse> mcqResponses;
    ArrayList<Exam> examArrayList = new ArrayList<>();
    RecyclerView recyclerView;

    public void setData(ArrayList<Exam> examArrayList) {
            this.examArrayList = examArrayList;
            notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_number;
        MaterialCardView rl_root;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_number = itemView.findViewById(R.id.tv_number);
            rl_root = itemView.findViewById(R.id.rl_root);
        }
    }

    public PalletAdapter(Context context, List<MCQResponse> mcqResponses ,RecyclerView recyclerView) {
        mContext = context;
        this.mcqResponses = mcqResponses;
        this.recyclerView =recyclerView;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_pallet, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        number = position + 1;
        holder.tv_number.setText(""+number);
        try
        {
                if(examArrayList.get(position).getSelect_position() == position)
                {
                    holder.rl_root.setCardBackgroundColor(mContext.getResources().getColor(R.color.blue));
                    holder.tv_number.setTextColor(mContext.getResources().getColor(R.color.white));
                }
                if(examArrayList.get(position).getSkipped().equalsIgnoreCase("1"))
                {
                    holder.rl_root.setCardBackgroundColor(mContext.getResources().getColor(R.color.red));
                    holder.tv_number.setTextColor(mContext.getResources().getColor(R.color.white));
                }

            holder.tv_number.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EducationApplication.editor.putInt("current_position", Integer.parseInt(holder.tv_number.getText().toString())-1).apply();
                    recyclerView.scrollToPosition(Integer.parseInt(holder.tv_number.getText().toString())-1);
                }
            });

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mcqResponses.size();
    }
}