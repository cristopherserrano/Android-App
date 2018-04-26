package com.bitebuilder;

import com.google.firebase.storage.StorageReference;

public class FoodItem {
    private int image = 0;
    private String name = "", imageUrl = "";
    private String[] ingredients;
    private StorageReference imageReference;

<<<<<<< Updated upstream
=======
    public FoodItem(Parcel in) {
        super();
        String[] ingredients = new String[20];
        in.readStringArray(ingredients);
        this.ingredients = ingredients;
        this.name = in.readString();
        this.imageUrl = in.readString();
    }

>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
<<<<<<< Updated upstream
=======
=======
>>>>>>> Stashed changes
    public FoodItem(String name, String imageUrl, String ingredients) {
        super();
        this.name = name;
        this.imageUrl = imageUrl;
        this.ingredients = new String[20];
        for(int i = 0; i < ingredients.split(",").length; i++) {
            this.ingredients[i] = ingredients.split(",")[i];
        }
    }
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes

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