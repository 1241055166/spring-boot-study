package com.henrt.ik;


import org.apache.commons.lang3.StringUtils;
import org.wltea.analyzer.cfg.DefaultConfig;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

/**
 * @Description:  使用 IK 分词器进行词频统计
 * @Author: huangjw-b
 * @Date: 2021/12/14
 */
public class ikCountTermFrequency {

    /**
     * 全文本词频统计
     * 单纯统计词频
     *
     * @param content  文本内容
     * @param useSmart 是否使用 smart
     * @return 词，词频
     * @throws IOException
     */
    private static Map<String, Integer> countTermFrequency(String content, Boolean useSmart) throws IOException {
        // 输出结果 Map
        Map<String, Integer> frequencies = new HashMap<>();
        if (StringUtils.isBlank(content)) {
            return frequencies;
        }
        DefaultConfig conf = new DefaultConfig();
        conf.setUseSmart(useSmart);
        // 使用 IKSegmenter 初始化文本信息并加载词典
        IKSegmenter ikSegmenter = new IKSegmenter(new StringReader(content), conf);
        Lexeme lexeme;
        while ((lexeme = ikSegmenter.next()) != null) {
            if (lexeme.getLexemeText().length() > 1) {// 过滤单字，也可以过滤其他内容，如数字和单纯符号等内容
                final String term = lexeme.getLexemeText();
                // Map 累加操作
                frequencies.compute(term, (k, v) -> {
                    if (v == null) {
                        v = 1;
                    } else {
                        v += 1;
                    }
                    return v;
                });
            }
        }
        return frequencies;
    }

    /**
     * 文本列表词频和词文档频率统计
     * 统计词频和文档频率
     *
     * @param docs     文档列表
     * @param useSmart 是否使用只能分词
     * @return 词频列表 词-[词频,文档频率]
     * @throws IOException
     */
    private static Map<String, Integer[]> countTFDF(List<String> docs, boolean useSmart) throws IOException {
        // 输出结果 Map
        Map<String, Integer[]> frequencies = new HashMap<>();
        for (String doc : docs) {
            if (StringUtils.isBlank(doc)) {
                continue;
            }
            DefaultConfig conf = new DefaultConfig();
            conf.setUseSmart(useSmart);
            // 使用 IKSegmenter 初始化文本信息并加载词典
            IKSegmenter ikSegmenter = new IKSegmenter(new StringReader(doc), conf);
            Lexeme lexeme;
            // 用于文档频率统计的 Set
            Set<String> terms = new HashSet<>();
            while ((lexeme = ikSegmenter.next()) != null) {
                if (lexeme.getLexemeText().length() > 1) {
                    final String text = lexeme.getLexemeText();
                    // 进行词频统计
                    frequencies.compute(text, (k, v) -> {
                        if (v == null) {
                            v = new Integer[]{1, 0};
                        } else {
                            v[0] += 1;
                        }
                        return v;
                    });
                    terms.add(text);
                }
            }
            // 进行文档频率统计：无需初始化 Map，统计词频后 Map 里面必有该词记录
            for (String term : terms) {
                frequencies.get(term)[1] += 1;
            }
        }
        return frequencies;
    }

    /**
     * 按出现次数，从高到低排序取 TopN (采用小顶堆的方式)
     *
     *  获取词云 TopN 个词
     *   获取 TopN 个词用于词云展示有多种排序方式，可以直接根据词频、文档频率或者 TF-IDF 等算法进行排序，本文仅根据词频求取 TopN。M 个数字获取 TopN 有以下算法：
     *   M 小 N 小：快速选择算法
     *   M 大 N 小：小顶堆
     *   M 大 N 大：归并排序
     * @param data 词和排序数字对应的 Map
     * @param TopN 词云展示的 TopN
     * @return 前 N 个词和排序值
     */
    private static List<Map.Entry<String, Integer>> order(Map<String, Integer> data, int topN) {
        PriorityQueue<Map.Entry<String, Integer>> priorityQueue = new PriorityQueue<>(data.size(), new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            priorityQueue.add(entry);
        }
        //TODO 当前100词频一致时(概率极低)的处理办法，if( list(0).value == list(99).value ){xxx}
        List<Map.Entry<String, Integer>> list = new ArrayList<>();
        //统计结果队列size和topN值取较小值列表
        int size = priorityQueue.size() <= topN ? priorityQueue.size() : topN;
        for (int i = 0; i < size; i++) {
            list.add(priorityQueue.remove());
        }
        return list;
    }

    public static void main(String[] args) throws IOException {
        String content = "/Users/huangjiawen/Desktop/111.txt";
        countTermFrequency("content",true);
    }


}
