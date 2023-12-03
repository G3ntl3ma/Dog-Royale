package com.nexusvision.server.model.messages.menu;


import lombok.Builder;
import lombok.Data;

/**
 * Error Message
 *
 * @author kellerb, felixwr
 */
@Data
@Builder
public class Error extends AbstractMenuMessage {
    private int dataId;
    private String message;
}
