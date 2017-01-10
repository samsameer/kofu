package com.sd.epos.customeralpha.Activitys;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.sd.epos.customeralpha.Adapter.RemarkAdapter;
import com.sd.epos.customeralpha.Models.Data;
import com.sd.epos.customeralpha.R;
import com.sd.epos.customeralpha.common.Utils;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by jabbir on 1/7/15.
 */
public class Remarks extends Activity {
    private RecyclerView remRecycle;
    private RecyclerView.Adapter remAdapter;
    private RecyclerView.LayoutManager remLayoutManager;
    private static final String TAG = "Remarks";
    private List<Data> mRemrks;
    private ArrayList<String> sEemark = new ArrayList<String>();
    private HashMap<Integer, String> siemrk = new HashMap<Integer, String>();
    private ArrayList<String> sRemrk = new ArrayList<String>();
    private HashMap<String, ArrayList<String>> semrk = new HashMap<String, ArrayList<String>>();
    private int sizeRemark = 0;
    private JSONObject jsADD;
    private JSONObject jsObj;
    private TextView addTo;
    private Spinner remSpin;
    private ArrayAdapter<String> xcAdap;
    private JSONArray jssrrt;
    private int getPOs;
    private String cusPos = "";
    private EditText rEditetx;
    private int isMainreml = 3;
    private String cmpAdd = "";
    private RadioGroup rButtonGrp;
    private TextView newAdd, remvPersn;
    private HashMap<Integer, ArrayList<String>> inAdd = new HashMap<Integer, ArrayList<String>>();
    private ArrayList<String> inList = new ArrayList<String>();
    private TextView aNumberPicker;
    private String[] myStrings;
    private int[] selectedItem;
    private Bitmap bmp;
    private String xremrk = "";
    private String remrktt="";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remrkitem);
        Utils.removeJsonSharedPreferences(Remarks.this, "Remark");
        getWindow().setLayout(1800, 1200);
        remRecycle = (RecyclerView) findViewById(R.id.remk_recycle);
        remRecycle.setHasFixedSize(true);
        remLayoutManager = new GridLayoutManager(this, 4);
        remRecycle.setLayoutManager(remLayoutManager);
        remSpin = (Spinner) findViewById(R.id.splInr);
        newAdd = (TextView) findViewById(R.id.addnew);
        remvPersn = (TextView) findViewById(R.id.remv);
        remSpin.setEnabled(true);
        addTo = (TextView) findViewById(R.id.add);
        rEditetx = (EditText) findViewById(R.id.tt_edit);

        try{
            remrktt=(getIntent().getExtras().getString("itemid"));
        }
        catch (Exception e){
            //remrktt=Integer.parseInt(getIntent().getExtras().getString("itemid"));
        }

        String remrk = Utils.MAINTAG + Utils.ORDERRE + Utils.COMCODE + "01" + "&ai_itemid=" + remrktt;
       // String remrk = Utils.MAINTAG + "/kpos/getOrderRemarks?" + Utils.COMCODE + Utils.compnyCd;

        Log.d("posss", "" + getPOs);

        rButtonGrp = (RadioGroup) findViewById(R.id.rmkgour);
        aNumberPicker = (TextView) findViewById(R.id.vnum1);
//        aNumberPicker.setMaxValue(50);
//        aNumberPicker.setMinValue(1);
//        aNumberPicker.setValue(1);
//        aNumberPicker.setWrapSelectorWheel(false);

        isMainreml = getIntent().getExtras().getInt("isMain");
        Utils.removeJsonSharedPreferences(Remarks.this, "remarkker");
        xremrk = "";
        try {
            xremrk = getIntent().getStringExtra("maxri");
        } catch (Exception e) {

        }
        rEditetx.setText(xremrk);

        Log.d("URL", remrk);
        try {
            myStrings = getIntent().getStringArrayExtra("strings");
            ((TextView) findViewById(R.id.itm)).setText(myStrings[0]);
            ((TextView) findViewById(R.id.prct)).setText(myStrings[1]);
        } catch (Exception e) {

        }
//        if(isMainreml==2){
//
//        }

        for (int ix = 0; ix < Utils.getint(Remarks.this, "noCust"); ix++)
            redButton((Utils.getint(Remarks.this, "noCust") - 1), ix);


        //cHk(hourButtonLayout.getCheckedRadioButtonId());
        selectedItem = getIntent().getIntArrayExtra("data");

        if (isMainreml == 0)
            try {
                byte[] byteArray = getIntent().getByteArrayExtra("image");

                bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                BitmapDrawable ob = new BitmapDrawable(getResources(), bmp);
                ((ImageView) findViewById(R.id.vImg)).setVisibility(View.VISIBLE);
                ((ImageView) findViewById(R.id.vImg)).setBackground(ob);

                jsObj = Utils.loadJSONObject(Remarks.this, "remarksArry", "" + getPOs);
                if (!jsObj.equals("{}"))
                    Log.d(TAG + "Test", "" + jsObj);
                rEditetx.setText(jsObj.getString("remark_edit"));
                addTo.setVisibility(View.VISIBLE);
            } catch (JSONException js) {
            }
        else {

            try {
                ((ImageView) findViewById(R.id.vImg)).setVisibility(View.GONE);
                addTo.setVisibility(View.INVISIBLE);
                cusPos = getIntent().getStringExtra("cusPos");
                cmpAdd = getIntent().getStringExtra("cmpAdd");
                jsObj = Utils.loadJSONObject(Remarks.this, "ArrVariable", "" + cmpAdd + "_" + getPOs + "_" + cusPos);
                if (!jsObj.equals("{}"))
                    Log.d(TAG + "Test1", "" + jsObj);
                rEditetx.setText(jsObj.getString("remark_edit"));
            } catch (JSONException js) {
            }
        }

        if (!Utils.isConnected(Remarks.this)) {
//            try {
//                String json = "";
//                try {
//                    BufferedInputStream resourceStream = new BufferedInputStream(getResources().openRawResource(R.raw.get));
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(resourceStream));
//
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        json += line;
//                    }
//                    reader.close();
//                    resourceStream.close();
//                } catch (Exception ex) {
//                    Log.e("myApp", ex.getMessage());
//                }
//
//                JSONArray jsonobject = new JSONArray(json);
//                txt(jsonobject);
////
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        } else {
            Utils.get(remrk, null, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                    try {
                        txt(timeline);

                    } catch (Exception exception) {
                        Log.d(TAG + "2this error", "" + exception.toString());
                    }
                    System.out.println(timeline);
                }
            });
        }

        addTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methodTocall(rButtonGrp.getCheckedRadioButtonId());
            }

        });

        rButtonGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup rGroup, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton) rGroup.findViewById(checkedId);
                boolean isChecked = checkedRadioButton.isChecked();
                if (isChecked) {
                    getPOs = checkedRadioButton.getId();
                    xcAdap = new ArrayAdapter<String>(Remarks.this, android.R.layout.simple_spinner_dropdown_item, sEemark);
                    remSpin.setAdapter(xcAdap);
                    geString(remSpin.getSelectedItem().toString(), remSpin.getId(), getPOs);
                    aNumberPicker.setText("" + 1);
                    rEditetx.setText("");
                    ArrayList<String> yoT = new ArrayList<String>();
                    if (!inAdd.isEmpty()) {
                        if (inAdd.get(rButtonGrp.getCheckedRadioButtonId()) != null) {
                            yoT = inAdd.get(rButtonGrp.getCheckedRadioButtonId());
                            if (yoT.size() > 0) {
                                aNumberPicker.setText("" + 1);
                                rEditetx.setText(yoT.get(1));
                            }
                        }

                    }
                }
            }
        });
        newAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG + "s123456789", "" + (Utils.getint(Remarks.this, "noCust") + 1));
                Utils.saveint(Remarks.this, (Utils.getint(Remarks.this, "noCust") + 1), "noCust");
                rButtonGrp.removeAllViews();
                for (int ix = 0; ix < Utils.getint(Remarks.this, "noCust"); ix++) {
                    redButton((Utils.getint(Remarks.this, "noCust") - 1), ix);
                    rButtonGrp.check(getPOs);
                }


                ((HorizontalScrollView) findViewById(R.id.hsscrl)).postDelayed(new Runnable() {
                    public void run() {
                        ((HorizontalScrollView) findViewById(R.id.hsscrl)).fullScroll(HorizontalScrollView.FOCUS_RIGHT);

                    }
                }, 100L);

            }

        });
        remvPersn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG + "s123456789", "" + (Utils.getint(Remarks.this, "noCust") - 1));
                if (Utils.getint(Remarks.this, "noCust") > 1)
                    Utils.saveint(Remarks.this, (Utils.getint(Remarks.this, "noCust") - 1), "noCust");

                rButtonGrp.removeAllViews();
                for (int ix = 0; ix < Utils.getint(Remarks.this, "noCust"); ix++) {
                    redButton((Utils.getint(Remarks.this, "noCust") - 1), ix);
                    rButtonGrp.check(getPOs);
                }


                ((HorizontalScrollView) findViewById(R.id.hsscrl)).postDelayed(new Runnable() {
                    public void run() {
                        ((HorizontalScrollView) findViewById(R.id.hsscrl)).fullScroll(HorizontalScrollView.FOCUS_RIGHT);

                    }
                }, 100L);

            }

        });


        remSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                geString(remSpin.getSelectedItem().toString(), position, getPOs);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Your code here
            }
        });

        ((Button) findViewById(R.id.cnclttt)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { Utils.xTo ="";

                finish();
            }
        });

        ((Button) findViewById(R.id.addR)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Utils.xTo="";
                    jssrrt = new JSONArray();
                    JSONArray jsd = new JSONArray();
                    if (isMainreml > 0) {
                        methodTocall(rButtonGrp.getCheckedRadioButtonId());
                    }

                    if (isMainreml == 4) {
                        for (int i = 0; i < sizeRemark; i++) {
                            jssrrt.put(Utils.loadJSONObject(Remarks.this, "Remark", "" + i + "_" + 0));
                        }
                        String remark = rEditetx.getText().toString();
                        Log.i("inadd", "" + inAdd);//inAdd.get(y).get(1)
                        ArrayList<String> ySeq = new ArrayList<String>();
                        for (int j = 0; j < jssrrt.length(); j++) {
                            // jsADD.put("Remarks", jssrrt);
                            JSONObject ksw = jssrrt.getJSONObject(j);
                            Iterator<String> iter = ksw.keys();
                            while (iter.hasNext()) {
                                String key = iter.next();
                                Object vlue = ksw.get(key);
                                if (!vlue.toString().isEmpty())
                                    ySeq.add(vlue.toString());
                            }

                        }
                        Collections.sort(ySeq);
                        for (int we = 0; we < ySeq.size(); we++) {
                            remark += " " + ySeq.get(we).toString();
                            Log.d("Value", remark);
                        }
                        Utils.xTo = remark;
                        Utils.xpTo = 1;
                        finish();
                    }


                    for (int y = 0; y < Utils.getint(Remarks.this, "noCust"); y++) {


                        if (inAdd.get(y) != null) {
                            if (isMainreml == 0) {
                                jsADD = new JSONObject();
                                jsADD.put(Utils.mPar, selectedItem[0]);
                                jsADD.put(Utils.subPar, selectedItem[1]);
                                jsADD.put(Utils.qtyPar, inAdd.get(y).get(0).toString());
                                String remark = inAdd.get(y).get(1).toString();
                                for (int i = 0; i < sizeRemark; i++) {
                                    jssrrt.put(Utils.loadJSONObject(Remarks.this, "Remark", "" + i + "_" + y));
                                }
                                ArrayList<String> xSeq = new ArrayList<String>();
                                for (int j = 0; j < jssrrt.length(); j++) {
                                    // jsADD.put("Remarks", jssrrt);
                                    JSONObject ksw = jssrrt.getJSONObject(j);
                                    Iterator<String> iter = ksw.keys();
                                    while (iter.hasNext()) {
                                        String key = iter.next();
                                        Object vlue = ksw.get(key);
                                        if (!vlue.toString().isEmpty())
                                            xSeq.add(vlue.toString());
                                    }
                                }
                                Collections.sort(xSeq);
                                for (int we = 0; we < xSeq.size(); we++) {
                                    remark += " " + xSeq.get(we).toString();
                                    Log.d("Value", remark);
                                }
                                Log.d("Value3333333", remark);
                                jsADD.put("remark_edit", remark);
                                Log.d(TAG + "c", "" + jsADD);
                                jsd.put(jsADD);
                                if (Utils.loadJSONArray(Remarks.this, "totalArry", "" + y).equals("[]")) {
                                    Utils.saveJSONArray(Remarks.this, "totalArry", "" + y, jsd);
                                    Log.e("@@@@@@@@33", "" + Utils.loadJSONArray(Remarks.this, "totalArry", "" + y));
                                } else {
                                    JSONArray jsw = new JSONArray();
                                    jsw = Utils.loadJSONArray(Remarks.this, "totalArry", "" + y);
                                    jsw.put(jsADD);

                                    Utils.saveJSONArray(Remarks.this, "totalArry", "" + y, jsw);
                                    Log.e("@@@@@@@@test", "" + Utils.loadJSONArray(Remarks.this, "totalArry", "" + y));

                                }
                                try {
                                    //Utils.removeJsonSharedPreferences(Remarks.this, "mkeit");
                                    Utils.removeFromSharedPreferences(Remarks.this, "remarksArry", "" + 0);
                                    Utils.removeFromSharedPreferences(Remarks.this, "ArrVariable", "" + 0);
                                    Utils.removeFromSharedPreferences(Remarks.this, "variitem", "" + 1);
                                } catch (Exception e) {

                                }
                            } else {
                                for (int i = 0; i < sizeRemark; i++) {
                                    jssrrt.put(Utils.loadJSONObject(Remarks.this, "Remark", "" + i + "_" + y));
                                }
                                String remark = rEditetx.getText().toString();
                                Log.i("inadd", "" + inAdd);//inAdd.get(y).get(1)
                                ArrayList<String> ySeq = new ArrayList<String>();
                                for (int j = 0; j < jssrrt.length(); j++) {
                                    // jsADD.put("Remarks", jssrrt);
                                    JSONObject ksw = jssrrt.getJSONObject(j);
                                    Iterator<String> iter = ksw.keys();
                                    while (iter.hasNext()) {
                                        String key = iter.next();
                                        Object vlue = ksw.get(key);
                                        if (!vlue.toString().isEmpty())
                                            ySeq.add(vlue.toString());
                                    }

                                }
                                Collections.sort(ySeq);
                                for (int we = 0; we < ySeq.size(); we++) {
                                    remark += " " + ySeq.get(we).toString();
                                    Log.d("Value", remark);
                                }


                                if (isMainreml == 3) {
                                    Utils.savePString(Remarks.this, "rrrrTTR", remark, "rrrrTTR" + getIntent().getExtras().getInt("stringRemrk") + "1");
                                    Log.d("string name eeee", "" + Utils.getPString(getApplicationContext(), "rrrrTTR", "rrrrTTR" + getIntent().getExtras().getInt("stringRemrk") + "1"));
                                    Utils.xTo = remark;
                                    Utils.xpTo = 1;
                                    finish();
                                }

                                cusPos = getIntent().getStringExtra("cusPos");
                                Utils.savePString(Remarks.this, "RemarksVariab", remark, "" + cmpAdd + "_" + y + "_" + cusPos);
                                HashMap<String, ArrayList<String>> texts = new HashMap<String, ArrayList<String>>();
                                JSONObject jd = Utils.loadJSONObject(Remarks.this, "mkeit", cmpAdd + "_" + y);
                                Utils.savePString(Remarks.this, "rR", remark, "rrrr");
                                try {
                                    JSONArray js = jd.getJSONArray("" + cusPos);
                                    js.put(6, remark);
                                    jd.put("" + cusPos, js);
                                    Utils.saveJSONObject(Remarks.this, "mkeit", cmpAdd + "_" + y, jd);
                                    Log.d("jd3***********", "" + Utils.loadJSONObject(Remarks.this, "mkeit", cmpAdd + "_" + y));

                                } catch (Exception e) {

                                }


                            }


                        } else {
                            Toast.makeText(Remarks.this, "please Click 'Add ' for Customer " + (y + 1), Toast.LENGTH_SHORT);

                        }
                        finish();
                    }


                } catch (Exception js) {
                    js.printStackTrace();
                    Log.d(TAG + "6this error", "" + js.toString());
                }
            }
        });


    }

    private void redButton(int name, int ix) {

        RadioButton rdbtn = new RadioButton(this);
        rdbtn.setId(ix);
        rdbtn.setGravity(Gravity.CENTER | Gravity.LEFT);
        rdbtn.setTextColor(getResources().getColor(R.color.primary));
        rdbtn.setTextSize(20);
        rdbtn.setSingleLine(true);
        rdbtn.setPadding(20, 20, 20, 20);
        rdbtn.setCompoundDrawablePadding(10);
        rdbtn.setText("G " + (ix + 1));
        rdbtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.radioimg, 0, 0, 0);
        rdbtn.setButtonDrawable(new StateListDrawable());
        if (ix == name) {
            rdbtn.isChecked();
        }
        rButtonGrp.addView(rdbtn);


    }

    private void geString(String xcz, int Postion, int tPOs) {
        Data spx;
        Log.d(TAG + "9cccc", "" + Postion);
        mRemrks = new ArrayList<Data>();
        ArrayList<String> cxd = new ArrayList<String>();
        cxd = semrk.get(xcz);
        for (int x = 0; x < cxd.size(); x++) {
            spx = new Data();
            spx.setremarks(cxd.get(x).toString());
            mRemrks.add(spx);
        }
        remAdapter = new RemarkAdapter(Remarks.this, mRemrks, Postion, getPOs, isMainreml, cusPos);
        remRecycle.setAdapter(remAdapter);
    }


    private void methodTocall(int inx) {

        inList = new ArrayList<String>();
        inList.add(Integer.toString(1));
        inList.add(rEditetx.getText().toString());
        Log.i("FFFFFFF", "" + inList);
        inAdd.put(inx, inList);
        Log.i("FFinAddFF", "" + inAdd);

    }

    private void txt(JSONArray timeline) {
        try {
            JSONArray jsAr = timeline;
            sEemark = new ArrayList<String>();
            ArrayList<Integer> its = new ArrayList<Integer>();

            for (int z = 0; z < jsAr.length(); z++) {
                siemrk.put(Integer.parseInt(jsAr.getJSONObject(z).getString("seq_no")), jsAr.getJSONObject(z).getString("remarks_group"));
            }
            for (Map.Entry<Integer, String> entry : siemrk.entrySet()) {
                System.out.println("Item is:" + entry.getKey() + " with value:" +
                        entry.getValue());

                its.add(entry.getKey());
            }
            Set<Integer> hes = new HashSet<>();
            hes.addAll(its);
            its.clear();
            its.addAll(hes);
            for (int i = 0; i < its.size(); i++) {
                sEemark.add(siemrk.get(its.get(i)));
            }

           // List<String> al = new ArrayList<>();
// add elements to al, including duplicates
            Set<String> hs = new HashSet<>();
            hs.addAll(sEemark);
            sEemark.clear();
            sEemark.addAll(hs);
            Log.d("Smeeeeee",""+sEemark);

            sizeRemark = sEemark.size();
            Log.d("SsizeRemark",""+sizeRemark);
            for (int i = 0; i < sEemark.size(); i++) {
                // Utils.removeFromSharedPreferences(Remarks.this, "Remark", "" + i);
                sRemrk = new ArrayList<String>();
                for (int z = 0; z < jsAr.length(); z++) {

                    if (sEemark.get(i).equalsIgnoreCase(jsAr.getJSONObject(z).getString("remarks_group"))) {
                        sRemrk.add(jsAr.getJSONObject(z).getString("remarks"));
                    }

//                    if (i == 0) {
//                        sRemrk = new ArrayList<String>();
//                    } else {
//
//                    }

                }


                semrk.put(sEemark.get(i), sRemrk);
            }

//            String xyz = sEemark.get(1);
//            sEemark = new ArrayList<String>();
//            sEemark.add(xyz);

            if (isMainreml != 0) {

                rButtonGrp.removeAllViews();
                redButton(1, getIntent().getExtras().getInt("stringRemrk"));
                rButtonGrp.check(getIntent().getExtras().getInt("stringRemrk"));
                newAdd.setVisibility(View.GONE);
                remvPersn.setVisibility(View.GONE);


            } else {
                newAdd.setVisibility(View.VISIBLE);
                remvPersn.setVisibility(View.VISIBLE);
                rButtonGrp.check(0);
            }


            getPOs = rButtonGrp.getCheckedRadioButtonId();
            xcAdap = new ArrayAdapter<String>(Remarks.this, android.R.layout.simple_spinner_dropdown_item, sEemark);
            remSpin.setAdapter(xcAdap);
            geString(remSpin.getSelectedItem().toString(), remSpin.getId(), getPOs);
        } catch (Exception e) {

        }

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
        super.onBackPressed();
        overridePendingTransition(R.anim.nothing, R.anim.fade_in);
    }

}