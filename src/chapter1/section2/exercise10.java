/**
 *  Develop a class VisualCounter that allows both increment and decrement
 *  operations. Take two arguments N and max in the constructor, where N 
 *  specifies the maximum number of operations and max specifies the maximum
 *  absolute value for the counter. As a side effect, create a plot showing 
 *  the value of the counter each time its tally changes.
*/

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.awt.Color;

public class VisualCounter {
    private int maxOperationNum;
    private int n;
    private int count;
    private final int max;

    public VisualCounter(int n, int max) {
        maxOperationNum = n;
        this.max = max;
        StdDraw.setCanvasSize(1024, 512);
        StdDraw.setPenColor(Color.RED);
        StdDraw.setXscale(0, n + 1);
        StdDraw.setYscale(-max - 4, max + 4);
        StdDraw.setPenRadius(0.04);
    }

    //这里其实有一种更加优雅的处理方法，就是将count++这条语句放到if语句体里，相对应的将<=
    //改为<;对max的处理方式类似.代码见下方,以后遇到类似的情况可用该方法处理，毕竟包在if语句
    //体里面更加美观。
    public void increment() {
        count++;
        if (count <= maxOperationNum && Math.abs(n + 1) <= max) {
            ++n;
            StdDraw.point(count, n);
        }
    }
    //
    // if (count < maxOperationNum && n < max) {
    //     n++;
    //     count++;
    // }

    public void decrement() {
        count++;
        if (count <= maxOperationNum && Math.abs(n - 1) <= max) {
            --n;
            StdDraw.point(count, n);
        }
    }

    // if (count < maxOperationNum && (Math.abs(n) < max || n == max)) {
    //     count++;
    //     n--;
    // }

    public int getCount() {
        return count;
    }

    public int getN() {
        return n;
    }

    public static void main(String[] args) {
        VisualCounter vc = new VisualCounter(10, 6);
        vc.increment();
        vc.increment();
        vc.increment();
        StdOut.println(vc.getCount());
        StdOut.println(vc.getN());
        StdOut.println();
        vc.decrement();
        StdOut.println(vc.getCount());
        StdOut.println(vc.getN());
        StdOut.println();
        vc.decrement();
        vc.increment();
        vc.increment();
        vc.increment();
        vc.increment();
    }
}