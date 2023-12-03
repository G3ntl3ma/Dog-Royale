package com.nexusvision.server.model.messages.menu;

import lombok.Builder;
import lombok.Data;

/**
 * @author felixwr
 */
@Data
@Builder
public abstract class AbstractMenuMessage {

    protected Integer type;
}
