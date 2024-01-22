package com.nexusvision.server.handler.message.menu;

import com.nexusvision.server.model.messages.menu.RequestTournamentList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class RequestTournamentListHandlerTest {

    @Mock
    private RequestTournamentList requestTournamentList;

    @InjectMocks
    private FindTournamentHandler findTournamentHandler;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHandle() {
        // mocking every input for tournaments as 0
        when(requestTournamentList.getTournamentsUpcomingCount()).thenReturn(0);
        when(requestTournamentList.getTournamentsFinishedCount()).thenReturn(0);
        when(requestTournamentList.getTournamentsRunningCount()).thenReturn(0);

        //copy the result of handlerError for zero tournaments
        String expectedResult = "{\"dataId\":0,\"message\":\"Failed to find tournament (no tournaments)\",\"type\":108}";

        int mockClientID = 123;
        when(requestTournamentList.getClientId()).thenReturn(mockClientID);

        // Call the method being tested TODO: Catch exception here
        // String result = findTournamentHandler.handle(requestTournamentList, mockClientID);

        // Verify the result TODO: change
        //assertEquals(expectedResult, result);
    }
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


            ReturnTournamentList expectedReturnFindTournament = new ReturnTournamentList();
            expectedReturnFindTournament.setType(TypeMenue.returnFindTournament);
            expectedReturnFindTournament.setClientId(456);
            expectedReturnFindTournament.setTournamentFinished(Collections.emptyList()); // Replace with expected list
            expectedReturnFindTournament.setTournamentStarting(Collections.emptyList()); // Replace with expected list
            expectedReturnFindTournament.setTournamentInProgress(Collections.emptyList()); // Replace with expected list
            String expectedJson = gson.toJson(expectedReturnFindTournament);
            assertEquals(expectedJson, result);
        }

         */

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