package com.esportebet365.apostasonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class StartNextActivity extends AppCompatActivity {

    ImageView ivContinue;
    ImageView[] ivFlags = new ImageView[TeamType.values().length];

    private View.OnClickListener m_clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            selectTeam(view);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start_next);

        ivFlags[TeamType.Argentina.ordinal()] = findViewById(R.id.iv_argentina);
        ivFlags[TeamType.Belgium.ordinal()] = findViewById(R.id.iv_belgium);
        ivFlags[TeamType.Brazil.ordinal()] = findViewById(R.id.iv_brazil);
        ivFlags[TeamType.GreatBritain.ordinal()] = findViewById(R.id.iv_greatbritain);
        ivFlags[TeamType.Turkey.ordinal()] = findViewById(R.id.iv_turkey);
        ivFlags[TeamType.Korea.ordinal()] = findViewById(R.id.iv_korea);
        ivFlags[TeamType.Japan.ordinal()] = findViewById(R.id.iv_japan);
        ivFlags[TeamType.France.ordinal()] = findViewById(R.id.iv_france);
        ivFlags[TeamType.Finland.ordinal()] = findViewById(R.id.iv_finland);

        for (ImageView ivFlag : ivFlags) {
            ivFlag.setOnClickListener(m_clickListener);
        }

        ivContinue = findViewById(R.id.iv_continue);
        ivContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contin();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        AppAll.setTeam(null);
        AppAll.setTour(0);

        for (ImageView view : ivFlags) {
            view.setSelected(false);
        }
        ivContinue.setVisibility(View.INVISIBLE);
    }

    private void selectTeam(View view) {
        TeamType teamType = null;
        for (int cnt = 0; cnt < ivFlags.length; cnt++) {
            ivFlags[cnt].setSelected(false);
            if (ivFlags[cnt] == view) {
                ivFlags[cnt].setSelected(true);
                teamType = TeamType.values()[cnt];
            }
        }
        AppAll.setTeam(teamType);

        ivContinue.setVisibility(View.VISIBLE);
    }

    private void contin() {
        ArrayList<Integer> teams = new ArrayList<>();

        TeamType firstTeam = AppAll.getTeam();
        teams.add(firstTeam.ordinal());
        TeamType secondTeam = TeamType.values()[AppAll.getRandom(TeamType.values().length, firstTeam.ordinal())];
        teams.add(secondTeam.ordinal());
        AppAll.setPair(0, new Pair<>(firstTeam, secondTeam));

        for (int cnt = 1; cnt < 4; cnt++) {
            firstTeam = TeamType.values()[AppAll.getRandom(TeamType.values().length, teams)];
            teams.add(firstTeam.ordinal());
            secondTeam = TeamType.values()[AppAll.getRandom(TeamType.values().length, teams)];
            teams.add(secondTeam.ordinal());
            AppAll.setPair(cnt, new Pair<>(firstTeam, secondTeam));
        }

        Intent intent = new Intent(StartNextActivity.this,BetActivity.class);
        startActivity(intent);
    }
}
