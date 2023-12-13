package com.example.DogRoyalClient.messages.game;

import lombok.Data;

@Data
public class Cancel extends AbstractGameMessage{
    private int[] winnerOrder;
}
