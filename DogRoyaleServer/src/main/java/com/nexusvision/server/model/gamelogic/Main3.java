//TODO
//for ai
//keep track of if move was valid of current player to build a player is out list
//keep track of players that are being skipped

//for ai need to know the
//missing: how many cards on hand per player
//solution: cards on hand per player is sent by server 3.5
//missing: how many cards in deck
//solution: 110 - #pile - #sumhandcards

import com.nexusvision.server.model.gamelogic.EngineServerHandler;

//get board state and construct savestate from it
//convert move to a json and send
//listen to which card we get
public class Main3 {
    private static final int PORT = Integer.parseInt(AppConfig.getInstance().getProperty("port"));
    public static void main(String[] args) {
        String SERVER_ADDRESS = "127.0.0.1";
        EngineServerHandler engineServerHandler = new EngineServerHandler();
        engineServerHandler.run(SERVER_ADDRESS, PORT);
    }
}

