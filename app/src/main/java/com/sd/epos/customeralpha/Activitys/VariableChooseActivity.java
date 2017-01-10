package com.sd.epos.customeralpha.Activitys;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.StateListDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sd.epos.customeralpha.Adapter.MyAdapter;
import com.sd.epos.customeralpha.Models.NamesTopup;
import com.sd.epos.customeralpha.R;
import com.sd.epos.customeralpha.common.CoolPriceView;
import com.sd.epos.customeralpha.common.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by jabbir on 15/10/15.
 */
public class VariableChooseActivity extends Activity {
    public RecyclerView mVariable;
    public RecyclerView.LayoutManager mVarManger;
    public RecyclerView.Adapter mvarAdapter;
    public RadioGroup.LayoutParams params_rb;
    public RadioGroup hourButtonLayout;
    public RadioGroup mGoup;
    private int xcount = 0;
    private int[] seItem;
    private String[] myStrings = {};
    private JSONArray jVariable = new JSONArray();
    private HashMap<Integer, String> siemrk = new HashMap<Integer, String>();
    private ArrayList<String> mGroups = new ArrayList<String>();
    public static Map<String, String> timgHas = new HashMap<>();


    private HashMap<String, ArrayList<NamesTopup>> tsMainIt = new HashMap<String, ArrayList<NamesTopup>>();
    private ArrayList<NamesTopup> mItems = new ArrayList<NamesTopup>();
    private HashMap<String, JSONObject> txtTs = new HashMap<String, JSONObject>();
    public CoolPriceView txtPrice;
    private EditText txEdit;
    private TextView addMen, subMen;
    private int idC = 0;
    private ArrayList<JSONObject> ts = new ArrayList<JSONObject>();
    private Bitmap bmp;
    private ProgressDialog pdDilog;
    private HomeKeyLocker mHomeKeyLocker;
    private double price = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.variable_new);

        hourButtonLayout = (RadioGroup) findViewById(R.id.hour_radio_group);
        mVariable = (RecyclerView) findViewById(R.id.addvae);
        mGoup = (RadioGroup) findViewById(R.id.mgoup);
        mVariable.setHasFixedSize(true);
        mVarManger = new GridLayoutManager(this, 2);
        mVariable.setLayoutManager(mVarManger);
        pdDilog = new ProgressDialog(this);
        pdDilog.setMessage("Loding Data.....");
        pdDilog.setCancelable(false);
        pdDilog.setIndeterminate(true);
        seItem = getIntent().getIntArrayExtra("data");
        myStrings = getIntent().getStringArrayExtra("strings");
        txEdit = (EditText) findViewById(R.id.miqty);
        addMen = (TextView) findViewById(R.id.newww);
        subMen = (TextView) findViewById(R.id.remv_var);
        xcount = Integer.parseInt(txEdit.getText().toString());
        Utils.removeJsonSharedPreferences(this, "RemarksVariab");
        Utils.removeJsonSharedPreferences(this, "mkeit");
        timgHas = Utils.loadMap(VariableChooseActivity.this, "Imrgcvx");

        pdDilog.show();
//        mHomeKeyLocker = new HomeKeyLocker();
//        mHomeKeyLocker.lock(this);
        //thread1.start();
        new gViewLayout().execute();

        //al = Utils.readList(VariableChooseActivity.this, "Main_Cat");
        ((TextView) findViewById(R.id.txt12)).setText(myStrings[0]);
        txtPrice = (CoolPriceView) findViewById(R.id.txt13);
        txtPrice.setPrice(Double.parseDouble((myStrings[1])));
        price = Double.parseDouble((myStrings[1]));
        txtPrice.setStyle(16.0f, 14.0f, 14.0f, this.getResources().getColor(R.color.tgraddy), this.getResources().getColor(R.color.tgraddy), this.getResources().getColor(R.color.tgraddy));

        //byte[] byteArray = getIntent().getByteArrayExtra("image");

        //bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
//        BitmapDrawable ob = new BitmapDrawable(getResources(), bmp);
//        ((ImageView) findViewById(R.id.imger)).setBackground(ob);
//        findViewById(R.id.imger).setTransitionName("THIA");

        Utils.saveint(VariableChooseActivity.this, 1, "noCust");
        hourButtonLayout.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup rGroup, int checkedId) {
                try {
                    RadioButton checkedRadioButton = (RadioButton) rGroup.findViewById(checkedId);
                    boolean isChecked = checkedRadioButton.isChecked();


                    if (isChecked) {
                        cHk(checkedRadioButton.getId());
                    }
                } catch (Exception e) {
                    Log.d("Exce", e.getMessage());
                }
                try {

                    try {
                        if (myStrings[2].equalsIgnoreCase("editview")) {

                            JSONArray jsArryfor = Utils.loadJSONArray(VariableChooseActivity.this, "totalArry", "" + 1);
                            JSONArray jdss = new JSONArray();
                            Log.d("textttt", "" + seItem[0] + "ccc" + seItem[1]);
                            jdss = (jsArryfor.getJSONObject(seItem[2])).getJSONArray("variable");
                            Log.d("********sssjd", "" + jdss);

                            Utils.saveJSONObject(VariableChooseActivity.this, "mkeit", mGroups.get(checkedId) + "_" + mGoup.getCheckedRadioButtonId(), jdss.getJSONObject(checkedId));
                            JSONObject jd = Utils.loadJSONObject(VariableChooseActivity.this, "mkeit", mGroups.get(checkedId) + "_" + mGoup.getCheckedRadioButtonId());
                            Log.d("********jd", "" + jd);

                        }
                    } catch (Exception e) {

                    }


                    mvarAdapter = new MyAdapter(VariableChooseActivity.this, mItems, mGroups.get(checkedId), 0, mGoup.getCheckedRadioButtonId());
                    mVariable.setAdapter(mvarAdapter);
                } catch (Exception e) {

                }


            }
        });
//

        for (int i = 0; i < Integer.parseInt(txEdit.getText().toString()); i++) {
            addRadioButtons(0, i);
        }

        mGoup.check(idC);

        mGoup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup rGroup, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton) rGroup.findViewById(checkedId);
                boolean isChecked = checkedRadioButton.isChecked();

                if (isChecked) {
                    idC = checkedRadioButton.getId();
                    meth(hourButtonLayout.getCheckedRadioButtonId());
////                    mTocall(mGoup.getCheckedRadioButtonId());
////                    mItemAdapter = new VariItemAdapter(VariableChooseActivity.this, mGroups, posTo);
////                    mItemRecycle.setAdapter(mItemAdapter);
//                    mvarAdapter = new MyAdapter(VariableChooseActivity.this, mItems, mGroups.get(checkedId), 0, mGoup.getCheckedRadioButtonId());
//                    mVariable.setAdapter(mvarAdapter);
                }
            }
        });

        ((TextView) findViewById(R.id.newww)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int xc = Integer.parseInt(txEdit.getText().toString()) + 1;
                txEdit.setText("" + xc);
                int x = mGoup.getCheckedRadioButtonId();
                mGoup.removeAllViews();
                for (int ix = 0; ix < xc; ix++) {
                    addRadioButtons((xc - 1), ix);

                }

                ((HorizontalScrollView) findViewById(R.id.hsr)).postDelayed(new Runnable() {
                    public void run() {
                        ((HorizontalScrollView) findViewById(R.id.hsr)).fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                    }
                }, 100L);
                mGoup.check(x);
                Utils.saveint(VariableChooseActivity.this, xc, "noCust");
            }

        });


        ((TextView) findViewById(R.id.remv_var)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int xct = Integer.parseInt(txEdit.getText().toString()) - 1;
                if (xct <= 1) {
                    xct = 1;
                }
                txEdit.setText("" + xct);
//                if (Utils.getint(VariableChooseActivity.this, "noCust") > 1)
//                    Utils.saveint(VariableChooseActivity.this, (Utils.getint(VariableChooseActivity.this, "noCust") - 1), "noCust");
//
                int k = mGoup.getCheckedRadioButtonId();
//                Log.d("pos", "" + k);
                int x = mGoup.getCheckedRadioButtonId();
                mGoup.removeAllViews();
                for (int ix = 0; ix < xct; ix++) {
                    addRadioButtons((xct - 1), ix);

                }
                Log.d("iddd", "" + idC);
                mGoup.check(x);
                /// xcount+=1;
                Utils.saveint(VariableChooseActivity.this, xct, "noCust");

                ((HorizontalScrollView) findViewById(R.id.hsr)).postDelayed(new Runnable() {
                    public void run() {
                        ((HorizontalScrollView) findViewById(R.id.hsr)).fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                    }
                }, 100L);

            }

        });

        ((Button) findViewById(R.id.addOrdere)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONArray jsdttttt = new JSONArray();
                    Log.d("getChildCount", "" + mGoup.getChildCount());

//                    mGoup-> no of customer
//                      mGroups-> no of items

//


                    for (int y = 0; y < mGoup.getChildCount(); y++) {
                        JSONArray jsm = new JSONArray();


                        JSONObject jsADD = new JSONObject();
                        ArrayList<Integer> xctt = new ArrayList<Integer>();
                        //if (ivAdd.get(y) != null) {
                        Log.d("this size", "" + mGroups.size());
                        for (int i = 0; i < mGroups.size(); i++) {
                            if (!Utils.loadJSONObject(VariableChooseActivity.this, "mkeit", (mGroups.get(i) + "_" + y)).toString().equals("{}")) {
                                Log.d("TAG", "toggleSelection  " + Utils.loadJSONObject(VariableChooseActivity.this, "mkeit", mGroups.get(i) + "_" + y));
                                jsm.put(Utils.loadJSONObject(VariableChooseActivity.this, "mkeit", mGroups.get(i) + "_" + y));

                            } else {
                                HashMap<String, ArrayList<String>> aHash = new HashMap<String, ArrayList<String>>();
                                Log.d("TAG", "mgroups" + (mGroups.get(i) + "___postion" + i));

                                ArrayList<NamesTopup> mIems = new ArrayList<NamesTopup>();
                                mIems = tsMainIt.get((mGroups.get(i)));
                                for (int t = 0; t < mIems.size(); t++) {
                                    NamesTopup natureddd = (mIems.get(t));
                                    Log.d("TAG", "dddddddddd" + natureddd.getName());
                                    if (natureddd.getcdef_flag().equalsIgnoreCase("M")) {
                                        ArrayList<String> editTextLi = new ArrayList<String>();
                                        NamesTopup nature = (mIems.get(t));
                                        editTextLi.add(nature.getName());
                                        editTextLi.add(nature.getPrice());
                                        editTextLi.add(nature.getcitem_no());
                                        editTextLi.add(nature.getcseq_no());

                                        editTextLi.add("1");
                                        editTextLi.add(nature.getcuom());
                                        editTextLi.add("");
                                        aHash.put("" + y, editTextLi);
                                        JSONObject jdObj = new JSONObject(aHash);
                                        jsm.put(jdObj);
                                        Log.d("TAG", "___postionjsm" + jsm);
                                    }
                                }


                            }

                            Log.d("TAG", "t " + jsm.length() + "___postion" + i);
                        }

                        //if (jsm.length()  >= mGroups.size()) {


                        Log.d("TAG", "toggleSelectionwww  " + seItem);
                        if (jsm.length() != 0)
                            Utils.saveJSONArray(VariableChooseActivity.this, "variitem", "" + y, jsm);
                        Log.d("$$$$$$", "" + Utils.loadJSONArray(VariableChooseActivity.this, "variitem", "" + y));
                        jsADD = new JSONObject();
                        jsADD.put(Utils.mPar, seItem[0]);
                        jsADD.put(Utils.subPar, seItem[1]);
                        jsADD.put(Utils.qtyPar, 1);

                        if (Utils.getPString(getApplicationContext(), "rrrrTTR", "rrrrTTR" + y + "1") != null)
                            jsADD.put(Utils.remPar, Utils.getPString(getApplicationContext(), "rrrrTTR", "rrrrTTR" + Utils.cusOn + "1"));
                        else {
                            jsADD.put(Utils.remPar, "");

                        }
                        // jsADD.put("remark_edit", itmRemark.getText().toString());
                        jsADD.put(Utils.varPar, Utils.loadJSONArray(VariableChooseActivity.this, "variitem", "" + y));
                        jsdttttt.put(jsADD);

                        //} else {


                        if (y == (mGoup.getChildCount() - 1)) {



                            if (Utils.loadJSONArray(VariableChooseActivity.this, "totalArry", "" + 1).equals("[]")) {
                                Utils.posTo += (0 + mGoup.getChildCount());
                                Utils.saveJSONArray(VariableChooseActivity.this, "totalArry", "" + 1, jsdttttt);
                                Log.e("@@@@@@@@33", "" + Utils.loadJSONArray(VariableChooseActivity.this, "totalArry", "" + 1));
                            } else {


                                int xxxT = 0;
                                JSONArray jsw = new JSONArray();
                                jsw = Utils.loadJSONArray(VariableChooseActivity.this, "totalArry", "" + 1);

                                Log.d("dddddd", "" + jsdttttt.length());




                                if(myStrings.length!=2){

                                    for (int i = 0; i < jsw.length(); i++) {
                                        //if (js.toString().contains("\"item_id\":" + Integer.parseInt(eSoon[0].toString()) ))
                                        if ((jsw.getJSONObject(i).getString("main").equalsIgnoreCase("" + seItem[0])) && jsw.getJSONObject(i).getString("subcat").equalsIgnoreCase("" + seItem[1])) {
                                            xxxT = 1;
                                            for (int w = 0; w < jsdttttt.length(); w++) {
                                                jsw.put(i, jsdttttt.getJSONObject(w));
                                            }


                                            Utils.saveJSONArray(VariableChooseActivity.this, "totalArry", "" + 1, jsw);
                                            break;
                                            //[{"main":"3","subcat":"0","qty":"1","remark":""}]
                                        }
                                    }
                                } else {
                                    Utils.posTo += (0 + mGoup.getChildCount());
                                    for (int w = 0; w < jsdttttt.length(); w++) {
                                        jsw.put(jsdttttt.getJSONObject(w));
                                    }

                                    Utils.saveJSONArray(VariableChooseActivity.this, "totalArry", "" + 1, jsw);
                                    Log.e("@@@@@@@@33331", "" + Utils.loadJSONArray(VariableChooseActivity.this, "totalArry", "" + 1));

                                }





                            }

                            Utils.removeJsonSharedPreferences(VariableChooseActivity.this, "remarksArry");
                            Utils.removeJsonSharedPreferences(VariableChooseActivity.this, "ArrVariable");
                            Utils.removeJsonSharedPreferences(VariableChooseActivity.this, "variitem");
//                            Intent ii = new Intent(VariableChooseActivity.this, HomeBaseActivity.class);
//                           // ii.putExtra("nextLev", 3);
//                            startActivity(ii);

                            finish();
                        }


//                        } else {
//
//                            Log.d("TAG", "to222  " + jsm);
//                           // jsm=new JSONArray();
//                            //Toast.makeText(VariableChooseActivity.this,  + (y + 1), Toast.LENGTH_SHORT);
//                            Toast toast = Toast.makeText(VariableChooseActivity.this, getString(R.string.select) + (mGroups.size()) + " Course Set Items for the Customer" + (y + 1), Toast.LENGTH_SHORT);
//                            toast.setGravity(Gravity.CENTER, 0, 0);
//                            toast.show();
//
//                            break;
//
//
//                            //}
//
//                        }

                    }


                } catch (JSONException es) {
                    Log.e("@@@@@@@@33331", "eeeee");
                }
                // finish();
            }
        });
        ((ImageView) findViewById(R.id.tclose)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
//                    Utils.removeJsonSharedPreferences(VariableChooseActivity.this, "variitem");
//                    Utils.removeJsonSharedPreferences(VariableChooseActivity.this, "mkeit");
                    finish();
                } catch (Exception es) {

                }
                // finish();
            }
        });

    }

    public void priceChange() {
//
//        double tst=Double.parseDouble(txtprice.gett)
//
//        txtPrice.setTex
    }


    private void rdBtn(String name, int i) {
        RadioButton button = new RadioButton(this);
        button.setId(i);
        button.setText(name);
        button.setGravity(Gravity.CENTER);
        button.setTextColor(getResources().getColor(R.color.text_secondary));
        button.setTextSize(14);
        button.setSingleLine(true);
        button.setPadding(10, 5, 5, 0);
        button.setButtonDrawable(new StateListDrawable());
        button.setTypeface(null, Typeface.BOLD_ITALIC);
        params_rb = new RadioGroup.LayoutParams(
                500, ViewGroup.LayoutParams.FILL_PARENT);
        params_rb.setMargins(0, 15, 20, 15);
        button.setLayoutParams(params_rb);
        hourButtonLayout.addView(button);
    }

    private void cHk(int id) {
        for (int i = 0; i < mGroups.size(); i++) {
            if (i == id) {
                hourButtonLayout.getChildAt(id).setBackgroundColor(getResources().getColor(R.color.ind));
                ((RadioButton) hourButtonLayout.getChildAt(id)).setTextColor(getResources().getColor(R.color.white));
                //  ((TextView) findViewById(R.id._tdd)).setText(((RadioButton) hourButtonLayout.getChildAt(id)).getText());
            } else {
                hourButtonLayout.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.tgraddy));

                ((RadioButton) hourButtonLayout.getChildAt(i)).setTextColor(getResources().getColor(R.color.white));


            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);

        activityManager.moveTaskToFront(getTaskId(), 0);
    }


    public class gViewLayout extends AsyncTask<String, Void, HashMap<String, Integer>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            icon = BitmapFactory.decodeResource(getResources(),
//                    R.drawable.se);

            //icon = Utils.decodeSampledBitmapFromResource(getResources(), R.drawable.noimg, 100, 100);
            pdDilog.show();

        }

        @Override
        protected HashMap<String, Integer> doInBackground(String... urls) {
            HashMap<String, Integer> yxyy = new HashMap<String, Integer>();

            byte[] decodedString;
            try {
                JSONArray inoArry = new JSONArray();
                mItems = new ArrayList<NamesTopup>();
                NamesTopup species;
                siemrk = new HashMap<Integer, String>();
//                try {
//                    if (myStrings[2].equalsIgnoreCase("editview")) {
//                        JSONArray jsArryfor = Utils.loadJSONArray(VariableChooseActivity.this, "totalArry", "" + 1);
//                        Log.d("122222dddddd2", "" + jsArryfor);
//                        int main_pos = Integer.parseInt(jsArryfor.getJSONObject(seItem[0]).getString(Utils.mPar));
//                        int second_pos = Integer.parseInt(jsArryfor.getJSONObject(seItem[0]).getString(Utils.subPar));
//                        int Qty = Integer.parseInt(jsArryfor.getJSONObject(seItem[0]).getString(Utils.qtyPar));
//                        seItem = new int[]{main_pos, second_pos};
//
//                    }
//                } catch (Exception e) {
//
//                }


                jVariable = Utils.loadJSONArray(VariableChooseActivity.this, "basha", "1");
                inoArry = jVariable.getJSONArray(seItem[0]);
                Log.d("siemrk111inoArry", "......" + seItem[0] + "fffffffff" + inoArry);
                if (inoArry.getJSONObject(seItem[1]).has("variable_item")) {
                    int lengti = inoArry.getJSONObject(seItem[1]).getJSONArray("variable_item").length();
                    Log.d("lengti", "" + lengti);
                    for (int i = 0; i < lengti; i++) {
                        JSONObject jsd = inoArry.getJSONObject(seItem[1]).getJSONArray("variable_item").getJSONObject(i);
                        mGroups.add(jsd.getString("cgroup"));
                        siemrk.put(Integer.parseInt(jsd.getString("cseq_no")), jsd.getString("cgroup"));
                        Log.d("siemrk111", "......" + siemrk);
                        species = new NamesTopup();
                        Double value = Double.parseDouble(jsd.getString("cprice"));
                        species.setName(jsd.getString("citem_desc"));
                        species.setcitem_no(jsd.getString("citem_id"));
                        Log.d("citem id", jsd.getString("citem_id"));
                        species.setThumbnail(null);
                        species.setcseq_no(jsd.getString("cseq_no"));
                        //species.setcseq_no(jsd.getString("cgseq_no"));
                        species.setcdef_flag(jsd.getString("cdef_flag"));
                        species.setnoCunt(jsd.getString("grouplimit"));

                        species.setcuom(jsd.getString("cuom"));
                        species.setPrice(String.format("%.2f", value));
                        species.setgrpName(jsd.getString("cgroup"));


                        if (jsd.getString("cdef_flag").equalsIgnoreCase("M")) {
                            HashMap<String, ArrayList<String>> aHash = new HashMap<String, ArrayList<String>>();
                            ArrayList<String> editTextLi = new ArrayList<String>();
                            editTextLi.add(species.getName());
                            editTextLi.add(species.getPrice());
                            editTextLi.add(species.getcitem_no());
                            editTextLi.add(species.getcseq_no());
                            editTextLi.add("" + 1);
                            editTextLi.add(species.getcuom());
                            editTextLi.add("");
                            aHash.put("" + i, editTextLi);
                            JSONObject jdObj = new JSONObject(aHash);
                            Log.d("@jdObjVariable", "" + jdObj);

                            //(context, "mkeit", cmpAdd + "_" + custID, jdObj);
                            Log.d("cmapp", "" + jdObj);

                            txtTs.put(jsd.getString("cgroup"), jdObj);
                            //ts.add(jdObj);


                            //

                        }

                        Log.d("cmappts", "" + ts);
                        mItems.add(species);
                    }
                    Set<String> valueSet = new HashSet<String>(siemrk.values());
                    Iterator<String> it = valueSet.iterator();

                    Map<Integer, String> uniqueMap = new HashMap<Integer, String>();
                    while (it.hasNext()) {
                        String value = it.next();
                        for (Map.Entry<Integer, String> e : siemrk.entrySet()) {
                            if (value.equals(e.getValue()) && !uniqueMap.containsValue(value)) {
                                uniqueMap.put(e.getKey(), value);
                            }
                        }
                    }
                    System.out.println("xxxxxx" + uniqueMap);
                    Log.d("siemrk", "......" + uniqueMap);
                    ArrayList<Integer> its = new ArrayList<Integer>();
                    for (Map.Entry<Integer, String> entry : uniqueMap.entrySet()) {
                        System.out.println("Item is:" + entry.getKey() + " with value:" +
                                entry.getValue());
                        its.add(entry.getKey());
                    }
                    Collections.sort(its);
                    Log.d("si its", "......" + its.size());
                    mGroups = new ArrayList<String>();
                    for (int i = 0; i < its.size(); i++) {
                        mGroups.add(uniqueMap.get(its.get(i)));
                        Log.d("DoLog.droundmGroups", "......" + mGroups.size());


                        for (int ke = 0; ke < mGroups.size(); ke++) {
                            ArrayList<NamesTopup> tsMan = new ArrayList<NamesTopup>();

                            for (int w = 0; w < mItems.size(); w++) {


                                NamesTopup natureddd = (mItems.get(w));
                                Log.d("TAG", "dddddddddd" + natureddd.getName());
                                if (natureddd.getgrpName().equalsIgnoreCase(mGroups.get(ke))) {
                                    tsMan.add(natureddd);
                                }


                            }
                            tsMainIt.put(mGroups.get(ke), tsMan);


                        }


                    }
//


                } else {
                    //lin2.setVisibility(View.GONE);
                    Log.d("DoLog.dround", "......" + mItems.size());
                    Log.d("variable_item", "No variable_item");


                }
            } catch (Exception ex) {
                Log.d("Errorrr", "" + Utils.TtlItem.size());
            }

            return yxyy;
        }

        @Override
        protected void onPostExecute(HashMap<String, Integer> result) {

            try {
                if ((pdDilog != null) && pdDilog.isShowing()) {
                    pdDilog.dismiss();
                }

                if (mItems.size() == 0) {
                    finish();
                } else {
                    for (int u = 0; u < mGroups.size(); u++) {
                        rdBtn(mGroups.get(u).toString(), u);
                    }
                    meth(0);
                }


            } catch (final IllegalArgumentException e) {
                // Handle or log or ignore
            }
            //pdDilog.dismiss();


        }
    }


//    Thread thread1 = new Thread() {
//        public void run() {
//            JSONArray inoArry = new JSONArray();
//            try {
//
//                //  Log.d(TAG, "No variable_item" + mItems);
//
//            } catch (Exception je) {
//                Log.d("DoLog.droundjee", "......" + je.getMessage());
//                //pdDilog.dismiss();
//                thread1.interrupt();
//            }
//
//
//            hourButtonLayout.post(new Runnable() {
//                public void run() {
//                    pdDilog.dismiss();
//                    for (int u = 0; u < mGroups.size(); u++) {
//                        rdBtn(mGroups.get(u).toString(), u);
//                    }
//                    meth(0);
//
//                }
//            });
//
//            thread1.interrupt();
//
//
//        }
//    };

    public void addRadioButtons(int number, int selec) {
        RadioButton rdbtn = new RadioButton(this);
        rdbtn.setId(selec);
        rdbtn.setGravity(Gravity.CENTER | Gravity.LEFT);
        rdbtn.setTextColor(getResources().getColor(R.color.tgraddy));
        rdbtn.setTextSize(20);
        rdbtn.setSingleLine(true);
        rdbtn.setPadding(20, 20, 20, 20);
        rdbtn.setCompoundDrawablePadding(10);
        rdbtn.setText("GUEST" + (selec + 1));
        rdbtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.radio_img, 0, 0, 0);
        rdbtn.setButtonDrawable(new StateListDrawable());
        mGoup.addView(rdbtn);


    }
//
//    @Override
//    protected void onResume() {
//        if (mvarAdapter != null)
//            mvarAdapter.notifyDataSetChanged();
//
//        Log.d("
//
// ", "No variable_item");
//        super.onResume();
//    }

    private void meth(int chck) {
        hourButtonLayout.check(chck);
        cHk(hourButtonLayout.getCheckedRadioButtonId());

        // {"main":4,"subcat":0,"qty":1,"remark":"","variable":[{"0":["Baby Spinach And Quinoa Salad","15.90","46","1","1","set",""]},{"0":["Avocado (1.50)","1.50","327","2","1","SER",""]}]}]


        mvarAdapter = new MyAdapter(VariableChooseActivity.this, mItems, mGroups.get(chck), 0, mGoup.getCheckedRadioButtonId());
        mVariable.setAdapter(mvarAdapter);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        timgHas = null;
//        mHomeKeyLocker.unlock();
//        mHomeKeyLocker = null;
    }
}