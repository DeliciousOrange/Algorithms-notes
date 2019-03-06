import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    // 没必要定义moves1字段，因为能从node字段通过简单计算得出移动步数，在其他依赖该字段的
    // 方法中直接用node字段计算就行了，像这样能从其他字段通过运算得出的字段不应重复定义，
    // class每多一个字段就意味着耗费多余的内存，这是不良的编程实践
    // private int moves1;

    private Node head1 = new Node();
    private Node head2 = new Node();
    private Node node;
    // 尽量不要集合数据类型stack、pq等放在类的状态或字段里，这样会导致较高的内存占用率，
    // 就像本题中MinPQ最好放在构造函数内定义成局部变量，这样在计算完成之后，由于没有对
    // MinPQ的引用了，java的GC就可以及时回收该部分内存。同样，下面的stack最好也定义成
    // 局部变量。
    // private Stack<Board> boards = new Stack<Board>();

    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("argument is null");
        }
        // moves1 = -1;
        Board newInitial = initial.twin();
        Node node1 = new Node();
        Node node2 = new Node();
        MinPQ<Node> pq1 = new MinPQ<Node>();
        MinPQ<Node> pq2 = new MinPQ<Node>();

        node1.board = initial;
        node1.priority = node1.board.manhattan();
        node1.prev = head1;
        pq1.insert(node1);

        node2.board = newInitial;
        node2.priority = node2.board.manhattan();
        node2.prev = head2;
        pq2.insert(node2);

        while (true) {
            Node node3 = pq1.delMin();
            Node node4 = pq2.delMin();
            if (node3.board.isGoal()) {
                node = node3;
                break;
            }
            else if (node4.board.isGoal()) {
                node = null;
                break;
            }

            for (Board board : node3.board.neighbors()) {
                Node childNode = new Node();
                if (!board.equals(node3.prev.board)) {
                    childNode.board = board;
                    childNode.moves = node3.moves + 1;
                    childNode.priority = board.manhattan() + childNode.moves;
                    childNode.prev = node3;
                    pq1.insert(childNode);
                }
            }

            for (Board board : node4.board.neighbors()) {
                Node childNode = new Node();
                if (!board.equals(node4.prev.board)) {
                    childNode.board = board;
                    childNode.moves = node4.moves + 1;
                    childNode.priority = board.manhattan() + childNode.moves;
                    childNode.prev = node4;
                    pq2.insert(childNode);
                }
            }
        }

        // 由于我们将stack相关的代码放到solution方法内，所以下面的代码连同stack的定义
        // 都挪到solution方法体内

        // if (node != null) {
        //     while (node.prev != null) {
        //         boards.push(node.board);
        //         node = node.prev;
        //         moves1++;
        //     }
        // }
        // else {
        //     boards = null;
        // }
    }

    private class Node implements Comparable<Node> {
        private Board board;
        private Node prev;
        private int moves;
        private int priority;

        public int compareTo(Node that) {
            if (this.priority > that.priority
            ) {
                return +1;
            }
            else if (this.priority < that.priority
            ) {
                return -1;
            }
            else {
                return 0;
            }

            // 这里要注意||或运算符的用法，比如：a > b || a < b || a == b && b > c,最后
            // 一个a==b是多余的。类似地，下面else if里面||符号后面没必要再写this.priority
            // == that.priority，因为第一个if和else if已经判断大于和小于的情况，剩下的情况
            // 肯定就只剩等于的情况了。
            // if (this.priority > that.priority
            //         || this.priority == that.priority && distanceThis > distanceThat) {
            //     return +1;
            // }
            // else if (this.priority < that.priority
            //         || distanceThis < distanceThat) {
            //     return -1;
            // }
            // else {
            //     return 0;
            // }
        }
    }

    public boolean isSolvable() {
        return node != null;
    }

    public int moves() {
        return node.moves;
    }

    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }
        Stack<Board> boards = new Stack<Board>();

        // 像下面这样写if...else...反而丑陋，有时候仅用一个if反而更加清晰明朗，先用一个
        // 判断非期望情况，如果不满足直接return，若是期望情况则往下执行正常情况

        // if (node != null) {
        while (node.prev != null) {
            boards.push(node.board);
            node = node.prev;
        }
        // }
        // else {
        //     boards = null;
        // }
        return boards;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
