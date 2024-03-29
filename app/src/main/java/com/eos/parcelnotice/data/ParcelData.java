package com.eos.parcelnotice.data;

import java.util.Date;

public class ParcelData {
    private String recipeient;
    private String taker;
    private String status;
    private String createdAt;
    private String updatedAt;
    private String userId;

    public String getRecipeient() {
        return recipeient;
    }

    public void setRecipeient(String recipeient) {
        this.recipeient = recipeient;
    }

    public String getTaker() {
        return taker;
    }

    public void setTaker(String taker) {
        this.taker = taker;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
