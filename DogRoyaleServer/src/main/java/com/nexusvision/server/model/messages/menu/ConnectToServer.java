package com.nexusvision.server.model.messages.menu;

import lombok.Data;

@Data
public class ConnectToServer extends AbstractMenuMessage {
    private String name;
    private Boolean isObserver = null;
}
