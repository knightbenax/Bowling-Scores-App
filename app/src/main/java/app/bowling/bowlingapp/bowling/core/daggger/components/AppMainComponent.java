package app.bowling.bowlingapp.bowling.core.daggger.components;

import app.bowling.bowlingapp.bowling.BowlingApplication;
import app.bowling.bowlingapp.bowling.core.daggger.modules.externaldependency.ContextModule;
import app.bowling.bowlingapp.bowling.core.daggger.modules.viewmodels.CoreViewModelModule;
import app.bowling.bowlingapp.bowling.core.daggger.modules.viewmodels.MainActivityViewModelModule;
import app.bowling.bowlingapp.bowling.core.daggger.permissions.PermissionsManagerModule;
import app.bowling.bowlingapp.bowling.core.views.CoreActivity;
import app.bowling.bowlingapp.bowling.screens.MainActivity;
import dagger.Component;

@Component(modules = {ContextModule.class, CoreViewModelModule.class, MainActivityViewModelModule.class, PermissionsManagerModule.class})

public interface AppMainComponent {
    void inject(MainActivity homeActivity);
    void inject(BowlingApplication application);
    void inject(CoreActivity coreActivity);
}
