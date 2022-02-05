package com.henry.lottery.controller;


import com.henry.lottery.constants.ReturnCodeEnum;
import com.henry.lottery.dal.model.LotteryRecord;
import com.henry.lottery.dto.ResultResp;
import com.henry.lottery.service.ILotteryRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/lottery-record")
public class LotteryRecordController {

    @Autowired
    ILotteryRecordService lotteryRecordService;

    @GetMapping
    public ResultResp<List<LotteryRecord>> records() {
        List<LotteryRecord> records = lotteryRecordService.list();
        ResultResp<List<LotteryRecord>> resultResp = new ResultResp<>();
        resultResp.setMsg(ReturnCodeEnum.SUCCESS.getMsg());
        resultResp.setCode(ReturnCodeEnum.SUCCESS.getCode());
        resultResp.setResult(records);
        return resultResp;
    }
}
