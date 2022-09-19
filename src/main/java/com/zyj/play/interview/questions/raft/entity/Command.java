package com.zyj.play.interview.questions.raft.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Command implements Serializable {

    String key;

    String value;
}
