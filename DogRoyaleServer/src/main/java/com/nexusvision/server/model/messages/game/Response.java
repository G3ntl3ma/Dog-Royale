package com.nexusvision.server.model.messages.game;

/**
 * Clients process response
 *
 * @author kellerb
 */

import com.nexusvision.server.model.messages.AbstractMessage;
import lombok.Data;

@Data
public class Response extends AbstractMessage {
    private boolean updated;
}
