package com.nexusvision.server.model.messages.menu;


import lombok.Data;

/**
 * Error Message
 *
 * @author kellerb, felixwr
 */
@Data
public class Error extends AbstractMenuMessage {
    private int dataId;
    private String message;
}
