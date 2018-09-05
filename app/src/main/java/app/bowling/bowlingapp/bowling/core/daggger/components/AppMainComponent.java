package app.bowling.bowlingapp.bowling.core.daggger.components;

import dagger.Component;
import rabaapp.raba.app.raba.RabaApplication;
import rabaapp.raba.app.raba.core.daggger.modules.network.ContextModule;
import rabaapp.raba.app.raba.core.daggger.modules.viewmodels.CoreViewModelModule;
import rabaapp.raba.app.raba.core.daggger.modules.viewmodels.MainActivityViewModelModule;
import rabaapp.raba.app.raba.core.daggger.permissions.PermissionsManagerModule;
import rabaapp.raba.app.raba.core.views.CoreActivity;
import rabaapp.raba.app.raba.screens.mainactivity.activity.MainActivity;
import rabaapp.raba.app.raba.screens.walletactvity.activity.WalletActivity;

@Component(modules = {ContextModule.class, CoreViewModelModule.class, MainActivityViewModelModule.class, PermissionsManagerModule.class})

public interface AppMainComponent {
    void inject(MainActivity homeActivity);
    void inject(WalletActivity walletActivity);
    void inject(RabaApplication application);
    void inject(CoreActivity coreActivity);
}
