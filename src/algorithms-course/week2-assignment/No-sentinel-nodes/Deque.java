/******************************************************************************
 *  Name:   LianYing Zhang
 *  NetID:  lianying168000
 *  Precept:    P00
 *
 *  Description:    This is a Abstract Data Type for modeling a percolation
 *  system.
 *
 *  Written:    26/01/2019
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

// 本实现是未使用哨兵节点的版本，写起来较麻烦且易出bug，可考虑写带哨兵节点的改进版本
public class Deque<Item> implements Iterable<Item> {
    private DoubleNode first;
    private DoubleNode last;
    private int count;

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
    }

    private class DoubleNode {
        Item item;
        DoubleNode last;
        DoubleNode next;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return count;
    }

    // add the item to the front
    public void addFirst(Item item) {
        DoubleNode oldfirst = first;
        first = new DoubleNode();
        first.item = item;
        //这里=号右边应该是oldfirst，而不是first
        first.next = oldfirst;
        first.last = null;
        count++;
        if (oldfirst != null) {
            oldfirst.last = first;
        }
        else {
            last = first;
        }
    }

    // add the item to the end
    public void addLast(Item item) {
        DoubleNode oldlast = last;
        last = new DoubleNode();
        last.item = item;
        last.next = null;
        count++;
        if (oldlast == null) {
            first = last;
            last.last = null;
        }
        else {
            oldlast.next = last;
            last.last = oldlast;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        Item item = first.item;
        first = first.next;
        count--;
        if (isEmpty()) {
            last = null;
        }
        return item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        Item item = last.item;
        count--;
        if (last.last == null) {
            first = null;
            last = null;
        }
        else {
            // 这里并不能用last.last.next=null来表示移除最后一个节点，因为即使这样做了
            // 并没有移动last指针，那么remove方法的返回值item始终等于那个第一次调用remove
            // 方法返回的item，因为链表虽然变短了，但是我们从外部看到的效果实际是由last指针
            // 决定的
            last = last.last;
        }
        return item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private DoubleNode current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
        }

        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();
        deque.addLast("hello");
        deque.addFirst("world");
        deque.addLast("haha");
        for (String s : deque) {
            StdOut.println(s);
        }
    }
}
