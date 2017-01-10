package com.sd.epos.customeralpha.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sd.epos.customeralpha.Adapter.FLitermenuAdapter;
import com.sd.epos.customeralpha.R;
import com.sd.epos.customeralpha.common.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jabbir on 7/3/16.
 */
public class FliterActivity  extends Activity {
    private RecyclerView _fliterRecyclevie;
    private RecyclerView.LayoutManager mDListManager;
    private List<String> al;
    public static  ArrayList<String> tFLitr=new ArrayList<>();
    private FLitermenuAdapter _ftladapter;
    private com.gc.materialdesign.views.ButtonFlat txSUbmit,txCancel;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fliter_actvity);
        _fliterRecyclevie = (RecyclerView) findViewById(R.id.fltr);
        _fliterRecyclevie.setHasFixedSize(true);
        mDListManager = new GridLayoutManager(this, 5);
        _fliterRecyclevie.setLayoutManager(mDListManager);
        txSUbmit=(com.gc.materialdesign.views.ButtonFlat)findViewById(R.id.flt_sub);
        txCancel=(com.gc.materialdesign.views.ButtonFlat)findViewById(R.id.flt_canc);
        al = Utils.readList(FliterActivity.this, "Main_Cat");
        // yxy.setText(al.get(0).toString());
        _ftladapter = new FLitermenuAdapter(FliterActivity.this, al, 0);
        _fliterRecyclevie.setAdapter(_ftladapter);
        txSUbmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tFLitr = new ArrayList<String>();
                ArrayList<Integer> xc = new ArrayList<Integer>();
                xc = _ftladapter.mFilter();
                for (int i = 0; i < al.size(); i++) {
                    if (xc.contains(i)) {
                        tFLitr.add(al.get(i));
                    }

                }
                Log.d("tetttttt", "" + tFLitr);

                if (tFLitr.size() < 1) {
                    Toast.makeText(FliterActivity.this, "Please choose from the menu", Toast.LENGTH_SHORT).show();
                } else {
                    Utils.writeList(FliterActivity.this, tFLitr, "tFLitr");
                    overridePendingTransition(R.anim.nothing, R.anim.left_right);
                    Intent xz = new Intent(FliterActivity.this, HomeScreen.class);
                    xz.putExtra("nextLev", 3);
                    startActivity(xz);
                }


            }
        });
        ((TextView)findViewById(R.id.fltsel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mthSelctall();
            }
        });
        txCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.nothing, R.anim.left_right);
                finish();
            }
        });

    }
    private void mthSelctall(){
        if(((TextView)findViewById(R.id.fltsel)).getText().equals("Select All"))
        {
            ((TextView)findViewById(R.id.fltsel)).setText("UnSelect All");
            ((TextView)findViewById(R.id.fltsel)).setTextColor(getResources().getColor(R.color.red));
            _ftladapter = new FLitermenuAdapter(FliterActivity.this, al, 2);
            _fliterRecyclevie.setAdapter(_ftladapter);
        }
        else{
            _ftladapter = new FLitermenuAdapter(FliterActivity.this, al, 3);
            _fliterRecyclevie.setAdapter(_ftladapter);
            ((TextView)findViewById(R.id.fltsel)).setTextColor(getResources().getColor(R.color.brown));
            ((TextView)findViewById(R.id.fltsel)).setText("Select All");
        }

    }
}
