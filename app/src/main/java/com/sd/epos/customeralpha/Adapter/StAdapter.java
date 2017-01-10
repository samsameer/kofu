package com.sd.epos.customeralpha.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sd.epos.customeralpha.Activitys.Remarks;
import com.sd.epos.customeralpha.Models.SetDataTopup;
import com.sd.epos.customeralpha.R;
import com.sd.epos.customeralpha.common.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jabbir on 14/7/15.
 */
public class StAdapter extends RecyclerView.Adapter<StAdapter.ViewHolder> {
    private Context context;
    private List<SetDataTopup> neItems;
    private String cmpAdd;
    private int posIn, custID;
    private JSONObject jdObj;
    private int xPodt = -1;
    private  int xLength=0;
    private ArrayList<Boolean> getStatus;
    private ArrayList<String> editTextLi = new ArrayList<String>();
   private  HashMap<String, ArrayList<String>> aHash = new HashMap<String, ArrayList<String>>();
    public StAdapter(Context context, ArrayList<SetDataTopup> posList, String cmpAdd, int posInt, int custID) {
        super();
        this.context = context;
        this.neItems = posList;
        this.cmpAdd = cmpAdd;
        this.posIn = posInt;
        this.custID = custID;
        getStatus = new ArrayList<Boolean>();
        xLength=neItems.size();

        //notifyItemRangeChanged(0, mItems.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.aset_view, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        SetDataTopup nature = neItems.get(i);
        viewHolder.chkBox.setText(nature.getcitem_desc());
        viewHolder.nQty.setText(nature.getcqty());
        viewHolder.imgRemrk.setImageBitmap(nature.getThumbnail());
        viewHolder.rTextV.setText("");
        try {
            Log.d("xxxxx2", "" + xPodt);
            if (xPodt != -1) {

                if (i == xPodt) {
                    String xTrs=Utils.getPString(context, "rR", "rrrr");
                   // JSONObject js = Utils.loadJSONObject(context, "mkeit", cmpAdd + "_" + custID);
                    viewHolder.rTextV.setText(xTrs);
                } else
                    viewHolder.rTextV.setText("");
            }

        } catch (Exception e) {

        }
        chkItem();
    }

    @Override
    public int getItemCount() {

        return neItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        // public TextView titleName;
        public TextView lPrice;
        private EditText nQty;
        private TextView chkBox;
        private ImageView imgRemrk;
        public TextView rTextV;
        public TextView rmkbtn;


        public ViewHolder(final View itemView) {
            super(itemView);
            chkBox = (TextView) itemView.findViewById(R.id.setch1k);
            rTextV = (TextView) itemView.findViewById(R.id.settextVV);
            lPrice = (TextView) itemView.findViewById(R.id.settxt2);
            nQty = (EditText) itemView.findViewById(R.id.setnpe1);
            imgRemrk = (ImageView) itemView.findViewById(R.id.setimgVie);
            rmkbtn = (TextView) itemView.findViewById(R.id.setrmkbtn);
            chkItem();
            nQty.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    chkItem();

                }
            });
            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    switch (v.getId()) {

                        case R.id.setrmkbtn:
                            xPodt = getPosition();
                            SetDataTopup nature = neItems.get(getPosition());
                            String[] myStrings = new String[]{nature.getcitem_desc().toString(), nature.getcf_qty().toString()};
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
                            context.startActivity(i);
                            notifyDataSetChanged();
                            break;
                    }


                }
            };

            rmkbtn.setOnClickListener(clickListener);

            //  imgRemrk.setOnClickListener(clickListener);

        }
    }
    private void chkItem() {
        JSONObject jh = new JSONObject();
        try {
            jh = Utils.loadJSONObject(context, "mkeit", cmpAdd + "_" + custID);

            aHash = new HashMap<String, ArrayList<String>>();
            for (int k = 0; k < xLength; k++) {
                editTextLi = new ArrayList<String>();
                SetDataTopup nature = neItems.get(k);
                editTextLi.add(nature.getcitem_desc());
                editTextLi.add(nature.getcitem_no());
                editTextLi.add(nature.getcitem_id());
                editTextLi.add(nature.getcf_qty());
                editTextLi.add(nature.getcqty());
                editTextLi.add("");
                aHash.put("" + k, editTextLi);
//                if (getStatus.get(k)) {
//                    if (jh.has("" + k)) {
//                        xNu = jh.getJSONArray("" + xPodt).getString(6);
//                        if (k == xPodt) {
//                            System.out.println("xxxxxxxxxxx1" + xPodt);
//                        }
//
////                        if (viewHolder != null) ;
//                        //viewHolder.getPosition().
//                        //System.out.println("xxxxxxxxxxx" + xNu.substring(xNu.lastIndexOf(",") + 1));
//
//                    } else {
//                        xNu = "";
//                    }
//
//                    Log.d("keyseyf", "" + k);
//                } else {
//                    SetDataTopup nature = neItems.get(k);
//                    aHash.remove("" + k);
//                }

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
            //hif(!xNu.matches(""))
            //  dataSetChanged();
        } catch (Exception e) {

        }

    }

    private void delTetItem(final int postion) {
        SetDataTopup nature = neItems.get(postion);
        AlertDialog.Builder alertDel = new AlertDialog.Builder(context);
        // alertDel.setMessage(nature.getcremarks());
        alertDel.setTitle("Remark");
        alertDel.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });


        alertDel.show();


    }
}
