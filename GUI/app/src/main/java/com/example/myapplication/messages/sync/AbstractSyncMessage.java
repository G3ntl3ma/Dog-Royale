package com.example.myapplication.messages.sync;

import lombok.Data;

/**
 * @author hachmeye
 */
@Data
public abstract class AbstractSyncMessage {
    public enum TypeSync {
        joinObs,
        leaveObs,
        leavePlayer,
        liveTimer,
        turnTimer
    }

    protected TypeSync type;
}
