package com.sd.epos.customeralpha.Models;

/**
 * Created by jabbir on 9/9/15.
 */
public class UOmmodel {

    private String item_no;
    private String item_id;
    private String s_no;
    private String uom_cf;
    private String is_default;
    private String uom;

//


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


    public String gets_no() {
        return s_no;
    }

    public void sets_no(String s_no) {
        this.s_no = s_no;
    }


    public String getuom_cf() {
        return uom_cf;
    }

    public void setuom_cf(String uom_cf) {
        this.uom_cf = uom_cf;
    }


    public String getuom() {
        return uom;
    }

    public void setuom(String uom) {
        this.uom = uom;
    }

    public String getis_default() {
        return is_default;
    }

    public void setis_default(String is_default) {
        this.is_default = is_default;
    }

}
