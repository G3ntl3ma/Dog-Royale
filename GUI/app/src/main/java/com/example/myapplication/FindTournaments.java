package com.example.myapplication;

import com.google.gson.annotations.SerializedName;


/**
 * Represents Request of tournaments.
 */

public class FindTournaments {
    @SerializedName("type")
    TypeMenue type = TypeMenue.findTournament;

    @SerializedName("clientId")
    int clientId;

    @SerializedName("tournamentStarting") // count of starting tournaments the client wants to see
    int tournamentStarting;

    @SerializedName("tournamentFinished") // count of finished tournaments the client wants to see
    int tournamentFinished;
}
