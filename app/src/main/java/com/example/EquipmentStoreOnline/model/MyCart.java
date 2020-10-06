package com.example.EquipmentStoreOnline.model;

public class MyCart {
    public int idproduct;
    public String namrproduct;
    public long priceproduct;
    public String imageproduct;
    public int amountproduct;

    public MyCart(int idproduct, String namrproduct, long priceproduct, String imageproduct, int amountproduct) {
        this.idproduct = idproduct;
        this.namrproduct = namrproduct;
        this.priceproduct = priceproduct;
        this.imageproduct = imageproduct;
        this.amountproduct = amountproduct;
    }

    public int getIdproduct() {
        return idproduct;
    }

    public void setIdproduct(int idproduct) {
        this.idproduct = idproduct;
    }

    public String getNamrproduct() {
        return namrproduct;
    }

    public void setNamrproduct(String namrproduct) {
        this.namrproduct = namrproduct;
    }

    public long getPriceproduct() {
        return priceproduct;
    }

    public void setPriceproduct(long priceproduct) {
        this.priceproduct = priceproduct;
    }

    public String getImageproduct() {
        return imageproduct;
    }

    public void setImageproduct(String imageproduct) {
        this.imageproduct = imageproduct;
    }

    public int getAmountproduct() {
        return amountproduct;
    }

    public void setAmountproduct(int amountproduct) {
        this.amountproduct = amountproduct;
    }
}
