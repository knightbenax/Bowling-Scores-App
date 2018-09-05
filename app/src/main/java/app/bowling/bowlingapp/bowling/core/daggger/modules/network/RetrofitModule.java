package app.bowling.bowlingapp.bowling.core.daggger.modules.network;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import rabaapp.raba.app.raba.BuildConfig;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = {NetworkModule.class, GsonConverterModule.class, RxCallAdapterFactoryModule.class})
public class RetrofitModule {

    @Provides
    public Retrofit provideRetrofit(OkHttpClient okHttpClient, RxJavaCallAdapterFactory callAdapterFactory) {
        //this base is fake and will change url will change
        return new Retrofit.Builder()
                .addCallAdapterFactory(callAdapterFactory)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
//                .baseUrl("http://d0d503e5.ngrok.io")
                .baseUrl(BuildConfig.BASE_URL)
                .build();

    }
}
