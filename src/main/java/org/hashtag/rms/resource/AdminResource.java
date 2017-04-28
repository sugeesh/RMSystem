package org.hashtag.rms.resource;

/**
 * @author Sugeesh Chandraweera
 */
public class AdminResource {
    private String tax;
    private String s_charge;

    public AdminResource() {
    }

    public AdminResource(String tax, String s_charge) {
        this.tax = tax;
        this.s_charge = s_charge;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getS_charge() {
        return s_charge;
    }

    public void setS_charge(String s_charge) {
        this.s_charge = s_charge;
    }
}
