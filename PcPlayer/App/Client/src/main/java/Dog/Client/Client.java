package Dog.Client;

import Dog.Client.Interfaces.IClientObservable;
import Dog.Client.Interfaces.IClientObserverGameplay;
import Dog.Client.Interfaces.IClientObserverMenu;
import Dtos.*;
import Service.Deserializer;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client implements IClientObservable {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;
    private int clientID;
    private ArrayList<IClientObserverMenu> cOMenu;
    private ArrayList<IClientObserverGameplay> cOGameplay;

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
    public int getClientID(){
        return clientID;
    }

    public void sendMessage(String message) {
        new Thread(()->{ sendMessageSync(message); }).start();
    }

    private synchronized void sendMessageSync(String message){
        try{
            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }catch(IOException e) {
            closeConnection();
        }
    }

    public void listenForMessage() {
        new Thread(() -> {
            String message;

            while(socket.isConnected()) {
                try {
                    message = bufferedReader.readLine();

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
                    }else if(dtoClass == ReturnFindTournamentDto.class){
                        ReturnFindTournamentDto dto = (ReturnFindTournamentDto) deserializer.deserialize();
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

    @Override
    public void registerObserverGameplay(IClientObserverGameplay observer) {
        cOGameplay.add(observer);
    }

    @Override
    public void removeObserverGameplay(IClientObserverGameplay observer) {
        cOGameplay.remove(observer);
    }

    @Override
    public void registerObserverMenu(IClientObserverMenu observer) {
        cOMenu.add(observer);

    }

    @Override
    public void removeObserverMenu(IClientObserverMenu observer) {
        cOMenu.remove(observer);
    }

    @Override
    public void updateGameList(ReturnGameListDto dto) {
            for(IClientObserverMenu observer : cOMenu){
                observer.handleGameListUpdate(dto);
            }
    }

    @Override
    public void updateFindTournament(ReturnFindTournamentDto findTournament) {

    }

    @Override
    public void updateTournamentInfo(ReturnTournamentInfoDto tournamentInfo) {

    }

    @Override
    public void updateError(ErrorDto error) {

    }

    @Override
    public void updateTechData(ReturnTechDataDto techData) {

    }

    @Override
    public void updateGameConfig(ReturnLobbyConfigDto lobbyConfig) throws IOException {
        for(IClientObserverGameplay observer : cOGameplay){
            observer.handleLobbyConfig(lobbyConfig);
        }
    }

    @Override
    public void updateMoveValid(MoveValidDto move) {
        for(IClientObserverGameplay observer : cOGameplay){
            observer.handleMoveValid(move);
        }
    }

    @Override
    public void updateDrawCards(DrawCardsDto drawCards) {
        for (IClientObserverGameplay observer :cOGameplay){
            observer.handleDrawCards(drawCards);
        }
    }

    @Override
    public void updateBoardState(BoardStateDto boardState) {
        for (IClientObserverGameplay observer : cOGameplay){
            observer.handleBoardState(boardState);
        }
    }

    @Override
    public void updateUpdateDrawCards(UpdateDrawCardsDto updateDrawCards) {
        for(IClientObserverGameplay observer : cOGameplay){
            observer.handleUpdateDrawCards(updateDrawCards);
        }
    }

    @Override
    public void updateFreeze(FreezeDto freeze) {

    }

    @Override
    public void updateUnfreeze(UnfreezeDto unfreeze) {

    }

    @Override
    public void updateCancel(CancelDto cancel) {

    }

    @Override
    public void updateKick(KickDto kick) {

    }

    @Override
    public void updateLiveTimer(LiveTimerDto liveTimer) {
        for(IClientObserverGameplay observer : cOGameplay){
            observer.handleUpdateLiveTimer(liveTimer);
        }
    }

    @Override
    public void updateTurnTimer(TurnTimerDto turnTimerDto) {
        for(IClientObserverGameplay observer : cOGameplay){
            observer.handleUpdateTurnTimer(turnTimerDto);
        }
    }
}
