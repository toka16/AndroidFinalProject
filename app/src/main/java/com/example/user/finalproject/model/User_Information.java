package com.example.user.finalproject.model;


public class User_Information {
    private String rowName;
    private String value;
    private int db_ID;

    public User_Information(){
    }

    public String getRowName(){
        return this.rowName;
    }

    public void setRowName(String rowName){
        this.rowName = rowName;
    }

    public String getValue(){
        return this.value;
    }

    public void setValue(String value){
        this.value = value;
    }

    public int getDb_ID(){
        return this.db_ID;
    }

    public void setDb_ID(int db_ID){
        this.db_ID = db_ID;
    }

    public String toString(){
        return this.rowName + this.value;
    }
}
