package com.nexusvision.server.model.messages.menu;

import com.nexusvision.server.model.messages.AbstractMessage;
import lombok.Data;

import java.util.List;

/**
 * Server returns technical data
 *
 * @author kellerb
 */
@Data
public class ReturnTechData extends AbstractMessage {
    private String serverVersion;
    private String supportedProtocol;
    private List<Integer> embeddedExtensions;
}
