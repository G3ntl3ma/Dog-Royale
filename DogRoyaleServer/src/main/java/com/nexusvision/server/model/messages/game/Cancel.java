package com.nexusvision.server.model.messages.game;

import com.nexusvision.server.model.messages.AbstractMessage;
import lombok.Data;

import java.util.List;

/**
 * Cancels
 *
 * @author kellerb
 */
@Data
public class Cancel extends AbstractMessage {
    private List<Integer> winnerOrder;
}
