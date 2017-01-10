package com.sd.epos.customeralpha.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.sd.epos.customeralpha.Activitys.HomeBaseActivity;
import com.sd.epos.customeralpha.Activitys.Remarks;
import com.sd.epos.customeralpha.Activitys.VariableChooseActivity;
import com.sd.epos.customeralpha.Models.Data;
import com.sd.epos.customeralpha.Models.TotalClass;
import com.sd.epos.customeralpha.R;
import com.sd.epos.customeralpha.common.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


/**
 * Created by jabbir on 8/6/15.
 */
public class ViewOrderadapter extends RecyclerView.Adapter<ViewOrderadapter.ViewHolder> {
    private ArrayList<String> mDataset;
    private Context context;
    private JSONArray layoutResourceId;
    private static final String TAG = ViewOrderadapter.class.getSimpleName();
    private List<TotalClass> mItems;
    private TotalClass species;
    private String secondSubString = "";
    private HashMap<Integer, ArrayList<String>> getArrys = new HashMap<Integer, ArrayList<String>>();
    private ArrayList<Boolean> status = new ArrayList<Boolean>();
    private HashMap<Integer, String> ordArrys = new HashMap<Integer, String>();


    private ArrayList<Integer> selectedItems;
    private ArrayList<String> insideItem;
    private List<Data> selItems;
    private ArrayList<String> tAway;
    private ArrayList<String> lisremrk;
    private int cusNum;
    private int xPodt = -1;
    int tx = 0;
    int xyzzzz = -1;
    private HashMap<String, ArrayList<Integer>> addHash = new HashMap<String, ArrayList<Integer>>();
    private JSONArray ttl = new JSONArray();
    private String[] vcx = {};
    private int xYes=-1;
    private Bitmap con;

    public ViewOrderadapter(Context context, List<TotalClass> nameItem, int cusTid,int xyes) {
        super();
        this.layoutResourceId = layoutResourceId;
        this.cusNum = cusTid;
        con = Utils.decodeSampledBitmapFromResource(context.getResources(), R.drawable.nopic, 100, 100);

        status = new ArrayList<Boolean>();
        this.context = context;
//        bottomUp = AnimationUtils.loadAnimation(context,
//                R.anim.slide_in_up);
this.xYes=xyes;
        this.mItems = nameItem;
        tAway = new ArrayList<String>();


        for (int i = 0; i < mItems.size(); i++) {
            tAway.add("N");
            status.add(false);
        }
        //xyzzzz=Utils.sNo;
        Log.d("came1", "" + "cameeeee1111rrrrrrr");
        selectedItems = new ArrayList<Integer>();
        JSONArray mDataset = new JSONArray();
        List<String> fir = new ArrayList<String>();
        xyzzzz = 0;
        xyzzzz = mItems.size() - 1;

        try {
            if(xYes==0){
                if (Utils.readList(context, "fire").size() > 0) {
                    fir = Utils.readList(context, "fire");
                    ArrayList<Integer> cvTes = new ArrayList<Integer>();
                    String strings[] = new String[fir.size()];
                    for (int i = 0; i < strings.length; i++) {
                        strings[i] = fir.get(i);
                        cvTes.add(Integer.parseInt(fir.get(i)));
                        Log.d("catch1", "**strings" + Integer.parseInt(fir.get(i)));
                    }
                    for (int e = 0; e < cvTes.size(); e++) {
                        status.set(cvTes.get(e), true);
                    }
                }
            }
            else
            {
                for (int i = 0; i < mItems.size(); i++) {
                    status.add(false);
                }
                Utils.removeJsonSharedPreferences(context, "fire");
            }


        } catch (Exception e) {

        }


        try {
            if (!Utils.loadJSONArray(context, "SAVED", "" + cusTid).toString().equalsIgnoreCase("[]")) {
                mDataset = Utils.loadJSONArray(context, "SAVED", "" + cusNum);
                Log.d("catch1", "*************cusNum******" + cusNum);
                if (mDataset != null) {
                    int len = mDataset.length();
                    for (int i = 0; i < len; i++) {
                        if (mDataset.get(i).toString().equalsIgnoreCase("Y"))
                            tAway.set(i, mDataset.get(i).toString());
                        else
                            tAway.set(i, "N");
                    }
                }
            }
        } catch (Exception e) {

        }

//        if (mDataset != null) {
//            Log.d("catch1", "*************nameItem******" + mDataset);
//            for (int ie = 0; ie < mDataset.size(); ie++) {
//
//            }
//
//        }
        //new DownloadFilesTask().execute();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.myorder_item, viewGroup, false);
        View rowView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.myorder_item, viewGroup, false);
        } else
            rowView = v;

        ViewHolder viewHolder = new ViewHolder(rowView);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Log.d("came2", "" + "cameeeee2");
        TotalClass nature = mItems.get(i);

        viewHolder.titleName.setText(nature.getName());

//        Bitmap b = BitmapFactory.decodeByteArray(nature.getThumbnail(), 0, (nature.getThumbnail().));

        Bitmap b = Utils.decodeBase64(context, HomeBaseActivity.xyzHash.get(nature.getitemId()));
        if (b == null)
            viewHolder.thmy.setImageBitmap(con);
        else
            viewHolder.thmy.setImageBitmap(b);

        viewHolder.rsLay.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
        viewHolder.titleName.setTextColor(context.getResources().getColor(android.R.color.black));
        viewHolder.lPrice.setText("$ " + nature.getprice());
        viewHolder.npe1.setText(nature.getQty());
        viewHolder.scrl_vie.setVisibility(View.VISIBLE);
        viewHolder.remrkview.setText(nature.getremark().replace(",", " "));
        viewHolder.remrkview.setSelected(true);
        viewHolder.selectItem.setSelected(false);
        viewHolder.mBthn.setVisibility(View.GONE);
        // xyzzzz=xyzzzz+1;

//        try {
//            Log.d("came1", "" + "cameeeee22222rrrrrrr");
//            if (xPodt != -1) {
//
//                if (i == xPodt) {
//                    String xTrs = Utils.getPString(context, "rR", "rrrr");
//                    xPodt = -1;
//                    // JSONObject js = Utils.loadJSONObject(context, "mkeit", cmpAdd + "_" + custID);
//                    viewHolder.remrkview.setText(xTrs);
//
//                } else
//                    viewHolder.remrkview.setText("");
//            }
//
//        } catch (Exception e) {
//
//        }

        if (tAway.get(i).equals("Y") || nature.gettAway().equalsIgnoreCase("Y"))
            viewHolder.togBut.setChecked(true);
        else
            viewHolder.togBut.setChecked(false);


        if (nature.getReddm.equalsIgnoreCase("NO")) {
            viewHolder.mDel.setVisibility(View.VISIBLE);
            viewHolder.npe1.setFocusable(true);
            status.set(i, false);
            viewHolder.mfired.setVisibility(View.GONE);
            // viewHolder.mRemarl.setVisibility(View.VISIBLE);
            viewHolder.maddd.setVisibility(View.VISIBLE);
            viewHolder.mminu.setVisibility(View.VISIBLE);
            viewHolder.selectItem.setVisibility(View.GONE);
            viewHolder.togBut.setClickable(true);
            viewHolder.mAdd.setVisibility(View.INVISIBLE);


        } else if (nature.getReddm.equalsIgnoreCase("DONE") || nature.getReddm.equalsIgnoreCase("Y")) {
            viewHolder.mfired.setVisibility(View.VISIBLE);
            status.set(i, false);
            viewHolder.rsLay.setBackgroundColor(context.getResources().getColor(android.R.color.white));
            viewHolder.titleName.setTextColor(context.getResources().getColor(android.R.color.black));
            viewHolder.selectItem.setVisibility(View.GONE);
            viewHolder.mDel.setVisibility(View.GONE);
            viewHolder.npe1.setFocusable(false);
            viewHolder.mminu.setVisibility(View.INVISIBLE);
            viewHolder.mAdd.setVisibility(View.INVISIBLE);
            viewHolder.mRemarl.setVisibility(View.INVISIBLE);
            viewHolder.maddd.setVisibility(View.INVISIBLE);
            viewHolder.togBut.setClickable(false);
        } else {
            viewHolder.rsLay.setBackgroundColor(context.getResources().getColor(android.R.color.white));
            viewHolder.titleName.setTextColor(context.getResources().getColor(android.R.color.black));
            viewHolder.mminu.setVisibility(View.INVISIBLE);
            viewHolder.mfired.setVisibility(View.GONE);
            viewHolder.selectItem.setVisibility(View.VISIBLE);
            viewHolder.selectItem.setSelected(status.get(i));
            viewHolder.selectItem.setChecked(status.get(i));
            viewHolder.mDel.setVisibility(View.GONE);
            viewHolder.mAdd.setVisibility(View.INVISIBLE);
            viewHolder.mRemarl.setVisibility(View.INVISIBLE);
            viewHolder.maddd.setVisibility(View.INVISIBLE);
            viewHolder.npe1.setFocusable(false);
            viewHolder.togBut.setClickable(false);

        }


        if (nature.gethasVar().equalsIgnoreCase("NO")) {
            viewHolder.linRecycle.setVisibility(View.GONE);
        } else {
            if (nature.getReddm.equalsIgnoreCase("NO")) {
                viewHolder.eDitvar.setVisibility(View.VISIBLE);
            } else
                viewHolder.eDitvar.setVisibility(View.GONE);


            Log.d("came", "" + nature.getistopUp().size());
            if (nature.getistopUp().size() <= 2)
                viewHolder.linRecycle.getLayoutParams().height = 160;
            else if (4 < nature.getistopUp().size() && nature.getistopUp().size() <= 5)
                viewHolder.linRecycle.getLayoutParams().height = 240;
            else
                viewHolder.linRecycle.getLayoutParams().height = 300;
            viewHolder.ChAdapter = new ViewAddadapter((Activity) context, nature.getistopUp());
            viewHolder.mChairList.setAdapter(viewHolder.ChAdapter);
            // viewHolder.thmy.getAppTaskThumbnailSize()
        }


        // notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

//    protected boolean isSelected(int position) {
//        return selectedItems.contains(Integer.valueOf(position));
//    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private ToggleButton togBut;
        public TextView titleName;
        public TextView lPrice;
        private ImageView thmy;
        private ImageView mDel;
        private EditText npe1;
        private CheckedTextView selectItem;
        public TextView mRemarl;
        private TextView remrkview;
        public TextView mAdd;
        private TextView mfired, maddd, mminu;
        private LinearLayout mBthn;
        private RecyclerView mChairList;
        private RecyclerView.Adapter ChAdapter;
        private HorizontalScrollView scrl_vie;
        private RecyclerView.LayoutManager lmutManager;
        private LinearLayout linRecycle;
        private RelativeLayout rsLay;
        private TextView eDitvar;
        private int previousTotal = 0;
        private boolean loading = true;
        private int visibleThreshold = 5;
        int firstVisibleItem, visibleItemCount, totalItemCount;

        public ViewHolder(final View itemView) {
            super(itemView);
            rsLay = (RelativeLayout) itemView.findViewById(R.id.res_lys);
            titleName = (TextView) itemView.findViewById(R.id.oName);
            lPrice = (TextView) itemView.findViewById(R.id.lprice);
            thmy = (ImageView) itemView.findViewById(R.id.img_thl);
            mDel = (ImageView) itemView.findViewById(R.id.img_thnail);
            mBthn = (LinearLayout) itemView.findViewById(R.id.lir221);
            scrl_vie = (HorizontalScrollView) itemView.findViewById(R.id.scrl_vie);
            npe1 = (EditText) itemView.findViewById(R.id.edNum);
            remrkview = (TextView) itemView.findViewById(R.id.remrkview);
            selectItem = (CheckedTextView) itemView.findViewById(R.id.chkIt);
            mRemarl = (TextView) itemView.findViewById(R.id.mRemrk);
            mAdd = (TextView) itemView.findViewById(R.id.mAdd);
            mminu = (TextView) itemView.findViewById(R.id.view_var);
            maddd = (TextView) itemView.findViewById(R.id.newww);
            eDitvar = (TextView) itemView.findViewById(R.id.edit_v);
            mfired = (TextView) itemView.findViewById(R.id.fired);
            linRecycle = (LinearLayout) itemView.findViewById(R.id.linrout);
            togBut = (ToggleButton) itemView.findViewById(R.id.mySwitch);
            mChairList = (RecyclerView) itemView.findViewById(R.id.itmMode);
            mChairList.setHasFixedSize(true);
            lmutManager = new GridLayoutManager((Activity) context, 2);
            // mChairList.setLayoutManager(lmutManager);
            LinearLayoutManager layManager = new LinearLayoutManager((Activity) context);
            layManager.setOrientation(LinearLayoutManager.VERTICAL);
            mChairList.setLayoutManager(lmutManager);


            //itemView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.left_right));

            if (selectItem == null) {

                selectItem = new CheckedTextView(context);
            }


            npe1.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    Log.d("came11111111", "" + "cameeeee" + getPosition());

                    TotalClass nature = mItems.get(getPosition());
                    nature.setQty(npe1.getText().toString().replace("$", ""));
                    Double xt = Double.parseDouble(npe1.getText().toString());
                    Double txy = Double.parseDouble(lPrice.getText().toString().replace("$ ", ""));

                    if (tx == 1) {
                        tx = 0;
                    } else if (tx == 2) {
                        tx = 0;
                    }

                    //
                    txy = ((double) (txy * xt));
                    lPrice.setText("$ " + String.format("%.2f", txy));

//                    TotalClass nature = mItems.get(getPosition());
//                    nature.setQty(npe1.getText().toString().replace("$", ""));
                }
            });
            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    switch (v.getId()) {
                        case R.id.mRemrk:
                            // alert_edit(getPosition());
                            TotalClass nature = mItems.get(getPosition());
                            Utils.removeJsonSharedPreferences(context, "rR");
                            xPodt = getPosition();
                            Intent i = new Intent(context, Remarks.class);
                            i.putExtra("maxri", remrkview.getText().toString());
                            i.putExtra("stringRemrk", 0);
                            i.putExtra("cusPos", "" + getPosition());
                            i.putExtra("isMain", 4);
                            i.putExtra("itemid", nature.getitemId());
                            Utils.xpTo = xPodt;
                            context.startActivity(i);
//                            notifyDataSetChanged();
                            break;
                        case R.id.img_thnail:
                            delTetItem(getPosition());
                            break;


                        case R.id.newww:


                            int xc = Integer.parseInt(npe1.getText().toString()) + 1;

                            tx = 1;
                            addDelmethd(getPosition(), tx);
                            npe1.setText("" + xc);

//                            int xc = Integer.parseInt(npe1.getText().toString()) + 1;
//                            npe1.setText("" + xc);


                            break;
                        case R.id.view_var:

                            int xct = Integer.parseInt(npe1.getText().toString()) - 1;
                            if (xct < 1) {
                                xct = 1;
                            } else if (xct == 1) {
                                tx = 2;
                                addDelmethd(getPosition(), tx);
                            } else {
                                tx = 2;
                                addDelmethd(getPosition(), tx);
                            }
                            npe1.setText("" + xct);

//                            int xct = Integer.parseInt(npe1.getText().toString()) - 1;
//                            if (xct <= 1) {
//                                xct = 1;
//                            }
//                            npe1.setText("" + xct);
                            break;


                        case R.id.mAdd:
//                            //ArrayList<NamesTopup> twz = new ArrayList<NamesTopup>();
//                            ArrayList<String> mittwz = new ArrayList<String>();
//                            ArrayList<ArrayList<String>> t2twz = new ArrayList<ArrayList<String>>();
//
//                            TotalClass nature = mItems.get(getPosition());
//                            // twz = nature.getistopUp();
//                            ArrayList<NamesTopup> object = new ArrayList<NamesTopup>();
//                            object = nature.getistopUp();
//                            for (int i = 0; i < object.size(); i++) {
//                                mittwz = new ArrayList<String>();
//                                NamesTopup species = object.get(i);
//                                mittwz.add(species.getName());
//                                mittwz.sadd(species.getPrice());
//                                mittwz.add(species.getcremarks());
//                                mittwz.add(species.getQty());
//                                Log.d("catch1", "*************mittwz******" + mittwz);
//                                t2twz.add(mittwz);
//
//                            }
//                            Log.d("catch1", "*************id******" + t2twz);
//                            Intent i = new Intent(context, AddonViewsDilog.class);
//                            i.putExtra("ARRAYLIST", t2twz);
//                            context.startActivity(i);
                            break;


                        case R.id.edit_v:

                            try {
                                JSONArray jsArryfor = Utils.loadJSONArray(context, "totalArry", "" + 1);
                                JSONObject jdss = new JSONObject();
                                int post = 0;
                                Log.d("ddddd", "" + HomeBaseActivity.xMainfor + "eeee" + getPosition() + "sddd" + Utils.sNo);


                                if (Utils.sNo == 0)
                                    post = getPosition();
                                else
                                    post = (getPosition() - HomeBaseActivity.xMainfor);

                                jdss = jsArryfor.getJSONObject(post);
                                Log.d("rrfffr", "" + Integer.parseInt(jdss.getString(Utils.mPar)) + "ddd" + Integer.parseInt(jdss.getString(Utils.subPar)) + "ddd" + post);

                                TotalClass nccature = mItems.get(getPosition());
                                String Stu[] = {nccature.getName(), nccature.getprice(), "editview"};
                                int mypostion[] = {Integer.parseInt(jdss.getString(Utils.mPar)), Integer.parseInt(jdss.getString(Utils.subPar)), post};
                                Intent intent = new Intent(((Activity) context), VariableChooseActivity.class);
                                intent.putExtra("data", mypostion);
                                intent.putExtra("strings", Stu);
                                ((Activity) context).startActivity(intent);


                            } catch (Exception e) {

                            }
                            break;


                        case R.id.chkIt:
                            int index = selectedItems.indexOf(getPosition());


                            if (index != -1) {
                                selectItem.setSelected(false);
                                status.set(getPosition(), false);

                            } else {
//                                //txName.toggle();
                                if (selectItem.isSelected()) {
                                    status.set(getAdapterPosition(), false);
                                    selectItem.setSelected(status.get(getAdapterPosition()));


                                } else {
                                    status.set(getPosition(), true);
                                    selectItem.setSelected(status.get(getAdapterPosition()));

                                }
                                aledit();
                            }


                            break;
                    }

                    notifyDataSetChanged();
                }
            };
            eDitvar.setOnClickListener(clickListener);
            maddd.setOnClickListener(clickListener);
            mminu.setOnClickListener(clickListener);
            selectItem.setOnClickListener(clickListener);
            mRemarl.setOnClickListener(clickListener);
            mAdd.setOnClickListener(clickListener);
            mDel.setOnClickListener(clickListener);
            mChairList.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                @Override
                public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                    int action = e.getAction();
                    switch (action) {
                        case MotionEvent.ACTION_MOVE:
                            rv.getParent().requestDisallowInterceptTouchEvent(true);
                            break;
                    }
                    return false;
                }

                @Override
                public void onTouchEvent(RecyclerView rv, MotionEvent e) {

                }


            });
            // or get it from the layout by ToggleButton Btn=(ToggleButton) findViewById(R.id.IDofButton);
            togBut.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    TotalClass nature = mItems.get(getPosition());
                    if (isChecked) {
                        buttonView.setTextColor(context.getResources().getColor(R.color.white));
                        tAway.set(getPosition(), "Y");
                        //nature.settAway("Y");
                    } else {
                        buttonView.setTextColor(context.getResources().getColor(R.color.text_secondary));
                        tAway.set(getPosition(), "N");
                    }


                    JSONArray jsArray = new JSONArray(tAway);
                    Utils.saveJSONArray(context, "SAVED", "" + cusNum, jsArray);
                    try {
                        Log.d("&&&&&&&&", "" + cusNum + "" + Utils.loadJSONArray(context, "SAVED", "" + cusNum));
                    } catch (Exception e) {
                        Log.d("catch1", "*************json******" + ttl);
                    }

                }
            });


        }
    }


    public List<Integer> getSelectedItems() {
        return selectedItems;
    }


    private void aledit() {

        addHash = new HashMap<String, ArrayList<Integer>>();
        insideItem = new ArrayList<String>();
        for (int i = 0; i < status.size(); i++) {
            if (status.get(i)) {
                insideItem.add("" + i);
            }
        }
        if(insideItem.size()>=1){
            Utils.writeList(context, insideItem, "fire");
            Log.d("&&&&&&&&", "" + Utils.readList(context, "fire"));
        }
        // addHash.put("" + cusNum, insideItem);




    }

    private void alert_edit(int pos) {
        final int x = pos;
        String firstSubString = "";

        TotalClass nature = mItems.get(pos);
        String remark = nature.getremark();
        if (!remark.matches("")) {
            if (remark.contains("_")) {
                String[] split = remark.split("_");
                firstSubString = split[0];
                secondSubString = split[1];
            }

        } else {
            firstSubString = "";
            secondSubString = "";
        }


        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        final EditText edittext = new EditText(context);
        edittext.setText(firstSubString);
        edittext.setHint("                        Please write your remarks.        ");
        alert.setMessage(secondSubString);

        alert.setTitle(R.string.remrk);
        alert.setView(edittext);

        alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                TotalClass nature = mItems.get(x);
                nature.setremark(edittext.getText() + secondSubString.replace("\n", ""));
                notifyDataSetChanged();

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });

        alert.show();


    }

    private void delTetItem(final int postion) {
        AlertDialog.Builder alertDel = new AlertDialog.Builder(context);
        alertDel.setMessage("                    Are you sure you want to delete this Item ?                                               ");
        alertDel.setTitle("Delete Item");
        alertDel.setPositiveButton("         Delete              ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                JSONArray jsArryfor = new JSONArray();
                int wxz = 0;

                try {
                    if (HomeBaseActivity.xMainfor == -1 || HomeBaseActivity.xMainfor == 0) {
                        Utils.posTo += -1;
                        jsArryfor = Utils.loadJSONArray(context, "totalArry", "" + 1);
                        Log.d("notifiy", "" + Utils.RemoveJSONArray(jsArryfor, postion));
                        Utils.saveJSONArray(context, "totalArry", "" + 1, Utils.RemoveJSONArray(jsArryfor, postion));
                        mItems.remove(postion);
                        notifyItemRemoved(postion);
                        notifyItemRangeChanged(postion, mItems.size());
                    } else {
                        Log.d("notifiy", HomeBaseActivity.xMainfor + "poist" + postion);
                        int xPost = (postion - HomeBaseActivity.xMainfor);

                        Utils.posTo += -1;

                        jsArryfor = Utils.loadJSONArray(context, "totalArry", "" + 1);
                        Log.d("notifiy", "" + Utils.RemoveJSONArray(jsArryfor, xPost));
                        Utils.saveJSONArray(context, "totalArry", "" + 1, Utils.RemoveJSONArray(jsArryfor, xPost));
                        mItems.remove(postion);
                        notifyItemRemoved(postion);
                        notifyItemRangeChanged(postion, mItems.size());
                    }


                } catch (Exception e) {
                    Utils.posTo += 1;

                }


            }
        });

        alertDel.setNegativeButton("Cancel                          ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });

        alertDel.show();


    }

    private void addDelmethd(final int postion, final int addDel) {
        try {
            int posTin = 0;

            if (HomeBaseActivity.xMainfor == -1 || HomeBaseActivity.xMainfor == 0) {
                posTin = postion;
            } else {
                posTin = (postion - HomeBaseActivity.xMainfor);
            }

            JSONArray jsArryfor = new JSONArray();
            jsArryfor = Utils.loadJSONArray(context, "totalArry", "" + Utils.cusOn);
            int qty = Integer.parseInt(jsArryfor.getJSONObject(posTin).getString("qty"));
            if (addDel == 2) {
                qty = qty - 1;
            } else {
                qty = qty + 1;
            }
            JSONObject js = new JSONObject();
            js = jsArryfor.getJSONObject(posTin);
            js.remove("qty");
            js.put("qty", "" + qty);
            jsArryfor.put(posTin, js);

            TotalClass nature = mItems.get(postion);

            JSONArray jsd = new JSONArray();
            Log.d("postion", "" + posTin);
            if ((jsArryfor.getJSONObject(posTin).toString().contains(Utils.varPar))) {

                double xsd = 0;
                jsd = jsArryfor.getJSONObject(posTin).getJSONArray(Utils.varPar);
                for (int j = 0; j < jsd.length(); j++) {
                    JSONObject ksw = jsd.getJSONObject(j);
                    Iterator<String> iter = ksw.keys();
                    while (iter.hasNext()) {
                        String key = iter.next();
                        try {
                            nature.getistopUp().get(j).setQty("" + qty);
                            ksw.getJSONArray(key).put(4, "" + qty);

                        } catch (Exception e) {

                        }
                    }
                    jsd.put(j, ksw);
                }


                jsArryfor.getJSONObject(posTin).put("variable", jsd);
                Utils.saveJSONArray(context, "totalArry", "" + Utils.cusOn, jsArryfor);
                notifyDataSetChanged();


            } else {
                Utils.saveJSONArray(context, "totalArry", "" + Utils.cusOn, jsArryfor);
            }


        } catch (Exception e) {

        }


    }

    public void mthod() {
        try {
            try {
                JSONArray jsArryfor = Utils.loadJSONArray(context, "totalArry", "" + 1);
                Utils.saveJSONArray(context, "totalArry", "" + 1, Utils.UpdateremJSONArray(jsArryfor, xPodt, Utils.xTo));
                Log.d("notifiy1222222", "" + Utils.loadJSONArray(context, "totalArry", "" + 1));

            } catch (JSONException je) {

            }

            TotalClass nature = mItems.get(xPodt);

            nature.setremark(Utils.xTo);
            notifyDataSetChanged();
        } catch (Exception e) {
        }

//        int tx=Utils.xpTo;
//        String xyz= Utils.xTo;
//        Log.d("same eeee",""+tx);
//        Log.d("string name eeee", "" +xyz);

    }


}







