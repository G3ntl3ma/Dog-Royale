package com.nexusvision.server.handler.message.menu;
import com.nexusvision.server.common.MessageListener;
import com.nexusvision.server.handler.HandlerTest;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class RequestTechDataHandlerTest extends HandlerTest {

    RequestTechDataHandler handler = new RequestTechDataHandler();

    @Test
    void testHandle() {
        RequestTechData request = new RequestTechData();
        request.setType(TypeMenue.requestTechData.getOrdinal());

        ReturnTechData returnTechData = handleAndRetrieve(request, clientId1, messageListener1);

        assertEquals(TypeMenue.returnTechData.getOrdinal(), returnTechData.getType());
        assertNotNull(returnTechData.getServerVersion());
        assertNotNull(returnTechData.getSupportedProtocol());
        assertNotNull(returnTechData.getEmbeddedExtensions());
    }

    private ReturnTechData handleAndRetrieve(RequestTechData request, int clientId, MessageListener messageListener) {
        return handleAndRetrieve(request, handler, clientId, messageListener, ReturnTechData.class);
    }

//    @Mock
//    private RequestTechData requestTechData;
//
//    @InjectMocks
//    private RequestTechDataHandler requestTechDataHandler;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void testHandle() throws HandlingException {
//        // mocking of clientID, its just an example
//        int clientID = 123;
//
//        // mocking ReturnTechData to check if the handler returns a JSON string
//        ReturnTechData returnTechData = mock(ReturnTechData.class);
//
//        List<ReturnTechData> extensions = new ArrayList<ReturnTechData>();
//
//        when(returnTechData.getType()).thenReturn(TypeMenue.returnTechData.getOrdinal());
//        when(returnTechData.getServerVersion()).thenReturn("Server version");
//        when(returnTechData.getSupportedProtocol()).thenReturn("Supported Protocol");
//        when(returnTechData.getEmbeddedExtensions()).thenReturn(extensions);
//
//        String expectedJsonString = "{\"type\":1,\"serverVersion\":\"1.0\",\"supportedProtocol\":\"HTTP\",\"embeddedExtensions\":\"Some extensions\"}";
//
//        // call the method being tested
//        String result = requestTechDataHandler.handle(requestTechData, clientID);
//
//        // verify the result
//        assertEquals(expectedJsonString, result);
//
//        // verify that the necessary ReturnTechData methods were called
//        verify(returnTechData).getType();
//        verify(returnTechData).getServerVersion();
//        verify(returnTechData).getSupportedProtocol();
//        verify(returnTechData).getEmbeddedExtensions();
//    }

}
