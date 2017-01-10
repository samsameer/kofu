package com.sd.epos.customeralpha.Activitys;

import android.animation.ObjectAnimator;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Path;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.samsung.android.sdk.richnotification.SrnRichNotificationManager;
import com.sd.epos.customeralpha.Adapter.GridAdapter;
import com.sd.epos.customeralpha.Adapter.ListmenuAdapter;
import com.sd.epos.customeralpha.R;
import com.sd.epos.customeralpha.common.RecyclerItemClickListener;
import com.sd.epos.customeralpha.common.Utils;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jabbir on 16/9/15.
 */
public class HomeScreen extends HomeBaseActivity {
    //public Carousel carousel;
    private List<String> al;
    private List<String> mk_al;
    private int[] dataI = {R.id.viewo, R.id.prom, R.id.feedb, R.id.relod, R.id.home, R.id.tableni, R.id.servic, R.id.fsubmit, R.id.submitfir};
    private ArrayAdapter<String> xcAdap;
    private int xMain = 0;
    private int subPo = 0;
    private String xs;
    private EditText xx;
    private Button tstBtn;
    private int[] txr = {R.id.fab_event, R.id.fab_others};
    private LinearLayoutManager layoutManager;
    private String sUsername, sPassrname;
    private String[] sConfig = new String[7];
    //static TextView yxy;
    private int chkFull = 0;
    private String sVie = "";
    private ImageView _flitr;
    private ViewPager mViewPager;
    int page = 1;
    private int dfrm = 0;
    private HomeKeyLocker mHomeKeyLocker;
    private SrnRichNotificationManager mRichNotificationManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_homescreen);
        Utils.sNo = 0;
        //  mTextvie = (TextView) findViewById(R.id.fsubmit);
        mRichNotificationManager = new SrnRichNotificationManager(getApplicationContext());
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        //yxy = (TextView) findViewById(R.id.txt_maintaf);
        mListView = (RecyclerView) findViewById(R.id.listMe);
        mListView.setHasFixedSize(true);
        mDListRec = (RecyclerView) findViewById(R.id.myview);
        mDListRec.setHasFixedSize(true);
        _flitr = (ImageView) findViewById(R.id._imfltr);
        mDListManager = new GridLayoutManager(this, 1);
        mDListRec.setLayoutManager(mDListManager);
        mListManager = new GridLayoutManager(this, 1);
        dfrm = getIntent().getExtras().getInt("nextLev");

        Utils.removeJsonSharedPreferences(context, "fire");
        layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mListView.setLayoutManager(mListManager);
        mLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //screenOrent();
//        CustomPagerAdapter mCustomPagerAdapter = new CustomPagerAdapter(this);
        countNum = (TextView) findViewById(R.id.rda);
//        mViewPager = (ViewPager) findViewById(R.id.log);
//
//        mViewPager.setAdapter(mCustomPagerAdapter);
//        pageSwitcher(2);
//        ((com.gc.materialdesign.views.ButtonFlat) findViewById(R.id.selectall)).setVisibility(View.INVISIBLE);
//        ((com.gc.materialdesign.views.ButtonFlat) findViewById(R.id.clr)).setVisibility(View.INVISIBLE);
//        //screenOrent();
        if ((Utils.getint(HomeScreen.this, "ser_dilogshow")) != 1) {
            Utils.posTo = 0;
            startActivity(new Intent(HomeScreen.this, MainActivity.class));
        }
        ((TextView) findViewById(R.id.sl)).setVisibility(View.INVISIBLE);
        ((TextView) findViewById(R.id.frt)).setVisibility(View.INVISIBLE);
        //checkVlue(0);
        mHomeKeyLocker = new HomeKeyLocker();
        if (Utils.loadArray("serverName", HomeScreen.this).length == 7) {
            sConfig = Utils.loadArray("serverName", HomeScreen.this);
            Utils.userAuth = sConfig[2];
            Utils.passAuth = sConfig[3];
            Utils.MAINTAG = sConfig[0] + ":" + sConfig[1];
            Utils.compnyCd = sConfig[5];
            Utils.posTo = 0;

        } else {
            Utils.userAuth = null;
            Utils.passAuth = null;
            Intent intX = new Intent(HomeScreen.this, MainActivity.class);
            startActivity(intX);
            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
        }
        mk_al = Utils.readList(HomeScreen.this, "Main_Cat");
        al = Utils.readList(HomeScreen.this, "tFLitr");
        if (al.size() == 0)
            al = Utils.readList(HomeScreen.this, "Main_Cat");
        else if (dfrm == 3) {
            animate(1);
            txMng(al.get(0));
            mAdapter = new GridAdapter(HomeScreen.this, Utils.TtlItem.get(al.get(0)), xMain);
            mRecyclerView.setAdapter(mAdapter);
        }
        try {
            txMng(al.get(0));

//
//
            for (int i = 0; i < txr.length; i++) {

                FloatingActionButton button = (FloatingActionButton) findViewById(txr[i]);
                button.setOnClickListener(mSendListener);
            }
        } catch (Exception e) {

        }
//        ((com.gc.materialdesign.views.ButtonFlat) findViewById(R.id.selectall)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mthdSelectAll();
//
//            }
//
//
//        });
//        ((com.gc.materialdesign.views.ButtonFlat) findViewById(R.id.clr)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                firtokitchen();
//
//            }
//
//
//        });


//        if(Utils.getString(HomeScreen.this, "Tableno")==null)
//            ((TextView) findViewById(dataI[5])).setVisibility(View.INVISIBLE);

        // yxy.setText(al.get(0).toString());


//        if (dfrm == 3) {
//            mAdapter = new GridAdapter(HomeScreen.this, Utils.TtlItem.get(al.get(0)), 0);
//            mRecyclerView.setAdapter(mAdapter);
//        }


        mListAdapter = new ListmenuAdapter(HomeScreen.this, al, 0);
        mListView.setAdapter(mListAdapter);

        mListView.setItemAnimator(new DefaultItemAnimator());
        mListView.addOnItemTouchListener(
                new RecyclerItemClickListener(HomeScreen.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        mListAdapter = new ListmenuAdapter(HomeScreen.this, al, position);
                        mListView.setAdapter(mListAdapter);
                        mListManager.scrollToPosition(position);
                        //mListManager.scrollToPositionWithOffset(position, 0);
                        sVie = al.get(position).toString();
                        //yxy.setText(sVie);
                        if (Utils.TtlItem.get(al.get(position)) != null) {

                            xMain = position;

                            if (mRecyclerView.getVisibility() != View.VISIBLE) {
                                mkChmage();
                            }
                            if (Utils.TtlItem.get(al.get(position)) != null) {

                                txMng(al.get(position));

                                mAdapter = new GridAdapter(HomeScreen.this, Utils.TtlItem.get(al.get(position)), xMain);

                                mRecyclerView.setAdapter(mAdapter);
                                Utils.txt(HomeScreen.this, mRecyclerView);
                                //Utils.txt(HomeScreen.this,mRecyclerView);
                            } else {
                                new gridViewLayout().execute();

                            }
                        }


                    }
                }

                )
        );
        if (getIntent().getExtras().getInt("nextLev") == 2) {
            if (Utils.TtlItem.get(al.get(0)) != null) {
                txMng(al.get(0));
                mAdapter = new GridAdapter(HomeScreen.this, Utils.TtlItem.get(al.get(0)), xMain);
                mRecyclerView.setAdapter(mAdapter);
                // Utils.txt(HomeScreen.this,mRecyclerView);
            }
        }
        ((TextView) findViewById(R.id.home)).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


//                if (mHomeKeyLocker == null) {
//
//                    mHomeKeyLocker = new HomeKeyLocker();
//                    mHomeKeyLocker.lock(HomeScreen.this);
//
//
//                    Toast.makeText(HomeScreen.this, "Locked", Toast.LENGTH_SHORT).show();
//                }
//            else {
//                    Toast.makeText(HomeScreen.this, "Unlocked", Toast.LENGTH_SHORT).show();
//                    mHomeKeyLocker.unlock();
//                    mHomeKeyLocker=null;
//                }


                return false;
            }
        });

        ((TextView) findViewById(R.id.sl)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mthdSelectAll();

            }


        });
        ((TextView) findViewById(R.id.frt)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firtokitchen();

            }


        });
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(HomeScreen.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // tstBtn = ((Button) view.findViewById(R.id.addiy));
                        if (((TextView) view.findViewById(R.id.txto)).getVisibility() == View.VISIBLE) {
                            xs = ((TextView) view.findViewById(R.id.txto)).getText().toString();

                            int mnxd = 0;
                            new Thread(new Runnable() {
                                public void run() {
                                    mListView.post(new Runnable() {
                                        public void run() {
                                            Log.d("xd", xs);
                                            int uiX = 0;

                                            for (int u = 0; u < al.size(); u++) {
                                                Log.d("al", al.get(u).toString());
                                                if (xs.equalsIgnoreCase(al.get(u).toString())) {


                                                    mListAdapter = new ListmenuAdapter(HomeScreen.this, al, u);
                                                    mListView.setAdapter(mListAdapter);
                                                    // layoutManager.scrollToPosition(u);
                                                    if (Utils.TtlItem.get(al.get(u)) != null)
                                                        mAdapter = new GridAdapter(HomeScreen.this, Utils.TtlItem.get(al.get(u)), xMain);

                                                    txMng(xs);

                                                    mRecyclerView.setAdapter(mAdapter);
                                                    Utils.txt(HomeScreen.this, mRecyclerView);

                                                    break;
                                                }

                                            }

                                        }
                                    });
                                }
                            }).start();
                        }
                        xx = ((EditText) view.findViewById(R.id.qty));
                        subPo = position;


                        final Button xyBtn = ((Button) view.findViewById(R.id.addiy));
                        xyBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //Log.i("getcount", xx);
                                xyBtn.setBackground(getResources().getDrawable(R.drawable.r_corner_red));
                                xyBtn.setTextColor(getResources().getColor(R.color.white));
                                xyBtn.setText("Added!");
                                new CountDownTimer(1300, 1300) {
                                    public void onTick(long ms) {
                                    }

                                    public void onFinish() {
                                        ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1, ScaleAnimation.RELATIVE_TO_SELF, .5f, ScaleAnimation.RELATIVE_TO_SELF, .5f);
                                        scale.setDuration(500);
                                        scale.setInterpolator(new OvershootInterpolator());
                                        xyBtn.setTextColor(getResources().getColor(R.color.primary));
//  xyBtn.setTextColor(getResources().getColor(R.color.white));
                                        xyBtn.setBackground(getResources().getDrawable(R.drawable.rounded_corner));
                                        xyBtn.setText("ADD");
                                    }
                                }.start();

                                try {
                                    JSONObject js = new JSONObject();
                                    js.put(Utils.mPar, "" + xMain);
                                    js.put(Utils.subPar, "" + subPo);
                                    js.put(Utils.qtyPar, "" + xx.getText().toString());
                                    js.put(Utils.remPar, "");
                                    addJsn(js);
                                    xx.setText("" + Utils.cusOn);
                                } catch (Exception e) {

                                }



                            }
                        });


                    }
                }

                )
        );
        countNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.removeJsonSharedPreferences(context, "rR");
                mkChmage();
                countNum.setText(Integer.toString(Utils.posTo));
            }
        });

        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.viewo:
                        Utils.removeJsonSharedPreferences(context, "rR");
                        Utils.removeJsonSharedPreferences(context, "fire");
                        mkChmage();
                        countNum.setText(Integer.toString(Utils.posTo));
                        break;
                    case R.id.prom:
                        Intent irt = new Intent(HomeScreen.this, FeedbackActivity.class);
                        startActivity(irt);

                        // relaseMemry();
//                        Dialog dialog = new Dialog(HomeScreen.this, "Promotions", getResources().getString(R.string.prom));
//                        dialog.show();
//                        ButtonFlat acceptButton = dialog.getButtonAccept();
//                        acceptButton.setText("Thank You");
                        // acceptButton.setTextColor(context.getResources().getColor(R.color.gold));
                        break;
                    case R.id.feedb:
                        mthWaiter();

                        break;
                    case R.id.relod:

                        chkFull = 10;
                        adminLogin();


                        break;
                    case R.id.home:
                        chkFull = 4;
                        adminLogin();


                        break;
                    case R.id.tableni:

                        if (!((TextView) findViewById(dataI[2])).getText().equals("Customer")) {
                            Intent ir = new Intent(HomeScreen.this, TableSelection.class);
                            ir.putExtra("tbl", 2);
                            startActivity(ir);
                        } else {
                            chkFull = 1;
                            adminLogin();


                        }
                        break;
                    case R.id.servic:
                        dilog(HomeScreen.this);
                        break;

                    case R.id._imfltr:

                        chkFull = 3;
                        adminLogin();


                        break;
                    case R.id.fsubmit:
                        btnFirstClick(0);
//                        if (Utils.isConnected(HomeScreen.this))
//                            btnFirstClick();
//                        eslse
//                           // btnFirstClick();
//                            Utils.alertDialogShow(HomeScreen.this, "please check that the connection to the network is active and try again.", "Network Error", 4);

                        break;
                    case R.id.submitfir:
                        btnFirstClick(2);
                        break;


                }
            }
        };
        for (int i = 0; i < dataI.length; i++) {

            //Utils.txt(HomeScreen.this, ((TextView) findViewById(dataI[i])));
            ((TextView) findViewById(dataI[i])).setOnClickListener(listener);
        }


        _flitr.setOnClickListener(listener);

    }


    @Override
    protected void onRestart() {
        //countNum.setText(Integer.toString( Utils.posTo));
        Log.d("came here check ", "" + Utils.posTo);
        if (mRecyclerView.getVisibility() != View.VISIBLE) {
            //  ((FrameLayout) findViewById(R.id.txct_frm)).setVisibility(View.GONE);
            //yxy.setText("VIEW ORDER  ");
            mRecyclerView.setVisibility(View.GONE);
            mDListRec.setVisibility(View.VISIBLE);
            ((TextView) findViewById(dataI[0])).setText("Main Menu");
            countNum.setVisibility(View.INVISIBLE);
            ((TextView) findViewById(R.id.fsubmit)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.submitfir)).setVisibility(View.VISIBLE);
            Intialick();
        }


        super.onRestart();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // do what you want to be done on home button click event
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();

        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);

        activityManager.moveTaskToFront(getTaskId(), 0);


    }

    private void txMng(String dsxd) {
        for (int i = 0; i < mk_al.size(); i++) {
            if (mk_al.get(i).toString().equalsIgnoreCase(dsxd)) {
                xMain = i;
                break;
            }

        }
    }
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//
//        // Checks the orientation of the screen for landscape and portrait
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//
//           // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//            mListManager = new GridLayoutManager(this, 1);
//            mListView.setLayoutManager(mListManager);
//            mLayoutManager = new GridLayoutManager(this, 3);
//            mRecyclerView.setLayoutManager(mLayoutManager);
//            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
//        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
//            //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//            mListManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//            mListView.setLayoutManager(mListManager);
//            mLayoutManager = new GridLayoutManager(this, 2);
//            mRecyclerView.setLayoutManager(mLayoutManager);
//            Log.d("my orient", "ORIENTATION_PORTRAIT");
//            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
//        }
//    }


//    public void onAttachedToWindow() {
//        this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
//        super.onAttachedToWindow();
//    }

    @Override
    protected void onResume() {
        moveit(countNum);
        countNum.setText(Integer.toString(Utils.posTo));
        Log.d("came here cresume ", "" + Utils.posTo);

        //mHomeKeyLocker.lock(this);

        ((TextView) findViewById(dataI[5])).setText("Table No " + Utils.getString(HomeScreen.this, "Tableno"));
///
        int xVar = Utils.getint(HomeScreen.this, "ccccints");
        if (xVar == 0)
            xVar = 1;

        ((TextView) findViewById(dataI[6])).setText("No Of Guest:" + xVar);


        if (Utils.xpTo != -1) {
            Utils.xpTo = -1;
            mDListAdapter.mthod();

        }

//        mRichNotificationManager.start();
//        mRichNotificationManager.registerRichNotificationListener(HomeScreen.this);
        //screenOrent();
        super.onResume();
    }

    private void animate(int xzs) {
        try {
            Utils.posTo = Utils.loadJSONArray(HomeScreen.this, "totalArry", "" + 1).length();
        } catch (Exception e) {

        }

        // Utils.posTo += xzs;
        moveit(countNum);
        countNum.setText(Integer.toString(Utils.posTo));
    }

    private void addJsn(JSONObject jADD) {
        try {
            if (Utils.loadJSONArray(HomeScreen.this, "totalArry", "" + 1).equals("[]")) {
                JSONArray jsd = new JSONArray();
                jsd.put(jADD);
                Utils.saveJSONArray(HomeScreen.this, "totalArry", "" + 1, jsd);
                Log.d("@@@@@@@@33", "" + Utils.loadJSONArray(HomeScreen.this, "totalArry", "" + 1));
            } else {
                JSONArray jsw = new JSONArray();
                jsw = Utils.loadJSONArray(HomeScreen.this, "totalArry", "" + 1);
                Log.d("@@@@@@@@33", "" + Utils.loadJSONArray(HomeScreen.this, "totalArry", "" + 1));
                int xxxT = 0;
                for (int i = 0; i < jsw.length(); i++) {
                    //if (js.toString().contains("\"item_id\":" + Integer.parseInt(eSoon[0].toString()) ))
                    if ((jsw.getJSONObject(i).getString("main").equalsIgnoreCase(jADD.getString("main"))) && jsw.getJSONObject(i).getString("subcat").equalsIgnoreCase(jADD.getString("subcat"))) {
                        xxxT = 1;
                        int cx = (Integer.parseInt(jsw.getJSONObject(i).getString("qty")) + Integer.parseInt(jADD.getString("qty")));
                        jsw.getJSONObject(i).put("qty", "" + cx);
                        Utils.saveJSONArray(HomeScreen.this, "totalArry", "" + 1, jsw);
                        break;
                        //[{"main":"3","subcat":"0","qty":"1","remark":""}]
                    }
                }
                if (xxxT == 0) {
                    Log.d("@@@@@@@@33iiiii", "" + jADD);
                    jsw.put(jADD);
                    Utils.saveJSONArray(HomeScreen.this, "totalArry", "" + 1, jsw);
                }


                Log.d("@@@@@@@@test", "" + Utils.loadJSONArray(HomeScreen.this, "totalArry", "" + 1));

            }
            animate(1);
        } catch (Exception e) {

        }
    }

    public void mkChmage() {
        if (mRecyclerView.getVisibility() == View.VISIBLE) {
            //  ((FrameLayout) findViewById(R.id.txct_frm)).setVisibility(View.GONE);
            //yxy.setText("VIEW ORDER  ");
            mRecyclerView.setVisibility(View.GONE);
            mDListRec.setVisibility(View.VISIBLE);
            ((TextView) findViewById(dataI[0])).setText("Main Menu");
            countNum.setVisibility(View.INVISIBLE);
            ((TextView) findViewById(R.id.fsubmit)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.submitfir)).setVisibility(View.VISIBLE);
            Intialick();
        } else {


            ((TextView) findViewById(R.id.sl)).setVisibility(View.INVISIBLE);
            ((TextView) findViewById(R.id.frt)).setVisibility(View.INVISIBLE);
            ((TextView) findViewById(R.id.submitfir)).setVisibility(View.INVISIBLE);
            countNum.setVisibility(View.VISIBLE);
            ((TextView) findViewById(dataI[0])).setText("View Order");
            mRecyclerView.setVisibility(View.VISIBLE);
            mDListRec.setVisibility(View.GONE);
            ((TextView) findViewById(R.id.fsubmit)).setVisibility(View.INVISIBLE);
//            mAdapter = new GridAdapter(HomeScreen.this, Utils.TtlItem.get(u), u);
//          xMain = u;
//            mRecyclerView.setAdapter(mAdapter);

        }


    }


    private void mthWaiter() {
        if (((TextView) findViewById(dataI[2])).getText().equals("Customer")) {
            ((TextView) findViewById(dataI[2])).setText("Waiter");
            ((TextView) findViewById(dataI[2])).setTextColor(getResources().getColor(R.color.gold));
            ((TextView) findViewById(dataI[2])).setCompoundDrawablesWithIntrinsicBounds(R.drawable.coffe, 0, 0, 0);

        } else {
            ((TextView) findViewById(dataI[2])).setText("Customer");
            ((TextView) findViewById(dataI[2])).setTextColor(getResources().getColor(R.color.white));
            ((TextView) findViewById(dataI[2])).setCompoundDrawablesWithIntrinsicBounds(R.drawable.c_on, 0, 0, 0);

        }


    }

//    private void performExample(IExample example) {
//        UUID uuid = mRichNotificationManager.notify(example.createRichNoti());
//
////        Toast.makeText(HomeScreen.this, "Notification Id : " + uuid,
////                Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onError(UUID arg0, SrnRichNotificationManager.ErrorType arg1) {
//        // TODO Auto-generated method stub
////        Toast.makeText(getApplicationContext(),
////                "Something wrong with uuid" + arg0.toString() + "Error:" + arg1.toString(),
////                Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    public void onRead(UUID arg0) {
//        // TODO Auto-generated method stub
////        Toast.makeText(getApplicationContext(), "Read uuid" + arg0.toString(), Toast.LENGTH_LONG)
////                .show();
//
//    }
//
//    @Override
//    public void onRemoved(UUID arg0) {
//        // TODO Auto-generated method stub
////        Toast.makeText(getApplicationContext(), "Removed uuid" + arg0.toString(), Toast.LENGTH_LONG)
////                .show();
//
//    }

    private void moveit(final View view) {

        float x = view.getX();
        float y = view.getY();
        Path path = new Path();

        path.moveTo(x + 0, y + 0);
        path.lineTo(x + 100, y + 150);
        path.lineTo(x + 400, y + 150);
        path.lineTo(0, 0);
        ObjectAnimator objectAnimator =
                ObjectAnimator.ofFloat(view, View.X,
                        View.Y, path);
        objectAnimator.setDuration(500);
        objectAnimator.start();
    }


    private void adminLogin() {

        final android.app.Dialog alertDialog = new android.app.Dialog(HomeScreen.this);
        alertDialog.setContentView(R.layout.admin);
        alertDialog.setTitle("");
        alertDialog.setCanceledOnTouchOutside(false);

        final EditText user = (EditText) alertDialog.findViewById(R.id.us1);
        final EditText pass = (EditText) alertDialog.findViewById(R.id.pass2);
        checkVlue(1);


//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);


        com.gc.materialdesign.views.ButtonFlat dialogButton = (com.gc.materialdesign.views.ButtonFlat) alertDialog.findViewById(R.id.cdilo);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkVlue(0);
                alertDialog.dismiss();
            }
        });

        com.gc.materialdesign.views.ButtonFlat dialogButton1 = (com.gc.materialdesign.views.ButtonFlat) alertDialog.findViewById(R.id.sdilog);
        // if button is clicked, close the custom dialog
        dialogButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!Utils.isConnected(HomeScreen.this)) {

                    Utils.USER = sUsername;
                    if (chkFull == 1) {
                        Intent ir = new Intent(HomeScreen.this, TableSelection.class);
                        ir.putExtra("tbl", 2);
                        startActivity(ir);
                    } else if (chkFull == 2)
                        startActivity(new Intent(HomeScreen.this, MainActivity.class));
                    else if (chkFull == 10)
                        startActivity(new Intent(HomeScreen.this, MainActivity.class));
                    else if (chkFull == 4) {
                        alertT();
                    } else if (chkFull == 3) {
                        startActivity(new Intent(HomeScreen.this, FliterActivity.class));
                        overridePendingTransition(R.anim.left_right, R.anim.right_left);
                    } else
                        alertT();


                    //  Utils.alertDialogShow(HomeScreen.this, getString(R.string.no_network), getString(R.string.ttl_network), 0);
                } else {
                    sUsername = user.getText().toString();
                    sPassrname = pass.getText().toString();
                    if (sUsername.matches("") || sPassrname.matches("")) {
                        Toast.makeText(HomeScreen.this, "Please enter missing fields.", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        if (chkFull == 10)
                            startActivity(new Intent(HomeScreen.this, MainActivity.class));
                        else {
                            String urlLo = Utils.MAINTAG + Utils.GETMain + Utils.compnyCd + "&userid=" + sUsername + "&password=" + sPassrname;
                            Utils.get(urlLo, null, new JsonHttpResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                                    // Pull out the first event on the public timeline
                                    try {
                                        JSONArray jAr = timeline;
                                        if (jAr.getJSONObject(0).getString("retval") == "0") {
                                            Utils.USER = sUsername;
                                            if (chkFull == 1) {
                                                Intent ir = new Intent(HomeScreen.this, TableSelection.class);
                                                ir.putExtra("tbl", 2);
                                                startActivity(ir);
                                            } else if (chkFull == 2)
                                                startActivity(new Intent(HomeScreen.this, MainActivity.class));
                                            else if (chkFull == 3) {
                                                startActivity(new Intent(HomeScreen.this, FliterActivity.class));
                                                overridePendingTransition(R.anim.left_right, R.anim.right_left);
                                            } else if (chkFull == 4) {
                                                alertT();
                                            } else
                                                alertT();
                                        } else {
                                            Utils.alertDialogShow(HomeScreen.this, getString(R.string.auth), getString(R.string.aut_alert), 0);

                                        }
                                    } catch (JSONException exception) {
                                        Log.d("this error", "" + exception);
                                    }
                                    System.out.println(timeline);
                                }

                            });
                        }

                    }


                }

                alertDialog.dismiss();
            }
        });

        alertDialog.show();

//
//
//        alertDialog.setMessage(message);
//        int x = xyz;
//        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//
//                alertDialog.dismiss();
//            }
//        });

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    private void screenOrent() {
        int ot = getResources().getConfiguration().orientation;
        switch (ot) {

            case Configuration.ORIENTATION_LANDSCAPE:

                Log.d("my orient", "ORIENTATION_LANDSCAPE");
                break;
            case Configuration.ORIENTATION_PORTRAIT:

                break;


            default:
                Log.d("my orient", "default val");
                break;
        }

    }

    private void alertT() {
        AlertDialog.Builder alertDel = new AlertDialog.Builder(HomeScreen.this);
        alertDel.setMessage("                    Are you sure you want to Cancel your Order ?                                               ");
        alertDel.setTitle("CANCEL ORDER ");
        alertDel.setPositiveButton("         Yes              ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Utils.removeJsonSharedPreferences(HomeScreen.this, "totalArry");
                Utils.removeJsonSharedPreferences(HomeScreen.this, "SAVED");
                Utils.removeJsonSharedPreferences(HomeScreen.this, "fire");
//                Intent i = new Intent(HomeScreen.this, LaunchActivity.class);
//                startActivity(i);
////                finish();
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory(Intent.CATEGORY_HOME);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });

        alertDel.setNegativeButton("NO                          ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });

        alertDel.show();


    }


    private void dilog(final Context mContext) {
        RelativeLayout linearLayout = new RelativeLayout(mContext);
        final NumberPicker aNumberPicker = new NumberPicker(mContext);
        aNumberPicker.setMaxValue(30);
        aNumberPicker.setMinValue(1);
        aNumberPicker.setValue(Utils.getint(HomeScreen.this, "ccccints"));


        // Hide soft keyboard on NumberPickers by overwriting the OnFocusChangeListener

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(50, 50);

        RelativeLayout.LayoutParams numPicerParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        linearLayout.setLayoutParams(params);
        linearLayout.addView(aNumberPicker, numPicerParams);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle("Choose Number of Guest");
        alertDialogBuilder.setView(linearLayout);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                Log.e("", "New Quantity Value : " + aNumberPicker.getValue());
                                if (aNumberPicker.getValue() < 1) {
                                    Utils.saveint(mContext, aNumberPicker.getValue(), "ccccints");
                                    ((TextView) findViewById(dataI[6])).setText("No Of Guest: 1");
                                } else {
                                    Utils.saveint(mContext, aNumberPicker.getValue(), "ccccints");
                                    ((TextView) findViewById(dataI[6])).setText("No Of Guest: " + aNumberPicker.getValue());
                                }


                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void textCont() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeScreen.this);
        LayoutInflater inflater = (HomeScreen.this).getLayoutInflater();
        builder.setTitle("");
        builder.setCancelable(false);
        builder.setView(inflater.inflate(R.layout.intial_choose, null));
//            // Add action buttons
//            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int id) {
//
//                }
//            }

        builder.create();
        builder.show();

    }

    public void pageSwitcher(int seconds) {
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

    private void relaseMemry() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeScreen.this);
        alertDialog.setTitle("Subscribe to Our Mailing List ");
        alertDialog.setMessage("Please provide your valid email id. ");

        final EditText input = new EditText(HomeScreen.this);
        input.setSingleLine(true);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);


        alertDialog.setPositiveButton("Subscribe",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String password = input.getText().toString();

                        if (Utils.EMAIL_ADDRESS_PATTERN.matcher(password).matches()) {

                            Toast.makeText(getApplicationContext(),
                                    "Thank you for Subscribe to Our Mailing List", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Invalid Email!", Toast.LENGTH_SHORT).show();

                            return;
                        }
                    }
                });

//        alertDialog.setNegativeButton("NO",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });

        alertDialog.show();
    }


    private View.OnClickListener mSendListener = new View.OnClickListener() {
        String[] manualMsgs = {"manual message 1", "manual mess 2", "manual mess 3", "manual mess 4"};

        @Override
        public void onClick(View v) {
            String xDts = "";
            int id = v.getId();
            switch (id) {
                case R.id.fab_event:
                    //case R.id.fab_photo:
                case R.id.fab_others:
                    FloatingActionButton button = (FloatingActionButton) findViewById(id);
                    xDts = button.getTitle().toString();
                    // performExample(new EventExample(HomeScreen.this, xDts+"   "+ ((TextView) findViewById(dataI[5])).getText()));
                    ((FloatingActionsMenu) findViewById(R.id.fab_menu)).collapse();
                    Toast.makeText(getApplicationContext(), "please wait will assist you shortly.",
                            Toast.LENGTH_SHORT).show();
                    break;


            }


        }
    };

    private void checkVlue(int isChecked) {
        if (isChecked == 0) {

            //mHomeKeyLocker.lock(this);
        } else {
            //mHomeKeyLocker.unlock();
        }
    }

}
