package ServiceTest;

import Dtos.ConnectedToServerDto;
import Enums.TypeMenue;
import Service.Deserializer;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for Deserializer Service.
 */
public class DeserializerTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DeserializerTest(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( DeserializerTest.class );
    }

    /**
     * Rigorous Test :-)
     */
    public void testDeserializer()
    {
        String connectToServerMessage = "{" +
                "\"type\": 101," +
                "\"name\": \"MyNameIsJeff\"," +
                "\"isObserver\": true" +
                "}";

        Deserializer deserializer = new Deserializer(connectToServerMessage);

        ConnectedToServerDto dto = (ConnectedToServerDto) deserializer.deserialize();

        assertEquals(dto.getType(), TypeMenue.connectedToServer.ordinal()+100);
    }
}
