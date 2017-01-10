package com.sd.epos.customeralpha.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckedTextView;

import com.sd.epos.customeralpha.Models.Data;
import com.sd.epos.customeralpha.R;
import com.sd.epos.customeralpha.common.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jabbir on 29/6/15.
 */
public class RemarkAdapter extends RecyclerView.Adapter<RemarkAdapter.ViewHolder> {
    private String[] mDataset;
    private Context context;
    private List<Data> layoutResourceId;
    private JSONObject jas;
    private JSONObject jhjd;
    private Animation bottomUp;
    private List<Data> mItems;
    private int postT = 0;
    private int selectedItem = -1;
    private int cusId = 1;
    private JSONObject jd = new JSONObject();
    private ArrayList<Boolean> status = new ArrayList<Boolean>();
    private ArrayList<HashMap<String, String>> mCheckStates;
    private ArrayList<Integer> selectedItems;
    private ArrayList<Integer> sedItems;
    HashMap<String, String> addHash = new HashMap<String, String>();
    private ArrayList<Integer> txt = new ArrayList<Integer>();
    private int isMainreml = 0;
    private String cusPos="";

    public RemarkAdapter(Context context, List<Data> layoutResourceId, int pos, int cusId, int isMainreml, String cusPos) {
        super();
        this.layoutResourceId = layoutResourceId;
        this.cusId = cusId;
        this.context = context;
        this.postT = pos;
        this.isMainreml = isMainreml;
        bottomUp = AnimationUtils.loadAnimation(context,
                R.anim.slide_in_up);
        mItems = new ArrayList<Data>();
        mItems = layoutResourceId;
        for (int i = 0; i < mItems.size(); i++) {
            status.add(false);
        }
        selectedItems = new ArrayList<Integer>();
        jhjd = new JSONObject();
        try {
            sedItems = new ArrayList<Integer>();
            if (isMainreml == 0) {
                jhjd = Utils.loadJSONObject(context, "remarksArry", "" + cusId);
            } else {
               Log.d("getSTring",Utils.getString(context, "texA")+"_"+cusId);
                jhjd = Utils.loadJSONObject(context, "ArrVariable", "" + Utils.getString(context, "texA")+"_"+cusId+"_"+cusPos);
            }
            Iterator<?> keys = jhjd.getJSONArray("Remarks").getJSONObject(pos).keys();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                sedItems.add(Integer.parseInt(key));
            }
            System.out.println("Wrong index number, please enter correct number. " + sedItems);
            for (int i = 0; i < status.size(); i++) {
                for (int k = 0; k < sedItems.size(); k++) {
                    if (sedItems.get(k) == i) {
                        status.set(i, true);
                    }
                }

            }
            Mrth();

        } catch (JSONException js) {

        }


        try {

            jd = Utils.loadJSONObject(context, "Remark", "" + postT+"_"+cusId);


            if (jd != null) {
                for (Iterator<String> iter = jd.keys(); iter.hasNext(); ) {
                    txt.add(Integer.parseInt(iter.next()));
                    Collections.sort(txt);
                }
                for (int i = 0; i < status.size(); i++) {
                    for (int k = 0; k < txt.size(); k++) {
                        if (txt.get(k) == i) {
                            status.set(i, true);
                        }
                    }

                }
            }


        } catch (JSONException ex) {

        }


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int postion) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.simple_remark, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }


    @Override
    public int getItemViewType(int position) {

        return position;
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        Data nature = mItems.get(i);
        viewHolder.txName.setText(nature.getremarks());
        viewHolder.txName.setSelected(status.get(i));
        viewHolder.txName.setChecked(status.get(i));

    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }

    protected boolean isSelected(int position) {
        return selectedItems.contains(Integer.valueOf(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private CheckedTextView txName;

        public ViewHolder(View itemView) {
            super(itemView);
            txName = (CheckedTextView) itemView.findViewById(R.id.remrk);
            if (txName == null) {
                txName = new CheckedTextView(context);
            }

            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    switch (v.getId()) {
                        case R.id.remrk:
                            int index = selectedItems.indexOf(getPosition());
                            if (index != -1) {
                                txName.setSelected(false);
                                status.set(getPosition(), false);

                            } else {
//                                //txName.toggle();
                                if (txName.isSelected()) {
                                    txName.setSelected(false);
                                    status.set(getPosition(), false);
                                } else {
                                    txName.setSelected(true);
                                    status.set(getPosition(), true);
                                }
                                Mrth();
//                                mCheckStates = new ArrayList<HashMap<String, String>>();


                            }


                            break;
                    }

                    notifyItemRangeChanged(0, mItems.size());
                }
            };

            txName.setOnClickListener(clickListener);

        }

    }


    public ArrayList<HashMap<String, String>> getStudentist() {
        return mCheckStates;
    }

    private void Mrth() {
        addHash = new HashMap<String, String>();
        for (int k = 0; k < status.size(); k++) {
            if (status.get(k)) {
                Data nature = mItems.get(k);
                addHash.put("" + k, nature.getremarks());
                Log.d("@@@@@", "" + nature.getremarks());
            } else {
                Data nature = mItems.get(k);
                addHash.remove("" + k);
            }


        }
        Log.d("@@@k", "" + addHash.keySet());
        jas = new JSONObject(addHash);
        // if (!jas.toString().equals("{}"))
        Utils.saveJSONObject(context, "Remark", "" + postT+"_"+cusId, jas);
    }

//    @Override
//    public void onCheckedChanged(CompoundButton buttonView,
//                                 boolean isChecked) {
//
//
//
//    }

}