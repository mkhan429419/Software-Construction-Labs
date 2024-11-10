package fileSearch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class StringPermutationsTest {

    @Test
    public void testEmptyInput() {
        List<String> permutations = StringPermutations.generatePermutations("", false);
        assertTrue("Permutations should be empty for an empty input", permutations.isEmpty());
    }

    @Test
    public void testSingleCharacter() {
        List<String> permutations = StringPermutations.generatePermutations("a", false);
        assertEquals("Single character should have one permutation", 1, permutations.size());
        assertEquals("a", permutations.get(0));
    }

    @Test
    public void testUniqueCharactersWithDuplicatesIncluded() {
        List<String> permutations = StringPermutations.generatePermutations("abc", true);
        Set<String> expected = new HashSet<>(Arrays.asList("abc", "acb", "bac", "bca", "cab", "cba"));
        assertEquals("Number of permutations should match expected size", expected.size(), permutations.size());
        assertTrue("All permutations should be unique", new HashSet<>(permutations).equals(expected));
    }

    @Test
    public void testUniqueCharactersWithoutDuplicates() {
        List<String> permutations = StringPermutations.generatePermutations("abc", false);
        Set<String> expected = new HashSet<>(Arrays.asList("abc", "acb", "bac", "bca", "cab", "cba"));
        assertEquals("Number of unique permutations should match expected size", expected.size(), permutations.size());
        assertTrue("Permutations should match expected", new HashSet<>(permutations).equals(expected));
    }

    @Test
    public void testDuplicateCharactersWithDuplicatesIncluded() {
        List<String> permutations = StringPermutations.generatePermutations("aab", true);
        Set<String> expected = new HashSet<>(Arrays.asList("aab", "aba", "baa"));
        assertEquals("Number of permutations should match expected size", 6, permutations.size());
        assertTrue("Permutations should contain expected elements", permutations.containsAll(expected));
    }

    @Test
    public void testDuplicateCharactersWithoutDuplicates() {
        List<String> permutations = StringPermutations.generatePermutations("aab", false);
        Set<String> expected = new HashSet<>(Arrays.asList("aab", "aba", "baa"));
        assertEquals("Number of unique permutations should match expected size", expected.size(), permutations.size());
        assertTrue("Permutations should match expected", new HashSet<>(permutations).equals(expected));
    }

    @Test
    public void testLongStringPerformance() {
        // A basic performance test with a string length of 4, expecting 4! = 24 unique permutations
        List<String> permutations = StringPermutations.generatePermutations("abcd", false);
        assertEquals("Permutations of a 4-character string should have 24 unique results", 24, permutations.size());
    }
}
