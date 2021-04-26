package com.zyj.play.interview.questions.thirdquarter.redis.doc;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zhangyingjie
 * <p>
 * 运用你所学的缓存结构，设计和实现一个lru的缓存机制，它应该支持以下操作：
 * 获取数据get和put
 * 1.获取数据，如果get 关键字(key)存储在缓存中，则获取关键字的值（总是正数），否则返回-1；
 * 2.写入数据，如果关键字已经存在，则变更为其他数值，如果该关键字不存在，则插入该关键字。当缓存容量上达到上限时，它应当在写入之前删除最长时间未使用的，
 * 从而为新的数据留下空间。
 * <p>
 * (1)是否在O(1)时间的复杂度完整这两种操作。
 * <p>
 * 1.lru的核心算法是哈希链表
 * 第一种写法是用常见的LinkedHashMap 完成
 */
public class LruCacheDemo<K, V> extends LinkedHashMap<K, V> {
    private final int capacity;

    public LruCacheDemo(int capacity) {
        super(capacity, 0.75F, false);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return super.size() > capacity;
    }


    public static void main(String[] args) {
        LruCacheDemo<Integer, String> lruCacheDemo = new LruCacheDemo<>(3);
        lruCacheDemo.put(1, "a");
        lruCacheDemo.put(2, "b");
        lruCacheDemo.put(3, "c");
        System.out.println(lruCacheDemo.keySet());

        lruCacheDemo.put(4, "d");
        System.out.println(lruCacheDemo.keySet());
        lruCacheDemo.put(3, "c");
        System.out.println(lruCacheDemo.keySet());
        lruCacheDemo.put(3, "c");
        System.out.println(lruCacheDemo.keySet());
        lruCacheDemo.put(3, "c");
        System.out.println(lruCacheDemo.keySet());
        lruCacheDemo.put(5, "e");
        System.out.println(lruCacheDemo.keySet());

        lruCacheDemo.put(6,"f");
        System.out.println(lruCacheDemo.keySet());
    }
}
