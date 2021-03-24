package com.gachugusville.servicedforbusiness.Utils;

public class ProfilePictureUpload {
    private String imageUri;

    public ProfilePictureUpload() {
        //empty constructor needed for Firebase
    }

    public ProfilePictureUpload(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
