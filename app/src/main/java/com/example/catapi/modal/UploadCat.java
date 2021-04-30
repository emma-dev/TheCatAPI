package com.example.catapi.modal;

import com.google.gson.annotations.SerializedName;

public class UploadCat {
    @SerializedName("file")
    String file;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    //  @SerializedName("sub_id")
    String sub_id;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getSub_id() {
        return sub_id;
    }

    public void setSub_id(String sub_id) {
        this.sub_id = sub_id;
    }
}
