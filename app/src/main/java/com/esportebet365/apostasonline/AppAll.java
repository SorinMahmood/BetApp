package com.esportebet365.apostasonline;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.DrawableRes;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Random;

public class AppAll extends Application {

    public static final int MAX_GOAL_COUNT = 5;

    private static AppAll m_instance = null;

    public static AppAll getInstance() {
        return m_instance;
    }

    private Random m_random;
    private int m_totalScore = 1000;
    private Pair<TeamType, TeamType>[] m_teams = new Pair[4];
    private Pair<Integer, Integer>[] m_bets = new Pair[4];
    private Pair<Integer, Integer>[] m_scores = new Pair[4];
    private TeamType m_team;
    private int m_tour;

    @Override
    public void onCreate() {
        super.onCreate();

        soccerAtr();
        m_instance = this;

        m_random = new Random();
    }

    public static int getTotalScore() {
        return m_instance.m_totalScore;
    }

    public static void setTotalScore(int score) {
        m_instance.m_totalScore = score;
    }

    public static int getTour() {
        return m_instance.m_tour;
    }

    public static void setTour(int tour) {
        m_instance.m_tour = tour;
    }

    public static TeamType getTeam() {
        return m_instance.m_team;
    }

    public static void setTeam(TeamType team) {
        m_instance.m_team = team;
    }

    public static Pair<TeamType, TeamType> getPair(int number) {
        return m_instance.m_teams[number];
    }

    public static void setPair(int number, Pair<TeamType, TeamType> pair) {
        m_instance.m_teams[number] = pair;
    }

    public static Pair<Integer, Integer> getBet(int number) {
        return m_instance.m_bets[number];
    }

    public static void setBet(int number, Pair<Integer, Integer> pair) {
        m_instance.m_bets[number] = pair;
    }

    public static Pair<Integer, Integer> getScore(int number) {
        return m_instance.m_scores[number];
    }

    public static void setScore(int number, Pair<Integer, Integer> pair) {
        m_instance.m_scores[number] = pair;
    }

    public static @DrawableRes
    int getFlagResId(TeamType teamType) {
        TypedArray typedArray = m_instance.getResources().obtainTypedArray(R.array.arrayFlags);
        int resId = typedArray.getResourceId(teamType.ordinal(), 0);
        typedArray.recycle();
        return resId;
    }

    public static int getRandom(int count, int... exclude) {
        ArrayList<Integer> exs = new ArrayList<>(exclude.length);
        for (int ex : exclude) {
            exs.add(ex);
        }
        return getRandom(count, exs);
    }
    public static int getRandom(int count, ArrayList<Integer> exs) {
        Collections.sort(exs, new Comparator<Integer>(){
            @Override
            public int compare(Integer i0, Integer i1) {
                return Integer.compare(i0, i1);
            }
        });
        int random = m_instance.m_random.nextInt(count - exs.size());
        for (int ex : exs) {
            if (random < ex) {
                break;
            }
            random++;
        }
        return random;
    }
    private void soccerAtr() {
        AppsFlyerConversionListener conversionListener = new AppsFlyerConversionListener() {
            @Override
            public void onConversionDataSuccess(Map<String, Object> conversionData) {
                JSONObject json = new JSONObject(conversionData);
                write_key1(json.toString());
            }

            @Override
            public void onConversionDataFail(String errorMessage) {
                JSONObject json = new JSONObject();
                try {
                    json.put("error", errorMessage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                write_key1(json.toString());
            }

            @Override
            public void onAppOpenAttribution(Map<String, String> attributionData) {
                for (String attrName : attributionData.keySet()) {
                    Log.d("LOG_TAG", "attribute: " + attrName + " = " + attributionData.get(attrName));
                }
            }

            @Override
            public void onAttributionFailure(String errorMessage) {
                Log.d("LOG_TAG", "error onAttributionFailure : " + errorMessage);
            }
        };

        AppsFlyerLib.getInstance().init("oN8Wj3gv6EAM4rveYyx8Fg", conversionListener, this); //todo вставить ключ апса
        AppsFlyerLib.getInstance().start(this);
    }

    public void write_key1(String s) {
        SharedPreferences myPrefs = getSharedPreferences("file", Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = myPrefs.edit();
        editor.putString("key1", s);
        editor.apply();
    }
}
