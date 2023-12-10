package com.nexusvision.server.model.messages.game;

import com.nexusvision.server.model.messages.AbstractMessage;
import com.nexusvision.server.model.messages.game.TypeGame;
import lombok.Data;

@Data
public class LiveTimer extends AbstractMessage {
    private Integer liveTime;
}
