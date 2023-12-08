package com.nexusvision.server.model.messages.menu;


import com.nexusvision.server.model.messages.AbstractMessage;
import lombok.Data;

/**
 * Error Message
 *
 * @author kellerb, felixwr
 */
@Data
public class Error extends AbstractMessage {
    private int dataId;
    private String message;
}
