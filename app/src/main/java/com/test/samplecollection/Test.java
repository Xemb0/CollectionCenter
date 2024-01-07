package com.test.samplecollection;

public class Test {
    private  int mrp;
    private int inclusions;
    private String name;
    private int price;
    private String id;
    private String description;

    private String tag;

    public Test() {
        // Default constructor required for Firestore
    }

    public Test(String name, int price, String description,int mrp,int inclusions,String tag,String id) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.mrp = mrp;
        this.inclusions = inclusions;
        this.tag = tag;
        this.id =id;
    }

    // Getters and setters for each field
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setId(String id){this.id = id;}

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
    public String getId(){

        return id;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    private boolean addedToCart;

    public boolean isAddedToCart() {
        return addedToCart;
    }

    public void setAddedToCart(boolean addedToCart) {
        this.addedToCart = addedToCart;
    }
}
