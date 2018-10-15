package app.bowling.bowlingapp.bowling.core.daggger.modules.viewmodels;

import app.bowling.bowlingapp.bowling.core.daggger.modules.store.DataStoreModule;
import app.bowling.bowlingapp.bowling.core.database.DataStore;
import app.bowling.bowlingapp.bowling.viewmodels.MainActivityViewModel;
import dagger.Module;
import dagger.Provides;


@Module(includes = {DataStoreModule.class})
public class MainActivityViewModelModule {

    @Provides
    public MainActivityViewModel provideHomeActivityViewModel(DataStore datastore) {
        return new MainActivityViewModel(datastore);
    }
}
