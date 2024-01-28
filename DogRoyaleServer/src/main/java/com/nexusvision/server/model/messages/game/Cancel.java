package com.nexusvision.server.model.messages.game;

import com.nexusvision.server.model.messages.AbstractMessage;
import com.nexusvision.server.model.utils.WinnerOrderElement;
import lombok.Data;

import java.util.List;

/**
 * Cancels
 *
 * @author kellerb
 */
@Data
public class Cancel extends AbstractMessage {
    private List<WinnerOrderElement> winnerOrder;
}
