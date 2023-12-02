package com.nexusvision.server.model.messages.game;

/**
 * Clients verarbeiten Antwort
 *
 * @author kellerb
 */

import lombok.Data;

@Data
public class Response extends AbstractGameMessage {
    private boolean updated;
}
