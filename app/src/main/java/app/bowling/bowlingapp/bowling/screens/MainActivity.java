package app.bowling.bowlingapp.bowling.screens;

import android.os.Bundle;

import javax.inject.Inject;

import androidx.databinding.DataBindingUtil;
import app.bowling.bowlingapp.bowling.BowlingApplication;
import app.bowling.bowlingapp.bowling.R;
import app.bowling.bowlingapp.bowling.core.daggger.components.AppMainComponent;
import app.bowling.bowlingapp.bowling.core.views.CoreActivity;
import app.bowling.bowlingapp.bowling.databinding.ActivityMainBinding;
import app.bowling.bowlingapp.bowling.viewmodels.MainActivityViewModel;

public class MainActivity extends CoreActivity {

    @Inject
    MainActivityViewModel mainActivityViewModel;

    AppMainComponent component;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        ActivityUIModel activityUIModel = new ActivityUIModel("Games");
        binding.layoutGamesLink.toolbarLink.setModel(activityUIModel);

        component = BowlingApplication.get(this).getComponent();
        component.inject(this);
    }

    public class ActivityUIModel{

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        String title;

        public ActivityUIModel(String title){
            this.title = title;
        }

    }
}
