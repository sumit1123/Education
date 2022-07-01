package com.example.education.response;

public class RegisterResponse {

    private boolean Status;
    private String Massage;
    private String username;
    private String courseId;
    private String sub_course_id;
    private String MemberId;
    private String email_id;

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    public String getMassage() {
        return Massage;
    }

    public void setMassage(String massage) {
        Massage = massage;
    }

    public String getSub_course_id() {
        return sub_course_id;
    }

    public void setSub_course_id(String sub_course_id) {
        this.sub_course_id = sub_course_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getMemberId() {
        return MemberId;
    }

    public void setMemberId(String memberId) {
        MemberId = memberId;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }
}
