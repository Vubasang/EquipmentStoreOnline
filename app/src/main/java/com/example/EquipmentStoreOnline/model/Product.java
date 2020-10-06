package com.example.EquipmentStoreOnline.model;

import java.io.Serializable;

public class Product implements Serializable {
    public int ID;
    public String Nameproduct;
    public Integer Priceproduct;
    public String Imageproduct;
    public String Descriptionproduct;
    public int IDProduct;

    public Product(int ID, String nameproduct, Integer priceproduct, String imageproduct, String descriptionproduct, int IDProduct) {
        this.ID = ID;
        Nameproduct = nameproduct;
        Priceproduct = priceproduct;
        Imageproduct = imageproduct;
        Descriptionproduct = descriptionproduct;
        this.IDProduct = IDProduct;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNameproduct() {
        return Nameproduct;
    }

    public void setNameproduct(String nameproduct) {
        Nameproduct = nameproduct;
    }

    public Integer getPriceproduct() {
        return Priceproduct;
    }

    public void setPriceproduct(Integer priceproduct) {
        Priceproduct = priceproduct;
    }

    public String getImageproduct() {
        return Imageproduct;
    }

    public void setImageproduct(String imageproduct) {
        Imageproduct = imageproduct;
    }

    public String getDescriptionproduct() {
        return Descriptionproduct;
    }

    public void setDescriptionproduct(String descriptionproduct) {
        Descriptionproduct = descriptionproduct;
    }

    public int getIDProduct() {
        return IDProduct;
    }

    public void setIDProduct(int IDProduct) {
        this.IDProduct = IDProduct;
    }
}
