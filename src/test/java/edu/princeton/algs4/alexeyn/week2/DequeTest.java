package edu.princeton.algs4.alexeyn.week2;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * @author Alexey Novakov
 */
public class DequeTest {
    private Deque<Item> deque;

    @Before
    public void setUp() throws Exception {
        deque = new Deque<>();
    }

    @Test
    public void testIsEmpty() throws Exception {
        //then
        assertTrue(deque.isEmpty());
    }

    @Test
    public void testSize() throws Exception {
        //when
        deque.addFirst(new Item());
        deque.addFirst(new Item());
        //then
        assertEquals(2, deque.size());

        //when
        deque.addFirst(new Item());
        //then
        assertEquals(3, deque.size());
    }

    @Test
    public void testRemoveFirstReturnsLastAddedDeque() throws Exception {
        //when
        Item itemA = new Item();
        deque.addFirst(itemA);
        Item itemB = new Item();
        deque.addFirst(itemB);
        //then
        assertEquals(itemB, deque.removeFirst());
        assertEquals(itemA, deque.removeFirst());
    }

    @Test
    public void testRemoveFirstReturnFirstDeque() throws Exception {
        //when
        Item itemA = new Item();
        deque.addFirst(itemA);
        Item itemB = new Item();
        deque.addLast(itemB);
        //then
        assertEquals(itemA, deque.removeFirst());
        assertEquals(itemB, deque.removeFirst());
    }

    @Test
    public void testAddLastAppendElementToTheTail() throws Exception {
        //when
        Item itemA = new Item();
        deque.addLast(itemA);
        Item itemB = new Item();
        deque.addLast(itemB);
        //then
        assertEquals(itemA, deque.removeFirst());
        assertEquals(itemB, deque.removeFirst());
    }

    @Test
    public void testAddLastWhenCapacityNeedsToExtended() {
        //given
        Deque<Item> deque = new Deque<Item>();

        //when
        deque.addLast(new Item());
        deque.addLast(new Item());
        deque.addLast(new Item());

        //then
        assertEquals(3, deque.size());
//        assertEquals(4, deque.getCapacity());
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveFirstThrowsExceptionWhenEmptyDeque() {
        //when
        deque.removeFirst();
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveLastThrowsExceptionWhenEmptyDeque() {
        //when
        deque.removeLast();
    }

    @Test(expected = NullPointerException.class)
    public void testAddFirstNullDeque() {
        //when
        deque.addFirst(null);
    }

    @Test(expected = NullPointerException.class)
    public void testAddLastNullDequeThrowsException() {
        //when
        deque.addLast(null);
    }

    @Test
    public void testRemoveLast() throws Exception {
        //when
        Item itemA = new Item();
        deque.addFirst(itemA);
        Item itemB = new Item();
        deque.addFirst(itemB);
        //then
        assertEquals(itemA, deque.removeLast());
    }

    @Test
    public void testAddRemoveDoesNotThrowsOutOfBoundExceptionCalls() {
        //when
        for (int i = 0; i < 20; i++) {
            deque.addFirst(new Item());
            deque.removeFirst();
        }
        deque.addFirst(new Item());
        //then
        assertFalse(deque.isEmpty());
    }

    @Test
    public void testAddFirstRemoveLast() {
        //when
        for (int i = 0; i < 10; i++) {
            deque.addFirst(new Item(i));
        }
        //then
        assertEquals(0, deque.removeLast().getIntValue());
    }

    @Test
    public void testAddLastRemoveLast() {
        //when
        for (int i = 0; i < 10; i++) {
            deque.addLast(new Item(i));
        }
        //then
        assertEquals(9, deque.removeLast().getIntValue());
    }

//    @Test
//    public void testCapacityIncreased() {
//        //given
//        Deque deque = new Deque(1);
//        //when
//        deque.addFirst(new  Item());
//        deque.addFirst(new  Item());
//        //then
//        assertTrue(deque.getCapacity() >= 2);
//    }
//
//    @Test
//    public void testCapacityIncreasedByTwo() {
//        //given
//        int initialCapacity = 1;
//        Deque deque = new Deque(initialCapacity);
//        //when
//        int addCount = 4;
//        IntStream.range(0, addCount).forEach((i) -> deque.addFirst(new  Item()));
//        //then
//        int increasedCount = initialCapacity - addCount >= initialCapacity ? 0 : addCount - initialCapacity;
//        int increasedCapacity = initialCapacity * (int) Math.pow(2, increasedCount);
//        assertEquals(increasedCapacity, deque.getCapacity());
//    }

    @Test
    public void testRemoveWhenCapacityIsGrowing() {
        //given
        Deque<Item> deque = new Deque<Item>();
        //when
        Item itemA = new Item();
        deque.addFirst(itemA);

        Item itemB = new Item();
        deque.addFirst(itemB);

        Item itemC = new Item();
        deque.addLast(itemC);

        //then
        assertEquals(itemB, deque.removeFirst());
        assertEquals(itemC, deque.removeLast());
        assertEquals(itemA, deque.removeLast());
    }

//    @Test
//    public void testRemoveCapacityShrinksWhenRemoving() {
//        //when
//        deque.addFirst(new  Item());
//        deque.addFirst(new  Item());
//        deque.removeLast();
//        //then
//        assertEquals(10 / 2, deque.getCapacity());
//    }

//    @Test
//    public void testElementsReturnedWhenArrayShrinks() {
//        //given
//         Item itemA = new  Item();
//        deque.addFirst(itemA);
//         Item itemB = new  Item();
//        deque.addFirst(itemB);
//         Item itemC = new  Item();
//        deque.addFirst(itemC);
//
//        //when
//        deque.removeFirst();
//
//        //then
//        assertEquals(5, deque.getCapacity());
//        assertEquals(itemB, deque.removeFirst());
//        assertEquals(2, deque.getCapacity());
//        assertEquals(itemA, deque.removeFirst());
//        assertEquals(2, deque.getCapacity());
//    }

    @Test
    public void testIteratorNotNull() throws Exception {
        //when
        assertNotNull(deque.iterator());
    }

    @Test
    public void testIteratorReturnsAllDeque() {
        //when
        Item itemA = new Item();
        deque.addFirst(itemA);
        Item itemB = new Item();
        deque.addFirst(itemB);
        Item itemC = new Item();
        deque.addFirst(itemC);

        //then
        Iterator<Item> it = deque.iterator();
        assertEquals(itemC, it.next());
        assertEquals(itemB, it.next());
        assertEquals(itemA, it.next());
        assertFalse(it.hasNext());
    }

    @Test(expected = NoSuchElementException.class)
    public void testIteratorThrowsExceptionOnNextWhenNoDeque() {
        //when
        deque.addFirst(new Item());
        Iterator<Item> it = deque.iterator();
        it.next();
        it.next();
    }

    @Test(expected = NoSuchElementException.class)
    public void testIteratorThrowsExceptionOnNextWhenEmptyDeque() {
        //when
        deque.iterator().next();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIteratorThrowsExceptionOnRemove() {
        //when
        deque.iterator().remove();
    }
}