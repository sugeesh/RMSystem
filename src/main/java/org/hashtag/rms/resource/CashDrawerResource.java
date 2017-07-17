package org.hashtag.rms.resource;

import org.hashtag.rms.model.CashDrawer;

import java.util.Date;

/**
 * @author Sugeesh Chandraweera
 */
public class CashDrawerResource {
    private int id;
    private Date date;
    private double startingCash;
    private double ordersBalance;
    private double endOfTheDayBalance;
    private double actualCash;
    private double balanceChange;
    private String comment;

    public CashDrawerResource() {
    }

    public CashDrawerResource(int id, Date date, double startingCash, double ordersBalance, double endOfTheDayBalance, double actualCash, double balanceChange, String comment) {
        this.id = id;
        this.date = date;
        this.startingCash = startingCash;
        this.ordersBalance = ordersBalance;
        this.endOfTheDayBalance = endOfTheDayBalance;
        this.actualCash = actualCash;
        this.balanceChange = balanceChange;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getStartingCash() {
        return startingCash;
    }

    public void setStartingCash(double startingCash) {
        this.startingCash = startingCash;
    }

    public double getOrdersBalance() {
        return ordersBalance;
    }

    public void setOrdersBalance(double ordersBalance) {
        this.ordersBalance = ordersBalance;
    }

    public double getEndOfTheDayBalance() {
        return endOfTheDayBalance;
    }

    public void setEndOfTheDayBalance(double endOfTheDayBalance) {
        this.endOfTheDayBalance = endOfTheDayBalance;
    }

    public double getActualCash() {
        return actualCash;
    }

    public void setActualCash(double actualCash) {
        this.actualCash = actualCash;
    }

    public double getBalanceChange() {
        return balanceChange;
    }

    public void setBalanceChange(double balanceChange) {
        this.balanceChange = balanceChange;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public CashDrawerResource convertToResource(CashDrawer cashDrawer) {
        CashDrawerResource cashDrawerResource = new CashDrawerResource();
        cashDrawerResource.setId(cashDrawer.getId());
        cashDrawerResource.setDate(cashDrawer.getDate());
        cashDrawerResource.setStartingCash(cashDrawer.getStartingCash());
        cashDrawerResource.setOrdersBalance(cashDrawer.getOrdersBalance());
        cashDrawerResource.setEndOfTheDayBalance(cashDrawer.getEndOfTheDayBalance());
        cashDrawerResource.setActualCash(cashDrawer.getActualCash());
        cashDrawerResource.setBalanceChange(cashDrawer.getBalanceChange());
        cashDrawerResource.setComment(cashDrawer.getComment());
        return cashDrawerResource;
    }


}

