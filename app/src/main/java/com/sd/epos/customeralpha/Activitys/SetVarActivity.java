package com.sd.epos.customeralpha.Activitys;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
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
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sd.epos.customeralpha.Adapter.StAdapter;
import com.sd.epos.customeralpha.Models.SetDataTopup;
import com.sd.epos.customeralpha.R;
import com.sd.epos.customeralpha.common.CoolPriceView;
import com.sd.epos.customeralpha.common.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jabbir on 15/10/15.
 */
public class SetVarActivity extends Activity {
    public RecyclerView msetVar;
    public RecyclerView.LayoutManager mVarManger;
    public RecyclerView.Adapter mvarAdapter;
    public RadioGroup.LayoutParams params_rb;

    public RadioGroup mGoup;
    private int xcount = 0;
    private int[] seItem;
    private String[] myStrings = {};
    private JSONArray jVariable = new JSONArray();
    private HashMap<Integer, String> siemrk = new HashMap<Integer, String>();
    private ArrayList<String> mGroups = new ArrayList<String>();
    private ArrayList<SetDataTopup> mItems = new ArrayList<SetDataTopup>();
    private HashMap<String, JSONObject> txtTs = new HashMap<String, JSONObject>();
    public CoolPriceView txtPrice;
    private EditText txEdit;
    private TextView addMen, subMen;
    private int idC = 0;
    private Map<String, String> timgHas = new HashMap<>();
    private ArrayList<JSONObject> ts = new ArrayList<JSONObject>();
    private Bitmap bmp;
    private ProgressDialog pdDilog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_new);
        getWindow().setLayout(1800, 1400);

        msetVar = (RecyclerView) findViewById(R.id.setmeal);
        mGoup = (RadioGroup) findViewById(R.id.mgoup);
        msetVar.setHasFixedSize(true);
        mVarManger = new GridLayoutManager(this, 2);
        msetVar.setLayoutManager(mVarManger);
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
        timgHas = Utils.loadMap(SetVarActivity.this, "pageImages");
        pdDilog.show();
//        con = BitmapFactory.decodeResource(getResources(),
//                R.drawable.cbim);
        thread1.start();

        //al = Utils.readList(VariableChooseActivity.this, "Main_Cat");
        ((TextView) findViewById(R.id.txt12)).setText(myStrings[0]);
        txtPrice = (CoolPriceView) findViewById(R.id.txt13);
        txtPrice.setPrice(Double.parseDouble((myStrings[1])));
        txtPrice.setStyle(26.0f, 18.0f, 18.0f, this.getResources().getColor(R.color.gold), this.getResources().getColor(R.color.gold), this.getResources().getColor(R.color.white));

        byte[] byteArray = getIntent().getByteArrayExtra("image");

        bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        BitmapDrawable ob = new BitmapDrawable(getResources(), bmp);
        ((ImageView) findViewById(R.id.imger)).setBackground(ob);
        findViewById(R.id.imger).setTransitionName("THIA");

        Utils.saveint(SetVarActivity.this, 1, "noCust");
//        hourButtonLayout.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            public void onCheckedChanged(RadioGroup rGroup, int checkedId) {
//                RadioButton checkedRadioButton = (RadioButton) rGroup.findViewById(checkedId);
//                boolean isChecked = checkedRadioButton.isChecked();
//                if (isChecked) {
//                    cHk(checkedRadioButton.getId());
//                }
//
//                mvarAdapter = new MyAdapter(SetVarActivity.this, mItems, mGroups.get(checkedId), 0, mGoup.getCheckedRadioButtonId());
//                msetVar.setAdapter(mvarAdapter);
//
//
//            }
//        });
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
                    // meth(hourButtonLayout.getCheckedRadioButtonId());
////                    mTocall(mGoup.getCheckedRadioButtonId());
////                    mItemAdapter = new VariItemAdapter(VariableChooseActivity.this, mGroups, posTo);
////                    mItemRecycle.setAdapter(mItemAdapter);
//                    mvarAdapter = new MyAdapter(VariableChooseActivity.this, mItems, mGroups.get(checkedId), 0, mGoup.getCheckedRadioButtonId());
//                    msetVar.setAdapter(mvarAdapter);
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
                Utils.saveint(SetVarActivity.this, xc, "noCust");
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
                Utils.saveint(SetVarActivity.this, xct, "noCust");

                ((HorizontalScrollView) findViewById(R.id.hsr)).postDelayed(new Runnable() {
                    public void run() {
                        ((HorizontalScrollView) findViewById(R.id.hsr)).fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                    }
                }, 100L);

            }

        });

        ((TextView) findViewById(R.id.addOrdere)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONArray jsdttttt = new JSONArray();
                    Log.d("getChildCount", "" + mGoup.getChildCount());

                    for (int y = 0; y < mGoup.getChildCount(); y++) {
                        JSONArray jsm = new JSONArray();


                        JSONObject jsADD = new JSONObject();
                        //if (ivAdd.get(y) != null) {

                            if (!Utils.loadJSONObject(SetVarActivity.this, "mkeit", ("setitem" + "_" + y)).toString().equals("{}")) {
                                Log.d("TAG", "toggleSelection  " + Utils.loadJSONObject(SetVarActivity.this, "mkeit", "setitem"  + "_" + y));
                                jsm.put(Utils.loadJSONObject(SetVarActivity.this, "mkeit", "setitem"  + "_" + y));

                            }
                            Log.d("TAG", "t " + jsm.length() + "___postion" + y);

//                        if (jsm.length() != mGroups.size()) {
//                            Log.d("TAG", "to222  " + jsm);
//                            //Toast.makeText(VariableChooseActivity.this,  + (y + 1), Toast.LENGTH_SHORT);
//                            Toast toast = Toast.makeText(SetVarActivity.this, getString(R.string.select) + (mGroups.size() - 1) + " Course Set Items for the Customer" + (y + 1), Toast.LENGTH_SHORT);
//                            toast.setGravity(Gravity.CENTER, 0, 0);
//                            toast.show();
//
//                            break;
//                        } else {
                            Utils.posTo += (0 + 1);
                            Log.d("TAG", "toggleSelectionwww  " + seItem);
                            if (jsm.length() != 0)
                                Utils.saveJSONArray(SetVarActivity.this, "variitem", "" + y, jsm);
                            Log.d("$$$$$$", "" + Utils.loadJSONArray(SetVarActivity.this, "variitem", "" + y));
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
                            jsADD.put(Utils.setitem, Utils.loadJSONArray(SetVarActivity.this, "variitem", "" + y));
                            jsdttttt.put(jsADD);
                            if (Utils.loadJSONArray(SetVarActivity.this, "totalArry", "" + Utils.cusOn).equals("[]")) {
                                Utils.saveJSONArray(SetVarActivity.this, "totalArry", "" + Utils.cusOn, jsdttttt);
                                Log.e("@@@@@@@@33", "" + Utils.loadJSONArray(SetVarActivity.this, "totalArry", "" + Utils.cusOn));
                            } else {
                                JSONArray jsw = new JSONArray();
                                jsw = Utils.loadJSONArray(SetVarActivity.this, "totalArry", "" + Utils.cusOn);
                                jsw.put(jsADD);
                                Utils.saveJSONArray(SetVarActivity.this, "totalArry", "" + Utils.cusOn, jsw);
                                Log.e("@@@@@@@@33331", "" + Utils.loadJSONArray(SetVarActivity.this, "totalArry", "" + Utils.cusOn));

                            }
                            //} else {

                            Utils.removeJsonSharedPreferences(SetVarActivity.this, "remarksArry");
                            Utils.removeJsonSharedPreferences(SetVarActivity.this, "ArrVariable");
                            Utils.removeJsonSharedPreferences(SetVarActivity.this, "variitem");
                            finish();
                            //}



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

//    private void rdBtn(String name, int i) {
//        RadioButton button = new RadioButton(this);
//        button.setId(i);
//        button.setText(name);
//        button.setGravity(Gravity.CENTER);
//        button.setTextColor(getResources().getColor(R.color.text_secondary));
//        button.setTextSize(16);
//        button.setSingleLine(true);
//        button.setPadding(10, 5, 5, 0);
//        button.setButtonDrawable(new StateListDrawable());
//        button.setTypeface(null, Typeface.BOLD_ITALIC);
//        params_rb = new RadioGroup.LayoutParams(
//                300, ViewGroup.LayoutParams.FILL_PARENT);
//        params_rb.setMargins(0, 5, 10, 5);
//        button.setLayoutParams(params_rb);
//        hourButtonLayout.addView(button);
//    }

    //    private void cHk(int id) {
//        for (int i = 0; i < mGroups.size(); i++) {
//            if (i == id) {
//                hourButtonLayout.getChildAt(id).setBackgroundColor(getResources().getColor(R.color.black_overlay));
//                ((RadioButton) hourButtonLayout.getChildAt(id)).setTextColor(getResources().getColor(R.color.gold));
//            } else {
//                hourButtonLayout.getChildAt(i).setBackgroundColor(getResources().getColor(android.R.color.transparent));
//                ((RadioButton) hourButtonLayout.getChildAt(i)).setTextColor(getResources().getColor(R.color.white));
//
//
//            }
//        }
//    }
    @Override
    protected void onPause() {
        super.onPause();

        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);

        activityManager.moveTaskToFront(getTaskId(), 0);
    }

    Thread thread1 = new Thread() {
        public void run() {
            JSONArray inoArry = new JSONArray();
            try {

                mItems = new ArrayList<SetDataTopup>();
                SetDataTopup species;
                siemrk = new HashMap<Integer, String>();
                jVariable = Utils.loadJSONArray(SetVarActivity.this, "basha", "1");
                inoArry = jVariable.getJSONArray(seItem[0]);
                Log.d("siemrk111inoArry", "......" + seItem[0] + "____" + seItem[1]);
                if (inoArry.getJSONObject(seItem[1]).has("set_item")) {
                    int lengti = inoArry.getJSONObject(seItem[1]).getJSONArray("set_item").length();
                    Log.d("lengti", "" + lengti);
                    for (int i = 0; i < lengti; i++) {
                        JSONObject jsd = inoArry.getJSONObject(seItem[1]).getJSONArray("set_item").getJSONObject(i);
                        Log.d("siemrk111", "......" + jsd);
                        species = new SetDataTopup();
                        species.setcitem_desc(jsd.getString("citem_desc"));
                        species.setcitem_no(jsd.getString("citem_no"));
                        species.setcitem_id(jsd.getString("citem_id"));
//                        if (jsd.getString("citem_id").matches("" + 2) || timgHas.get(jsd.getString("citem_id")).matches("") || timgHas.get(jsd.getString("citem_id")) == null) {
//                            species.setThumbnail(BitmapFactory.decodeResource(getResources(),
//                                    R.drawable.cbim));
//                        } else {
//                            Bitmap bitmap = Utils.decodeBase64(
//
// .get(jsd.getString("citem_id")));
//                            species.setThumbnail(bitmap);
//                        }
//                        Bitmap bitmap = Utils.decodeBase64(timgHas.get(jsd.getString("citem_id")));
//                        if (bitmap == null) {
//                            species.setThumbnail(con);
//                        } else {
//                            species.setThumbnail(bitmap);
//                        }
                        species.setThumbnail(Utils.tImages.get(jsd.getString("citem_id")));
                        species.setcuom(jsd.getString("cuom"));
                        Double cf_qty = Double.parseDouble(jsd.getString("cf_qty"));
                        Double cqty = Double.parseDouble(jsd.getString("cqty"));
                        species.setcqty(String.format("%.2f", cqty));
                        species.setcf_qty(String.format("%.2f", cf_qty));
                        mItems.add(species);
                    }

                } else {
                    //lin2.setVisibility(View.GONE);
                    Log.d("DoLog.dround", "......" + mItems.size());
                    Log.d("set_item", "No set_item");

                }
                //  Log.d(TAG, "No set_item" + mItems);

            } catch (JSONException je) {

            }

            mGoup.post(new Runnable() {
                public void run() {
                    pdDilog.dismiss();

                    meth(0);

                }
            });
//

            thread1.interrupt();


        }
    };

    public void addRadioButtons(int number, int selec) {
        RadioButton rdbtn = new RadioButton(this);
        rdbtn.setId(selec);
        rdbtn.setGravity(Gravity.CENTER | Gravity.LEFT);
        rdbtn.setTextColor(getResources().getColor(R.color.primary));
        rdbtn.setTextSize(20);
        rdbtn.setSingleLine(true);
        rdbtn.setPadding(20, 20, 20, 20);
        rdbtn.setCompoundDrawablePadding(10);
        rdbtn.setText("G " + (selec + 1));
        rdbtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.radio_img, 0, 0, 0);
        rdbtn.setButtonDrawable(new StateListDrawable());
        mGoup.addView(rdbtn);


    }

    @Override
    protected void onResume() {
        if (mvarAdapter != null)
            mvarAdapter.notifyDataSetChanged();

        Log.d("set_itemffffff", "No set_item");
        super.onResume();
    }

    private void meth(int chck) {
//        hourButtonLayout.check(chck);
//        cHk(hourButtonLayout.getCheckedRadioButtonId());
        mvarAdapter = new StAdapter(SetVarActivity.this, mItems,"setitem", 0, mGoup.getCheckedRadioButtonId());
        msetVar.setAdapter(mvarAdapter);
    }

    @Override
    public void onBackPressed() {


        //super.onBackPressed();
    }
}