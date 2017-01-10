package com.sd.epos.customeralpha.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sd.epos.customeralpha.Activitys.Remarks;
import com.sd.epos.customeralpha.Activitys.VariableChooseActivity;
import com.sd.epos.customeralpha.Models.NamesTopup;
import com.sd.epos.customeralpha.R;
import com.sd.epos.customeralpha.common.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jabbir on 8/6/15.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private String[] mDataset;
    private Context context;
    private int layoutResourceId;
    private int pos;
    private Animation bottomUp;
    private JSONArray jVariable;
    private JSONArray inoArry;
    private ProgressDialog spinnerDialog;
    public int mSelectedItem = -1;
    private static final String TAG = MyAdapter.class.getSimpleName();
    List<NamesTopup> mItems;
    private List<NamesTopup> neItems;
    private String cmpAdd;
    private ArrayList<String> cremrk;
    private ArrayList<Boolean> getStatus;
    HashMap<Integer, String[]> jsd = new HashMap<Integer, String[]>();
    private ArrayList<Integer> selectedItems;
    private ArrayList<String> editTextLi;
    private HashMap<String, ArrayList<String>> aHash;
    private JSONObject jdObj;
    private int posIn = 0;
    private ArrayList<Integer> cxY;
    private ArrayList<String> vcx;
    private JSONArray js = new JSONArray();
    private Boolean TX = false;
    private int custID = 0;
    private String xNu = "null";
    private ViewHolder viewHolder = null;
    private int xPodt = -1;
    private int groupNumber=0;

    public MyAdapter(Context context, List<NamesTopup> pos, String cmpAdd, int posInt, int custID) {
        super();
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.cmpAdd = cmpAdd;
        this.posIn = posInt;
        this.custID = custID;

        bottomUp = AnimationUtils.loadAnimation(context,
                R.anim.slide_in_up);
        mItems = new ArrayList<NamesTopup>();
        neItems = new ArrayList<NamesTopup>();
        this.mItems = pos;
        selectedItems = new ArrayList<Integer>();
        vcx = new ArrayList<String>();
        cremrk = new ArrayList<String>();
        getStatus = new ArrayList<Boolean>();

        for (int i = 0; i < mItems.size(); i++) {

            //  "" + cmpAdd + "_" + y + "_" + cusPos
            // Log.d("Value***********",  cmpAdd + "_" + custID + "_" + i+"@@@@@@@@"+Utils.getString(context,"" + cmpAdd + "_" + custID + "_" + i));
            if (Utils.getPString(context, "RemarksVariab", "" + cmpAdd + "_" + custID + "_" + i) != null) {
                Log.d("Value***********", cmpAdd + "_" + custID + "_" + i);
                NamesTopup nature = mItems.get(i);
                nature.setcremarks(Utils.getString(context, "" + cmpAdd + "_" + custID + "_" + i));

            }
           // notifyDataSetChanged();

            NamesTopup nature = mItems.get(i);
            if (cmpAdd.equalsIgnoreCase(nature.getgrpName())) {
                Log.d("********", nature.getName().toString());
                getStatus.add(false);
                vcx.add("1");
                cremrk.add("");
                neItems.add(nature);
            }
        }

        try {
            cxY = new ArrayList<Integer>();
            JSONObject jd = Utils.loadJSONObject(context, "mkeit", cmpAdd + "_" + custID);
            Log.d("********jd", "" + jd);
//{"0":["Japanese Braised Pork Belly","8"],"1":["Barbecue Pork","8"]}
            if (jd != null) {
                for (Iterator<String> iter = jd.keys(); iter.hasNext(); ) {
                    cxY.add(Integer.parseInt(iter.next()));
                    Collections.sort(cxY);
                }

                for (int i = 0; i < getStatus.size(); i++) {
                    for (int k = 0; k < cxY.size(); k++) {
                        if (cxY.get(k) == i) {
                            getStatus.set(i, true);
                            try {
                                JSONArray cx = jd.getJSONArray("" + cxY.get(k));
                                Log.d("@@@@@@@", "" + cx);
                                vcx.set(i, cx.get(1).toString());
                                cremrk.set(i, cx.get((cx.length() - 1)).toString());
                            } catch (JSONException e) {
                         Log.d("eee", "" + e);
                            }


                        }

                    }

                }
                // notifyItemRangeChanged(0, mItems.size());
                Log.d("cmeeee333", "ggggg11");
                chkItem();
            }
            Log.d("3#######", "" + vcx);

        } catch (JSONException ex) {

        }
        //notifyItemRangeChanged(0, mItems.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.my_text_view, viewGroup, false);
        viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        NamesTopup nature = neItems.get(i);
        viewHolder.chkBox.setText(nature.getName());
        viewHolder.lPrice.setText(nature.getPrice());
        viewHolder.nQty.setText(vcx.get(i).toString());
        viewHolder.rTextV.setText(cremrk.get(i).toString());
        try{

            Bitmap bitmap = Utils.decodeBase64(VariableChooseActivity.timgHas.get(nature.getcitem_no()));
            if (bitmap == null) {
                bitmap = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.nopic);
                viewHolder.imgRemrk.setImageBitmap(bitmap);
            } else
                viewHolder.imgRemrk.setImageBitmap(bitmap);


        }
        catch (Exception e){
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.nopic);
            viewHolder.imgRemrk.setImageBitmap(bitmap);

        }


        if (nature.getcdef_flag().equalsIgnoreCase("M")) {
            viewHolder.chkBox.setChecked(true);
            viewHolder.nQty.setText("1");
            viewHolder.chkBox.setSelected(getStatus.set(i, true));
            TX = true;

            Log.d("cmeeee", "ggggg");

            //chkItem();

        } else {
            viewHolder.chkBox.setSelected(getStatus.get(i));
            viewHolder.chkBox.setChecked(getStatus.get(i));
            if(getStatus.get(i)){
                viewHolder.chkBox.setTextColor(context.getResources().getColor(R.color.txtgray));
            }
            else
                viewHolder.chkBox.setTextColor(context.getResources().getColor(R.color.tgraddy));
            try {
                Log.d("xxxxx2", "" + xPodt);
                if (xPodt != -1) {

                    if (i == xPodt) {
                        JSONObject js = Utils.loadJSONObject(context, "mkeit", cmpAdd + "_" + custID);
                        viewHolder.rTextV.setText(js.getJSONArray("" + xPodt).getString(6));
                    }
                    else
                    viewHolder.rTextV.setText("");
                }

            } catch (Exception e) {

            }

            Log.d("cmeeee1sss1", "ggggg1sss1");
            chkItem();
        }

    }

    @Override
    public int getItemCount() {

        return neItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    protected boolean isSelected(int position) {
        return selectedItems.contains(Integer.valueOf(position));
    }

    protected void dataSetChanged() {
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // public TextView titleName;
        public TextView lPrice;
        private EditText nQty;
        private CheckedTextView chkBox;
        private ImageView imgRemrk;
        public TextView rTextV;
        public TextView rmkbtn;


        public ViewHolder(final View itemView) {
            super(itemView);
            //titleName = (TextView) itemView.findViewById(R.id.txt1);
            chkBox = (CheckedTextView) itemView.findViewById(R.id.ch1k);
            rTextV = (TextView) itemView.findViewById(R.id.textVV);
            lPrice = (TextView) itemView.findViewById(R.id.txt2);
            nQty = (EditText) itemView.findViewById(R.id.npe1);
            //itemView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.left_right));
            imgRemrk = (ImageView) itemView.findViewById(R.id.imgVie);
            rmkbtn = (TextView) itemView.findViewById(R.id.rmkbtn);
            // rTextV.setMovementMethod(new ScrollingMovementMethod());


            nQty.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    Log.d("adapter", "" + getPosition());
                    vcx.set(getPosition(), "1");
                    Log.d("cmeeee44444", "ggggg11");
                    //chkItem();
//                    try{
//                       notifyDataSetChanged();
//                    }
//                    catch(IllegalStateException e){
//                        Log.d("print",e.getMessage());
//
//                    }

                }
            });

            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.rmkbtn:
                            if (getStatus.get(getPosition())) {
                                xPodt = getPosition();
                                NamesTopup nature = neItems.get(getPosition());
                                String[] myStrings = new String[]{nature.getName().toString(), nature.getPrice().toString()};


                                Intent i = new Intent(context, Remarks.class);
                                Utils.saveString(context, cmpAdd, "texA");
                                i.putExtra("stringRemrk", custID);
                                Log.d("stringRemrk", "" + custID);
                                i.putExtra("cusPos", "" + getAdapterPosition());
                                Log.d("cusPos", "" + getAdapterPosition());
                                i.putExtra("isMain", 1);
                                i.putExtra("strings", myStrings);
                                i.putExtra("cmpAdd", cmpAdd);
                                Log.d("cmpAdd", "" + cmpAdd);
                                i.putExtra("itemid",nature.getcitem_id());
                                context.startActivity(i);
                                notifyDataSetChanged();
                            } else {
                                Utils.alertDialogShow(context, "Choose the Variable Item", "Alert", 0);
                            }
                            break;
                        case R.id.imgVie:
                        case R.id.ch1k:
                            int index = selectedItems.indexOf(getPosition());
                            Log.d("getPostion", "" + index);
                            NamesTopup natur = new NamesTopup();



                            if (!TX) {

                                NamesTopup nature = neItems.get(getPosition());
                                if(Integer.parseInt(nature.getnoCunt())==0)
                                {
                                    if (chkBox.isSelected()) {
                                        chkBox.setSelected(false);
                                        chkBox.setTextColor(context.getResources().getColor(R.color.tgraddy));
//                                        for (int i = 0; i < getStatus.size(); i++) {
//                                            if (getPosition() == i) {
//                                                getStatus.set(getPosition(), false);
//                                            }
//                                            // getStatus.set(i, false);
//
//                                        }


                                        getStatus.set(getPosition(), false);
                                        nQty.setText("1");
                                        vcx.set(getPosition(), "1");
                                        Log.d("edit text", "" + vcx);
                                        rTextV.setText("");

                                            } else {
                                        chkBox.setSelected(true);
                                        chkBox.setTextColor(context.getResources().getColor(R.color.gold));
                                        vcx.set(getPosition(), "1");
                                        Log.d("edit2222 text", "" + vcx);
                                        getStatus.set(getPosition(), true);


//                                        for (int i = 0; i < getStatus.size(); i++) {
//                                            if (getPosition() == i) {
//
//                                            }
//                                            // getStatus.set(i, false);
//
//                                        }
                                    }

                                }
                                else {
                                    if (chkBox.isSelected()) {
                                        chkBox.setSelected(false);
                                        chkBox.setTextColor(context.getResources().getColor(R.color.tgraddy));
                                        for (int i = 0; i < getStatus.size(); i++) {
                                            if (getPosition() == i) {
                                                getStatus.set(getPosition(), false);
                                            }
                                            else
                                             getStatus.set(i, false);

                                        }

                                        nQty.setText("1");
                                        vcx.set(getPosition(), "1");
                                        Log.d("edit text", "" + vcx);
                                        rTextV.setText("");

                                    } else {
                                        chkBox.setSelected(true);
                                        chkBox.setTextColor(context.getResources().getColor(R.color.gold));
                                        vcx.set(getPosition(), "1");
                                        Log.d("edit2222 text", "" + vcx);
                                        for (int i = 0; i < getStatus.size(); i++) {
                                            if (getPosition() == i) {
                                                getStatus.set(getPosition(), true);
                                            }
                                            else
                                              getStatus.set(i, false);

                                        }
                                    }
                                }

                            } else {
                                chkBox.setSelected(true);
                                chkBox.setTextColor(context.getResources().getColor(R.color.gold));
                                for (int i = 0; i < getStatus.size(); i++) {
                                    if (getPosition() == i) {
                                        getStatus.set(getPosition(), true);
                                    }

                                }
                                vcx.set(getPosition(), "1");
                                Log.d("edit2222 text", "" + vcx);
                            }

                            Log.d("cmeeee5555", "ggggg11");
                            chkItem();
                            notifyDataSetChanged();
                            break;
                    }


                    // for()


                }
            };
            rmkbtn.setOnClickListener(clickListener);
            chkBox.setOnClickListener(clickListener);
            imgRemrk.setOnClickListener(clickListener);


        }
    }

    private void chkItem() {
        JSONObject jh = new JSONObject();
        try {
            jh = Utils.loadJSONObject(context, "mkeit", cmpAdd + "_" + custID);

            aHash = new HashMap<String, ArrayList<String>>();
            Log.d("status",""+getStatus.size()+getStatus);
            for (int k = 0; k < getStatus.size(); k++) {
                Log.d("getstatus",""+getStatus.get(k));
                if (getStatus.get(k)) {
//                    if (jh.has("" + k)) {
//                        xNu = jh.getJSONArray("" + xPodt).getString(6);
//                        if (k == xPodt) {
//                            System.out.println("xxxxxxxxxxx1" + xPodt);
//                        }
//
//                    } else {
//                        xNu = "";
//                    }
                    xNu = "";
                    editTextLi = new ArrayList<String>();
                    NamesTopup nature = neItems.get(k);
                    editTextLi.add(nature.getName());
                    editTextLi.add(nature.getPrice());
                    editTextLi.add(nature.getcitem_no());
                    editTextLi.add(nature.getcseq_no());
                    editTextLi.add(""+1);
                    editTextLi.add(nature.getcuom());
                    editTextLi.add(xNu);
                    aHash.put("" + k, editTextLi);
                    Log.d("keyseyf", "" + aHash);
                } else {
                    NamesTopup nature = neItems.get(k);
                    aHash.remove("" + k);
                }

            }

            jdObj = new JSONObject(aHash);
            Log.d("@jdObjVariable11111", "" + jdObj);
            Utils.saveJSONObject(context, "mkeit", cmpAdd + "_" + custID, jdObj);
            Log.i("strinng", "" + Utils.loadJSONObject(context, "mkeit", cmpAdd + "_" + custID));
//            NamesTopup nature = mItems.get(xPodt);
//            nature.setcremarks(xNu);
            notifyDataSetChanged();
//            notifyItemChanged(xPodt);
//            notifyDataSetChanged();
            //if(!xNu.matches(""))
            //  dataSetChanged();
        } catch (Exception e) {

        }

    }

    public void updateResults(List<NamesTopup> results) {
        // assign the new result list to your existing list it will work
        notifyDataSetChanged();
    }

}
