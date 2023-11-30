package com.nexusvision.messages.menu;

import lombok.Data;

@Data
public class ConnectToServer extends AbstractMenuMessage {
    String name;
    boolean isObserver;
}
