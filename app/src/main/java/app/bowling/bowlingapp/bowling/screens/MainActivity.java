package app.bowling.bowlingapp.bowling.screens;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.bottomappbar.BottomAppBar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.ColorRes;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;
import app.bowling.bowlingapp.bowling.BowlingApplication;
import app.bowling.bowlingapp.bowling.R;
import app.bowling.bowlingapp.bowling.core.daggger.components.AppMainComponent;
import app.bowling.bowlingapp.bowling.core.database.models.Game;
import app.bowling.bowlingapp.bowling.core.models.ExtendedGame;
import app.bowling.bowlingapp.bowling.core.utils.BowlingAppState;
import app.bowling.bowlingapp.bowling.core.viewadapters.GamesAdapter;
import app.bowling.bowlingapp.bowling.core.views.CoreActivity;
import app.bowling.bowlingapp.bowling.databinding.ActivityMainBinding;
import app.bowling.bowlingapp.bowling.viewmodels.MainActivityViewModel;

public class MainActivity extends CoreActivity {

    @Inject
    public MainActivityViewModel mainActivityViewModel;

    AppMainComponent component;
    public GameScreenAvailable gameScreenAvailableListener;

    ActivityMainBinding binding;
    BottomAppBar bottomAppBar;

    private int current_frame = 0;
    private int total_score = 0;

    GamesAdapter gamesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        component = BowlingApplication.get(this).getComponent();
        component.inject(this);

        initListeners();
        initToolbar();
        initAppState();
        initRecyclerView();
        loadGames();
    }

    private void initRecyclerView() {
        gamesAdapter = new GamesAdapter(this);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);
        binding.layoutGamesLink.gamesList.setLayoutManager(llm);
        binding.layoutGamesLink.gamesList.setAdapter(gamesAdapter);
        binding.layoutGamesLink.gamesList.setHasFixedSize(true);
    }

    @Override
    public void onBackPressed(){
        if (BowlingAppState.getInstance().getCurrentScreen().equals(BowlingAppState.GAME_SCREEN)){
            //go back to main screen
           backToGamesScreen();
        } else {
            super.onBackPressed();
        }
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.app_bar_search:
                return true;
            case R.id.app_bar_trash:
                deleteGames();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bottom_app_bar_menu, menu);
        return true;
    }

    public void backToGamesScreen(){
        animateToGamesList();
        BowlingAppState.getInstance().setCurrentScreen(BowlingAppState.GAMES);
        loadGames();
        CoreActivity.hideKeyboard(this);
    }

    private void initListeners(){

        gameScreenAvailableListener = new GameScreenAvailable() {
            @Override
            public void OnGameScreenAvailable(Game currentGame) {
                //show the game screen layout
                BowlingAppState.getInstance().setCurrentScreen(BowlingAppState.GAME_SCREEN);
                initNewGame(currentGame);
            }

            @Override
            public void OnOldGameScreenAvailable(Game currentGame) {
                BowlingAppState.getInstance().setCurrentScreen(BowlingAppState.GAME_SCREEN);
                initOldGame(currentGame);
            }
        };
    }

    private void initAppState() {
        BowlingAppState.getInstance().setCurrentScreen(BowlingAppState.GAMES);
        mainActivityViewModel.getActivityUIModel().setBack_btn_visibility(View.GONE);
    }

    private void loadGames(){

        if (mainActivityViewModel.gamesCount() > 0){
            setVisibility(binding.layoutGamesLink.noGamesHolder, View.GONE);
            gamesAdapter.setGameList(mainActivityViewModel.getAllGames());
            gamesAdapter.notifyDataSetChanged();
            bottomAppBar.getMenu().getItem(0).setEnabled(true);
            bottomAppBar.getMenu().getItem(1).setEnabled(false);
            tintMenuIcon(bottomAppBar.getMenu().getItem(0), R.color.black);
            tintMenuIcon(bottomAppBar.getMenu().getItem(1), R.color.gray);
        } else {
            //there are available games. else show the default empty
            setVisibility(binding.layoutGamesLink.noGamesHolder, View.VISIBLE);
            bottomAppBar.getMenu().getItem(0).setEnabled(false);
            bottomAppBar.getMenu().getItem(1).setEnabled(false);
            tintMenuIcon(bottomAppBar.getMenu().getItem(0), R.color.gray);
            tintMenuIcon(bottomAppBar.getMenu().getItem(1), R.color.gray);
        }

    }

    public void enableDelete(){
        boolean enabled = false;

        for (ExtendedGame extendedGame: gamesAdapter.getExtendedGames()) {
            if (extendedGame.isIs_selected()){
                enabled = true;
                break;
            }
        }

        if (enabled){
            bottomAppBar.getMenu().getItem(1).setEnabled(true);
            tintMenuIcon(bottomAppBar.getMenu().getItem(1), R.color.black);
        } else {
            bottomAppBar.getMenu().getItem(1).setEnabled(false);
            tintMenuIcon(bottomAppBar.getMenu().getItem(1), R.color.gray);
        }

    }

    private void deleteGames(){
        List<ExtendedGame> toRemove = new ArrayList<>();

        for (ExtendedGame extendedGame: gamesAdapter.getExtendedGames()) {
            if (extendedGame.isIs_selected()){
                mainActivityViewModel.deleteGames(String.valueOf(extendedGame.getUid()));
                toRemove.add(extendedGame);
                gamesAdapter.removeAt(extendedGame.getAdapter_postion());
            }
        }

        gamesAdapter.getExtendedGames().removeAll(toRemove);
        //gamesAdapter.clearExtendedGames();
        loadGames();
        enableDelete();
    }

    public void unselectItems(){
        for (ExtendedGame extendedGame: gamesAdapter.getExtendedGames()) {
            extendedGame.setIs_selected(false);
        }

        gamesAdapter.notifyDataSetChanged();
        enableDelete();
    }

    public void tintMenuIcon(MenuItem item, @ColorRes int color) {
        Drawable normalDrawable = item.getIcon();
        Drawable wrapDrawable = DrawableCompat.wrap(normalDrawable);
        DrawableCompat.setTint(wrapDrawable, getResources().getColor(color));

        item.setIcon(wrapDrawable);
    }

    private void initToolbar(){

        /**
         * This is where all BottomAppBar stuff goes.
         * **/
        bottomAppBar = binding.layoutGamesLink.bottomAppBar;

        //
        mainActivityViewModel.setNewActivityUIModelInstance("Games", this);
        binding.layoutGamesLink.toolbarLink.setModel(mainActivityViewModel.getActivityUIModel());
        binding.layoutGamesLink.setModel(mainActivityViewModel.getActivityUIModel());
        //setSupportActionBar(toolbar);
        bottomAppBar.inflateMenu(R.menu.bottom_app_bar_menu);
        ActionMenuView actionMenuView = null;

        //Because of screen orientation refresh changes;
        //
        bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);
        bottomAppBar.setFabCradleRoundedCornerRadius(getResources().getDimension(R.dimen.size_4));
        bottomAppBar.setFabCradleMargin(getResources().getDimension(R.dimen.size_8));

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

        /**
         * Move this to the Model and Databind
         * the hell out of it.
         * **/
        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.app_bar_search:
                        return true;
                    case R.id.app_bar_trash:
                        deleteGames();
                        return true;
                    default:
                        return false;
                }
            }
        });

    }

    private void setVisibility(View view, int visibility){
        view.setVisibility(visibility);
    }

    private int getVisibility(View view){
        return view.getVisibility();
    }

    public int getCurrent_frame() {
        return current_frame;
    }

    public void setCurrent_frame(int current_frame) {
        this.current_frame = current_frame;
    }

    public EditText getLastEditText(){
        return binding.layoutGameScreenLink.fourthRow.three;
    }

    public void showLastField() {
        setVisibility(binding.layoutGameScreenLink.fourthRow.three, View.VISIBLE);
        binding.layoutGameScreenLink.fourthRow.three.setEnabled(true);
        binding.layoutGameScreenLink.fourthRow.lastFrameParent.setWeightSum(3);
    }

    public interface GameScreenAvailable{
        void OnGameScreenAvailable(Game currentGame);
        void OnOldGameScreenAvailable(Game currentGame);
    }

    private void animationStart(int value, View firstTarget, View secondTarget, View thirdTarget){

        //TransitionSet mStaggeredTransition = new TransitionSet();
        AutoTransition autoTransition = new AutoTransition();
        autoTransition.setDuration(value);
        autoTransition.addTarget(firstTarget).setStartDelay(20).addTarget(secondTarget).setStartDelay(10).addTarget(thirdTarget);

        TransitionManager.beginDelayedTransition(binding.parent, autoTransition);
    }


    public void animateToGameScreen(){
        animationStart(300, bottomAppBar, binding.layoutGameScreenLink.getRoot(), binding.layoutGamesLink.getRoot());

        bottomAppBar.setFabCradleMargin(0);
        bottomAppBar.setFabCradleRoundedCornerRadius(0);
        bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams)bottomAppBar.getLayoutParams();
        layoutParams.height = 0;
        bottomAppBar.setLayoutParams(layoutParams);

        setVisibility(binding.layoutGamesLink.getRoot(), View.GONE);
        setVisibility(binding.layoutGameScreenLink.getRoot(), View.VISIBLE);
    }


    public void animateToGamesList(){
        //The animations have to happen
        //in the reverse order this time
        animationStart(200, bottomAppBar, binding.layoutGameScreenLink.getRoot(), binding.layoutGamesLink.getRoot());

        setVisibility(binding.layoutGameScreenLink.getRoot(), View.GONE);
        setVisibility(binding.layoutGamesLink.getRoot(), View.VISIBLE);

        bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);
        bottomAppBar.setFabCradleRoundedCornerRadius(getResources().getDimension(R.dimen.size_4));
        bottomAppBar.setFabCradleMargin(getResources().getDimension(R.dimen.size_8));

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams)bottomAppBar.getLayoutParams();
        layoutParams.height = CoordinatorLayout.LayoutParams.WRAP_CONTENT;
        bottomAppBar.setLayoutParams(layoutParams);
    }

    private void initOldGame(Game oldGame){

        mainActivityViewModel.setNewActivityGameScreenUIModelInstance(oldGame, MainActivity.this, false);
        mainActivityViewModel.getActivityGameScreenUIModel().setBack_btn_visibility(View.VISIBLE);
        binding.layoutGameScreenLink.toolbarLink.setModel(mainActivityViewModel.getActivityGameScreenUIModel());

        current_frame = 0;
        total_score = 0;

        animateToGameScreen();

        //we want to reset all the frame UIs to be set at the beginning;
        binding.layoutGameScreenLink.firstRow.one.setEnabled(true);
        binding.layoutGameScreenLink.firstRow.two.setEnabled(true);
        binding.layoutGameScreenLink.firstRow.three.setEnabled(false);
        binding.layoutGameScreenLink.firstRow.four.setEnabled(false);
        binding.layoutGameScreenLink.firstRow.five.setEnabled(false);
        binding.layoutGameScreenLink.firstRow.six.setEnabled(false);

        binding.layoutGameScreenLink.secondRow.one.setEnabled(false);
        binding.layoutGameScreenLink.secondRow.two.setEnabled(false);
        binding.layoutGameScreenLink.secondRow.three.setEnabled(false);
        binding.layoutGameScreenLink.secondRow.four.setEnabled(false);
        binding.layoutGameScreenLink.secondRow.five.setEnabled(false);
        binding.layoutGameScreenLink.secondRow.six.setEnabled(false);

        binding.layoutGameScreenLink.thirdRow.one.setEnabled(false);
        binding.layoutGameScreenLink.thirdRow.two.setEnabled(false);
        binding.layoutGameScreenLink.thirdRow.three.setEnabled(false);
        binding.layoutGameScreenLink.thirdRow.four.setEnabled(false);
        binding.layoutGameScreenLink.thirdRow.five.setEnabled(false);
        binding.layoutGameScreenLink.thirdRow.six.setEnabled(false);

        binding.layoutGameScreenLink.fourthRow.one.setEnabled(false);
        binding.layoutGameScreenLink.fourthRow.two.setEnabled(false);

        binding.layoutGameScreenLink.firstRow.setModel(mainActivityViewModel.getActivityGameScreenUIModel());
        binding.layoutGameScreenLink.secondRow.setModel(mainActivityViewModel.getActivityGameScreenUIModel());
        binding.layoutGameScreenLink.thirdRow.setModel(mainActivityViewModel.getActivityGameScreenUIModel());
        binding.layoutGameScreenLink.fourthRow.setModel(mainActivityViewModel.getActivityGameScreenUIModel());

        setVisibility(binding.layoutGameScreenLink.fourthRow.three, View.GONE);
        binding.layoutGameScreenLink.fourthRow.three.setEnabled(false);
        binding.layoutGameScreenLink.fourthRow.lastFrameParent.setWeightSum(2);

        setCurrent_frame(oldGame.getCurrent_frame());
        populateScores(oldGame);
    }

    private void initNewGame(Game currentGame){
        mainActivityViewModel.setNewActivityGameScreenUIModelInstance(currentGame, MainActivity.this, true);
        mainActivityViewModel.getActivityGameScreenUIModel().setBack_btn_visibility(View.VISIBLE);
        binding.layoutGameScreenLink.toolbarLink.setModel(mainActivityViewModel.getActivityGameScreenUIModel());

        animateToGameScreen();

        current_frame = 0;
        total_score = 0;

        //we want to reset all the frame UIs to be set at the beginning;
        binding.layoutGameScreenLink.firstRow.one.setEnabled(true);
        binding.layoutGameScreenLink.firstRow.two.setEnabled(true);
        binding.layoutGameScreenLink.firstRow.three.setEnabled(false);
        binding.layoutGameScreenLink.firstRow.four.setEnabled(false);
        binding.layoutGameScreenLink.firstRow.five.setEnabled(false);
        binding.layoutGameScreenLink.firstRow.six.setEnabled(false);

        binding.layoutGameScreenLink.secondRow.one.setEnabled(false);
        binding.layoutGameScreenLink.secondRow.two.setEnabled(false);
        binding.layoutGameScreenLink.secondRow.three.setEnabled(false);
        binding.layoutGameScreenLink.secondRow.four.setEnabled(false);
        binding.layoutGameScreenLink.secondRow.five.setEnabled(false);
        binding.layoutGameScreenLink.secondRow.six.setEnabled(false);

        binding.layoutGameScreenLink.thirdRow.one.setEnabled(false);
        binding.layoutGameScreenLink.thirdRow.two.setEnabled(false);
        binding.layoutGameScreenLink.thirdRow.three.setEnabled(false);
        binding.layoutGameScreenLink.thirdRow.four.setEnabled(false);
        binding.layoutGameScreenLink.thirdRow.five.setEnabled(false);
        binding.layoutGameScreenLink.thirdRow.six.setEnabled(false);

        binding.layoutGameScreenLink.fourthRow.one.setEnabled(false);
        binding.layoutGameScreenLink.fourthRow.two.setEnabled(false);

        binding.layoutGameScreenLink.firstRow.one.setText("");
        binding.layoutGameScreenLink.firstRow.two.setText("");
        binding.layoutGameScreenLink.firstRow.three.setText("");
        binding.layoutGameScreenLink.firstRow.four.setText("");
        binding.layoutGameScreenLink.firstRow.five.setText("");
        binding.layoutGameScreenLink.firstRow.six.setText("");

        binding.layoutGameScreenLink.secondRow.one.setText("");
        binding.layoutGameScreenLink.secondRow.two.setText("");
        binding.layoutGameScreenLink.secondRow.three.setText("");
        binding.layoutGameScreenLink.secondRow.four.setText("");
        binding.layoutGameScreenLink.secondRow.five.setText("");
        binding.layoutGameScreenLink.secondRow.six.setText("");

        binding.layoutGameScreenLink.thirdRow.one.setText("");
        binding.layoutGameScreenLink.thirdRow.two.setText("");
        binding.layoutGameScreenLink.thirdRow.three.setText("");
        binding.layoutGameScreenLink.thirdRow.four.setText("");
        binding.layoutGameScreenLink.thirdRow.five.setText("");
        binding.layoutGameScreenLink.thirdRow.six.setText("");

        binding.layoutGameScreenLink.fourthRow.one.setText("");
        binding.layoutGameScreenLink.fourthRow.two.setText("");

        binding.layoutGameScreenLink.firstRow.scoreOne.setText("");
        binding.layoutGameScreenLink.firstRow.scoreTwo.setText("");
        binding.layoutGameScreenLink.firstRow.scoreThree.setText("");
        binding.layoutGameScreenLink.secondRow.scoreOne.setText("");
        binding.layoutGameScreenLink.secondRow.scoreTwo.setText("");
        binding.layoutGameScreenLink.secondRow.scoreThree.setText("");
        binding.layoutGameScreenLink.thirdRow.scoreOne.setText("");
        binding.layoutGameScreenLink.thirdRow.scoreTwo.setText("");
        binding.layoutGameScreenLink.thirdRow.scoreThree.setText("");
        binding.layoutGameScreenLink.fourthRow.scoreOne.setText("");

        binding.layoutGameScreenLink.firstRow.setModel(mainActivityViewModel.getActivityGameScreenUIModel());
        binding.layoutGameScreenLink.secondRow.setModel(mainActivityViewModel.getActivityGameScreenUIModel());
        binding.layoutGameScreenLink.thirdRow.setModel(mainActivityViewModel.getActivityGameScreenUIModel());
        binding.layoutGameScreenLink.fourthRow.setModel(mainActivityViewModel.getActivityGameScreenUIModel());

        setVisibility(binding.layoutGameScreenLink.fourthRow.three, View.GONE);
        binding.layoutGameScreenLink.fourthRow.three.setText("");
        binding.layoutGameScreenLink.fourthRow.three.setEnabled(false);
        binding.layoutGameScreenLink.fourthRow.lastFrameParent.setWeightSum(2);
    }

    public void setIndexBasedOnSelectedEditText(CharSequence s){
        //this method sets the index based on the selected first score box of each frame

        if (s.hashCode() == binding.layoutGameScreenLink.firstRow.one.getText().hashCode()){
            setCurrent_frame(0);
        } else if (s.hashCode() == binding.layoutGameScreenLink.firstRow.three.getText().hashCode()){
            setCurrent_frame(1);
        } else if (s.hashCode() == binding.layoutGameScreenLink.firstRow.five.getText().hashCode()){
            setCurrent_frame(2);
        } else if (s.hashCode() == binding.layoutGameScreenLink.secondRow.one.getText().hashCode()){
            setCurrent_frame(3);
        } else if (s.hashCode() == binding.layoutGameScreenLink.secondRow.three.getText().hashCode()){
            setCurrent_frame(4);
        } else if (s.hashCode() == binding.layoutGameScreenLink.secondRow.five.getText().hashCode()){
            setCurrent_frame(5);
        } else if (s.hashCode() == binding.layoutGameScreenLink.thirdRow.one.getText().hashCode()){
            setCurrent_frame(6);
        } else if (s.hashCode() == binding.layoutGameScreenLink.thirdRow.three.getText().hashCode()){
            setCurrent_frame(7);
        } else if (s.hashCode() == binding.layoutGameScreenLink.thirdRow.five.getText().hashCode()){
            setCurrent_frame(8);
        } else if (s.hashCode() == binding.layoutGameScreenLink.fourthRow.one.getText().hashCode()){
            setCurrent_frame(9);
        }

        //Log.e("RE", String.valueOf(getCurrent_frame()));
    }


    public EditText getCurrentFirstEditText(CharSequence s){
        EditText editText = null;

        //Log.e("Frame Inside First", String.valueOf(getCurrent_frame()));

        /*switch (getCurrent_frame()){
            case 0:
                editText = binding.layoutGameScreenLink.firstRow.one;
                break;
            case 1:
                editText = binding.layoutGameScreenLink.firstRow.three;
                break;
            case 2:
                editText = binding.layoutGameScreenLink.firstRow.five;
                break;
            case 3:
                editText = binding.layoutGameScreenLink.secondRow.one;
                break;
            case 4:
                editText = binding.layoutGameScreenLink.secondRow.three;
                break;
            case 5:
                editText = binding.layoutGameScreenLink.secondRow.five;
                break;
            case 6:
                editText = binding.layoutGameScreenLink.thirdRow.one;
                break;
        }*/

        if (s.hashCode() == binding.layoutGameScreenLink.firstRow.one.getText().hashCode() || s.hashCode() == binding.layoutGameScreenLink.firstRow.two.getText().hashCode()){
            editText = binding.layoutGameScreenLink.firstRow.one;
        } else if (s.hashCode() == binding.layoutGameScreenLink.firstRow.three.getText().hashCode() || s.hashCode() == binding.layoutGameScreenLink.firstRow.four.getText().hashCode()){
            editText = binding.layoutGameScreenLink.firstRow.three;
        } else if (s.hashCode() == binding.layoutGameScreenLink.firstRow.five.getText().hashCode() || s.hashCode() == binding.layoutGameScreenLink.firstRow.six.getText().hashCode()){
            editText = binding.layoutGameScreenLink.firstRow.five;
        } else if (s.hashCode() == binding.layoutGameScreenLink.secondRow.one.getText().hashCode() || s.hashCode() == binding.layoutGameScreenLink.secondRow.two.getText().hashCode()){
            editText = binding.layoutGameScreenLink.secondRow.one;
        } else if (s.hashCode() == binding.layoutGameScreenLink.secondRow.three.getText().hashCode() || s.hashCode() == binding.layoutGameScreenLink.secondRow.four.getText().hashCode()){
            editText = binding.layoutGameScreenLink.secondRow.three;
        } else if (s.hashCode() == binding.layoutGameScreenLink.secondRow.five.getText().hashCode() || s.hashCode() == binding.layoutGameScreenLink.secondRow.six.getText().hashCode()){
            editText = binding.layoutGameScreenLink.secondRow.five;
        } else if (s.hashCode() == binding.layoutGameScreenLink.thirdRow.one.getText().hashCode() || s.hashCode() == binding.layoutGameScreenLink.thirdRow.two.getText().hashCode()){
            editText = binding.layoutGameScreenLink.thirdRow.one;
        } else if (s.hashCode() == binding.layoutGameScreenLink.thirdRow.three.getText().hashCode() || s.hashCode() == binding.layoutGameScreenLink.thirdRow.four.getText().hashCode()){
            editText = binding.layoutGameScreenLink.thirdRow.three;
        } else if (s.hashCode() == binding.layoutGameScreenLink.thirdRow.five.getText().hashCode() || s.hashCode() == binding.layoutGameScreenLink.thirdRow.six.getText().hashCode()){
            editText = binding.layoutGameScreenLink.thirdRow.five;
        } else if (s.hashCode() == binding.layoutGameScreenLink.fourthRow.one.getText().hashCode() || s.hashCode() == binding.layoutGameScreenLink.fourthRow.two.getText().hashCode()){
            editText = binding.layoutGameScreenLink.fourthRow.one;
        }

        return editText;
    }


    public EditText getCurrentSecondEditText(CharSequence s){
        EditText editText = null;


        //Log.e("Frame Inside Second", String.valueOf(getCurrent_frame()));

        /*switch (getCurrent_frame()){
            case 0:
                editText = binding.layoutGameScreenLink.firstRow.two;
                break;
            case 1:
                editText = binding.layoutGameScreenLink.firstRow.four;
                break;
            case 2:
                editText = binding.layoutGameScreenLink.firstRow.six;
                break;
            case 3:
                editText = binding.layoutGameScreenLink.secondRow.two;
                break;
            case 4:
                editText = binding.layoutGameScreenLink.secondRow.four;
                break;
            case 5:
                editText = binding.layoutGameScreenLink.secondRow.six;
                break;
            case 6:
                editText = binding.layoutGameScreenLink.thirdRow.two;
                break;
        }*/
        if (s.hashCode() == binding.layoutGameScreenLink.firstRow.one.getText().hashCode() || s.hashCode() == binding.layoutGameScreenLink.firstRow.two.getText().hashCode()){
            editText = binding.layoutGameScreenLink.firstRow.two;
        } else if (s.hashCode() == binding.layoutGameScreenLink.firstRow.three.getText().hashCode() || s.hashCode() == binding.layoutGameScreenLink.firstRow.four.getText().hashCode()){
            editText = binding.layoutGameScreenLink.firstRow.four;
        } else if (s.hashCode() == binding.layoutGameScreenLink.firstRow.five.getText().hashCode() || s.hashCode() == binding.layoutGameScreenLink.firstRow.six.getText().hashCode()){
            editText = binding.layoutGameScreenLink.firstRow.six;
        } else if (s.hashCode() == binding.layoutGameScreenLink.secondRow.one.getText().hashCode() || s.hashCode() == binding.layoutGameScreenLink.secondRow.two.getText().hashCode()){
            editText = binding.layoutGameScreenLink.secondRow.two;
        } else if (s.hashCode() == binding.layoutGameScreenLink.secondRow.three.getText().hashCode() || s.hashCode() == binding.layoutGameScreenLink.secondRow.four.getText().hashCode()){
            editText = binding.layoutGameScreenLink.secondRow.four;
        } else if (s.hashCode() == binding.layoutGameScreenLink.secondRow.five.getText().hashCode() || s.hashCode() == binding.layoutGameScreenLink.secondRow.six.getText().hashCode()){
            editText = binding.layoutGameScreenLink.secondRow.six;
        } else if (s.hashCode() == binding.layoutGameScreenLink.thirdRow.one.getText().hashCode() || s.hashCode() == binding.layoutGameScreenLink.thirdRow.two.getText().hashCode()){
            editText = binding.layoutGameScreenLink.thirdRow.two;
        } else if (s.hashCode() == binding.layoutGameScreenLink.thirdRow.three.getText().hashCode() || s.hashCode() == binding.layoutGameScreenLink.thirdRow.four.getText().hashCode()){
            editText = binding.layoutGameScreenLink.thirdRow.four;
        } else if (s.hashCode() == binding.layoutGameScreenLink.thirdRow.five.getText().hashCode() || s.hashCode() == binding.layoutGameScreenLink.thirdRow.six.getText().hashCode()){
            editText = binding.layoutGameScreenLink.thirdRow.six;
        } else if (s.hashCode() == binding.layoutGameScreenLink.fourthRow.one.getText().hashCode() || s.hashCode() == binding.layoutGameScreenLink.fourthRow.two.getText().hashCode()){
            editText = binding.layoutGameScreenLink.fourthRow.two;

        }

        return editText;
    }

    public void disableNextSetOfEditText(CharSequence s){
        /**
         * This enables the next set of editText after the second
         * score has been entered in the preceeding frame
         * **/

        //int temp = getCurrent_frame() - 1;
        //setCurrent_frame(temp);
        /*switch (index){
            case 0:
                binding.layoutGameScreenLink.firstRow.three.setEnabled(false);
                binding.layoutGameScreenLink.firstRow.four.setEnabled(false);
                break;
            case 1:
                binding.layoutGameScreenLink.firstRow.five.setEnabled(false);
                binding.layoutGameScreenLink.firstRow.six.setEnabled(false);
                break;
            case 2:
                binding.layoutGameScreenLink.secondRow.one.setEnabled(false);
                binding.layoutGameScreenLink.secondRow.two.setEnabled(false);
                break;
        }*/

        if (s.hashCode() == binding.layoutGameScreenLink.firstRow.one.getText().hashCode() || s.hashCode() == binding.layoutGameScreenLink.firstRow.two.getText().hashCode()){
            binding.layoutGameScreenLink.firstRow.three.setEnabled(false);
            binding.layoutGameScreenLink.firstRow.four.setEnabled(false);
        } else if (s.hashCode() == binding.layoutGameScreenLink.firstRow.three.getText().hashCode() || s.hashCode() == binding.layoutGameScreenLink.firstRow.four.getText().hashCode()){
            binding.layoutGameScreenLink.firstRow.five.setEnabled(false);
            binding.layoutGameScreenLink.firstRow.six.setEnabled(false);
        } else if (s.hashCode() == binding.layoutGameScreenLink.firstRow.five.getText().hashCode() || s.hashCode() == binding.layoutGameScreenLink.firstRow.six.getText().hashCode()){
            binding.layoutGameScreenLink.secondRow.one.setEnabled(false);
            binding.layoutGameScreenLink.secondRow.two.setEnabled(false);
        } else if (s.hashCode() == binding.layoutGameScreenLink.secondRow.one.getText().hashCode() || s.hashCode() == binding.layoutGameScreenLink.secondRow.two.getText().hashCode()){
            binding.layoutGameScreenLink.secondRow.three.setEnabled(false);
            binding.layoutGameScreenLink.secondRow.four.setEnabled(false);
        } else if (s.hashCode() == binding.layoutGameScreenLink.secondRow.three.getText().hashCode() || s.hashCode() == binding.layoutGameScreenLink.secondRow.four.getText().hashCode()){
            binding.layoutGameScreenLink.secondRow.five.setEnabled(false);
            binding.layoutGameScreenLink.secondRow.six.setEnabled(false);
        } else if (s.hashCode() == binding.layoutGameScreenLink.secondRow.five.getText().hashCode() || s.hashCode() == binding.layoutGameScreenLink.secondRow.six.getText().hashCode()){
            binding.layoutGameScreenLink.thirdRow.one.setEnabled(false);
            binding.layoutGameScreenLink.thirdRow.two.setEnabled(false);
        } else if (s.hashCode() == binding.layoutGameScreenLink.thirdRow.one.getText().hashCode() || s.hashCode() == binding.layoutGameScreenLink.thirdRow.two.getText().hashCode()){
            binding.layoutGameScreenLink.thirdRow.three.setEnabled(false);
            binding.layoutGameScreenLink.thirdRow.four.setEnabled(false);
        } else if (s.hashCode() == binding.layoutGameScreenLink.thirdRow.three.getText().hashCode() || s.hashCode() == binding.layoutGameScreenLink.thirdRow.four.getText().hashCode()){
            binding.layoutGameScreenLink.thirdRow.five.setEnabled(false);
            binding.layoutGameScreenLink.thirdRow.six.setEnabled(false);
        } else if (s.hashCode() == binding.layoutGameScreenLink.thirdRow.five.getText().hashCode() || s.hashCode() == binding.layoutGameScreenLink.thirdRow.six.getText().hashCode()){
            binding.layoutGameScreenLink.fourthRow.one.setEnabled(false);
            binding.layoutGameScreenLink.fourthRow.two.setEnabled(false);
            binding.layoutGameScreenLink.fourthRow.three.setEnabled(false);
        }
    }


    public void enableNextSetOfEditText(CharSequence s){
        /**
        * This enables the next set of editText after the second
        * score has been entered in the preceeding frame
        * **/
        //int temp = getCurrent_frame() + 1;
        //setCurrent_frame(temp);
        /*switch (index){
            case 1:
                binding.layoutGameScreenLink.firstRow.three.setEnabled(true);
                binding.layoutGameScreenLink.firstRow.four.setEnabled(true);
                break;
            case 2:
                binding.layoutGameScreenLink.firstRow.five.setEnabled(true);
                binding.layoutGameScreenLink.firstRow.six.setEnabled(true);
                break;
            case 3:
                binding.layoutGameScreenLink.secondRow.one.setEnabled(true);
                binding.layoutGameScreenLink.firstRow.two.setEnabled(true);
                break;
        }*/


        if (s.hashCode() == binding.layoutGameScreenLink.firstRow.one.getText().hashCode() || s.hashCode() == binding.layoutGameScreenLink.firstRow.two.getText().hashCode()){
            binding.layoutGameScreenLink.firstRow.three.setEnabled(true);
            binding.layoutGameScreenLink.firstRow.four.setEnabled(true);
        } else if (s.hashCode() == binding.layoutGameScreenLink.firstRow.three.getText().hashCode() || s.hashCode() == binding.layoutGameScreenLink.firstRow.four.getText().hashCode()){
            binding.layoutGameScreenLink.firstRow.five.setEnabled(true);
            binding.layoutGameScreenLink.firstRow.six.setEnabled(true);
        } else if (s.hashCode() == binding.layoutGameScreenLink.firstRow.five.getText().hashCode() || s.hashCode() == binding.layoutGameScreenLink.firstRow.six.getText().hashCode()){
            binding.layoutGameScreenLink.secondRow.one.setEnabled(true);
            binding.layoutGameScreenLink.secondRow.two.setEnabled(true);
        } else if (s.hashCode() == binding.layoutGameScreenLink.secondRow.one.getText().hashCode() || s.hashCode() == binding.layoutGameScreenLink.secondRow.two.getText().hashCode()){
            binding.layoutGameScreenLink.secondRow.three.setEnabled(true);
            binding.layoutGameScreenLink.secondRow.four.setEnabled(true);
        } else if (s.hashCode() == binding.layoutGameScreenLink.secondRow.three.getText().hashCode() || s.hashCode() == binding.layoutGameScreenLink.secondRow.four.getText().hashCode()){
            binding.layoutGameScreenLink.secondRow.five.setEnabled(true);
            binding.layoutGameScreenLink.secondRow.six.setEnabled(true);
        } else if (s.hashCode() == binding.layoutGameScreenLink.secondRow.five.getText().hashCode() || s.hashCode() == binding.layoutGameScreenLink.secondRow.six.getText().hashCode()){
            binding.layoutGameScreenLink.thirdRow.one.setEnabled(true);
            binding.layoutGameScreenLink.thirdRow.two.setEnabled(true);
        } else if (s.hashCode() == binding.layoutGameScreenLink.thirdRow.one.getText().hashCode() || s.hashCode() == binding.layoutGameScreenLink.thirdRow.two.getText().hashCode()){
            binding.layoutGameScreenLink.thirdRow.three.setEnabled(true);
            binding.layoutGameScreenLink.thirdRow.four.setEnabled(true);
        } else if (s.hashCode() == binding.layoutGameScreenLink.thirdRow.three.getText().hashCode() || s.hashCode() == binding.layoutGameScreenLink.thirdRow.four.getText().hashCode()){
            binding.layoutGameScreenLink.thirdRow.five.setEnabled(true);
            binding.layoutGameScreenLink.thirdRow.six.setEnabled(true);
        } else if (s.hashCode() == binding.layoutGameScreenLink.thirdRow.five.getText().hashCode() || s.hashCode() == binding.layoutGameScreenLink.thirdRow.six.getText().hashCode()){
            binding.layoutGameScreenLink.fourthRow.one.setEnabled(true);
            binding.layoutGameScreenLink.fourthRow.two.setEnabled(true);
            binding.layoutGameScreenLink.fourthRow.three.setEnabled(true);
        }
    }


    public void enableNextSetOfEditText(int frame){


        if (frame == 0){
            binding.layoutGameScreenLink.firstRow.three.setEnabled(true);
            binding.layoutGameScreenLink.firstRow.four.setEnabled(true);
        } else if (frame == 1){
            binding.layoutGameScreenLink.firstRow.five.setEnabled(true);
            binding.layoutGameScreenLink.firstRow.six.setEnabled(true);
        } else if (frame == 2){
            binding.layoutGameScreenLink.secondRow.one.setEnabled(true);
            binding.layoutGameScreenLink.secondRow.two.setEnabled(true);
        } else if (frame == 3){
            binding.layoutGameScreenLink.secondRow.three.setEnabled(true);
            binding.layoutGameScreenLink.secondRow.four.setEnabled(true);
        } else if (frame == 4){
            binding.layoutGameScreenLink.secondRow.five.setEnabled(true);
            binding.layoutGameScreenLink.secondRow.six.setEnabled(true);
        } else if (frame == 5){
            binding.layoutGameScreenLink.thirdRow.one.setEnabled(true);
            binding.layoutGameScreenLink.thirdRow.two.setEnabled(true);
        } else if (frame == 6){
            binding.layoutGameScreenLink.thirdRow.three.setEnabled(true);
            binding.layoutGameScreenLink.thirdRow.four.setEnabled(true);
        } else if (frame == 7){
            binding.layoutGameScreenLink.thirdRow.five.setEnabled(true);
            binding.layoutGameScreenLink.thirdRow.six.setEnabled(true);
        } else if (frame == 8){
            binding.layoutGameScreenLink.fourthRow.one.setEnabled(true);
            binding.layoutGameScreenLink.fourthRow.two.setEnabled(true);
            binding.layoutGameScreenLink.fourthRow.three.setEnabled(true);
        }
    }


    private void populateScores(Game oldGame){

        //this can be refactored better into a lopp.
        String first_score = oldGame.getScores().get(0).getFirstRow();
        String second_score = oldGame.getScores().get(0).getSecondRow();
        if (first_score.equals("14") || second_score.equals("14")){
            //means this scores haven't been entered yet.
        } else {
            binding.layoutGameScreenLink.firstRow.one.setText(first_score);
            binding.layoutGameScreenLink.firstRow.two.setText(second_score);
            binding.layoutGameScreenLink.firstRow.one.setEnabled(true);
            binding.layoutGameScreenLink.firstRow.two.setEnabled(true);
            enableNextSetOfEditText(0);
        }

        first_score = oldGame.getScores().get(1).getFirstRow();
        second_score = oldGame.getScores().get(1).getSecondRow();
        if (first_score.equals("14") || second_score.equals("14")){
            //means this scores haven't been entered yet.
        } else {
            binding.layoutGameScreenLink.firstRow.three.setText(first_score);
            binding.layoutGameScreenLink.firstRow.four.setText(second_score);
            binding.layoutGameScreenLink.firstRow.three.setEnabled(true);
            binding.layoutGameScreenLink.firstRow.four.setEnabled(true);
            enableNextSetOfEditText(1);
        }


        first_score = oldGame.getScores().get(2).getFirstRow();
        second_score = oldGame.getScores().get(2).getSecondRow();
        if (first_score.equals("14") || second_score.equals("14")){
            //means this scores haven't been entered yet.
        } else {
            binding.layoutGameScreenLink.firstRow.five.setText(first_score);
            binding.layoutGameScreenLink.firstRow.six.setText(second_score);
            binding.layoutGameScreenLink.firstRow.five.setEnabled(true);
            binding.layoutGameScreenLink.firstRow.six.setEnabled(true);
            enableNextSetOfEditText(2);
        }

        first_score = oldGame.getScores().get(3).getFirstRow();
        second_score = oldGame.getScores().get(3).getSecondRow();
        if (first_score.equals("14") || second_score.equals("14")){
            //means this scores haven't been entered yet.
        } else {
            binding.layoutGameScreenLink.secondRow.one.setText(first_score);
            binding.layoutGameScreenLink.secondRow.two.setText(second_score);
            binding.layoutGameScreenLink.secondRow.one.setEnabled(true);
            binding.layoutGameScreenLink.secondRow.two.setEnabled(true);
            enableNextSetOfEditText(3);
        }

        first_score = oldGame.getScores().get(4).getFirstRow();
        second_score = oldGame.getScores().get(4).getSecondRow();
        if (first_score.equals("14") || second_score.equals("14")){
            //means this scores haven't been entered yet.
        } else {
        binding.layoutGameScreenLink.secondRow.three.setText(first_score);
        binding.layoutGameScreenLink.secondRow.four.setText(second_score);
            binding.layoutGameScreenLink.secondRow.three.setEnabled(true);
            binding.layoutGameScreenLink.secondRow.four.setEnabled(true);
            enableNextSetOfEditText(4);
        }

        first_score = oldGame.getScores().get(5).getFirstRow();
        second_score = oldGame.getScores().get(5).getSecondRow();
        if (first_score.equals("14") || second_score.equals("14")){
            //means this scores haven't been entered yet.
        } else {
        binding.layoutGameScreenLink.secondRow.five.setText(first_score);
        binding.layoutGameScreenLink.secondRow.six.setText(second_score);
            binding.layoutGameScreenLink.secondRow.five.setEnabled(true);
            binding.layoutGameScreenLink.secondRow.six.setEnabled(true);
            enableNextSetOfEditText(5);
        }

        first_score = oldGame.getScores().get(6).getFirstRow();
        second_score = oldGame.getScores().get(6).getSecondRow();
        if (first_score.equals("14") || second_score.equals("14")){
            //means this scores haven't been entered yet.
        } else {
        binding.layoutGameScreenLink.thirdRow.one.setText(first_score);
        binding.layoutGameScreenLink.thirdRow.two.setText(second_score);
            binding.layoutGameScreenLink.thirdRow.one.setEnabled(true);
            binding.layoutGameScreenLink.thirdRow.two.setEnabled(true);
            enableNextSetOfEditText(6);
        }

        first_score = oldGame.getScores().get(7).getFirstRow();
        second_score = oldGame.getScores().get(7).getSecondRow();
        if (first_score.equals("14") || second_score.equals("14")){
            //means this scores haven't been entered yet.
        } else {
        binding.layoutGameScreenLink.thirdRow.three.setText(first_score);
        binding.layoutGameScreenLink.thirdRow.four.setText(second_score);
            binding.layoutGameScreenLink.thirdRow.three.setEnabled(true);
            binding.layoutGameScreenLink.thirdRow.four.setEnabled(true);
            enableNextSetOfEditText(7);
        }
        first_score = oldGame.getScores().get(8).getFirstRow();
        second_score = oldGame.getScores().get(8).getSecondRow();
        if (first_score.equals("14") || second_score.equals("14")){
            //means this scores haven't been entered yet.
        } else {
        binding.layoutGameScreenLink.thirdRow.five.setText(first_score);
        binding.layoutGameScreenLink.thirdRow.six.setText(second_score);
            binding.layoutGameScreenLink.thirdRow.five.setEnabled(true);
            binding.layoutGameScreenLink.thirdRow.six.setEnabled(true);
            enableNextSetOfEditText(8);
        }

        first_score = oldGame.getScores().get(9).getFirstRow();
        second_score = oldGame.getScores().get(9).getSecondRow();
        if (first_score.equals("14") || second_score.equals("14")){
            //means this scores haven't been entered yet.
        } else {
        binding.layoutGameScreenLink.fourthRow.one.setText(first_score);
        binding.layoutGameScreenLink.fourthRow.two.setText(second_score);
            binding.layoutGameScreenLink.fourthRow.one.setEnabled(true);
            binding.layoutGameScreenLink.fourthRow.two.setEnabled(true);
        }

        if (oldGame.getLast_score() != 14){
            binding.layoutGameScreenLink.fourthRow.three.setText(String.valueOf(oldGame.getLast_score()));
            binding.layoutGameScreenLink.fourthRow.three.setEnabled(true);
            setVisibility(binding.layoutGameScreenLink.fourthRow.three, View.VISIBLE);
            binding.layoutGameScreenLink.fourthRow.lastFrameParent.setWeightSum(3);
            Log.e("Last Score", String.valueOf(oldGame.getLast_score()));
            //Log.e("Last Score Text", binding.layoutGameScreenLink.fourthRow.three.getText().toString());
        }

        mainActivityViewModel.getActivityGameScreenUIModel().calculateScores();
    }


    public void displayScores(Integer scores[]){

        if (scores[0] != null){
            binding.layoutGameScreenLink.firstRow.scoreOne.setText(String.valueOf(scores[0]));
        }

        if (scores[1] != null){
            //this should be refactored to use a loop in the calculating scores method in the ViewModel.
            //More efficient.
            int temp = 0;//scores[0] + scores[1];
            for(int i = 1; i >= 0; i--){
                temp = temp + scores[i];
            }

            if (getCurrent_frame() >= 1){
                binding.layoutGameScreenLink.firstRow.scoreTwo.setText(String.valueOf(temp));
            }
        }

        if (scores[2] != null){
            //int temp = scores[0] + scores[1] + scores[2];
            int temp = 0;//scores[0] + scores[1];
            for(int i = 2; i >= 0; i--){
                temp = temp + scores[i];
            }

            if (getCurrent_frame() >= 2){
                binding.layoutGameScreenLink.firstRow.scoreThree.setText(String.valueOf(temp));
            }
        }

        if (scores[3] != null){
            //int temp = scores[0] + scores[1] + scores[2] + scores[3];
            int temp = 0;//scores[0] + scores[1];
            for(int i = 3; i >= 0; i--){
                temp = temp + scores[i];
            }

            if (getCurrent_frame() >= 3){
                binding.layoutGameScreenLink.secondRow.scoreOne.setText(String.valueOf(temp));
            }

        }

        if (scores[4] != null){

            //int temp = scores[0] + scores[1] + scores[2] + scores[4];
            int temp = 0;//scores[0] + scores[1];
            for(int i = 4; i >= 0; i--){
                temp = temp + scores[i];
            }

            if (getCurrent_frame() >= 4){
                binding.layoutGameScreenLink.secondRow.scoreTwo.setText(String.valueOf(temp));
            }

        }

        if (scores[5] != null){

            int temp = 0;//scores[0] + scores[1];
            for(int i = 5; i >= 0; i--){
                temp = temp + scores[i];
            }

            if (getCurrent_frame() >= 5){
                binding.layoutGameScreenLink.secondRow.scoreThree.setText(String.valueOf(temp));
            }
        }

        if (scores[6] != null){

            int temp = 0;//scores[0] + scores[1];
            for(int i = 6; i >= 0; i--){
                temp = temp + scores[i];
            }

            if (getCurrent_frame() >= 6){
                binding.layoutGameScreenLink.thirdRow.scoreOne.setText(String.valueOf(temp));
            }
        }

        if (scores[7] != null){
            int temp = 0;//scores[0] + scores[1];
            for(int i = 7; i >= 0; i--){
                temp = temp + scores[i];
            }

            if (getCurrent_frame() >= 7){
                binding.layoutGameScreenLink.thirdRow.scoreTwo.setText(String.valueOf(temp));
            }

        }

        if (scores[8] != null){
            int temp = 0;//scores[0] + scores[1];
            for(int i = 8; i >= 0; i--){
                temp = temp + scores[i];
            }

            if (getCurrent_frame() >= 8){
                binding.layoutGameScreenLink.thirdRow.scoreThree.setText(String.valueOf(temp));
            }

        }

        if (scores[9] != null){

            if (getCurrent_frame() >= 9){
                binding.layoutGameScreenLink.fourthRow.scoreOne.setText(String.valueOf(scores[9]));
            }

        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }



}
