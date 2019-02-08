/******************************************************************************
 *  Name:   LianYing Zhang
 *  NetID:  lianying168000
 *  Precept:    P00
 *
 *  Description:    This is a generic data type for modeling a RandomizedQueue.
 *
 *  Written:    06/02/2019
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

// 本实现是未使用哨兵节点的版本，写起来较麻烦且易出bug，可考虑写带哨兵节点的改进版本
// 本实现的dequeue方法不能实现常量运行时间复杂度，因为链表无论如何都不能实现随机访问，故不能得满分，应改为数组实现
public class RandomizedQueue<Item> implements Iterable<Item> {
    private DoubleNode first;
    private DoubleNode last;
    private int count;

    // construct an empty randomized queue
    public RandomizedQueue() {
        first = null;
        last = null;
    }

    private class DoubleNode {
        Item item;
        DoubleNode prev;
        DoubleNode next;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the randomized queue
    public int size() {
        return count;
    }

    // add the item
    public void enqueue(Item item) {
        DoubleNode oldlast = last;
        last = new DoubleNode();
        last.item = item;
        last.next = null;
        // count++;
        if (oldlast == null) {
            first = last;
            first.prev = null;
            first.next = null;
        }
        else {
            oldlast.next = last;
            last.prev = oldlast;
        }
        count++;
    }

    // remove and return a random item
    public Item dequeue() {
        int index = StdRandom.uniform(count);
        DoubleNode temp = first;
        while (index > 0) {
            temp = temp.next;
            index--;
        }
        Item item = temp.item;
        if (count == 1) {
            first = first.next;
            last = null;
        }
        else if (temp.prev == null) {
            first = first.next;
            first.prev = null;
        }
        else if (temp.next == null) {
            last = last.prev;
            last.next = null;
            // 这里不应写temp.prev.next=null，因为此时temp就是last，故应写last
            //下面两行代码会报null指针异常，因为第一行将last置为null，然后又在null上调用prev
            // temp.prev.next = null;
            // temp.prev = null;
        }
        else {
            temp.prev.next = temp.next;
            temp.next.prev = temp.prev;
        }
        count--;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        int index = StdRandom.uniform(count);
        DoubleNode temp = first;
        while (index > 0) {
            temp = temp.next;
            index--;
        }
        Item item = temp.item;
        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    // 这里题目的要求是返回一个随机顺序的迭代器，我们不能直接调用外部RandomizedQueue类的
    // dequeue方法，这样会删除原队列的元素。这样的话我们有两种方案可用：一是从原队列弹出元
    // 素的同时保持对已弹出元素的追踪，但这样做较麻烦，不易实现；那么我们其实可以使用另一种
    // 方案，即新建一个RandomizedQueue对象，复制原队列但元素，然后调用新队列的dequeue方法
    // 进行随机弹出。
    private class ListIterator implements Iterator<Item> {
        // private DoubleNode current = first;
        RandomizedQueue<Item> rq;

        /*
         *  create a new RandomizedQueue<Item> object so that can dequeue elements
         *  randomly
         */
        public ListIterator() {
            rq = new RandomizedQueue<Item>();
            DoubleNode temp = first;
            while (temp != null) {
                rq.enqueue(temp.item);
                temp = temp.next;
            }
            //下面的代码并不能实现复制一个队列，存在bug，应使用上面的代码复制队列
            // rq.first = first;
            // rq.last = last;
            // rq.count = count;
        }

        public boolean hasNext() {
            return rq.first != null;
        }

        public void remove() {
        }

        public Item next() {
            Item item = rq.dequeue();
            return item;
        }
    }

    // unit testing (optional)
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        StdOut.println(rq.isEmpty());
        rq.enqueue("hello");
        rq.enqueue("world");
        rq.enqueue("zhang");
        rq.enqueue("lian");
        rq.enqueue("ying");
        // StdOut.println(rq.size());
        // StdOut.println(rq.dequeue());
        // StdOut.println(rq.size());
        // StdOut.println(rq.dequeue());
        // StdOut.println(rq.dequeue());
        // StdOut.println(rq.sample());
        // StdOut.println(rq.size());
        for (String str : rq) {
            StdOut.println(str);
        }
    }
}
