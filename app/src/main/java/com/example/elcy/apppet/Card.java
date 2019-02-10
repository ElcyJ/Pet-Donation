package com.example.elcy.apppet;

public class Card {
    private String petId;
    private String ownerId;
    private String name;

    public Card(String petId, String ownerId, String name) {
        this.petId = petId;
        this.ownerId = ownerId;
        this.name = name;
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


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
