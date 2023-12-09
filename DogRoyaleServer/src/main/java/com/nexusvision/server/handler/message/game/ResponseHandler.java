package com.nexusvision.server.handler.message.game;

import com.nexusvision.server.controller.GameLobby;
import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.handler.message.MessageHandler;
import com.nexusvision.server.model.enums.CardType;
import com.nexusvision.server.model.gamelogic.Figure;
import com.nexusvision.server.model.gamelogic.Game;
import com.nexusvision.server.model.gamelogic.Player;
import com.nexusvision.server.model.messages.game.BoardState;
import com.nexusvision.server.model.messages.game.Response;
import com.nexusvision.server.model.messages.game.TypeGame;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ResponseHandler extends MessageHandler<Response> {

    @Override
    protected String performHandle(Response message, int clientID) {
        return null;
    }
}
