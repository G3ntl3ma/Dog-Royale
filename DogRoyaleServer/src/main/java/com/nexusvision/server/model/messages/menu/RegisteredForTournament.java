package com.nexusvision.server.model.messages.menu;


import com.nexusvision.server.model.messages.AbstractMessage;
import lombok.Data;

import java.util.List;

/**
 *  Server confirms successful tournament registration
 *
 * @author kellerb
 */
@Data
public class RegisteredForTournament extends AbstractMessage {
    private List<Player> players;
    private boolean success;
    private int tournamentId;
    private int maxPlayer;
    private int rounds;


    @Data
    public static class Player{
        private int clientId;
        private String name;
    }
}
