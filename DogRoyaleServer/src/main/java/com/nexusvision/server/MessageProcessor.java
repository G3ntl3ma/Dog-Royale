package com.nexusvision.server;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
/**
 * Verarbeitet Nachrichten
 *
 * @author kellerb
 */
public class MessageProcessor {
    private static final Logger logger = LogManager.getLogger(MessageProcessor.class);

    /**
     * Verarbeitet eine Nachricht
     *
     * @param inputStream Der Eingabestrom, der die Nachricht enthält
     * @param outputStream Der Ausgabestrom, der die Antwort enthält
     * @throws IOException
     */
    public void processMessage(InputStream inputStream, OutputStream outputStream) throws IOException {
        // Nachricht lesen
        String message = readMessage(inputStream);
        logger.info("Nachricht empfangen: " + message);

        // Nachricht verarbeiten
        // ...

        // Antwort senden
        writeMessage(outputStream, "Antwort auf Nachricht: " + message);
    }

    /**
     * Liest eine Nachricht aus dem Eingabestrom
     *
     * @param inputStream Der Eingabestrom
     * @return Die Nachricht
     * @throws IOException
     */
    private String readMessage(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int length = inputStream.read(buffer);
        return new String(buffer, 0, length);
    }

    /**
     * Schreibt eine Nachricht in den Ausgabestrom
     *
     * @param outputStream Der Ausgabestrom
     * @param message Die Nachricht
     * @throws IOException
     */
    private void writeMessage(OutputStream outputStream, String message) throws IOException {
        outputStream.write(message.getBytes());
        outputStream.flush();
    }
}
