package com.sd.epos.customeralpha.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sd.epos.customeralpha.R;
import com.sd.epos.customeralpha.common.Utils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jabbir on 13/1/16.
 */
public class ChooseActivity extends Activity {
    private String[] myStrings;
    private Bitmap bmp;
    private EditText _lqty;
    private TextView bck_Button, sub_mt;
    private com.sd.epos.customeralpha.common.CoolPriceView txtPric;
    private int[] selectedItem;
    public Timer timer;
    private ViewPager mViewPager;
    int page = 1;
    private String[] sConfig = new String[7];
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choos_layout);
        if (Utils.loadArray("serverName", ChooseActivity.this).length != 7) {
            Utils.userAuth = null;
            Utils.passAuth = null;
            Intent intX = new Intent(ChooseActivity.this, MainActivity.class);
            startActivity(intX);
            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
        }
//        CustomPagerAdapter  mCustomPagerAdapter = new CustomPagerAdapter(this);
//         mViewPager = (ViewPager) findViewById(R.id.view_pager);
//        mViewPager.setAdapter(mCustomPagerAdapter);
        pageSwitcher(2);
//        Intent ii = new Intent(ChooseActivity.this, ChooseActivity.class);
//        ii.putExtra("nextLev", 0);
//        startActivity(ii);
//        finish();
//        bck_Button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//
//            }
//        });
//
//        sub_mt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//            }
//        });

//
        ((TextView) findViewById(R.id._eating)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ii = new Intent(ChooseActivity.this, HomeScreen.class);
        ii.putExtra("nextLev", 0);
        startActivity(ii);
        finish();

            }
        });
        ((TextView) findViewById(R.id._take)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ii = new Intent(ChooseActivity.this, HomeScreen.class);
        ii.putExtra("nextLev", 0);
        startActivity(ii);
        finish();
            }
        });
    }

    public  void pageSwitcher(int seconds) {
  Timer timer = new Timer(); // At this line a new Thread will be created
        timer.scheduleAtFixedRate(new RemindTask(), 0, seconds * 1000); // delay
        // in
        // milliseconds
    }

    // this is an inner class...
    class RemindTask extends TimerTask {

        @Override
        public void run() {

            // As the TimerTask run on a seprate thread from UI thread we have
            // to call runOnUiThread to do work on UI thread.
            runOnUiThread(new Runnable() {
                public void run() {

                    if (page > 5) {
                        page=1;
                        mViewPager.setCurrentItem(page);

                    // In my case the number of pages are 5
//                        timer.cancel();
//                        // Showing a toast for just testing purpose
//                        Toast.makeText(getApplicationContext(), "Timer stoped",
//                                Toast.LENGTH_LONG).show();
                    } else {
                        mViewPager.setCurrentItem(page++);
                    }
                }
            });

        }
    }



}