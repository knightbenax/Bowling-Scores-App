package app.bowling.bowlingapp.bowling.core.daggger.modules.viewmodels;


import dagger.Module;
import dagger.Provides;
import rabaapp.raba.app.raba.core.daggger.modules.network.RetrofitModule;
import rabaapp.raba.app.raba.core.viewmodels.CoreViewModel;


@Module(includes = {RetrofitModule.class})
public class CoreViewModelModule {

    @Provides
    public CoreViewModel provideCoreViewModel() {
        return new CoreViewModel();
    }
}
