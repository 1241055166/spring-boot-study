package com.henry.lottery.service.impl.stock;


import com.henry.lottery.dal.model.Lottery;
import com.henry.lottery.dal.model.LotteryItem;
import lombok.Data;

@Data
public class RewardContext {

    private Lottery lottery;

    private LotteryItem lotteryItem;

    private String key;

    private String accountIp;

    private String prizeName;

    private Integer level;

    private Integer prizeId;
}
