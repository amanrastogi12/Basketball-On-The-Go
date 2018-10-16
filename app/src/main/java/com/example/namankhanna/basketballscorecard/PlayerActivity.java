package com.example.namankhanna.basketballscorecard;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity {

    TextView tvTeam1, tvTeam2;
    EditText etPlayer1, etPlayer2, etTNum1, etTNum2;
    FloatingActionButton fabAddPlayers1, fabAddPlayers2;
    RecyclerView rvListPlayers1, rvListPlayers2;
    String team1, team2;
    DatabaseHelper helper;
    ArrayList<Player> players1 = new ArrayList<>();
    ArrayList<Player> players2 = new ArrayList<>();
    PlayerAdapter adapter1, adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        tvTeam1 = findViewById(R.id.tvTeamName1);
        tvTeam2 = findViewById(R.id.tvTeamName2);
        etPlayer1 = findViewById(R.id.etPlayer1);
        etPlayer2 = findViewById(R.id.etPlayer2);
        etTNum1 = findViewById(R.id.etTNum1);
        etTNum2 = findViewById(R.id.etTNum2);
        fabAddPlayers1 = findViewById(R.id.fabAddPlayer1);
        fabAddPlayers2 = findViewById(R.id.fabAddPlayer2);
        rvListPlayers1 = findViewById(R.id.rvListPlayers1);
        rvListPlayers2 = findViewById(R.id.rvListPlayers2);
        helper = new DatabaseHelper(this);
        adapter1 = new PlayerAdapter(players1, this);
        adapter2 = new PlayerAdapter(players2, this);

        rvListPlayers1.setLayoutManager(new LinearLayoutManager(this));
        rvListPlayers1.setAdapter(adapter1);

        rvListPlayers2.setLayoutManager(new LinearLayoutManager(this));
        rvListPlayers2.setAdapter(adapter2);

        team1 = getIntent().getStringExtra("TEAM1");
        team2 = getIntent().getStringExtra("TEAM2");
        tvTeam1.setText(team1);
        tvTeam2.setText(team2);

        fabAddPlayers1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(etPlayer1.getText().toString()) &&
                        !TextUtils.isEmpty(etTNum1.getText().toString()) &&
                        players1.size() != 10) {
                    writePlayersIntoDatabase(team1, etPlayer1.getText().toString(), Integer.valueOf(etTNum1.getText().toString()));
                }
            }
        });

        fabAddPlayers2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(etPlayer2.getText().toString()) &&
                        !TextUtils.isEmpty(etTNum2.getText().toString()) &&
                        players2.size() != 10) {
                    writePlayersIntoDatabase(team2, etPlayer2.getText().toString(), Integer.valueOf(etTNum2.getText().toString()));
                }
            }
        });

        (findViewById(R.id.btnLetsPlay)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(players1.size() == 10 && players2.size() == 10) {
                    Intent intent = new Intent(PlayerActivity.this, GameActivity.class);
                    intent.putExtra("TEAM1", team1);
                    intent.putExtra("TEAM2", team2);
                    startActivity(intent);
                }
            }
        });

        readPlayersFromDatabase();

    }

    private void readPlayersFromDatabase() {
        Cursor cursor1 = helper.readPlayers(team1);
        Cursor cursor2 = helper.readPlayers(team2);
        while (cursor1.moveToNext()) {
            String name = cursor1.getString(cursor1.getColumnIndex("Name"));
            int num = cursor1.getInt(cursor1.getColumnIndex("TNo"));
            players1.add(new Player(name, num));
        }
        while (cursor2.moveToNext()) {
            String name = cursor2.getString(cursor2.getColumnIndex("Name"));
            int num = cursor2.getInt(cursor2.getColumnIndex("TNo"));
            players2.add(new Player(name, num));
        }
    }

    private void writePlayersIntoDatabase(String team, String name, int num) {
       helper.writePlayers(team, name, num);
       if(team.equals(team1)) {
           players1.add(new Player(name, num));
           adapter1.notifyDataSetChanged();
           etPlayer1.setText("");
           etTNum1.setText("");
       }
       else {
           players2.add(new Player(name, num));
           adapter2.notifyDataSetChanged();
           etPlayer2.setText("");
           etTNum2.setText("");
       }
    }
}
