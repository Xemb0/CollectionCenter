package com.test.samplecollection;

public class Test {
    private  int mrp;
    private int inclusions;
    private String name;
    private int price;
    private String description;

    private String tag;

    public Test() {
        // Default constructor required for Firestore
    }

    public Test(String name, int price, String description,int mrp,int inclusions,String tag) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.mrp = mrp;
        this.inclusions = inclusions;
        this.tag = tag;
    }

    // Getters and setters for each field
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }
    public int getInclusions(){
        return inclusions;
    }
    public int getMrp(){
        return  mrp;
    }
    public String getTag(){
        return tag;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
