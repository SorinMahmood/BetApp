package com.esportebet365.apostasonline;

import static android.view.MotionEvent.ACTION_CANCEL;
import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_UP;

import static com.esportebet365.apostasonline.AppAll.MAX_GOAL_COUNT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PlayActivity extends AppCompatActivity {

    TextView tvScore;
    ImageView ivBall;
    ImageView ivPlayerTop;
    ImageView ivPlayerBottom;

    int m_fieldWidth;
    int m_fieldHeight;
    int m_ballSize;
    int m_playerWidth;
    int m_playerHeight;
    int m_playerGap;
    Handler m_handler;
    int m_playPair;

    float xBallGoal;
    float yBallGoal;
    float dXBallGoal;
    float dYBallGoal;
    float dXBall;
    float dYBall;
    float dXPlayer;
    boolean goal;

    Pair<Float, Float>[] m_ballDirs = new Pair[] {
            new Pair<>(-2F, -2F),
            new Pair<>(2F, 2F),
            new Pair<>(2F, -2F),
            new Pair<>(-2F, 2F),
    };
    float[] m_playerDirs = new float[] {
            0.75F,
            -0.75F
    };

    private final Animator.AnimatorListener m_ballAnimatorListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animator) {
        }

        @Override
        public void onAnimationEnd(Animator animator) {
            animateBall();
        }

        @Override
        public void onAnimationCancel(Animator animator) {
        }

        @Override
        public void onAnimationRepeat(Animator animator) {
        }
    };
    private final Animator.AnimatorListener m_playerAnimatorListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animator) {
        }

        @Override
        public void onAnimationEnd(Animator animator) {
            animatePlayerTop();
        }

        @Override
        public void onAnimationCancel(Animator animator) {
        }

        @Override
        public void onAnimationRepeat(Animator animator) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_play);

        tvScore = findViewById(R.id.tv_score);
        ivBall = findViewById(R.id.iv_ball);
        ivPlayerTop = findViewById(R.id.iv_player_top);
        ivPlayerBottom = findViewById(R.id.iv_player_bottom);

        m_ballSize = getResources().getDimensionPixelSize(R.dimen.dimenBall);
        m_playerWidth = getResources().getDimensionPixelSize(R.dimen.dimenPlayerWidth);
        m_playerHeight = getResources().getDimensionPixelSize(R.dimen.dimenPlayerHeight);
        m_playerGap = getResources().getDimensionPixelSize(R.dimen.dimenPlayerGap);
        m_handler = new Handler();

        RelativeLayout rlField = findViewById(R.id.rl_field);
        rlField.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                int fieldWidth = right - left;
                int fieldHeight = bottom - top;
                if (m_fieldWidth != fieldWidth || m_fieldHeight != fieldHeight) {
                    m_fieldWidth = fieldWidth;
                    m_fieldHeight = fieldHeight;
                    init();
                }
            }
        });

        TeamType userTeam = AppAll.getTeam();
        for (int cnt = 0; cnt < 4; cnt++) {
            Pair<TeamType, TeamType> pair = AppAll.getPair(cnt);
            if (pair.first == userTeam || pair.second == userTeam) {
                m_playPair = cnt;
                break;
            }
        }
        updateScore();
    }

    @Override
    public void onBackPressed() {
        stop(false);

        super.onBackPressed();
    }

    private void init() {
        ivBall.setX((m_fieldWidth - m_ballSize) / 2F);
        ivBall.setY((m_fieldHeight - m_ballSize) / 2F);

        ivPlayerTop.setX((m_fieldWidth - m_playerWidth) / 2F);
        ivPlayerTop.setY(0);

        ivPlayerBottom.setX((m_fieldWidth - m_playerWidth) / 2F);
        ivPlayerBottom.setY(m_fieldHeight - m_playerHeight);

        Pair<Float, Float> ballDir = m_ballDirs[AppAll.getRandom(m_ballDirs.length)];
        dXBall = ballDir.first;
        dYBall = ballDir.second;
        dXPlayer = m_playerDirs[AppAll.getRandom(m_playerDirs.length)];

        dXBallGoal = dXBall;
        dYBallGoal = dYBall;

        goal = false;

        m_handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                start();
            }
        }, 1000);
    }

    private void updateScore() {
        Pair<Integer, Integer> score = AppAll.getScore(m_playPair);
        tvScore.setText(getString(R.string.playScore, score.first, score.second));
    }

    private void start() {
        animateBall();
        animatePlayerTop();
        ivPlayerBottom.setOnTouchListener(new PlayerTouchListener(this));
    }

    private void stop(boolean finish) {
        ivPlayerBottom.setOnTouchListener(null);
        if (finish) {
            for (int cnt = 0; cnt < 4; cnt++) {
                if (cnt != m_playPair) {
                    int score = AppAll.getRandom(MAX_GOAL_COUNT + 1);
                    AppAll.setScore(cnt, new Pair<>(score, MAX_GOAL_COUNT - score));
                }
            }
            Intent intent = new Intent(PlayActivity.this,ResultActivity.class);
            startActivity(intent);
        } else {
            AppAll.setScore(m_playPair, new Pair<>(0, 0));
        }
    }

    private void goal(boolean bottomPlayer) {
        ivPlayerTop.animate().cancel();
        ivPlayerBottom.animate().cancel();

        Pair<Integer, Integer> score = AppAll.getScore(m_playPair);
        int scoreBottom = score.first;
        int scoreTop = score.second;
        if (bottomPlayer) {
            scoreBottom++;
        } else {
            scoreTop++;
        }
        AppAll.setScore(m_playPair, new Pair<>(scoreBottom, scoreTop));
        updateScore();

        m_handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Pair<Integer, Integer> score = AppAll.getScore(m_playPair);
                if ((score.first + score.second) < MAX_GOAL_COUNT) {
                    init();
                } else {
                    stop(true);
                }
            }
        }, 1000);
    }

    private void animateBall() {
        if (goal) {
            animateBallGoal();
        } else {
            float fieldTop = m_playerHeight - m_playerGap;
            float fieldBottom = m_fieldHeight - (m_playerHeight - m_playerGap) - m_ballSize;
            float fieldWidth = m_fieldWidth - m_ballSize;

            float ballX = Math.round(ivBall.getX());
            float ballY = Math.round(ivBall.getY());

            double speed = Math.sqrt(dXBall * dXBall + dYBall * dYBall) / 4;

            if (ballY <= (fieldTop + 1) || ballY >= (fieldBottom - 1)) {
                float playerX;
                if (ballY < (m_fieldHeight / 2F)) {
                    playerX = Math.round(ivPlayerTop.getX());
                } else {
                    playerX = Math.round(ivPlayerBottom.getX());
                }
                if ((ballX + m_ballSize) < playerX || ballX > (playerX + m_playerWidth)) {
                    goal = true;
                    float dX = xBallGoal - ballX;
                    float dY = yBallGoal - ballY;
                    long duration = Math.round(Math.sqrt(dX * dX + dY * dY) / speed);
                    ivBall.animate()
                            .x(Math.round(xBallGoal))
                            .y(Math.round(yBallGoal))
                            .setDuration(duration)
                            .setInterpolator(new LinearInterpolator())
                            .setListener(m_ballAnimatorListener);
                    return;
                }
            }

            float x1 = ballX + 100 * dXBall;
            float y1 = ballY + 100 * dYBall;

            PointF crossH;
            PointF crossV;
            if (dXBall > 0) {
                crossV = calculateCross(ballX, ballY, x1, y1, fieldWidth, fieldTop, fieldWidth, fieldBottom);
            } else {
                crossV = calculateCross(ballX, ballY, x1, y1, 0, fieldTop, 0, fieldBottom);
            }
            if (dYBall > 0) {
                crossH = calculateCross(ballX, ballY, x1, y1, 0, fieldBottom, fieldWidth, fieldBottom);
            } else {
                crossH = calculateCross(ballX, ballY, x1, y1, 0, fieldTop, fieldWidth, fieldTop);
            }

            float fieldTopGoal = 0;
            float fieldBottomGoal = m_fieldHeight - m_ballSize;
            PointF crossHGoal;
            PointF crossVGoal;
            if (dXBall > 0) {
                crossVGoal = calculateCross(ballX, ballY, x1, y1, fieldWidth, fieldTopGoal, fieldWidth, fieldBottomGoal);
            } else {
                crossVGoal = calculateCross(ballX, ballY, x1, y1, 0, fieldTopGoal, 0, fieldBottomGoal);
            }
            if (dYBall > 0) {
                crossHGoal = calculateCross(ballX, ballY, x1, y1, 0, fieldBottomGoal, fieldWidth, fieldBottomGoal);
            } else {
                crossHGoal = calculateCross(ballX, ballY, x1, y1, 0, fieldTopGoal, fieldWidth, fieldTopGoal);
            }

            long duration;
            float dX = crossV.x - ballX;
            float dY = crossV.y - ballY;
            float distV = dX * dX + dY * dY;
            dX = crossH.x - ballX;
            dY = crossH.y - ballY;
            float distH = dX * dX + dY * dY;
            if (distH < distV) {
                dYBall = -dYBall;
                x1 = crossH.x;
                y1 = crossH.y;
                duration = Math.round(Math.sqrt(distH) / speed);
            } else {
                dXBall = -dXBall;
                x1 = crossV.x;
                y1 = crossV.y;
                duration = Math.round(Math.sqrt(distV) / speed);
            }

            float dXGoal = crossVGoal.x - ballX;
            float dYGoal = crossVGoal.y - ballY;
            float distVGoal = dXGoal * dXGoal + dYGoal * dYGoal;
            dXGoal = crossHGoal.x - ballX;
            dYGoal = crossHGoal.y - ballY;
            float distHGoal = dXGoal * dXGoal + dYGoal * dYGoal;
            if (distHGoal < distVGoal) {
                dYBallGoal = -dYBallGoal;
                xBallGoal = crossHGoal.x;
                yBallGoal = crossHGoal.y;
            } else {
                dXBallGoal = -dXBallGoal;
                xBallGoal = crossVGoal.x;
                yBallGoal = crossVGoal.y;
            }
            ivBall.animate()
                    .x(Math.round(x1))
                    .y(Math.round(y1))
                    .setDuration(duration)
                    .setInterpolator(new LinearInterpolator())
                    .setListener(m_ballAnimatorListener);
        }
    }

    private void animateBallGoal() {
        float ballX = Math.round(ivBall.getX());
        float ballY = Math.round(ivBall.getY());
        float x1 = ballX + 100 * dXBallGoal;
        float y1 = ballY + 100 * dYBallGoal;

        float fieldTopGoal = 0;
        float fieldBottomGoal = m_fieldHeight - m_ballSize;
        float fieldWidth = m_fieldWidth - m_ballSize;
        PointF crossHGoal;
        PointF crossVGoal;
        if (dXBallGoal > 0) {
            crossVGoal = calculateCross(ballX, ballY, x1, y1, fieldWidth, fieldTopGoal, fieldWidth, fieldBottomGoal);
        } else {
            crossVGoal = calculateCross(ballX, ballY, x1, y1, 0, fieldTopGoal, 0, fieldBottomGoal);
        }
        if (dYBallGoal > 0) {
            crossHGoal = calculateCross(ballX, ballY, x1, y1, 0, fieldBottomGoal, fieldWidth, fieldBottomGoal);
        } else {
            crossHGoal = calculateCross(ballX, ballY, x1, y1, 0, fieldTopGoal, fieldWidth, fieldTopGoal);
        }

        double speed = Math.sqrt(dXBallGoal * dXBallGoal + dYBallGoal * dYBallGoal) / 4;
        long duration;
        float dX = crossVGoal.x - ballX;
        float dY = crossVGoal.y - ballY;
        float distV = dX * dX + dY * dY;
        dX = crossHGoal.x - ballX;
        dY = crossHGoal.y - ballY;
        float distH = dX * dX + dY * dY;
        if (distH < distV) {
            dYBallGoal = -dYBallGoal;
            x1 = crossHGoal.x;
            y1 = crossHGoal.y;
            duration = Math.round(Math.sqrt(distH) / speed);
            ivBall.animate()
                    .x(Math.round(x1))
                    .y(Math.round(y1))
                    .setDuration(duration)
                    .setInterpolator(new LinearInterpolator())
                    .setListener(m_ballAnimatorListener);
        } else {
            goal(ballY < (m_fieldHeight / 2F));
        }
    }

    private void animatePlayerTop() {
        float y = Math.round(ivPlayerTop.getY());
        float x0 = Math.round(ivPlayerTop.getX());
        float x1 = x0 + 100 * dXPlayer;

        float fieldWidth = m_fieldWidth - m_playerWidth;

        PointF crossV;
        if (dXPlayer > 0) {
            crossV = calculateCross(x0, y, x1, y, fieldWidth, 0, fieldWidth, m_fieldHeight);
        } else {
            crossV = calculateCross(x0, y, x1, y, 0, 0, 0, m_fieldHeight);
        }

        double speed = Math.sqrt(dXBall * dXBall) / 4;
        long duration;
        float dX = crossV.x - x1;
        float distV = dX * dX;
        dXPlayer = -dXPlayer;
        x1 = crossV.x;
        duration = Math.round(Math.sqrt(distV) / speed);

        if (x1 < 0) {
            x1 = fieldWidth;
        } else if (x1 > fieldWidth) {
            x1 = 0;
        }
        ivPlayerTop.animate()
                .x(Math.round(x1))
                .setDuration(duration)
                .setInterpolator(new LinearInterpolator())
                .setListener(m_playerAnimatorListener);
    }

    private void animatePlayerBottom() {
    }

    private PointF calculateCross(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
        float n;
        if (y2 - y1 != 0) {
            float q = (x2 - x1) / (y1 - y2);
            float sn = (x3 - x4) + (y3 - y4) * q;
            if (sn == 0) {
                return new PointF(0, 0);
            }
            float fn = (x3 - x1) + (y3 - y1) * q;
            n = fn / sn;
        }
        else {
            if ((y3 - y4) == 0) {
                return new PointF(0, 0);
            }
            n = (y3 - y1) / (y3 - y4);
        }
        return new PointF(x3 + (x4 - x3) * n, y3 + (y4 - y3) * n);
    }

    private class PlayerTouchListener extends GestureDetector.SimpleOnGestureListener implements View.OnTouchListener {
        GestureDetectorCompat m_gestureDetector;
        float m_x;
        float m_dX;

        PlayerTouchListener(Context context) {
            m_gestureDetector = new GestureDetectorCompat(context, this);
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            boolean result = false;
            try {
                result = m_gestureDetector.onTouchEvent(motionEvent);

                switch (motionEvent.getAction()) {
                    case ACTION_DOWN:
                        m_x = ivPlayerBottom.getX();
                        m_dX = m_x - motionEvent.getRawX();
                        result = true;
                        break;
                    case ACTION_MOVE:
                        move(motionEvent.getRawX());
                        result = true;
                        break;
                    case ACTION_UP:
                    case ACTION_CANCEL:
                        result = true;
                        break;
                }
            }
            catch (IllegalArgumentException ignore) {
            }
            return result;
        }

        private void move(float x) {
            float pos = x + m_dX;
            if (pos < 0) {
                pos = 0;
            } else if (pos > (m_fieldWidth - m_playerWidth)) {
                pos = m_fieldWidth - m_playerWidth;
            }
            ivPlayerBottom.animate().x(pos).setDuration(0).start();
        }
    }
}
