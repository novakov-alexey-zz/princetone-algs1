package edu.princeton.algs4.alexeyn.week2;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * @author Alexey Novakov
 */
public class RandomizedQueueTest {

    private RandomizedQueue<Item> queue;

    @Before
    public void setUp() {
        queue = new RandomizedQueue<>();
    }

    @Test
    public void testIsEmpty() throws Exception {
        //then
        assertTrue(queue.isEmpty());

        //when
        queue.enqueue(new Item());
        queue.dequeue();
        //then
        assertTrue(queue.isEmpty());
    }

    @Test
    public void testSize() throws Exception {
        //when
        queue.enqueue(new Item());
        queue.enqueue(new Item());
        //then
        assertEquals(2, queue.size());

        //when
        queue.dequeue();
        //then
        assertEquals(1, queue.size());

        //when
        queue.dequeue();
        //then
        assertEquals(0, queue.size());
    }

    @Test
    public void testEnqueue() throws Exception {
        //when
        Item itemA = new Item();
        queue.enqueue(itemA);
        //then
        assertEquals(1, queue.size());
        assertEquals(itemA, queue.dequeue());
    }

    @Test
    public void testEnqueDequeCalls() {
        //when
        for (int i = 0; i < 50; i++) {
            queue.enqueue(new Item(i));
            queue.dequeue();
        }

        //then
        assertTrue(queue.isEmpty());
    }

    @Test(expected = NoSuchElementException.class)
    public void testQueueThrowsExceptionWhenDequeOnEmpty() {
        //when
        queue.dequeue();
    }

    @Test
    public void testSample() throws Exception {
        //given
        Item item = new Item();

        //when
        queue.enqueue(item);
        Item actualItem = queue.sample();

        //then
        assertEquals(item, actualItem);
        assertFalse(queue.isEmpty());
    }

    @Test
    public void testIterator() throws Exception {
        //when
        Iterator it = queue.iterator();
        //then
        assertNotNull(it);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIteratorThrowsExceptionOnRemove() {
        //given
        Iterator it = new RandomizedQueue().iterator();
        //when
        it.remove();
    }

    @Test
    public void testIteratorReturnsRandomItem() throws Exception {
        //given
        Item[] origSequence = new Item[]{new Item(), new Item(), new Item()};
        for (Item item : origSequence) {
            queue.enqueue(item);
        }

        boolean alwaysEquals = true;

        //when
        for (int i = 0; i < 3; i++) {
            List<Item> exItems = new ArrayList<>();
            for (Item item : queue) {
                exItems.add(item);
            }

            alwaysEquals &= Arrays.deepEquals(origSequence, exItems.toArray());
        }


        //then
        assertFalse(alwaysEquals);
    }

    @Test
    public void testIteratorHasNext() {
        //given
        queue.enqueue(new Item());
        queue.enqueue(new Item());
        Iterator<Item> it = queue.iterator();

        //when
        it.next();
        //then
        assertTrue(it.hasNext());

        //when
        it.next();
        //then
        assertFalse(it.hasNext());
    }

    @Test
    public void testTwoIteratorsDoNotAffectEachOther() {
        //given
        queue.enqueue(new Item());
        queue.enqueue(new Item());
        Iterator<Item> it1 = queue.iterator();
        Iterator<Item> it2 = queue.iterator();

        //when
        it1.next();
        it1.next();
        //then
        assertFalse(it1.hasNext());
        assertTrue(it2.hasNext());

        //when
        it2.next();
        //then
        assertTrue(it2.hasNext());

        //when
        it2.next();
        //then
        assertFalse(it2.hasNext());
    }

    @Test(expected = NoSuchElementException.class)
    public void testRandomIteratorThrowsExceptionOnNextWhenEmpty() {
        //when
        queue.iterator().next();
    }
}