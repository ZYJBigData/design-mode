package com.zyj.play.letcode;

import lombok.extern.slf4j.Slf4j;

/**
 * 给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。
 * 你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。设计一个算法来计算你所能获取的最大利润。
 * 返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0 。
 *
 * @author zhangyingjie
 * <p>
 */
@Slf4j
public class StockSolution {
    public static void main(String[] args) {
//        int[] prices = {7, 6, 4, 3, 1};
        int[] prices = {1, 2};
//        int[] prices = {7, 1, 5, 3, 6, 4};
//        int[] prices = {2, 4, 1};
        int i = maxProfit(prices);
    }

    public static int maxProfit(int prices[]) {
        int minprice = Integer.MAX_VALUE;
        int maxprofit = 0;
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < minprice) {
                minprice = prices[i];
            } else if (prices[i] - minprice > maxprofit) {
                maxprofit = prices[i] - minprice;
            }
        }
        return maxprofit;
    }
}
