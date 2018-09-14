package app.bowling.bowlingapp.bowling.core.models;

import android.util.Log;
import android.view.View;

import java.util.Calendar;
import java.util.Locale;

import app.bowling.bowlingapp.bowling.core.database.models.Game;

public class ExtendedGame extends Game {

    private String date_started_readable;

    private String total_score_string;

    int total_score = 0;

    int in_progress_tag = View.VISIBLE;

    public int getAdapter_postion() {
        return adapter_postion;
    }

    public void setAdapter_postion(int adapter_postion) {
        this.adapter_postion = adapter_postion;
    }

    int adapter_postion;

    public String getDate_started_readable() {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(Long.parseLong(this.getDate_started()));
        //Log.e("Score", date);
        return android.text.format.DateFormat.format("E, MMM dd yyyy\nhh:mm a", calendar).toString();
        //return date_started_readable;
    }

    public int getIn_progress_tag() {
        if (isGame_finished()){
            setIn_progress_tag(View.INVISIBLE);
        } else {
            setIn_progress_tag(View.VISIBLE);
        }

        return in_progress_tag;
    }

    public void setIn_progress_tag(int in_progress_tag) {
        this.in_progress_tag = in_progress_tag;
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

    public boolean isIs_selected() {
        return is_selected;
    }

    public void setIs_selected(boolean is_selected) {
        this.is_selected = is_selected;
    }

    private boolean is_selected = false;


    private int getStrikeScores(int i){

        int score = 0;

        FrameScore tempScore = getScores().get(i + 1);
        int temp = Integer.parseInt(tempScore.getFirstRow());
        int temp2 = Integer.parseInt(tempScore.getSecondRow());

        if (temp == 10){
            //this is another preceeding strike
            int next_range = i + 2;
            if (next_range < 10){
                tempScore = getScores().get(next_range);
                if (Integer.parseInt(tempScore.getFirstRow()) != 14){
                    score = 10 + 10 + Integer.parseInt(tempScore.getFirstRow());
                } else {
                    score = 10 + 10;
                }

            } else {
                score = 10 + temp + temp2;
            }
        } else {
            //this check is to take care of the empty state we described as 14.
            //Needs some serious refactoring
            if (temp == 14 || temp2 == 14){
                score = 10;
            } else {
                score = 10 + temp + temp2;
            }
        }

        return score;
    }


    private void calculateScores(){

        int total;

        for(int i = 0; i < 9; i++){
            FrameScore frameScore = getScores().get(i);
            if(!frameScore.getFirstRow().equals("14") && !frameScore.getSecondRow().equals("14")){
                if (Integer.parseInt(frameScore.getFirstRow()) == 10) {
                    //a strike happened here. Get the next two scores
                        total = getStrikeScores(i);
                } else if ((Integer.parseInt(frameScore.getFirstRow()) + Integer.parseInt(frameScore.getSecondRow())) == 10){
                    //a spare happened here. Get the next score
                    FrameScore tempScore = getScores().get(i + 1);
                    int temp = Integer.parseInt(tempScore.getFirstRow());
                    //this check is to take care of the empty state we described as 14.
                    //Needs some serious refactoring
                    if (temp == 14){
                        total = 10;
                    } else {
                        total = 10 + temp;
                    }
                    //total = 10 + Integer.parseInt(tempScore.getFirstRow());
                } else {
                    total = Integer.parseInt(frameScore.getFirstRow()) + Integer.parseInt(frameScore.getSecondRow());
                }

                total_score = total + total_score;
            } else {
                total_score = total_score + 0;
            }

        }

        //add the last rows
        FrameScore frameScore = getScores().get(9);
        total_score = total_score + Integer.parseInt(frameScore.getFirstRow()) + Integer.parseInt(frameScore.getSecondRow());

        //add the last score
        if (getLast_score() != 14){
            total_score = total_score + getLast_score();
        }

        Log.e("Last Score Inner", String.valueOf(getLast_score()));

        total_score_string = String.format("%03d", total_score);
    }
}
