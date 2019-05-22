package com.umpaytest.algorithm;

/**
 * @author: Hucl
 * @date: 2019/3/25 16:57
 * @description: 查找
 */
public class SearchDemo {

    /**
     * 折半查找
     * @param arr arr
     * @param sv search value
     * @return the index of search value ,
     *         if not found return -1
     */
    public static int binarySearch(int[] arr, int sv) {
        int start = 0;
        int end = arr.length-1;

        while (start<=end) {
            int mid = start + ((end-start)>>1);
            if (arr[mid] < sv) {
                start = mid+1;
            } else if (arr[mid] > sv) {
                end = mid-1;
            } else {
                if ((mid==0) || (arr[mid-1]!=sv)) {
                    return mid;
                } else {
                    end = mid-1;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] arr = {2,2,2,3,4,5,5,6};
//        System.out.println(binarySearch(arr,2));

        float a = 3.4f;
        float b = 3.6f;
        System.out.println(Math.round(a));
        System.out.println(Math.round(b));

    }
}
