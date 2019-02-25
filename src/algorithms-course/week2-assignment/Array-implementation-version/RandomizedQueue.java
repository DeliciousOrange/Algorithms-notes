/******************************************************************************
 *  Name:   LianYing Zhang
 *  NetID:  lianying168000
 *  Precept:    P00
 *
 *  Description:    This is a generic data type for modeling a RandomizedQueue.
 *
 *
 *  Written:    08/02/2019
 *
 ******************************************************************************/

//  在具体实现时不要忘了考虑各种异常情况
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a = (Item[]) new Object[1]; // queue items
    private int N = 0; // number of items

    // Returns true if the queue has no elements
    public boolean isEmpty() {
        return N == 0;
    }

    // Returns the number of items
    public int size() {
        return N;
    }

    // Move queue to a new array of size max.
    private void resize(int max) {
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < N; i++)
            temp[i] = a[i];
        a = temp;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("the argument is null.");
        }
        if (N == a.length) resize(2 * a.length);
        a[N++] = item;
    }

    //  remove and return a random item
    //  这里有个小难题：就是在随机弹出一个元素之后，如何填充这个空位置，我的做法是将数组末尾的元素和它交换位置，同时将N减1，这样
    //  就巧妙地“删除了”该元素。这种原地删除的思想在Leetcode中也经常遇到。
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("there are not more elements to return.");
        }
        int index = StdRandom.uniform(N);
        Item item = a[index];
        a[index] = a[N - 1];
        a[N - 1] = null;    //避免游离即内存泄露
        N--;
        if (N > 0 && N == a.length / 4) resize(a.length / 2);   //当数组中元素实际存放数量少于数组大小的1/4时，减小数组的大小
        return item;
    }

    // Returns a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("there are not more elements to return.");
        }
        int index = StdRandom.uniform(N);
        return a[index];
    }

    // Returns an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        RandomizedQueue<Item> rq; // a new RandomizedQueue object

        /*
         *  create a new RandomizedQueue<Item> object so that can dequeue elements
         *  randomly
         */
        public ListIterator() {
            //  迭代器的构造器复制原队列的元素，使得在迭代时不影响外层队列的元素
            rq = new RandomizedQueue<Item>();
            for (int i = 0; i < N; i++) {
                rq.enqueue(a[i]);
            }
        }

        public boolean hasNext() {
            return rq.size() != 0;
        }

        public void remove() {
            throw new UnsupportedOperationException("unsupported operation.");
        }

        public Item next() {
            if (rq.size() == 0) {
                throw new NoSuchElementException("there are no more elements to return.");
            }
            Item item = rq.dequeue();
            return item;
        }
    }

    // unit testing (optional)
    //单元测试确保覆盖所有方法
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.enqueue(34);
        rq.enqueue(42);
        rq.enqueue(23);

        for (Integer x : rq) {
            StdOut.println(x);
        }
        StdOut.println(rq.size());
        StdOut.println(rq.sample());
        StdOut.println(rq.isEmpty());
        StdOut.println(rq.size());
        StdOut.println(rq.dequeue());
        StdOut.println(rq.dequeue());
        StdOut.println(rq.sample());
        StdOut.println(rq.size());
    }
}
