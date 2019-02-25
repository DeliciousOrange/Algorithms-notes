import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

public class SortCompare {
    // 此例使用枚举类型是为了方便复用函数time，通过传入不同的枚举值，让同一个函数执行两套不同的分支
    // 处理逻辑。
    private enum InsertionSortType {
        PRIMITIVE, NON_PRIMITIVE
    }

    private static void insertionSortPrimitive(int[] a) {
        for (int i = 0; i < a.length; i++) {
            // 注意这里将a[j] < a[j-1]的比较放到for循环中，虽然更加简洁但是存在重复比较的问题
            // 若要避免重复比较，只需将比较语句放到for循环体内，当小于不成立时break跳出内层循环
            // 即可。
            for (int j = i; j > 0 && a[j] < a[j - 1]; j--) {
                int temp = a[i];
                a[i] = a[j];
                a[j] = temp;
            }

            // 上面的for循环中，我在一开始写的时候，写成下面这样，外层for循环的i从1开始，因为在
            // 内层for循环体内有j-1，所以若i从0开始，就会抛出数组越界异常。但是i=1不符合我们一
            // 贯的写法，为了和平常的习惯保持一致，我们只需一个小技巧，就是如上面的代码写法那样，
            // 在内层for循环条件里加上一条j > 0的判断。

            // for (int i = 1;i < a.length; i++) {
            //     for (int j = i; j>0 ; j--) {
            //         if (a[j] < a[j-1]) {
            //             int temp = a[i];
            //             a[i] = a[j];
            //             a[j] = temp;
            //         } else {
            //             break;
            //         }
            //     }
            // }
        }
    }

    private static void insertionSortNonPrimitive(Integer[] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = i; j > 0 && a[j] < a[j - 1]; j--) {
                Integer temp = a[i];
                a[i] = a[j];
                a[j] = temp;
            }
        }
    }

    public static double time(InsertionSortType insertionSortType, int[] arrayInt, Integer[] arrayInteger) {
        Stopwatch timer = new Stopwatch();
        if (insertionSortType == InsertionSortType.PRIMITIVE) {
            insertionSortPrimitive(arrayInt);
        } else {
            insertionSortNonPrimitive(arrayInteger);
        }
        return timer.elapsedTime();
    }

    private static double timeRandomInputInt(InsertionSortType insertionSortType, int N, int T) {
        double total = 0.0;
        int[] a = new int[N];
        for (int i = 0; i < T; i++) {
            for (int j = 0; j < N; j++) {
                a[j] = StdRandom.uniform(65535555);
            }
            total += time(insertionSortType, a, null);
        }
        return total;
    }

    private static double timeRandomInputInteger(InsertionSortType insertionSortType, int N, int T) {
        double total = 0.0;
        Integer[] a = new Integer[N];
        for (int i = 0; i < T; i++) {
            for (int j = 0; j < N; j++) {
                a[j] = StdRandom.uniform(65535555);
            }
            total += time(insertionSortType, null, a);
        }
        return total;
    }

    public static void sortCompare(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        double t1 = timeRandomInputInt(InsertionSortType.PRIMITIVE, N, T);
        double t2 = timeRandomInputInteger(InsertionSortType.NON_PRIMITIVE, N, T);
        StdOut.printf("For %d random int\n %s is", N, "primitive type");
        StdOut.printf(" %.1f times faster than %s\n", t2 / t1, "Integer type");
    }

    public static void main(String[] args) {
        sortCompare(args);
    }
}
