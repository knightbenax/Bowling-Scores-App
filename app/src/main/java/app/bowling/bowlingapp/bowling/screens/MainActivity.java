package app.bowling.bowlingapp.bowling.screens;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomappbar.BottomAppBar;

import javax.inject.Inject;

import androidx.appcompat.widget.ActionMenuView;
import androidx.databinding.DataBindingUtil;
import app.bowling.bowlingapp.bowling.BowlingApplication;
import app.bowling.bowlingapp.bowling.R;
import app.bowling.bowlingapp.bowling.core.customviews.BowlingDialog;
import app.bowling.bowlingapp.bowling.core.daggger.components.AppMainComponent;
import app.bowling.bowlingapp.bowling.core.views.CoreActivity;
import app.bowling.bowlingapp.bowling.databinding.ActivityMainBinding;
import app.bowling.bowlingapp.bowling.viewmodels.MainActivityViewModel;

public class MainActivity extends CoreActivity {

    @Inject
    MainActivityViewModel mainActivityViewModel;
    ActivityUIModel activityUIModel;
    AppMainComponent component;


    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        component = BowlingApplication.get(this).getComponent();
        component.inject(this);

        initToolbar();
        initAppStates();
    }

    private void initAppStates() {

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bottom_app_bar_menu, menu);
        return true;
    }

    private void initToolbar(){

        /**
         * This is where all BottomAppBar stuff goes.
         * **/

        BottomAppBar bottomAppBar = binding.layoutGamesLink.bottomAppBar;



        activityUIModel = new ActivityUIModel("Games");
        binding.layoutGamesLink.toolbarLink.setModel(activityUIModel);
        binding.layoutGamesLink.setModel(activityUIModel);
        //setSupportActionBar(toolbar);
        bottomAppBar.inflateMenu(R.menu.bottom_app_bar_menu);
        ActionMenuView actionMenuView = null;

        /**
         * Setting the padding on the BottomAppBar instead clips out the menuview for some
         * reasons. Feel it's a bug.
         *
         * So instead would just get the ActionMenuView which houses the menu and apply the
         * padding to that instead.
         *
         * Reuben 1. Google 0.
         * **/
        for(int i = 0; i < bottomAppBar.getChildCount(); ++i) {
            View view = bottomAppBar.getChildAt(i);
            if (view instanceof ActionMenuView) {
                actionMenuView = (ActionMenuView)view;
               break;
            }
        }


        if (actionMenuView != null){
            //Adding the padding on both ends for Rtl layouts
            actionMenuView.setPadding(15, 0, 15, 0);
        }

    }

    public class ActivityUIModel{
        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }

        String title;
        String playerName;

        public ActivityUIModel(String title){
            this.title = title;
        }

        public void showNewDialog(View view){

            /**
             * Gonna use the primitive findViewById to find
             * my views here because it would be
             * **/




            final BowlingDialog bowlingDialog = new BowlingDialog(MainActivity.this);
            bowlingDialog.setContentView(R.layout.layout_new_dialog);
            Button startButton = bowlingDialog.findViewById(R.id.start_button);
            Button cancelButton = bowlingDialog.findViewById(R.id.cancel_button);
            EditText editText = bowlingDialog.findViewById(R.id.player_name);

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bowlingDialog.cancel();
                }
            });

            startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Save the game and start
                }
            });

            bowlingDialog.show();

        }



    }
}
