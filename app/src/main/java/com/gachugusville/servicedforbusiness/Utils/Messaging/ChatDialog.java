package com.gachugusville.servicedforbusiness.Utils.Messaging;

import com.stfalcon.chatkit.commons.models.IDialog;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;

import java.util.List;

public class ChatDialog implements IDialog {

    String id, dialogPhoto, dialogName;
    IMessage last_message;
    int unreadCounts;

    public ChatDialog() {
    }

    public ChatDialog(String id, String dialogPhoto, String dialogName, IMessage last_message, int unreadCounts) {
        this.id = id;
        this.dialogPhoto = dialogPhoto;
        this.dialogName = dialogName;
        this.last_message = last_message;
        this.unreadCounts = unreadCounts;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getDialogPhoto() {
        return id;
    }

    @Override
    public String getDialogName() {
        return dialogName;
    }

    @Override
    public List<? extends IUser> getUsers() {
        return null;
    }

    @Override
    public IMessage getLastMessage() {
        return last_message;
    }

    @Override
    public void setLastMessage(IMessage message) {
        this.last_message = message;
    }

    @Override
    public int getUnreadCount() {
        return 0;
    }
}
