package app.bowling.bowlingapp.bowling.activity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

import app.bowling.bowlingapp.bowling.BowlingApplication;
import app.bowling.bowlingapp.bowling.CustomTestRunner;
import app.bowling.bowlingapp.bowling.R;
import app.bowling.bowlingapp.bowling.screens.MainActivity;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(CustomTestRunner.class)
@Config(application = BowlingApplication.class)
public class MainActivityTest {

    private MainActivity mainActivity;
    //MainActivityViewModel mainActivityViewModel;

    @Before
    public void setUp(){
        mainActivity = Robolectric.buildActivity(MainActivity.class).resume().get();
        //mainActivityViewModel = mainActivity.mainActivityViewModel;
    }

    @Test
    public void checkActvityNotNull(){
        assertNotNull(mainActivity);
    }

    @Test
    public void shouldHaveCorrectAppName() throws Exception {
        String hello = mainActivity.getResources().getString(R.string.app_name);
        assertThat(hello, equalTo("Bowling"));
    }




}