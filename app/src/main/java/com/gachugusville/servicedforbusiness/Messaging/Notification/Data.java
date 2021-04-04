package com.gachugusville.servicedforbusiness.Messaging.Notification;

public class Data {
    private String user, body, title, sent;

    private int icon;

    public Data() {
    }

    public Data(String user, String body, String title, String sent, int icon) {
        this.user = user;
        this.body = body;
        this.title = title;
        this.sent = sent;
        this.icon = icon;
    }


}
