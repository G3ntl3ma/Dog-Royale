package com.example.myapplication.GameInformationClasses;

import com.example.myapplication.messages.menu.ReturnLobbyConfig;

import java.util.List;

import lombok.Data;

public class PlayerOrder{
    private OrderType type;
    private List<Order> order;

    @Data
    public class Order{
        private Integer clientId;
        private String name;
    }

}