package com.sd.epos.customeralpha.common;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sd.epos.customeralpha.Activitys.MainActivity;
import com.sd.epos.customeralpha.Models.EndangeredItem;
import com.sd.epos.customeralpha.Models.Itemmodel;
import com.sd.epos.customeralpha.Models.ItemreqModel;
import com.sd.epos.customeralpha.Models.UOmmodel;
import com.sd.epos.customeralpha.Models.Vendormodel;
import com.sd.epos.customeralpha.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

/**
 * Created by jabbir on 6/10/15.
 */
public class Utils {
    private ProgressDialog progressDialog;


    public static void pdiloag(Context context, String msg, int xy) {

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        if (xy == 0)
            progressDialog.show();
        else
            progressDialog.dismiss();


    }

    //public static int[] tsNck = {R.drawable.ifts, R.drawable.a_1, R.drawable.a_2, R.drawable.a_3, R.drawable.a_4, R.drawable.ifts, R.drawable.a_1, R.drawable.a_2, R.drawable.a_3, R.drawable.a_4, R.drawable.ifts, R.drawable.a_1, R.drawable.a_2, R.drawable.a_3, R.drawable.a_4};
    /*
constants here
 *///http://203.125.118.243:8088/kpos/getItemsfulldetails?compcode=01&paramgroup_code=BV
    public static String ITEM_FUL_DET = "/kpos/getItemsfulldetails?compcode=";
    public static String ITEM_PAGE_DET = "/kpos/getbuttonPage_woimage?compcode=";
    public static String GETPOSCLIENT = "/kpos/getPosClientParam?compcode=";
    public static String FireOrder = "/kpos/PutBulkFiretoKitchen?compcode=01&xmldata=";
    public static String FirePOrder = "/kpos/PutBulkFiretoKitchen?";
    public static String servOrder = "/kpos/PutTableRequestAlert?compcode=";
    public static String ExNT = "/kpos/putorder?compcode=";
    public static String auto_NT = "/kpos/putorder_autofire?compcode=";
    public static String PExNT = "/kpos/putorder?";
    public static String GETOrder = "/kpos/GetOrder_uuid?compcode=";
    public static String GETMain = "/kpos/checkuserrights?compcode=";
    public static String GMain = "/kpos/getTransferDtls?compcode=";
    public static String compnyCd = "01";
    public static String stcMain = "/kpos/getTransferDocList?compcode=";
    public static String putstc = "/kpos/putStockIn?compcode=";
    public static String tknNumber = "/kpos/getTokenNo?compcode=" + Utils.compnyCd;
    public static String userAuth;


    public static int cusOn = 1;
    public static String mPar = "main";
    public static String subPar = "subcat";
    public static String qtyPar = "qty";
    public static String remPar = "remark";
    public static String setitem = "setitem";
    public static String varPar = "variable";
    public static String passAuth;
    public static String REG = "&regcode=";
    public static String OUTCODE = "&outletcode=";
    private static final String PREFIX = "json";
    public static String PARAM = "&paramgroup_code=";
    public static String MAINTAG = null;
    public static String USER = null;
    public static int posTo = 0;
    public static int xpTo = -1;
    public static String xTo = "";
    public static String ORDERRE = "/kpos/getOrderItemRemarks?";
    public static String COMCODE = "compcode=";
    //    public static HashMap<Integer, ArrayList<Data>> gusArray = new HashMap<Integer, ArrayList<Data>>();
//    public static ArrayList<Data> Contacts = new ArrayList<Data>();
    public static String MEMRG = "/kpos/ipad_put_customer_info?compcode=";
    public static int CC = 1;
    public static int FirstTime = 0;
    public static int FLISTime = 0;
    public static String[] Tjs = new String[6];
    public static String GETTABl = "/kpos/getTableStatus?compcode=";
    public static int sNo = 0;
    public static ArrayList<String> js = new ArrayList<>();
    public static HashMap<String, ArrayList<EndangeredItem>> TtlItem = new HashMap<String, ArrayList<EndangeredItem>>();
    public static HashMap<Integer, ArrayList<ItemreqModel>> ttItemRequest = new HashMap<Integer, ArrayList<ItemreqModel>>();
    public static List<String> places = Arrays.asList("comp_code", "wh_id", "doc_date", "table_no", "order_no", "s_no", "userid");
    public static List<String> SMASTER = Arrays.asList("comp_code", "wh_id", "shift_code", "recdate", "userid", "ref_no", "doc_date", "doc_time", "str_doc_no", "req_doc_no");
    public static List<String> SDETAIL = Arrays.asList("comp_code", "wh_id", "shift_code", "recdate", "userid", "s_no", "item_id", "item_no", "qty_in", "ref_qty_in", "prod_date", "serial_lot_no");
    public static String[] jsStr = {"doc_no", "doc_date", "status", "confirm_status", "required_by", "remarks"};
    public static String[] xStrd = {"doc_sno", "vend_no", "vend_name", "item_no", "item_descr", "qty_order", "uom"};
    public static ArrayList<Itemmodel> jsItemID = new ArrayList<Itemmodel>();
    public static ArrayList<UOmmodel> jsUom = new ArrayList<UOmmodel>();
    public static ArrayList<Vendormodel> jsVend = new ArrayList<Vendormodel>();
    public static int ITEMFIRS = 0;
    public static HashMap<String, Bitmap> tImages = new HashMap<String, Bitmap>();

    public static String currenDate() {
        String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        return date;

    }

    public static String curdndt() {//2015/04/27 19:11:11
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime;
    }

    public static String currenttim() {//2015/04/27 19:11:11
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime;
    }


    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );


    public static String rUom(String xyy) {
        Double value = Double.parseDouble(xyy);
        String x = String.format("%.2f", value);
        return x;
    }

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }

    //private static final String BASE_URL = "http://api.twitter.com/1/";
    private static int DEFAULT_TIMEOUT = 20 * 1000;
    private static AsyncHttpClient client = new AsyncHttpClient();


//    client.setTimeout(2000);
//    client.setConnectTimeout(2000);
//    AsyncHttpClient aClient = new AsyncHttpClient();
//    aClient.setTimeout(DEFAULT_TIMEOUT);

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client = new AsyncHttpClient();
        client.setBasicAuth(Utils.userAuth, Utils.passAuth);
        client.setTimeout(500 * 1000);
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client = new AsyncHttpClient();
        client.setBasicAuth(Utils.userAuth, Utils.passAuth);
        client.setTimeout(500 * 1000);
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    //    public static int calculateInSampleSize(
//            BitmapFactory.Options options, int reqWidth, int reqHeight) {
//        // Raw height and width of image
//        final int height = options.outHeight;
//        final int width = options.outWidth;
//        int inSampleSize = 1;
//
//        if (height > reqHeight || width > reqWidth) {
//
//            final int halfHeight = height / 2;
//            final int halfWidth = width / 2;
//
//            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
//            // height and width larger than the requested height and width.
//            while ((halfHeight / inSampleSize) > reqHeight
//                    && (halfWidth / inSampleSize) > reqWidth) {
//                inSampleSize *= 2;
//            }
//        }
//
//        return inSampleSize;
//    }
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return relativeUrl;
    }


    public static void txt(Context context, View viewRoot) {
        int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 7;
        int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
        int finalRadius = Math.max(viewRoot.getWidth(), viewRoot.getHeight());

        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, 0, finalRadius);
        //viewRoot.setVisibility(View.VISIBLE);
        anim.setDuration(400);
        anim.setInterpolator(new AccelerateInterpolator());
        anim.start();
    }

    public static void txtAnmination(View viewRoot, final int xc) {
        int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 6;
        int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
        int finalRadius = Math.max(viewRoot.getWidth(), viewRoot.getHeight());

        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, 0, finalRadius);
        //viewRoot.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //if (xc == 1)
                //animateRevealShow(mSearchView);
                //   animateButtonsIn();
            }
        });
        anim.start();
    }


    public static void writeList(Context context, ArrayList<String> list, String prefix) {
        SharedPreferences prefs = context.getSharedPreferences("YourApp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        int size = prefs.getInt(prefix + "_size", 0);

        // clear the previous data if exists
        for (int i = 0; i < size; i++)
            editor.remove(prefix + "_" + i);

        // write the current list
        for (int i = 0; i < list.size(); i++)
            editor.putString(prefix + "_" + i, list.get(i));

        editor.putInt(prefix + "_size", list.size());
        editor.commit();
    }

    public static List<String> readList(Context context, String prefix) {
        SharedPreferences prefs = context.getSharedPreferences("YourApp", Context.MODE_PRIVATE);
        int size = prefs.getInt(prefix + "_size", 0);
        List<String> data = new ArrayList<String>(size);
        for (int i = 0; i < size; i++)
            data.add(prefs.getString(prefix + "_" + i, null));

        return data;
    }


    public static void saveArrayw(Context con, String prefName, String key, ArrayList<String> array) {
        SharedPreferences prefs = con.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray jArray = new JSONArray(array);
        editor.remove(key);
        editor.putString(key, jArray.toString());
        editor.commit();
    }

    public static ArrayList<String> getArrayw(Context con, String prefName, String key) {
        SharedPreferences prefs = con.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        ArrayList<String> array = new ArrayList<String>();
        String jArrayString = prefs.getString(key, "NOPREFSAVED");
        if (jArrayString.matches("NOPREFSAVED")) return getDefaultArray();
        else {
            try {
                array = new ArrayList<String>();
                JSONArray jArray = new JSONArray(jArrayString);
                for (int i = 0; i < jArray.length(); i++) {
                    array.add(jArray.getString(i));
                }
                return array;
            } catch (JSONException e) {
                return getDefaultArray();
            }
        }
    }

    public static JSONArray js(Context cx) {

        JSONArray jst = new JSONArray();
        try {

            jst = Utils.loadJSONArray(cx, "basha", "1");

        } catch (Exception e) {


        }
        return jst;
    }

    public static Bitmap getBitmapFromString(String jsonString) {
/*
* This Function converts the String back to Bitmap
* */
        byte[] decodedString = Base64.decode(jsonString, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    public static String getStringFromBitmap(Bitmap bitmapPicture) {
 /*
 * This functions converts Bitmap picture to a string which can be
 * JSONified.
 * */
        String encodedImage = "";
        try {
            final int COMPRESSION_QUALITY = 100;
            ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
            bitmapPicture.compress(Bitmap.CompressFormat.PNG, 100, byteArrayBitmapStream);
            byte[] b = byteArrayBitmapStream.toByteArray();
            encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        } catch (Exception e) {

        }

        return encodedImage;
    }

    private static ArrayList<String> getDefaultArray() {
        ArrayList<String> array = new ArrayList<String>();
        array.add("N");
        array.add("N");
        array.add("N");

        return array;
    }

    public static String decompress(byte[] compressed) throws IOException {
        final int BUFFER_SIZE = 32;
        ByteArrayInputStream is = new ByteArrayInputStream(compressed);
        GZIPInputStream gis = new GZIPInputStream(is, BUFFER_SIZE);
        StringBuilder string = new StringBuilder();
        byte[] data = new byte[BUFFER_SIZE];
        int bytesRead;
        while ((bytesRead = gis.read(data)) != -1) {
            string.append(new String(data, 0, bytesRead));
        }
        gis.close();
        is.close();
        return string.toString();
    }

    public static void saveint(Context context, int text, String key) {
        SharedPreferences prefs = context.getSharedPreferences("preferenceName", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, text); //3
        editor.commit(); //4
    }

    public static int getint(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences("preferenceName", 0);
        return prefs.getInt(key, 0);
    }

    public static void saveString(Context context, String text, String key) {
        SharedPreferences prefs = context.getSharedPreferences("preferenceName", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, text); //3
        editor.commit(); //4
    }

    public static String getString(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences("preferenceName", 0);
        return prefs.getString(key, null);
    }

    public static void savePString(Context context, String prefe, String text, String key) {
        SharedPreferences prefs = context.getSharedPreferences(prefe, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, text); //3
        editor.commit(); //4
    }

    public static String getPString(Context context, String prefe, String key) {
        SharedPreferences prefs = context.getSharedPreferences(prefe, 0);
        return prefs.getString(key, null);
    }

    public static boolean saveArray(String[] array, String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("preferenceName", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(arrayName + "_size", array.length);
        for (int i = 0; i < array.length; i++)
            editor.putString(arrayName + "_" + i, array[i]);
        return editor.commit();
    }

    public static String[] loadArray(String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("preferenceName", 0);
        int size = prefs.getInt(arrayName + "_size", 0);
        String array[] = new String[size];
        for (int i = 0; i < size; i++)
            array[i] = prefs.getString(arrayName + "_" + i, null);
        return array;
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager connMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = connMan.getActiveNetworkInfo();
        if (network == null || !network.isConnected()) {

            return false;
        }
        return true;
    }

    public static void alertDialogShow(final Context context, String message, String title, final int xyz) {

        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setMessage(message);
        int x = xyz;
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (xyz == 1) {
                    ((Activity) (context)).finish();
                    context.startActivity(((Activity) context).getIntent());
//                    Intent i = new Intent(context, HomeScreen.class);
//                    context.startActivity(i);
                } else if (xyz == 2) {
                    ((Activity) (context)).finish();
                    context.startActivity(((Activity) context).getIntent());
                } else if (xyz == 3) {
                    Intent i = new Intent(context, MainActivity.class);
                    context.startActivity(i);
                    ((Activity) (context)).finish();
                } else if (xyz == 0) {
                    alertDialog.dismiss();
                }
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }


    public static void saveMap(final Context context, Map<String, String> inputMap, String key) {
        SharedPreferences pSharedPref = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pSharedPref.edit();
        if (pSharedPref != null) {
            JSONObject jsonObject = new JSONObject(inputMap);
            String jsonString = jsonObject.toString();
            editor.remove(key).commit();
            editor.putString(key, jsonString);
            editor.commit();
        }
    }

    public static Map<String, String> loadMap(final Context context, String keys) {
        Map<String, String> outputMap = new HashMap<String, String>();
        SharedPreferences pSharedPref = context.getSharedPreferences(keys, Context.MODE_PRIVATE);
        try {
            if (pSharedPref != null) {
                String jsonString = pSharedPref.getString(keys, (new JSONObject()).toString());
                JSONObject jsonObject = new JSONObject(jsonString);
                Iterator<String> keysItr = jsonObject.keys();
                while (keysItr.hasNext()) {
                    String key = keysItr.next();
                    String value = (String) jsonObject.get(key);
                    outputMap.put(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputMap;
    }


    public static void saveJSONObject(Context c, String prefName, String key, JSONObject object) {
        SharedPreferences settings = c.getSharedPreferences(prefName, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(Utils.PREFIX + key, object.toString());
        editor.commit();
    }

    public static void saveJSONArray(Context c, String prefName, String key, JSONArray array) {
        SharedPreferences settings = c.getSharedPreferences(prefName, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(Utils.PREFIX + key, array.toString());
        editor.commit();
    }

    public static JSONObject loadJSONObject(Context c, String prefName, String key) throws JSONException {
        SharedPreferences settings = c.getSharedPreferences(prefName, 0);
        return new JSONObject(settings.getString(Utils.PREFIX + key, "{}"));
    }

    public static void removeFromSharedPreferences(Context c, String prefName, String key) {
        if (c != null) {
            SharedPreferences mSharedPreferences = c.getSharedPreferences(prefName, 0);
            if (mSharedPreferences != null)
                //mSharedPreferences.edit().remove(key).commit();
                mSharedPreferences.edit().clear().commit();
        }

    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONArray loadJSONArray(Context c, String prefName, String key) throws JSONException {
        SharedPreferences settings = c.getSharedPreferences(prefName, 0);
        return new JSONArray(settings.getString(Utils.PREFIX + key, "[]"));
    }

    public static void remove(Context c, String prefName, String key) {
        SharedPreferences settings = c.getSharedPreferences(prefName, 0);
        if (settings.contains(Utils.PREFIX + key)) {
            SharedPreferences.Editor editor = settings.edit();
            editor.remove(Utils.PREFIX + key);
            editor.commit();
        }
    }

    public static void d(String TAG, String message) {
        int maxLogSize = 15000;
        for (int i = 0; i <= message.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i + 1) * maxLogSize;
            end = end > message.length() ? message.length() : end;
            android.util.Log.d(TAG, message.substring(start, end));
        }
    }

    public static void removeJsonSharedPreferences(Context c, String prefName) {
        if (c != null) {
            SharedPreferences mSharedPreferences = c.getSharedPreferences(prefName, 0);
            if (mSharedPreferences != null)
                mSharedPreferences.edit().clear().commit();
            //mSharedPreferences.edit().clear().commit();
        }

    }

    public static Bitmap decodeBase64(Context con, String input) {


        try {

            Bitmap bitmap = null;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPurgeable = true;
            byte[] decodedByte = Base64.decode(input, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
            return bitmap;

        } catch (Exception ee) {
            Log.w("ImageView", "OOM with sampleSize ", ee);
            System.gc();
            Bitmap bitmap = Utils.decodeSampledBitmapFromResource(con.getResources(), R.drawable.nopic, 100, 100);
            return bitmap;
        }


    }

//
//    public static void savetotl(Context c, String key,  HashMap<Integer, List<Data>> object) {
//        SharedPreferences settings = c.getSharedPreferences("preferenceName", 0);
//        SharedPreferences.Editor editor = settings.edit();
//        editor.putString(Utils.PREFIX + key, object.toString());
//        editor.commit();
//    }

    public static void printMap(Map<Integer, String> map) {
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println("Key : " + entry.getKey()
                    + " Value : " + entry.getValue());
        }
    }

    public static void setDividerColor(Context context, NumberPicker picker) {

        java.lang.reflect.Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (java.lang.reflect.Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    pf.set(picker, context.getResources().getColor(R.color.white));
                    //Log.v(TAG,"here");
                    // pf.set(picker, context.getResources().getDrawable(R.drawable.line));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        //}
    }

    //public static void getData()

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }


    public final static ArrayList<String> getCountry() {

        Locale[] locales = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<String>();
        for (Locale locale : locales) {
            String country = locale.getDisplayCountry();
            if (country.trim().length() > 0 && !countries.contains(country)) {
                countries.add(country);
            }
        }
        Collections.sort(countries);

        return countries;


    }


    public final static Map<String, String> createMap(Map<String, String> m) {
        Map<String, String> map = new HashMap<String, String>();
        Map<String, String> tmpMap = new HashMap<String, String>();
        for (Map.Entry<String, String> entry : m.entrySet()) {
            if (!tmpMap.containsKey(entry.getValue())) {
                tmpMap.put(entry.getValue(), entry.getKey());
            }
        }
        for (Map.Entry<String, String> entry : tmpMap.entrySet()) {
            map.put(entry.getValue(), entry.getKey());
        }
        return map;
    }


//    public final static String getgender(String name, Context con) {
//        String[] mTestArray = con.getResources().getStringArray(R.array.gender_array);
//        String gendr;
//        if (name == mTestArray[0]) {
//            gendr = "S";
//        } else if (name == mTestArray[0]) {
//            gendr = "M";
//
//        } else {
//            gendr = "N";
//        }
//
//        return gendr;
//
//    }

    public static void dilog(final Context mContext) {
        RelativeLayout linearLayout = new RelativeLayout(mContext);
        final NumberPicker aNumberPicker = new NumberPicker(mContext);
        aNumberPicker.setMaxValue(50);
        aNumberPicker.setMinValue(1);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(50, 50);
        RelativeLayout.LayoutParams numPicerParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        linearLayout.setLayoutParams(params);
        linearLayout.addView(aNumberPicker, numPicerParams);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle("Select the number");
        alertDialogBuilder.setView(linearLayout);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                Log.e("", "New Quantity Value : " + aNumberPicker.getValue());
                                Utils.saveint(mContext, aNumberPicker.getValue(), "ints");


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

    public static String jsStat(String xyz) {
        String status = "";
        if (xyz.equalsIgnoreCase("O"))
            status = "OPEN";
        else if (xyz.equalsIgnoreCase("C"))
            status = "CLOSED";
        else if (xyz.equalsIgnoreCase("L"))
            status = "CANCEL";


        return status;
    }


    public static String jConf(String xyz) {
        String status = "";
        if (xyz.equalsIgnoreCase("Y"))
            status = "CONFIRMED";
        else if (xyz.equalsIgnoreCase("N"))
            status = "UNCONFIRMED";


        return status;
    }

    public static final JSONObject makJsonObject(int main_p, int secnd_p, int Qty,
                                                 ArrayList<String> remark, ArrayList<String> Addons, int persons)
            throws JSONException {
        JSONObject obj = null;
        ArrayList<String> xzs = new ArrayList<String>();
        ArrayList<String> sa = new ArrayList<String>();
        xzs = remark;
        sa = Addons;

        obj = new JSONObject();
        try {
            obj.put("main_p", main_p);
            obj.put("secnd_p", secnd_p);
            obj.put("Qty", Qty);
            obj.put("remark", remark);
            obj.put("Addons", Addons);
            if (sa.size() == 0) {
                obj.remove("Addons");
            } else if (xzs.size() == 0) {
                obj.remove("remark");
            }


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return obj;
    }

    public static JSONArray RemoveJSONArray(JSONArray jarray, int pos) {

        JSONArray Njarray = new JSONArray();
        try {
            for (int i = 0; i < jarray.length(); i++) {
                if (i != pos)
                    Njarray.put(jarray.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Njarray;
    }

    public static JSONArray UpdateremJSONArray(JSONArray jarray, int pos, String xMan) {
        try {
            JSONObject js = new JSONObject();
            js = jarray.getJSONObject(pos);
            js.put("remark", xMan);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("rrrrrrrrr", "" + jarray);
        return jarray;
    }


    public static Bitmap doIBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            Log.e("Exception", e.getMessage());
            return null;
        }
    }
}
