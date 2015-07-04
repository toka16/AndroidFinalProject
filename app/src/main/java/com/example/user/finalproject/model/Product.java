package com.example.user.finalproject.model;

public class Product {

    private String name;
    private String description;
    private double price;
    private String category;

    public Product(String name, String description){
        this.name = name;
        this.description = description;
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

    public void setCategoryName(String category){
        this.category = category;
    }

    public String getCategoryName(){
        return category;
    }

    public String toString(){
        return "name: " + name + ", description: " + description + ", price: " + price;
    }
}
