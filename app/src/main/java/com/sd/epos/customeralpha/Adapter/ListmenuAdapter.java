package com.sd.epos.customeralpha.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.sd.epos.customeralpha.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jabbir on 29/5/15.
 */
public class ListmenuAdapter extends RecyclerView.Adapter<ListmenuAdapter.ViewHolder> {
    Context context;

    private Animation bottomUp;
    private int xPostu;
    List<String> mItems;


    public ListmenuAdapter(Context context, List<String> layoutResourceId, int post) {
        super();
        this.mItems = new ArrayList<String>();
        this.context = context;
        this.xPostu = post;
        mItems = layoutResourceId;
        bottomUp = AnimationUtils.loadAnimation(context,
                R.anim.slide_in_up);


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.ovrLay.setText(mItems.get(i));
    //  viewHolder.imgrrr.setImageDrawable(context.getResources().getDrawable(Utils.tsNck[i]));
        if (xPostu == i) {
            viewHolder.ovrLay.setBackgroundColor(context.getResources().getColor(R.color.ind));
            viewHolder.ovrLay.setTextColor(context.getResources().getColor(R.color.white));
            //viewHolder.imgrrr.setBackgroundColor(context.getResources().getColor(R.color.ind));
            xPostu = 200;
        }


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
        public TextView ovrLay;
       // public ImageView imgrrr;

        public ViewHolder(View itemView) {
            super(itemView);

            ovrLay = (TextView) itemView.findViewById(R.id.cname);
            //imgrrr = (ImageView) itemView.findViewById(R.id.imgrrr);
            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {


                        case R.id.cname:
                            ovrLay.setBackgroundColor(context.getResources().getColor(R.color.black_overlay));
                            ovrLay.setTextColor(context.getResources().getColor(R.color.ind));
                            break;
                    }

                }
            };
            ovrLay.setOnClickListener(clickListener);


        }
    }

}
