package app.bowling.bowlingapp.bowling.core.models;

import android.util.Log;

import java.util.Calendar;
import java.util.Locale;

import app.bowling.bowlingapp.bowling.BR;
import app.bowling.bowlingapp.bowling.core.database.models.Game;

public class ExtendedGame extends Game {

    private String date_started_readable;



    private String total_score_string;

    int total_score = 0;

    public String getDate_started_readable() {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(Long.parseLong(this.getDate_started()));
        //Log.e("Score", date);
        return android.text.format.DateFormat.format("E, MMM dd yyyy\nhh:mm a", calendar).toString();
        //return date_started_readable;
    }

    public void setDate_started_readable(String date_started_readable) {
        this.date_started_readable = date_started_readable;
    }

    public String getTotal_score_string() {
        calculateScores();
        return total_score_string;
    }

    public void setTotal_score_string(String total_score_string) {
        this.total_score_string = total_score_string;
    }

    private void calculateScores(){

        int total;

        for(int i = 0; i < 10; i++){
            FrameScore frameScore = getScores().get(i);
            if (Integer.parseInt(frameScore.getFirstRow()) == 10) {
                //a strike happened here. Get the next two scores
                FrameScore tempScore = getScores().get(i + 1);
                total = 10 + Integer.parseInt(tempScore.getFirstRow()) + Integer.parseInt(tempScore.getSecondRow());
            } else if ((Integer.parseInt(frameScore.getFirstRow()) + Integer.parseInt(frameScore.getSecondRow())) == 10){
                //a spare happened here. Get the next score
                FrameScore tempScore = getScores().get(i + 1);
                total = 10 + Integer.parseInt(tempScore.getFirstRow());
            } else {
                total = Integer.parseInt(frameScore.getFirstRow()) + Integer.parseInt(frameScore.getSecondRow());
            }

            total_score = total + total_score;

                /*int temp = 0;
                //add all previous scores
                for (int j = i; j >= 0; j--){
                    temp = temp + scores[j];
                    Log.e("Score", String.valueOf(j));
                    Log.e("Score", String.valueOf(temp));
                }

                scores[i] = temp;*/
        }

        total_score_string = String.format("%03d", total_score);
    }
}
