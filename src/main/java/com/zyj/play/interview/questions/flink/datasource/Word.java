package com.zyj.play.interview.questions.flink.datasource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author zhangyingjie
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Word implements Serializable {
    private long timestamp;
    private String word;
    private int count;

    public Word(String word, int count) {
        this.word = word;
        this.count = count;
    }
}
