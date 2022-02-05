package com.henry.lottery.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.henry.lottery.dal.model.Lottery;
import com.henry.lottery.dto.DoDrawDto;

/**
 * <p>
 * 服务类
 * </p>
 */
public interface ILotteryService extends IService<Lottery> {

    void doDraw(DoDrawDto drawDto) throws Exception;
}
