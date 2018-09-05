package app.bowling.bowlingapp.bowling.core.daggger.modules.store;

import dagger.Module;
import dagger.Provides;
import rabaapp.raba.app.raba.core.database.OfflineStore;
import rabaapp.raba.app.raba.core.database.RoomManager;


@Module(includes = {RoomManagerModule.class})
public class OfflineStoreModule {

    @Provides
    public OfflineStore provideOfflineStore(RoomManager roomManager){
        return new OfflineStore(roomManager);
    }

}
