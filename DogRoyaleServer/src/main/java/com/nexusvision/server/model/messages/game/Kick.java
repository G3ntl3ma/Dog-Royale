package com.nexusvision.server.model.messages.game;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Kick extends AbstractGameMessage{
    private int clientId;
    private String reason;
}
