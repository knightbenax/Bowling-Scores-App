package app.bowling.bowlingapp.bowling.core.daggger.modules.store;

import dagger.Module;
import dagger.Provides;
import rabaapp.raba.app.raba.core.database.RoomManager;

@Module
public class RoomManagerModule {

    @Provides
    public RoomManager provideRealmManager() {
        return RoomManager.getInstance();
    }
}
