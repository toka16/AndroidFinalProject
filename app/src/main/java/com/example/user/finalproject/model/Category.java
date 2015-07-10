package com.example.user.finalproject.model;

import java.util.ArrayList;

/**
 * Created by toka on 7/10/2015.
 */
public class Category {
    private String name;
    private long db_ID;
    private int server_ID;
    private ArrayList<Product> products;

    public long getDb_ID() {
        return db_ID;
    }

    public int getServer_ID() {
        return server_ID;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDb_ID(long db_ID) {
        this.db_ID = db_ID;
    }

    public void setServer_ID(int server_ID) {
        this.server_ID = server_ID;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}
