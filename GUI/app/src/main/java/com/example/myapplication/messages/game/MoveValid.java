package com.example.myapplication.messages.game;

import java.util.List;

import lombok.Data;

/**
 * Server wertet Spielzug aus
 *
 * @author kellerb
 */
@Data
public class MoveValid extends AbstractGameMessage{
    private boolean skip;
    private Card card;
    private List<SelectedValue> selectedValue;

    private int pieceId;
    private boolean isStarter;
    private int opponentPieceId;
    private boolean validMove;

    public class SelectedValue {
        boolean test;
        int selectedCardValue;
   }
}
