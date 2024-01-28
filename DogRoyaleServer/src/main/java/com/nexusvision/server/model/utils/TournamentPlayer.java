package com.nexusvision.server.model.utils;

import lombok.*;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class TournamentPlayer {
    private final int clientId;
    private final String name;
    @Setter
    private int points;
}