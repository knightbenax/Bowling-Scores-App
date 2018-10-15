package app.bowling.bowlingapp.bowling.core.daggger.modules.store;


import app.bowling.bowlingapp.bowling.core.database.DataStore;
import app.bowling.bowlingapp.bowling.core.database.OfflineStore;
import dagger.Module;
import dagger.Provides;


@Module(includes = {OfflineStoreModule.class})
public class DataStoreModule {

    @Provides
    public DataStore provideDataStore(OfflineStore offlineStore) {
        return new DataStore(offlineStore);
    }

}
