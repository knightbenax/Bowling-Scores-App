package app.bowling.bowlingapp.bowling.core.daggger.modules.network;

import dagger.Module;
import dagger.Provides;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

@Module
public class RxCallAdapterFactoryModule {

    @Provides
    public RxJavaCallAdapterFactory provideRxJavaCallAdapterFactory() {
        RxJavaCallAdapterFactory callAdapterFactory = RxJavaCallAdapterFactory.create();
        return callAdapterFactory;
    }
}
