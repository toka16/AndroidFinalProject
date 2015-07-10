package com.example.user.finalproject.model;

public class News {

    private String name;
    private String description;
    private String fromDate;
    private String toDate;
    private long db_ID;
    private int server_ID;

    public News(String name, String description){
        this.name = name;
        this.description = description;
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

    public void setName(String newName){
        name = newName;
    }

    public String getName(){
        return name;
    }

    public void setDescription(String newDescription){
        description = newDescription;
    }

    public String getDescription(){
        return description;
    }

    public void setFromDate(String fromDate){
        this.fromDate = fromDate;
    }

    public String getFromDate(){
        return fromDate;
    }

    public void setToDate(String toDate){
        this.toDate = toDate;
    }

    public String getToDate(){
        return toDate;
    }

    public String toString(){
        return "news name: " + name + ", news description: " + description + ", from date: " + fromDate + ", toDate: " + toDate;
    }
}
