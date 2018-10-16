package com.example.namankhanna.basketballscorecard;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {

    ArrayList<Team> teamArrayList;
    Context context;

    public interface OnTeamClickListener {
        void getTeamNames(String team1, String team2);
    }

    OnTeamClickListener onTeamClickListener;

    public TeamAdapter(ArrayList<Team> teamArrayList, Context context) {
        this.teamArrayList = teamArrayList;
        this.context = context;
    }

    public void setOnTeamClickListener(OnTeamClickListener onTeamClickListener) {
        this.onTeamClickListener = onTeamClickListener;
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.list_team, parent, false);
        return new TeamViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder holder, int position) {
        final Team team = teamArrayList.get(position);
        holder.tvTeam1.setText(team.getTeam1());
        holder.tvTeam2.setText(team.getTeam2());
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onTeamClickListener != null) {
                    onTeamClickListener.getTeamNames(team.getTeam1(), team.team2);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return teamArrayList.size();
    }

    public static class TeamViewHolder extends RecyclerView.ViewHolder {

        TextView tvTeam1, tvTeam2;
        View rootView;

        public TeamViewHolder(View itemView) {
            super(itemView);
            tvTeam1 = itemView.findViewById(R.id.tvTeam1);
            tvTeam2 = itemView.findViewById(R.id.tvTeam2);
            rootView = itemView;
        }
    }
}
