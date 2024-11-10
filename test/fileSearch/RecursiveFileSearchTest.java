package fileSearch;
import static org.junit.Assert.*;
import org.junit.Test;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecursiveFileSearchTest {

	@Test
	public void testFileFoundCaseSensitive() {
	    // Use the actual directory where the file is located
	    File directory = new File("C:/Users/user/Downloads/ps1/ps1/test/fileSearch/testforlab");
	    String fileName = "maheen.txt.txt";
	    boolean caseSensitive = true;

	    List<String> results = new ArrayList<>();
	    int count = RecursiveFileSearch.searchFiles(directory, fileName, caseSensitive, results);

	    assertEquals(1, count);
	    assertTrue(results.contains(new File("C:/Users/user/Downloads/ps1/ps1/test/fileSearch/testforlab/maheen.txt.txt").getAbsolutePath()));
	}

    @Test
    public void testFileNotFound() {
        File directory = new File("src/test/resources");
        String fileName = "nonexistent.txt";
        boolean caseSensitive = true;

        List<String> results = new ArrayList<>();
        int count = RecursiveFileSearch.searchFiles(directory, fileName, caseSensitive, results);

        assertEquals(0, count);
        assertTrue(results.isEmpty());
    }

    @Test
    public void testCaseInsensitiveSearch() {
        // Use the actual directory where the file is located
        File directory = new File("C:/Users/user/Downloads/ps1/ps1/test/fileSearch/testforlab");
        String fileName = "TeSt.TxT.TxT";  // Case variation of the actual file
        boolean caseSensitive = false;

        List<String> results = new ArrayList<>();
        int count = RecursiveFileSearch.searchFiles(directory, fileName, caseSensitive, results);

        assertEquals(1, count);
        assertTrue(results.contains(new File("C:/Users/user/Downloads/ps1/ps1/test/fileSearch/testforlab/test.txt.txt").getAbsolutePath()));
    }


    @Test
    public void testSearchInSubdirectories() {
        // Use the actual directory where the file is located
        File directory = new File("C:/Users/user/Downloads/ps1/ps1/test/fileSearch/testforlab");
        String fileName = "testt.txt"; 
        boolean caseSensitive = true;

        List<String> results = new ArrayList<>();
        int count = RecursiveFileSearch.searchFiles(directory, fileName, caseSensitive, results);

        assertEquals(1, count);
        assertTrue(results.contains(new File("C:/Users/user/Downloads/ps1/ps1/test/fileSearch/testforlab/subdir/testt.txt").getAbsolutePath()));
    }


    @Test
    public void testInvalidDirectory() {
        File directory = new File("invalid/path");
        String fileName = "sample.txt";
        boolean caseSensitive = true;

        List<String> results = new ArrayList<>();
        int count = RecursiveFileSearch.searchFiles(directory, fileName, caseSensitive, results);

        assertEquals(0, count);
        assertTrue(results.isEmpty());
    }
}
