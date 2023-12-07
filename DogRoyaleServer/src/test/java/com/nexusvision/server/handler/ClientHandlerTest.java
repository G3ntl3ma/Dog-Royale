package com.nexusvision.server.handler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
/*
@ExtendWith(MockitoExtension.class)
public class ClientHandlerTest {
    @Mock
    private BufferedReader reader;

    @Mock
    private PrintWriter writer;

    @InjectMocks
    private ClientHandler clientHandler;

    @Test
    public void testRun() throws IOException {
        when(reader.readLine()).thenReturn("Hello", "World", null);

        clientHandler = new ClientHandler(new Socket());
        clientHandler.run();

        // Verify that the processMessage method was called for each received message
        // Assert that the response message sent to the client is consistent with the processed message

        verify(writer).println("Nachricht erhalten");
        verify(writer).println("Message processed: Hello");
        verify(writer).println("Message processed: World");
    }
}

     */