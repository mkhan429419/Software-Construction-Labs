package fileSearch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class StringPermutations {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get user input for the string
        System.out.print("Enter a string to generate permutations: ");
        String input = scanner.nextLine();

        // Get user choice for duplicate handling
        System.out.print("Include duplicate permutations? (yes/no): ");
        String choice = scanner.nextLine().trim().toLowerCase();

        boolean includeDuplicates = choice.equals("yes");

        // Generate permutations based on user choice
        List<String> permutations = generatePermutations(input, includeDuplicates);

        System.out.println("Generated permutations:");
        for (String permutation : permutations) {
            System.out.println(permutation);
        }

        scanner.close();
    }

    /**
     * Generates all permutations of a given string.
     *
     * @param input            The string for which permutations are generated.
     * @param includeDuplicates If true, includes duplicate permutations; if false, excludes them.
     * @return A list of all unique permutations of the input string.
     */
    public static List<String> generatePermutations(String input, boolean includeDuplicates) {
        List<String> result = new ArrayList<>();

        // Handle empty string case
        if (input == null || input.isEmpty()) {
            System.out.println("Error: Input string is empty.");
            return result;
        }

        if (includeDuplicates) {
            // Generate permutations with duplicates
            generatePermutationsWithDuplicates(input, "", result);
        } else {
            // Generate unique permutations using a set to filter duplicates
            Set<String> uniquePermutations = new HashSet<>();
            generatePermutationsWithoutDuplicates(input, "", uniquePermutations);
            result.addAll(uniquePermutations);
        }

        return result;
    }

    /**
     * Recursive helper function to generate permutations with duplicates allowed.
     */
    private static void generatePermutationsWithDuplicates(String str, String prefix, List<String> result) {
        if (str.isEmpty()) {
            result.add(prefix);
        } else {
            for (int i = 0; i < str.length(); i++) {
                String remaining = str.substring(0, i) + str.substring(i + 1);
                generatePermutationsWithDuplicates(remaining, prefix + str.charAt(i), result);
            }
        }
    }

    /**
     * Recursive helper function to generate unique permutations without duplicates.
     */
    private static void generatePermutationsWithoutDuplicates(String str, String prefix, Set<String> result) {
        if (str.isEmpty()) {
            result.add(prefix);
        } else {
            for (int i = 0; i < str.length(); i++) {
                String remaining = str.substring(0, i) + str.substring(i + 1);
                generatePermutationsWithoutDuplicates(remaining, prefix + str.charAt(i), result);
            }
        }
    }
}
