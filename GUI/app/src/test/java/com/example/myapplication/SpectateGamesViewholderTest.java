package com.example.myapplication;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class SpectateGamesViewholderTest {
    @Mock
    private SpectateGamesAdapter spectateGamesAdapter;
    private SpectateGamesViewholder spectateGamesViewholder;
    @Test
    void linkAdapter() {
        assertEquals(spectateGamesAdapter,spectateGamesViewholder.linkAdapter(spectateGamesAdapter));
    }
}