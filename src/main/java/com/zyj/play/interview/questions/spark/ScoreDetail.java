package com.zyj.play.interview.questions.spark;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import scala.Serializable;

/**
 * @author zhangyingjie
 */
@ToString
@Data
@AllArgsConstructor
public class ScoreDetail implements Serializable {
    public final String name;
    public final String course;
    public final Float score;
}
