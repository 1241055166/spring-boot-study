package com.henry.controller;

import com.henry.service.RankTopService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author: Henry
 * @Date: 2021/10/18 下午10:17
 * @Description: 新闻排行榜
 */
@RestController
@RequestMapping("/rankTop")
public class RankTopController {

    @Resource
    public RankTopService rankTopService;

    /**
     * http://localhost:8080/rankTop/zscoreNews?newTitle=《欢乐喜剧人7》全新赛制养成新人&score=434000
     * 创建新闻统计&实时统计新闻热度
     * @param newTitle 新闻标题 （根据业务也可以写成新闻ID）
     * @param score    热度增量
     * @return 给新闻一个增量以后，返回新闻的当前分数。
     */
    @GetMapping("/zscoreNews")
    public Map<String, Object> zscoreNews(
            @RequestParam(value = "newTitle", required = true) String newTitle,
            @RequestParam(value = "score", defaultValue = "1") double score
    ) {
        Map<String, Object> map = rankTopService.incrementScore(newTitle, score);
        return map;
    }

    /**
     * http://localhost:8080/rankTop/findNewByNewTitle?newTitle=《欢乐喜剧人7》全新赛制养成新人
     * 查询某条新闻的热度
     * @param newTitle
     * @return
     */
    @GetMapping("/findNewByNewTitle")
    public Map<String, Object> findNewByNewTitle(
            @RequestParam(value = "newTitle", required = true) String newTitle
    ) {
        Map<String, Object> map = rankTopService.findNewByNewTitle(newTitle);
        return map;
    }

    /**
     * http://localhost:8080/rankTop/createNewsTop?startPage=0&endPage=29
     * 对统计的新闻数据降序排序，并将[29,0]之间的数据放入新闻排行榜。（这个方法可以设置成定时任务。）
     * @param startPage 开始下标
     * @param endPage   结束下标
     * @return
     */
    @GetMapping("/createNewsTop")
    public Map<String, Object> createNewsTop(
            @RequestParam(value = "startPage", defaultValue = "0") int startPage,
            @RequestParam(value = "endPage", defaultValue = "29") int endPage
    ) {
        Map<String, Object> map = rankTopService.createNewsTop(startPage, endPage);
        return map;
    }

    /**
     * http://localhost:8080/rankTop/newsTop?startPage=0&endPage=9
     * 查看top热榜
     *
     * @param startPage 开始下标
     * @param endPage   结束下标
     * @return
     */
    @GetMapping("/newsTop")
    public Map<String, Object> newsTop(
            @RequestParam(value = "startPage", defaultValue = "0") int startPage,
            @RequestParam(value = "endPage", defaultValue = "9") int endPage
    ) {
        Map<String, Object> map = rankTopService.newsTop(startPage, endPage);
        return map;
    }

    /**
     * http://localhost:8080/rankTop/addTestData
     * 批量增加测试数据（新闻统计）
     */
    @PostMapping("/addTestData")
    public void addTestData(@RequestBody List<Map<String, Object>> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).get("value").toString());
            System.out.println(Double.parseDouble(list.get(i).get("score").toString()));
            zscoreNews(list.get(i).get("value").toString(), Double.parseDouble(list.get(i).get("score").toString()));
        }
    }
    /**新增测试数据：
     [
     {
     "score": 2356428.0,
     "value": "《蒙面唱将猜猜猜》第五季收官"
     },
     {
     "score": 2335456.0,
     "value": "《欢乐喜剧人7》全新赛制养成新人"
     },
     {
     "score": 987655.0,
     "value": "《星光大道》2020年度总决赛"
     },
     {
     "score": 954566.0,
     "value": "网易北京:重构梦幻西游项目"
     },
     {
     "score": 943665.0,
     "value": "神武惊现靓号：44488888"
     },
     {
     "score": 876653.0,
     "value": "小米手机：红米"
     },
     {
     "score": 875444.0,
     "value": "英特尔扩大外包"
     },
     {
     "score": 755656.0,
     "value": "多益广州举办神武4手游比赛"
     },
     {
     "score": 687466.0,
     "value": "亮剑重播超记录"
     },
     {
     "score": 567645.0,
     "value": "春节快到了"
     },
     {
     "score": 554342.0,
     "value": "购票狂潮"
     },
     {
     "score": 466654.0,
     "value": "达摩院旗下拥有20多位世界级的科学家"
     },
     {
     "score": 456666.0,
     "value": "NBA MVP候选人"
     },
     {
     "score": 435654.0,
     "value": "CBA最佳新秀"
     },
     {
     "score": 392875.0,
     "value": "数字货币新时代"
     },
     {
     "score": 300454.0,
     "value": "网易新手游即将发布"
     },
     {
     "score": 277654.0,
     "value": "CBA12强排名：四强格局已定"
     },
     {
     "score": 265656.0,
     "value": "用黑科技悄悄改变大众生活"
     },
     {
     "score": 234665.0,
     "value": "玉溪：致力打造全省数字经济第一城"
     },
     {
     "score": 234665.0,
     "value": "广西培育消费新业态新模式"
     },
     {
     "score": 234656.0,
     "value": "互联网产品是顺从用户？还是教育用户？"
     },
     {
     "score": 234564.0,
     "value": "蒋军：企业做强，做大跟产品的关系是什么？"
     },
     {
     "score": 234564.0,
     "value": "热搜第一！微信又有重大更新，这次有点炸"
     },
     {
     "score": 234555.0,
     "value": "成功的人，往往都读这“6”种书"
     },
     {
     "score": 134566.0,
     "value": "外地职工留苏州过年 落户加15分"
     },
     {
     "score": 133455.0,
     "value": "蒋军：成功创业的7种思维！创业者必读！"
     },
     {
     "score": 98554.0,
     "value": "阿里平头哥:首个RISC - V版安卓10系统顺畅运行"
     },
     {
     "score": 87654.0,
     "value": "不断增强人民群众就医获得感"
     },
     {
     "score": 54347.0,
     "value": "《星光大道》年度总冠军出炉"
     },
     {
     "score": 43335.0,
     "value": "流量应是榜样，榜样应成力量"
     },
     {
     "score": 23555.0,
     "value": "《山海情》：主旋律可以这样好看"
     },
     {
     "score": 23456.0,
     "value": "2021艺考新动向"
     }
     ]
     */
}
