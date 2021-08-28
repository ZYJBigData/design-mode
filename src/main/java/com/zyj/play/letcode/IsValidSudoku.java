package com.zyj.play.letcode;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangyingjie
 */
@Slf4j
public class IsValidSudoku {
    public static void main(String[] args) {
        IsValidSudoku isValidSudoku = new IsValidSudoku();
        char[][] board = {
                {'5', '3', '.', '.', '7', '.', '.', '.', '.'}
                , {'6', '.', '.', '1', '9', '5', '.', '.', '.'}
                , {'.', '9', '8', '.', '.', '.', '.', '6', '.'}
                , {'8', '.', '.', '.', '6', '.', '.', '.', '3'}
                , {'4', '5', '.', '8', '.', '3', '.', '.', '1'}
                , {'7', '.', '.', '.', '2', '.', '.', '.', '6'}
                , {'.', '6', '.', '.', '.', '.', '2', '8', '.'}
                , {'.', '.', '.', '4', '1', '9', '.', '.', '5'}
                , {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };
        boolean validSudoku = isValidSudoku.isValidSudoku(board);
        System.out.println("result=={}" + validSudoku);
    }

    public boolean isValidSudoku(char[][] board) {
        //这种写法 是表示数组中的每个元素都是map
        Map<Integer, Integer>[] rows = new HashMap[9];
        Map<Integer, Integer>[] columns = new HashMap[9];
        Map<Integer, Integer>[] boxes = new HashMap[9];
        //对数组中的每个元素进行初始化，以防止数组中的每个元素是空指针
        for (int i = 0; i < 9; i++) {
            rows[i] = new HashMap<>();
            columns[i] = new HashMap<>();
            boxes[i] = new HashMap<>();
        }
        for (int i = 0; i < 9; i++) {
            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
            for (int j = 0; j < 9; j++) {
                char num = board[i][j];
                if (num != '.') {
                    //这里将字符的数字转成数字，结果将发生变化 数字对应的5表示的是53
                    int n = num;
                    int box_index = (i / 3) * 3 + j / 3;
                    System.out.println("box_index=={}" + box_index);
                    //每遍历一遍，取出整个行的值
                    rows[i].put(n, rows[i].getOrDefault(n, 0) + 1);
                    //每遍历一遍，取出全部列的第一个值
                    columns[j].put(n, columns[j].getOrDefault(n, 0) + 1);
                    //box_index 正好可以分成九份，九个map进行存值
                    boxes[box_index].put(n, boxes[box_index].getOrDefault(n, 0) + 1);
                    //遍历结束一遍之后 只要有一个map的值大于1 表名出现了重复的数字，返回值false
                    if (rows[i].get(n) > 1 || columns[j].get(n) > 1 || boxes[box_index].get(n) > 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
