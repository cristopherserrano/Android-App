package com.bitebuilder;

public class FoodItem {
    private int image;
    private String name;

    public FoodItem(int image, String name) {
        super();
        this.image = image;
        this.name = name;
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
}