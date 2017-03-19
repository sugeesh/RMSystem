package org.hashtag.rms.service;

import org.hashtag.rms.model.CashDrawer;
import org.hashtag.rms.repository.CashDrawerRepository;
import org.hashtag.rms.repository.OrderRepository;
import org.hashtag.rms.resource.CashDrawerResource;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    public CashDrawer getCashDrawerForToday() throws ParseException {

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
//        cal.add(Calendar.DATE, -1);
        Date yesterday = cal.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        List<CashDrawer> cashDrawerForDateRange = getCashDrawerForDateRange(dateFormat.format(yesterday), dateFormat.format(yesterday));
        if(cashDrawerForDateRange.size()>0) {
            return cashDrawerForDateRange.get(0);
        }
        return null;
    }


    public CashDrawer getCashDrawerForYesterday() throws ParseException {
        List<CashDrawer> cashDrawerForDateRange = getCashDrawerLastRecord();
        if(cashDrawerForDateRange.size()>0) {
            return cashDrawerForDateRange.get(0);
        }
        return null;
    }

    public Object saveCashDrawer(CashDrawerResource cashDrawerResource) {
        CashDrawer cashDrawer = new CashDrawer();

        cashDrawer.setDate(new Date());
        cashDrawer.setStartingCash(cashDrawerResource.getStartingCash());
        cashDrawer.setActualCash(cashDrawerResource.getActualCash());
        cashDrawer.setBalanceChange(cashDrawerResource.getBalanceChange());
        cashDrawer.setComment(cashDrawerResource.getComment());
        cashDrawer.setEndOfTheDayBalance(cashDrawerResource.getEndOfTheDayBalance());
        cashDrawer.setOrdersBalance(cashDrawerResource.getOrdersBalance());

        CashDrawer save = cashDrawerRepository.save(cashDrawer);
        return save;
    }

    public Object updateCashDrawer(CashDrawerResource cashDrawerResource) {
        CashDrawer cashDrawer = new CashDrawer();

        cashDrawer.setId(cashDrawerResource.getId());
        cashDrawer.setDate(new Date());
        cashDrawer.setStartingCash(cashDrawerResource.getStartingCash());
        cashDrawer.setActualCash(cashDrawerResource.getActualCash());
        cashDrawer.setBalanceChange(cashDrawerResource.getBalanceChange());
        cashDrawer.setComment(cashDrawerResource.getComment());
        cashDrawer.setEndOfTheDayBalance(cashDrawerResource.getEndOfTheDayBalance());
        cashDrawer.setOrdersBalance(cashDrawerResource.getOrdersBalance());

        CashDrawer save = cashDrawerRepository.save(cashDrawer);
        return save;
    }

    public List<CashDrawer> getCashDrawerForDateRange(String startDate, String endDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


        Date startDateObj = dateFormat.parse(startDate);
        Date endDateObj = dateFormat.parse(endDate);

        Calendar cal = Calendar.getInstance();
        cal.setTime(endDateObj);
        cal.set(Calendar.HOUR_OF_DAY,23);
        cal.set(Calendar.MINUTE,59);
        cal.set(Calendar.SECOND,59);
        endDateObj = cal.getTime();

        List<CashDrawer> list = cashDrawerRepository.findByDateBetween(startDateObj, endDateObj);
        return list;
    }


    public List<CashDrawer> getCashDrawerLastRecord() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<CashDrawer> list = null;
        list = cashDrawerRepository.findAllByOrderByDateDesc();
        return list;
    }
}
