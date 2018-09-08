package app.bowling.bowlingapp.bowling.viewmodels;

import javax.inject.Inject;

import app.bowling.bowlingapp.bowling.core.database.DataStore;
import app.bowling.bowlingapp.bowling.core.viewmodels.CoreViewModel;

public class MainActivityViewModel extends CoreViewModel {

    DataStore dataStore;

    @Inject
    public MainActivityViewModel(DataStore dataStore){
        this.dataStore = dataStore;
    }
}
