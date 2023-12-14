package com.test.samplecollection;

public class Test {
    private  Double mrp;
    private String inclusions;
    private String name;
    private double price;
    private String description;

    public Test() {
        // Default constructor required for Firestore
    }

    public Test(String name, double price, String description,Double mrp,String inclusions) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.mrp = mrp;
        this.inclusions = inclusions;
    }

    // Getters and setters for each field
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }
    public String getInclusions(){
        return inclusions;
    }
    public Double getMrp(){
        return  mrp;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
