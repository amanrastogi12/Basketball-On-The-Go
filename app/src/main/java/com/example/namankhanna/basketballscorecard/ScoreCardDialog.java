package com.example.namankhanna.basketballscorecard;

import android.app.Dialog;
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

    ScoreCard card;
    ArrayList<Player> players1, players2;

    TextView tvTeam1, tvTeam2, tvScore1, tvScore2, tvFoul1, tvFoul2, tvTimeOut1, tvTimeOut2;
    RecyclerView rvPlayers1,rvPlayers2;
    PlayerAdapter adapter1, adapter2;

    public void setDetails(ScoreCard card, ArrayList<Player> players1, ArrayList<Player> players2) {
        this.card = card;
        this.players1 = players1;
        this.players2 = players2;
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

        tvTeam1.setText(card.getTeam1());
        tvTeam2.setText(card.getTeam2());
        tvScore1.setText(String.valueOf(card.getTotalScore1()));
        tvScore2.setText(String.valueOf(card.getTotalScore2()));
        tvFoul1.setText("Fouls of " + card.getTeam1() + " : "+ String.valueOf(card.getFouls1()));
        tvFoul2.setText("Fouls of " + card.getTeam2() + " : "+ String.valueOf(card.getFouls2()));
        tvTimeOut1.setText("TimeOuts of " + card.getTeam1() + " : "+ String.valueOf(card.getTimeOuts1()));
        tvTimeOut2.setText("TimeOuts of " + card.getTeam2() + " : "+ String.valueOf(card.getTimeOuts2()));
        rvPlayers1.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvPlayers2.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter1 = new PlayerAdapter(players1, getActivity());
        rvPlayers1.setAdapter(adapter1);

        adapter2 = new PlayerAdapter(players2, getActivity());
        rvPlayers2.setAdapter(adapter2);

        builder.setView(view)
                .setTitle("Score Card")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        return builder.create();

    }
}
