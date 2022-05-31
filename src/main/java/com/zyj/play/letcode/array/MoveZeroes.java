package com.zyj.play.letcode.array;

/**
 * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
 *
 * @author zhangyingjie
 */
public class MoveZeroes {
    public static void main(String[] args) {
//        int[] nums = {0,1,0,3,12};
//        int[] nums = {1};
//        int[] nums = {2, 1};
//        int[] nums={1,0,1};
        MoveZeroes moveZeroes = new MoveZeroes();
//        moveZeroes.moveZeroes(nums);
//        for (int i = 0; i < nums.length; i++) {
//            System.out.print(nums[i] + " ");
//        }
        moveZeroes.reverse(1);
    }

    public void moveZeroes(int[] nums) {
        int slow = 0;
        int fast = 0;
        while (fast < nums.length) {
            if (nums[fast] != 0 && nums[slow]==0) {
                int temp = nums[fast];
                nums[fast] = nums[slow];
                nums[slow] = temp;
                slow++;
            }
            if (nums[slow]!=0){
                slow++;
            }
            fast++;
        }
    }

    public int reverse(int x) {
        while (true){
            try {
                Thread.sleep(5000);
                System.out.println("****************");
            }catch (Exception e){
                System.out.println(e);
            }finally {
                System.out.println("结束了。。。");
            }
        }
    }
}
