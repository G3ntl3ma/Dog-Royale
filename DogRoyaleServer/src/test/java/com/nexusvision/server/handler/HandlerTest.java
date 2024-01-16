package com.nexusvision.server.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nexusvision.server.common.MessageListener;
import com.nexusvision.server.controller.MessageBroker;
import com.nexusvision.server.controller.ServerController;
import com.nexusvision.utils.NewLineAppendingSerializer;
import org.junit.jupiter.api.BeforeAll;

/**
 * @author felixwr
 */
public abstract class HandlerTest {

    protected static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Object.class, new NewLineAppendingSerializer<>())
            .create();

    protected static ServerController serverController;
    protected static int clientId;
    protected static MessageListener messageListener;
    protected static MessageBroker messageBroker;

    /**
     * Create <code>clientId</code> and hook it up with <code>messageListener</code>
     */
    @BeforeAll
    protected static void setUp() {
        serverController = ServerController.getInstance();
        messageBroker = MessageBroker.getInstance();

        clientId = serverController.createNewClient();
        messageListener = new MessageListener();
        messageBroker.addIdentifier(clientId, messageListener);
    }
}
