package com.shop.test;

import org.assertj.core.util.Arrays;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;
import java.util.stream.IntStream;

public class SortTest {
    public static void main(String[] args) {
        int arr[] =new int[80000];
        Random random = new Random();
        for (int i = 0; i < 80000; i++) {
            arr[i] = random.nextInt(5000);
        }
        long start = System.currentTimeMillis();
        maoPaoSort(arr);
        long end = System.currentTimeMillis();
        System.out.println("冒泡花费时间:"+ new SimpleDateFormat("ss").format( new Date((end-start))));
        /***************************************************/
        start = System.currentTimeMillis();
        maoPaoSortPlus(arr);
        end = System.currentTimeMillis();
        //new SimpleDateFormat("ss").format( new Date((end-start)));
        System.out.println("冒泡进化花费时间:"+ new SimpleDateFormat("ss").format( new Date((end-start))));
       /***************************************************/
        start = System.currentTimeMillis();
        selectSort(arr);
        end = System.currentTimeMillis();
        System.out.println("选择花费时间:"+ new SimpleDateFormat("ss").format( new Date((end-start))));

        start = System.currentTimeMillis();
        IntStream sorted = java.util.Arrays.stream(arr).sorted();
        end = System.currentTimeMillis();
        System.out.println("stream花费时间:"+ new SimpleDateFormat("ss").format( new Date((end-start))));
    }
    public  static  void selectSort(int[] arr){
        int index = -1;
        int val= arr[0];
        int temp = 0;
        boolean flag = false;
        for (int x = 0 ; x<= arr.length - 2;x++){
            val= arr[x];
            for (int i = x+1; i <= arr.length-1; i++) {
                if(val >= arr[i]){
                    val = arr[i];
                    index = i;
                    flag= true;
                }
            }
            if(flag) {
                temp = arr[x];
                arr[x] = val;
                arr[index] = temp;
                flag= false;
            }
        }
    }
    public  static void maoPaoSortPlus(int... arr){
        int temp = 0;
        boolean flag =false;
        for (int x = 0; x <= arr.length -1; x++) {
            for (int i = 0; i <=  arr.length -2 - x ; i++) {
                if(arr[i] >= arr[i+1]){
                    flag = true;
                    temp = arr[i];
                    arr[i] = arr[i+1];
                    arr[i+1] = temp;
                }
            }
            if(!flag){
                break;
            }
        }

    }
    public  static void maoPaoSort(int... arr){
        int temp = 0;
        for (int x = 0; x <= arr.length -1; x++) {
            for (int i = 0; i <=  arr.length -2 - x ; i++) {
                if(arr[i] >= arr[i+1]){
                    temp = arr[i];
                    arr[i] = arr[i+1];
                    arr[i+1] = temp;
                }
            }

        }

    }
}
