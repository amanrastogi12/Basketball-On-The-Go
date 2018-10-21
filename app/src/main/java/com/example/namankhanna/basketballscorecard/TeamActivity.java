package com.example.namankhanna.basketballscorecard;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class TeamActivity extends AppCompatActivity implements TeamDialog.OnPositiveListener {

    RecyclerView rvListTeams;
    FloatingActionButton fabAddTeams;
    DatabaseHelper helper;
    ArrayList<Team> teamArrayList = new ArrayList<>();
    TeamAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        rvListTeams = findViewById(R.id.rvListTeams);
        fabAddTeams = findViewById(R.id.fabAddTeams);
        helper = new DatabaseHelper(this);
        adapter = new TeamAdapter(teamArrayList, this);

        checkScoreCardDialog();

        rvListTeams.setLayoutManager(new LinearLayoutManager(this));
        rvListTeams.setAdapter(adapter);

        adapter.setOnTeamClickListener(new TeamAdapter.OnTeamClickListener() {
            @Override
            public void getTeamNames(String team1, String team2) {
                Intent intent = new Intent(TeamActivity.this, PlayerActivity.class);
                intent.putExtra("TEAM1", team1);
                intent.putExtra("TEAM2", team2);
                startActivity(intent);
            }
        });

        fabAddTeams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTeamsDialog();
            }
        });

        readTeamsFromDatabase();
    }

    private void checkScoreCardDialog() {
        ScoreCard card = (ScoreCard) getIntent().getSerializableExtra("SCORE_CARD");
        Bundle bundle = getIntent().getBundleExtra("BUNDLE");
        if(card != null && bundle != null) {
            ArrayList<Player> players1 = (ArrayList<Player>) bundle.getSerializable("PLAYERS1");
            ArrayList<Player> players2 = (ArrayList<Player>) bundle.getSerializable("PLAYERS2");
            ScoreCardDialog cardDialog = new ScoreCardDialog();
            cardDialog.setDetails(card, players1, players2);
            cardDialog.show(getSupportFragmentManager(), "SCORE_CARD_DIALOG");
        }

    }

    private void readTeamsFromDatabase() {
        Cursor cursor = helper.readTeams();
        while (cursor.moveToNext()) {
            String team1 = cursor.getString(cursor.getColumnIndex("Team1"));
            String team2 = cursor.getString(cursor.getColumnIndex("Team2"));
            teamArrayList.add(new Team(team1, team2));
        }
    }

    private void openTeamsDialog() {
        TeamDialog dialog = new TeamDialog();
        dialog.show(getSupportFragmentManager(), "TEAM_DIALOG");
    }

    @Override
    public void getTeamNames(String team1, String team2) {
        helper.writeTeams(team1, team2);
        teamArrayList.add(new Team(team1, team2));
        adapter.notifyDataSetChanged();
    }
}
