package com.example.DogRoyalClient.GameInformationClasses;

import lombok.Data;

@Data
public class Observer{
    private Integer clientId;
    private String name;

    public Observer(Integer clientId, String name){
        this.clientId = clientId;
        this.name = name;
    }
}
