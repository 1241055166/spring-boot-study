package com.henry.redislikes.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.henry.redislikes.model.dto.JobBaseInfoDTO;
import com.henry.redislikes.model.entity.JobBaseInfo;
import com.henry.redislikes.model.vo.JobBaseInfoVO;

import java.util.List;

/**
 * <p>
 * 工作任务详情基础信息表 服务类
 * </p>
 *
 * @author henry
 * @since 2021-03-24
 */
public interface JobBaseInfoService extends IService<JobBaseInfo> {

    List<JobBaseInfoVO> selectJobList(JobBaseInfoDTO jobBaseInfoDTO);
}
