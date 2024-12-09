package expressivo;

import java.util.List;
import java.util.Collections;

public class BinarySearchRecursive {

    // Recursive binary search for integers
    public static int binarySearchRecursive(int[] array, int target, int low, int high) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array cannot be null or empty");
        }

        // Base case: search range is empty
        if (low > high) {
            return -1;
        }

        int mid = low + (high - low) / 2;

        // Target found
        if (array[mid] == target) {
            return mid;
        }

        // Search in the left half
        if (target < array[mid]) {
            return binarySearchRecursive(array, target, low, mid - 1);
        }

        // Search in the right half
        return binarySearchRecursive(array, target, mid + 1, high);
    }

    // Recursive binary search for strings
    public static int binarySearchRecursive(String[] array, String target, int low, int high) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array cannot be null or empty");
        }

        // Base case: search range is empty
        if (low > high) {
            return -1;
        }

        int mid = low + (high - low) / 2;

        // Target found
        if (array[mid].equals(target)) {
            return mid;
        }

        // Search in the left half
        if (target.compareTo(array[mid]) < 0) {
            return binarySearchRecursive(array, target, low, mid - 1);
        }

        // Search in the right half
        return binarySearchRecursive(array, target, mid + 1, high);
    }

    // Recursive binary search variant to find all indices of a target value
    public static void findAllIndices(int[] array, int target, int low, int high, List<Integer> result) {
        if (low > high) {
            return;
        }

        int mid = low + (high - low) / 2;

        // If target found, check left and right for duplicates and add all
        if (array[mid] == target) {
            findAllIndices(array, target, low, mid - 1, result);
            result.add(mid);
            findAllIndices(array, target, mid + 1, high, result);
        } else if (target < array[mid]) {
            findAllIndices(array, target, low, mid - 1, result);
        } else {
            findAllIndices(array, target, mid + 1, high, result);
        }

        // Sort result to ensure indices are in correct order
        Collections.sort(result);
    }
}
