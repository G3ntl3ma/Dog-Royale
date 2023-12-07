package com.nexusvision.server.model.messages.menu;

import lombok.Data;

/**
 * Client registers with his name
 *
 * @author felixwr
 */
@Data
public class ConnectToServer extends AbstractMenuMessage {
    private String name;
    private Boolean isObserver = null;
}
