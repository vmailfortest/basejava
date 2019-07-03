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
        final int[] result = {0};

        Arrays.stream(values)
                .distinct()
                .sorted()
                .forEach(x -> result[0] = result[0] * 10 + x);

        return result[0];
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        List<Integer> resOdd = new ArrayList<>();
        List<Integer> resEven = new ArrayList<>();
        final Integer[] sum = {0};

        integers.forEach(
                (x) -> {
                    (
                            (
                                    (x % 2) == 0
                            ) ? resOdd : resEven
                    ).add(x);
                    sum[0] = +x;
                }
        );

        return (sum[0] % 2) == 0 ? resOdd : resEven;
    }
}
