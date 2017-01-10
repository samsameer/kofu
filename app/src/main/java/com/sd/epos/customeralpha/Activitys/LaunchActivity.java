package com.sd.epos.customeralpha.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.crashlytics.android.Crashlytics;
import com.sd.epos.customeralpha.R;
import com.sd.epos.customeralpha.common.Utils;

import io.fabric.sdk.android.Fabric;

/**
 * Created by jabbir on 14/5/15.
 */
public class LaunchActivity extends Activity {
    private Animation animation;
    private ImageView imageView;
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_logo);
        animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Utils.removeJsonSharedPreferences(LaunchActivity.this, "totalArry");
       // Utils.removeJsonSharedPreferences(LaunchActivity.this, "fire");
        Utils.posTo = 0;
//
//        Intent i = new Intent(LaunchActivity .this, HomeScreen.class);
//        ii.putExtra("nextLev", 0);
//        startActivity(i);
//
//
////
//        ((LinearLayout)findViewById(R.id.texttostart)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent ii = new Intent(LaunchActivity .this, Remarks.class);
//                ii.putExtra("nextLev", 0);
//                startActivity(ii);
//            }
//        });


//        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.in);
//        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rl);
//        Drawable dr = new BitmapDrawable(bitmap);
//        relativeLayout.setBackgroundDrawable(dr);
        // Set Thread to transition to a different activity.
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                // Intent to jump to the next activity
                Intent ii = new Intent(LaunchActivity.this, HomeScreen.class);
                ii.putExtra("nextLev", 0);
                startActivity(ii);
                finish();

            }
        };
        //2 seconds.
        handler.postDelayed(runnable, 2000L);
    }


    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);

    }

}
