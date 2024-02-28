package com.zyj.play.letcode.util;


import com.zyj.play.letcode.pojo.ListNode;
import com.zyj.play.letcode.pojo.TreeNode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lyy
 */
public class DataGenerator {



    public  static TreeNode getBinaryTree(String str){

        Queue<Integer> queue = new LinkedList<>();
        Arrays.stream(str.split(",")).forEach(s -> {
            if("null".equalsIgnoreCase(s)){
                queue.add(null);
            }else {
                queue.add(Integer.valueOf(s));
            }
        });

        int depth = 1;
        List<TreeNode> list = new ArrayList<>();
        list.add(new TreeNode(queue.poll()));
        while (queue.size()>0){
            depth++;
            int getSize =1<<(depth-2);
            int thisSize = 1<<(depth-1);
            List<TreeNode> paramList=  new ArrayList<>(thisSize/2);
            for (int i = 0; i < thisSize; i++) {
               paramList.add(new TreeNode( queue.poll()));
            }
            //放入总队列
            int index = list.size()-getSize;
            int getIndex = 0;
            for (int i = index; i < index+getSize; i++) {
                for (int j = 0; j < 2; j++) {
                    if(j%2==0){
                        list.get(i).left = paramList.get(getIndex);
                    }else {
                        list.get(i).right = paramList.get(getIndex);
                    }

                    getIndex++ ;
                }
            }
            list.addAll(paramList);
        }
        return list.get(0) ;
    }




    public static ListNode getListNode(String str) {

        List<ListNode> listNodes = Arrays.stream(str.split(",")).map(s -> new ListNode(Integer.parseInt(s))).collect(Collectors.toList());

        for (int i = 0; i < listNodes.size() - 1; i++) {
            listNodes.get(i).setNext(listNodes.get(i + 1));
        }
        return listNodes.get(0);
    }


    public static void main(String[] args) {

        String str = "1,2,2,3,4,4,3";
//        String str = "1,2,2,null,3,null,3";
        TreeNode binaryTree = getBinaryTree(str);
        DataPrint.printBinaryTree(binaryTree);

    }


}
