package com.zyj.play.interview.questions.jvm.reference;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author zhangyingjie
 */
public class WeakHashMapDemo {
    public static void main(String[] args) {
        WeakHashMapDemo weakHashMapDemo = new WeakHashMapDemo();
        weakHashMapDemo.myHashMap();
        weakHashMapDemo.myWeakHashMap();
    }

    public void myHashMap() {
        Map<Integer, String> map = new HashMap<>();
        Integer key = new Integer(1);
        String value = "HashMap";
        map.put(key, value);
        System.out.println(map);
        key = null;
        System.out.println(map);
        System.gc();
        System.out.println(map + "\t " + map.size());
    }

    public void myWeakHashMap() {
        Map<Integer, String> map = new WeakHashMap<>();
        Integer key = new Integer(2);
        String value = "WeakHashMap";
        map.put(key, value);
        System.out.println(map);
        key = null;
        System.out.println(map);
        System.gc();
        System.out.println(map + "\t " + map.size());
    }
}
