package com.nexusvision.server.handler.message.menu;

import com.nexusvision.server.model.messages.menu.FindTournament;
import com.nexusvision.server.model.messages.menu.ReturnFindTournament;
import com.nexusvision.server.model.messages.menu.TypeMenue;
import net.bytebuddy.jar.asm.Handle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

public class FindTournamentHandlerTest {

    @Mock
    private FindTournament findTournament;

    @InjectMocks
    private FindTournamentHandler findTournamentHandler;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHandle() {
        // mocking every input for tournaments as 0
        when(findTournament.getTournamentStarting()).thenReturn(0);
        when(findTournament.getTournamentFinished()).thenReturn(0);
        when(findTournament.getTournamentInProgress()).thenReturn(0);

        //copy the result of handlerError for zero tournaments
        String expectedResult = "{\"dataId\":0,\"message\":\"Failed to find tournament (no tournaments)\",\"type\":108}";

        int mockClientID = 123;
        when(findTournament.getClientId()).thenReturn(mockClientID);

        // Call the method being tested
        String result = findTournamentHandler.handle(findTournament, mockClientID);

        // Verify the result
        assertEquals(expectedResult, result);
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