package app.bowling.bowlingapp.bowling.viewmodels;

import android.content.Context;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.DataBindingUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import app.bowling.bowlingapp.bowling.BR;
import app.bowling.bowlingapp.bowling.R;
import app.bowling.bowlingapp.bowling.core.customviews.BowlingDialog;
import app.bowling.bowlingapp.bowling.core.database.DataStore;
import app.bowling.bowlingapp.bowling.core.database.models.Game;
import app.bowling.bowlingapp.bowling.core.models.FrameScore;
import app.bowling.bowlingapp.bowling.core.viewmodels.CoreViewModel;
import app.bowling.bowlingapp.bowling.core.views.CoreActivity;
import app.bowling.bowlingapp.bowling.databinding.LayoutNewDialogBinding;
import app.bowling.bowlingapp.bowling.screens.MainActivity;

public class MainActivityViewModel extends CoreViewModel {

    DataStore dataStore;

    private ActivityUIModel activityUIModel;

    MainActivity mainActivity;

    private ActivityGameScreenUIModel activityGameScreenUIModel;
    private Context context;

    @Inject
    public MainActivityViewModel(DataStore dataStore){
        this.dataStore = dataStore;
    }

    public long saveGame(Game game){
        return dataStore.saveGame(game);
    }

    public int gamesCount(){
        return dataStore.getGamesCount();
    }

    public List<Game> getAllGames(){
        return dataStore.getAllGames();
    }

    public void deleteGames(String uid){
        dataStore.deleteGames(uid);
    }

    public void setNewActivityUIModelInstance(String title, MainActivity mainActivity){
        activityUIModel = new ActivityUIModel(title, mainActivity);
        context = mainActivity;
        this.mainActivity = mainActivity;
    }

    public void setNewActivityGameScreenUIModelInstance(Game game, MainActivity mainActivity, boolean newGame){
        activityGameScreenUIModel = new ActivityGameScreenUIModel(game, mainActivity, newGame);
        context = mainActivity;
        this.mainActivity = mainActivity;
    }

    public ActivityUIModel getActivityUIModel() {
        return activityUIModel;
    }

    public void setActivityUIModel(ActivityUIModel activityUIModel) {
        this.activityUIModel = activityUIModel;
    }

    public ActivityGameScreenUIModel getActivityGameScreenUIModel() {
        return activityGameScreenUIModel;
    }

    public void setActivityGameScreenUIModel(ActivityGameScreenUIModel activityGameScreenUIModel) {
        this.activityGameScreenUIModel = activityGameScreenUIModel;
    }

    public class ActivityUIModel{
        String title = "";

        int back_btn_visibility = View.VISIBLE;
        String player_name = "";
        BowlingDialog bowlingDialog;

        MainActivity mainActivity;

        public int getBack_btn_visibility() {
            return back_btn_visibility;
        }

        public void setBack_btn_visibility(int back_btn_visibility) {
            this.back_btn_visibility = back_btn_visibility;
        }

        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }

        public String getPlayer_name() {
            return player_name;
        }

        public void setPlayer_name(String player_name) {
            this.player_name = player_name;
        }


        public ActivityUIModel(String title, MainActivity mainActivity){
            this.title = title;
            this.mainActivity = mainActivity;
        }


        public void disableSelected(){
            mainActivity.unselectItems();
        }

        public void showNewDialog(View view){

            /**
             * Databind the dialog and model here
             * **/
            LayoutNewDialogBinding layoutNewDialogBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_new_dialog, null, false);
            bowlingDialog = new BowlingDialog(context);
            bowlingDialog.setContentView(layoutNewDialogBinding.getRoot());
            layoutNewDialogBinding.setModel(this);

            bowlingDialog.show();
        }

        public void cancelDialog(View view){
            if (bowlingDialog != null){
                bowlingDialog.cancel();
            }
        }


        public void startGame(View view){
            if (player_name.length() <= 0){
                //Snackbar.make(bowlingDialog.getWindow().getDecorView(), "You need a player name", Snackbar.LENGTH_SHORT).show();
                Toast.makeText(context, "You need a player name", Toast.LENGTH_SHORT).show();
            } else {
                CoreActivity.hideKeyboard(mainActivity);

                if (bowlingDialog != null){
                    bowlingDialog.dismiss();
                }


                //save to database
                Game game = new Game();
                game.setPlayer(player_name);

                Long timeStamp = System.currentTimeMillis();
                String temp = timeStamp.toString();
                game.setDate_started(temp);

                long new_id = saveGame(game);
                //attach the new_id to this game object
                game.setUid(new_id);
                //go to the game screen
                mainActivity.gameScreenAvailableListener.OnGameScreenAvailable(game);

                //clear the player_name variable
                player_name = "";

                //Go to next screen
            }

        }

    }


    public class ActivityGameScreenUIModel extends BaseObservable {
        String title = "";

        int back_btn_visibility = View.VISIBLE;
        String game_date = "";
        int frame_pins_left[] = new int[10];
        Integer scores[] = new Integer[10];

        String total_score = "000";

        Game currentGame;

        MainActivity mainActivity;

        int in_progress_tag = View.VISIBLE;

        public int getIn_progress_tag() {
            return in_progress_tag;
        }

        public void setIn_progress_tag(int in_progress_tag) {
            this.in_progress_tag = in_progress_tag;
        }

        public String getTotal_score() {
            return total_score;
        }

        public void setTotal_score(String total_score) {
            this.total_score = total_score;
        }

        public String getGame_date() {
            return game_date;
        }

        public void setGame_date(String game_date) {
            this.game_date = game_date;
        }

        public int getBack_btn_visibility() {
            return back_btn_visibility;
        }

        public void setBack_btn_visibility(int back_btn_visibility) {
            this.back_btn_visibility = back_btn_visibility;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }


        public ActivityGameScreenUIModel(Game game, MainActivity mainActivity, boolean newGame){
            this.title = game.getPlayer();
            currentGame = game;
            this.mainActivity = mainActivity;
            //Log.e("Date", game_date);
            //let's format the date to something human readable
            Calendar calendar = Calendar.getInstance(Locale.getDefault());
            calendar.setTimeInMillis(Long.parseLong(game.getDate_started()));
            this.game_date = android.text.format.DateFormat.format("E, MMM dd yyyy\nhh:mm a", calendar).toString();

            if (newGame){
                //if it's a new game initialise the List with empty scores
                loadGameScores(currentGame);
            }
        }

        public void backToGamesScreen(View view){
            mainActivity.backToGamesScreen();
        }

        private void loadGameScores(Game game) {

            //we want to load the game with empty slots so we
            //can put the scores in based on the frame number
            //we are using 14 to represent empty states. Since
            //you can never get a 14 score.
            List<FrameScore> thisList = new ArrayList<>();
            for (int i = 0; i < 10; i++){
                thisList.add(new FrameScore("14", "14"));
            }

            game.setScores(thisList);
            //save the game here. Just incase the user decides to quit at this point
            saveGame(game);
        }


        public void onFirstScoreChanged(CharSequence s, int start, int before, int count) {
            //check if the string is not "" to avoid a NumberFormatException
            if (s.toString().length() > 0){
                int temp = Integer.parseInt(s.toString());

                mainActivity.disableNextSetOfEditText(s);
                mainActivity.setIndexBasedOnSelectedEditText(s);

                int index = mainActivity.getCurrent_frame();
                //always wipe the corresponding frame second edit text so that the scores can be updated.
                mainActivity.getCurrentSecondEditText(s).setText("");
                mainActivity.getCurrentSecondEditText(s).setFilters(new InputFilter[] { new InputFilter.LengthFilter(2)});
                frame_pins_left[index] = 10;

                if (temp > 10){
                    //Value entered is more than allowed. Wipe value
                    mainActivity.getCurrentFirstEditText(s).setText("");
                } else if (temp == 10){
                    //a damn strike
                    if (index == 9){
                        //check if it's the ten frame and enable a 3 play on a strike
                        //if so, show the last field
                        mainActivity.showLastField();
                        currentGame.getScores().get(index).setFirstRow("10");
                        frame_pins_left[index] = 10;
                    } else {
                        currentGame.getScores().get(index).setFirstRow("10");
                        currentGame.getScores().get(index).setSecondRow("0");
                        mainActivity.getCurrentSecondEditText(s).setText("0");
                        frame_pins_left[index] = 0;
                    }


                } else {
                    //the number of pins knocked down is less than ten.
                    frame_pins_left[index] = 10 - temp;
                    //set the max length to one. Since the next pin cannot be more than ten.
                    //Not possible. Except with voodoo
                    mainActivity.getCurrentSecondEditText(s).setFilters(new InputFilter[] { new InputFilter.LengthFilter(1)});
                    currentGame.getScores().get(index).setFirstRow(String.valueOf(temp));

                }

                calculateScores();
            }
        }


        public void onFinalFirstScoreChanged(CharSequence s, int start, int before, int count) {
            //check if the string is not "" to avoid a NumberFormatException
            if (s.toString().length() > 0){
                int temp = Integer.parseInt(s.toString());

                mainActivity.setIndexBasedOnSelectedEditText(s);
                int index = mainActivity.getCurrent_frame();

                if (temp > 10){
                    //Value entered is more than allowed. Wipe value
                    mainActivity.getCurrentFirstEditText(s).setText("");
                } else if (temp == 10){
                    //a damn strike
                    //check if it's the ten frame and enable a 3 play on a strike
                    //if so, show the last field
                    mainActivity.showLastField();
                    currentGame.setLast_box(true);
                    currentGame.getScores().get(index).setFirstRow("10");
                    frame_pins_left[index] = 10;
                } else {
                    //the number of pins knocked down is less than ten.
                    frame_pins_left[index] = 10 - temp;
                    //set the max length to one. Since the next pin cannot be more than ten.
                    //Not possible. Except with voodoo
                    mainActivity.getCurrentSecondEditText(s).setFilters(new InputFilter[] { new InputFilter.LengthFilter(1)});
                    currentGame.getScores().get(index).setFirstRow(String.valueOf(temp));

                }
            }
        }

        public void onSecondScoreChanged(CharSequence s, int start, int before, int count) {

            //check if the string is not "" to avoid a NumberFormatException
            if (s.toString().length() > 0){
                int temp = Integer.parseInt(s.toString());
                int index = mainActivity.getCurrent_frame();

                if (temp > frame_pins_left[index]){
                    //value entered is more than allowed. Wipe value
                    mainActivity.getCurrentSecondEditText(s).setText("");
                } else if (temp == frame_pins_left[index]) {
                    //a spare has happened. Change the UI.
                    currentGame.getScores().get(index).setSecondRow(String.valueOf(temp));
                    if (index == 9){
                        //a spare has happened or strike has happened
                        mainActivity.showLastField();
                    } else {
                        //currentGame.getScores().get(index).setSecondRow(String.valueOf(temp));
                        mainActivity.enableNextSetOfEditText(s);
                    }
                    calculateScores();
                } else {
                    //not a spare
                    currentGame.getScores().get(index).setSecondRow(String.valueOf(temp));
                    mainActivity.enableNextSetOfEditText(s);
                    calculateScores();

                }
            }
        }


        public void onFinalSecondScoreChanged(CharSequence s, int start, int before, int count) {

            //check if the string is not "" to avoid a NumberFormatException
            if (s.toString().length() > 0){
                int temp = Integer.parseInt(s.toString());
                int index = mainActivity.getCurrent_frame();

                if (temp > frame_pins_left[index]){
                    //value entered is more than allowed. Wipe value
                    mainActivity.getCurrentSecondEditText(s).setText("");
                } else if (temp == frame_pins_left[index]) {
                    //a strike has happened.
                    currentGame.getScores().get(index).setSecondRow(String.valueOf(temp));
                    mainActivity.showLastField();
                    currentGame.setLast_box(true);
                    calculateScores();
                } else {
                    //not a spare
                    currentGame.getScores().get(index).setSecondRow(String.valueOf(temp));
                    if(!currentGame.isLast_box()){
                        currentGame.setGame_finished(true);
                    }
                    calculateScores();
                }
            }
        }


        public void onFinalScoreChanged(CharSequence s, int start, int before, int count) {

            //check if the string is not "" to avoid a NumberFormatException
            if (s.toString().length() > 0){
                int temp = Integer.parseInt(s.toString());
               if (temp > 10){
                    mainActivity.getLastEditText().setText("");
               } else {
                   currentGame.setLast_score(temp);
                   currentGame.setGame_finished(true);
                   calculateScores();
               }
            }
        }


        int getStrikeScores(int i){

            int score = 0;
            FrameScore tempScore = currentGame.getScores().get(i + 1);
            int temp = Integer.parseInt(tempScore.getFirstRow());
            int temp2 = Integer.parseInt(tempScore.getSecondRow());

            if (temp == 10){
                //this is another preceeding strike
                int next_range = i + 2;
                if (next_range < 10){
                    tempScore = currentGame.getScores().get(next_range);
                    //score = 10 + 10 + Integer.parseInt(tempScore.getFirstRow());
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


        public void calculateScores(){

            int total = 0;

            for(int i = 0; i < 9; i++){
                FrameScore frameScore = currentGame.getScores().get(i);
                if(!frameScore.getFirstRow().equals("14") && !frameScore.getSecondRow().equals("14")){
                    if (Integer.parseInt(frameScore.getFirstRow()) == 10) {
                        //a strike happened here. Get the next two scores
                        scores[i] = getStrikeScores(i);
                    } else if ((Integer.parseInt(frameScore.getFirstRow()) + Integer.parseInt(frameScore.getSecondRow())) == 10){
                        //a spare happened here. Get the next score
                            FrameScore tempScore = currentGame.getScores().get(i + 1);
                            int temp = Integer.parseInt(tempScore.getFirstRow());
                            //this check is to take care of the empty state we described as 14.
                            //Needs some serious refactoring
                            if (temp == 14){
                                scores[i] = 10;
                            } else {
                                scores[i] = 10 + temp;
                            }
                    } else {
                       scores[i] = Integer.parseInt(frameScore.getFirstRow()) + Integer.parseInt(frameScore.getSecondRow());
                    }

                    total = total + scores[i];
                } else {
                    total = total;
                }
            }

            //add the last rows
            FrameScore frameScore = currentGame.getScores().get(9);
            total = total + Integer.parseInt(frameScore.getFirstRow()) + Integer.parseInt(frameScore.getSecondRow());

            //add the last score
            if (currentGame.getLast_score() != 14){
                total = total + currentGame.getLast_score();
            }

            scores[9] = total;

            if (currentGame.isGame_finished()){
                setIn_progress_tag(View.INVISIBLE);
            } else {
                setIn_progress_tag(View.VISIBLE);
            }

            Long timeStamp = System.currentTimeMillis();
            String temp = timeStamp.toString();

            currentGame.setCurrent_frame(mainActivity.getCurrent_frame());
            currentGame.setDate_updated(temp);
            saveGame(currentGame);

            total_score = String.format("%03d", total);

            notifyPropertyChanged(BR._all);
            mainActivity.displayScores(scores);
            //frameScore = currentGame.getScores().get(1);

        }

    }

}
