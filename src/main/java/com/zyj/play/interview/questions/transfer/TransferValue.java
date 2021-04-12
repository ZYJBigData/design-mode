package com.zyj.play.interview.questions.transfer;

/**
 * @author zhangyingjie
 * 考察基本类型 对象 和String 之间的区别
 * 栈管运行 推管存储
 * 基本类型 八种基本类型在栈分配（复制）
 * 引用类型 在堆里（指针，指向一个）
 * String 类型 如果是new和引用类型一样 ，不new 在String常量池中 ，池中有的话就用，没有创建（指向两个值）。
 */
public class TransferValue {

    public void changeValue1(int age1) {
        age1 = 30;
    }

    public void changeValue2(People people) {
        people.setPersonName("xxx");
    }

    public void changeValue3(String str) {
        str = "xxx";
    }

    public void change(String str, char ch[]) {
        str = "test ok";
        ch[0] = 'b';
    }

    public static void main(String[] args) {
        TransferValue test = new TransferValue();
        int age = 20;
        test.changeValue1(age);
        // 20
        System.out.println("age----" + age);

        People people = new People("aa");
        test.changeValue2(people);
        // xxx
        System.out.println("peopleName----" + people.getPersonName());

        String str = "str";
        test.changeValue3(str);
        //str
        System.out.println("str-------" + str);

        String strObj = new String("good");
        //good
        System.out.println(strObj);
        char[] ch = {'t', 'e', 's', 't'};
        test.change(strObj, ch);
        //best
        System.out.println(ch);
    }
}


