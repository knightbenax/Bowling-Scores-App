package app.bowling.bowlingapp.bowling.viewmodel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import javax.inject.Inject;

import app.bowling.bowlingapp.bowling.BowlingApplication;
import app.bowling.bowlingapp.bowling.CustomTestRunner;
import app.bowling.bowlingapp.bowling.core.database.models.Game;
import app.bowling.bowlingapp.bowling.viewmodels.MainActivityViewModel;

import static junit.framework.TestCase.assertNotNull;

@RunWith(CustomTestRunner.class)
@Config(application = BowlingApplication.class)
public class MainActivityViewModelTest {


    @Inject
    MainActivityViewModel mainActivityViewModel;

    @Before
    public void setUp(){

    }

    @Test
    public void checkViewModelNotNull() throws Exception{
        assertNotNull(mainActivityViewModel);
    }

    @Test
    public void checkGameScreenModelNotNull() throws Exception{
        Game game = new Game();
        assertNotNull(mainActivityViewModel.getActivityGameScreenUIModel());
    }

}
