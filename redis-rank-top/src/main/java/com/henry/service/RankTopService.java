package com.henry.service;

import java.util.Map;

/**
 * @Author: Henry
 * @Date: 2021/10/18 下午10:08
 * @Description:
 */
public interface RankTopService {

    Map<String, Object> incrementScore(String newTitle,double zscore);
    Map<String, Object> findNewByNewTitle(String newTitle);
    Map<String, Object> createNewsTop(int startPage, int endPage);
    Map<String, Object> newsTop(int startPage, int endPage);

}
