package com.example.elcy.apppet;

public class FavoritesObject {
    private String favoriteId;
    private String favoriteImamgeUrl;
    private String favoriteSex;
    private String favoriteAge;

    public FavoritesObject(String favoriteId, String favoriteImamgeUrl, String favoriteSex, String favoriteAge, String favoriteOwnerId) {
        this.favoriteId = favoriteId;
        this.favoriteImamgeUrl = favoriteImamgeUrl;
        this.favoriteSex = favoriteSex;
        this.favoriteAge = favoriteAge;
        this.favoriteOwnerId = favoriteOwnerId;
    }

    public String getFavoriteImamgeUrl() {
        return favoriteImamgeUrl;
    }

    public void setFavoriteImamgeUrl(String favoriteImamgeUrl) {
        this.favoriteImamgeUrl = favoriteImamgeUrl;
    }

    public String getFavoriteSex() {
        return favoriteSex;
    }

    public void setFavoriteSex(String favoriteSex) {
        this.favoriteSex = favoriteSex;
    }

    public String getFavoriteAge() {
        return favoriteAge;
    }

    public void setFavoriteAge(String favoriteAge) {
        this.favoriteAge = favoriteAge;
    }

    public String getFavoriteOwnerId() {
        return favoriteOwnerId;
    }

    public void setFavoriteOwnerId(String favoriteOwnerId) {
        this.favoriteOwnerId = favoriteOwnerId;
    }

    private String favoriteOwnerId;


    public String getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(String favoriteId) {
        this.favoriteId = favoriteId;
    }
}
