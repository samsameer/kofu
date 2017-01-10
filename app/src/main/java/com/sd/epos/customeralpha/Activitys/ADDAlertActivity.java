package com.sd.epos.customeralpha.Activitys;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.sd.epos.customeralpha.Adapter.AddREmAdapter;
import com.sd.epos.customeralpha.Models.Data;
import com.sd.epos.customeralpha.R;
import com.sd.epos.customeralpha.common.Utils;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jabbir on 13/1/16.
 */
public class ADDAlertActivity extends Activity {
    private String[] myStrings;
    private Bitmap bmp;
    private EditText _lqty;
    private TextView bck_Button, sub_mt;
    private com.sd.epos.customeralpha.common.CoolPriceView txtPric;
    private int[] selectedItem;
    private ArrayList<String> sRemrk = new ArrayList<String>();
    private RecyclerView _newremRec;
    private List<Data> mRemrks;
    private AddREmAdapter _newremAdapter;
    private TextView _tcdRe;
    private RecyclerView.LayoutManager _newremLayoutManager;
    private String xcSe = "NaN";
    //
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intial_choose);
        //getWindow().setLayout(750, 500);
        WindowManager.LayoutParams wmlp = getWindow().getAttributes();
        wmlp.gravity = Gravity.BOTTOM;
        wmlp.y = 200;

        _newremRec = (RecyclerView) findViewById(R.id.newremrk);
        _newremRec.setHasFixedSize(true);
        _newremLayoutManager = new GridLayoutManager(this, 2);
        _newremRec.setLayoutManager(_newremLayoutManager);

        _tcdRe = (TextView) findViewById(R.id.tcdRe);
        _lqty = (EditText) findViewById(R.id.l_qty);
        bck_Button = (TextView) findViewById(R.id._cnlbtn);
        sub_mt = (TextView) findViewById(R.id._addbtn);
        txtPric = (com.sd.epos.customeralpha.common.CoolPriceView) findViewById(R.id.frstpric);

        String remrk = Utils.MAINTAG + Utils.ORDERRE + Utils.COMCODE + "01" + "&ai_itemid=" + getIntent().getStringExtra("itemid");

        //mthdRemrk(remrk);


        try {
            selectedItem = getIntent().getIntArrayExtra("data");
            myStrings = getIntent().getStringArrayExtra("strings");
            ((TextView) findViewById(R.id.frstName)).setText(myStrings[0]);
            txtPric.setPrice(Double.parseDouble(myStrings[1]));
            txtPric.setStyle(24.0f, 20.0f, 18.0f, getResources().getColor(R.color.text_secondary), getResources().getColor(R.color.text_secondary), getResources().getColor(R.color.text_secondary));
            byte[] byteArray = getIntent().getByteArrayExtra("image");
            bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            BitmapDrawable ob = new BitmapDrawable(getResources(), bmp);
            // Utils.txt(ADDAlertActivity.this, ((ImageView) findViewById(R.id.imtxt)));
            ((ImageView) findViewById(R.id.imtxt)).setBackground(ob);

        } catch (Exception e) {

        }

        bck_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        sub_mt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {

//                    JSONObject jssss = _newremAdapter.Mrth();
//                    Log.d("taa",""+jssss);
//                    if (jssss.toString().equals("{}")) {
//                        xcSe = "NaN";
//                    } else {
//                        xcSe ="";
//                        for (Iterator<String> iter = jssss.keys(); iter.hasNext(); ) {
//
//                            try {
//                                Object value = jssss.get(iter.next());
//                                xcSe = xcSe + value;
//                            } catch (JSONException e) {
//                                // Something went wrong!
//                            }
//                        }
//                    }
                    JSONObject js = new JSONObject();
                    js.put(Utils.mPar, "" + selectedItem[0]);
                    js.put(Utils.subPar, "" + selectedItem[1]);
                    js.put(Utils.qtyPar, "" + _lqty.getText().toString());
                    js.put(Utils.remPar, "");
                    addJsn(js);
                } catch (Exception e) {

                }


            }
        });


        ((TextView) findViewById(R.id.qty_mins)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int xct = Integer.parseInt(_lqty.getText().toString()) - 1;
                if (xct <= 1) {
                    xct = 1;
                }
                _lqty.setText("" + xct);

            }
        });
        ((TextView) findViewById(R.id.qty_plus)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int xc = Integer.parseInt(_lqty.getText().toString()) + 1;
                _lqty.setText("" + xc);
            }
        });
    }


    private void addJsn(JSONObject jADD) {
        try {


            if (Utils.loadJSONArray(ADDAlertActivity.this, "totalArry", "" + Utils.cusOn).equals("[]")) {
                JSONArray jsd = new JSONArray();
                jsd.put(jADD);
                Utils.saveJSONArray(ADDAlertActivity.this, "totalArry", "" + Utils.cusOn, jsd);
                Log.d("@@@@@@@@33", "" + Utils.loadJSONArray(ADDAlertActivity.this, "totalArry", "" + Utils.cusOn));
            } else {


                JSONArray jsw = new JSONArray();
                jsw = Utils.loadJSONArray(ADDAlertActivity.this, "totalArry", "" + Utils.cusOn);
                int xxxT = 0;

                if (_tcdRe.getVisibility() == View.VISIBLE) {
                    jsw.put(jADD);
                    Utils.saveJSONArray(ADDAlertActivity.this, "totalArry", "" + Utils.cusOn, jsw);
                } else {
                    for (int i = 0; i < jsw.length(); i++) {
                        //if (js.toString().contains("\"item_id\":" + Integer.parseInt(eSoon[0].toString()) ))
                        if ((jsw.getJSONObject(i).getString("main").equalsIgnoreCase(jADD.getString("main"))) && jsw.getJSONObject(i).getString("subcat").equalsIgnoreCase(jADD.getString("subcat"))) {
                            xxxT = 1;
                            int cx = (Integer.parseInt(jsw.getJSONObject(i).getString("qty")) + Integer.parseInt(jADD.getString("qty")));
                            jsw.getJSONObject(i).put("qty", "" + cx);
                            Utils.saveJSONArray(ADDAlertActivity.this, "totalArry", "" + Utils.cusOn, jsw);
                            break;
                            //[{"main":"3","subcat":"0","qty":"1","remark":""}]
                        }
                    }
                    if (xxxT == 0) {
                        Log.d("@@@@@@@@33iiiii", "" + jADD);
                        jsw.put(jADD);
                        Utils.saveJSONArray(ADDAlertActivity.this, "totalArry", "" + Utils.cusOn, jsw);
                    }


                }


                Log.d("@@@@@@@@test", "" + Utils.loadJSONArray(ADDAlertActivity.this, "totalArry", "" + Utils.cusOn));

            }
            try {
                Utils.posTo = Utils.loadJSONArray(ADDAlertActivity.this, "totalArry", "" + Utils.cusOn).length();
            } catch (Exception e) {

            }
            Toast.makeText(ADDAlertActivity.this, "Added item to Order.Please Click View Order to check", Toast.LENGTH_SHORT).show();
            finish();
            // animate(1);
        } catch (Exception e) {

        }
    }

    private void mthdRemrk(String urlRem) {
        Utils.get(urlRem, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                try {
                    //Toast.makeText(ADDAlertActivity.this, ""+timeline, Toast.LENGTH_SHORT).show();
                    mRemrks = new ArrayList<Data>();
                    Data spx = new Data();
                    if (timeline.length() <= 0) {
                        xcSe = "";
                        _tcdRe.setVisibility(View.GONE);
                        ((TextView) findViewById(R.id.qty_plus)).setVisibility(View.VISIBLE);
                        ((TextView) findViewById(R.id.qty_mins)).setVisibility(View.VISIBLE);
                    } else {
                        _tcdRe.setVisibility(View.VISIBLE);
                        ((TextView) findViewById(R.id.qty_plus)).setVisibility(View.INVISIBLE);
                        ((TextView) findViewById(R.id.qty_mins)).setVisibility(View.INVISIBLE);

                    }


                    for (int i = 0; i < timeline.length(); i++) {
                        spx = new Data();
                        spx.setremarks(timeline.getJSONObject(i).getString("remarks"));
                        mRemrks.add(spx);

                    }
                    _newremAdapter = new AddREmAdapter(ADDAlertActivity.this, mRemrks);
                    _newremRec.setAdapter(_newremAdapter);
                } catch (Exception exception) {
                    Log.d("Remark" + "2this error", "" + exception.toString());
                }
                System.out.println(timeline);
            }
        });
    }


    private void visBlity(int xc) {


    }


}