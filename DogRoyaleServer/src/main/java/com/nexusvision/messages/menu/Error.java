package com.nexusvision.messages.menu;


import lombok.Data;

/**
 * Error Message
 *
 * @author kellerb
 */
@Data
public class Error extends AbstractMenuMessage {
    private TypeMenue dataId;
    private String message;
}
