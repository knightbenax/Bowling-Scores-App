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




}

