package ServiceTest;

import Dtos.ConnectToServerDto;
import Dtos.LiveTimerDto;
import Dtos.UnfreezeDto;
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
     * These tests check, if the deserializer returns the right Json object
     * and if those Json objects transform into the right String
     */
    public void testConnectToServer()
    {
        ConnectToServerDto expectedDto = new ConnectToServerDto("Jeff",true);
        String expectedJason = expectedDto.toJson();
        Deserializer deserializer = new Deserializer(expectedJason);
        ConnectToServerDto dto = (ConnectToServerDto) deserializer.deserialize();
        assertEquals(dto.toJson(), expectedJason);
    }

    public void testLiveTimer()
    {
        LiveTimerDto expectedDto = new LiveTimerDto(420);
        String expectedJason = expectedDto.toJson();
        Deserializer deserializer = new Deserializer(expectedJason);
        LiveTimerDto dto = (LiveTimerDto) deserializer.deserialize();

        assertEquals(dto.toJson(), expectedJason);
    }

    public void testFreeze()
    {
        UnfreezeDto expectedDto = new UnfreezeDto();
        String expectedJason = expectedDto.toJson();
        Deserializer deserializer = new Deserializer(expectedJason);
        UnfreezeDto dto = (UnfreezeDto) deserializer.deserialize();

        assertEquals(dto.toJson(), expectedJason);
    }

}
