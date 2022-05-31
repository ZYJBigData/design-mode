package com.zyj.play.interview.questions.flink.datasource.avro;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author yingjiezhang
 */
@AllArgsConstructor
@ToString
public class Person implements Serializable {
    public   String name;
    public Integer age;
}
