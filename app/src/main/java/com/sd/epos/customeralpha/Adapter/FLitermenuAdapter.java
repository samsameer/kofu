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

import com.sd.epos.customeralpha.R;
import com.sd.epos.customeralpha.common.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jabbir on 29/5/15.
 */
public class FLitermenuAdapter extends RecyclerView.Adapter<FLitermenuAdapter.ViewHolder> {
    Context context;

    private Animation bottomUp;
    private int xPostu;
    List<String> mItems;
    private ArrayList<Boolean> _getFliterItem;
    private List<String> al =new ArrayList<>();

    public FLitermenuAdapter(Context context, List<String> layoutResourceId, int post) {
        super();
        this.mItems = new ArrayList<String>();
        this.context = context;
        this.xPostu = post;
        mItems = layoutResourceId;
        bottomUp = AnimationUtils.loadAnimation(context,
                R.anim.slide_in_up);
        _getFliterItem = new ArrayList<Boolean>();
        if(post==2){
            for (int i = 0; i < mItems.size(); i++)
            _getFliterItem.add(true);
        }
        else if(post==3){
            for (int i = 0; i < mItems.size(); i++)
            _getFliterItem.add(false);
        }

        else{
            al = Utils.readList(context, "tFLitr");
            if(al.size()==0){
                for (int i = 0; i < mItems.size(); i++)
                    _getFliterItem.add(false);
            }
            else{
                for (int i = 0; i < mItems.size(); i++){
                    if(al.contains(mItems.get(i))){
                        _getFliterItem.add(true);
                    }
                    else
                        _getFliterItem.add(false);
                }


            }

        }




    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fliter_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.ovrLay.setText(mItems.get(i));
        viewHolder.ovrLay.setSelected(_getFliterItem.get(i));
        viewHolder.ovrLay.setChecked(_getFliterItem.get(i));


    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        public CheckedTextView ovrLay;
        // public ImageView imgrrr;

        public ViewHolder(View itemView) {
            super(itemView);


            ovrLay = (CheckedTextView) itemView.findViewById(R.id.fltr_name);
            //imgrrr = (ImageView) itemView.findViewById(R.id.imgrrr);
            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {


                        case R.id.fltr_name:
                            if (ovrLay.isSelected()) {
                                ovrLay.setChecked(false);
                                ovrLay.setSelected(false);
                                _getFliterItem.set(getAdapterPosition(), false);
                                ovrLay.setTextColor(context.getResources().getColor(R.color.text_secondary));
                                mFilter();
                            } else {
                                ovrLay.setChecked(true);
                                ovrLay.setSelected(true);
                                _getFliterItem.set(getAdapterPosition(), true);
                                ovrLay.setTextColor(context.getResources().getColor(R.color.ind));
                                mFilter();
                            }
                            break;
                    }

                }
            };
            ovrLay.setOnClickListener(clickListener);


        }
    }

    public ArrayList<Integer> mFilter() {

        ArrayList<Integer> tsArry = new ArrayList<Integer>();
        for (int i = 0; i < _getFliterItem.size(); i++) {
            if (_getFliterItem.get(i)) {
                tsArry.add(i);
                Log.d("list mnnnn", tsArry + "index" + i);
            }


        }

        return tsArry;
    }

}
