package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class RequestTournamentInfo {
    @SerializedName("type")
    TypeMenue type = TypeMenue.requestTournamentInfo;

    @SerializedName("clientId")
    int clientId;

    @SerializedName("tournamentId")
    int tournamentId;
}
