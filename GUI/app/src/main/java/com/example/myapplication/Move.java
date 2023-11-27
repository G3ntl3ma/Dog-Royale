package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a move of a player (only important for participant-client)
 */
public class Move {
    @SerializedName("type")
    TypeGame type = TypeGame.move;

    @SerializedName("skip")
    boolean skip; // true if player wants or needs to skip his turn

    @SerializedName("card")
    Card card;

    @SerializedName("selectedValue")
    int selectedValue; // for cards, where value has to be chosen. 0 if value cannot be chosen

    @SerializedName("isStarter")
    boolean isStarter; // true if player uses card to start with a piece

    @SerializedName("opponentPieceId")
    int opponentPieceId; // is the piece that player wants to swap with (only relevant for swap card)
}
