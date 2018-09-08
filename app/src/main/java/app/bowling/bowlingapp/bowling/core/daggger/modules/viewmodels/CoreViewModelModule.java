package app.bowling.bowlingapp.bowling.core.daggger.modules.viewmodels;


import app.bowling.bowlingapp.bowling.core.viewmodels.CoreViewModel;
import dagger.Module;
import dagger.Provides;

@Module
public class CoreViewModelModule {

    @Provides
    public CoreViewModel provideCoreViewModel() {
        return new CoreViewModel();
    }
}
