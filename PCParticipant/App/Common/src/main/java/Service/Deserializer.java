package Service;

import Dtos.*;
import Enums.TypeGame;
import Enums.TypeMenue;
import com.google.gson.*;

public class Deserializer {

    private final String jsonString;
    private final Gson gson;
    private JsonObject jsonObject;
    private Class<?> messageDtoType;
    private Class<?> messageDtoClass;

    public Deserializer(String jsonString){
        this.jsonString = jsonString;
        this.gson = new Gson();

        jsonObject = null;
        try{
            JsonElement jsonElement = JsonParser.parseString(jsonString);
            if(jsonElement.isJsonObject()){
                jsonObject = jsonElement.getAsJsonObject();
                initMessageType();
                initMessageDtoClass();
            }else{
                jsonObject = null;
            }
        }catch(JsonParseException e){
            jsonObject = null;
        }
    }

    public Class<?> getMessageDtoType(){
        return messageDtoType;
    }

    public Class<?> getMessageDtoClass() {
        return messageDtoClass;
    }

    public Dto deserialize()
    {
        Class<?> dtoClass = this.getMessageDtoClass();
        if (dtoClass != null) {
            return (Dto) this.gson.fromJson(this.jsonString, dtoClass);
        }
        return null;
    }

    private void initMessageType(){
        messageDtoType = null;
        JsonElement typeJson = jsonObject.get("type");
        if(typeJson == null){
            return;
        }

        int type = typeJson.getAsInt();
        if (type >= 100 && type < 200) {
            messageDtoType = TypeMenue.class;
        }else if (type >= 200 && type < 300){
            messageDtoType = TypeGame.class;
        }
    }

    private void initMessageDtoClass(){
        messageDtoClass = null;
        JsonElement typeJson = jsonObject.get("type");
        if(typeJson == null){
            return;
        }

        int type = typeJson.getAsInt();
        if (messageDtoType == TypeMenue.class) {
            // TypeMenue
            TypeMenue menuType = TypeMenue.values()[type - 100];
            switch(menuType){
                case connectToServer:
                    messageDtoClass = ConnectToServerDto.class;
                    break;
                case connectedToServer:
                    messageDtoClass = ConnectedToServerDto.class;
                    break;
                case disconnect:
                    messageDtoClass = DisconnectDto.class;
                    break;
                case requestGameList:
                    messageDtoClass = RequestGameListDto.class;
                    break;
                case returnGameList:
                    messageDtoClass = ReturnGameListDto.class;
                    break;
                case joinGameAsPlayer:
                    messageDtoClass = JoinGameAsPlayerDto.class;
                    break;
                case joinGameAsObserver:
                    messageDtoClass = JoinGameAsObserverDto.class;
                    break;
                case connectedToGame:
                    messageDtoClass = ConnectedToGameDto.class;
                    break;
                case requestTournamentList:
                    messageDtoClass = RequestTournamentListDto.class;
                    break;
                case returnTournamentList:
                    messageDtoClass = ReturnTournamentListDto.class;
                    break;
                case registerForTournament:
                    messageDtoClass = RegisterForTournamentDto.class;
                    break;
                case registeredForTournament:
                    messageDtoClass = RegisteredForTournamentDto.class;
                    break;
                case requestTournamentInfo:
                    messageDtoClass = RequestTournamentInfoDto.class;
                    break;
                case returnLobbyConfig:
                    messageDtoClass = ReturnLobbyConfigDto.class;
                    break;
                case error:
                    messageDtoClass = ErrorDto.class;
                    break;
                case requestTechData:
                    messageDtoClass = RequestTechDataDto.class;
                    break;
                case returnTechData:
                    messageDtoClass = ReturnTechDataDto.class;
                    break;
            }
        } else if (messageDtoType == TypeGame.class) {
            //TypeGame
            TypeGame gameType = TypeGame.values()[type - 200];
            switch(gameType){
                case boardState:
                    messageDtoClass = BoardStateDto.class;
                    break;
                case cancel:
                    messageDtoClass = CancelDto.class;
                    break;
                case drawCards:
                    messageDtoClass = DrawCardsDto.class;
                    break;
                case freeze:
                    messageDtoClass = FreezeDto.class;
                    break;
                case kick:
                    messageDtoClass = KickDto.class;
                    break;
                case joinObs:
                    messageDtoClass = JoinObsDto.class;
                    break;
                case leaveObs:
                    messageDtoClass = LeaveObsDto.class;
                    break;
                case move:
                    messageDtoClass = MoveDto.class;
                    break;
                case leavePlayer:
                    messageDtoClass = LeavePlayerDto.class;
                    break;
                case liveTimer:
                    messageDtoClass = LiveTimerDto.class;
                    break;
                case moveValid:
                    messageDtoClass = MoveValidDto.class;
                    break;
                case turnTimer:
                    messageDtoClass = TurnTimerDto.class;
                    break;
                case response:
                    messageDtoClass = ResponseDto.class;
                    break;
                case unfreeze:
                    messageDtoClass = ReturnLobbyConfigDto.class;
                    break;
                case updateDrawCards:
                    messageDtoClass = UpdateDrawCardsDto.class;
                    break;
            }
        }
    }
}
