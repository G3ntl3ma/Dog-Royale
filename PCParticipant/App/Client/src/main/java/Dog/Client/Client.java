package Dog.Client;

import Dog.Client.Interfaces.IClientObservable;
import Dog.Client.Interfaces.IClientObserverGameplay;
import Dog.Client.Interfaces.IClientObserverMenu;
import Dtos.*;
import Service.Deserializer;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * This class is responsible for the client.
 *
 * @author mtwardy
 */
public class Client implements IClientObservable {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;
    private int clientID;
    private ArrayList<IClientObserverMenu> cOMenu;
    private ArrayList<IClientObserverGameplay> cOGameplay;

    /**
     * constructor for client class
     *
     * @param socket socket client is connecting with server
     * @param username username user choose for client
     */

    public Client(Socket socket, String username)
    {
        try{
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
        }catch(IOException e) {
            closeConnection();
        }

        cOMenu = new ArrayList<>();
        cOGameplay = new ArrayList<>();
    }

    /**
     * getter method for clientID
     *
     * @return returns clientID server has choosen for client
     */
    public int getClientID(){
        return clientID;
    }

    public String getplayerName(){return username;}

    /**
     * opens a new thread for messages from client
     *
     * @param message stream of String characters
     */
    public void sendMessage(String message) {
        System.out.println(message);
        new Thread(()->{ sendMessageSync(message); }).start();
    }

    /**
     * buffering String characters of client messages
     *
     * @param message stream of String characters
     */
    private synchronized void sendMessageSync(String message){
        try{
            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }catch(IOException e) {
            closeConnection();
        }
    }

    /**
     * comparing Messaging classes for message from Server
     */
    public void listenForMessage() {
        new Thread(() -> {
            String message;

            while(socket.isConnected()) {
                try {
                    message = bufferedReader.readLine();

                    if(message == null){
                        break;
                    }

                    Deserializer deserializer = new Deserializer(message);
                    Class<?> dtoClass = deserializer.getMessageDtoClass();
                    // dtos messages related to menu
                    if(dtoClass == ConnectedToServerDto.class){
                        ConnectedToServerDto dto = (ConnectedToServerDto) deserializer.deserialize();
                        this.clientID = dto.getClientId();
                    }else if(dtoClass == ReturnGameListDto.class){
                        ReturnGameListDto dto = (ReturnGameListDto) deserializer.deserialize();
                        updateGameList(dto);
                    }else if(dtoClass == ConnectedToGameDto.class){
                        ConnectedToGameDto dto = (ConnectedToGameDto) deserializer.deserialize();
                    }else if(dtoClass == ReturnTournamentListDto.class){
                        ReturnTournamentListDto dto = (ReturnTournamentListDto) deserializer.deserialize();
                        updateFindTournament(dto);
                    }else if(dtoClass == RegisteredForTournamentDto.class){
                        // ignore
                    }else if(dtoClass == ReturnTournamentInfoDto.class){
                        ReturnTournamentInfoDto dto = (ReturnTournamentInfoDto) deserializer.deserialize();
                    }else if(dtoClass == ErrorDto.class){
                        ErrorDto dto = (ErrorDto) deserializer.deserialize();
                    }else if(dtoClass == ReturnTechDataDto.class){
                        ReturnTechDataDto dto = (ReturnTechDataDto) deserializer.deserialize();
                    }

                    // dto messages related to gameplay
                    else if(dtoClass == ReturnLobbyConfigDto.class) {
                        ReturnLobbyConfigDto dto = (ReturnLobbyConfigDto) deserializer.deserialize();
                        updateGameConfig(dto);
                    }else if(dtoClass == BoardStateDto.class){
                        BoardStateDto dto = (BoardStateDto) deserializer.deserialize();
                        updateBoardState(dto);
                    }else if(dtoClass == DrawCardsDto.class){
                        DrawCardsDto dto = (DrawCardsDto) deserializer.deserialize();
                        updateDrawCards(dto);
                    }else if(dtoClass == UpdateDrawCardsDto.class){
                        UpdateDrawCardsDto dto = (UpdateDrawCardsDto) deserializer.deserialize();
                        updateUpdateDrawCards(dto);
                    }else if(dtoClass == MoveValidDto.class){
                        MoveValidDto dto = (MoveValidDto) deserializer.deserialize();
                        updateMoveValid(dto);

                    }else if(dtoClass == FreezeDto.class){
                        FreezeDto dto = (FreezeDto) deserializer.deserialize();
                    }else if(dtoClass == UnfreezeDto.class){
                        UnfreezeDto dto = (UnfreezeDto) deserializer.deserialize();
                    }else if(dtoClass == CancelDto.class){
                        CancelDto dto = (CancelDto) deserializer.deserialize();
                    }else if(dtoClass == KickDto.class) {
                        KickDto dto = (KickDto) deserializer.deserialize();
                    }

                    // dto messages related to sync
                    else if(dtoClass == LiveTimerDto.class){
                        LiveTimerDto dto = (LiveTimerDto) deserializer.deserialize();
                        updateLiveTimer(dto);
                    }else if(dtoClass == TurnTimerDto.class){
                        TurnTimerDto dto = (TurnTimerDto) deserializer.deserialize();
                        updateTurnTimer(dto);
                    }

                }catch(IOException e){
                    closeConnection();
                    break;
                }
            }
        }).start();
    }

    /**
     * closes connection with server
     */
    public void closeConnection() {
        try{
            if(socket != null) {
                socket.close();
            }
            if(bufferedReader != null){
                bufferedReader.close();
            }
            if(bufferedWriter != null){
                bufferedWriter.close();
            }
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * puts Observer in List of gameplay observer
     *
     * @param observer Observer to be added
     */
    @Override
    public void registerObserverGameplay(IClientObserverGameplay observer) {
        cOGameplay.add(observer);
    }

    /**
     * removes Observer from List of gameplay observer
     *
     * @param observer Observer to be removed
     */
    @Override
    public void removeObserverGameplay(IClientObserverGameplay observer) {
        cOGameplay.remove(observer);
    }

    /**
     * puts Observer in List of menu observer
     *
     * @param observer Observer to be added
     */
    @Override
    public void registerObserverMenu(IClientObserverMenu observer) {
        cOMenu.add(observer);

    }

    /**
     * removes Observer from List of menu observer
     *
     * @param observer Observer to be removed
     */
    @Override
    public void removeObserverMenu(IClientObserverMenu observer) {
        cOMenu.remove(observer);
    }

    /**
     * updates Observer with new game list
     *
     * @param dto new game list
     */
    @Override
    public void updateGameList(ReturnGameListDto dto) {
            for(IClientObserverMenu observer : cOMenu){
                observer.handleGameListUpdate(dto);
            }
    }

    /**
     * updates Observer with new tournament list
     *
     * @param findTournament new tournament list
     */
    @Override
    public void updateFindTournament(ReturnTournamentListDto findTournament) {
        for (IClientObserverMenu observer : cOMenu) {
            observer.handleReturnFindTournament(findTournament);
        }
    }

    /**
     * updates Observer with new tournament info
     *
     * @param tournamentInfo new tournament info
     */
    @Override
    public void updateTournamentInfo(ReturnTournamentInfoDto tournamentInfo) {

    }

    /**
     * updates Observer with new error
     *
     * @param error new error
     */
    @Override
    public void updateError(ErrorDto error) {

    }

    /**
     * updates Observer with new tech data
     *
     * @param techData new tech data
     */
    @Override
    public void updateTechData(ReturnTechDataDto techData) {

    }

    /**
     * updates Observer with new lobby config
     *
     * @param lobbyConfig new lobby config
     * @throws IOException if the fxml file can't be loaded
     */
    @Override
    public void updateGameConfig(ReturnLobbyConfigDto lobbyConfig) throws IOException {
        for(IClientObserverGameplay observer : cOGameplay){
            observer.handleLobbyConfig(lobbyConfig);
        }
    }

    /**
     * updates Observer with new move valid
     *
     * @param move new move valid
     */
    @Override
    public void updateMoveValid(MoveValidDto move) {
        for(IClientObserverGameplay observer : cOGameplay){
            observer.handleMoveValid(move);
        }
    }

    /**
     * updates Observer with new draw cards
     *
     * @param drawCards new draw cards
     */
    @Override
    public void updateDrawCards(DrawCardsDto drawCards) {
        for (IClientObserverGameplay observer :cOGameplay){
            observer.handleDrawCards(drawCards);
        }
    }

    /**
     * updates Observer with new board state
     *
     * @param boardState new board state
     */
    @Override
    public void updateBoardState(BoardStateDto boardState) {
        for (IClientObserverGameplay observer : cOGameplay){
            observer.handleBoardState(boardState);
        }
    }

    /**
     * updates Observer with new update draw cards
     *
     * @param updateDrawCards new update draw cards
     */
    @Override
    public void updateUpdateDrawCards(UpdateDrawCardsDto updateDrawCards) {
        for(IClientObserverGameplay observer : cOGameplay){
            observer.handleUpdateDrawCards(updateDrawCards);
        }
    }

    /**
     * updates Observer with new freeze
     *
     * @param freeze new freeze
     */
    @Override
    public void updateFreeze(FreezeDto freeze) {

    }

    /**
     * updates Observer with new unfreeze
     *
     * @param unfreeze new unfreeze
     */
    @Override
    public void updateUnfreeze(UnfreezeDto unfreeze) {

    }

    /**
     * updates Observer with new cancel
     *
     * @param cancel new cancel
     */
    @Override
    public void updateCancel(CancelDto cancel) {

    }

    /**
     * updates Observer with new kick
     *
     * @param kick new kick
     */
    @Override
    public void updateKick(KickDto kick) {

    }

    /**
     * updates Observer with new live timer
     *
     * @param liveTimer new live timer
     */
    @Override
    public void updateLiveTimer(LiveTimerDto liveTimer) {
        for(IClientObserverGameplay observer : cOGameplay){
            observer.handleUpdateLiveTimer(liveTimer);
        }
    }

    /**
     * updates Observer with new turn timer
     *
     * @param turnTimerDto new turn timer
     */
    @Override
    public void updateTurnTimer(TurnTimerDto turnTimerDto) {
        for(IClientObserverGameplay observer : cOGameplay){
            observer.handleUpdateTurnTimer(turnTimerDto);
        }
    }
}
