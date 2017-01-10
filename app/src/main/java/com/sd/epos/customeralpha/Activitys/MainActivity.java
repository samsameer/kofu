package com.sd.epos.customeralpha.Activitys;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.sd.epos.customeralpha.R;
import com.sd.epos.customeralpha.common.Utils;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MainActivity extends Activity {
    private com.gc.materialdesign.views.ButtonFlat connectBtn, cancelBtn;
    private int[] myArray = {R.id.edit_1, R.id.edit_2, R.id.edit_3, R.id.edit_4, R.id.edit_5, R.id.edit_6, R.id.edit_7};
    private EditText[] miBox = new EditText[myArray.length];
    private String[] serConfig = new String[myArray.length];
    private ArrayList arrayListcat = new ArrayList();
    private HashMap<String, ArrayList<String>> arrayItemid = new HashMap<String, ArrayList<String>>();
    private ArrayList arr = new ArrayList();
    private JSONArray addArray = new JSONArray();
    private JSONArray addObj = new JSONArray();
    private JSONArray totlArray = new JSONArray();
    private int[] catIndx;
    private String sd;
private JSONArray resultjs;
    private String groupCode = "";
    private int xCheck = 0;
    private HashMap<String, String> cxHash;
    private HashMap<Integer, String> mapper1 = new HashMap<Integer, String>();
    private ProgressDialog progressDialog;
    private ArrayList<String> cdI = new ArrayList<String>();
    private ArrayList<ArrayList<String>> gcdI = new ArrayList<ArrayList<String>>();
    private ArrayList<HashMap<Integer, ArrayList<JSONObject>>> response;
    private HashMap<Integer, JSONArray> mapI = new HashMap<Integer, JSONArray>();
    private ArrayList<HashMap<Integer, ArrayList<JSONObject>>> cArryList = new ArrayList<HashMap<Integer, ArrayList<JSONObject>>>();
    private HashMap<String, ArrayList<String>> xczI = new HashMap<String, ArrayList<String>>();
    private ArrayList<String> cx = new ArrayList<String>();
    private StringBuilder sBuild;
    private Map<String, String> xhaNames = new HashMap<String, String>();
    //public JSONParser jsonParser = new JSONParser();
    private JSONArray xyz;
    JSONArray tz = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data.....");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        getWindow().setLayout(1000, 1300);
        connectBtn = (com.gc.materialdesign.views.ButtonFlat) findViewById(R.id.connect);
        cancelBtn = (com.gc.materialdesign.views.ButtonFlat) findViewById(R.id.cancel);
        for (int i = 0; i < myArray.length; i++) {
            miBox[i] = (EditText) findViewById(myArray[i]);
        }
        Log.d("Utils check here", "" + Utils.loadArray("serverName", MainActivity.this).length);
        if (Utils.loadArray("serverName", MainActivity.this).length == 7) {
            serConfig = Utils.loadArray("serverName", MainActivity.this);
            for (int k = 0; k < myArray.length; k++) {
                miBox[k].setText(serConfig[k]);
            }
        } else {
            for (int k = 0; k < myArray.length; k++) {
                miBox[k].setText("");
            }
        }
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //overridePendingTransition(R.anim.nothing, R.anim.fade_in);
            }
        });

        JSONObject mainJson = new JSONObject();

        try {
            JSONArray menuprice = new JSONArray();
            menuprice = mainJson.getJSONObject("data").getJSONObject("main_menu").getJSONArray("menu_&_price");
            for (int i = 0; i < menuprice.length(); i++) {
                JSONArray jsObj1 = new JSONArray();
                jsObj1 = menuprice.getJSONObject(i).getJSONArray("sub_categories");
                for (int j = 0; j < jsObj1.length(); j++) {
                    String sub_categor = jsObj1.getJSONObject(j).getString("sub_category");
                    JSONArray Dishesh = new JSONArray();
                    Dishesh = jsObj1.getJSONObject(j).getJSONArray("dishes");
                    for (int k = 0; k < Dishesh.length(); k++) {
                        String Id = Dishesh.getJSONObject(k).getString("id");
                        String resturent_id = Dishesh.getJSONObject(k).getString("restaurant_id");
                    }

                }

            }


        } catch (Exception e) {

        }


//        connectBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //  final JSONParser client = new JSONParser(MainActivity.this, l);
//
//                for (int j = 0; j < myArray.length; j++) {
//                    if (miBox[j].getText().toString().matches("")) {
//                        //Toast.makeText(MainActivity.this, "Enter the Missing " + miBox[j].getHint().toString(), Toast.LENGTH_SHORT).show();
//                        Toast toast =Toast.makeText(MainActivity.this, "Enter the Missing " + miBox[j].getHint().toString(),Toast.LENGTH_SHORT);
//                        TextView vi = (TextView) toast.getView().findViewById(android.R.id.message);
//                        vi.setTextColor(Color.WHITE);
//                        toast.show();
//                        return;
//                    } else {
//                    }
//                }
//            }
//        });
        connectBtn.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              //  final JSONParser client = new JSONParser(MainActivity.this, l);

                                              for (int j = 0; j < myArray.length; j++) {
                                                  if (miBox[j].getText().toString().matches("")) {
                                                      //Toast.makeText(MainActivity.this, "Enter the Missing " + miBox[j].getHint().toString(), Toast.LENGTH_SHORT).show();
                                                      Utils.alertDialogShow(MainActivity.this, getString(R.string.cread), getString(R.string.aut_alert), 0);
                                                      return;


                                                  } else {
                                                      Utils.removeJsonSharedPreferences(MainActivity.this, "xhaNames");
                                                      Utils.removeJsonSharedPreferences(MainActivity.this, "pageImages");
                                                      Utils.removeJsonSharedPreferences(MainActivity.this, "pImages");
                                                      Utils.removeJsonSharedPreferences(MainActivity.this, "Main_Cat");

                                                      Utils.removeJsonSharedPreferences(MainActivity.this, "xdcs");
                                                      Utils.removeJsonSharedPreferences(MainActivity.this, "totalArry");
                                                      Utils.removeJsonSharedPreferences(MainActivity.this, "SAVED");
                                                      Utils.TtlItem.clear();


                                                      serConfig[j] = (miBox[j].getText().toString());


                                                      if (j == myArray.length - 1) {
                                                          Utils.userAuth = serConfig[2];
                                                          Utils.passAuth = serConfig[3];
                                                          Log.i("here we get string", "" + Utils.userAuth + "d" + Utils.passAuth);
                                                          //String url = serConfig[0]+":"+serConfig[1]+Utils.getitemFulldtils+"01"+serConfig[5]+"&paramgroup_code="+serConfig[5];
                                                          //:8080/kpos/getPosClientParam?compcode=01&regcode=01&outletcode=01
                                                          if (!serConfig[0].contains("http://")) {
                                                              serConfig[0] = "http://" + serConfig[0];
                                                          }


                                                          if (!Utils.isConnected(MainActivity.this)) {

//                                                              try {
//                                                                  JSONArray jsdoff = new JSONArray();
//                                                                  jsdoff = myOfflineJson(R.raw.getposclientparam);
//
//
//                                                                  Utils.compnyCd = serConfig[5];
//                                                                  if (jsdoff.getJSONObject(0).has("group_code")) {
//                                                                      Utils.saveArray(serConfig, "serverName", MainActivity.this);
//                                                                      Log.d("Utils check", "" + Utils.loadArray("serverName", MainActivity.this).length);
//
//
//                                                                      HashMap<String, String> hm = new HashMap<String, String>();
//
//                                                                      Utils.saveString(MainActivity.this, jsdoff.getJSONObject(0).getString("wh_id").toString(), "wh_id");
//
//                                                                      //  String[] xyz = {"company_name", "wh_name", "wh_id", "group_code", "tax_type","tax_per", "curr_code", "pymt_code", "header_info", "footer_info", "absorb_tax"};
//                                                                      Utils.saveJSONArray(MainActivity.this, "server", "key", jsdoff);
//                                                                      Log.d("Ucheck", "" + Utils.loadJSONArray(MainActivity.this, "server", "key"));
//                                                                      String Urdl = "";
//                                                                      groupCode = jsdoff.getJSONObject(0).getString("group_code");
//                                                                      if (!groupCode.matches("")) {
//
//                                                                          Urdl = serConfig[0] + ":" + serConfig[1] + Utils.ITEM_PAGE_DET + serConfig[5] + Utils.PARAM + groupCode;
//                                                                          Log.d("second URl", "" + Urdl);
//                                                                      } else {
//                                                                          Utils.alertDialogShow(MainActivity.this, "Error", " Connection  refused", 3);
//                                                                      }
//
//                                                                  }
//                                                              } catch (Exception e) {
//                                                              }
//                                                              try {
//
//                                                                  JSONArray btnoff = new JSONArray();
//                                                                  btnoff = myOfflineJson(R.raw.getbuttonpagewoimage);
//                                                                  Utils.saveJSONArray(MainActivity.this, "pages", "1", btnoff);
//                                                                  Log.d("pages", "" + Utils.loadJSONArray(MainActivity.this, "pages", "1"));
//
////
//                                                                  try {
//                                                                      JSONArray fullnoff = new JSONArray();
//                                                                      fullnoff = myOfflineJson(R.raw.getitemsfulldetails);
//
//                                                                      addObj = new JSONArray();
//                                                                      addObj = fullnoff;
//                                                                      Utils.saveJSONArray(MainActivity.this, "fulldetails", "1", addObj);
//                                                                      new DownloadWebPageTask().execute();
//
//
//                                                                  } catch (Exception exception) {
//                                                                      Log.d("this error", "" + exception);
//
//                                                                  }
//

//                                                              } catch (Exception e) {
//                                                              }


                                                              //Utils.alertDialogShow(MainActivity.this, getString(R.string.netw), "Netwrok status", 0);
                                                              //Toast.makeText(MainActivity.this, getString(R.string.no_network), Toast.LENGTH_SHORT).show();
                                                          } else {

                                                              String posUrl = serConfig[0] + ":" + serConfig[1] + Utils.GETPOSCLIENT + serConfig[5] + Utils.REG + serConfig[4] + Utils.OUTCODE + serConfig[6];
                                                              Log.d("First URle here ", posUrl);
                                                              // getPublicTimeline(posUrl);
                                                              //progressDialog.show();
                                                              Utils.get(posUrl, null, new JsonHttpResponseHandler() {

                                                                  @Override
                                                                  public void onStart() {
                                                                      progressDialog.show();
                                                                  }

                                                                  @Override
                                                                  public void onSuccess(int statusCode, Header[] headers, JSONArray jsonFromNet) {
                                                                      // Pull out the first event on the public timeline
                                                                      try {
                                                                          Log.d("First URle jsonFromNet ", "" + jsonFromNet);
                                                                          if (jsonFromNet.toString().equalsIgnoreCase("[]")) {
                                                                              xCheck = 1;
                                                                              // Toast.makeText(MainActivity.this, "Please fill all missing fields.", Toast.LENGTH_SHORT).show();
                                                                              Toast toast = Toast.makeText(MainActivity.this, "Fail to connect server. Please try again.", Toast.LENGTH_LONG);
                                                                              toast.setGravity(Gravity.CENTER, 0, 0);
                                                                              toast.show();

                                                                              //Utils.alertDialogShow(getApplicationContext(), "Please fill all missing fields.", "Alert", 0);
                                                                              for (int k = 0; k < myArray.length; k++) {
                                                                                  miBox[k].setText("");
                                                                              }
                                                                              serConfig = new String[myArray.length];
                                                                              Utils.removeJsonSharedPreferences(MainActivity.this, "serverName");
                                                                              Utils.userAuth = "";
                                                                              Utils.passAuth = "";
                                                                              //progressDialog.dismiss();
                                                                              return;
                                                                          } else {
                                                                              Utils.compnyCd = serConfig[5];
                                                                              if (jsonFromNet.getJSONObject(0).has("group_code")) {
                                                                                  Utils.saveArray(serConfig, "serverName", MainActivity.this);
                                                                                  Log.d("Utils check", "" + Utils.loadArray("serverName", MainActivity.this).length);


                                                                                  HashMap<String, String> hm = new HashMap<String, String>();

                                                                                  Utils.saveString(MainActivity.this, jsonFromNet.getJSONObject(0).getString("wh_id").toString(), "wh_id");

                                                                                  //  String[] xyz = {"company_name", "wh_name", "wh_id", "group_code", "tax_type","tax_per", "curr_code", "pymt_code", "header_info", "footer_info", "absorb_tax"};
                                                                                  Utils.saveJSONArray(MainActivity.this, "server", "key", jsonFromNet);
                                                                                  Log.d("Ucheck", "" + Utils.loadJSONArray(MainActivity.this, "server", "key"));
                                                                                  String Urdl = "";
                                                                                  groupCode = jsonFromNet.getJSONObject(0).getString("group_code");
                                                                                  if (!groupCode.matches("")) {

                                                                                      Urdl = serConfig[0] + ":" + serConfig[1] + Utils.ITEM_PAGE_DET + serConfig[5] + Utils.PARAM + groupCode;
                                                                                      Log.d("second URl", "" + Urdl);
                                                                                  } else {
                                                                                      Utils.alertDialogShow(MainActivity.this, "Error", " Connection  refused", 3);
                                                                                  }


                                                                                  Utils.get(Urdl, null, new JsonHttpResponseHandler() {
                                                                                      @Override
                                                                                      public void onStart() {
                                                                                          progressDialog.show();

                                                                                      }

                                                                                      @Override
                                                                                      public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                                                                                          // Pull out the first event on the public timeline
                                                                                          try {
                                                                                              if (timeline.length() == 0) {
                                                                                                  Toast toast = Toast.makeText(MainActivity.this, "Fail to connect server. Please try' again.", Toast.LENGTH_LONG);
                                                                                                  toast.setGravity(Gravity.CENTER, 0, 0);
                                                                                                  toast.show();
                                                                                                  //progressDialog.dismiss();
                                                                                                  return;

                                                                                              } else {

                                                                                                  Utils.saveJSONArray(MainActivity.this, "pages", "1", timeline);
                                                                                                  Log.d("pages", "" + Utils.loadJSONArray(MainActivity.this, "pages", "1"));
                                                                                                  String srdl = serConfig[0] + ":" + serConfig[1] + Utils.ITEM_FUL_DET + serConfig[5] + Utils.PARAM + groupCode;//groupCode;
                                                                                                  Log.d("Third URl", "" + srdl);
                                                                                                  Utils.get(srdl, null, new JsonHttpResponseHandler() {
                                                                                                      @Override
                                                                                                      public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                                                                                                          // Pull out the first event on the public timeline
                                                                                                          try {

                                                                                                              if (timeline == null && timeline.length() <= 0) {
                                                                                                                  Toast toast = Toast.makeText(MainActivity.this, "Fail to connect server. Please try again.", Toast.LENGTH_LONG);
                                                                                                                  toast.setGravity(Gravity.CENTER, 0, 0);
                                                                                                                  toast.show();
                                                                                                                  return;

                                                                                                              } else {
                                                                                                                  addObj = new JSONArray();
                                                                                                                  addObj = timeline;
                                                                                                                  Utils.saveJSONArray(MainActivity.this, "fulldetails", "1", addObj);
                                                                                                                  new DownloadWebPageTask().execute();

                                                                                                              }


                                                                                                          } catch (Exception exception) {
                                                                                                              Log.d("this error", "" + exception);

                                                                                                          }

                                                                                                      }

                                                                                                      @Override
                                                                                                      public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                                                                                          Utils.alertDialogShow(MainActivity.this, "Error", " Connection  refused", 0);
                                                                                                      }


                                                                                                      @Override
                                                                                                      public void onFinish() {
                                                                                                          progressDialog.dismiss();
                                                                                                      }
                                                                                                  });


                                                                                                  // new DownloadWebPageTask().execute();


                                                                                              }


                                                                                          } catch (Exception exception) {
                                                                                              //progressDialog.dismiss();
                                                                                              return;

                                                                                              // handleError(exception);
                                                                                          }                   // Do something with the response
                                                                                          System.out.println(timeline);
                                                                                      }
                                                                                  });

                                                                              } else {
                                                                                  Utils.alertDialogShow(MainActivity.this, "Error", " Connection  refused", 3);
                                                                              }


                                                                          }

                                                                      } catch (Exception e) {


                                                                          return;
                                                                      }


                                                                      // Do something with the response
                                                                      System.out.println("");
                                                                  }

                                                                  @Override
                                                                  public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                                                      xCheck = 1;
                                                                      throwable.printStackTrace(System.out);
                                                                      Utils.alertDialogShow(MainActivity.this, "Error", " Connection  refused", 3);
                                                                  }


                                                                  @Override
                                                                  public void onFinish() {

                                                                      progressDialog.dismiss();
                                                                  }


                                                              });

                                                          }

                                                      }

                                                  }


                                              }


                                          }
                                      }

        );

    }

    private class DownloadWebPageTask extends AsyncTask<String, Void, JSONArray> {

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Download and generating data messages...");
            progressDialog.show();
            super.onPreExecute();
        }


        @Override
        protected JSONArray doInBackground(String... urls) {

            try {
                // arrayItemid = new HashMap<String, String>();
                JSONArray jsonpageA = Utils.loadJSONArray(MainActivity.this, "pages", "1");
                Map<String, String> xdcs = new HashMap<String, String>();

                for (int kw = 0; kw < jsonpageA.length(); kw++) {

                    if (jsonpageA.getJSONObject(kw).isNull("page_type")) {
                        Log.d("wwwwjabbir", "" + jsonpageA.getJSONObject(kw).getString("page_type"));
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Utils.alertDialogShow(MainActivity.this, "Json Error", "Json Error", 3);
                            }
                        });
                    } else {
                        if (jsonpageA.getJSONObject(kw).getString("page_type").equals("I")) {
                            JSONObject actor = jsonpageA.getJSONObject(kw);
                            mapper1.put(Integer.parseInt(actor.getString("page_seq")), actor.getString("page_desc"));
                        }
                    }

                }

                //Log.d("**********", Utils.createMap(arrayItemid) + "");
                arrayListcat = new ArrayList<String>();
                ArrayList<Integer> aListcat = new ArrayList<Integer>();
                for (Map.Entry<Integer, String> entry : mapper1.entrySet()) {
                    System.out.println("Item is:" + entry.getKey() + " with value:" +
                            entry.getValue());
                    aListcat.add(entry.getKey());
                }
                Set<Integer> hes = new HashSet<>();
                hes.addAll(aListcat);
                aListcat.clear();
                aListcat.addAll(hes);
                Collections.sort(aListcat);
                for (int i = 0; i < aListcat.size(); i++) {
                    arrayListcat.add(mapper1.get(aListcat.get(i)));
                }

                Log.d("**********", arrayListcat + "" + arrayListcat.size());
                Utils.writeList(MainActivity.this, arrayListcat, "Main_Cat");
                //Utils.writeList(MainActivity.this, arrayItemid, "pages");
                xczI = new HashMap<String, ArrayList<String>>();
                gcdI = new ArrayList<ArrayList<String>>();


                arrayItemid = new HashMap<String, ArrayList<String>>();
                for (int k = 0; k < arrayListcat.size(); k++) {
                    String[] xyz = {};
                    Log.d("**", "" + k);
                    String no = arrayListcat.get(k).toString();
                    cdI = new ArrayList<String>();
                    arr = new ArrayList<String>();
                    for (int kw = 0; kw < jsonpageA.length(); kw++) {
                        JSONObject actor = jsonpageA.getJSONObject(kw);
                        if (!actor.getString("page_type").equals("R")) {
                            String name = actor.getString("page_desc");
                            if (name != null && !name.isEmpty()) {
                                if (no.equalsIgnoreCase(name)) {
                                    if (jsonpageA.getJSONObject(kw).getString("userid").startsWith("PAGE_")) {

                                        String[] parts = (jsonpageA.getJSONObject(kw).getString("userid")).split("_");
                                        System.out.println("example" + parts[1]);


                                        xhaNames.put(jsonpageA.getJSONObject(kw).getString("page_id"), jsonpageA.getJSONObject(kw).getString("page_desc"));
                                        arr.add(jsonpageA.getJSONObject(kw).getString("userid"));
                                        Log.d(jsonpageA.getJSONObject(kw).getString("userid") + "------", jsonpageA.getJSONObject(kw).getString("item_id"));
                                        xdcs.put(jsonpageA.getJSONObject(kw).getString("userid"), jsonpageA.getJSONObject(kw).getString("item_id"));
                                    }
                                    cdI.add(actor.getString("item_id"));
                                }
                            }
                        }
                    }
                    if (!arr.isEmpty()) {
                        arrayItemid.put("" + k, arr);
//                        Log.d("kkkkArraylength " + k, "" + arr);
//                        Log.d("**********", arrayItemid + "");
                    }
                    gcdI.add(cdI);

                    // Log.d("kkkkArraylength " + k, "" + arr);
                    xczI.put("" + k, cdI);
                    //Log.d("Arraylength", "" + gcdI);
                    //Log.d("@@@@@arrang", k + "" + cdI);
                }

                xdcs = Utils.createMap(xdcs);
                Log.d("Arraylength", "" + xdcs);
                Utils.saveMap(MainActivity.this, xdcs, "xdcs");

                int total_s = -1;
                totlArray = new JSONArray();
                xyz = addObj;
                int x = 0;
                Map<String, String> cvx = new HashMap<String, String>();
                Map<String, String> Imrgcvx = new HashMap<String, String>();

                Map<String, String> cvrrx = new HashMap<String, String>();
                for (int o = 0; o < gcdI.size(); o++) {
                    System.out.println(gcdI.get(o));
                    cx = new ArrayList<String>();
                    cx = gcdI.get(o);
                    addArray = new JSONArray();
                    for (int w = 0; w < cx.size(); w++) {
                        x = w;
                        String nme = cx.get(w).toString();
                        //  Log.d("@@@@@arrang", "" + "" + nme);
                        total_s++;

                        for (int i = 0; i < xyz.length(); i++) {
                            JSONObject actor1 = xyz.getJSONObject(i);
                            String name = actor1.getString("item_id");
                            String price = actor1.getString("price");
                            String imgurl = actor1.getString("image");
                            Double value = Double.parseDouble(price);


                            if (nme.equalsIgnoreCase(name) && (!(String.format("%.2f", value)).equalsIgnoreCase("0.00"))) {
                                Log.d("***xxxxxxxxnn", "" + name);
                                addArray.put(xyz.getJSONObject(i));

                                if(!Utils.isConnected(MainActivity.this))
                                    cvx.put(name, offlineString(imgurl));
                                else
                                cvx.put(name, sBuildxc(imgurl));
                                if (actor1.has("variable_item")) {
                                    for (int vr = 0; vr < actor1.getJSONArray("variable_item").length(); vr++) {

//                                            if(!nme.equalsIgnoreCase(actor1.getJSONArray("variable_item").getJSONObject(vr).getString("citem_id")))
//                                            {
                                        if (!Imrgcvx.containsKey(actor1.getJSONArray("variable_item").getJSONObject(vr).getString("citem_id")))
                                        {
                                            if(!Utils.isConnected(MainActivity.this))
                                                Imrgcvx.put(actor1.getJSONArray("variable_item").getJSONObject(vr).getString("citem_id"), offlineString(actor1.getJSONArray("variable_item").getJSONObject(vr).getString("c_image")));
                                            else
                                                Imrgcvx.put(actor1.getJSONArray("variable_item").getJSONObject(vr).getString("citem_id"), sBuildxc(actor1.getJSONArray("variable_item").getJSONObject(vr).getString("c_image")));

                                        }
                                             //}

                                        Log.d("getJSONObject(i)", "" + actor1.getJSONArray("variable_item").getJSONObject(vr).getString("citem_id"));

                                    }

                                }

                            } else if (nme.equalsIgnoreCase(name) && ((String.format("%.2f", value)).equalsIgnoreCase("0.00"))) {
                                if (xdcs.containsValue(name)) {
                                    Log.d("xcDatata", name + " ___ Image " + imgurl);
                                    cvrrx.put(name, (imgurl));

                                } else {
                                    addArray.put(xyz.getJSONObject(i));
                                    if(!Utils.isConnected(MainActivity.this)){
                                        cvx.put(name, offlineString(imgurl));
                                    }
                                    else
                                        cvx.put(name, sBuildxc(imgurl));
                                    //cvx.put(name, sBuildxc(imgurl));

                                }


                            }


//
                        }


                    }
                    Log.d("***xxxxxxxxnnddddddd", "" + addArray);
                    Log.d("***xxnddddddd", "" + arrayItemid.keySet());
                    for (String key : arrayItemid.keySet()) {
                        if (Integer.toString(o).equals(key)) {
                            ArrayList item = new ArrayList<>();
                            item = arrayItemid.get(key);
                            for (int j = 0; j < item.size(); j++) {
                                JSONObject js = new JSONObject();
                                js.put("userid", item.get(j));
                                Log.d("***js", "" + js);
                                addArray.put(js);
                            }

                            break;
                        }
                    }


                    ArrayList<Integer> tsInt = new ArrayList<>();
                    for (int we = 0; we < addArray.length(); we++) {
                        if (addArray.getJSONObject(we).isNull("userid"))
                            if (addArray.getJSONObject(we).has("button_no"))
                                tsInt.add(Integer.parseInt(addArray.getJSONObject(we).getString("button_no")));

                    }

// add elements to al, including duplicates
                    Set<Integer> hs = new HashSet<>();
                    hs.addAll(tsInt);
                    tsInt.clear();
                    tsInt.addAll(hs);
                    Collections.sort(tsInt);
                    Log.d("***tsInt", "" + tsInt);


                    JSONArray jsAry = new JSONArray();

                    if (tsInt.size() <= 0) {
                        jsAry = addArray;
                    } else {
                        for (int fd = 0; fd < tsInt.size(); fd++) {
                            for (int w = 0; w < addArray.length(); w++) {
                                if (addArray.getJSONObject(w).isNull("userid")) {
                                    if (Integer.parseInt(addArray.getJSONObject(w).getString("button_no")) == tsInt.get(fd))

                                    {
                                        jsAry.put((addArray.getJSONObject(w)));
                                        //break;
                                    }
                                }


                            }

                        }
                    }


                    Log.d("***xxxxxxxx", "" + jsAry.length());
                    totlArray.put(jsAry);
                    Log.d("***xxxxxxxx", "" + totlArray);

                }

                cvrrx = Utils.createMap(cvrrx);
                Map<String, String> cf = new HashMap<String, String>();
                for (String key : cvrrx.keySet()) {
                    System.out.println(key);
                    if(!Utils.isConnected(MainActivity.this)){
                        cf.put(key, offlineString(cvrrx.get(key)));
                    }
                    else
                        cf.put(key, (sBuildxc(cvrrx.get(key))));


                }
                xhaNames = Utils.createMap(xhaNames);
                Log.d("xhaNames", "" + xhaNames);
                Utils.saveMap(MainActivity.this, Imrgcvx, "Imrgcvx");
                Utils.saveMap(MainActivity.this, xhaNames, "xhaNames");
                Utils.saveMap(MainActivity.this, cf, "pImages");

                //Log.d("**************", "" + ":" + cvx);
                Utils.saveMap(MainActivity.this, cvx, "pageImages");

                Log.d("catIndxxyz", "" + ":" + arrayItemid);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return totlArray;
        }

        @Override
        protected void onPostExecute(JSONArray result) {
            // progressDialog.dismiss();
            progressDialog.dismiss();
            Utils.MAINTAG = serConfig[0] + ":" + serConfig[1];


            try {
                Utils.saveJSONArray(MainActivity.this, "basha", "1", result);
                ArrayList<ArrayList<Integer>> combiArray = new ArrayList<ArrayList<Integer>>();

            } catch (Exception je) {

            }

            Utils.saveint(MainActivity.this, 1, "ser_dilogshow");
            if (Utils.getString(MainActivity.this, "Tableno") == null) {

                Intent i = new Intent(MainActivity.this, TableSelection.class);
                i.putExtra("nextLev", 1);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.nothing, R.anim.fade_in);
            } else {
                Intent i = new Intent(MainActivity.this, HomeScreen.class);
                i.putExtra("nextLev", 0);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.nothing, R.anim.fade_in);
            }


        }
    }

    //    Thread closeActivity = new Thread(new Runnable() {
//        @Override
//        public void run() {
//            try {
//                Thread.sleep(3000);
//                // Do some stuff
//            } catch (Exception e) {
//                e.getLocalizedMessage();
//            }
//        }
//    });
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.nothing, R.anim.fade_in);
    }

    private String sBuildxc(String cd) {

        try {
            Log.i("read al giles", cd);
            StringBuilder everything = new StringBuilder();
            URL url = new URL(cd);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String sResponse;
            sBuild = new StringBuilder();

            while ((sResponse = in.readLine()) != null) {
                sBuild = sBuild.append(sResponse);
            }
            in.close();
            Log.i("sd", sBuild.toString());


        } catch (MalformedURLException e) {
        } catch (Exception e) {
            Log.i("sd444", e.getMessage());


        }
//        Bitmap bs = Utils.decodeBase64(sBuild.toString());

        return sBuild.toString();
    }


    private String offlineString(String cd) {

        String json = "";
        int rawt;
        try {

            String[] parts = (cd.split("resources/"));
            String first = parts[0];
            String second = parts[1];
            Log.d("first", first);
            Log.d("second", (second.replace("_", "d")).replace(".txt",""));

            sd = "a" + (second.replace("_", "d")).replace(".txt","");
               //int xcz=Integer.parseInt();
//
//            sd = cd.replace("a", "");
//            sd = sd.replace();

            int id = this.getResources().getIdentifier(sd, "raw", this.getPackageName());

            try {
                BufferedInputStream resourceStream = new BufferedInputStream(getResources().openRawResource(id));
                BufferedReader reader = new BufferedReader(new InputStreamReader(resourceStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    json += line;
                }
                reader.close();
                resourceStream.close();
            } catch (Exception ex) {
                Log.e("myApp", ex.getMessage());
            }
        }
            catch (Exception e){}


        return json;
    }


    private JSONArray myOfflineJson(int nmeUrl) {
        JSONArray jsonobject = new JSONArray();


        try {
            String json = "";
            try {
                BufferedInputStream resourceStream = new BufferedInputStream(getResources().openRawResource(nmeUrl));
                BufferedReader reader = new BufferedReader(new InputStreamReader(resourceStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    json += line;
                }
                reader.close();
                resourceStream.close();
            } catch (Exception ex) {
                Log.e("myApp", ex.getMessage());
            }

            jsonobject = new JSONArray(json);
            //adap(jsonobject);
//
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonobject;
    }

    private void  mthd(){

JSONArray jsArry=new JSONArray();

        try{
            for (int i = 0; i < resultjs.length(); i++) {
                JSONObject jsObj=new JSONObject();
                jsObj=resultjs.getJSONObject(i);
String nameObj=jsObj.getString("guest");
                Log.e("nameObj",nameObj);

            }
        }
        catch(Exception e){

        }


    }

}




//        LabelView label = new LabelView(this);
//        label.setText("POP");
//        label.setBackgroundColor(0xff03a9f4);
//        label.setTargetView(findViewById(R.id.txt1), 10, LabelView.Gravity.LEFT_TOP);
