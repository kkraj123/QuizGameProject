package com.example.iqlevelgame.model;

public class CatogoryModel {
    private  String catogoryId,catogoryName,catogoryImage;

    public CatogoryModel() {
    }

    public CatogoryModel(String catogoryId, String catogoryName, String catogoryImage) {
        this.catogoryId = catogoryId;
        this.catogoryName = catogoryName;
        this.catogoryImage = catogoryImage;
    }

    public String getCatogoryId() {
        return catogoryId;
    }

    public void setCatogoryId(String catogoryId) {
        this.catogoryId = catogoryId;
    }

    public String getCatogoryName() {
        return catogoryName;
    }

    public void setCatogoryName(String catogoryName) {
        this.catogoryName = catogoryName;
    }

    public String getCatogoryImage() {
        return catogoryImage;
    }

    public void setCatogoryImage(String catogoryImage) {
        this.catogoryImage = catogoryImage;
    }
}
