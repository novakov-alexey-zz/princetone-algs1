package edu.princeton.algs4.alexeyn.week2;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * @author Alexey Novakov
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int DEFAULT_CAPACITY = 10;
    private Item[] array;
    private int size;

    public RandomizedQueue() {
        array = (Item[]) new Object[DEFAULT_CAPACITY];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        Objects.requireNonNull(item);
        if (size == array.length) {
            extendArray();
        }

        array[size++] = item;
    }

    private void extendArray() {
        Item[] newArray = (Item[]) new Object[array.length * 2];
        System.arraycopy(array, 0, newArray, 0, array.length);
        array = newArray;
    }

    public Item dequeue() {
        throwExceptionWhenEmpty();

        int removeIndex = getRandomIndex(size);
        Item item = array[removeIndex];
        array = shrinkArray(array, removeIndex);
        size--;

        return item;
    }

    private int getRandomIndex(int max) {
        return StdRandom.uniform(0, max);
    }

    private Item[] shrinkArray(Item[] origArray, int removeIndex) {
        Item[] newArray = (Item[]) new Object[origArray.length];
        System.arraycopy(origArray, 0, newArray, 0, removeIndex);
        System.arraycopy(origArray, removeIndex + 1, newArray, removeIndex, origArray.length - 1 - removeIndex);
        return newArray;
    }

    private void throwExceptionWhenEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    public Item sample() {
        throwExceptionWhenEmpty();
        return array[getRandomIndex(size)];
    }

    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {
        private Item[] items = array;
        private int itemsSize = size;

        @Override
        public boolean hasNext() {
            return itemsSize > 0;
        }

        @Override
        public Item next() {
            if (hasNext()) {
                int nextIndex = getRandomIndex(itemsSize);
                Item item = items[nextIndex];
                items = shrinkArray(items, nextIndex);
                itemsSize--;

                return item;
            }

            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
