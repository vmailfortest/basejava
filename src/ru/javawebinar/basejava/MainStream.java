package ru.javawebinar.basejava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainStream {
    public static void main(String[] args) {
        int[] arrayInt = new int[]{6, 7, 1, 3, 6, 9, 1, 9};
        System.out.println(minValue(arrayInt));

        List<Integer> arrayInteger = Arrays.asList(6, 7, 3, 6, 8, 1);
        System.out.println(oddOrEven(arrayInteger));
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (acc, number) -> acc * 10 + number);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        List<Integer> resOdd = new ArrayList<>();
        List<Integer> resEven = new ArrayList<>();

        int sum = integers.stream().mapToInt(Integer::intValue)
                .reduce(0, (acc, number) -> {
                    (((number % 2) == 0) ? resOdd : resEven).add(number);
                    return acc + number;
                });

        return (sum % 2) == 0 ? resOdd : resEven;
    }
}
