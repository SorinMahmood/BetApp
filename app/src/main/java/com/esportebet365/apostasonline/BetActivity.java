package com.esportebet365.apostasonline;

import static com.esportebet365.apostasonline.AppAll.MAX_GOAL_COUNT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class BetActivity extends AppCompatActivity {

    TextView tvScore;
    TextView tvTitle;

    ImageView ivFirst;
    ImageView ivSecond;

    LinearLayoutCompat llPair1;
    ImageView ivPair1First;
    ImageView ivPair1FirstUp;
    ImageView ivPair1FirstDown;
    TextView tvPair1First;
    ImageView ivPair1Second;
    ImageView ivPair1SecondUp;
    ImageView ivPair1SecondDown;
    TextView tvPair1Second;

    LinearLayoutCompat llPair2;
    ImageView ivPair2First;
    ImageView ivPair2FirstUp;
    ImageView ivPair2FirstDown;
    TextView tvPair2First;
    ImageView ivPair2Second;
    ImageView ivPair2SecondUp;
    ImageView ivPair2SecondDown;
    TextView tvPair2Second;

    LinearLayoutCompat llPair3;
    ImageView ivPair3First;
    ImageView ivPair3FirstUp;
    ImageView ivPair3FirstDown;
    TextView tvPair3First;
    ImageView ivPair3Second;
    ImageView ivPair3SecondUp;
    ImageView ivPair3SecondDown;
    TextView tvPair3Second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bet);

        for (int cnt = 0; cnt < 4; cnt++) {
            AppAll.setBet(cnt, new Pair<>(0, 0));
            AppAll.setScore(cnt, new Pair<>(0, 0));
        }

        tvScore = findViewById(R.id.tv_score);
        tvTitle = findViewById(R.id.tv_title);

        ivFirst = findViewById(R.id.iv_first);
        ivSecond = findViewById(R.id.iv_second);

        llPair1 = findViewById(R.id.ll_pair_1);
        ivPair1First = findViewById(R.id.iv_pair_1_first);
        ivPair1FirstUp = findViewById(R.id.iv_pair_1_first_up);
        ivPair1FirstUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incrementBet(1, true, true);
            }
        });
        ivPair1FirstDown = findViewById(R.id.iv_pair_1_first_down);
        ivPair1FirstDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incrementBet(1, true, false);
            }
        });
        tvPair1First = findViewById(R.id.tv_pair_1_first);
        ivPair1Second = findViewById(R.id.iv_pair_1_second);
        ivPair1SecondUp = findViewById(R.id.iv_pair_1_second_up);
        ivPair1SecondUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incrementBet(1, false, true);
            }
        });
        ivPair1SecondDown = findViewById(R.id.iv_pair_1_second_down);
        ivPair1SecondDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incrementBet(1, false, false);
            }
        });
        tvPair1Second = findViewById(R.id.tv_pair_1_second);

        llPair2 = findViewById(R.id.ll_pair_2);
        ivPair2First = findViewById(R.id.iv_pair_2_first);
        ivPair2FirstUp = findViewById(R.id.iv_pair_2_first_up);
        ivPair2FirstUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incrementBet(2, true, true);
            }
        });
        ivPair2FirstDown = findViewById(R.id.iv_pair_2_first_down);
        ivPair2FirstDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incrementBet(2, true, false);
            }
        });
        tvPair2First = findViewById(R.id.tv_pair_2_first);
        ivPair2Second = findViewById(R.id.iv_pair_2_second);
        ivPair2SecondUp = findViewById(R.id.iv_pair_2_second_up);
        ivPair2SecondUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incrementBet(2, false, true);
            }
        });
        ivPair2SecondDown = findViewById(R.id.iv_pair_2_second_down);
        ivPair2SecondDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incrementBet(2, false, false);
            }
        });
        tvPair2Second = findViewById(R.id.tv_pair_2_second);

        llPair3 = findViewById(R.id.ll_pair_3);
        ivPair3First = findViewById(R.id.iv_pair_3_first);
        ivPair3FirstUp = findViewById(R.id.iv_pair_3_first_up);
        ivPair3FirstUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incrementBet(3, true, true);
            }
        });
        ivPair3FirstDown = findViewById(R.id.iv_pair_3_first_down);
        ivPair3FirstDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incrementBet(3, true, false);
            }
        });
        tvPair3First = findViewById(R.id.tv_pair_3_first);
        ivPair3Second = findViewById(R.id.iv_pair_3_second);
        ivPair3SecondUp = findViewById(R.id.iv_pair_3_second_up);
        ivPair3SecondUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incrementBet(3, false, true);
            }
        });
        ivPair3SecondDown = findViewById(R.id.iv_pair_3_second_down);
        ivPair3SecondDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incrementBet(3, false, false);
            }
        });
        tvPair3Second = findViewById(R.id.tv_pair_3_second);

        ImageView ivStart = findViewById(R.id.iv_start);
        ivStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        tvScore.setText(getString(R.string.totalScore, AppAll.getTotalScore()));

        int resId = 0;
        switch (AppAll.getTour()) {
            case 0:
                resId = R.string.title_bet_quarterfinal;
                break;
            case 1:
                resId = R.string.title_bet_semifinal;
                break;
            case 2:
                resId = R.string.title_bet_final;
                break;
        }
        tvTitle.setText(getString(resId));

        updateTeam();
        updateGoal();
    }

    private void updateTeam() {
        Pair<TeamType, TeamType> goal = AppAll.getPair(0);
        ivFirst.setImageResource(AppAll.getFlagResId(goal.first));
        ivSecond.setImageResource(AppAll.getFlagResId(goal.second));

        goal = AppAll.getPair(1);
        ivPair1First.setImageResource(AppAll.getFlagResId(goal.first));
        ivPair1Second.setImageResource(AppAll.getFlagResId(goal.second));

        goal = AppAll.getPair(2);
        ivPair2First.setImageResource(AppAll.getFlagResId(goal.first));
        ivPair2Second.setImageResource(AppAll.getFlagResId(goal.second));

        goal = AppAll.getPair(3);
        ivPair3First.setImageResource(AppAll.getFlagResId(goal.first));
        ivPair3Second.setImageResource(AppAll.getFlagResId(goal.second));
    }

    private void updateGoal() {
        llPair1.setVisibility(View.GONE);
        llPair2.setVisibility(View.GONE);
        llPair3.setVisibility(View.GONE);

        switch (AppAll.getTour()) {
            case 0:
                llPair3.setVisibility(View.VISIBLE);
                Pair<Integer, Integer> goal = AppAll.getBet(3);
                tvPair3First.setText(Integer.toString(goal.first));
                tvPair3Second.setText(Integer.toString(goal.second));

                llPair2.setVisibility(View.VISIBLE);
                goal = AppAll.getBet(2);
                tvPair2First.setText(Integer.toString(goal.first));
                tvPair2Second.setText(Integer.toString(goal.second));
            case 1:
                llPair1.setVisibility(View.VISIBLE);
                goal = AppAll.getBet(1);
                tvPair1First.setText(Integer.toString(goal.first));
                tvPair1Second.setText(Integer.toString(goal.second));
        }
    }

    private void incrementBet(int pair, boolean firstTeam, boolean up) {
        Pair<Integer, Integer> goal = AppAll.getBet(pair);
        int first = goal.first;
        int second = goal.second;
        if (firstTeam) {
            if (up) {
                first++;
                if (first > MAX_GOAL_COUNT) {
                    first = MAX_GOAL_COUNT;
                }
                if ((first + second) > MAX_GOAL_COUNT) {
                    first = MAX_GOAL_COUNT - second;
                }
            } else {
                first--;
                if (first < 0) {
                    first = 0;
                }
            }
        } else {
            if (up) {
                second++;
                if (second > MAX_GOAL_COUNT) {
                    second = MAX_GOAL_COUNT;
                }
                if ((first + second) > MAX_GOAL_COUNT) {
                    second = MAX_GOAL_COUNT - first;
                }
            } else {
                second--;
                if (second < 0) {
                    second = 0;
                }
            }
        }
        AppAll.setBet(pair, new Pair<>(first, second));

        updateGoal();
    }

    private void start() {
        Intent intent = new Intent(BetActivity.this,PlayActivity.class);
        startActivity(intent);
    }

}