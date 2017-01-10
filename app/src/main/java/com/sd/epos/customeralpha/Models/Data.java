package com.sd.epos.customeralpha.Models;

/**
 * Created by jabbir on 17/6/15.
 */
public class Data {
    private boolean isSelected;
    //    "remarks_group": "FOODREM",
//            "description": "FOOD REMARKS",
//            "remarks_code": "LESSOIL",
//            "remarks": "LESS OIL"
    private String remarks;
    private String remarks_group;

    public String getremaroup() {
        return remarks_group;
    }

    public void setremaroup(String remaroup) {
        this.remarks_group = remaroup;
    }


    public String getremarks() {
        return remarks;
    }

    public void setremarks(String remarks) {
        this.remarks = remarks;
    }
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }


}