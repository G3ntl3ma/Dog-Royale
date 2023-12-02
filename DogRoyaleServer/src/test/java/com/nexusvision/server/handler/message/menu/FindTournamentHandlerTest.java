package com.nexusvision.server.handler.message.menu;

import com.google.gson.*;
import com.nexusvision.server.model.messages.menu.FindTournament;
import com.nexusvision.server.model.messages.menu.TypeMenue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;
import com.nexusvision.server.model.messages.menu.Error;

import static org.junit.jupiter.api.Assertions.*;

public class FindTournamentHandlerTest {

    Gson gson = new Gson();

    @Mock
    private FindTournament mockFindTournament;

    @InjectMocks
    private FindTournamentHandler findTournamentHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testHandleNoTournaments() {
        // if every Tournament is empty
        when(mockFindTournament.getTournamentStarting()).thenReturn(0);
        when(mockFindTournament.getTournamentFinished()).thenReturn(0);
        when(mockFindTournament.getTournamentInProgress()).thenReturn(0);

        // mocking of tournament message
        String result = findTournamentHandler.handle(mockFindTournament, 123);

        //compare mocked tournament with empty tournament
        Error expectedError = new Error();
        expectedError.setType(TypeMenue.error.getOrdinal());
        expectedError.setDataId(TypeMenue.findTournament.getOrdinal());
        expectedError.setMessage("tournament fail (no tournaments)");
        String expectedJson = gson.toJson(expectedError);
        assertEquals(expectedJson, result);
    }
        /*
        @Test
        void testHandleWithTournaments() {
            // make an example
            when(mockFindTournament.getTournamentStarting()).thenReturn(1);
            when(mockFindTournament.getTournamentFinished()).thenReturn(2);
            when(mockFindTournament.getTournamentInProgress()).thenReturn(3);

            // mocking of tournament message
            String result = findTournamentHandler.handle(mockFindTournament, 456);


            ReturnFindTournament expectedReturnFindTournament = new ReturnFindTournament();
            expectedReturnFindTournament.setType(TypeMenue.returnFindTournament);
            expectedReturnFindTournament.setClientId(456);
            expectedReturnFindTournament.setTournamentFinished(Collections.emptyList()); // Replace with expected list
            expectedReturnFindTournament.setTournamentStarting(Collections.emptyList()); // Replace with expected list
            expectedReturnFindTournament.setTournamentInProgress(Collections.emptyList()); // Replace with expected list
            String expectedJson = gson.toJson(expectedReturnFindTournament);
            assertEquals(expectedJson, result);
        }

         */
}
/*
    @Test
    void testEquals() {
    }

    @Test
    void canEqual() {
    }

    @Test
    void testHashCode() {
    }

    @Test
    void testToString() {
    }
}

 */