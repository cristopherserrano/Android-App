package com.bitebuilder;

import com.google.firebase.storage.StorageReference;

public class FoodItem {
    private int image = 0;
    private String name = "", imageUrl = "";
    private String[] ingredients;
    private StorageReference imageReference;

    public FoodItem(int image, String name) {
        super();
        this.image = image;
        this.name = name;
    }

    public FoodItem(String name, String imageUrl, String[] ingredients) {
        super();
        this.name = name;
        this.imageUrl = imageUrl;
        this.ingredients = ingredients;
    }


    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String title) {
        this.name = name;
    }

    public String getImageUrl() { return imageUrl; }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String[] getIngredients() { return ingredients; }

    public void setIngredients(String[] ingredients) { this.ingredients = ingredients; }

    public StorageReference getImageReference() { return imageReference; }

    public void setImageReference(StorageReference imageReference) { this.imageReference = imageReference; }
}