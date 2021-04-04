package com.gachugusville.servicedforbusiness.Utils.Messaging;

import com.stfalcon.chatkit.commons.models.IUser;

public class Author implements IUser {
    String id, name, avatar;

    public Author() {
    }

    public Author(String id, String name, String avatar) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getAvatar() {
        return null;
    }
}
