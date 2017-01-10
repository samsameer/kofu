package com.sd.epos.customeralpha.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sd.epos.customeralpha.Activitys.ADDAlertActivity;
import com.sd.epos.customeralpha.Activitys.HomeBaseActivity;
import com.sd.epos.customeralpha.Activitys.VariableChooseActivity;
import com.sd.epos.customeralpha.Models.EndangeredItem;
import com.sd.epos.customeralpha.R;
import com.sd.epos.customeralpha.common.CoolPriceView;
import com.sd.epos.customeralpha.common.Utils;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jabbir on 29/5/15.
 */
public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {
    Context context;
    private JSONObject js = new JSONObject();
    private Animation bottomUp;
    private int xPostu;
    List<EndangeredItem> mItems;
    private Bitmap con;


    public GridAdapter(Context context, ArrayList<EndangeredItem> layoutResourceId, int xPostu) {
        super();
        this.mItems = new ArrayList<EndangeredItem>();
        this.context = context;
        this.xPostu = xPostu;
        mItems = layoutResourceId;





//        Collections.sort(mItems, new Comparator<EndangeredItem>() {
//            @Override
//            public int compare(EndangeredItem lhs, EndangeredItem rhs) {
//                return lhs.getName().compareTo(rhs.getbtnPage());
//            }
//        });


        bottomUp = AnimationUtils.loadAnimation(context,
                R.anim.slide_in_up);
        con = Utils.decodeSampledBitmapFromResource(context.getResources(), R.drawable.nopic, 100, 100);


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.new_home_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        EndangeredItem nature = mItems.get(i);
        //viewHolder.lin1V.setVisibility(View.VISIBLE);
        if (nature.getuserId() != null) {
            viewHolder.ovrLay.setVisibility(View.VISIBLE);
            viewHolder.imgThumbnail.setVisibility(View.VISIBLE);
            viewHolder.idLin.setVisibility(View.GONE);

            if (nature.getThumbnail() == null)
                viewHolder.imgThumbnail.setImageBitmap(con);
            else
                viewHolder.imgThumbnail.setImageBitmap(nature.getThumbnail());


           // viewHolder.imgThumbnail.setVisibility(View.INVISIBLE);
            //viewHolder.ovrLay.setVisibility(View.INVISIBLE);

//            if(xPostu==1){
//                viewHolder.imgThumbnail.setScaleType(ImageView.ScaleType.CENTER);
//            }
//            else
//
//                viewHolder.imgThumbnail.setScaleType(ImageView.ScaleType.FIT_XY);


            //viewHolder.imgThumbnail.setImageBitmap(nature.getThumbnail());
            List<String> ts = new ArrayList<String>();
            ts = Utils.readList(context, "Main_Cat");
            String example = nature.getuserId();
            viewHolder.mtop.setVisibility(View.GONE);
            viewHolder.ovrLay.setText(nature.getName());
//            System.out.println(example.substring(example.lastIndexOf("_") + 1));
//            String xe = example.substring(example.lastIndexOf("_") + 1);
//
//
//            for (int it = 0; it < ts.size(); it++) {
//                if (Integer.parseInt(xe) == (it + 1)) {
//                    viewHolder.ovrLay.setText(ts.get(it));
//                    break;
//                }
//
//            }


        } else {
            if ((nature.getvName().equalsIgnoreCase("no"))) {
                viewHolder.addOrd.setVisibility(View.VISIBLE);
                viewHolder.addPlus.setVisibility(View.VISIBLE);
                viewHolder.addMins.setVisibility(View.VISIBLE);
                viewHolder.mQty.setVisibility(View.VISIBLE);
               // viewHolder.txtxx.setVisibility(View.VISIBLE);
                viewHolder.detail.setVisibility(View.GONE);
               // viewHolder.idLin.setBackgroundColor(context.getResources().getColor(R.color.brown));


            } else {
                viewHolder.detail.setVisibility(View.VISIBLE);
               // viewHolder.txtxx.setVisibility(View.GONE);
                viewHolder.addOrd.setVisibility(View.GONE);
                viewHolder.addPlus.setVisibility(View.GONE);
                viewHolder.addMins.setVisibility(View.GONE);
                viewHolder.mQty.setVisibility(View.GONE);
             //   viewHolder.idLin.setBackgroundColor(context.getResources().getColor(R.color.brown));
            }
            viewHolder.img_txtc.setVisibility(View.VISIBLE);
            viewHolder.idLin.setVisibility(View.VISIBLE);
            viewHolder.ovrLay.setVisibility(View.GONE);
            viewHolder.imgThumbnail.setVisibility(View.VISIBLE);
            viewHolder.img_txtc.setVisibility(View.VISIBLE);
            viewHolder.mtop.setVisibility(View.VISIBLE);
            viewHolder.tvspecies.setText(nature.getName());
            Bitmap bitmap = Utils.decodeBase64(context, HomeBaseActivity.xyzHash.get(nature.getitemId()));
            if (bitmap == null)
                viewHolder.imgThumbnail.setImageBitmap(con);
            else
                viewHolder.imgThumbnail.setImageBitmap(bitmap);

            viewHolder.img_txtc.setPrice(Double.parseDouble(nature.getPrice().toString()));
            viewHolder.img_txtc.setStyle(18.0f, 16.0f, 14.0f, context.getResources().getColor(R.color.red), context.getResources().getColor(R.color.red), context.getResources().getColor(R.color.text_secondary));
            //viewHolder.imgThumbnail.startAnimation(bottomUp);
        }
//

    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgThumbnail;
        public TextView tvspecies;
        public EditText mQty;
        public TextView addPlus;
        public TextView addMins,txtxx;
        public CoolPriceView img_txtc;
        public Button addOrd,detail;
        public TextView ovrLay;
        public LinearLayout idLin, mtop;

        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = (ImageView) itemView.findViewById(R.id.img);
            tvspecies = (TextView) itemView.findViewById(R.id.titl);
            mQty = (EditText) itemView.findViewById(R.id.qty);
            addPlus = (TextView) itemView.findViewById(R.id.plu);
            addMins = (TextView) itemView.findViewById(R.id.mins);
            img_txtc = (CoolPriceView) itemView.findViewById(R.id.pric);
            addOrd = (Button) itemView.findViewById(R.id.addiy);
            detail = (Button) itemView.findViewById(R.id.detailsdiy);
            ovrLay = (TextView) itemView.findViewById(R.id.txto);
            txtxx= (TextView) itemView.findViewById(R.id.txtxx);
            idLin = (LinearLayout) itemView.findViewById(R.id.btm);
            mtop = (LinearLayout) itemView.findViewById(R.id.top);
//            lin1V.setMaxValue(50);
//            lin1V.setMinValue(1);
//            lin1V.setValue(1);
//            lin1V.setWrapSelectorWheel(false);
            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.addiy:


                            break;

                        case R.id.plu:

                            int xc = Integer.parseInt(mQty.getText().toString()) + 1;
                            mQty.setText("" + xc);


                            break;
                        case R.id.mins:
                            int xct = Integer.parseInt(mQty.getText().toString()) - 1;
                            if (xct <= 1) {
                                xct = 1;
                            }
                            mQty.setText("" + xct);
                            break;
                        case R.id.detailsdiy:
                        case R.id.img:
                            if (tvspecies.getVisibility() == View.VISIBLE && ovrLay.getVisibility() != View.VISIBLE) {
                                EndangeredItem tr = new EndangeredItem();
                                tr = mItems.get(getAdapterPosition());
                                int mypostion[] = {xPostu, getAdapterPosition()};
                                String[] myStrings = new String[]{tvspecies.getText().toString(), tr.getPrice().toString()};

                                Log.d("texttttt", "" + tr.getvName() + "______" + tr.getsetName());

                                if ((tr.getvName().equalsIgnoreCase("no")) && addOrd.getVisibility() == View.VISIBLE) {
                                    EndangeredItem nature = mItems.get(getPosition());
                                    Intent intX = new Intent(((Activity) context), ADDAlertActivity.class);
                                    Bitmap bitmap = ((BitmapDrawable) imgThumbnail.getDrawable()).getBitmap();
                                    ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bStream);
                                    byte[] byteArray = bStream.toByteArray();
                                    intX.putExtra("image", byteArray);
                                    intX.putExtra("stringRemrk", 1);
                                    intX.putExtra("isMain", 0);
                                    intX.putExtra("itemid",nature.getitemId());
                                    intX.putExtra("data", mypostion);
                                    intX.putExtra("strings", myStrings);
                                    ((Activity) context).startActivity(intX);
                                    ((Activity) context).overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);

                                } else {
                                    if (tr.getvName().equalsIgnoreCase("yes")) {
                                        //((Activity) context).getWindow().setExitTransition(new Explode());
                                        Intent intent = new Intent(((Activity) context), VariableChooseActivity.class);
//                                        Bitmap bitmap = ((BitmapDrawable) imgThumbnail.getDrawable()).getBitmap();
//                                        final View androidRobotView = imgThumbnail;
//                                        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
//                                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bStream);
//                                        byte[] byteArray = bStream.toByteArray();
                                       // intent.putExtra("image", byteArray);
                                        intent.putExtra("data", mypostion);
                                        intent.putExtra("strings", myStrings);

//                                        Log.i(myStrings + "---", ",,," + mypostion);
//                                        ActivityOptions options = ActivityOptions
//                                                .makeSceneTransitionAnimation(((Activity) context), androidRobotView, "THIA");
                                        ((Activity) context).startActivity(intent);
                                    }


//                                    } else if (tr.getsetName().equalsIgnoreCase("yes")) {
//                                        //((Activity) context).getWindow().setExitTransition(new Explode());
//                                        Intent intent = new Intent(((Activity) context), SetVarActivity.class);
//                                        Bitmap bitmap = ((BitmapDrawable) imgThumbnail.getDrawable()).getBitmap();
//                                        final View androidRobotView = imgThumbnail;
//                                        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
//                                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
//                                        byte[] byteArray = bStream.toByteArray();
//                                        intent.putExtra("image", byteArray);
//                                        intent.putExtra("data", mypostion);
//                                        intent.putExtra("strings", myStrings);
//
//                                        Log.i(myStrings + "---", ",,," + mypostion);
//                                        ActivityOptions options = ActivityOptions
//                                                .makeSceneTransitionAnimation(((Activity) context), androidRobotView, "THIA");
//                                        ((Activity) context).startActivity(intent);
//                                    }
                                }
                            }
                            break;

                    }


                }
            };
            imgThumbnail.setOnClickListener(clickListener);
            addOrd.setOnClickListener(clickListener);
            detail.setOnClickListener(clickListener);
            addPlus.setOnClickListener(clickListener);
            addMins.setOnClickListener(clickListener);
            //chkBox.setOnClickListener(clickListener);


        }
    }


}
