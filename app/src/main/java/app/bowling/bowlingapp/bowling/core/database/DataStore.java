package app.bowling.bowlingapp.bowling.core.database;

import java.util.List;

import javax.inject.Inject;

import app.bowling.bowlingapp.bowling.core.database.models.Game;


public class DataStore {


    private OfflineStore offlineStore;

    @Inject
    public DataStore(OfflineStore offlineStore){
        this.offlineStore = offlineStore;
    }

    public long saveGame(Game game){
        return offlineStore.saveGame(game);
    }

    public int getGamesCount(){
        return offlineStore.getGamesCount();
    }

    public List<Game> getAllGames(){
        return offlineStore.getAllGames();
    }

    public void deleteAllGames(){
        offlineStore.deleteAllGames();
    }

    public Game getGameById(String uid){
        return offlineStore.getGameById(uid);
    }

}
