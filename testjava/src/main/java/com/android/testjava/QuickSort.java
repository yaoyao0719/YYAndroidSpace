package com.android.testjava;

import java.util.Arrays;

/**
 * @author:yaoyao
 * @email :229847401@qq.com
 * @date :2018/1/2
 * @desc :快排，基础实现
 */

public class QuickSort {
    public static void main(String[] args) {
        int[] array = {3, 7, 8, 5, 1, 2, 9, 5, 4};
        quickSort(array);
    }

    public static void quickSort(int[] array) {
        quickSort(array, 0, array.length - 1);
    }

    public static void quickSort(int[] array, int low, int high) {
        if (null == array || array.length == 0) return;
        if (low >= high) return;
        int p = partition(array, low, high);
        quickSort(array, low, p - 1);
        quickSort(array, p + 1, high);
    }

    public static int partition(int[] arr, int left, int right) {
        int base = arr[left];//基准值
        while (left < right) {
            while (left < right && arr[right] >= base) {
                right--;
            }
            arr[left] = arr[right];
            while (left < right && arr[left] <= base) {
                left++;
            }
            arr[right] = arr[left];
        }
        arr[left] = base;
        System.out.println("Sorting: " + Arrays.toString(arr));
        return left;
    }
}
