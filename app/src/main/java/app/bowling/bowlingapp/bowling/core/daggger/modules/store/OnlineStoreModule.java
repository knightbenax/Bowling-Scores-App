package app.bowling.bowlingapp.bowling.core.daggger.modules.store;

import dagger.Module;
import dagger.Provides;
import rabaapp.raba.app.raba.core.daggger.modules.network.RetrofitModule;
import rabaapp.raba.app.raba.core.database.OnlineStore;
import retrofit2.Retrofit;


@Module(includes = {RetrofitModule.class})
public class OnlineStoreModule {

    @Provides
    public OnlineStore provideOnlineStore(Retrofit retrofit){
        return new OnlineStore(retrofit);
    }

}
