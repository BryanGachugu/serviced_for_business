package com.gachugusville.servicedforbusiness.Utils;

public class ChatModel {
    String customer_url, name, Uid;
    float distance;

    public ChatModel() {
    }

    public ChatModel(String customer_url, String name, float distance, String uid) {
        this.customer_url = customer_url;
        this.name = name;
        this.distance = distance;
        Uid = uid;
    }

    public String getCustomer_url() {
        return customer_url;
    }

    public void setCustomer_url(String customer_url) {
        this.customer_url = customer_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getDistance() {
        return distance;
    }

    public void setPhone(float distance) {
        this.distance = distance;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }
}
