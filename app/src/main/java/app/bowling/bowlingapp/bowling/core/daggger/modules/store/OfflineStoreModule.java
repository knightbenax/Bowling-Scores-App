package app.bowling.bowlingapp.bowling.core.daggger.modules.store;

import app.bowling.bowlingapp.bowling.core.database.OfflineStore;
import app.bowling.bowlingapp.bowling.core.database.RoomManager;
import dagger.Module;
import dagger.Provides;


@Module(includes = {RoomManagerModule.class})
public class OfflineStoreModule {

    @Provides
    public OfflineStore provideOfflineStore(RoomManager roomManager){
        return new OfflineStore(roomManager);
    }

}
