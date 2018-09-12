package app.bowling.bowlingapp.bowling.core.database;

import java.util.List;

import javax.inject.Inject;

import app.bowling.bowlingapp.bowling.core.database.models.Game;

public class OfflineStore {

    private RoomManager roomManager;

    @Inject
    public OfflineStore(RoomManager roomManager){
        this.roomManager = roomManager;
    }

    public long saveGame(Game game){

        return roomManager.saveGame(game);

    }

    public List<Game> getAllGames(){
        return roomManager.getAllGames();
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
