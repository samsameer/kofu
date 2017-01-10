package com.sd.epos.customeralpha.common;

/**
 * Created by jabbir on 29/6/15.
 */
public class TableNames {

    private String mTablno;

    //    "table_no": "T2",
//            "table_name": "TABLE 2",
//            "status": "O",
//            "color": "GR",
//            "seq_no": 2
    private String mtable_name;
    private String status;
    private String mColor;
    private String no_of_cover;
    public String getName() {
        return mTablno;
    }

    public void setName(String name) {
        this.mTablno = name;
    }


    public String getPrice() {
        return mtable_name;
    }

    public void setPrice(String price) {
        this.mtable_name = price;
    }



    public String getno_of_cover() {
        return no_of_cover;
    }

    public void setno_of_cover(String no_of_cover) {
        this.no_of_cover = no_of_cover;
    }


    public String getQty() {
        return status;
    }

    public void setQty(String Qty) {
        this.status = Qty;
    }

    public String getcolory() {
        return mColor;
    }

    public void setcolor(String colr) {
        this.mColor = colr;
    }

}
