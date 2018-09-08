package app.bowling.bowlingapp.bowling.core.daggger.modules.store;

import app.bowling.bowlingapp.bowling.core.database.RoomManager;
import dagger.Module;
import dagger.Provides;

@Module
public class RoomManagerModule {

    @Provides
    public RoomManager provideRealmManager() {
        return RoomManager.getInstance();
    }
}
