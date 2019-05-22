package com.umpaytest.algorithm;

/**
 * @author: Hucl
 * @date: 2019/3/25 16:04
 * @description: 排序算法
 */
public class SortDemo {

    /**
     * 冒泡排序
     * @param arr
     */
    public static void bubbleSort(int[] arr) {
        int length = arr.length;
        for (int i=0;i<length;i++) {
            boolean flag = false;
            for (int j=0;j<length-i-1;j++) {
                if (arr[j]>arr[j+1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = tmp;
                    flag = true;
                }
            }
            if (!flag) break;
            printAll(arr);
        }

    }

    /**
     * 插入排序
     * @param arr
     */
    public static void insertionSort(int[] arr) {
        int n = arr.length;
//        for (int i=1;i<n;i++) {
////            int value = arr[i];
////            int j = i-1;
////            for (;j>=0;--j) {
////                if (arr[j]>value) {
////                    arr[j+1] = arr[j];
////                } else {
////                    break;
////                }
////                printAll(arr);
////
////            }
////            arr[j+1] = value;
////            printAll(arr);
////        }
        for (int i=1;i<n;i++) {
            int value = arr[i];
            int j = i-1;
            for (;j>=0;j--) {
                if (arr[j] > value) {
                    arr[j+1] = arr[j];
                } else {
                    break;
                }
                printAll(arr);
            }
            arr[j+1] = value;
            System.out.println("-------------");
            printAll(arr);
        }
    }

    private static void printAll(int[] arr) {
        for (int i : arr) {
            System.out.print(i+",");
        }
        System.out.println();
    }


    public static void main(String[] args) {
        int[] arr = {2,6,4,1,5};
//        bubbleSort(arr);
        insertionSort(arr);
    }
}
