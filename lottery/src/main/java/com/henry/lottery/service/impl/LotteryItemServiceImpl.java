package com.henry.lottery.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.henry.lottery.dal.mapper.LotteryItemMapper;
import com.henry.lottery.dal.model.LotteryItem;
import com.henry.lottery.service.ILotteryItemService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 */
@Service
public class LotteryItemServiceImpl extends ServiceImpl<LotteryItemMapper, LotteryItem> implements ILotteryItemService {

}
