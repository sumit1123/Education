package com.example.education.response;

// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString), Root.class); */
public class LoginResponse {
    public String member_id;
    public String user_id;
    public String name;
    public String phone;
    public String sub_course_id;
    public String email_id;
    public String password;
    public String sponser_id;
    public String user_type;
    public String date_time;
    public Object topup_date;
    public String purchase_status;
    public Object center_code;
    public Object bank;
    public Object branch;
    public Object account_number;
    public Object ifsc;
    public Object account_name;
    public Object pan_card;
    public Object aadhar_card;
    public Object date_time_bank_update;
    public Object fist_update_date;
    public Object country;
    public Object state;
    public Object city;
    public Object address;
    public Object district_id;
    public String category_id;
    public String course_id;
    public Object pin_code;
    public Object referal_code;
    public Object update_date;
    public String account_status;
    public Object account_status_date;
    public String wallet;
    public String upgradeStatus;
}

