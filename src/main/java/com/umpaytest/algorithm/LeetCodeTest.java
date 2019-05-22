package com.umpaytest.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Hucl
 * @date: 2019/4/29 08:51
 * @description:
 */
public class LeetCodeTest {

    public int singleNumber(int[] nums) {
        int result = 0;
        for (int i=0;i<nums.length;i++) {
            result =result^nums[i];
        }
        return result;
    }

    /**
     * 统计数组中各个元素出现的个数
     * @param nums
     * @return
     */
    public int elementCount(int[] nums) {
        Map<Integer,Integer> map = new HashMap<>(nums.length);
        for (int i : nums) {
            Integer value = map.get(i);
            if (value == null) {
                map.put(i,1);
            } else {
                map.put(i,map.get(i)+1);
            }
//            map.merge(ke)
        }
        return -1;
    }


    public static void main(String[] args) {
        LeetCodeTest test = new LeetCodeTest();
        int[] nums = {2,3,2};
        System.out.println(test.singleNumber(nums));
//        test.elementCount(nums);
    }
}
