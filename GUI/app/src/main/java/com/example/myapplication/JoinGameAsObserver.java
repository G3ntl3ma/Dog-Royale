package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class JoinGameAsObserver {
    @SerializedName("type")
    TypeMenue type = TypeMenue.joinGameAsObserver;

    @SerializedName("gameID")
    int gameId; // unique number that identifies a game

    @SerializedName("clientId")
    int clientId;
}
