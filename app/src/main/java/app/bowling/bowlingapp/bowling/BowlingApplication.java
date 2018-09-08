package app.bowling.bowlingapp.bowling;

import android.app.Activity;
import android.app.Application;

import app.bowling.bowlingapp.bowling.core.daggger.components.AppMainComponent;
import app.bowling.bowlingapp.bowling.core.daggger.components.DaggerAppMainComponent;
import app.bowling.bowlingapp.bowling.core.daggger.modules.externaldependency.ContextModule;
import app.bowling.bowlingapp.bowling.core.daggger.modules.viewmodels.MainActivityViewModelModule;
import app.bowling.bowlingapp.bowling.core.database.RoomManager;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

public class BowlingApplication extends Application {

    private void initRoom(){
        RoomManager.getInstance().with(this);
    }

    private AppMainComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        initCalligraphy();

        component = DaggerAppMainComponent.builder().
                mainActivityViewModelModule(new MainActivityViewModelModule()).
                contextModule(new ContextModule(this)).build();

        component.inject(this);

        initRoom();
    }


    private void initCalligraphy() {
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/Cera/Cera Pro Regular.otf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
    }

    public AppMainComponent getComponent() {
        return component;
    }


    public static BowlingApplication get(Activity activity) {
        return (BowlingApplication)activity.getApplication();
    }


}
