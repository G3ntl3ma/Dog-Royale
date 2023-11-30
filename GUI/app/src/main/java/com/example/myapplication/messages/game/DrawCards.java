package com.example.myapplication.messages.game;


import lombok.Data;

import java.util.List;

/**
 * Kartenziehen
 *
 * @author kellerb
 */
@Data
public class DrawCards extends AbstractGameMessage {
    private List<Card> droppedCards;
    private List<Card> drawnCards;
}
