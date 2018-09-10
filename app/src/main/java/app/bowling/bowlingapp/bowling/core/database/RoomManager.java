package app.bowling.bowlingapp.bowling.core.database;

import android.content.Context;

import app.bowling.bowlingapp.bowling.core.database.models.Game;


public class RoomManager {

    private static RoomManager roomManager;
    private static AppDatabase appDatabase;

    private Context context;

    public static RoomManager getInstance() {
        if (roomManager == null) {
            synchronized (RoomManager.class) {
                if (roomManager == null) {
                    roomManager = new RoomManager();
                }
            }
        }

        return roomManager;
    }

    public RoomManager with(Context context){
        this.context = context;
        appDatabase = AppDatabase.getAppDatabase(context);

        return this;
    }

    public Game getGame(){
        return appDatabase.gameDao().getSingleGame();
    }

    public int getGamesCount(){
        return appDatabase.gameDao().countGames();
    }

    public Game getGameById(String uid){
        return appDatabase.gameDao().getSingleGameById(uid);
    }

    public void saveGame(Game game){

        appDatabase.gameDao().insert(game);
    }

    public void deleteAllGames(){
        appDatabase.gameDao().deleteAll();
    }


}
