package com.nexusvision.server.model.utils;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class TournamentPlayer {
    private final int clientId;
    private final String name;
    @Setter
    private int points;
}