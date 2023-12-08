package GUI.app.src.main.java.com.example.myapplication.handler.messageHandler.menu;

import com.example.myapplication.messages.menu.ConnectedToServer;

import GUI.app.src.main.java.com.example.myapplication.handler.Handler;
import GUI.app.src.main.java.com.example.myapplication.handler.HandlingException;
//import GUI.app.src.main.java.com.example.myapplication.handler.ServerHandler;
import GUI.app.src.main.java.com.example.myapplication.messages.menu.TypeMenu;
import lombok.Data;

//@Data
//public class ConnectedToServerHandler extends Handler implements GUI.app.src.main.java.com.example.myapplication.handler.messageHandler.menu.MenuMessageHandler<ConnectedToServer> {
//    /**
//     *
//     * @param message: message from server that contains clientId
//     *sets ServerHandler.clientId and sends findTournament request to the server
//     */
//
////    @Override
////    public String handle(com.example.myapplication.messages.menu.ConnectedToServer message) throws HandlingException {
////        try{
////            ServerHandler.setClientID(message.getClientId());
////            com.example.myapplication.messages.menu.FindTournament findTournament = new com.example.myapplication.messages.menu.FindTournament();
////            findTournament.setType(TypeMenu.requestTournamentInfo.getOrdinal());
////            findTournament.setClientId(ServerHandler.getClientID());
////            findTournament.setTournamentStarting(10);//gets only 10 tournaments
////            findTournament.setTournamentInProgress(10);
////            findTournament.setTournamentFinished(10);
////            return gson.toJson(findTournament);
////
////        } catch (Exception e) {
////        throw new HandlingException("Exception while handling connectedToServer",
////                e, TypeMenu.connectedToServer.getOrdinal());
////    }
////
////}
//
//}
