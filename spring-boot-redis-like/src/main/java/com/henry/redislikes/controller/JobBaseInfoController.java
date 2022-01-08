package com.henry.redislikes.controller;


import com.henry.redislikes.model.domain.AjaxResult;
import com.henry.redislikes.model.dto.JobBaseInfoDTO;
import com.henry.redislikes.model.vo.JobBaseInfoVO;
import com.henry.redislikes.service.JobBaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 工作任务详情基础信息表 前端控制器
 * </p>
 *
 * @author henry
 * @since 2021-03-24
 */
@RestController
@RequestMapping("/jobBaseInfo")
public class JobBaseInfoController extends BaseController{

    @Autowired
    private JobBaseInfoService jobBaseInfoService;

    @PostMapping("/list")
    public AjaxResult list(@RequestBody JobBaseInfoDTO jobBaseInfoDTO) {
        startPage();
        List<JobBaseInfoVO> list = jobBaseInfoService.selectJobList(jobBaseInfoDTO);
        return AjaxResult.success(getDataTable(list));
    }
}
