package com.henry.lottery.service.impl;


import com.henry.lottery.dal.mapper.LotteryRecordMapper;
import com.henry.lottery.dal.model.LotteryItem;
import com.henry.lottery.dal.model.LotteryRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class AsyncLotteryRecordTask {

    @Autowired
    LotteryRecordMapper lotteryRecordMapper;

    @Async("lotteryServiceExecutor")
    public void saveLotteryRecord(String accountIp, LotteryItem lotteryItem, String prizeName) {
        log.info(Thread.currentThread().getName() + "---saveLotteryRecord");
        //存储中奖信息
        LotteryRecord record = new LotteryRecord();
        record.setAccountIp(accountIp);
        record.setItemId(lotteryItem.getId());
        record.setPrizeName(prizeName);
        record.setCreateTime(LocalDateTime.now());
        lotteryRecordMapper.insert(record);
    }
}
