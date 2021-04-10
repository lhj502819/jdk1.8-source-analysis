package com.source;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collector;

/**
 * TODO
 *
 * @author lihongjian
 * @since 2020/7/29
 */
public class Test1 {
    public static void main(String[] args) {
        List<String> list1 = new ArrayList<>(2);
        list1.add("1");
        list1.add("2");
        List<String> list2 = new ArrayList<>(3);
        list2.add("1");
        list2.add("2");
        list2.add("3");
        List<String> list3 = new ArrayList<>(4);
        list3.add("1");
        list3.add("2");
        list3.add("3");
        list3.add("4");
        List<List<String>> lists = new ArrayList<>();
        lists.add(list1);
        lists.add(list2);
        lists.add(list3);

        Collections.sort(lists, (o1,o2)->{
            return o2.size()-o1.size();
        });
        List<List<String>> lists1 = lists.subList(0, 1);
        List<Integer> listInt = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        Map<Integer, List<String>> map2 = new HashMap<>();
        for (List<String> list : lists) {
            map.put(list.hashCode() + "AA" + list.size(), list.size());
            map2.put(list.hashCode(), list);
            listInt.add(list.size());
        }
        int[] a = new int[lists.size()];
        Integer[] integers = new Integer[lists.size()];
        Integer[] integer2 = listInt.toArray(integers);
        for (int i = 0; i < integer2.length; i++) {
            a[i] = integer2[i];
        }
        printArray("排序前：", a);
        bubbleSort(a);
        printArray("排序前：", a);
        int[] b = new int[10];
        for (int i = 0; i < a.length; i++) {
            b[i] = a[i];
        }
        for (int i : b) {
            for (Map.Entry<String, Integer> stringIntegerEntry : map.entrySet()) {
                if (Integer.valueOf(stringIntegerEntry.getKey().split("AA")[1]).equals(i)) {
                    List<String> list = map2.get(Integer.valueOf(stringIntegerEntry.getKey().split("AA")[0]));
                    System.out.println(list);
                }
            }
        }
    }


    public static void bubbleSort(int array[]) {
        int t = 0;
        for (int i = 0; i < array.length - 1; i++)
            for (int j = 0; j < array.length - 1 - i; j++)
                if (array[j] < array[j + 1]) {
                    t = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = t;
                }
    }

    private static void printArray(String pre, int[] a) {
        System.out.print(pre + "\n");
        for (int i = 0; i < a.length; i++)
            System.out.print(a[i] + "\t");
        System.out.println();
    }


    private static void Sort(int[] a, int left, int right) {
        if (left >= right)
            return;

        int mid = (left + right) / 2;
        //二路归并排序里面有两个Sort，多路归并排序里面写多个Sort就可以了
        Sort(a, left, mid);
        Sort(a, mid + 1, right);
        merge(a, left, mid, right);

    }

    private static void merge(int[] a, int left, int mid, int right) {

        int[] tmp = new int[a.length];
        int r1 = mid + 1;
        int tIndex = left;
        int cIndex = left;
        // 逐个归并
        while (left <= mid && r1 <= right) {
            if (a[left] <= a[r1])
                tmp[tIndex++] = a[left++];
            else
                tmp[tIndex++] = a[r1++];
        }
        // 将左边剩余的归并
        while (left <= mid) {
            tmp[tIndex++] = a[left++];
        }
        // 将右边剩余的归并
        while (r1 <= right) {
            tmp[tIndex++] = a[r1++];
        }


        // TODO Auto-generated method stub
        //从临时数组拷贝到原数组
        while (cIndex <= right) {
            a[cIndex] = tmp[cIndex];
            //输出中间归并排序结果
            System.out.print(a[cIndex] + "\t");
            cIndex++;
        }
        System.out.println();
    }


    private static String dateToString(String str) {
        String replace = str.replace("-", "");
        replace = replace.replace(":", "");
        replace = replace.replace(" ", "");
        return replace;
    }


}

class MyCompartor implements Comparator<List> {

    @Override
    public int compare(List o1, List o2) {
        return o2.size()-o1.size();
    }
}
