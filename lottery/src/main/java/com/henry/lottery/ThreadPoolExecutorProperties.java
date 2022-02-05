package com.henry.lottery;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "async.executor.thread")
public class ThreadPoolExecutorProperties {
    private int corePoolSize;
    private int maxPoolSize;
    private int queueCapacity;
    private String namePrefix;
}
