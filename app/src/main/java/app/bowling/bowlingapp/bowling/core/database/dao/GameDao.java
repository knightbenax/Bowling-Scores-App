package app.bowling.bowlingapp.bowling.core.database.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import app.bowling.bowlingapp.bowling.core.database.models.Game;


@Dao
public interface GameDao {

    @Query("SELECT * FROM game")
    List<Game> getAll();

    @Query("SELECT * FROM game ORDER BY uid DESC LIMIT 1")
    Game getSingleGame();

    @Query("SELECT * FROM game WHERE uid = :uid LIMIT 1")
    Game getSingleGameById(String uid);

    @Query("SELECT * FROM game WHERE player LIKE :player")
    Game findByPlayer(String player);

    @Query("SELECT * FROM game WHERE date_started LIKE :date_started")
    Game findByDate(String date_started);

    @Query("SELECT COUNT(*) from game")
    int countGames();

    @Insert
    void insertAll(Game... games);

    @Delete
    void delete(Game game);

    @Query("DELETE FROM game")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Game game);

}
