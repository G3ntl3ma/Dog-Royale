package com.example.DogRoyalClient.GameInformationClasses;

import lombok.Data;

@Data
public class  Color{
    private Integer clientId;
    private Integer color;

    public Color(Integer clientId, Integer color){
        this.clientId = clientId;
        this.color = color;
    }

    public Integer getClientId(){
        return clientId;
    }

    public Integer getColor(){
        return color;
    }
}
