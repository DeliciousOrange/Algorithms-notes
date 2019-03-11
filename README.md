# Algorithms's notes

1、iterator存在的目的不是为了替代for循环，也不是为性能而生，主要用于用抽象的手段隔离内部的实现细节。  
2、归并排序对于小的子数组开销有些大（因为需要迭代调用，这样就会创建大量小数组），实践中可行的一个改进是：当数组小于等于7时使用插入排序，运行时间大约快20%左右  
3、java的comparable API实现的是total order全序关系，简单来说，就是数组中的items能够按照特定顺序排列，全序关系是一种binary relation <=，满足三个关系式：  
+ 非对称性 如果v<=w，并且w<=v，那么v=w
+ 传递性   如果v<=w，并且w<=x,那么v<=x
+ 完整性   要么v<=w，要么w<=v，要么两者相等

值得注意的是：double类型不满足全序关系，因为Double.NaN <= Double.NaN为false。    
我们日常接触到的对自然数的标准排序、字符串的字母表排序以及对日期按年份的排序都是全序排序。

4、在java中，callback的实现是通过interface实现的。类似的，c#是通过delegate实现的，c是通过函数指针实现的，c++是通过class-type functors实现的，python和js是通过first-class functions实现的。  
5、选择排序的运行时间和数据的顺序无关，它总是需要平方的时间，即使数据是有序的，因为它总是需要遍历一遍右边剩下的未排序元素来找到最小的元素；但它需要的数据移动次数是最小的，仅仅使用线性增长次数的数据交换，每个item仅使用一次exchange就被放到了它最终应该待的有序位置。  
6、插入排序若要排序一个随机顺序的数组（keys不重复），需要大约1/4 N平方次比较和大约约1/4 N平方次交换。最好情况即数组是有序的情况下，需要N-1次比较和0次交换；最差情况下及数组是逆序的情况下，需要1/2 N平方次比较和1/2 N平方次交换。  
7、若一个数组逆序对的数量<=cN，那么，我们称它是部分有序数组，对于部分有序数组而言，插入排序运行时间是线性的，因为，交换次数等于逆序对的数量，比较次数等于交换次数+N-1。    
8、逆序对(inversion)是a pair of keys that are out of order. 比如：A E E L M O T R X P S中，有T-R T-P T-S R-P X-P X-S共6个逆序对。  
9、排序算法的目标就是将数组元素的主键按照某种方式排列，其中，元素和主键的具体性质在不同的应用中均有差别，在java中，元素通常都是对象，对主键的抽象描述则是通过Comparable接口实现的，具体来说，是该接口中的compareTo方法，该方法定义了对象的自然次序。  
10、如何实现非易变(immutable)数据类型？非易变数据类型是指一旦创建就无法改变数据类型的值的类型。它的优势有：  
+ 简化调试
+ 在有恶意代码攻击时更安全
+ 简化并发编程
+ 可以在优先级队列或符号表中作为key使用  
immutable数据类型的唯一劣势就是对于每个数据类型值必须创建一个new object。
要实现immutable，我们需要对传入构造函数的input进行copy，在data type里面创建一个private final instance variable持有这个copy，同时实例方法不改变这个私有实例变量。  
java中有许多immutable数据类型，比如String,Integer,Double,Color,Vector,Transaction,Point2D.同时也有许多Mutable数据类型，比如StringBuilder,Stack,Counter,Java array。

public class BST<Key extends Comparable<Key>, Value> {
    public Node root;

    private class Node {
        private Key key;
        private Value val;
        private Node left;
        private Node right;

        // private int N;
        // private int n;
        // private int len;
        public Node(Key key, Value val) {
            this.key = key;
            this.val = val;
            // this.N = N;
            // this.n = n;
            // this.len = len;
        }
        // public Node(Key key, Value val, int n, int len) {
        //     this.key = key;
        //     this.val = val;
        //     // this.N = N;
        //     // this.n = n;
        //     // this.len = len;
        // }
    }

    // public int height() {
    //     return height(root);
    // }
    //
    // private int height(Node x) {
    //     if (x == null) {
    //         return 0;
    //     }
    //     else {
    //         return x.n;
    //     }
    // }

    public Key min() {
        return min(root).key;
    }

    private Node min(Node root) {
        if (root == null) {
            return null;
        }
        Node x = root.left;
        Node y = root;
        while (x != null) {
            x = x.left;
            y = y.left;
        }
        return y;
    }

    // public Key floor(Key key) {
    //     Node x = floor(root, key);
    //     if (x == null) {
    //         return null;
    //     }
    //     return x.key;
    // }

    // private Node floor(Node x, Key key) {
    //
    // }

    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node root, Key key) {
        Node x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) {
                x = x.left;
            }
            else if (cmp > 0) {
                x = x.right;
            }
            else {
                return x.val;
            }
        }
        return null;
    }

    public void put(Key key, Value val) {
        root = put(root, key, val);
    }

    private Node put(Node root, Key key, Value val) {
        Node x = root;
        Node newNode = new Node(key, val);
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) {
                if (x.left == null) {
                    x.left = newNode;
                    break;
                }
                x = x.left;
            }
            else if (cmp > 0) {
                if (x.right == null) {
                    x.right = newNode;
                    break;
                }
                x = x.right;
            }
            else {
                break;
            }
        }
        if (x==null){
            return newNode;
        }
        return root;
    }
    // private Node put(Node root, Key key, Value val) {
    //     Node x = root;
    //     Stack<Node> stack = new Stack<Node>();
    //     while (x != null) {
    //         int cmp = key.compareTo(x.key);
    //         if (cmp < 0) {
    //             stack.push(x);
    //             x = x.left;
    //         }
    //         else if (cmp > 0) {
    //             stack.push(x);
    //             x = x.right;
    //         }
    //         else {
    //             stack.push(x);
    //             break;
    //         }
    //     }
    //     Node newNode = new Node(key, val);
    //     if (stack.isEmpty()) {
    //         return newNode;
    //     }
    //     else {
    //         Node y = stack.peek();
    //         if (key.compareTo(y.key) > 0) {
    //             y.right = newNode;
    //         }
    //         else if (key.compareTo(y.key) < 0) {
    //             y.left = newNode;
    //         }
    //         else {
    //             y.val = newNode.val;
    //         }
    //         return root;
    //     }
    // }
    // private Node put(Node x, int len, Key key, Value val) {
    //     if (x == null) {
    //         return new Node(key, val, 0, len);
    //     }
    //     int cmp = key.compareTo(x.key);
    //     if (cmp > 0) {
    //         x.right = put(x.right, len + 1, key, val);
    //     }
    //     else if (cmp < 0) {
    //         x.left = put(x.left, len + 1, key, val);
    //     }
    //     else {
    //         x.val = val;
    //     }
    //     x.n = Math.max(height(x.left), height(x.right)) + 1;
    //     return x;
    // }

    // public int avgCompares() {
    //     int pathLengthSum = avgCompares(root);
    //     return pathLengthSum / height() +1;
    // }

    // private int avgCompares(Node x) {
    //     int len = 0;
    //     if (x == null) {
    //         return 0;
    //     }
    //     if (x.left != null) {
    //         len += avgCompares(x.left);
    //     }
    //     len += x.len;
    //     len += avgCompares(x.right);
    //     return len;
    // }

    // public int avgCompares() {
    //     int pathLengthSum = avgCompares(root, 0);
    //     return pathLengthSum / height() + 1;
    // }
    //
    // public int avgCompares(Node x, int deep) {
    //     int len = 0;
    //     if (x == null) {
    //         return 0;
    //     }
    //     if (x.left != null) {
    //         len += avgCompares(x.left, deep + 1);
    //     }
    //     len += deep;
    //     len += avgCompares(x.right, deep + 1);
    //     return len;
    // }

    public static void main(String[] args) {
        BST<Integer, String> bst = new BST<Integer, String>();
        bst.put(4, "h");
        bst.put(1, "e");
        bst.put(6, "l");
        bst.put(5, "l");
        bst.put(7, "o");
        // System.out.println(bst.height());
        System.out.println("--------");
        // System.out.println(bst.avgCompares());
        // System.out.println(bst.avgCompares());
        System.out.println(bst.min());
    }
}
