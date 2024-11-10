package fileSearch;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecursiveFileSearch {

    // Main method to start the program
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java RecursiveFileSearch <directory_path> <file_name1> <file_name2> ... <case_sensitive(true/false)>");
            System.exit(1);
        }

        String directoryPath = args[0];
        boolean caseSensitive = Boolean.parseBoolean(args[args.length - 1]); // Last argument as case-sensitivity flag
        List<String> fileNames = new ArrayList<>();

        // Collect all the filenames from the command-line arguments except the directory path and case-sensitivity flag
        for (int i = 1; i < args.length - 1; i++) {
            fileNames.add(args[i]);
        }

        File directory = new File(directoryPath);

        // Validate if the provided directory exists and is a directory
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("Invalid directory path: " + directoryPath);
            System.exit(1);
        }

        System.out.println("Searching for files...");

        // Search for each file
        for (String fileName : fileNames) {
            int count = searchFiles(directory, fileName, caseSensitive, new ArrayList<>());
            if (count > 0) {
                System.out.println("Found \"" + fileName + "\" " + count + " time(s).");
            } else {
                System.out.println("File \"" + fileName + "\" not found.");
            }
        }
    }

    /**
     * Recursive method to search for a specific file within a directory and its subdirectories.
     *
     * @param directory     The directory to search within.
     * @param fileName      The name of the file to search for.
     * @param caseSensitive Whether the search should be case-sensitive.
     * @param results       A list to store found file paths.
     * @return The count of occurrences of the file in the directory.
     */
    public static int searchFiles(File directory, String fileName, boolean caseSensitive, List<String> results) {
        int count = 0;

        // Get all files and directories in the current directory
        File[] files = directory.listFiles();
        if (files == null) return 0; // Return if directory is empty or cannot be read

        for (File file : files) {
            if (file.isDirectory()) {
                // Recursive call for subdirectories
                count += searchFiles(file, fileName, caseSensitive, results);
            } else {
                // Check if the file name matches the search criteria
                boolean match = caseSensitive ? 
                        file.getName().equals(fileName) : 
                        file.getName().equalsIgnoreCase(fileName);

                if (match) {
                    results.add(file.getAbsolutePath());
                    count++;
                }
            }
        }

        // Display paths of found files
        if (!results.isEmpty()) {
            for (String path : results) {
                System.out.println("Found: " + path);
            }
        }
        return count;
    }
}
