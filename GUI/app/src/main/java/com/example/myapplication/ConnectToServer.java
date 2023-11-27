package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

/**
 * Client requests connecting to a server
 */
public class ConnectToServer {
    @SerializedName("type")
    private TypeMenue type = TypeMenue.connectToServer;

    @SerializedName("name")
    private String name;

    @SerializedName("isObserver")
    private boolean isObserver;

    // TODO: Konstruktor, Getter und Setter hier hinzufügen

}

