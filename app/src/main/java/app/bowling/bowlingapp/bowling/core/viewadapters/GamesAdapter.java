package app.bowling.bowlingapp.bowling.core.viewadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import app.bowling.bowlingapp.bowling.R;
import app.bowling.bowlingapp.bowling.core.database.models.Game;
import app.bowling.bowlingapp.bowling.core.models.ExtendedGame;
import app.bowling.bowlingapp.bowling.databinding.LayoutSingleGameBinding;
import app.bowling.bowlingapp.bowling.screens.MainActivity;


public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.ViewHolder> {

    private Context mContext;

    private List<Game> gameList = new ArrayList<>();
    MainActivity activity;

    // 1
    public GamesAdapter(MainActivity activity) {
        this.mContext = activity;
        this.activity = activity;
    }

    public List<Game> getGameList() {
        return gameList;
    }

    public void setGameList(List<Game> gameList) {
        this.gameList = gameList;
        this.notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutSingleGameBinding layoutSingleGameBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.layout_single_game, parent, false);
        ViewHolder viewHolder = new ViewHolder(layoutSingleGameBinding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Game game = gameList.get(position);

        ExtendedGame extendedGame = new ExtendedGame();
        extendedGame.setPlayer(game.getPlayer());
        extendedGame.setDate_started(game.getDate_started());
        extendedGame.setScores(game.getScores());

        holder.layoutSingleGameBinding.setGame(extendedGame);
    }

    // 3
    @Override
    public long getItemId(int position) {
        return gameList.get(position).getUid();
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public LayoutSingleGameBinding layoutSingleGameBinding;

        public ViewHolder(LayoutSingleGameBinding layoutSingleGameBinding) {
            super(layoutSingleGameBinding.getRoot());
            this.layoutSingleGameBinding = layoutSingleGameBinding;
        }
    }

}
