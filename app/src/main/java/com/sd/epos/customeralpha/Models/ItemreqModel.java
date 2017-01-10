package com.sd.epos.customeralpha.Models;

import android.graphics.Bitmap;

/**
 * Created by jabbir on 21/8/15.
 */
public class ItemreqModel {
    private String sNo;
    private String itemId;
    private String itemNo;
    private String Qty;
    private String price;
    private String remark;
    private String npCover;
    private String mName;
    private Bitmap mThumbnail;
    public String getReddm;
    public String tAway;
    private String userId;



    public String getuserId() {
        return userId;
    }

    public void setuserId(String userId) {
        this.userId = userId;
    }

    public String getsNo() {
        return sNo;
    }

    public void setsNo(String sNo) {
        this.sNo = sNo;
    }

    public String getitemId() {
        return itemId;
    }

    public void setitemId(String itemId) {
        this.itemId = itemId;
    }

    public String getitemNo() {
        return itemNo;
    }

    public void setitemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String Qty) {
        this.Qty = Qty;
    }

    public String getprice() {
        return price;
    }

    public void setprice(String price) {
        this.price = price;
    }

    public String getremark() {
        return remark;
    }

    public void setremark(String remark) {
        this.remark = remark;
    }


    public String getnpCover() {
        return npCover;
    }

    public void setnpCover(String npCover) {
        this.npCover = npCover;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public Bitmap getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.mThumbnail = thumbnail;
    }

    public void setreedem(String reedem) {
        this.getReddm = reedem;
    }

    public String getreedem() {
        return getReddm;
    }

    public void settAway(String tAway) {
        this.tAway = tAway;
    }

    public String gettAway() {
        return tAway;
    }

}
