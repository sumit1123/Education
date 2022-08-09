package com.example.education.mcq;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.daasuu.ahp.AnimateHorizontalProgressBar;
import com.example.education.EducationApplication;
import com.example.education.R;
import com.example.education.repo.request.Exam;
import com.example.education.repo.request.MCQ;
import com.example.education.response.MCQResponse;
import com.example.education.response.SetResponse;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.radiobutton.MaterialRadioButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MCQAdapter extends RecyclerView.Adapter<MCQAdapter.ViewHolder> {

    private Context mContext;
    List<MCQResponse> mcqResponses = new ArrayList<>();
    RecyclerView recyclerView;
    int selected_position;
    String examtype;
    ArrayList<Exam> examArrayList = new ArrayList<>();
    ArrayList<MCQ> mcqArrayList = new ArrayList<>();
    AnimateHorizontalProgressBar progressBar;
    ViewHolder holder;
    int total_correct_answer = 0;

    public void setData(List<MCQResponse> mcqResponses, RecyclerView recyclerView, String examtype) {
        this.mcqResponses = mcqResponses;
        this.recyclerView = recyclerView;
        this.examtype = examtype;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_question, tv_solution, tv_position;
        TextView tv_option_1, tv_option_2, tv_option_3, tv_option_4;
        LinearLayout radio_group;
        MaterialCardView ll_option_a, ll_option_b, ll_option_c, ll_option_d;
        LinearLayout ll_solution, rl_root;
        ImageView img_back, img_next;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_question = itemView.findViewById(R.id.tv_question);
            tv_option_1 = itemView.findViewById(R.id.tv_option_1);
            tv_option_2 = itemView.findViewById(R.id.tv_option_2);
            tv_option_3 = itemView.findViewById(R.id.tv_option_3);
            tv_option_4 = itemView.findViewById(R.id.tv_option_4);
            ll_option_a = itemView.findViewById(R.id.ll_option_a);
            ll_option_b = itemView.findViewById(R.id.ll_option_b);
            ll_option_c = itemView.findViewById(R.id.ll_option_c);
            ll_option_d = itemView.findViewById(R.id.ll_option_d);
            radio_group = itemView.findViewById(R.id.radio_group);
            ll_solution = itemView.findViewById(R.id.ll_solution);
            tv_solution = itemView.findViewById(R.id.tv_solution);
            tv_position = itemView.findViewById(R.id.tv_position);
            rl_root = itemView.findViewById(R.id.rl_root);
            img_back = itemView.findViewById(R.id.img_back);
            img_next = itemView.findViewById(R.id.img_next);
        }
    }

    public MCQAdapter(Context context, ArrayList<Exam> examArrayList, AnimateHorizontalProgressBar progressBar) {
        mContext = context;
        this.examArrayList = examArrayList;
        this.progressBar = progressBar;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_mcq, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        this.holder = holder;
        holder.tv_question.setText(mcqResponses.get(position).question);
        holder.tv_option_1.setText(mcqResponses.get(position).option1);
        holder.tv_option_2.setText(mcqResponses.get(position).option2);
        holder.tv_option_3.setText(mcqResponses.get(position).option3);
        holder.tv_option_4.setText(mcqResponses.get(position).option4);
        holder.tv_position.setText(position + 1 + "/" + mcqResponses.size());
        progressBar.setMax(mcqResponses.size());
        progressBar.setProgress(position + 1);
        EducationApplication.editor.putInt("total_mcq_questions" ,  mcqResponses.size()).apply();
        if (examtype.equalsIgnoreCase("2")) {
            holder.ll_solution.setVisibility(View.GONE);
        } else {
            holder.ll_solution.setVisibility(View.VISIBLE);
        }
        holder.ll_option_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (examtype.equalsIgnoreCase("2")) {
                    mcqResponses.get(holder.getAdapterPosition()).selected_answer = 1;
                    holder.ll_option_a.setBackgroundTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.gray)));
                    holder.ll_option_a.setStrokeColor(mContext.getResources().getColor(R.color.black));
                    holder.ll_option_b.setStrokeColor(mContext.getResources().getColor(R.color.white));
                    holder.ll_option_c.setStrokeColor(mContext.getResources().getColor(R.color.white));
                    holder.ll_option_d.setStrokeColor(mContext.getResources().getColor(R.color.white));
                    Exam exam = new Exam();
                    exam.setQuesiton(mcqResponses.get(holder.getAdapterPosition()).question);
                    exam.setCorrect_answer(mcqResponses.get(holder.getAdapterPosition()).correct_option);
                    if(mcqResponses.get(holder.getAdapterPosition()).correct_option.equalsIgnoreCase("1"))
                    {
                        exam.setCorrect_answer_val(mcqResponses.get(holder.getAdapterPosition()).option1);
                    }
                    else if(mcqResponses.get(holder.getAdapterPosition()).correct_option.equalsIgnoreCase("2"))
                    {
                        exam.setCorrect_answer_val(mcqResponses.get(holder.getAdapterPosition()).option2);
                    }
                    else if(mcqResponses.get(holder.getAdapterPosition()).correct_option.equalsIgnoreCase("3"))
                    {
                        exam.setCorrect_answer_val(mcqResponses.get(holder.getAdapterPosition()).option3);
                    }
                    else if(mcqResponses.get(holder.getAdapterPosition()).correct_option.equalsIgnoreCase("4"))
                    {
                        exam.setCorrect_answer_val(mcqResponses.get(holder.getAdapterPosition()).option4);
                    }
                    exam.setSelect_answer(mcqResponses.get(holder.getAdapterPosition()).option1);
                    exam.setSelect_position(holder.getAdapterPosition());
                    examArrayList.set(holder.getAdapterPosition(), exam);

                } else {
                    if (mcqResponses.get(EducationApplication.sharedPreferences.getInt("current_position", 0)).selected_answer == -1) {
                        mcqResponses.get(EducationApplication.sharedPreferences.getInt("current_position", 0)).selected_answer = 1;
                        mcqResponses.get(EducationApplication.sharedPreferences.getInt("current_position", 0)).isSelected = true;
                        setAnswer(holder, EducationApplication.sharedPreferences.getInt("current_position", 0), mcqResponses.get(EducationApplication.sharedPreferences.getInt("current_position", 0)).selected_answer);

                    }
                }
            }
        });
        holder.ll_option_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (examtype.equalsIgnoreCase("2")) {
                    mcqResponses.get(holder.getAdapterPosition()).selected_answer = 2;
                    holder.ll_option_b.setStrokeColor(mContext.getResources().getColor(R.color.black));
                    holder.ll_option_b.setBackgroundTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.gray)));
                    holder.ll_option_a.setStrokeColor(mContext.getResources().getColor(R.color.white));
                    holder.ll_option_c.setStrokeColor(mContext.getResources().getColor(R.color.white));
                    holder.ll_option_d.setStrokeColor(mContext.getResources().getColor(R.color.white));
                    Exam exam = new Exam();
                    exam.setQuesiton(mcqResponses.get(holder.getAdapterPosition()).question);
                    exam.setCorrect_answer(mcqResponses.get(holder.getAdapterPosition()).correct_option);
                    if(mcqResponses.get(holder.getAdapterPosition()).correct_option.equalsIgnoreCase("1"))
                    {
                        exam.setCorrect_answer_val(mcqResponses.get(holder.getAdapterPosition()).option1);
                    }
                    else if(mcqResponses.get(holder.getAdapterPosition()).correct_option.equalsIgnoreCase("2"))
                    {
                        exam.setCorrect_answer_val(mcqResponses.get(holder.getAdapterPosition()).option2);
                    }
                    else if(mcqResponses.get(holder.getAdapterPosition()).correct_option.equalsIgnoreCase("3"))
                    {
                        exam.setCorrect_answer_val(mcqResponses.get(holder.getAdapterPosition()).option3);
                    }
                    else if(mcqResponses.get(holder.getAdapterPosition()).correct_option.equalsIgnoreCase("4"))
                    {
                        exam.setCorrect_answer_val(mcqResponses.get(holder.getAdapterPosition()).option4);
                    }
                    exam.setSelect_answer(mcqResponses.get(holder.getAdapterPosition()).option2);
                    exam.setSelect_position(holder.getAdapterPosition());
                    examArrayList.set(holder.getAdapterPosition(), exam);
                } else {
                    if (mcqResponses.get(EducationApplication.sharedPreferences.getInt("current_position", 0)).selected_answer == -1) {
                        mcqResponses.get(EducationApplication.sharedPreferences.getInt("current_position", 0)).selected_answer = 2;
                        mcqResponses.get(EducationApplication.sharedPreferences.getInt("current_position", 0)).isSelected = true;
                        setAnswer(holder, EducationApplication.sharedPreferences.getInt("current_position", 0), mcqResponses.get(EducationApplication.sharedPreferences.getInt("current_position", 0)).selected_answer);
                    }

                }

            }
        });
        holder.ll_option_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (examtype.equalsIgnoreCase("2")) {
                    mcqResponses.get(holder.getAdapterPosition()).selected_answer = 3;
                    holder.ll_option_c.setStrokeColor(mContext.getResources().getColor(R.color.black));
                    holder.ll_option_c.setBackgroundTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.gray)));
                    holder.ll_option_a.setStrokeColor(mContext.getResources().getColor(R.color.white));
                    holder.ll_option_b.setStrokeColor(mContext.getResources().getColor(R.color.white));
                    holder.ll_option_d.setStrokeColor(mContext.getResources().getColor(R.color.white));
                    Exam exam = new Exam();
                    exam.setQuesiton(mcqResponses.get(holder.getAdapterPosition()).question);
                    exam.setCorrect_answer(mcqResponses.get(holder.getAdapterPosition()).correct_option);
                    if(mcqResponses.get(holder.getAdapterPosition()).correct_option.equalsIgnoreCase("1"))
                    {
                        exam.setCorrect_answer_val(mcqResponses.get(holder.getAdapterPosition()).option1);
                    }
                    else if(mcqResponses.get(holder.getAdapterPosition()).correct_option.equalsIgnoreCase("2"))
                    {
                        exam.setCorrect_answer_val(mcqResponses.get(holder.getAdapterPosition()).option2);
                    }
                    else if(mcqResponses.get(holder.getAdapterPosition()).correct_option.equalsIgnoreCase("3"))
                    {
                        exam.setCorrect_answer_val(mcqResponses.get(holder.getAdapterPosition()).option3);
                    }
                    else if(mcqResponses.get(holder.getAdapterPosition()).correct_option.equalsIgnoreCase("4"))
                    {
                        exam.setCorrect_answer_val(mcqResponses.get(holder.getAdapterPosition()).option4);
                    }
                    exam.setSelect_answer(mcqResponses.get(holder.getAdapterPosition()).option3);
                    exam.setSelect_position(holder.getAdapterPosition());
                    examArrayList.set(holder.getAdapterPosition(), exam);
                } else {
                    if (mcqResponses.get(EducationApplication.sharedPreferences.getInt("current_position", 0)).selected_answer == -1) {
                        mcqResponses.get(EducationApplication.sharedPreferences.getInt("current_position", 0)).selected_answer = 3;
                        mcqResponses.get(EducationApplication.sharedPreferences.getInt("current_position", 0)).isSelected = true;
                        setAnswer(holder, EducationApplication.sharedPreferences.getInt("current_position", 0), mcqResponses.get(EducationApplication.sharedPreferences.getInt("current_position", 0)).selected_answer);
                    }
                }

            }
        });
        holder.ll_option_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (examtype.equalsIgnoreCase("2")) {
                    mcqResponses.get(holder.getAdapterPosition()).selected_answer = 4;
                    holder.ll_option_d.setStrokeColor(mContext.getResources().getColor(R.color.black));
                    holder.ll_option_d.setBackgroundTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.gray)));
                    holder.ll_option_a.setStrokeColor(mContext.getResources().getColor(R.color.white));
                    holder.ll_option_b.setStrokeColor(mContext.getResources().getColor(R.color.white));
                    holder.ll_option_c.setStrokeColor(mContext.getResources().getColor(R.color.white));
                    Exam exam = new Exam();
                    exam.setQuesiton(mcqResponses.get(holder.getAdapterPosition()).question);
                    exam.setCorrect_answer(mcqResponses.get(holder.getAdapterPosition()).correct_option);
                    if(mcqResponses.get(holder.getAdapterPosition()).correct_option.equalsIgnoreCase("1"))
                    {
                        exam.setCorrect_answer_val(mcqResponses.get(holder.getAdapterPosition()).option1);
                    }
                    else if(mcqResponses.get(holder.getAdapterPosition()).correct_option.equalsIgnoreCase("2"))
                    {
                        exam.setCorrect_answer_val(mcqResponses.get(holder.getAdapterPosition()).option2);
                    }
                    else if(mcqResponses.get(holder.getAdapterPosition()).correct_option.equalsIgnoreCase("3"))
                    {
                        exam.setCorrect_answer_val(mcqResponses.get(holder.getAdapterPosition()).option3);
                    }
                    else if(mcqResponses.get(holder.getAdapterPosition()).correct_option.equalsIgnoreCase("4"))
                    {
                        exam.setCorrect_answer_val(mcqResponses.get(holder.getAdapterPosition()).option4);
                    }
                    exam.setSelect_answer(mcqResponses.get(holder.getAdapterPosition()).option4);
                    exam.setSelect_position(holder.getAdapterPosition());
                    examArrayList.set(holder.getAdapterPosition(), exam);
                } else {
                    if (mcqResponses.get(EducationApplication.sharedPreferences.getInt("current_position", 0)).selected_answer == -1) {
                        mcqResponses.get(EducationApplication.sharedPreferences.getInt("current_position", 0)).selected_answer = 4;
                        mcqResponses.get(EducationApplication.sharedPreferences.getInt("current_position", 0)).isSelected = true;
                        setAnswer(holder, EducationApplication.sharedPreferences.getInt("current_position", 0), mcqResponses.get(EducationApplication.sharedPreferences.getInt("current_position", 0)).selected_answer);
                    }
                }

            }
        });
        if (examtype.equalsIgnoreCase("1")) {
            setColors(holder, position);
        } else {
            setColorsExam(holder, holder.getAdapterPosition());
        }
    }

    private void setColorsExam(ViewHolder holder, int position) {
        if (examArrayList.get(position).getSelect_answer().equalsIgnoreCase("1")) {
            holder.ll_option_a.setStrokeColor(mContext.getResources().getColor(R.color.gray));
            holder.ll_option_a.setBackgroundColor(mContext.getResources().getColor(R.color.gray));
            holder.ll_option_b.setStrokeColor(mContext.getResources().getColor(R.color.white));
            holder.ll_option_c.setStrokeColor(mContext.getResources().getColor(R.color.white));
            holder.ll_option_d.setStrokeColor(mContext.getResources().getColor(R.color.white));
        } else if (examArrayList.get(position).getSelect_answer().equalsIgnoreCase("2")) {
            holder.ll_option_b.setStrokeColor(mContext.getResources().getColor(R.color.gray));
            holder.ll_option_b.setBackgroundColor(mContext.getResources().getColor(R.color.gray));
            holder.ll_option_a.setStrokeColor(mContext.getResources().getColor(R.color.white));
            holder.ll_option_c.setStrokeColor(mContext.getResources().getColor(R.color.white));
            holder.ll_option_d.setStrokeColor(mContext.getResources().getColor(R.color.white));
        } else if (examArrayList.get(position).getSelect_answer().equalsIgnoreCase("3")) {
            holder.ll_option_c.setStrokeColor(mContext.getResources().getColor(R.color.gray));
            holder.ll_option_c.setBackgroundColor(mContext.getResources().getColor(R.color.gray));
            holder.ll_option_a.setStrokeColor(mContext.getResources().getColor(R.color.white));
            holder.ll_option_b.setStrokeColor(mContext.getResources().getColor(R.color.white));
            holder.ll_option_d.setStrokeColor(mContext.getResources().getColor(R.color.white));
        } else if (examArrayList.get(position).getSelect_answer().equalsIgnoreCase("4")) {
            holder.ll_option_d.setStrokeColor(mContext.getResources().getColor(R.color.gray));
            holder.ll_option_d.setBackgroundColor(mContext.getResources().getColor(R.color.gray));
            holder.ll_option_a.setStrokeColor(mContext.getResources().getColor(R.color.white));
            holder.ll_option_c.setStrokeColor(mContext.getResources().getColor(R.color.white));
            holder.ll_option_b.setStrokeColor(mContext.getResources().getColor(R.color.white));
        } else if (examArrayList.get(position).getSelect_answer().equalsIgnoreCase("0")) {
            holder.ll_option_d.setStrokeColor(mContext.getResources().getColor(R.color.white));
            holder.ll_option_a.setStrokeColor(mContext.getResources().getColor(R.color.white));
            holder.ll_option_c.setStrokeColor(mContext.getResources().getColor(R.color.white));
            holder.ll_option_b.setStrokeColor(mContext.getResources().getColor(R.color.white));

            holder.ll_option_a.setBackgroundTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.white)));
            holder.ll_option_b.setBackgroundTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.white)));
            holder.ll_option_c.setBackgroundTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.white)));
            holder.ll_option_d.setBackgroundTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.white)));
        }
    }

    private void setColors(ViewHolder holder, int position) {
        Log.e("color position", position + "");
        if (mcqResponses.get(position).selected_answer == 1) {
            if (mcqResponses.get(position).correct_option.equalsIgnoreCase("1")) {
                holder.ll_option_a.setStrokeColor(mContext.getResources().getColor(R.color.green));
            } else {
                holder.ll_option_a.setCardBackgroundColor(mContext.getResources().getColor(R.color.red));
                if (mcqResponses.get(position).correct_option.equalsIgnoreCase("2")) {
                    holder.ll_option_b.setStrokeColor(mContext.getResources().getColor(R.color.green));
                } else if (mcqResponses.get(position).correct_option.equalsIgnoreCase("3")) {
                    holder.ll_option_c.setStrokeColor(mContext.getResources().getColor(R.color.green));
                } else if (mcqResponses.get(position).correct_option.equalsIgnoreCase("4")) {
                    holder.ll_option_d.setStrokeColor(mContext.getResources().getColor(R.color.green));
                }
            }
        } else if (mcqResponses.get(position).selected_answer == 2) {
            if (mcqResponses.get(position).correct_option.equalsIgnoreCase("2")) {
                holder.ll_option_b.setStrokeColor(mContext.getResources().getColor(R.color.green));
            } else {
                holder.ll_option_b.setStrokeColor(mContext.getResources().getColor(R.color.red));
                if (mcqResponses.get(position).correct_option.equalsIgnoreCase("1")) {
                    holder.ll_option_a.setStrokeColor(mContext.getResources().getColor(R.color.green));
                } else if (mcqResponses.get(position).correct_option.equalsIgnoreCase("3")) {
                    holder.ll_option_c.setStrokeColor(mContext.getResources().getColor(R.color.green));
                } else if (mcqResponses.get(position).correct_option.equalsIgnoreCase("4")) {
                    holder.ll_option_d.setStrokeColor(mContext.getResources().getColor(R.color.green));
                }
            }
        } else if (mcqResponses.get(position).selected_answer == 3) {
            if (mcqResponses.get(position).correct_option.equalsIgnoreCase("3")) {
                holder.ll_option_c.setStrokeColor(mContext.getResources().getColor(R.color.green));
            } else {
                holder.ll_option_c.setStrokeColor(mContext.getResources().getColor(R.color.red));
                if (mcqResponses.get(position).correct_option.equalsIgnoreCase("2")) {
                    holder.ll_option_b.setStrokeColor(mContext.getResources().getColor(R.color.green));
                } else if (mcqResponses.get(position).correct_option.equalsIgnoreCase("1")) {
                    holder.ll_option_a.setStrokeColor(mContext.getResources().getColor(R.color.green));
                } else if (mcqResponses.get(position).correct_option.equalsIgnoreCase("4")) {
                    holder.ll_option_d.setStrokeColor(mContext.getResources().getColor(R.color.green));
                }
            }
        } else if (mcqResponses.get(position).selected_answer == 4) {
            if (mcqResponses.get(position).correct_option.equalsIgnoreCase("4")) {
                holder.ll_option_d.setStrokeColor(mContext.getResources().getColor(R.color.green));
            } else {
                holder.ll_option_d.setStrokeColor(mContext.getResources().getColor(R.color.red));
                if (mcqResponses.get(position).correct_option.equalsIgnoreCase("1")) {
                    holder.ll_option_a.setStrokeColor(mContext.getResources().getColor(R.color.green));
                } else if (mcqResponses.get(position).correct_option.equalsIgnoreCase("3")) {
                    holder.ll_option_c.setStrokeColor(mContext.getResources().getColor(R.color.green));
                } else if (mcqResponses.get(position).correct_option.equalsIgnoreCase("2")) {
                    holder.ll_option_b.setStrokeColor(mContext.getResources().getColor(R.color.green));
                }
            }
        } else {
            holder.ll_option_a.setStrokeColor(mContext.getResources().getColor(R.color.white));
            holder.ll_option_b.setStrokeColor(mContext.getResources().getColor(R.color.white));
            holder.ll_option_c.setStrokeColor(mContext.getResources().getColor(R.color.white));
            holder.ll_option_d.setStrokeColor(mContext.getResources().getColor(R.color.white));

            holder.ll_option_a.setBackgroundTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.white)));
            holder.ll_option_b.setBackgroundTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.white)));
            holder.ll_option_c.setBackgroundTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.white)));
            holder.ll_option_d.setBackgroundTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.white)));
        }
    }


    private void setAnswer(ViewHolder holder, int position, int selected_answer) {
        if (mcqResponses.get(position).isSelected) {
            if (mcqResponses.get(position).correct_option.equalsIgnoreCase(String.valueOf(selected_answer))) {
                total_correct_answer = total_correct_answer + 1;
                EducationApplication.editor.putInt("total_mcq_correct_answer" , total_correct_answer).apply();
                if (selected_answer == 1) {
                    holder.ll_option_a.setStrokeColor(mContext.getResources().getColor(R.color.green));
                    holder.ll_option_a.setBackgroundTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.light_green)));
                } else if (selected_answer == 2) {
                    holder.ll_option_b.setStrokeColor(mContext.getResources().getColor(R.color.green));
                    holder.ll_option_b.setBackgroundTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.light_green)));
                }
                if (selected_answer == 3) {
                    holder.ll_option_c.setStrokeColor(mContext.getResources().getColor(R.color.green));
                    holder.ll_option_c.setBackgroundTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.light_green)));
                } else if (selected_answer == 4) {
                    holder.ll_option_d.setStrokeColor(mContext.getResources().getColor(R.color.green));
                    holder.ll_option_d.setBackgroundTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.light_green)));
                }
            } else {
                if (selected_answer == 1) {
                    holder.ll_option_a.setStrokeColor(mContext.getResources().getColor(R.color.red));
                    holder.ll_option_a.setBackgroundTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.colorAccent)));
                } else if (selected_answer == 2) {
                    holder.ll_option_b.setStrokeColor(mContext.getResources().getColor(R.color.red));
                    holder.ll_option_b.setBackgroundTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.colorAccent)));
                }
                if (selected_answer == 3) {
                    holder.ll_option_c.setStrokeColor(mContext.getResources().getColor(R.color.red));
                    holder.ll_option_c.setBackgroundTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.colorAccent)));
                } else if (selected_answer == 4) {
                    holder.ll_option_d.setStrokeColor(mContext.getResources().getColor(R.color.red));
                    holder.ll_option_d.setBackgroundTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.colorAccent)));
                }

                if (mcqResponses.get(position).correct_option.equalsIgnoreCase(String.valueOf(1))) {
                    holder.ll_option_a.setStrokeColor(mContext.getResources().getColor(R.color.green));
                    holder.ll_option_a.setBackgroundTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.light_green)));
                } else if (mcqResponses.get(position).correct_option.equalsIgnoreCase(String.valueOf(2))) {
                    holder.ll_option_b.setStrokeColor(mContext.getResources().getColor(R.color.green));
                    holder.ll_option_b.setBackgroundTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.light_green)));
                }
                if (mcqResponses.get(position).correct_option.equalsIgnoreCase(String.valueOf(3))) {
                    holder.ll_option_c.setStrokeColor(mContext.getResources().getColor(R.color.green));
                    holder.ll_option_c.setBackgroundTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.light_green)));
                } else if (mcqResponses.get(position).correct_option.equalsIgnoreCase(String.valueOf(4))) {
                    holder.ll_option_d.setStrokeColor(mContext.getResources().getColor(R.color.green));
                    holder.ll_option_d.setBackgroundTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.light_green)));
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return mcqResponses.size();
    }
}