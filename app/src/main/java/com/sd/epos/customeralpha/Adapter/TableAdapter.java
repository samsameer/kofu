package com.sd.epos.customeralpha.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.sd.epos.customeralpha.Activitys.HomeScreen;
import com.sd.epos.customeralpha.R;
import com.sd.epos.customeralpha.common.TableNames;
import com.sd.epos.customeralpha.common.Utils;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jabbir on 29/6/15.
 */
public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHolder> {
    private String[] mDataset;
    private Context context;
    private List<TableNames> layoutResourceId;
    private JSONArray jas;
    private Animation bottomUp;
    private List<TableNames> mItems;
    private int selectedItem = -1;
    private int extra;

    public TableAdapter(Context context, List<TableNames> layoutResourceId, int xtraInt) {
        super();
        this.layoutResourceId = layoutResourceId;

        this.context = context;
        this.extra = xtraInt;
        bottomUp = AnimationUtils.loadAnimation(context,
                R.anim.slide_in_up);
        mItems = new ArrayList<TableNames>();
        mItems = layoutResourceId;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int postion) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.simple_grid, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        TableNames nature = mItems.get(i);
        viewHolder.txNo.setText(nature.getName());
        viewHolder.txName.setText(nature.getPrice());
        if (nature.getcolory().equalsIgnoreCase("GR"))
            viewHolder.txColor.setBackgroundColor(context.getResources().getColor(R.color.primary_dark));

        else
            viewHolder.txColor.setBackgroundColor(context.getResources().getColor(R.color.pink));

    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txName;
        private TextView txNo;
        private TextView txColor;

        public ViewHolder(View itemView) {
            super(itemView);
            txName = (TextView) itemView.findViewById(R.id.tbl_name);
            txNo = (TextView) itemView.findViewById(R.id.tbl_no);
            txColor = (TextView) itemView.findViewById(R.id.tbl_color);
            txNo.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_in_up));
            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TableNames nature = mItems.get(getAdapterPosition());
                    Log.d("***********", getPosition() + "nameof the table" + txName.getText());
                    //v.setBackgroundColor(context.getResources().getColor(R.color.pink));
                    //Utils.saveString(context, txNo.getText().toString(), "Tableno");
                    Utils.saveint(context, Integer.parseInt(nature.getno_of_cover()), "ccccints");
                    if (Utils.getint(context, "tbl") == 2) {

                    } else {
                        Utils.saveString(context, txNo.getText().toString(), "Tableno");
                        Utils.removeJsonSharedPreferences(context, "totalArry");
                        Utils.removeJsonSharedPreferences(context, "SAVED");
                        int addvlu = 1;
                        if (extra <= 1)
                            addvlu = 1;
                        else
                            addvlu = 2;


                        Intent xz = new Intent(context, HomeScreen.class);
                        xz.putExtra("nextLev", addvlu);
                        context.startActivity(xz);
                    }

                }
            };

            txNo.setOnClickListener(clickListener);
            //tvspecies.setOnClickListener(clickListener);
            //notifyDataSetChanged();
        }

    }


}