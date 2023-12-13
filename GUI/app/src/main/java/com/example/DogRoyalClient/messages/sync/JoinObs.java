package com.example.DogRoyalClient.messages.sync;

import lombok.Data;

@Data
public class JoinObs extends AbstractSyncMessage {
    private int countObs;
}
