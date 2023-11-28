import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ClientHandlerTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnitRunner.rule();

    @Mock
    private BufferedReader reader;

    @Mock
    private PrintWriter writer;

    @Test
    public void testRun() throws IOException {
        when(reader.readLine()).thenReturn("Hello", "World", null);

        ClientHandler clientHandler = new ClientHandler(new Socket());
        clientHandler.run();

        // Verify that the processMessage method was called for each received message
        // Assert that the response message sent to the client is consistent with the processed message

        verify(writer).println("Nachricht erhalten");
        verify(writer).println("Message processed: Hello");
        verify(writer).println("Message processed: World");
    }
}