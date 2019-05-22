package com.umpaytest.enums;

import com.umpaytest.entity.User;
import lombok.Data;

/**
 * @author: Hucl
 * @date: 2019/5/15 11:01
 * @description:
 */
@Data
public class Label {
    private String score;

    private long count;

    private String scoreBox;

    public Label() {
    }

    public Label(String score, long count, String scoreBox) {
        this.score = score;
        this.count = count;
        this.scoreBox = scoreBox;
    }


    public static void main(String[] args) {

    }

}
