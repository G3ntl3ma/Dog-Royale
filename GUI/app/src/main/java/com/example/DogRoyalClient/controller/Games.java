package com.example.DogRoyalClient.controller;

import com.example.DogRoyalClient.messages.menu.ReturnGameList;

import java.util.List;

import lombok.Data;

/**
 * Games the Client get From server are stored here
 */
@Data
public class Games {
    public static List<ReturnGameList.StartingGame> startingGames;
    public static List<ReturnGameList.RunningGame> runningGames;
    public static List<ReturnGameList.FinishingGame> finishedGames;

}
