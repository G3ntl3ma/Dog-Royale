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

    /**
     * Returns the name of the player with the given clientId
     * @param clientId
     * @return clientId
     */
    public String getName(Integer clientId) {
        for (Order order : this.order) {
            if (order.getClientId() == clientId) {
                return order.getName();
            }
        }
        return null;
    }


}