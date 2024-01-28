package com.nexusvision.server.model.messages.menu;

import com.nexusvision.server.model.messages.AbstractMessage;
import lombok.Data;

/**
 * Ausrichter registers with his name
 *
 * @author felixwr
 */
@Data
public class ConnectToServer extends AbstractMessage {
    private String name;
    private boolean isObserver;
}
