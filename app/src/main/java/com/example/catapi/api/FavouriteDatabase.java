package com.example.catapi.api;

import com.bumptech.glide.Glide;
import com.example.catapi.modal.CatsModal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FavouriteDatabase {

    public static HashMap<String, CatsModal> favouriteCatsMap;

    static{
        favouriteCatsMap = new HashMap<>();
    }

    public static ArrayList<CatsModal> getFavouriteCats(){
        ArrayList<CatsModal> favouriteCatsArray = new ArrayList<>();
        for (Map.Entry<String, CatsModal> favouriteCat :
                favouriteCatsMap.entrySet()) {
            favouriteCatsArray.add(favouriteCat.getValue());
        }
        return favouriteCatsArray;
    }

    public static void addToFavourites(CatsModal newCat){
        String catId = newCat.getId();

        //Check If Cat Exists
        if (!favouriteCatsMap.containsKey(catId)){
            favouriteCatsMap.put(catId, newCat);
            System.out.println(newCat.getName() + " added to favourites.");
        } else {
            System.out.println("Cat already a favourite.");
        }
    }

    public static void removeFromFavourite(CatsModal newCat){
        String catId = newCat.getId();
        //Check If Cat Exists
        if (favouriteCatsMap.containsKey(catId)){
            favouriteCatsMap.remove(catId);
            System.out.println(newCat.getName() + " removed from favourites.");
        } else {
            System.out.println("Cat already not loved.");
        }

    }

}