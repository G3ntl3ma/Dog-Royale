package com.nexusvision.server.model.messages.menu;

import lombok.Data;

import java.util.List;

/**
 * Server returns technical data
 *
 * @author kellerb
 */
@Data
public class ReturnTechData extends AbstractMenuMessage{
    private String serverVersion;
    private String supportedProtocol;
    private List<Integer> embeddedExtensions;
}
