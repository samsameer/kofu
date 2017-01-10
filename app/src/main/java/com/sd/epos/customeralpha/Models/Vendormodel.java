package com.sd.epos.customeralpha.Models;

/**
 * Created by jabbir on 9/9/15.
 */
public class Vendormodel {

    private String vend_no;
    private String vend_name;
    private String curr_code;
    private String tax_type;

//

    public String getvend_no() {
        return vend_no;
    }

    public void setvend_no(String vend_no) {
        this.vend_no = vend_no;
    }

    public String getvend_name() {
        return vend_name;
    }

    public void setvend_name(String vend_name) {
        this.vend_name = vend_name;
    }


    public String getcurr_code() {
        return curr_code;
    }

    public void setcurr_code(String curr_code) {
        this.curr_code = curr_code;
    }


    public String gettax_type() {
        return tax_type;
    }

    public void settax_type(String tax_type) {
        this.tax_type = tax_type;
    }


}
