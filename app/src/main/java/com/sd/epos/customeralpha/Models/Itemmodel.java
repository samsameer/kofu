package com.sd.epos.customeralpha.Models;

/**
 * Created by jabbir on 9/9/15.
 */
public class Itemmodel {

    private String item_no;
    private String item_id;
    private String item_descr;
    private String cat_code;
    private String uom;

    public String getitem_no() {
        return item_no;
    }

    public void setitem_no(String item_no) {
        this.item_no = item_no;
    }

    public String getitem_ide() {
        return item_id;
    }

    public void setitem_id(String item_id) {
        this.item_id = item_id;
    }


    public String getitem_descr() {
        return item_descr;
    }

    public void setitem_descr(String item_descr) {
        this.item_descr = item_descr;
    }


    public String getcat_code() {
        return cat_code;
    }

    public void setcat_code(String cat_code) {
        this.cat_code = cat_code;
    }


    public String getuom() {
        return uom;
    }

    public void setuom(String uom) {
        this.uom = uom;
    }


}
