package com.example.DogRoyalClient.messages.sync;

import lombok.Data;

@Data
public class LiveTimer extends AbstractSyncMessage {
    int liveTime;
}
