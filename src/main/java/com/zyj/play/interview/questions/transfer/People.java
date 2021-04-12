package com.zyj.play.interview.questions.transfer;

/**
 * @author zhangyingjie
 */

public class People {
    private Integer id;
    private String personName;

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public People(String personName) {
        this.personName = personName;
    }

    public String getPersonName() {
        return personName;
    }
}
