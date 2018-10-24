package com.example.namankhanna.basketballscorecard;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class TeamDialog extends AppCompatDialogFragment {

    public interface OnPositiveListener {
        void getTeamNames(String team1, String team2);
    }

    OnPositiveListener listener;
    EditText etTeam1, etTeam2;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View itemView = inflater.inflate(R.layout.dialog_team, null);

        etTeam1 = itemView.findViewById(R.id.etTeam1);
        etTeam2 = itemView.findViewById(R.id.etTeam2);

        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int start, int end, Spanned spanned, int i2, int i3) {
                for(int i = start ; i < end ; i++) {
                    if(!Character.isLetter(charSequence.charAt(i)) && charSequence.charAt(i) != ' ') {
                        return "";
                    }
                }
                return null;
            }
        };

        etTeam1.setFilters(new InputFilter[]{filter});
        etTeam2.setFilters(new InputFilter[]{filter});

        builder.setView(itemView)
                .setTitle("Team Names")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!TextUtils.isEmpty(etTeam1.getText().toString()) && !TextUtils.isEmpty(etTeam2.getText().toString())
                                && !etTeam1.getText().toString().equals(etTeam2.getText().toString())) {
                            listener.getTeamNames(etTeam1.getText().toString(), etTeam2.getText().toString());
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
            listener = (OnPositiveListener) context;
        } catch (ClassCastException cce) {
            throw new ClassCastException(context.toString() + "Interface Must be Implemented");
        }
    }
}
