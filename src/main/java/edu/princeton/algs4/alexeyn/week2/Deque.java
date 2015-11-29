package edu.princeton.algs4.alexeyn.week2;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * @author Alexey Novakov
 */
public class Deque<Item> implements Iterable<Item> {
    private static final int DEFAULT_CAPACITY = 10;
    private Item[] array;
    private int size;
    private int head;

    public Deque() {
        array = (Item[]) new Object[DEFAULT_CAPACITY];
        resetHead();
    }

    private void resetHead() {
        head = array.length / 2;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        Objects.requireNonNull(item);
        if (!isEmpty()) {
            int newIndex = head - 1;

            if (newIndex >= 0) {
                addItemAt(newIndex, item);
                head = newIndex;
            } else {
                extendArray();
                addFirst(item);
            }
        } else {
            addItemAt(head, item);
        }
    }

    public void addLast(Item item) {
        Objects.requireNonNull(item);
        if (!isEmpty()) {
            int newIndex = head + size;

            if (newIndex < array.length) {
                addItemAt(newIndex, item);
            } else {
                extendArray();
                shrinkArray();
                addLast(item);
            }
        } else {
            addItemAt(head, item);
        }
    }

    private void addItemAt(int index, Item item) {
        array[index] = item;
        size++;
    }

    private void extendArray() {
        Item[] newArray = (Item[]) new Object[array.length * 2];
        int middle = newArray.length / 2;
        System.arraycopy(array, 0, newArray, middle, array.length);
        head = head + middle;
        array = newArray;
    }

    public Item removeFirst() {
        throwExceptionRemoveOnEmpty();

        Item item = array[head];
        removeItemAt(head);
        adjustHead();
        shrinkArray();

        return item;
    }

    private void adjustHead() {
        if (isEmpty())
            resetHead();
        else
            head++;
    }

    public Item removeLast() {
        throwExceptionRemoveOnEmpty();

        int lastIndex = head + size - 1;
        Item item = array[lastIndex];
        removeItemAt(lastIndex);
        shrinkArray();

        return item;
    }

    private void shrinkArray() {
        if (size != 0 && array.length / size > 2) {
            Item[] newArray = (Item[]) new Object[array.length / 2];
            System.arraycopy(array, head, newArray, 0, size);
            head = 0;
            array = newArray;
        }
    }

    private void throwExceptionRemoveOnEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("It is not possible to remove first element from empty Deque");
        }
    }

    private void removeItemAt(int index) {
        array[index] = null;
        size--;
    }

    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private int index = head;

        @Override
        public boolean hasNext() {
            return index < head + size;
        }

        @Override
        public Item next() {
            if (hasNext()) {
                return array[index++];
            }

            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
