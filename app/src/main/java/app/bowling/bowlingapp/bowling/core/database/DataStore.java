package app.bowling.bowlingapp.bowling.core.database;

import javax.inject.Inject;

import app.bowling.bowlingapp.bowling.core.database.models.Game;


public class DataStore {


    private OfflineStore offlineStore;

    @Inject
    public DataStore(OfflineStore offlineStore){
        this.offlineStore = offlineStore;
    }

    public void saveGame(Game game){
        offlineStore.saveGame(game);
    }

    public int getGamesCount(){
        return offlineStore.getGamesCount();
    }

    public void deleteAllGames(){
        offlineStore.deleteAllGames();
    }

    public Game getGameById(String uid){
        return offlineStore.getGameById(uid);
    }

}
