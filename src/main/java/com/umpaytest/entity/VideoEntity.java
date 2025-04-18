package com.umpaytest.entity;

import lombok.Data;

/**
 * @author Hu.ChangLiang
 * @date 2025/2/25 15:45
 */
@Data
public class VideoEntity {
    private Integer learnTime;

    private Double doubleLearnTime;

    public Integer getLearnTime() {
        if (doubleLearnTime != null) {
            learnTime = doubleLearnTime.intValue();
        }
        return learnTime;
    }
}
