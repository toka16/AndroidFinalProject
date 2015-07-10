package com.example.user.finalproject.model;


import java.util.ArrayList;

public class Menu {
    private String name;
    private String description;
    private double price;
    private long db_ID;
    private int server_ID;
    private ArrayList<Product> products;

    public Menu(String name){
        this.name = name;
        products = new ArrayList<>();
    }

    public ArrayList<Product> getProducts(){
        return this.products;
    }

    public void setProducts(ArrayList<Product> products){
        this.products = products;
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
}
