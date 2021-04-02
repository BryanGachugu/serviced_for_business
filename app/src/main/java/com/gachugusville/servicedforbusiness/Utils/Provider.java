package com.gachugusville.servicedforbusiness.Utils;

import java.util.List;

import ca.antonious.materialdaypicker.MaterialDayPicker;

public class Provider {

    private static Provider provider;

    public Provider() {

    }

    public static Provider getInstance() {
        if (provider == null) {
            provider = new Provider();
        }
        return provider;
    }

    String user_name, brand_name, service_category, service_identity, personal_description,
            short_note_to_users, phone, ref_url1, ref_url2, email, country;
    List<Reviews> reviews;
    List<String> provider_skills;
    List<MaterialDayPicker.Weekday> days_available;
    String profile_pic_url = "";
    boolean available_country_wide, always_available, isGoogleAuth, isRegistrationFinished;
    int total_rating = 0;
    float rating = 0f;
    int time_available_from = 0, time_available_to = 0;
    int reach_in_distance = 0;
    int jobs_done = 0, account_views = 0, number_of_reviews = 0, number_of_profile_likes = 0;
    double estimated_earnings = 0, latitude = 0, longitude = 0;

    private Provider(String user_name, String brand_name, String service_category, String service_identity, String personal_description,
                     String short_note_to_users, String phone, double latitude, double longitude, String profile_pic_url, String ref_url1, String ref_url2, String email, String country, List<Reviews> reviews, List<String> provider_skills,
                     List<MaterialDayPicker.Weekday> days_available, boolean available_country_wide,
                     boolean always_available, boolean isGoogleAuth, boolean isRegistrationFinished, int total_rating, float rating, int time_available_from, int time_available_to, int reach_in_distance,
                     int jobs_done, int account_views, int number_of_reviews, int number_of_profile_likes, double estimated_earnings) {
        this.user_name = user_name;
        this.brand_name = brand_name;
        this.service_category = service_category;
        this.service_identity = service_identity;
        this.personal_description = personal_description;
        this.short_note_to_users = short_note_to_users;
        this.phone = phone;
        this.latitude = latitude;
        this.profile_pic_url = profile_pic_url;
        this.ref_url1 = ref_url1;
        this.ref_url2 = ref_url2;
        this.email = email;
        this.country = country;
        this.longitude = longitude;
        this.reviews = reviews;
        this.provider_skills = provider_skills;
        this.days_available = days_available;
        this.available_country_wide = available_country_wide;
        this.always_available = always_available;
        this.isGoogleAuth = isGoogleAuth;
        this.isRegistrationFinished = isRegistrationFinished;
        this.total_rating = total_rating;
        this.rating = rating;
        this.time_available_from = time_available_from;
        this.time_available_to = time_available_to;
        this.reach_in_distance = reach_in_distance;
        this.jobs_done = jobs_done;
        this.account_views = account_views;
        this.number_of_reviews = number_of_reviews;
        this.number_of_profile_likes = number_of_profile_likes;
        this.estimated_earnings = estimated_earnings;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getService_category() {
        return service_category;
    }

    public void setService_category(String service_category) {
        this.service_category = service_category;
    }

    public String getService_identity() {
        return service_identity;
    }

    public void setService_identity(String service_identity) {
        this.service_identity = service_identity;
    }

    public String getPersonal_description() {
        return personal_description;
    }

    public void setPersonal_description(String personal_description) {
        this.personal_description = personal_description;
    }

    public String getShort_note_to_users() {
        return short_note_to_users;
    }

    public void setShort_note_to_users(String short_note_to_users) {
        this.short_note_to_users = short_note_to_users;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getProfile_pic_url() {
        return profile_pic_url;
    }

    public void setProfile_pic_url(String profile_pic_url) {
        this.profile_pic_url = profile_pic_url;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<Reviews> getReviews() {
        return reviews;
    }

    public void setReviews(List<Reviews> reviews) {
        this.reviews = reviews;
    }

    public List<String> getProvider_skills() {
        return provider_skills;
    }

    public void setProvider_skills(List<String> provider_skills) {
        this.provider_skills = provider_skills;
    }

    public String getRef_url1() {
        return ref_url1;
    }

    public void setRef_url1(String ref_url1) {
        this.ref_url1 = ref_url1;
    }

    public String getRef_url2() {
        return ref_url2;
    }

    public void setRef_url2(String ref_url2) {
        this.ref_url2 = ref_url2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<MaterialDayPicker.Weekday> getDays_available() {
        return days_available;
    }

    public void setDays_available(List<MaterialDayPicker.Weekday> days_available) {
        this.days_available = days_available;
    }

    public boolean isAvailable_country_wide() {
        return available_country_wide;
    }

    public void setAvailable_country_wide(boolean available_country_wide) {
        this.available_country_wide = available_country_wide;
    }

    public boolean isAlways_available() {
        return always_available;
    }

    public void setAlways_available(boolean always_available) {
        this.always_available = always_available;
    }

    public boolean isGoogleAuth() {
        return isGoogleAuth;
    }

    public void setGoogleAuth(boolean googleAuth) {
        isGoogleAuth = googleAuth;
    }

    public boolean isRegistrationFinished() {
        return isRegistrationFinished;
    }

    public void setRegistrationFinished(boolean registrationFinished) {
        isRegistrationFinished = registrationFinished;
    }

    public int getTotal_rating() {
        return total_rating;
    }

    public void setTotal_rating(int total_rating) {
        this.total_rating = total_rating;
    }

    public float getRating() {

        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public float getTime_available_from() {
        return time_available_from;
    }

    public void setTime_available_from(int time_available_from) {
        this.time_available_from = time_available_from;
    }

    public float getTime_available_to() {
        return time_available_to;
    }

    public void setTime_available_to(int time_available_to) {
        this.time_available_to = time_available_to;
    }

    public float getReach_in_distance() {
        return reach_in_distance;
    }

    public void setReach_in_distance(int reach_in_distance) {
        this.reach_in_distance = reach_in_distance;
    }

    public int getNumber_of_reviews() {
        return number_of_reviews;
    }

    public void setNumber_of_reviews(int number_of_reviews) {
        this.number_of_reviews = number_of_reviews;
    }

    public int getJobs_done() {
        return jobs_done;
    }

    public void setJobs_done(int jobs_done) {
        this.jobs_done = jobs_done;
    }

    public int getAccount_views() {
        return account_views;
    }

    public void setAccount_views(int account_views) {
        this.account_views = account_views;
    }

    public int getNumber_of_profile_likes() {
        return number_of_profile_likes;
    }

    public void setNumber_of_profile_likes(int number_of_profile_likes) {
        this.number_of_profile_likes = number_of_profile_likes;
    }

    public double getEstimated_earnings() {
        return estimated_earnings;
    }

    public void setEstimated_earnings(double estimated_earnings) {
        this.estimated_earnings = estimated_earnings;
    }
}
