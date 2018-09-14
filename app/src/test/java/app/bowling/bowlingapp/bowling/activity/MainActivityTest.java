package app.bowling.bowlingapp.bowling.activity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import app.bowling.bowlingapp.bowling.BuildConfig;
import app.bowling.bowlingapp.bowling.screens.MainActivity;
import app.bowling.bowlingapp.bowling.viewmodels.MainActivityViewModel;

import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {


    private MainActivity mainActivity;
    MainActivityViewModel mainActivityViewModel;

    /*@InjectMocks
    private
    MainActivityViewModel mainActivityViewModel;

    @Mock
    private
    MainActivityViewModel.ActivityGameScreenUIModel activityGameScreenUIModel;*/

    @Before
    public void setUp(){
        mainActivity = Robolectric.setupActivity(MainActivity.class);
        mainActivityViewModel = mainActivity.mainActivityViewModel;
    }

    @Test
    public void checkActvityNotNull() throws Exception{
        assertNotNull(mainActivityViewModel);
    }


}