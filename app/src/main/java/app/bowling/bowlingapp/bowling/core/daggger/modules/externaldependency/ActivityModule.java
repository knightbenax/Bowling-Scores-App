package app.bowling.bowlingapp.bowling.core.daggger.modules.externaldependency;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;


@Module
public class ActivityModule {

    Activity activity;
    @Provides
    public Activity provideActivity() {
        return this.activity;
    }
}
