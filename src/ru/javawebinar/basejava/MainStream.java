package ru.javawebinar.basejava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

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
                .collect(
                        (Supplier<ArrayList<Integer>>) ArrayList::new,
                        (x, y) -> {
                            if (x.size() == 0) {
                                x.add(0);
                            }
                            x.set(0, x.get(0) * 10 + y);
                        },
                        ArrayList::addAll).get(0);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        List<Integer> resOdd = new ArrayList<>();
        List<Integer> resEven = new ArrayList<>();

        Integer sum = integers.stream().mapToInt(Integer::intValue).collect(
                (Supplier<ArrayList<Integer>>) ArrayList::new,
                (x, y) -> {
                    if (x.size() == 0) {
                        x.add(0);
                    }
                    (((y % 2) == 0) ? resOdd : resEven).add(y);
                    x.set(0, x.get(0) + y);
                },
                ArrayList::addAll).get(0);

        return (sum % 2) == 0 ? resOdd : resEven;
    }
}
