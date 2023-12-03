package com.nexusvision.server.model.messages.game;

import lombok.Builder;
import lombok.Data;

/**
 * @author felixwr
 */
@Data
@Builder
public abstract class AbstractGameMessage {

    protected Integer type;
}
