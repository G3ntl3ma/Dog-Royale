package com.example.myapplication.messages.sync;

import lombok.Data;

@Data
public class TurnTimer extends AbstractSyncMessage {
    int turnTime;
}
