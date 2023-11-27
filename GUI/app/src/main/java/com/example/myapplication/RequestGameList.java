package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class RequestGameList {
    @SerializedName("type")
    TypeMenue type = TypeMenue.requestGameList;

    @SerializedName("clientId")
    int clientID;

    @SerializedName("gameCountStarting")
    int gameCountStarting; // count of starting games the client wants to see

    @SerializedName("gameCountInProgress")
    int getGameCountInProgress; // count of in-progress games the client wants to see

    @SerializedName("gameCountFinished")
    int gameCountFinished; // count of finished games the client wants to see

}
