package com.nexusvision.server.handler.message.menu;

import com.google.gson.Gson;
import com.nexusvision.server.model.messages.menu.ConnectToServer;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ConnectToServerHandlerTest {

    Gson gson = new Gson();

    @Mock
    private ConnectToServer mockConnectToServer;

    @InjectMocks
    private ConnectToServerHandler connectToServerHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    //@Test
    //void testHandle






}
