import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamTasks {
    private static long sum = 0;

    public static void main(String[] args) {
        int[] testArr = randomTestArray(0);
        System.out.println("Source array: " + Arrays.toString(testArr));
        System.out.println("Result int: " + intValue(testArr));
        // II Тестовый массив, в принципе, пойдет и для второй задачи
        System.out.println("SUM Elements = " + Arrays.stream(testArr).sum());
        System.out.println("Result oddOrEven: " + oddOrEven(Arrays.stream(testArr).boxed().toList()));
        System.out.println("Result oddOrEven2: " + oddOrEven2(Arrays.stream(testArr).boxed().toList()));
        System.out.println("Result oddOrEven3: " + oddOrEven3(Arrays.stream(testArr).boxed().toList()));
        System.out.println("Result oddOrEven4: " + oddOrEven4(Arrays.stream(testArr).boxed().toList()));
    }

    public static int intValue(int[] values) {
        return Arrays.stream(values)
                .sorted()
                .distinct()
                .reduce(0, (left, right) -> left * 10 + right);
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        List<Integer> oddsList = new ArrayList<>();
        List<Integer> evensList = new ArrayList<>();
        long sumList = integers.stream()
                .peek((i) -> {
                    if (i % 2 == 0) evensList.add(i);
                    else oddsList.add(i);
                })
                .mapToInt(i -> i)
                .sum();
        return (sumList % 2 == 0) ? evensList : oddsList;
    }

    public static List<Integer> oddOrEven2(List<Integer> integers) {
        // Раскладываем элементы в два объекта Result по признаку четности с одновременным подсчетом сумм этих элементов
        class Result {
            enum oddOrEvenKey {
                ODD, EVEN
            }

            private long sum;
            private final List<Integer> elements = new ArrayList<>();

            public Result(Integer integer) {
                sum = integer;
                elements.add(integer);
            }

            public boolean sumIsEven() {
                return sum % 2 == 0;
            }

            public static Result addSum(Result accumulator, Result element) {
                accumulator.sum += element.sum;
                accumulator.elements.addAll(element.elements);
                return accumulator;
            }

            public static oddOrEvenKey oddOrEvenKey(int i) {
                return (i % 2 == 0) ? oddOrEvenKey.EVEN : oddOrEvenKey.ODD;
            }

            public List<Integer> getList() {
                return elements;
            }
        }

        Map<Result.oddOrEvenKey, Result> resultMap = integers.stream()
                .collect(Collectors.toMap(Result::oddOrEvenKey, Result::new, Result::addSum));

        if (resultMap.isEmpty()) {
            return new ArrayList<>();
        }

        if ((!resultMap.containsKey(Result.oddOrEvenKey.EVEN) || resultMap.get(Result.oddOrEvenKey.EVEN).sumIsEven()) ^ (!resultMap.containsKey(Result.oddOrEvenKey.ODD) || resultMap.get(Result.oddOrEvenKey.ODD).sumIsEven())) {
            return resultMap.get(Result.oddOrEvenKey.ODD).getList();
        } else {
            return resultMap.get(Result.oddOrEvenKey.EVEN).getList();
        }
    }

    public static List<Integer> oddOrEven3(List<Integer> integers) {
        Map<Boolean, List<Integer>> resultMap = integers.stream()
                .collect(Collectors.partitioningBy(i -> i % 2 == 0));

        if (resultMap.get(false).size() % 2 == 0) {
            return resultMap.get(true);
        } else {
            return resultMap.get(false);
        }
    }

    public static List<Integer> oddOrEven4(List<Integer> integers) {
        sum = 0;
        Map<Boolean, List<Integer>> resultMap = integers.stream()
                .collect(Collectors.partitioningBy(i -> {
                    sum = sum + i;
                    return i % 2 == 0;
                }));

        if (sum % 2 == 0) {
            return resultMap.getOrDefault(true, new ArrayList<>());
        } else {
            return resultMap.getOrDefault(false, new ArrayList<>());
        }
    }

    public static int[] randomTestArray(int capacity) {
        int[] res = new int[capacity];
        for (var i = 0; i < capacity; i++) {
            res[i] = (int) (Math.random() * 9) + 1;
        }
        return res;
    }
}
