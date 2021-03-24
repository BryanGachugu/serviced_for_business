package com.gachugusville.servicedforbusiness.Utils;

public class Reviews {

    String reviewer_profile_pic_url, review_date, reviewer_name, review_string;
    float review_rating;

    public Reviews() {
        //for firebase
    }

    public Reviews(String reviewer_profile_pic_url, String review_date, String reviewer_name, String review_string, float review_rating) {
        this.reviewer_profile_pic_url = reviewer_profile_pic_url;
        this.review_date = review_date;
        this.reviewer_name = reviewer_name;
        this.review_string = review_string;
        this.review_rating = review_rating;
    }

    public String getReviewer_profile_pic_url() {
        return reviewer_profile_pic_url;
    }

    public void setReviewer_profile_pic_url(String reviewer_profile_pic_url) {
        this.reviewer_profile_pic_url = reviewer_profile_pic_url;
    }

    public String getReview_date() {
        return review_date;
    }

    public void setReview_date(String review_date) {
        this.review_date = review_date;
    }

    public String getReviewer_name() {
        return reviewer_name;
    }

    public void setReviewer_name(String reviewer_name) {
        this.reviewer_name = reviewer_name;
    }

    public String getReview_string() {
        return review_string;
    }

    public void setReview_string(String review_string) {
        this.review_string = review_string;
    }

    public float getReview_rating() {
        return review_rating;
    }

    public void setReview_rating(float review_rating) {
        this.review_rating = review_rating;
    }
}
