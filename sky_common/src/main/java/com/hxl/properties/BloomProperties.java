package com.hxl.properties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "bloom-filter")
@Data
public class BloomProperties {
    //期望插入的元素数量
    private Long expectedInsertions;

    //误判率
    private double fpp;
}
