package com.example.namankhanna.basketballscorecard;

import android.database.Cursor;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {


    long timeLeft1 = 2400000;
    long timeLeft2 = 24000;
    TextView tvTeam1, tvTeam2;
    RecyclerView rvPlayers1, rvPlayers2;
    TextView tvScore1, tvScore2;
    String team1, team2;
    FloatingActionButton fabPlayTimer1, fabPauseTimer1, fabPlayTimer2, fabPauseTimer2;
    TextView tvTimer1, tvTimer2;
    CountDownTimer timer1, timer2;
    ArrayList<Player> players1 = new ArrayList<>();
    ArrayList<Player> players2 = new ArrayList<>();
    PlayerAdapter adapter1, adapter2;
    DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        tvTeam1 = findViewById(R.id.tvName1);
        tvTeam2 = findViewById(R.id.tvName2);
        rvPlayers1 = findViewById(R.id.rvPlayers1);
        rvPlayers2 = findViewById(R.id.rvPlayers2);
        tvScore1 = findViewById(R.id.tvScore1);
        tvScore2 = findViewById(R.id.tvScore2);
        fabPlayTimer1 = findViewById(R.id.fabPlayTimer1);
        fabPlayTimer2 = findViewById(R.id.fabPlayTimer2);
        fabPauseTimer1 = findViewById(R.id.fabPauseTimer1);
        fabPauseTimer2 = findViewById(R.id.fabPauseTimer2);
        tvTimer1 = findViewById(R.id.tvTimer1);
        tvTimer2 = findViewById(R.id.tvTimer2);
        helper = new DatabaseHelper(this);
        adapter1 = new PlayerAdapter(players1, this);
        adapter2 = new PlayerAdapter(players2, this);

        rvPlayers1.setLayoutManager(new LinearLayoutManager(this));
        rvPlayers1.setAdapter(adapter1);

        rvPlayers2.setLayoutManager(new LinearLayoutManager(this));
        rvPlayers2.setAdapter(adapter2);

        team1 = getIntent().getStringExtra("TEAM1");
        team2 = getIntent().getStringExtra("TEAM2");
        tvTeam1.setText(team1);
        tvTeam2.setText(team2);

        fabPlayTimer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimer1();
            }
        });

        fabPauseTimer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer1.cancel();
            }
        });

        fabPlayTimer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimer2();
            }
        });

        fabPauseTimer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer2.cancel();
            }
        });

        readPlayersFromDatabase();
    }

    private void startTimer2() {
        timer2 = new CountDownTimer(timeLeft2, 1000) {
            @Override
            public void onTick(long millis) {
                timeLeft2 = millis;
                int sec = (int) (timeLeft2 / 1000) % 60;
                tvTimer2.setText(String.valueOf(sec));
            }

            @Override
            public void onFinish() {
                tvTimer2.setText("24");
            }
        }.start();
    }

    private void startTimer1() {
        timer1 = new CountDownTimer(timeLeft1, 1000) {
            @Override
            public void onTick(long millis) {
                timeLeft1 = millis;
                updateCountDownTimer1Text();
            }

            @Override
            public void onFinish() {
                tvTimer1.setText("40:00");
            }
        }.start();
    }

    private void updateCountDownTimer1Text() {
        int s = (int) (timeLeft1 / 1000) % 60;
        int m = (int) (timeLeft1 / 1000) / 60;
        String time = String.format("%02d:%02d", m, s);
        tvTimer1.setText(time);
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
}
