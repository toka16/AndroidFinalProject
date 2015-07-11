package com.example.user.finalproject.model;

public class Product {

    private String name;
    private String description;
    private double price;
    private String image_link;
    private byte[] product_image;

    private long db_ID;
    private int server_ID;

    public Product(String name, String description){
        this.name = name;
        this.description = description;
    }

    public Product(){}

    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }

    public long getDb_ID(){
        return this.db_ID;
    }

    public void setDb_ID(long db_id){
        this.db_ID = db_id;
    }

    public int getServer_ID(){
        return this.server_ID;
    }

    public void setServer_ID(int server_id){
        this.server_ID = server_id;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setName(String newName){
        name = newName;
    }

    public String getName(){
        return name;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public double getPrice(){
        return price;
    }

    public String toString(){
        return "name: " + name + ", description: " + description + ", price: " + price;
    }

    public void setProductImage(byte[] image){
        product_image = image;
    }

    public byte[] getProductImage(){
        return product_image;
    }
}
