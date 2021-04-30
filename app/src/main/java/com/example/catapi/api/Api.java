package com.example.catapi.api;

import com.example.catapi.modal.CatsModal;

import java.util.ArrayList;

public class Api {
    ArrayList<CatsModal> breeds;
    String url;

    public ArrayList<CatsModal> getBreeds() {
        return breeds;
    }

    public String getUrl() {
        return url;
    }

}
