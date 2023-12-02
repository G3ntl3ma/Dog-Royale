package com.nexusvision.server.handler.message.menu;

import static org.junit.jupiter.api.Assertions.assertEquals;
/*
public class RequestGameListHandlerTest {

    Gson gson = new Gson();

    @Mock
    private RequestGameList mockRequestGameList;

    @InjectMocks
    private RequestGameListHandler requestGameListHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testHandleNoGameList() {
        // if every Tournament is empty
        when(mockRequestGameList.getGameCountStarting()).thenReturn(0);
        when(mockRequestGameList.getGameCountInProgress()).thenReturn(0);
        when(mockRequestGameList.getGameCountFinished()).thenReturn(0);

        // mocking of tournament message
        String result = requestGameListHandler.handle(mockRequestGameList, 123);

        //compare mocked tournament with empty tournament
        Error expectedError = new Error();
        expectedError.setType(TypeMenue.error);
        expectedError.setDataId(TypeMenue.requestGameList.ordinal() + 100);
        expectedError.setMessage("Game List failed (no games available)");
        String expectedJson = gson.toJson(expectedError);
        assertEquals(expectedJson, result);
    }
}

 */


