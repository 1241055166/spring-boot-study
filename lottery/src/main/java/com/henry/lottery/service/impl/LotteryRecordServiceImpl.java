package com.henry.lottery.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.henry.lottery.dal.mapper.LotteryRecordMapper;
import com.henry.lottery.dal.model.LotteryRecord;
import com.henry.lottery.service.ILotteryRecordService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 */
@Service
public class LotteryRecordServiceImpl extends ServiceImpl<LotteryRecordMapper, LotteryRecord> implements ILotteryRecordService {

}
