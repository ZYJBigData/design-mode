package com.zyj.play.interview.questions.thirdquarter.redis.doc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangyingjie
 * <p>
 * 完全手写一个lru 不适用jdk的成熟的api
 * map负责查找，构建一个双向链表负责Node 负责存储数据
 */
public class LruCacheDemo1 {

    //构造一个Node节点，作为数据的载体
    class Node<K, V> {
        K key;
        V value;
        Node<K, V> pre;
        Node<K, V> next;

        public Node() {
            this.pre = this.next = null;
        }

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.pre = this.next = null;
        }
    }

    //构建一个虚拟的双向链表，里面存储的是Node
    class DoubleLinkedList<K, V> {
        Node<K, V> head;
        Node<K, V> tail;

        public DoubleLinkedList() {
            head = new Node<>();
            tail = new Node<>();
            head.next = tail;
            tail.pre = head;
        }

        //增加头节点
        public void addHead(Node<K, V> node) {
            node.next = head.next;
            node.pre = head;
            node.next.pre = node;
            head.next = node;
        }

        //删除一个节点
        public void removeNode(Node<K, V> node) {
            node.next.pre = node.pre;
            node.pre.next = node.next;
            node.pre = null;
            node.next = null;
        }

        //获取最后一个节点
        public Node<K, V> getLastNode() {
            return tail.pre;
        }

        private int cacheSize;
        Map<Integer, Node<Integer, Integer>> map;
        DoubleLinkedList<Integer, Integer> doubleLinkedList;

        public void LruCacheDemo1(int cacheSize) {
            this.cacheSize = cacheSize;//坑位
            this.map = new HashMap<>();//查找
            this.doubleLinkedList = new DoubleLinkedList<>();//装链表的集合
        }

        public int get(int key) {
            if (!map.containsKey(key)) {
                return -1;
            }
            Node<Integer, Integer> node = map.get(key);
            doubleLinkedList.removeNode(node);
            doubleLinkedList.addHead(node);
            return node.value;
        }

        public int put(int key, int value) {
            if (map.containsKey(key)) {
                Node<Integer, Integer> node = map.get(key);
                node.value = value;
                map.put(key, node);
                doubleLinkedList.removeNode(node);
                doubleLinkedList.addHead(node);
            } else {
                if (map.size() == cacheSize) {//坑位满了
                    Node<Integer, Integer> lastNode = doubleLinkedList.getLastNode();
                    map.remove(lastNode.key);
                    doubleLinkedList.removeNode(lastNode);
                }else {
                    //新增一个
                    Node<Integer, Integer> node = new Node<>(key, value);
                    doubleLinkedList.addHead(node);
                    map.put(key,node);
                }
            }
            return -1;
        }
    }

    public static void main(String[] args) {

    }
}
