package com.nexusvision.server.model.messages.game;

import com.nexusvision.server.model.messages.AbstractMessage;
import lombok.Data;

@Data
public class JoinObs extends AbstractMessage {
    private int countObs;
}
