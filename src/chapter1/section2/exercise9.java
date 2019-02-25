/**
 *   InstrumentBinarySearch(page47)touseaCountertocountthetotalnumber of keys examined during
 *   all searches and then print the total after all searches are com- plete. Hint : Create a
 *   Counter in main() and pass it as an argument to rank().
 */

import edu.princeton.cs.algs4.Counter;
import edu.princeton.cs.algs4.StdOut;

public class BinarySearch {
    public static int rank(int key, int[] a, Counter counter) {  // Array must be sorted.
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {  // Key is in a[lo..hi] or not present.
            counter.increment();
            int mid = lo + (hi - lo) / 2;
            if (key < a[mid]) hi = mid - 1;
            else if (key > a[mid]) lo = mid + 1;
            else return mid;
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] a = { 1, 2, 3, 4, 5 };
        Counter counter = new Counter("searchCount");
        rank(4, a, counter);
        StdOut.println(counter);
    }
}
