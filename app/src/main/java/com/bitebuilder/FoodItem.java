package com.bitebuilder;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.firebase.storage.StorageReference;

public class FoodItem implements Parcelable {
    private int image = 0;
    private boolean selected = false;
    private String name = "", imageUrl = "";
    private String[] ingredients;
    private StorageReference imageReference;

    public FoodItem(Parcel in) {
        String[] ingredients = new String[20];
        in.readStringArray(ingredients);
        this.name = in.readString();
        this.imageUrl = in.readString();
    }

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

    public boolean getSelected() { return selected; }

    public void toggleSelected() { this.selected = !this.selected; }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(ingredients);
        dest.writeString(name);
        dest.writeString(imageUrl);
    }

    public static final Creator<FoodItem> CREATOR = new Creator<FoodItem>() {
        @Override
        public FoodItem createFromParcel(Parcel in) {
            return new FoodItem(in);
        }
        @Override
        public FoodItem[] newArray(int size) {
            return new FoodItem[size];
        }
    };
}