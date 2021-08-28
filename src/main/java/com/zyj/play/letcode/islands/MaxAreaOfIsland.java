package com.zyj.play.letcode.islands;

import java.util.*;

/**
 * 给定一个包含了一些 0 和 1 的非空二维数组 grid 。
 * <p>
 * 一个 岛屿 是由一些相邻的 1 (代表土地) 构成的组合，这里的「相邻」要求两个 1 必须在水平或者竖直方向上相邻。你可以假设 grid 的四个边缘都被 0（代表水）包围着。
 * <p>
 * 找到给定的二维数组中最大的岛屿面积。(如果没有岛屿，则返回面积为 0 。)
 *
 * @author zhangyingjie
 */
public class MaxAreaOfIsland {
    public static int[][] girds = {
            {0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
            {0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0},
            {0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0}
    };
    public static int[][] girds1 = {
            {1, 1, 0, 0, 0},
            {1, 1, 0, 0, 0},
            {0, 0, 0, 1, 1},
            {0, 0, 0, 1, 1}
    };

    public static void main(String[] args) {
        int result = maxAreaOfIsland(girds1);
        System.out.println("result=={}" + result);
    }

    public static void dfs(int[][] girds, int c, int r, List<Integer> list) {
        int rl = girds[0].length;
        int cl = girds.length;
        if (r < 0 || r >= rl || c < 0 || c >= cl || girds[c][r] != 1) {
            return;
        }
        girds[c][r] = 2;
        list.add(girds[c][r]);
        dfs(girds, c, r - 1, list);
        dfs(girds, c, r + 1, list);
        dfs(girds, c - 1, r, list);
        dfs(girds, c + 1, r, list);
    }

    public static int maxAreaOfIsland(int[][] girds) {
        if (girds == null || girds.length == 0) {
            return 0;
        }
        //将岛屿的个数扔进集合中
        ArrayList<Integer> list1 = new ArrayList<>();
        for (int c = 0; c < girds.length; ++c) {
            for (int r = 0; r < girds[0].length; ++r) {
                if (girds[c][r] == 1) {
                    ArrayList<Integer> list = new ArrayList<>();
                    dfs(girds, c, r, list);
                    list1.add(list.size());
                }
            }
        }
        //如果没有岛屿出现的话
        if (list1.size() == 0) {
            list1.add(0);
        }
        Collections.sort(list1);
        return list1.get(list1.size() - 1);
    }
}
