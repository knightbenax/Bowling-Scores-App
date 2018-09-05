package app.bowling.bowlingapp.bowling.core.daggger.modules.viewmodels;

import dagger.Module;
import dagger.Provides;
import rabaapp.raba.app.raba.core.daggger.modules.network.RetrofitModule;
import rabaapp.raba.app.raba.core.daggger.modules.store.DataStoreModule;
import rabaapp.raba.app.raba.core.database.DataStore;
import rabaapp.raba.app.raba.screens.mainactivity.viewmodel.MainActivityViewModel;
import retrofit2.Retrofit;


@Module(includes = {DataStoreModule.class, RetrofitModule.class})
public class MainActivityViewModelModule {

    @Provides
    public MainActivityViewModel provideHomeActivityViewModel(DataStore datastore, Retrofit retrofit) {
        return new MainActivityViewModel(datastore, retrofit);
    }
}
