package com.sd.epos.customeralpha.Models;

/**
 * Created by jabbir on 7/9/15.
 */
public class Ordermodel {
    private String dCno;
    private String acPeriod;
    private String docDate;
    private String venNo;
    private String venName;
    private String status;
    private String conStatus;
    private String docRefre;
    private String reqBy;
    public String ref_Info;


    public String getdCno() {
        return dCno;
    }

    public void setdCno(String dCno) {
        this.dCno = dCno;
    }

    public String getacPeriod() {
        return acPeriod;
    }

    public void setacPeriod(String acPeriod) {
        this.acPeriod = acPeriod;
    }

    public String getdocDate() {
        return docDate;
    }

    public void setdocDate(String docDate) {
        this.docDate = docDate;
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

    public String getstatus() {
        return status;
    }

    public void setstatus(String status) {
        this.status = status;
    }

    public String getconStatus() {
        return conStatus;
    }

    public void setconStatus(String conStatus) {
        this.conStatus = conStatus;
    }


    public String getdocRefre() {
        return docRefre;
    }

    public void setdocRefre(String docRefre) {
        this.docRefre = docRefre;
    }

    public String getreqBy() {
        return reqBy;
    }

    public void setreqBy(String reqBy) {
        this.reqBy = reqBy;
    }

    public void setref_Info(String ref_Info) {
        this.ref_Info = ref_Info;
    }


}
