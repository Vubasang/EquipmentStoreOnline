package com.example.EquipmentStoreOnline.model;

public class Typeproduct {
    public int Id;
    public String Nametypeproduct;
    public String Imagetypeproduct;

    public Typeproduct(int id, String nametypeproduct, String imagetypeproduct) {
        Id = id;
        Nametypeproduct = nametypeproduct;
        Imagetypeproduct = imagetypeproduct;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNametypeproduct() {
        return Nametypeproduct;
    }

    public void setNametypeproduct(String nametypeproduct) {
        Nametypeproduct = nametypeproduct;
    }

    public String getImagetypeproduct() {
        return Imagetypeproduct;
    }

    public void setImagetypeproduct(String imagetypeproduct) {
        Imagetypeproduct = imagetypeproduct;
    }
}
