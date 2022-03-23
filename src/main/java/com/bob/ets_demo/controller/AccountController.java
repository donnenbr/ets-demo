package com.bob.ets_demo.controller;

import com.bob.ets_demo.EtsDemoApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Sort;

import com.bob.ets_demo.model.*;

import java.math.BigDecimal;

import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;

@RestController
public class AccountController {

    Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    AccountRepository accountRepo;
    @Autowired
    TransactionDateRepository transDateRepo;
    @Autowired
    TransactionRepository transRepo;

    @GetMapping("/doTransaction")
    public ModelAndView doTransaction() {
        ModelAndView modelAndView = new ModelAndView("DoTransaction");
        return modelAndView;
    }

    // just for testing
    @GetMapping("account/{userId}")
    public ModelAndView getAccount(@PathVariable String userId) {
        ModelAndView modelAndView = new ModelAndView("AccountInfo");
        AccountModel acct = accountRepo.findById(userId).get();
        // deal with not found
        if (acct == null) {
            modelAndView.addObject("errorMessage", "Account is invalid");
            return modelAndView;
        }
        // get the most recent transaction for the user.
        // one way, which brings back ALL the transactions and just grabs the first one.  not efficient
        Iterable<TransactionModel> transactionRows = transRepo.findByUserId(userId, Sort.by(Sort.Direction.DESC, "transactionDateTime"));
        Iterator<TransactionModel> transactionIter = transactionRows.iterator();
        logger.info("*** trans " + transactionIter);

        // better way which should return just one (or no) rows
        Optional<TransactionModel> latesTransaction = transRepo.findFirstByUserIdOrderByTransactionDateTimeDesc(userId);
        logger.info("*** latest trans " + latesTransaction);
        Map<String,Object> map = new HashMap<>();
        map.put("userId", acct.getUserId());
        map.put("balance", acct.getTotalAmount());
        /*
        if (iter.hasNext()) {
            TransactionModel transModel = iter.next();
            map.put("lastTransactionTimestamp", transModel.getTransactionDateTime());
            map.put("lastTransactionAmount", transModel.getTransactionAmount());
        }
         */
        if (latesTransaction.isPresent()) {
            map.put("lastTransactionTimestamp", latesTransaction.get().getTransactionDateTime());
            map.put("lastTransactionAmount",  latesTransaction.get().getTransactionAmount());
        }
        modelAndView.addObject("data", map);
        return modelAndView;
    }
    @GetMapping("accounts")
    public Iterable<AccountModel> getAccounts() {
        Iterable<AccountModel> acctList = accountRepo.findAll();
        return acctList;
    }

    @PostMapping("executeTransaction")
    public Map<String,Object> executeTransaction(@RequestBody TransactionModel transModel) {
        logger.info("*** transaction " + transModel);
        Map<String,Object> map = new HashMap<>();
        // return error if amount is null
        if (transModel.getUserId() == null || transModel.getUserId().trim().length() < 1 ||
                transModel.getTransactionAmount() == null) {
            map.put("error", "userId and transaction amount m,ust be specified");
            return map;
        }
        Optional<AccountModel> acctOpt = accountRepo.findById(transModel.getUserId());
        // return error if account is null
        if (acctOpt.isEmpty()) {
            map.put("error", "userId is invalid");
            return map;
        }
        AccountModel acct = acctOpt.get();
        BigDecimal newBalance = acct.getTotalAmount().add(transModel.getTransactionAmount());
        if (newBalance.doubleValue() < 0) {
            map.put("error", "Insufficient funds!!!");
            return map;
        }
        try {
            acct.setTotalAmount(newBalance);
            if (transModel.getTransactionDateTime() == null) {
                transModel.setTransactionDateTime(new Timestamp(System.currentTimeMillis()));
            }
            saveTransaction(acct, transModel);
        }
        catch (Throwable t) {
            logger.error("*** ERROR - " + t);
            map.put("error", t.toString());
            return map;
        }
        map.put("balance", acct.getTotalAmount());
        return map;
    }

    @Transactional
    private void saveTransaction(AccountModel acct, TransactionModel transModel) {
        // temp - set id of transaction recs
        transModel.setId(System.currentTimeMillis());
        transRepo.save(transModel);
        TransactionDateModel transDate = new TransactionDateModel(System.currentTimeMillis(), transModel.getUserId(), transModel.getTransactionDateTime());
        transDateRepo.save(transDate);
        accountRepo.save(acct);
    }
}
