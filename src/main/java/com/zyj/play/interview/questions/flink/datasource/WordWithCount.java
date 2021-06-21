package com.zyj.play.interview.questions.flink.datasource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhangyingjie
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WordWithCount {
    public String word;
    public long count;
}
