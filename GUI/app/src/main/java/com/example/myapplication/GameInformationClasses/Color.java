package com.example.myapplication.GameInformationClasses;

import lombok.Data;

@Data
public class  Color{
    private Integer clientId;
    private Integer color;

    public Color(Integer clientId, Integer color){
        this.clientId = clientId;
        this.color = color;
    }
}