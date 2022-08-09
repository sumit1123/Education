package com.example.education.response;

import android.os.Parcel;
import android.os.Parcelable;

public class CourseResponse implements Parcelable {
    public String course_id;
    public String category_id;
    public String course_name;
    public String course_price;
    public String course_details;
    public String video_id;
    public String hash_key;
    public String video_url;
    public String course_duration;
    public String date_time;
    public Object course_color;
    public String course_image;
    public String distrbutor_income;
    public String partner_income;
    public String status;
    public String gst;
    public String referrerIncome;
    public boolean isSelected;

    public String point_one;
    public String point_two;
    public String point_three;
    public String point_four;
    public String point_five;
    public String point_six;
    public String point_seven;
    public String point_eight;
    public String point_nine;
    public String point_ten;

    public String pointone_image;
    public String pointtwo_image;
    public String pointthree_image;
    public String pointfour_image;
    public String pointfive_image;
    public String pointsix_image;
    public String pointseven_image;
    public String pointeight_image;
    public String pointnine_image;
    public String pointten_image;

    protected CourseResponse(Parcel in) {
        course_id = in.readString();
        category_id = in.readString();
        course_name = in.readString();
        course_price = in.readString();
        course_duration = in.readString();
        date_time = in.readString();
        course_image = in.readString();
        distrbutor_income = in.readString();
        partner_income = in.readString();
        status = in.readString();
        gst = in.readString();
        referrerIncome = in.readString();
        isSelected = in.readByte() != 0;
        video_id = in.readString();
        course_details = in.readString();
        point_one = in.readString();
        point_two = in.readString();
        point_three = in.readString();
        point_four = in.readString();
        point_five = in.readString();
        point_six = in.readString();
        point_seven = in.readString();
        point_eight = in.readString();
        point_nine = in.readString();
        point_ten = in.readString();
        hash_key =in.readString();
        video_url = in.readString();

    }

    public static final Creator<CourseResponse> CREATOR = new Creator<CourseResponse>() {
        @Override
        public CourseResponse createFromParcel(Parcel in) {
            return new CourseResponse(in);
        }

        @Override
        public CourseResponse[] newArray(int size) {
            return new CourseResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(course_id);
        parcel.writeString(category_id);
        parcel.writeString(course_name);
        parcel.writeString(course_price);
        parcel.writeString(course_duration);
        parcel.writeString(date_time);
        parcel.writeString(course_image);
        parcel.writeString(distrbutor_income);
        parcel.writeString(partner_income);
        parcel.writeString(status);
        parcel.writeString(gst);
        parcel.writeString(referrerIncome);
        parcel.writeByte((byte) (isSelected ? 1 : 0));
        parcel.writeString(video_id);
        parcel.writeString(course_details);
        parcel.writeString(point_one);
        parcel.writeString(point_two);
        parcel.writeString(point_three);
        parcel.writeString(point_four);
        parcel.writeString(point_five);
        parcel.writeString(point_six);
        parcel.writeString(point_seven);
        parcel.writeString(point_eight);
        parcel.writeString(point_nine);
        parcel.writeString(point_ten);
        parcel.writeString(hash_key);
        parcel.writeString(video_url);
    }
}