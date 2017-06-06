package org.hashtag.rms.resource;

/**
 * @author Sugeesh Chandraweera
 */
public class AdminResource {
    private String tax_dinein;
    private String s_charge_dinein;
    private String tax_takeaway;
    private String s_charge_takeaway;

    public AdminResource() {
    }

    public AdminResource(String tax_dinein, String s_charge_dinein, String tax_takeaway, String s_charge_takeaway) {
        this.tax_dinein = tax_dinein;
        this.s_charge_dinein = s_charge_dinein;
        this.tax_takeaway = tax_takeaway;
        this.s_charge_takeaway = s_charge_takeaway;
    }

    public String getTax_dinein() {
        return tax_dinein;
    }

    public void setTax_dinein(String tax_dinein) {
        this.tax_dinein = tax_dinein;
    }

    public String getS_charge_dinein() {
        return s_charge_dinein;
    }

    public void setS_charge_dinein(String s_charge_dinein) {
        this.s_charge_dinein = s_charge_dinein;
    }

    public String getTax_takeaway() {
        return tax_takeaway;
    }

    public void setTax_takeaway(String tax_takeaway) {
        this.tax_takeaway = tax_takeaway;
    }

    public String getS_charge_takeaway() {
        return s_charge_takeaway;
    }

    public void setS_charge_takeaway(String s_charge_takeaway) {
        this.s_charge_takeaway = s_charge_takeaway;
    }
}
