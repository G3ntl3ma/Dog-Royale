package com.nexusvision.server.model.messages.game;

/**
 * Clients verarbeiten Antwort
 *
 * @author kellerb
 */

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response extends AbstractGameMessage {
    private boolean updated;
}
