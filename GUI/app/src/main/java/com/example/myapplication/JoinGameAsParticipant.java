package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class JoinGameAsParticipant {
    @SerializedName("type")
    TypeMenue type = TypeMenue.joinGameAsParticipant;

    @SerializedName("gameID")
    int gameID;

    @SerializedName("clientId")
    int clientId; // unique number that was given by and is used by the server to identify the client

    @SerializedName("playerName")
    String playerName;
}
