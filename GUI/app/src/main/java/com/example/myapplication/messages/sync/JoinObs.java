package com.example.myapplication.messages.sync;

import lombok.Data;

@Data
public class JoinObs extends AbstractSyncMessage {
    private int countObs;
}