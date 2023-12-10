package com.nexusvision.server.service;

import com.nexusvision.server.model.gamelogic.Game;
import com.nexusvision.server.model.gamelogic.Player;

public class PlayerService {

    public Player getPlayer(int playerId, Game game) {
        Player player = null;
        for (Player tempPlayer : game.getPlayerList()) {
            if (tempPlayer.getPlayerId() == playerId) {
                player = tempPlayer;
                break;
            }
        }
        return player;
    }
}
