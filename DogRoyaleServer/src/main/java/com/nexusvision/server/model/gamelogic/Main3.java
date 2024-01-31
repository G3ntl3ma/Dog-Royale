import com.nexusvision.config.AppConfig;
import com.nexusvision.server.model.gamelogic.EngineServerHandler;

//get board state and construct savestate from it
//convert move to a json and send
//listen to which card we get
public class Main3 {
    private static final int PORT = Integer.parseInt(AppConfig.getInstance().getProperty("port"));
    public static void main(String[] args) {
        String SERVER_ADDRESS = "127.0.0.1";
        EngineServerHandler engineServerHandler = new    EngineServerHandler();
        engineServerHandler.run(SERVER_ADDRESS, 33100);
    }
}

