package com.sd.epos.customeralpha.Models;

import android.graphics.Bitmap;

/**
 * Created by jabbir on 8/6/15.
 */
public class NamesTopup {
    private String mName;
    private Bitmap mThumbnail;
    private String mPrice;
    private String Qty;
    private String grpName;
    private String cdef_flag;
    private String cseq_no;
    private String citem_no;
    private String citem_id;
    private String cuom;
    private String cremarks;
    private String noCunt;

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

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        this.mPrice = price;
    }

    public String getcdef_flag() {
        return cdef_flag;
    }

    public void setcdef_flag(String cdef_flag) {
        this.cdef_flag = cdef_flag;
    }

    public String getcuom() {
        return cuom;
    }

    public void setcuom(String cuom) {
        this.cuom = cuom;
    }

    public String getcseq_no() {
        return cseq_no;
    }

    public void setcseq_no(String cseq_no) {
        this.cseq_no = cseq_no;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String Qty) {
        this.Qty = Qty;
    }

    public String getgrpName() {
        return grpName;
    }

    public void setgrpName(String grpName) {
        this.grpName = grpName;
    }

    public String getcitem_no() {
        return citem_no;
    }

    public void setcitem_no(String citem_no) {
        this.citem_no = citem_no;
    }


    public String getnoCunt() {
        return noCunt;
    }

    public void setnoCunt(String noCunt) {
        this.noCunt = noCunt;
    }


    public String getcitem_id() {
        return citem_id;
    }

    public void setcitem_id(String citem_id) {
        this.citem_id = citem_id;
    }


    public String getcremarks() {
        return cremarks;
    }

    public void setcremarks(String cremarks) {
        this.cremarks = cremarks;
    }
}
