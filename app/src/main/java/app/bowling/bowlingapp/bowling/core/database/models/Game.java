package app.bowling.bowlingapp.bowling.core.database.models;


import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import app.bowling.bowlingapp.bowling.core.converters.FrameScoreTypeConverter;
import app.bowling.bowlingapp.bowling.core.models.FrameScore;

@Entity(tableName = "game")
public class Game {

    @PrimaryKey(autoGenerate = true)
    private long uid;

    @ColumnInfo(name = "player")
    private String player;

    @ColumnInfo(name = "date_started")
    private String date_started;

    @ColumnInfo(name = "date_updated")
    private String date_updated;

    @ColumnInfo(name = "game_finished")
    private boolean game_finished;

    @ColumnInfo(name = "current_frame")
    private int current_frame;

    @ColumnInfo(name = "last_score")
    private int last_score = 14;

    @ColumnInfo(name = "last_box")
    private boolean last_box = false;

    @TypeConverters(FrameScoreTypeConverter.class)
    private List<FrameScore> scores;

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getDate_started() {
        return date_started;
    }

    public void setDate_started(String date_started) {
        this.date_started = date_started;
    }

    public String getDate_updated() {
        return date_updated;
    }

    public void setDate_updated(String date_updated) {
        this.date_updated = date_updated;
    }

    public List<FrameScore> getScores() {
        return scores;
    }

    public void setScores(List<FrameScore> scores) {
        this.scores = scores;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }


    public boolean isGame_finished() {
        return game_finished;
    }

    public void setGame_finished(boolean date_finished) {
        this.game_finished = date_finished;
    }

    public int getCurrent_frame() {
        return current_frame;
    }

    public void setCurrent_frame(int current_frame) {
        this.current_frame = current_frame;
    }

    public boolean isLast_box() {
        return last_box;
    }

    public void setLast_box(boolean last_box) {
        this.last_box = last_box;
    }

    public int getLast_score() {
        return last_score;
    }

    public void setLast_score(int last_score) {
        this.last_score = last_score;
    }
}

