package com.example.catapi.modal;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CatsModal {
    String id;
    String name;
    Cat url;
    String imageUrl;
    String description;
    Weight weight;
    String temperament;
    String origin;
    @SerializedName("life_span")
    String lifeSpan;
    @SerializedName("dog_friendly")
    String dogfriend;
    @SerializedName("wikipedia_url")
    String wikiUrl;

    public boolean favourite;

    public CatsModal(String id,Cat url, String name, String description, Weight weight, String temperament, String origin, String lifeSpan, String dogfriend, String wikiUrl) {
        this.id = id;
        this.url =url;
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.temperament = temperament;
        this.origin = origin;
        this.lifeSpan = lifeSpan;
        this.dogfriend = dogfriend;
        this.wikiUrl = wikiUrl;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getTemperament() {
        return temperament;
    }

    public String getOrigin() {
        return origin;
    }

    public String getLifeSpan() {
        return lifeSpan;
    }

    public String getDogfriend() {
        return dogfriend;
    }

    public String getWikiUrl() {
        return wikiUrl;
    }

    public Weight getWeight() {
        return weight;
    }

    public Cat getUrl() {
        return url;
    }
    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                "image='" + url + '\'' +
                '}';
    }



    public class Weight{
        String metric;

        public Weight(String metric) {
            this.metric = metric;
        }

        public String getMetric() {
            return metric;
        }
    }


    public class Cat {
        String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
