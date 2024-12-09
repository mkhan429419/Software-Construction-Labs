package expressivo;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class BinarySearchRecursiveTest {

    @Test
    public void testBinarySearchRecursiveIntegers() {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        assertEquals(4, BinarySearchRecursive.binarySearchRecursive(array, 5, 0, array.length - 1));
        assertEquals(-1, BinarySearchRecursive.binarySearchRecursive(array, 10, 0, array.length - 1));
    }

    @Test
    public void testBinarySearchRecursiveStrings() {
        String[] array = {"apple", "banana", "cherry", "date", "fig"};
        assertEquals(2, BinarySearchRecursive.binarySearchRecursive(array, "cherry", 0, array.length - 1));
        assertEquals(-1, BinarySearchRecursive.binarySearchRecursive(array, "grape", 0, array.length - 1));
    }

    @Test
    public void testFindAllIndices() {
        int[] array = {1, 2, 2, 2, 3, 4, 5};
        List<Integer> result = new ArrayList<>();
        BinarySearchRecursive.findAllIndices(array, 2, 0, array.length - 1, result);
        assertEquals(List.of(1, 2, 3), result);

        result.clear();
        BinarySearchRecursive.findAllIndices(array, 6, 0, array.length - 1, result);
        assertTrue(result.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullArray() {
        BinarySearchRecursive.binarySearchRecursive((int[]) null, 5, 0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyArray() {
        BinarySearchRecursive.binarySearchRecursive(new int[]{}, 5, 0, 0);
    }
}
