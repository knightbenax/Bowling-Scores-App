package app.bowling.bowlingapp.bowling.core.daggger.modules.store;


import dagger.Module;
import dagger.Provides;
import rabaapp.raba.app.raba.core.database.DataStore;
import rabaapp.raba.app.raba.core.database.OfflineStore;
import rabaapp.raba.app.raba.core.database.OnlineStore;


@Module(includes = {OfflineStoreModule.class, OnlineStoreModule.class})
public class DataStoreModule {

    @Provides
    public DataStore provideDataStore(OfflineStore offlineStore, OnlineStore onlineStore) {
        return new DataStore(offlineStore, onlineStore);
    }

}
