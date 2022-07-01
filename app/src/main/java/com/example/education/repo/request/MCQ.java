package com.example.education.repo.request;

public class MCQ {

    private String quesiton;
    private String correct_answer;
    private String select_answer;
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
}
