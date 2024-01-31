package com.nexusvision.server.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.nexusvision.server.common.MessageListener;
import com.nexusvision.server.controller.MessageBroker;
import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.handler.message.MessageHandler;
import com.nexusvision.server.model.entities.Client;
import com.nexusvision.server.model.messages.AbstractMessage;
import com.nexusvision.utils.NewLineAppendingSerializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author felixwr
 */
public abstract class HandlerTest {

    protected static final Gson gson = new Gson();

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
    static void setupBeforeAll() {
        serverController = ServerController.getInstance();
        messageBroker = MessageBroker.getInstance();

        clientId1 = serverController.createNewClient();
        Client client1 = serverController.getClientById(clientId1);
        client1.setName("clientId:" + clientId1);
        messageListener1 = new MessageListener();

        clientId2 = serverController.createNewClient();
        Client client2 = serverController.getClientById(clientId2);
        client2.setName("clientId:" + clientId2);
        messageListener2 = new MessageListener();
    }

    @AfterAll
    static void tearDownAfterAll() throws NoSuchFieldException, IllegalAccessException {
        serverController = null;
        messageBroker = null;
        messageListener1 = null;
        messageListener2 = null;
        Field instance = ServerController.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);

        instance = MessageBroker.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @BeforeEach
    void setup() {
        messageBroker.addIdentifier(clientId1, messageListener1);
        messageBroker.addIdentifier(clientId2, messageListener2);
    }

    @AfterEach
    void tearDown() {
        messageBroker.deleteSubscriber(clientId1);
        messageListener1.clearMessages();

        messageBroker.deleteSubscriber(clientId2);
        messageListener2.clearMessages();
    }

    /**
     * Handles the request and retrieves the response message
     *
     * @param request The request to be processed
     * @param handler The handler that will process the request
     * @param clientId The client id that will receive the response message
     * @param messageListener The message listener that will receive the response message
     * @param responseClass The class object of the response message
     * @return The response message as an object
     * @param <M> The response message type
     * @param <R> The request message type
     */
    protected <M extends AbstractMessage,
               R extends AbstractMessage> M handleAndRetrieve(R request,
                                                              MessageHandler<R> handler,
                                                              int clientId,
                                                              MessageListener messageListener,
                                                              Class<M> responseClass) {
        try {
            handler.handle(request, clientId);
        } catch (HandlingException e) {
            fail("Handling exception thrown during test: " + e.getMessage());
        }

        String response = messageListener.retrieveMessage();
        M message = null;

        try {
            message = gson.fromJson(response, responseClass);
        } catch (JsonSyntaxException e) {
            fail("Response string has wrong format: " + e.getMessage());
        }
        return message;
    }
}
