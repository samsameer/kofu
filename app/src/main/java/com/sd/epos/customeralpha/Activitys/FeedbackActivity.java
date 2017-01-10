package com.sd.epos.customeralpha.Activitys;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.sd.epos.customeralpha.R;
import com.sd.epos.customeralpha.common.Utils;

/**
 * Created by jabbir on 18/10/15.
 */
public class FeedbackActivity extends Activity {
    private WebView txVi;
    private LinearLayout lx1;
    private ScrollView lx2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);
        getWindow().setLayout(1600, 1500);
        //txVi = (TextView) findViewById(R.id.ty2);
        lx1 = (LinearLayout) findViewById(R.id.btnnl);
        lx2 = (ScrollView) findViewById(R.id.scr1);


        final WebView webview = (WebView) findViewById(R.id.ty2);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                webview.loadUrl("javascript:document.getElementsByClassName('header')[0].style.display=\"none\";");
            }
        });
        webview.loadUrl("http://apac2.sharingan.capillarytech.com/app/TCCRegistration#!/login");
//        txVi = (WebView) findViewById(R.id.ty2);
//        txVi.getSettings().setJavaScriptEnabled(true);
//        txVi.loadUrl("http://www.elemen.com.sg/rewards");
//        WebSettings settings = txVi.getSettings();
//        settings.setSupportMultipleWindows(false);

        if (!Utils.isConnected(FeedbackActivity.this)) {

            webview.setVisibility(View.GONE);
            lx2.setVisibility(View.VISIBLE);
        }
        else{
            lx2.setVisibility(View.GONE);
            webview.setVisibility(View.VISIBLE);
        }

        ((Button) findViewById(R.id.lbtn1)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mth();
            }
        });
        ((Button) findViewById(R.id.accept)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mth();
            }
        });
        ((Button) findViewById(R.id.decli)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mth();
            }
        });
        ((Button) findViewById(R.id.lbtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lx1.setVisibility(View.GONE);
                lx2.setVisibility(View.VISIBLE);

            }
        });


    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            // do your menu stuff here
            Log.d("TAG", "came here");
            return true;
        } else
            return super.onKeyDown(keyCode, event);
    }

    private void mth() {
        Intent i = new Intent(FeedbackActivity.this, HomeScreen.class);
        i.putExtra("nextLev", 2);
        startActivity(i);


    }
    @Override
    protected void onPause() {
        super.onPause();

        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);

        activityManager.moveTaskToFront(getTaskId(), 0);
    }
    @Override
    public void onBackPressed() {
        mth();




    }
}
