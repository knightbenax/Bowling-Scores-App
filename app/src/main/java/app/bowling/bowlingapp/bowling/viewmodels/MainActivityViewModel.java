package app.bowling.bowlingapp.bowling.viewmodels;

import android.view.View;

import javax.inject.Inject;

import app.bowling.bowlingapp.bowling.R;
import app.bowling.bowlingapp.bowling.core.customviews.BowlingDialog;
import app.bowling.bowlingapp.bowling.core.database.DataStore;
import app.bowling.bowlingapp.bowling.core.database.models.Game;
import app.bowling.bowlingapp.bowling.core.viewmodels.CoreViewModel;
import app.bowling.bowlingapp.bowling.screens.MainActivity;

public class MainActivityViewModel extends CoreViewModel {

    DataStore dataStore;

    @Inject
    public MainActivityViewModel(DataStore dataStore){
        this.dataStore = dataStore;
    }

    public void saveGame(Game game){
        dataStore.saveGame(game);
    }

    public int gamesCount(){
        return dataStore.getGamesCount();
    }


}
