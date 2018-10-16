package com.example.namankhanna.basketballscorecard;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {

    ArrayList<Player> playerArrayList;
    Context context;

    public PlayerAdapter(ArrayList<Player> playerArrayList, Context context) {
        this.playerArrayList = playerArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.list_player, parent, false);
        return new PlayerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        Player player = playerArrayList.get(position);
        holder.tvPlayer.setText(player.getName());
        holder.tvTNum.setText(String.valueOf(player.gettNum()));
    }

    @Override
    public int getItemCount() {
        return playerArrayList.size();
    }

    public static class PlayerViewHolder extends RecyclerView.ViewHolder {

        TextView tvPlayer, tvTNum;

        public PlayerViewHolder(View itemView) {
            super(itemView);
            tvPlayer = itemView.findViewById(R.id.tvPlayer);
            tvTNum = itemView.findViewById(R.id.tvTNum);
        }
    }
}
