package com.example.elcy.apppet;

import android.widget.ArrayAdapter;

public class Card {
    private String petType;
    private String petId;
    private String ownerId;
    private String sex;
    private String petImageUrl;

    public Card(String petType, String petId, String ownerId, String name, String petImageUrl) {
        this.petType = petType;
        this.petId = petId;
        this.ownerId = ownerId;
        this.sex = name;
        this.petImageUrl = petImageUrl;
    }

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }


    public String getSex() {
        return sex;
    }

    public void setSex(String name) {
        this.sex = name;
    }

    public String getPetImageUrl() {
        return petImageUrl;
    }

    public void setPetImageUrl(String petImageUrl) {
        this.petImageUrl = petImageUrl;
    }

}
