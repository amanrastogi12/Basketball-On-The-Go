package com.example.namankhanna.basketballscorecard;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity implements ScoreDialog.OnPositiveScoreListener {

    public static final String TAG = "GAME_ACTIVITY";

    TextView tvTeam1, tvTeam2;
    RecyclerView rvPlayers1, rvPlayers2;
    TextView tvScore1, tvScore2;
    String team1, team2;
    FloatingActionButton fabPlayTimer1, fabPauseTimer1, fabPlayTimer2, fabPauseTimer2;
    TextView tvTimer1, tvTimer2;
    CountDownTimer timer1, timer2;
    int foul1 = 0, foul2 = 0;
    int timeOut1 = 0, timeOut2 = 0;
    long timeLeft1 = 2400000, timeLeft2 = 24000;
    boolean isTimer1Running = false, isTimer2Running = false;
    ArrayList<Player> players1 = new ArrayList<>();
    ArrayList<Player> players2 = new ArrayList<>();
    int[] score1, score2;
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

        score1 = new int[10];
        score2 = new int[10];
        for(int i = 0 ; i < 10 ; i++) {
            score1[i] = 0;
            score2[i] = 0;
        }

        adapter1.setOnPlayerClickListener(new PlayerAdapter.OnPlayerClickListener() {
            @Override
            public void getPlayer(int id, String name) {
                displayScoreDialog(1, id, name);
            }
        });

        adapter2.setOnPlayerClickListener(new PlayerAdapter.OnPlayerClickListener() {
            @Override
            public void getPlayer(int id, String name) {
                displayScoreDialog(2, id, name);
            }
        });

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
                if(!isTimer1Running) {
                    startTimer1();
                }
            }
        });

        fabPauseTimer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isTimer1Running) {
                    pauseTimer1();
                }
            }
        });

        fabPlayTimer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isTimer2Running) {
                    startTimer2();
                }
            }
        });

        fabPauseTimer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isTimer2Running) {
                    pauseTimer2();
                }
            }
        });

        View.OnClickListener incDecListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.fabAddFoul1:  foul1++;
                    break;
                    case R.id.fabRemoveFoul1: if(foul1 != 0)
                        foul1--;
                    break;
                    case R.id.fabAddFoul2: foul2++;
                    break;
                    case R.id.fabRemoveFoul2: if(foul2 != 0)
                        foul2--;
                    break;
                    case R.id.fabAddTimeout1:  timeOut1++;
                        break;
                    case R.id.fabRemoveTimeout1: if(foul1 != 0)
                        timeOut1--;
                        break;
                    case R.id.fabAddTimeout2: timeOut2++;
                        break;
                    case R.id.fabRemoveTimeout2: if(foul2 != 0)
                        timeOut2--;
                        break;
                }
            }
        };

        (findViewById(R.id.fabAddFoul1)).setOnClickListener(incDecListener);
        (findViewById(R.id.fabRemoveFoul1)).setOnClickListener(incDecListener);
        (findViewById(R.id.fabAddFoul2)).setOnClickListener(incDecListener);
        (findViewById(R.id.fabRemoveFoul2)).setOnClickListener(incDecListener);

        (findViewById(R.id.fabAddTimeout1)).setOnClickListener(incDecListener);
        (findViewById(R.id.fabRemoveTimeout1)).setOnClickListener(incDecListener);
        (findViewById(R.id.fabAddTimeout2)).setOnClickListener(incDecListener);
        (findViewById(R.id.fabRemoveTimeout2)).setOnClickListener(incDecListener);

        (findViewById(R.id.btnNewGame)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(GameActivity.this)
                        .setTitle("Confirm")
                        .setMessage("Are you sure you want to start a new game ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(GameActivity.this, TeamActivity.class));
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).create().show();
            }
        });

        (findViewById(R.id.btnResetGame)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(GameActivity.this)
                        .setTitle("Confirm")
                        .setMessage("Are you sure you want to reset the game ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                tvScore1.setText("0");
                                tvScore2.setText("0");
                                resetTimers();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).create().show();
            }
        });

        readPlayersFromDatabase();
    }

    private void displayScoreDialog(int i, int id, String name) {
        ScoreDialog dialog = new ScoreDialog();
        if(i == 1) {
            dialog.setIdAndName(id, name, team1);
        }
        else {
            dialog.setIdAndName(id, name, team2);
        }
        dialog.show(getSupportFragmentManager(), "SCORE_DIALOG");
    }

    @Override
    public void getScore(int id, String name, String teamName, int score) {
        if(isTimer1Running && isTimer2Running) {
            if(teamName.equals(team1)) {
                score1[id] += score;
                if(score1[id] < 0)
                    score1[id] = 0;
                int sum = sumArray(score1, 10);
                tvScore1.setText(String.valueOf(sum));
                Toast.makeText(this, "Team: "+teamName + " Player: " + name + " Score: " + score1[id], Toast.LENGTH_SHORT).show();
            }
            else {
                score2[id] += score;
                if(score2[id] < 0)
                    score2[id] = 0;
                int sum = sumArray(score2, 10);
                tvScore2.setText(String.valueOf(sum));
                Toast.makeText(this, "Team: "+teamName + " Player: " + name + " Score: " + score2[id], Toast.LENGTH_SHORT).show();
            }
            timer2.cancel();
            tvTimer2.setText("24");
            timeLeft2 = 24000;
            startTimer2();
        }
        else {
            Toast.makeText(this, "Start the timers in order to start the game", Toast.LENGTH_SHORT).show();
        }
    }

    private int sumArray(int[] arr, int n) {
        int s = 0;
        for(int i = 0 ; i < n ; i++)
            s += arr[i];
        return s;
    }

    private void resetTimers() {
        timer1.cancel();
        timer2.cancel();
        tvTimer1.setText("40:00");
        tvTimer2.setText("24");
        timeLeft1 = 2400000;
        timeLeft2 = 24000;
        isTimer1Running = false;
        isTimer2Running = false;
    }

    private void pauseTimer2() {
        timer2.cancel();
        isTimer2Running = false;
    }

    private void startTimer2() {
        timer2 = new CountDownTimer(timeLeft2, 1000) {
            @Override
            public void onTick(long millis) {
                timeLeft2 = millis;
                int s = (int) (timeLeft2 / 1000) % 60;
                String time = String.format("%02d", s);
                tvTimer2.setText(time);
            }

            @Override
            public void onFinish() {
                tvTimer2.setText("24");
                timeLeft2 = 24000;
                isTimer2Running = false;
            }
        }.start();
        isTimer2Running = true;
    }

    private void pauseTimer1() {
        timer1.cancel();
        isTimer1Running = false;
    }

    private void startTimer1() {
        timer1 = new CountDownTimer(timeLeft1, 1000) {
            @Override
            public void onTick(long millis) {
                timeLeft1 = millis;
                int s = (int) (timeLeft1 / 1000) % 60;
                int m = (int) (timeLeft1 / 1000) / 60;
                String time = String.format("%02d:%02d", m, s);
                tvTimer1.setText(time);
            }

            @Override
            public void onFinish() {
                tvTimer1.setText("40:00");
                timeLeft1 = 2400000;
                isTimer1Running = false;
            }
        }.start();
        isTimer1Running = true;
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
