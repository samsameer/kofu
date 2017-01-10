package com.sd.epos.customeralpha.Models;

/**
 * Created by jabbir on 7/9/15.
 */
public class OrderDetailmodel {
    private String venNo;
    private String venName;
    private String wh_id;
    private String pc_code;
    private String doc_sno;
    private String sort_seq;
    private String doc_ref_no;
    private String item_id;
    private String item_no;
    private String item_descr;
    private String vend_part_no;
    private String uom;

    private String uom_cf;
    public String qty_order;




    public String getwh_id() {
        return wh_id;
    }

    public void setwh_id(String wh_id) {
        this.wh_id = wh_id;
    }

    public String getsort_seq() {
        return sort_seq;
    }

    public void setsort_seq(String sort_seq) {
        this.sort_seq = sort_seq;
    }

    public String getitem_id() {
        return item_id;
    }

    public void setitem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getvenNo() {
        return venNo;
    }

    public void setvenNo(String venNo) {
        this.venNo = venNo;
    }

    public String getvenName() {
        return venName;
    }

    public void setvenName(String venName) {
        this.venName = venName;
    }


    public String getitem_descr() {
        return item_descr;
    }

    public void setitem_descr(String item_descr) {
        this.item_descr = item_descr;
    }

    public String getvend_part_no() {
        return vend_part_no;
    }

    public void setvend_part_no(String vend_part_no) {
        this.vend_part_no = vend_part_no;
    }


    public String getpc_code() {
        return pc_code;
    }

    public void setpc_code(String pc_code) {
        this.pc_code = pc_code;
    }

    public String getdoc_sno() {
        return doc_sno;
    }

    public void setdoc_sno(String doc_sno) {
        this.doc_sno = doc_sno;
    }

/********************/

public String getdoc_ref_no() {
    return doc_ref_no;
}

    public void setdoc_ref_no(String doc_ref_no) {
        this.doc_ref_no = doc_ref_no;
    }

    public String getitem_no() {
        return item_no;
    }

    public void setitem_no(String item_no) {
        this.item_no = item_no;
    }


    public String getuom() {
        return uom;
    }

    public void setuom(String uom) {
        this.uom = uom;
    }

    public String getqty_order() {
        return qty_order;
    }

    public void setqty_order(String qty_order) {
        this.qty_order = qty_order;
    }






}
