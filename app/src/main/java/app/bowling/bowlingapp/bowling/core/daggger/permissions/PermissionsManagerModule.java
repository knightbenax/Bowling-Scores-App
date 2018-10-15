package app.bowling.bowlingapp.bowling.core.daggger.permissions;

import android.app.Activity;

import app.bowling.bowlingapp.bowling.core.daggger.modules.externaldependency.ActivityModule;
import app.bowling.bowlingapp.bowling.core.permissions.PermissionsManager;
import dagger.Module;
import dagger.Provides;


@Module(includes = {ActivityModule.class})
public class PermissionsManagerModule {

    @Provides
    public PermissionsManager providePermissionsManager(Activity activity) {
        return new PermissionsManager(activity);
    }
}
