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
    protected static MessageBroker messageBroker;

    protected static int clientId1;
    protected static int clientId2;
    protected static MessageListener messageListener1;
    protected static MessageListener messageListener2;

    /**
     * Create <code>clientId</code> and hook it up with <code>messageListener</code>
     */
    @BeforeAll
    protected static void setUp() {
        serverController = ServerController.getInstance();
        messageBroker = MessageBroker.getInstance();

        clientId1 = serverController.createNewClient();
        messageListener1 = new MessageListener();
        messageBroker.addIdentifier(clientId1, messageListener1);

        clientId2 = serverController.createNewClient();
        messageListener2 = new MessageListener();
        messageBroker.addIdentifier(clientId2, messageListener2);
    }
}
