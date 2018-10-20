package com.example.namankhanna.basketballscorecard;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ScoreDialog extends AppCompatDialogFragment {

    int id;
    String name, teamName;
    TextView tvScore;
    Button btnPlus1, btnPlus2, btnPlus3, btnMinus1;

    public interface OnPositiveScoreListener {
        void getScore(int id, String name, String teamName,int score);
    }

    OnPositiveScoreListener listener;

    public void setIdAndName(int id, String name, String teamName) {
        this.id = id;
        this.name = name;
        this.teamName = teamName;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_score, null);

        tvScore = view.findViewById(R.id.tvDialogScore);
        btnPlus1 = view.findViewById(R.id.btnScorePlus1);
        btnPlus2 = view.findViewById(R.id.btnScorePlus2);
        btnPlus3 = view.findViewById(R.id.btnScorePlus3);
        btnMinus1 = view.findViewById(R.id.btnScoreMinus1);

        View.OnClickListener setScoreListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.btnScorePlus1: tvScore.setText("+1");
                    break;
                    case R.id.btnScorePlus2: tvScore.setText("+2");
                    break;
                    case R.id.btnScorePlus3: tvScore.setText("+3");
                    break;
                    case R.id.btnScoreMinus1: tvScore.setText("-1");
                    break;
                }
            }
        };

        btnPlus1.setOnClickListener(setScoreListener);
        btnPlus2.setOnClickListener(setScoreListener);
        btnPlus3.setOnClickListener(setScoreListener);
        btnMinus1.setOnClickListener(setScoreListener);

        builder.setView(view)
                .setTitle("Score")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(!tvScore.getText().toString().equals("0")) {
                            listener.getScore(id, name, teamName, Integer.valueOf(tvScore.getText().toString()));
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnPositiveScoreListener) context;
        } catch (ClassCastException cce) {
            throw new ClassCastException(context.toString() + "Interface must be implemented");
        }
    }
}
