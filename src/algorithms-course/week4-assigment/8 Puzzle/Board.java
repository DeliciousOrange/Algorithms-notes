import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

import java.util.Arrays;

public class Board {
    private int n;
    private int[][] blocks;
    private int coordinateX;
    private int coordinateY;
    private boolean flag;
    // 将stack定义为对象的state状态，虽然方便了，但是还要额外定义一个flag标志位，同时对象的内存占用变大了
    // 不符合作业要求。想象一下在某个应用中你有几亿个对象，那么从每个对象都节省16字节，那么总计就能节省几个
    // GB的内存空间，这是相当可观的。在规划class的结构定义时，要谨慎定义对象的字段或状态，尤其是集合型数据
    // 类型，能定义成local variable局部变量就定义成局部变量，虽然如本题目这样，在每次调用neighbors时，
    // 需要重新计算并构造stack，但若不是复杂的算法，往往不在意这点计算量。
    private Stack<Board> neighbors = new Stack<Board>();

    public Board(int[][] blocks) {
        if (blocks == null) {
            throw new IllegalArgumentException("argument is null");
        }
        n = blocks.length;
        int temp;
        int[][] tempBlocks = new int[n][n];
        this.blocks = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] == 0) {
                    coordinateX = i;
                    coordinateY = j;
                }
                tempBlocks[i][j] = blocks[i][j];
            }
        }

        // 一开始将实例变量neighbors的构造也放到构造函数里了，这样就导致了循环调用，导致栈溢出，
        // 注意在写构造函数的时候不要导致循环调用构造函数
        for (int i = 0; i < n; i++) {
            this.blocks[i] = Arrays.copyOf(tempBlocks[i], tempBlocks[i].length);
        }
    }

    private void exch(int[][] a, int[] b, int[] c) {
        int temp;
        temp = a[b[0]][b[1]];
        a[b[0]][b[1]] = a[c[0]][c[1]];
        a[c[0]][c[1]] = temp;
    }

    public int dimension() {
        return n;
    }

    public int hamming() {
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.blocks[i][j] != (i * n + 1 + j) && this.blocks[i][j] != 0) {
                    count++;
                }
            }
        }
        return count;
    }

    public int manhattan() {
        int count = 0;
        int[] coordinates = new int[2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n;
                 j++) {
                if (this.blocks[i][j] != 0) {
                    coordinates[0] = (this.blocks[i][j] - 1) / n;
                    coordinates[1] = (this.blocks[i][j] - 1) % n;
                    count += Math.abs(coordinates[0] - i) + Math.abs(coordinates[1] - j);
                }
            }
        }
        return count;
    }

    public boolean isGoal() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.blocks[i][j] != (i * n + 1 + j) && !(i == n - 1 && j == n - 1)) {
                    return false;
                }
            }
        }
        return true;
    }

    public Board twin() {
        int[] tempArray1 = new int[2];
        int[] tempArray2 = new int[2];
        int[][] tempBlocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            tempBlocks[i] = Arrays.copyOf(this.blocks[i], this.blocks.length);
        }
        for (int i = 0, k = 0; i < n; i++) {
            for (int j = 0; j < n && tempBlocks[i][j] != 0; j++) {
                if (k >= 2) {
                    break;
                }
                if (k == 0) {
                    tempArray1[0] = i;
                    tempArray1[1] = j;
                }
                else {
                    tempArray2[0] = i;
                    tempArray2[1] = j;
                }
                k++;
            }
        }
        exch(tempBlocks, tempArray1, tempArray2);
        return new Board(tempBlocks);
    }

    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        if (y == this) {
            return true;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }
        Board that = (Board) y;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.blocks[i][j] != that.blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {
        int[][] tempBlocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            tempBlocks[i] = Arrays.copyOf(this.blocks[i], this.blocks[i].length);
        }
        Board board;
        int[] tempArray1 = new int[2];
        int[] tempArray2 = new int[2];
        if (coordinateX - 1 >= 0) {
            tempArray1[0] = coordinateX;
            tempArray1[1] = coordinateY;
            tempArray2[0] = coordinateX - 1;
            tempArray2[1] = coordinateY;
            exch(tempBlocks, tempArray1, tempArray2);
            board = new Board(tempBlocks);
            neighbors.push(board);
            exch(tempBlocks, tempArray2, tempArray1);
        }
        if (coordinateX + 1 < n) {
            tempArray1[0] = coordinateX;
            tempArray1[1] = coordinateY;
            tempArray2[0] = coordinateX + 1;
            tempArray2[1] = coordinateY;
            exch(tempBlocks, tempArray1, tempArray2);
            board = new Board(tempBlocks);
            neighbors.push(board);
            exch(tempBlocks, tempArray2, tempArray1);
        }
        if (coordinateY - 1 >= 0) {
            tempArray1[0] = coordinateX;
            tempArray1[1] = coordinateY;
            tempArray2[0] = coordinateX;
            tempArray2[1] = coordinateY - 1;
            exch(tempBlocks, tempArray1, tempArray2);
            board = new Board(tempBlocks);
            neighbors.push(board);
            exch(tempBlocks, tempArray2, tempArray1);
        }
        if (coordinateY + 1 < n) {
            tempArray1[0] = coordinateX;
            tempArray1[1] = coordinateY;
            tempArray2[0] = coordinateX;
            tempArray2[1] = coordinateY + 1;
            exch(tempBlocks, tempArray1, tempArray2);
            board = new Board(tempBlocks);
            neighbors.push(board);
            exch(tempBlocks, tempArray2, tempArray1);
        }
        return neighbors;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocks[i][j] = in.readInt();
            }
        }
        Board initial = new Board(blocks);
        System.out.println(initial.dimension());
        System.out.println(initial.hamming());
        System.out.println(initial.manhattan());
        System.out.println(initial.isGoal());
        System.out.println(initial);
        Board board = initial.twin();
        System.out.println(board);
        for (Board b : initial.neighbors()) {
            System.out.println(b);
        }
    }
}
