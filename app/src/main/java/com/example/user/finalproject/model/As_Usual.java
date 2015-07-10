package com.example.user.finalproject.model;


import java.util.ArrayList;

public class As_Usual {
    private String name;
    private double price;
    private long db_ID;
    private ArrayList<Product> products;

    public As_Usual(String name){
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
        return "name: " + name + ", price: " + price;
    }
}
