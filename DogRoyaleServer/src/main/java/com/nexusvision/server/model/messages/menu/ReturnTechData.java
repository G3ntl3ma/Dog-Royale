package com.nexusvision.server.model.messages.menu;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Server antwortet mit technischen Daten
 *
 * @author kellerb
 */
@Data
@Builder
public class ReturnTechData extends AbstractMenuMessage{
    private String serverVersion;
    private String supportedProtocol;
    private List<Integer> embeddedExtensions;
}
