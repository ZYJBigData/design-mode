package com.zyj.play.interview.questions.spark;

import lombok.AllArgsConstructor;
import scala.Serializable;

/**
 * @author zhangyingjie
 */
@AllArgsConstructor
public class ScoreDetail implements Serializable {
    public final String name;
    public final String course;
    public final Float score;
}
