package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

/**
 * Client sends after processing signal from server
 * Server waits for every client to send this
 */

public class Response {
    @SerializedName("type")
    TypeGame type = TypeGame.response;

    @SerializedName("updated")
    boolean updated; // true if GUI was successfully updated
}
