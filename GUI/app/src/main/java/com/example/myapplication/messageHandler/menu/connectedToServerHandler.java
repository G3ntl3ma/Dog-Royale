package GUI.app.src.main.java.com.example.myapplication.messageHandler.menu;

public class connectedToServerHandler {

    public void handle(com.example.myapplication.messages.menu.ConnectedToServer message) {
        com.example.myapplication.ServerHandler.setClientId(message.getClientId());
    };

}
