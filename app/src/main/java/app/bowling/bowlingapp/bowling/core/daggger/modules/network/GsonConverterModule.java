package app.bowling.bowlingapp.bowling.core.daggger.modules.network;

import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class GsonConverterModule {

    @Provides
    public GsonConverterFactory provideGson() {
        return GsonConverterFactory.create(new GsonBuilder().serializeNulls().create());
    }

}
