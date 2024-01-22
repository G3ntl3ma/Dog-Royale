package com.nexusvision.server.model.messages.menu;


import com.nexusvision.server.model.messages.AbstractMessage;
import com.nexusvision.server.model.utils.PlayerElement;
import lombok.Data;

import java.util.List;

/**
 *  Server confirms successful tournament registration
 *
 * @author felixwr
 */
@Data
public class RegisteredForTournament extends AbstractMessage {
    private boolean success;
    private int tournamentId;
    private int maxPlayer;
    private List<PlayerElement> players;
    private int maxGames;
}
