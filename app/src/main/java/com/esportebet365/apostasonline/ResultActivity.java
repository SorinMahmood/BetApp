package com.esportebet365.apostasonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ResultActivity extends AppCompatActivity {

    TextView tvScore;
    TextView tvTitle;

    LinearLayoutCompat llPair0;
    TextView tvPair0;
    ImageView ivPair0First;
    TextView tvPair0First;
    TextView tvPair0Div;
    ImageView ivPair0Second;
    TextView tvPair0Second;

    LinearLayoutCompat llPair1;
    TextView tvPair1;
    ImageView ivPair1First;
    TextView tvPair1First;
    TextView tvPair1Div;
    ImageView ivPair1Second;
    TextView tvPair1Second;

    LinearLayoutCompat llPair2;
    TextView tvPair2;
    ImageView ivPair2First;
    TextView tvPair2First;
    TextView tvPair2Div;
    ImageView ivPair2Second;
    TextView tvPair2Second;

    LinearLayoutCompat llPair3;
    TextView tvPair3;
    ImageView ivPair3First;
    TextView tvPair3First;
    TextView tvPair3Div;
    ImageView ivPair3Second;
    TextView tvPair3Second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_result);

        tvScore = findViewById(R.id.tv_score);
        tvTitle = findViewById(R.id.tv_title);

        llPair0 = findViewById(R.id.ll_pair_0);
        tvPair0 = findViewById(R.id.tv_pair_0);
        ivPair0First = findViewById(R.id.iv_pair_0_first);
        tvPair0First = findViewById(R.id.tv_pair_0_first);
        tvPair0Div = findViewById(R.id.tv_pair_0_div);
        ivPair0Second = findViewById(R.id.iv_pair_0_second);
        tvPair0Second = findViewById(R.id.tv_pair_0_second);

        llPair1 = findViewById(R.id.ll_pair_1);
        tvPair1 = findViewById(R.id.tv_pair_1);
        ivPair1First = findViewById(R.id.iv_pair_1_first);
        tvPair1First = findViewById(R.id.tv_pair_1_first);
        tvPair1Div = findViewById(R.id.tv_pair_1_div);
        ivPair1Second = findViewById(R.id.iv_pair_1_second);
        tvPair1Second = findViewById(R.id.tv_pair_1_second);

        llPair2 = findViewById(R.id.ll_pair_2);
        tvPair2 = findViewById(R.id.tv_pair_2);
        ivPair2First = findViewById(R.id.iv_pair_2_first);
        tvPair2First = findViewById(R.id.tv_pair_2_first);
        tvPair2Div = findViewById(R.id.tv_pair_2_div);
        ivPair2Second = findViewById(R.id.iv_pair_2_second);
        tvPair2Second = findViewById(R.id.tv_pair_2_second);

        llPair3 = findViewById(R.id.ll_pair_3);
        tvPair3 = findViewById(R.id.tv_pair_3);
        ivPair3First = findViewById(R.id.iv_pair_3_first);
        tvPair3First = findViewById(R.id.tv_pair_3_first);
        tvPair3Div = findViewById(R.id.tv_pair_3_div);
        ivPair3Second = findViewById(R.id.iv_pair_3_second);
        tvPair3Second = findViewById(R.id.tv_pair_3_second);

        ImageView ivContinue = findViewById(R.id.iv_continue);
        ivContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextTour();
            }
        });


        int resId = 0;
        switch (AppAll.getTour()) {
            case 0:
                resId = R.string.title_result_quarterfinal;
                break;
            case 1:
                resId = R.string.title_result_semifinal;
                break;
            case 2:
                resId = R.string.title_result_final;
                break;
        }
        tvTitle.setText(getString(resId));

        Pair<TeamType, TeamType> pair = AppAll.getPair(0);
        ivPair0First.setImageResource(AppAll.getFlagResId(pair.first));
        ivPair0Second.setImageResource(AppAll.getFlagResId(pair.second));

        pair = AppAll.getPair(1);
        ivPair1First.setImageResource(AppAll.getFlagResId(pair.first));
        ivPair1Second.setImageResource(AppAll.getFlagResId(pair.second));

        pair = AppAll.getPair(2);
        ivPair2First.setImageResource(AppAll.getFlagResId(pair.first));
        ivPair2Second.setImageResource(AppAll.getFlagResId(pair.second));

        pair = AppAll.getPair(3);
        ivPair3First.setImageResource(AppAll.getFlagResId(pair.first));
        ivPair3Second.setImageResource(AppAll.getFlagResId(pair.second));

        llPair1.setVisibility(View.GONE);
        llPair2.setVisibility(View.GONE);
        llPair3.setVisibility(View.GONE);

        String win = getString(R.string.win);
        String lose = getString(R.string.lose);
        int colorWin = ContextCompat.getColor(this, R.color.colorWin);
        int colorLose = ContextCompat.getColor(this, R.color.colorLose);
        int totalScore = AppAll.getTotalScore();
        switch (AppAll.getTour()) {
            case 0:
                llPair3.setVisibility(View.VISIBLE);
                Pair<Integer, Integer> score = AppAll.getScore(3);
                tvPair3First.setText(Integer.toString(score.first));
                tvPair3Second.setText(Integer.toString(score.second));
                Pair<Integer, Integer> bet = AppAll.getBet(3);
                if (bet.first.equals(score.first) && bet.second.equals(score.second)) {
                    tvPair3.setText(win);
                    tvPair3.setTextColor(colorWin);
                    tvPair3Div.setTextColor(colorWin);
                    tvPair3First.setTextColor(colorWin);
                    tvPair3Second.setTextColor(colorWin);
                    totalScore += 50;
                } else {
                    tvPair3.setText(lose);
                    tvPair3.setTextColor(colorLose);
                    tvPair3Div.setTextColor(colorLose);
                    tvPair3First.setTextColor(colorLose);
                    tvPair3Second.setTextColor(colorLose);
                    totalScore -= 50;
                }

                llPair2.setVisibility(View.VISIBLE);
                score = AppAll.getScore(2);
                tvPair2First.setText(Integer.toString(score.first));
                tvPair2Second.setText(Integer.toString(score.second));
                bet = AppAll.getBet(2);
                if (bet.first.equals(score.first) && bet.second.equals(score.second)) {
                    tvPair2.setText(win);
                    tvPair2.setTextColor(colorWin);
                    tvPair2Div.setTextColor(colorWin);
                    tvPair2First.setTextColor(colorWin);
                    tvPair2Second.setTextColor(colorWin);
                    totalScore += 50;
                } else {
                    tvPair2.setText(lose);
                    tvPair2.setTextColor(colorLose);
                    tvPair2Div.setTextColor(colorLose);
                    tvPair2First.setTextColor(colorLose);
                    tvPair2Second.setTextColor(colorLose);
                    totalScore -= 50;
                }
            case 1:
                llPair1.setVisibility(View.VISIBLE);
                score = AppAll.getScore(1);
                tvPair1First.setText(Integer.toString(score.first));
                tvPair1Second.setText(Integer.toString(score.second));
                bet = AppAll.getBet(1);
                if (bet.first.equals(score.first) && bet.second.equals(score.second)) {
                    tvPair1.setText(win);
                    tvPair1.setTextColor(colorWin);
                    tvPair1Div.setTextColor(colorWin);
                    tvPair1First.setTextColor(colorWin);
                    tvPair1Second.setTextColor(colorWin);
                    totalScore += 50;
                } else {
                    tvPair1.setText(lose);
                    tvPair1.setTextColor(colorLose);
                    tvPair1Div.setTextColor(colorLose);
                    tvPair1First.setTextColor(colorLose);
                    tvPair1Second.setTextColor(colorLose);
                    totalScore -= 50;
                }
            case 2:
                score = AppAll.getScore(0);
                tvPair0First.setText(Integer.toString(score.first));
                tvPair0Second.setText(Integer.toString(score.second));
                if (score.first > score.second) {
                    tvPair0.setText(win);
                    tvPair0.setTextColor(colorWin);
                    tvPair0Div.setTextColor(colorWin);
                    tvPair0First.setTextColor(colorWin);
                    tvPair0Second.setTextColor(colorWin);
                    totalScore += 100;
                } else {
                    tvPair0.setText(lose);
                    tvPair0.setTextColor(colorLose);
                    tvPair0Div.setTextColor(colorLose);
                    tvPair0First.setTextColor(colorLose);
                    tvPair0Second.setTextColor(colorLose);
                }
        }
        AppAll.setTotalScore(totalScore);

        tvScore.setText(getString(R.string.totalScore, totalScore));
    }

    private void nextTour() {
        int tour = AppAll.getTour();
        tour++;
        if (tour < 3) {
            AppAll.setTour(tour);
            ArrayList<Pair<Integer, TeamType>> winners = new ArrayList<>();
            int pairCount = 0;
            switch (tour) {
                case 1:
                    pairCount = 4;
                    break;
                case 2:
                    pairCount = 2;
                    break;
            }
            for (int cnt = 0; cnt < pairCount; cnt++) {
                Pair<Integer, Integer> score = AppAll.getScore(cnt);
                Pair<TeamType, TeamType> pair = AppAll.getPair(cnt);
                if (score.first > score.second) {
                    winners.add(new Pair<>(score.first, pair.first));
                } else {
                    winners.add(new Pair<>(score.second, pair.second));
                }
            }
            TeamType userTeam = AppAll.getTeam();
            Collections.sort(winners, new Comparator<Pair<Integer, TeamType>>() {
                @Override
                public int compare(Pair<Integer, TeamType> i0, Pair<Integer, TeamType> i1) {
                    if (i0.second == userTeam) {
                        return -1;
                    }
                    if (i1.second == userTeam) {
                        return 1;
                    }
                    return Integer.compare(i1.first, i0.first);
                }
            });
            int userIndex = -1;
            for (int cnt = 0; cnt < winners.size(); cnt++) {
                Pair<Integer, TeamType> team = winners.get(cnt);
                if (team.second == userTeam) {
                    userIndex = cnt;
                    break;
                }
            }
            if (userIndex >= 0) {
                switch (tour) {
                    case 1:
                        if (userIndex != 2) {
                            AppAll.setPair(0, new Pair<>(winners.get(0).second, winners.get(2).second));
                        } else {
                            AppAll.setPair(0, new Pair<>(winners.get(2).second, winners.get(0).second));
                        }
                        if (userIndex != 3) {
                            AppAll.setPair(1, new Pair<>(winners.get(1).second, winners.get(3).second));
                        } else {
                            AppAll.setPair(1, new Pair<>(winners.get(3).second, winners.get(1).second));
                        }
                        break;
                    case 2:
                        AppAll.setPair(0, new Pair<>(winners.get(0).second, winners.get(1).second));
                        break;
                }
                Intent intent = new Intent(ResultActivity.this,BetActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(ResultActivity.this,StartNextActivity.class);
                startActivity(intent);
            }
        } else {
            Intent intent = new Intent(ResultActivity.this,StartNextActivity.class);
            startActivity(intent);
        }
    }


}