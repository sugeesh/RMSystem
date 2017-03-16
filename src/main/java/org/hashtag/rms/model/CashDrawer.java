package org.hashtag.rms.model;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Sugeesh Chandraweera
 */
@Entity
@Table(name = "CashDrawer")
public class CashDrawer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CashDrawerID")
    private Integer id;

    @Column(name = "Date")
    private Date date;

    @Column(name = "StartingCash")
    private Double startingCash;

    @Column(name = "OrdersBalance")
    private Double ordersBalance;

    @Column(name = "EndOfTheDayBalance")
    private Double endOfTheDayBalance;

    @Column(name = "ActualCash")
    private Double actualCash;

    @Column(name = "BalanceChange")
    private Double balanceChange;

    @Column(name = "Comment")
    private String comment;

    public CashDrawer() {
    }

    public CashDrawer(Date date, Double startingCash, Double ordersBalance, Double endOfTheDayBalance, Double actualCash, Double balanceChange, String comment) {
        this.date = date;
        this.startingCash = startingCash;
        this.ordersBalance = ordersBalance;
        this.endOfTheDayBalance = endOfTheDayBalance;
        this.actualCash = actualCash;
        this.balanceChange = balanceChange;
        this.comment = comment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getStartingCash() {
        return startingCash;
    }

    public void setStartingCash(Double startingCash) {
        this.startingCash = startingCash;
    }

    public Double getOrdersBalance() {
        return ordersBalance;
    }

    public void setOrdersBalance(Double ordersBalance) {
        this.ordersBalance = ordersBalance;
    }

    public Double getEndOfTheDayBalance() {
        return endOfTheDayBalance;
    }

    public void setEndOfTheDayBalance(Double endOfTheDayBalance) {
        this.endOfTheDayBalance = endOfTheDayBalance;
    }

    public Double getActualCash() {
        return actualCash;
    }

    public void setActualCash(Double actualCash) {
        this.actualCash = actualCash;
    }

    public Double getBalanceChange() {
        return balanceChange;
    }

    public void setBalanceChange(Double balanceChange) {
        this.balanceChange = balanceChange;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
