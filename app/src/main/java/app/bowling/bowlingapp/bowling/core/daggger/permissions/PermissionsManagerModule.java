package app.bowling.bowlingapp.bowling.core.daggger.permissions;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import rabaapp.raba.app.raba.core.daggger.modules.externaldependency.ActivityModule;
import rabaapp.raba.app.raba.core.permissions.PermissionsManager;


@Module(includes = {ActivityModule.class})
public class PermissionsManagerModule {

    @Provides
    public PermissionsManager providePermissionsManager(Activity activity) {
        return new PermissionsManager(activity);
    }
}
