package com.example.myapplication.GameInformationClasses;


import java.util.List;

import lombok.Data;

@Data
public class PlayerOrder{
    private OrderType type;
    private List<Order> order;

    public PlayerOrder(OrderType type, List<Order> order){
        this.type = type;
        this.order = order;
    }

    public List<Order> getOrder(){
        return this.order;
    }

}