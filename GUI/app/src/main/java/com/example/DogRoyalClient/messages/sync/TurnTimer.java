package com.example.DogRoyalClient.messages.sync;

import lombok.Data;

@Data
public class TurnTimer extends AbstractSyncMessage {
    int turnTime;
}
