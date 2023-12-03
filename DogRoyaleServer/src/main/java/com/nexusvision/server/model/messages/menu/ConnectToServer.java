package com.nexusvision.server.model.messages.menu;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConnectToServer extends AbstractMenuMessage {
    private String name;
    private Boolean isObserver = null;
}
