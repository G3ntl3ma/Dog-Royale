package com.example.myapplication.GameInformationClasses;

public class Order {
    private Integer clientId;
    private String name;

    public Order(Integer clientId, String name){
        this.clientId = clientId;
        this.name = name;
    }

    public Integer getClientId(){
        return this.clientId;
    }

    public String getName(){
        return this.name;
    }
}
