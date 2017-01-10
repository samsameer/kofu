package com.sd.epos.customeralpha.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.sd.epos.customeralpha.Adapter.TableAdapter;
import com.sd.epos.customeralpha.R;
import com.sd.epos.customeralpha.common.TableNames;
import com.sd.epos.customeralpha.common.Utils;

import org.apache.http.Header;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by jabbir on 14/5/15.
 */
public class TableSelection extends Activity {
    private RecyclerView tbRecyclerView;
    private RecyclerView.LayoutManager tbLayoutManager;
    private RecyclerView.Adapter tbAdapter;
    private Toolbar mToolbar;
    private ArrayList<TableNames> mItems;
    private ArrayList<Integer> seqNo;
    private int tblSelect = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_selection);
//        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
        tblSelect = getIntent().getExtras().getInt("tbl");
        String Url = Utils.MAINTAG + Utils.GETTABl + Utils.compnyCd + "&whid=" + Utils.getString(TableSelection.this, "wh_id") + "&docdate=" + Utils.currenDate();
        Log.d("url", "" + Url);
        tbRecyclerView = (RecyclerView) findViewById(R.id.table_view);
        tbRecyclerView.setHasFixedSize(true);
        tbLayoutManager = new GridLayoutManager(this, 4);
        //int firstVisiblePosition = tbLayoutManager.findFirstVisibleItemPosition();
        //tbLayoutManager.scrollToPositionWithOffset(pos, 0);
        tbRecyclerView.setLayoutManager(tbLayoutManager);
//        getSupportActionBar().setTitle(R.string.tbl_se);
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//                overridePendingTransition(R.anim.nothing, R.anim.fade_in);
//            }
//        });
//        Utils.removeFromSharedPreferences(TableSelection.this, "totalArry", "" + 1);
        Utils.removeFromSharedPreferences(TableSelection.this, "orderNum", "" + 1);
        Utils.removeJsonSharedPreferences(TableSelection.this, "SAVED");
        Utils.removeJsonSharedPreferences(TableSelection.this, "totalArry");
        Utils.posTo = 0;
        try {
            String url;



            //url =

            if (!Utils.isConnected(TableSelection.this)) {
//                try {
//                    String json = "";
//                    try {
//                        BufferedInputStream resourceStream = new BufferedInputStream(getResources().openRawResource(R.raw.gettablestatus));
//                        BufferedReader reader = new BufferedReader(new InputStreamReader(resourceStream));
//
//                        String line;
//                        while ((line = reader.readLine()) != null) {
//                            json += line;
//                        }
//                        reader.close();
//                        resourceStream.close();
//                    } catch (Exception ex) {
//                        Log.e("myApp", ex.getMessage());
//                    }
//
//                    JSONArray jsonobject = new JSONArray(json);
//                    adap(jsonobject);
////
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            } else {
                //url =
                Utils.get(Url, null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                        // Pull out the first event on the public timeline
                        if(timeline.length()==0){
                            Utils.saveString(TableSelection.this, "01", "Tableno");
                            Intent xz = new Intent(TableSelection.this, HomeScreen.class);
                            xz.putExtra("nextLev", 1);
                            startActivity(xz);
                        }
                        else
                        adap(timeline);
                        // Do something with the response
                        System.out.println(timeline);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void adap(JSONArray js) {
        try {
            seqNo = new ArrayList<Integer>();

            TableNames spx;
            JSONArray jsAr = js;


            for (int z = 0; z < jsAr.length(); z++) {
                seqNo.add(Integer.parseInt(jsAr.getJSONObject(z).getString("seq_no")));
            }

            Collections.sort(seqNo);
            Log.d("******seqNo", "" + seqNo);
            mItems = new ArrayList<TableNames>();
            for (int k = 0; k < seqNo.size(); k++) {

                for (int z = 0; z < jsAr.length(); z++) {
                    if (seqNo.get(k) == Integer.parseInt(jsAr.getJSONObject(z).getString("seq_no"))) {
                        spx = new TableNames();
                        spx.setName(jsAr.getJSONObject(z).getString("table_no"));
                        spx.setPrice(jsAr.getJSONObject(z).getString("table_name"));
                        spx.setcolor(jsAr.getJSONObject(z).getString("color"));
                        spx.setQty(jsAr.getJSONObject(z).getString("seq_no"));

                        if (jsAr.getJSONObject(z).has("no_of_cover"))
                            spx.setno_of_cover(jsAr.getJSONObject(z).getString("no_of_cover"));
                        else
                            spx.setno_of_cover("1");

                        mItems.add(spx);
                        break;
                    }
                }

            }
            Log.d("******EEEE", "" + mItems.size());

            tbAdapter = new TableAdapter(TableSelection.this, mItems, tblSelect);
            tbRecyclerView.setAdapter(tbAdapter);

        } catch (Exception e) {

        }
    }
}