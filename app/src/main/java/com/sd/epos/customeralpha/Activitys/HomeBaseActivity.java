package com.sd.epos.customeralpha.Activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sd.epos.customeralpha.Adapter.GridAdapter;
import com.sd.epos.customeralpha.Adapter.ViewOrderadapter;
import com.sd.epos.customeralpha.Models.EndangeredItem;
import com.sd.epos.customeralpha.Models.NamesTopup;
import com.sd.epos.customeralpha.Models.SetDataTopup;
import com.sd.epos.customeralpha.Models.TotalClass;
import com.sd.epos.customeralpha.R;
import com.sd.epos.customeralpha.common.Utils;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by jabbir on 16/9/15.
 */

public abstract class HomeBaseActivity extends Activity {
    public RecyclerView mRecyclerView;
    public RecyclerView.LayoutManager mLayoutManager;
    public RecyclerView mListView;
    public RecyclerView.LayoutManager mListManager;
    public RecyclerView.Adapter mListAdapter;
    public RecyclerView.Adapter mAdapter;
    private int postn = 0;
    private int xPstn = -1;
    private ArrayList<String> LastArryList = new ArrayList<>();
    private int xCounter = 0;
    private String inside = "";
    private int xuuuPost = 0;
    private ArrayList<String> insi = new ArrayList<String>();
    private ArrayList<String> tyArry = new ArrayList<String>();
    public RecyclerView mDListRec;
    public RecyclerView.LayoutManager mDListManager;
    public ViewOrderadapter mDListAdapter;
    protected HomeBaseActivity context;
    private String xmlStrMai = "";
    private String tokenNumber = "";
    private JSONArray MainGetOrd = new JSONArray();
    private Bitmap icon, con;
    private String _lastOrder;
    private ArrayList<TotalClass> tItems = new ArrayList<TotalClass>();
    private HashMap<String, ArrayList<TotalClass>> ttlItems = new HashMap<String, ArrayList<TotalClass>>();
    public TextView countNum;
    private List<String> al_trr;
    private ArrayList<EndangeredItem> mItems;
    private ArrayList<NamesTopup> umItems = new ArrayList<NamesTopup>();
    private ArrayList<SetDataTopup> sUitItems = new ArrayList<SetDataTopup>();
    private int totalSize = 1;
    private int cForm = 0;
    private Map<String, String> xcAdap = new HashMap<>();
    public static Map<String, String> xyzHash = new HashMap<>();
    private Map<String, String> noHash = new HashMap<>();
    private List<String> tesTT = new ArrayList<>();
    private ProgressDialog pdDilog;
    private HashMap<String, String> firNumr = new HashMap<String, String>();
    private HashMap<String, Bitmap> tsHash = new HashMap<String, Bitmap>();
    private String xmlMain = "";
    public static int xMainfor = -1;
    int sizerr = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cForm = getIntent().getExtras().getInt("nextLev");
        Log.d("cForm", "" + cForm);
        tesTT = Utils.readList(HomeBaseActivity.this, "Main_Cat");
        Log.d("cFormtesTT", "" + Utils.TtlItem.size());

        pdDilog = new ProgressDialog(this);
        pdDilog.setMessage("Loding Data.....");
        pdDilog.setCancelable(false);
        pdDilog.setIndeterminate(true);
        xcAdap = Utils.loadMap(HomeBaseActivity.this, "xhaNames");
        xyzHash = Utils.loadMap(HomeBaseActivity.this, "pageImages");
        noHash = Utils.loadMap(HomeBaseActivity.this, "pImages");
        if (cForm <= 1) {
            Utils.TtlItem = new HashMap<String, ArrayList<EndangeredItem>>();
            new gridViewLayout().execute();
        }

//        mTextvie.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//            }
//        });

    }

    public class gridViewLayout extends AsyncTask<String, Void, HashMap<String, ArrayList<EndangeredItem>>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            icon = BitmapFactory.decodeResource(getResources(),
//                    R.drawable.se);

            //mRecyclerView.getAdapter().
            icon = Utils.decodeSampledBitmapFromResource(getResources(), R.drawable.nopic, 100, 100);
            pdDilog.show();

        }

        @Override
        protected HashMap<String, ArrayList<EndangeredItem>> doInBackground(String... urls) {


            byte[] decodedString;
            try {

                Utils.TtlItem = new HashMap<String, ArrayList<EndangeredItem>>();
                JSONArray jas = new JSONArray();
                jas = Utils.js(HomeBaseActivity.this);

                Log.i("came", "came");

                for (int j = 0; j < tesTT.size(); j++) {
                    JSONArray result = new JSONArray();
                    result = jas.getJSONArray(j);
                    mItems = new ArrayList<EndangeredItem>();

                    for (int i = 0; i < result.length(); i++) {

                        EndangeredItem species;
                        if (result.getJSONObject(i) != null) {
                            if (result.getJSONObject(i).isNull("userid")) {

                                species = new EndangeredItem();
                                Double value = Double.parseDouble(result.getJSONObject(i).getString("price"));
                                species.setName(result.getJSONObject(i).getString("item_desc"));
                                species.setPrice(String.format("%.2f", value));
                                // species.setbtnPage(result.getJSONObject(i).getString("button_no"));
                                species.setitemId(result.getJSONObject(i).getString("item_id"));

//                                if(!result.getJSONObject(i).has("button_no"))
//                                    species.setbtnPage(result.getJSONObject(i).getString("button_no"));


                                if (result.getJSONObject(i).has("variable_item")) {
                                    species.setvName("yes");
                                } else {
                                    species.setvName("no");
                                }
                                if (result.getJSONObject(i).has("set_item")) {
                                    species.setsetName("yes");
                                } else {
                                    species.setsetName("no");
                                }


                                if (!result.getJSONObject(i).getString("image").matches("") || xyzHash.get(result.getJSONObject(i).getString("item_id")).matches("")) {
                                    Log.i("st3333ring", result.getJSONObject(i).getString("image"));
                                    species.setThumbnail(con);
                                    tsHash.put(result.getJSONObject(i).getString("item_id"), con);
//
                                } else {

                                    tsHash.put(result.getJSONObject(i).getString("item_id"), icon);
                                    species.setThumbnail(icon);
                                }
                            } else {
                                species = new EndangeredItem();
                                if (result.getJSONObject(i).has("variable_item")) {
                                    species.setvName("yes");
                                } else {
                                    species.setvName("no");
                                }
                                if (result.getJSONObject(i).has("set_item")) {
                                    species.setsetName("yes");
                                } else {
                                    species.setsetName("no");
                                }
                                String namer = "";
                                //species.setbtnPage("yyy");
                                species.setitemId("yyy");
                                try {
                                    String id = Utils.loadMap(HomeBaseActivity.this, "xdcs").get(result.getJSONObject(i).getString("userid"));
                                    Log.d("id11", "" + id + "_________" + result.getJSONObject(i).getString("userid"));

                                    //namer=Utils.loadMap(NavHomeActivity.this,"userPage").get(id);
                                    if (noHash.get(id).matches(""))
                                        species.setThumbnail(icon);
                                    else {
                                        Bitmap bitmap = Utils.decodeBase64(HomeBaseActivity.this, noHash.get(id));
                                        species.setThumbnail(bitmap);
                                    }

                                } catch (Exception e) {
                                    species.setThumbnail(icon);
                                }
                                String[] parts = (result.getJSONObject(i).getString("userid")).split("_");
                                System.out.println("ccccccccc" + parts[1]);
                                species.setName(xcAdap.get(parts[1]));
                                species.setPrice("");
                                species.setuserId(result.getJSONObject(i).getString("userid"));
                            }


                            mItems.add(species);
                        }


                        Log.d("Errorrr1111", "" + mItems.size());
                    }

//                    ArrayList<Integer> tsInt = new ArrayList<>();
//                    for (int we = 0; we < mItems.size(); we++) {
//                        if (!mItems.get(we).getbtnPage().equalsIgnoreCase("yyy"))
//                            tsInt.add(Integer.parseInt(mItems.get(we).getbtnPage()));
//
//                    }
//                    Collections.sort(tsInt);
//
//
//                     ArrayList<EndangeredItem> mTems=new ArrayList<EndangeredItem>();
//                    for (int fd = 0; fd < tsInt.size(); fd++) {
//                        for (int w = 0; w < mItems.size(); w++) {
//                            if (!mItems.get(w).getbtnPage().equalsIgnoreCase("yyy")) {
//                                if (Integer.parseInt(mItems.get(w).getbtnPage()) == tsInt.get(fd))
//
//                                {
//                                    mTems.add(mItems.get(w));
//                                    break;
//                                }
//                            }
//                        }
//
//
//                        }


                    //  mItems.get


                    Utils.TtlItem.put(tesTT.get(j), mItems);
                    Log.d("0********", "" + Utils.TtlItem);

                }

                Utils.tImages = tsHash;
                Log.d("1********", "" + Utils.tImages.size());

            } catch (Exception ex) {
                Log.d("Errorrr", "" + Utils.TtlItem.size());
                // Log.d("Errorrr", "" + ex);
            }

            return Utils.TtlItem;
        }

        @Override
        protected void onPostExecute(HashMap<String, ArrayList<EndangeredItem>> result) {
            //tsHash.clear();
//            noHash.clear();
//            mItems.clear();
//            xcAdap.clear();
            Utils.FirstTime = 0;
            try {
                if ((pdDilog != null) && pdDilog.isShowing()) {
                    pdDilog.dismiss();
                }
            } catch (final IllegalArgumentException e) {
                // Handle or log or ignore
            }
            //pdDilog.dismiss();
            tstMths();

        }
    }


    private void tstMths() {
        if (Utils.TtlItem.size() != 0) {
            al_trr = Utils.readList(HomeBaseActivity.this, "tFLitr");
            if (al_trr.size() == 0)
                mAdapter = new GridAdapter(HomeBaseActivity.this, Utils.TtlItem.get(tesTT.get(0)), 0);
            else {
                txMng(al_trr.get(0));
                mAdapter = new GridAdapter(HomeBaseActivity.this, Utils.TtlItem.get(al_trr.get(0)), xuuuPost);
            }

            mRecyclerView.setAdapter(mAdapter);
        } else {
            startActivity(new Intent(HomeBaseActivity.this, MainActivity.class));
        }

    }

    public class DownloadFilesTask extends AsyncTask<Void, Integer, HashMap<String, ArrayList<TotalClass>>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdDilog.show();

        }

        protected HashMap<String, ArrayList<TotalClass>> doInBackground(Void... arg0) {
            firNumr = new HashMap<String, String>();

            tItems = new ArrayList<TotalClass>();
            ttlItems = new HashMap<String, ArrayList<TotalClass>>();
            ArrayList<NamesTopup> rIte = new ArrayList<NamesTopup>();
            JSONArray jVariable = new JSONArray();
            try {
                jVariable = Utils.js(HomeBaseActivity.this);
                Log.d("cccccc", "" + jVariable.length());

            } catch (Exception e) {
                Log.d("cccccc", "cmmmd");
            }
            Utils.sNo = 0;

            String[] lString = new String[]{"item_id", "item_no", "print_status", "price", "qty", "remarks", "item_desc", "spec", "totalSize", "take_away_item", ""};
            String[] eSoon = new String[lString.length];

            if (MainGetOrd.length() != 0 && postn == 0) {

                tItems = new ArrayList<TotalClass>();
                Log.d("CCCCCCC", "" + MainGetOrd);
                xCounter = MainGetOrd.length();
                Utils.sNo = MainGetOrd.length();
                int orderN = MainGetOrd.length() - 1;
                firNumr = new HashMap<>();
                Utils.removeJsonSharedPreferences(HomeBaseActivity.this, "fire");
                for (int q = 0; q < MainGetOrd.length(); q++) {
                    //Bitmap largeIcon;
                    try {
                        if (MainGetOrd.getJSONObject(q).getString("print_status").equalsIgnoreCase("N"))
                            firNumr.put("" + q, MainGetOrd.getJSONObject(q).getString("order_no").toString());


                        Log.d("dfrrrr", "" + firNumr);
                        Utils.saveint(HomeBaseActivity.this, Integer.parseInt(MainGetOrd.getJSONObject(orderN).getString("order_no").toString()), "orderNum");
                        for (int w = 0; w < lString.length; w++) {
                            if (w < 7) {
                                if (w == 3) {
                                    Double value = Double.parseDouble(MainGetOrd.getJSONObject(q).getString(lString[w]).toString());
                                    eSoon[w] = String.format("%.2f", value);
                                } else if (w == 4) {
                                    eSoon[w] = MainGetOrd.getJSONObject(q).getString(lString[w]).toString().replaceAll("\\.0*$", "");

                                } else {
                                    eSoon[w] = MainGetOrd.getJSONObject(q).getString(lString[w]).toString();
                                }


                            } else if (w == 7) {
                                rIte = new ArrayList<NamesTopup>();
                                if (MainGetOrd.getJSONObject(q).has("variable_item")) {
                                    NamesTopup species = new NamesTopup();
                                    JSONArray varIt = MainGetOrd.getJSONObject(q).getJSONArray("variable_item");
                                    Log.d("catch1", "*******varIt******id******" + varIt.length());
                                    for (int t = 0; t < varIt.length(); t++) {
                                        species = new NamesTopup();
                                        Double value = Double.parseDouble(varIt.getJSONObject(t).getString("cprice"));
                                        Double qty = Double.parseDouble(varIt.getJSONObject(t).getString("cf_qty"));
                                        species.setName(varIt.getJSONObject(t).getString("citem_desc"));
                                        species.setPrice("$ " + String.format("%.2f", value));
                                        species.setcitem_no(varIt.getJSONObject(t).getString("citem_no"));
                                        species.setcseq_no(varIt.getJSONObject(t).getString("cseq_no").toString());
                                        species.setQty("" + qty);
                                        species.setcuom(varIt.getJSONObject(t).getString("cuom"));
                                        species.setcremarks(varIt.getJSONObject(t).getString("cremarks").toString());
                                        rIte.add(species);
                                    }
                                    eSoon[7] = "yes";
                                } else {
                                    eSoon[7] = "No";
                                }

                            } else {
                                eSoon[8] = "" + totalSize;
                                eSoon[9] = MainGetOrd.getJSONObject(q).getString(lString[9]);
                            }
                        }
                        // Bitmap xyz = Utils.scaleDown(Utils.tImages.get(eSoon[0].toString()), 100, true);
                        tItems.add(tIMethtems(rIte, eSoon, Utils.tImages.get(eSoon[0].toString())));
                    } catch (Exception e) {
                    }


                }
                ttlItems.put("" + postn, tItems);
                Log.d("CCCCCCC", "" + ttlItems);

            } else {
                xCounter = 0;
                tItems = new ArrayList<TotalClass>();
                ttlItems = new HashMap<String, ArrayList<TotalClass>>();
                MainGetOrd = new JSONArray();
                Utils.sNo = 0;
            }
            try {
                JSONArray jsArryfor = new JSONArray();
                for (int p = 0; p < totalSize; p++) {
                    tItems = new ArrayList<TotalClass>();
                    if (p == 0 && MainGetOrd.length() != 0) {
                        ArrayList<TotalClass> xxxItems = new ArrayList<TotalClass>();
                        xxxItems = ttlItems.get("" + p);
                        if (xxxItems.size() != 0) {
                            tItems = ttlItems.get("" + 0);
                            Log.d("CCCCCCCeeeeeee", "" + tItems.size());
                        }

                    } else {
                        tItems = new ArrayList<TotalClass>();

                    }
                    jsArryfor = Utils.loadJSONArray(HomeBaseActivity.this, "totalArry", "" + 1);
                    Log.d("122222dddddd2", "" + jsArryfor);


                    for (int i = 0; i < jsArryfor.length(); i++) {
                        int main_pos = Integer.parseInt(jsArryfor.getJSONObject(i).getString(Utils.mPar));
                        int second_pos = Integer.parseInt(jsArryfor.getJSONObject(i).getString(Utils.subPar));
                        int Qty = Integer.parseInt(jsArryfor.getJSONObject(i).getString(Utils.qtyPar));
                        String remarks = jsArryfor.getJSONObject(i).getString("remark");
                        JSONArray inoArry = jVariable.getJSONArray(main_pos);
                        Log.d("1222222inoArry", "" + main_pos);
                        String jsd = inoArry.getJSONObject(second_pos).getString("item_desc");
                        String itemNo = jVariable.getJSONArray(main_pos).getJSONObject(second_pos).getString("item_no");
                        String itemId = jVariable.getJSONArray(main_pos).getJSONObject(second_pos).getString("item_id");
                        String value = (inoArry.getJSONObject(second_pos).getString("price"));
                        Bitmap bitmap = null;

                        // bitmap = Utils.scaleDown(bitmap, 100, true);
                        Log.d("value", "" + value);
                        JSONArray jk = new JSONArray();
                        String arry = "";
                        String Redeem = "No";
                        umItems = new ArrayList<NamesTopup>();
                        sUitItems = new ArrayList<SetDataTopup>();
                        //if ((jsArryfor.getJSONObject(i).toString().contains(Utils.varPar))) {
                        JSONArray js = new JSONArray();
                        if ((jsArryfor.getJSONObject(i).toString().contains(Utils.varPar))) {

                            double xsd = 0;
                            js = jsArryfor.getJSONObject(i).getJSONArray(Utils.varPar);
                            for (int j = 0; j < js.length(); j++) {
                                JSONObject ksw = js.getJSONObject(j);
                                Iterator<String> iter = ksw.keys();
                                while (iter.hasNext()) {
                                    String key = iter.next();
                                    try {
                                        JSONArray jd = ksw.getJSONArray(key);
                                        if (jd.length() == 0) {
                                            Log.d("null", "null");
                                        }

                                        // jd.put(js.getJSONObject(j).getJSONArray("1"));
                                        if (!jd.equals("[]")) {
                                            Log.d("catch1", "*************jd******" + jd);
                                            NamesTopup species = new NamesTopup();
                                            for (int jw = 0; jw < jd.length(); jw++) {
                                                species = new NamesTopup();
                                                species.setName(jd.getString(0).toString());
                                                species.setPrice(jd.getString(1).toString());
                                                species.setcitem_no(jd.getString(2).toString());
                                                species.setcseq_no(jd.getString(3).toString());
                                                species.setQty(jd.getString(4).toString());
                                                species.setcuom(jd.getString(5).toString());
                                                species.setcremarks(jd.getString(6).toString().replace("\n", " "));
                                            }
                                            xsd = (xsd + (Double.parseDouble(jd.getString(1).toString())));
                                            umItems.add(species);
                                            Log.d("catch1", "*************jdid******" + umItems);
                                        } else {
                                            Log.d("catch1", "*************jdid******" + umItems.size());
                                        }

                                    } catch (JSONException e) {
                                        // Something went wrong!
                                    }
                                }
                            }
                            value = (String.format("%.2f", xsd));
                        }

                        Log.d("catch1", "*************jd******" + js.length());


                        //}

                        //[{"Main":0,"Subcat":1,"edit":"2","remark":{},"Variable":[]}]
                        //NamesTopup species = new NamesTopup();
                        String spec = "";
                        if (jsArryfor.getJSONObject(i).has(Utils.varPar)) {
                            spec = "yes";
                        } else {
                            spec = "no";
                        }
//                        else if (jsArryfor.getJSONObject(i).has(Utils.setitem)) {
//                            spec = "set";
//                        }

                        String[] ttlSw = new String[]{itemId, itemNo, Redeem, value, "" + Qty, remarks, jsd, spec, "" + totalSize, "N"};


                        tItems.add(tIMethtems(umItems, ttlSw, con));
                    }

                    Log.d("1111", "" + p);
                    ttlItems.put("" + p, tItems);

                }


            } catch (Exception e) {
                Log.d("catch1", "*************idqqqqq******" + ttlItems.size());
            }

            return ttlItems;
        }

        protected void onPostExecute(HashMap<String, ArrayList<TotalClass>> jvr) {

            try {
                //tsHash.clear();
                //tItems.clear();
                pdDilog.dismiss();
                if (xCounter == 0) {
                    ((TextView) findViewById(R.id.submitfir)).setVisibility(View.VISIBLE);
                    ((TextView) findViewById(R.id.fsubmit)).setVisibility(View.VISIBLE);
                    ((TextView) findViewById(R.id.sl)).setVisibility(View.INVISIBLE);
                    ((TextView) findViewById(R.id.frt)).setVisibility(View.INVISIBLE);
                } else if (xCounter != 0 && Utils.loadJSONArray(HomeBaseActivity.this, "totalArry", "" + 1).length() != 0) {
                    ((TextView) findViewById(R.id.fsubmit)).setVisibility(View.VISIBLE);
                    ((TextView) findViewById(R.id.submitfir)).setVisibility(View.VISIBLE);
                    ((TextView) findViewById(R.id.sl)).setVisibility(View.INVISIBLE);
                    ((TextView) findViewById(R.id.frt)).setVisibility(View.INVISIBLE);
                } else {
                    ((TextView) findViewById(R.id.fsubmit)).setVisibility(View.INVISIBLE);
                    ((TextView) findViewById(R.id.submitfir)).setVisibility(View.INVISIBLE);
                    ((TextView) findViewById(R.id.sl)).setVisibility(View.VISIBLE);
                    ((TextView) findViewById(R.id.frt)).setVisibility(View.VISIBLE);
                }


                // Log.d("######", "" + postn);
                tItems = new ArrayList<TotalClass>();
                tItems = jvr.get("" + 0);
                Log.d("Logggggggg", "*************id******" + xCounter);
                // if(tItems.size()!=0){
                if (tItems.size() == 0) {

                } else {
                    mDListAdapter = new ViewOrderadapter(HomeBaseActivity.this, tItems, 0,1);
                    mDListRec.setAdapter(mDListAdapter);
                }
                if (xPstn == 1) {
                        Utils.writeList(getApplicationContext(), LastArryList, "fire");
                        Log.d("&&&ccccc&&&&&", "" + Utils.readList(getApplicationContext(), "fire"));
                        mDListAdapter = new ViewOrderadapter(HomeBaseActivity.this, tItems, 0,0);
                        mDListRec.setAdapter(mDListAdapter);
                    //mthdSelectAll();
                    firtokitchen();
                }

                //}


            } catch (Exception jsd) {

            }

        }
    }


    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onPause() {
        super.onPause();

//        ActivityManager activityManager = (ActivityManager) getApplicationContext()
//                .getSystemService(Context.ACTIVITY_SERVICE);
//
//       // activityManager.moveTaskToFront(getTaskId(), 0);
    }

    private TotalClass tIMethtems(ArrayList<NamesTopup> mItems, String[] lsTing, Bitmap bitmap) {
        TotalClass ttlspecies = new TotalClass();
        ttlspecies.setitemId(lsTing[0]);
        ttlspecies.setitemNo(lsTing[1]);
        ttlspecies.setreedem(lsTing[2]);
        Double value = Double.parseDouble(lsTing[3]);
        ttlspecies.setprice(String.format("%.2f", value));
        ttlspecies.setQty(lsTing[4]);
        ttlspecies.setremark(lsTing[5]);
        ttlspecies.setistopUp(mItems);

        //ttlspecies.setdatatopUp(uItm);
        ttlspecies.setnpCover(lsTing[8]);
        ttlspecies.setThumbnail(bitmap);
        ttlspecies.setName(lsTing[6]);
        ttlspecies.sethasVar(lsTing[7]);
        ttlspecies.settAway(lsTing[9]);
        Log.d("catch1", "*******mm******id******" + mItems.size());
        return ttlspecies;


    }

    public void firtokitchen() {
        tyArry = new ArrayList<String>();
        List<String> jsNo = new ArrayList<String>();
        jsNo = Utils.readList(HomeBaseActivity.this, "fire");
        if (firNumr.size() >= 1 && jsNo.size() >= 1) {
            List<String> ijhttt = Arrays.asList("01", Utils.getString(HomeBaseActivity.this, "wh_id"), Utils.currenDate(), Utils.getString(HomeBaseActivity.this, "Tableno"), "" + Utils.getint(HomeBaseActivity.this, "orderNum"), "%s", "DBA");
            JSONObject objT = new JSONObject();
            //Utils.getString(NavHomeActivity.this, "Tableno")
            for (int i = 0; i < totalSize; i++) {
                try {


                    Log.d("@@sNo", "" + jsNo);
                    if (!jsNo.toString().equals("[]")) {

                        for (int j = 0; j < jsNo.size(); j++) {
                            objT = new JSONObject();
                            if (firNumr.get("" + jsNo.get(j)) == null) {
                                //Utils.alertDialogShow(HomeBaseActivity.this, getString(R.string.nOrd), "Alert", 0);
                                break;
                            } else {
                                inside = "";
                                TotalClass nature = tItems.get(i);
                                nature = ttlItems.get("" + 0).get(Integer.parseInt(jsNo.get(j)));
                                // Log.d("@@sNo", "" + nature.getviewOrder());


                                // Log.d(sd"catch1", "*************idqqqqq******" + ttlItems.size());
                                for (int k = 0; k < ijhttt.size(); k++) {
                                    if (k == 5)
                                        objT.put(Utils.places.get(k).toString(), (Integer.parseInt(jsNo.get(j)) + 1));
                                    else if (k == 4) {
                                            objT.put(Utils.places.get(k).toString(), firNumr.get("" + jsNo.get(j)));
                                            Log.d("catch1cccccc", "firNumr.get" + firNumr.get("" + j));

                                    } else
                                        objT.put(Utils.places.get(k).toString(), ijhttt.get(k).toString());
                                }
                                Log.d("objT", "objT.get" + objT);

                                Iterator<String> iter = objT.keys();
                                while (iter.hasNext()) {
                                    String key = iter.next();
                                    try {
                                        Object value = objT.get(key);

                                        inside += "<" + key + ">" + value.toString() +
                                                "</" + key + ">";
                                    } catch (JSONException e) {
                                        // Something went wrong!
                                    }
                                }
                                inside = "<" + "firetokitchen" + ">" + inside +
                                        "</" + "firetokitchen" + ">";
                                tyArry.add(inside);
                                Log.d("**value2", "*************id******" + tyArry);
                            }
                        }


                    }


                } catch (JSONException jE) {

                }
            }



//                try {
//                    String encodeXml;
//                    encodeXml = URLEncoder.encode((getResources().getString(R.string.encdq)).toString() + inside + (getResources().getString(R.string.encdtaiq)).toString(), "UTF-8");
//                    String simpleUrl = Utils.MAINTAG + Utils.FirePOrder;
//                    RequestParams par = new RequestParams();
//                    par.put("compcode", "01");
//                    par.put("xmldata", encodeXml);
//                    Utils.d("here this +++++", "" + simpleUrl);
//                    httpCall(simpleUrl, par);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            inside = "";
            for (int k = 0; k < tyArry.size(); k++) {
                inside += tyArry.get(k);
            }

            try {
                String encodeXml;
                if (inside.matches("") || inside.equals("")) {
                    Utils.alertDialogShow(HomeBaseActivity.this, getString(R.string.nOrd), "Alert", 0);
                } else {
                    encodeXml = URLEncoder.encode((getResources().getString(R.string.encdq)).toString() + inside + (getResources().getString(R.string.encdtaiq)).toString(), "UTF-8");
                    String simpleUrl = Utils.MAINTAG + Utils.FireOrder + encodeXml;
                    Log.d("here this +++++", "" + simpleUrl);
                    httpCall(simpleUrl, null);
                }


            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }


        Log.d("**value3", "*************id******" + inside);


    }

    private void backMethod() {
//        Intent intX = new Intent(HomeBaseActivity.this, HomeBaseActivity.class);
//        startActivity(intX);
        //finish();
        // overridePendingTransition(R.anim.slide_out_up, R.anim.slide_in_up);
    }

    public void Intialick() {
        String strUrl = Utils.MAINTAG + Utils.GETOrder + Utils.compnyCd + "&whid=" + Utils.getString(HomeBaseActivity.this, "wh_id") + "&docdate=" + Utils.currenDate() + "&tableno=" + Utils.getString(HomeBaseActivity.this, "Tableno") + "&uuid="
                + "51BA1377-45FA-4327-9A1A-98CE3F30EABE";
        Log.d("here this +++++", "" + strUrl);
        ttlItems = new HashMap<String, ArrayList<TotalClass>>();
        if (!Utils.isConnected(HomeBaseActivity.this) || Utils.getString(HomeBaseActivity.this, "Tableno") == null) {
            MainGetOrd = new JSONArray();
            //MainGetOrd = timeline;
            new DownloadFilesTask().execute();

            Log.d("Main Array", "" + "");
        } else
            httpCall(strUrl, null);


//            MainGetOrd = new JSONArray();
//        //MainGetOrd = timeline;
//        new DownloadFilesTask().execute();
    }

    public void mthdSelectAll() {
        ArrayList<String> tAway = new ArrayList<String>();
        int x = mDListRec.getChildCount();
        CheckedTextView selectItem;
        TextView txt;
        tItems = ttlItems.get("" + 0);
        for (int w = 0; w < tItems.size(); w++) {
            TotalClass nature = tItems.get(w);
            if (nature.getReddm.equalsIgnoreCase("N")) {
                tAway.add("" + w);
            }
        }
        try {
            ArrayList<String>  insideItem = new ArrayList<String>();
            //Utils.writeList(context, insideItem, "fire");

            Utils.writeList(getApplicationContext(), tAway, "fire");
            Log.d("&&&&&&&&", "" + Utils.readList(getApplicationContext(), "fire"));
//            JSONArray jsArray = new JSONArray(tAway);
//            Utils.saveJSONArray(getApplicationContext(), "fire", "" + 0, jsArray);
//            Log.d("&&&&&&&&", "" + Utils.loadJSONArray(getApplicationContext(), "fire", "" + 0));


            mDListAdapter = new ViewOrderadapter(HomeBaseActivity.this, tItems, 0,0);
            mDListRec.setAdapter(mDListAdapter);


        } catch (Exception ks) {
        }
    }

    public void btnFirstClick(final int xysa) {

        // tknMethod(Utils.MAINTAG + Utils.tknNumber);
        if (!Utils.isConnected(HomeBaseActivity.this)) {
        } else {
            try {

                xPstn = xysa;
                tyArry = new ArrayList<String>();
                Iterator it = ttlItems.entrySet().iterator();
                LastArryList = new ArrayList<>();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();

                    JSONObject objT = new JSONObject();
                    System.out.println(pair.getKey() + " = " + pair.getValue());
                    tItems = new ArrayList<TotalClass>();
                    tItems = ttlItems.get(pair.getKey());
                    List<String> tkAway = new ArrayList<String>();
                    JSONArray mDataset = new JSONArray();
                    Log.d("RRRRtkAway", "" + tkAway.size() + "  txttt" + tItems.size());
//                        for (int k = 0; k < tkAway.size(); k++) {
//                            Log.d("RRRRtkAway", "" + tkAway.get(k));
//                        }

                    try {
                        if (!Utils.loadJSONArray(HomeBaseActivity.this, "SAVED", "" + pair.getKey()).toString().equalsIgnoreCase("[]")) {
                            mDataset = Utils.loadJSONArray(HomeBaseActivity.this, "SAVED", "" + pair.getKey());
                            ArrayList<String> list = new ArrayList<String>();

                            if (mDataset != null) {
                                int len = mDataset.length();
                                for (int i = 0; i < len; i++) {
                                    tkAway.add(mDataset.get(i).toString());
                                }
                            }
                        }
                    } catch (Exception e) {

                    }
                    for (int i = 0; i < tItems.size(); i++) {

                        xmlStrMai = "";
                        TotalClass nature = tItems.get(i);
                        String takeWe = "N";
                        if (tkAway.size() != tItems.size()) {
                            takeWe = "N";
                        } else {
                            takeWe = tkAway.get(i);
                        }

                        // Boolean takW =tkAway.get(i);
                        Log.d("RRRRRRR", "" + nature.getReddm);
                        if (nature.getReddm.equalsIgnoreCase("NO")) {

                            xCounter += 1;
                            if(xPstn==1){
                                LastArryList.add(""+(xCounter-1));
                            }
                            objT.put("order_no", "" + 0);
                            objT.put("s_no", "" + xCounter);
                            objT.put("item_id", nature.getitemId());
                            objT.put("item_no", nature.getitemNo());
                            objT.put("qty", "" + nature.getQty().replace("$ 0.00", "1"));
                            objT.put("price", nature.getprice());
                            if (nature.getremark().equalsIgnoreCase("NaN"))
                                objT.put("remarks", "");
                            else
                                objT.put("remarks", nature.getremark().replace(",", "\r"));
                            objT.put("takeaway", takeWe);
                            // objT.put("guest", pair.getKey());
                            int cover = 1;
                            try {
                                cover = Utils.getint(HomeBaseActivity.this, "ccccints");
                            } catch (Exception e) {

                            }
                            objT.put("no_of_cover", "" + cover);
                            //Log.d("Log",""+ nature.getistopUp() );
                            if (nature.getistopUp() != null) {
                                ArrayList<NamesTopup> ts = new ArrayList<NamesTopup>();
                                insi = new ArrayList<String>();
                                ts = nature.getistopUp();
                                String total_versu = "";
                                String vars = "";
                                for (int iq = 0; iq < ts.size(); iq++) {
                                    JSONObject twobjT = new JSONObject();
                                    NamesTopup species = new NamesTopup();
                                    species = ts.get(iq);
                                    vars = "";
                                    twobjT.put("s_no", "" + xCounter);
                                    for (int q = 0; q < ts.size(); q++) {
                                        twobjT.put("seq_no", species.getcseq_no());
                                        twobjT.put("item_id", species.getcitem_no());
                                        twobjT.put("qty", "" + species.getQty().replace("$ 0.00", "1"));
                                        twobjT.put("uom", "" + species.getcuom());
                                        twobjT.put("price", species.getPrice().replace("$ ", ""));
                                        twobjT.put("remarks", "" + species.getcremarks().replace(",", "\r"));
                                    }
                                    // twobjT.put("uom", "" + species.getcuom());
                                    Iterator<String> iter = twobjT.keys();
                                    while (iter.hasNext()) {
                                        String key = iter.next();
                                        try {
                                            Object value = twobjT.get(key);

                                            vars += "<" + key + ">" + value.toString() +
                                                    "</" + key + ">";
                                        } catch (JSONException e) {
                                            // Something went wrong!
                                        }
                                    }
                                    vars = "<" + "v_data" + ">" + vars +
                                            "</" + "v_data" + ">";
                                    insi.add(vars);


                                    Log.d("**value", "*************id******" + insi);

                                }
                                String ttvars = "";
                                for (int oi = 0; oi < insi.size(); oi++) {
                                    ttvars += insi.get(oi);
                                }

//                                    vars = "<" + "variable_data" + ">" + vars +
//                                            "</" + "variable_data" + ">";

                                Log.d("**vars", "*************var******" + ttvars);
                                objT.put("variable_data", ttvars);
                            }

                            Log.d("RRRRRRR", "" + objT);
                            Iterator<String> iter = objT.keys();
                            while (iter.hasNext()) {
                                String key = iter.next();
                                try {
                                    Object value = objT.get(key);
                                    xmlStrMai += "<" + key + ">" + value.toString() +
                                            "</" + key + ">";
                                    Log.d("**vaeeee", "*************id******" + xmlStrMai);
                                } catch (JSONException e) {
                                    // Something went wrong!
                                }
                            }
                            xmlStrMai = "<" + "order" + ">" + xmlStrMai +
                                    "</" + "order" + ">";



                            tyArry.add(xmlStrMai);
                        } else {
                            Log.d("**value", "Came here for text");
                        }

                    }

                    //it.remove(); // avoids a ConcurrentModificationException
                }
                if (xmlStrMai.matches("")) {
                    Utils.alertDialogShow(HomeBaseActivity.this, getString(R.string.noOrd), "Alert", 0);
                    return;
                } else {
                    Utils.d("VIewmyorder", xmlStrMai);
                    xmlMain = "";
                    Log.d("tyArrrr", "" + tyArry.size());
                    for (int i = 0; i < tyArry.size(); i++) {
                        xmlMain += tyArry.get(i);
                    }
                    try {
                        Log.d("tyArrrr111111", "" + tyArry.size() + "ddd" + xmlMain);
                        if (xmlMain.matches("")) {
                            Toast.makeText(HomeBaseActivity.this, "Please check the Network  and try again.", Toast.LENGTH_SHORT).show();
                            backMethod();
                        } else {

                            alT(xmlMain,xPstn);
//
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                // Log.d("***********", "" + jsObjArry);
            } catch (JSONException js) {

            }

        }


    }

    private void httpCall(String strUrl, RequestParams valu) {
        Utils.post(strUrl, valu, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                pdDilog.show();
            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {

                // Pull out the first event on the public timeline
                try {
                    JSONArray jsAr = timeline;
                    MainGetOrd = new JSONArray();
                    Log.d("**@jsArryfor", "" + jsAr);
                    if (jsAr.length() == 0) {

                        xMainfor = 0;
                        postn = 0;

                        try {
                            tItems = new ArrayList<TotalClass>();
                            JSONArray jsArryfor = Utils.loadJSONArray(HomeBaseActivity.this, "totalArry", "" + 1);
                            Log.d("122222dddddd2", "" + jsArryfor);
                            if (timeline.length() == 0 && jsArryfor.length() == 0) {
                                tItems = new ArrayList<TotalClass>();
                                mDListAdapter = new ViewOrderadapter(HomeBaseActivity.this, tItems, 0,1);
                                mDListRec.setAdapter(mDListAdapter);


                            } else {
                                MainGetOrd = new JSONArray();
                                new DownloadFilesTask().execute();
                            }


                        } catch (Exception e) {
                            Log.d("came here", "ggggggg" + e.getMessage());
                        }


                        //Utils.alertDialogShow(HomeBaseActivity.this, "Currently No Items is ordered.", "Get Order", 0);


                        //return;
                    }
                    // else if()


                    else if (jsAr.toString().contains("@ll_orderno")) {


                        if (Integer.parseInt(jsAr.getJSONObject(0).getString("@ll_orderno").toString()) > 0) {

                            _lastOrder=jsAr.getJSONObject(0).getString("@ll_orderno");
                            ttlItems = new HashMap<String, ArrayList<TotalClass>>();
                            // Utils.saveint(HomeBaseActivity.this, Integer.parseInt(jsAr.getJSONObject(0).getString("@ll_orderno").toString()), "orderNum");
                            txtMthd(getString(R.string.sOrder));
                            // Utils.alertDialogShow(HomeBaseActivity.this, getString(R.string.sOrder), "", 2);
                        } else {
                            Utils.alertDialogShow(HomeBaseActivity.this, getString(R.string.failOrder) + "(" + jsAr.getJSONObject(0).getString("@ll_orderno").toString() + ")", "", 1);
                        }
                    } else if (jsAr.toString().contains("Return_Value")) {
                        if (Integer.parseInt(jsAr.getJSONObject(0).getString("Return_Value").toString()) == 0) {
                            new AlertDialog.Builder(HomeBaseActivity.this)
                                    .setTitle(" Firing of order items successful.")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            xPstn = -1;
                                            txtMthd(getString(R.string.fireOrder));
                                        }
                                    })
                                    .show().getWindow().setLayout(800, 300);
                        }
                        Utils.removeJsonSharedPreferences(HomeBaseActivity.this, "fire");

                    } else {
                        MainGetOrd = new JSONArray();
                        MainGetOrd = timeline;
                        xMainfor = (timeline.length());
                        new DownloadFilesTask().execute();

                        Log.d("Main Array", "" + timeline);
                    }

                } catch (Exception exception) {
                    Log.e("myappname", "exception", exception);
                    // handleError(exception);
                }

                // Do something with the response
                System.out.println(timeline);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("errror", "" + errorResponse);
                Utils.alertDialogShow(HomeBaseActivity.this, getString(R.string.failOrder), " Fail Alert", 1);
            }

            @Override
            public void onFinish() {
                pdDilog.dismiss();
            }
        });

    }


    public void run() {

    }

    private void tknMethod(String Url) {
        Utils.get(Url, null, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                pdDilog.show();
            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {

                // Pull out the first event on the public timeline
                try {
                    JSONArray jsAr = timeline;
                    Log.d("**@jsArryfor", "" + jsAr);
                    if (jsAr.length() == 0) {

                        Log.d("errror", "");
                        Utils.alertDialogShow(HomeBaseActivity.this, getString(R.string.failOrder), " Fail Alert", 1);
                        //return;
                    } else {
                        tokenNumber = jsAr.getJSONObject(0).getString("Token");
                    }

                } catch (Exception exception) {
                    Log.e("myappname", "exception", exception);
                    // handleError(exception);
                }

                // Do something with the response
                System.out.println(timeline);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("errror", "" + errorResponse);
                Utils.alertDialogShow(HomeBaseActivity.this, getString(R.string.failOrder), " Fail Alert", 1);
            }

            @Override
            public void onFinish() {
                pdDilog.dismiss();
            }
        });
    }

    private void backAnimation(View viewRoot) {
        int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
        int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
        int finalRadius = Math.max(viewRoot.getWidth(), viewRoot.getHeight());

//        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, 0, finalRadius);
//        // viewRoot.setBackgroundColor(color);
//        anim.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                // animateRevealShow(myView);
//            }
//        });
//        anim.start();
    }

    private Bitmap xc(String cd) {
        Bitmap xyz = null;
        try {
            // Create a URL for the desired page
            URL url = new URL(cd);

            // Read all the text returned by the server
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String str;
            while ((str = in.readLine()) != null) {
                xyz = Utils.decodeBase64(HomeBaseActivity.this, str);
                Log.d("str", str);
                // xyz=str;
                // str is one line of text; readLine() strips the newline character(s)
            }
            in.close();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        return xyz;
    }

    private void alT(final String xmlMain, final int xyPoint) {
        AlertDialog.Builder alertDel = new AlertDialog.Builder(HomeBaseActivity.this);
        alertDel.setMessage("                    Would you like to Submit your Order.                                              ");
        alertDel.setTitle("SUBMIT ORDER ");
        alertDel.setPositiveButton("         Yes              ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                try {
                    String[] xc = Utils.loadArray("serverName", HomeBaseActivity.this);
                    String url;
                    // Utils.getString(HomeBaseActivity.this, "Tableno")+"&printername=PRNK1"//Utils.getString(HomeBaseActivity.this, "Tableno")
                    url = URLEncoder.encode((getResources().getString(R.string.encdq)).toString() + xmlMain + (getResources().getString(R.string.encdtaiq)).toString(), "UTF-8");
                  String xyzPost= Utils.ExNT;
                    if(xyPoint==2){
                        xyzPost=Utils.auto_NT;
                  }

                    String simpleUrl = Utils.MAINTAG + xyzPost + Utils.compnyCd + "&whid=" + Utils.getString(HomeBaseActivity.this, "wh_id") + "&whcd=" + xc[6] + "&regcode=" + xc[4] + "&shiftcode=SHIFT1&docdate=" + Utils.currenDate() + "&tableno=" + Utils.getString(HomeBaseActivity.this, "Tableno") + "&xmlfile="
                            + url + "&userid=" + "DBA";
                    Log.d("here this +++++", "" + simpleUrl);
                    if (!Utils.isConnected(HomeBaseActivity.this)) {

                        txtMthd(getString(R.string.sOrder));
                    } else {
                        httpCall(simpleUrl, null);
                    }
                } catch (Exception e) {

                }


            }
        });

        alertDel.setNegativeButton("NO                          ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });

        alertDel.show();


    }

    private void txMng(String dsxd) {
        for (int i = 0; i < tesTT.size(); i++) {
            if (tesTT.get(i).toString().equalsIgnoreCase(dsxd)) {
                xuuuPost = i;
                break;
            }

        }
    }

    private void txtMthd(String firorder) {
        if (xPstn == 1) {

            Utils.removeFromSharedPreferences(getApplicationContext(), "orderNum", "" + 1);
            Utils.removeJsonSharedPreferences(getApplicationContext(), "SAVED");
            Utils.removeJsonSharedPreferences(getApplicationContext(), "totalArry");
            Intialick();

        } else {

            new AlertDialog.Builder(HomeBaseActivity.this)
                    .setTitle(firorder)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Utils.removeFromSharedPreferences(getApplicationContext(), "orderNum", "" + 1);
                            Utils.removeJsonSharedPreferences(getApplicationContext(), "SAVED");
                            Utils.removeJsonSharedPreferences(getApplicationContext(), "totalArry");
                            Utils.posTo = 0;
                            //startActivity(new Intent(HomeBaseActivity.this, FeedbackActivity.class));
                            Intent i = getBaseContext().getPackageManager()
                                    .getLaunchIntentForPackage(getBaseContext().getPackageName());
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.addCategory(Intent.CATEGORY_HOME);
                            startActivity(i);
                            android.os.Process.killProcess(android.os.Process.myPid());


                        }
                    })
                    .show().getWindow().setLayout(1000, 300);
        }

    }


}