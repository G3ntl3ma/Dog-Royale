package Service;

import Dtos.*;
import Enums.TypeGame;
import Enums.TypeMenue;
import com.google.gson.*;

public class Deserializer {

    private String jsonString;
    private Gson gson;
    public Deserializer(String jsonString){
        this.jsonString = jsonString;
        this.gson = new Gson();
    }

    public Class<?> getMessageDtoClass() {
        try{
            JsonElement jsonElement = JsonParser.parseString(jsonString);

            if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                int type = jsonObject.get("type").getAsInt();
                if (type >= 100 && type < 200) {
                    // TypeMenue
                    TypeMenue menuType = TypeMenue.values()[type - 100];
                    switch(menuType){
                        case connectToServer:
                            return ConnectToServerDto.class;
                        case connectedToServer:
                            return ConnectedToServerDto.class;
                        case disconnect:
                            return DisconnectDto.class;
                        case requestGameList:
                            return RequestGameListDto.class;
                        case returnGameList:
                            return ReturnGameListDto.class;
                        case joinGameAsParticipant:
                            return JoinGameAsParticipantDto.class;
                        case joinGameAsObserver:
                            return JoinGameAsObserverDto.class;
                        case connectedToGame:
                            return ConnectedToGameDto.class;
                        case findTournament:
                            return FindTournamentDto.class;
                        case returnFindTournament:
                            return ReturnFindTournamentDto.class;
                        case registerForTournament:
                            return RegisterForTournamentDto.class;
                        case registeredForTournament:
                            return RegisteredForTournamentDto.class;
                        case requestTournamentInfo:
                            return RequestTournamentInfoDto.class;
                        case returnLobbyConfig:
                            return ReturnLobbyConfigDto.class;
                        case error:
                            return ErrorDto.class;
                        case requestTechData:
                            return RequestTechDataDto.class;
                        case returnTechData:
                            return ReturnTechDataDto.class;
                    }
                } else if (type > 200 && type < 300) {
                    //TODO: TypeGame
                    TypeGame gameType = TypeGame.values()[type - 200];
                    switch(gameType){
                        case boardState:
                            return BoardStateDto.class;
                        case cancel:
                            return CancelDto.class;
                        case drawCards:
                            return DrawCardsDto.class;
                        case freeze:
                            return FreezeDto.class;
                        case kick:
                            return KickDto.class;
                        case joinObs:
                            return JoinObsDto.class;
                        case leaveObs:
                            return LeaveObsDto.class;
                        case move:
                            return MoveDto.class;
                        case leavePlayer:
                            return LeavePlayerDto.class;
                        case liveTimer:
                            return LiveTimerDto.class;
                        case moveValid:
                            return MoveValidDto.class;
                        case turnTimer:
                            return TurnTimerDto.class;
                        case response:
                            return ResponseDto.class;
                        case unfreeze:
                            return ReturnLobbyConfigDto.class;
                        case updateDrawCards:
                            return UpdateDrawCardsDto.class;
                    }
                }
            }
        }catch(JsonParseException ignored){
        }

        return null;
    }

    public Dto deserialize()
    {
        Class<?> dtoClass = this.getMessageDtoClass();
        if (dtoClass != null) {
            return (Dto) this.gson.fromJson(this.jsonString, dtoClass);
        }
        return null;
    }
}
