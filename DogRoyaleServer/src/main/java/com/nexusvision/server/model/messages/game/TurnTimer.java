package com.nexusvision.server.model.messages.game;

import com.nexusvision.server.model.messages.AbstractMessage;
import lombok.Data;

@Data
public class TurnTimer extends AbstractMessage {
    private int turnTime;
}
