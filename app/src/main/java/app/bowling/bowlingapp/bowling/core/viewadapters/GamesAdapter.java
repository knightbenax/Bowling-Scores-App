package app.bowling.bowlingapp.bowling.core.viewadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
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

    private List<ExtendedGame> extendedGames = new ArrayList<>();

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

    public List<ExtendedGame> getExtendedGames() {
        return extendedGames;
    }

    public void clearExtendedGames(){
        extendedGames = new ArrayList<>();
    }

    public void removeExtendGame(int position){
        extendedGames.remove(position);
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
        extendedGame.setUid(game.getUid());
        extendedGame.setCurrent_frame(game.getCurrent_frame());
        extendedGame.setGame_finished(game.isGame_finished());
        extendedGame.setAdapter_postion(position);
        extendedGames.add(extendedGame);

        holder.layoutSingleGameBinding.setGame(extendedGame);
        holder.layoutSingleGameBinding.setPosition(position);
        holder.layoutSingleGameBinding.parent.setBackgroundResource(extendedGame.isIs_selected() ? R.drawable.black_trans_white : R.drawable.black_trans);
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


    public void removeAt(int position){
        //removeExtendGame(position);
        gameList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, gameList.size());

    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public LayoutSingleGameBinding layoutSingleGameBinding;

        public ViewHolder(LayoutSingleGameBinding layoutSingleGameBinding) {
            super(layoutSingleGameBinding.getRoot());
            this.layoutSingleGameBinding = layoutSingleGameBinding;
            layoutSingleGameBinding.getRoot().setOnClickListener(this);
            layoutSingleGameBinding.getRoot().setOnLongClickListener(this);


        }

        @Override
        public void onClick(View view) {
            //int position = layoutSingleGameBinding.getPosition();
            activity.gameScreenAvailableListener.OnOldGameScreenAvailable(gameList.get(getAdapterPosition()));
        }

        @Override
        public boolean onLongClick(View view) {
            //Game game = gameList.get(getLayoutPosition());
            //int position = layoutSingleGameBinding.getPosition();
            ExtendedGame extendedGame = extendedGames.get(getAdapterPosition());
            extendedGame.setIs_selected(!extendedGame.isIs_selected());
            layoutSingleGameBinding.parent.setBackgroundResource(extendedGame.isIs_selected() ? R.drawable.black_trans_white : R.drawable.black_trans);
            activity.enableDelete();
            return true;
        }
    }

}
