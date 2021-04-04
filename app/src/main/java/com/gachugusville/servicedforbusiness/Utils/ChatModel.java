package com.gachugusville.servicedforbusiness.Utils;

public class ChatModel {
    String customer_url, customer_email, phone, Uid;

    public ChatModel() {
    }

    public ChatModel(String customer_url, String customer_email, String phone, String uid) {
        this.customer_url = customer_url;
        this.customer_email = customer_email;
        this.phone = phone;
        Uid = uid;
    }

    public String getCustomer_url() {
        return customer_url;
    }

    public void setCustomer_url(String customer_url) {
        this.customer_url = customer_url;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }
}
