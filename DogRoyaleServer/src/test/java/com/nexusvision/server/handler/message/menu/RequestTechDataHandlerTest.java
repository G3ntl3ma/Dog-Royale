package com.nexusvision.server.handler.message.menu;
import com.nexusvision.server.handler.HandlingException;
import com.nexusvision.server.handler.message.menu.RequestTechDataHandler;
import com.nexusvision.server.handler.HandlingException;
import com.nexusvision.server.model.messages.menu.RequestTechData;
import com.nexusvision.server.model.messages.menu.ReturnTechData;
import com.nexusvision.server.model.messages.menu.TypeMenue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
/*
TODO complete class Request Tech Data
     Array List of EmbeddedExtensions
public class RequestTechDataHandlerTest {

    @Mock
    private RequestTechData requestTechData;

    @InjectMocks
    private RequestTechDataHandler requestTechDataHandler;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHandle() throws HandlingException {
        // mocking of clientID, its just an example
        int clientID = 123;

        // mocking ReturnTechData to check if the handler returns a JSON string
        ReturnTechData returnTechData = mock(ReturnTechData.class);

        List<ReturnTechData> extensions = new ArrayList<ReturnTechData>();

        when(returnTechData.getType()).thenReturn(TypeMenue.returnTechData.getOrdinal());
        when(returnTechData.getServerVersion()).thenReturn("Server version");
        when(returnTechData.getSupportedProtocol()).thenReturn("Supported Protocol");
        when(returnTechData.getEmbeddedExtensions()).thenReturn(extensions);

        String expectedJsonString = "{\"type\":1,\"serverVersion\":\"1.0\",\"supportedProtocol\":\"HTTP\",\"embeddedExtensions\":\"Some extensions\"}";

        // call the method being tested
        String result = requestTechDataHandler.handle(requestTechData, clientID);

        // verify the result
        assertEquals(expectedJsonString, result);

        // verify that the necessary ReturnTechData methods were called
        verify(returnTechData).getType();
        verify(returnTechData).getServerVersion();
        verify(returnTechData).getSupportedProtocol();
        verify(returnTechData).getEmbeddedExtensions();
    }

}
*/