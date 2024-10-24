package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParallelSearchTest {

    @Test
    public void testSmallArray() {
        Integer[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int result = ParallelSearch.search(arr, 6);
        assertEquals(5, result);
    }

    @Test
    public void testLargeArrayRecursiveSearch() {
        Integer[] array = new Integer[1000];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        int result = ParallelSearch.search(array, 998);
        assertEquals(998, result);
    }

    @Test
    public void testElementNotFound() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int result = ParallelSearch.search(array, 11);
        assertEquals(-1, result);
    }

    @Test
    public void testDifferentTypes() {
        String[] array = {"apple", "banana", "cherry", "date", "elderberry"};
        int result = ParallelSearch.search(array, "cherry");
        assertEquals(2, result);
    }

    @Test
    public void testEmptyArray() {
        Integer[] array = {};
        int result = ParallelSearch.search(array, 1);
        assertEquals(-1, result);
    }
}