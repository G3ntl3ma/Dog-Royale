package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class RegisterForTournament {
    @SerializedName("type")
    TypeMenue type = TypeMenue.registerForTournament;

    @SerializedName("tournamentID")
    int tournamentID;

    @SerializedName("clientId")
    int clientId;

}
