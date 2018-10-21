package com.example.namankhanna.basketballscorecard;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class ScoreCardDialog extends AppCompatDialogFragment {

    public interface OnPositiveCardListener {
        void startActivity(boolean flag);
    }

    OnPositiveCardListener positiveCardListener;

    String team1, team2;
    int totalScore1, totalScore2, fouls1, fouls2, timeOuts1, timeOuts2;
    ArrayList<Player> players1, players2;
    int[] score1, score2;

    TextView tvTeam1, tvTeam2, tvScore1, tvScore2, tvFoul1, tvFoul2, tvTimeOut1, tvTimeOut2;
    RecyclerView rvPlayers1,rvPlayers2;
    PlayerAdapter adapter1, adapter2;

    public void setDetails(String team1, String team2, int totalScore1, int totalScore2,
                           int fouls1, int fouls2, int timeOuts1, int timeOuts2,
                           ArrayList<Player> players1, ArrayList<Player> players2,
                           int[] score1, int[] score2) {
        this.team1 = team1;
        this.team2 = team2;
        this.totalScore1 = totalScore1;
        this.totalScore2 = totalScore2;
        this.fouls1 = fouls1;
        this.fouls2 = fouls2;
        this.timeOuts1 = timeOuts1;
        this.timeOuts2 = timeOuts2;
        this.players1 = players1;
        this.players2 = players2;
        this.score1 = score1;
        this.score2 = score2;
    }

    private void updatePlayersList(ArrayList<Player> players, int[] score) {
        for(int i = 0 ; i < players.size() ; i++) {
            Player player = players.get(i);
            players.set(i, new Player(player.getName(), score[i]));
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_scorecard, null);

        tvTeam1 = view.findViewById(R.id.tvDialogTeam1);
        tvTeam2 = view.findViewById(R.id.tvDialogTeam2);
        tvScore1 = view.findViewById(R.id.tvDialogScore1);
        tvScore2 = view.findViewById(R.id.tvDialogScore2);
        tvFoul1 = view.findViewById(R.id.tvFouls1);
        tvFoul2 = view.findViewById(R.id.tvFouls2);
        tvTimeOut1 = view.findViewById(R.id.tvTimeOut1);
        tvTimeOut2 = view.findViewById(R.id.tvTimeOut2);
        rvPlayers1 = view.findViewById(R.id.rvDialogPlayers1);
        rvPlayers2 = view.findViewById(R.id.rvDialogPlayers2);

        tvTeam1.setText(team1);
        tvTeam2.setText(team2);
        tvScore1.setText(String.valueOf(totalScore1));
        tvScore2.setText(String.valueOf(totalScore2));
        tvFoul1.setText("Fouls of " + team1 + " : "+ String.valueOf(fouls1));
        tvFoul2.setText("Fouls of " + team2 + " : "+ String.valueOf(fouls2));
        tvTimeOut1.setText("TimeOuts of " + team1 + " : "+ String.valueOf(timeOuts1));
        tvTimeOut2.setText("TimeOuts of " + team2 + " : "+ String.valueOf(timeOuts2));
        rvPlayers1.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvPlayers2.setLayoutManager(new LinearLayoutManager(getActivity()));

        updatePlayersList(players1, score1);
        adapter1 = new PlayerAdapter(players1, getActivity());
        rvPlayers1.setAdapter(adapter1);

        updatePlayersList(players2, score2);
        adapter2 = new PlayerAdapter(players2, getActivity());
        rvPlayers2.setAdapter(adapter2);

        builder.setView(view)
                .setTitle("Score Card")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        positiveCardListener.startActivity(true);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        positiveCardListener.startActivity(true);
                    }
                });

        return builder.create();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            positiveCardListener = (OnPositiveCardListener) context;
        } catch (ClassCastException cce) {
            throw new ClassCastException(context.toString() + "Interface must be implemented");
        }
    }
}
