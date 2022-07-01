package com.example.education.repo.request;

import java.io.Serializable;

public class Exam implements Serializable {

    private String quesiton;
    private String correct_answer;
    private String correct_answer_val;
    private String select_answer;
    private String skipped = "0";
    private int select_position = 0;

    public String getQuesiton() {
        return quesiton;
    }

    public void setQuesiton(String quesiton) {
        this.quesiton = quesiton;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public String getSelect_answer() {
        return select_answer;
    }

    public void setSelect_answer(String select_answer) {
        this.select_answer = select_answer;
    }

    public int getSelect_position() {
        return select_position;
    }

    public void setSelect_position(int select_position) {
        this.select_position = select_position;
    }

    public String getCorrect_answer_val() {
        return correct_answer_val;
    }

    public void setCorrect_answer_val(String correct_answer_val) {
        this.correct_answer_val = correct_answer_val;
    }

    public String getSkipped() {
        return skipped;
    }

    public void setSkipped(String skipped) {
        this.skipped = skipped;
    }
}
