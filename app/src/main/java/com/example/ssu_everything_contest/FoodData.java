package com.example.ssu_everything_contest;

import android.widget.ImageView;

public class FoodData {
    private int foodImage;
    private String foodName;
    private String foodExplanation;



    public FoodData(int foodImage, String foodName, String foodExplanation) {
        this.foodImage=foodImage;
        this.foodName=foodName;
        this.foodExplanation=foodExplanation;
    }

    public int getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(int foodImage) {
        this.foodImage = foodImage;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodExplanation() {
        return foodExplanation;
    }

    public void setFoodExplanation(String foodExplanation) {
        this.foodExplanation = foodExplanation;
    }


}
