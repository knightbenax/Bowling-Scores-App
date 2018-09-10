package app.bowling.bowlingapp.bowling.core.database;

import javax.inject.Inject;

import app.bowling.bowlingapp.bowling.core.database.models.Game;

public class OfflineStore {

    private RoomManager roomManager;

    @Inject
    public OfflineStore(RoomManager roomManager){
        this.roomManager = roomManager;
    }

    public void saveGame(Game game){

        roomManager.saveGame(game);

    }

    public int getGamesCount(){
        return roomManager.getGamesCount();
    }

    public void deleteAllGames(){
        roomManager.deleteAllGames();
    }

    public Game getGameById(String uid){
        return roomManager.getGameById(uid);
    }
}
