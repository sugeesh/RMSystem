package org.hashtag.rms.service;

import org.hashtag.rms.model.CashDrawer;
import org.hashtag.rms.repository.CashDrawerRepository;
import org.hashtag.rms.repository.OrderRepository;
import org.hashtag.rms.resource.CashDrawerResource;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Buddhi on 3/16/2017.
 */
public class CashDrawerService {
    @Autowired
    private CashDrawerRepository cashDrawerRepository;

    public CashDrawer getCashDrawerForDate(Date date) {
//        CashDrawer cashDrawer = new CashDrawer();
        CashDrawer byDate = cashDrawerRepository.findByDate(date);

        return byDate;
    }

    public Object saveCashDrawer(CashDrawerResource cashDrawerResource) {
        CashDrawer cashDrawer = new CashDrawer();

        cashDrawer.setDate(cashDrawerResource.getDate());
        cashDrawer.setActualCash(cashDrawerResource.getActualCash());
        cashDrawer.setBalanceChange(cashDrawerResource.getBalanceChange());
        cashDrawer.setComment(cashDrawerResource.getComment());
        cashDrawer.setEndOfTheDayBalance(cashDrawerResource.getEndOfTheDayBalance());
        cashDrawer.setOrdersBalance(cashDrawerResource.getOrdersBalance());

        CashDrawer save = cashDrawerRepository.save(cashDrawer);
        return save;
    }

    public Object getCashDrawerForDateRange(String startDate, String endDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date startDateObj = dateFormat.parse(startDate);
        Date endDateObj = dateFormat.parse(endDate);

        List<CashDrawer> list = null;
        list = cashDrawerRepository.findByDateBetween(startDateObj, endDateObj);
        return list;
    }
}
