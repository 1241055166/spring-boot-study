package com.henry.redislikes.task;

import com.henry.redislikes.service.UserLikeDetailService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @className: LikeTask
 * @description: 点赞写如数据库定时任务
 * @author: henry
 */
@Slf4j
public class LikeTask extends QuartzJobBean {

    @Autowired
    UserLikeDetailService userLikeDetailService;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        log.info("LikeTask-------- {}", sdf.format(new Date()));

        //将 Redis 里的点赞信息同步到数据库里
        userLikeDetailService.transLikedFromRedis2DB();
        userLikeDetailService.transLikedCountFromRedis2DB();
    }
}
