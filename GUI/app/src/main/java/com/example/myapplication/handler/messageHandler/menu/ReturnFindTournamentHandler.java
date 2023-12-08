package GUI.app.src.main.java.com.example.myapplication.handler.messageHandler.menu;

import GUI.app.src.main.java.com.example.myapplication.handler.Handler;
import GUI.app.src.main.java.com.example.myapplication.handler.HandlingException;
//import GUI.app.src.main.java.com.example.myapplication.handler.ServerHandler;
import GUI.app.src.main.java.com.example.myapplication.messages.menu.TypeMenu;
import com.example.myapplication.messages.menu.RequestGameList;
import com.example.myapplication.messages.menu.ReturnFindTournament;

import lombok.Data;
//
//@Data
//public class ReturnFindTournamentHandler extends Handler implements GUI.app.src.main.java.com.example.myapplication.handler.messageHandler.menu.MenuMessageHandler<ReturnFindTournament> {
//    /**
//     *
//     * @param message The deserialized json string that's getting processed
//     * @return sends RequestGameList request
//     * @throws HandlingException
////     */
////    @Override
////    public String handle(com.example.myapplication.messages.menu.ReturnFindTournament message) throws HandlingException {
////        try{
////            //TODO save the Tournaments to use them later
////            //remains unhandled, just sends RequestGameList request
////            com.example.myapplication.messages.menu.RequestGameList requestGameList = new RequestGameList();
////            requestGameList.setType(TypeMenu.requestGameList.getOrdinal());
////            requestGameList.setClientID(ServerHandler.getClientID());
////            requestGameList.setGameCountStarting(10);//10 games To show
////            requestGameList.setGameCountInProgress(10);
////            requestGameList.setGameCountFinished(10);
////            return gson.toJson(requestGameList);
////
////        }catch (Exception e){
////            throw new HandlingException("Exception while handling ReturnFindTournament",
////                    e,TypeMenu.returnFindTournament.getOrdinal());
////        }
////
////    };
//}
