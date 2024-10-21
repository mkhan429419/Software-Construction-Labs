package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FilterTest {

    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");

    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    
    @Test
    public void testWrittenByEmptyList() {
        Filter filter = new Filter(Arrays.asList());
        filter.writtenBy("alyssa");
        List<Tweet> writtenBy = filter.getFilteredTweets();
        assertTrue("expected empty list", writtenBy.isEmpty());
    }

    @Test
    public void testWrittenByMultipleTweetsSingleResult() {
        Filter filter = new Filter(Arrays.asList(tweet1, tweet2));
        
        // Legal variant: Searching for "bbitdiddle" who has written tweet2
        filter.writtenBy("bbitdiddle");
        List<Tweet> writtenBy = filter.getFilteredTweets();
        
        // Expect that only tweet2 is returned
        assertEquals("expected singleton list", 1, writtenBy.size());
        assertTrue("expected list to contain tweet2", writtenBy.contains(tweet2));
    }


    @Test
    public void testWrittenByDifferentUsers() {
        Tweet tweet3 = new Tweet(3, "otheruser", "another tweet", d1);
        Filter filter = new Filter(Arrays.asList(tweet1, tweet2, tweet3));
        filter.writtenBy("alyssa");
        List<Tweet> writtenBy = filter.getFilteredTweets();
        
        assertEquals("expected singleton list", 1, writtenBy.size());
        assertTrue("expected list to contain tweet1", writtenBy.contains(tweet1));
    }

    @Test
    public void testWrittenByCaseSensitivity() {
        Filter filter = new Filter(Arrays.asList(tweet1, tweet2));
        filter.writtenBy("Alyssa");
        List<Tweet> writtenBy = filter.getFilteredTweets();
        
        assertEquals("expected singleton list", 1, writtenBy.size());
        assertTrue("expected list to contain tweet1", writtenBy.contains(tweet1));
    }

    @Test
    public void testInTimespanEmptyList() {
        Filter filter = new Filter(Arrays.asList());
        filter.inTimespan(new Timespan(d1, d2));
        List<Tweet> inTimespan = filter.getFilteredTweets();
        
        assertTrue("expected empty list", inTimespan.isEmpty());
    }

    @Test
    public void testInTimespanNoMatches() {
        Instant outsideStart = Instant.parse("2016-02-17T12:01:00Z");
        Instant outsideEnd = Instant.parse("2016-02-17T13:00:00Z");
        Filter filter = new Filter(Arrays.asList(tweet1, tweet2));
        filter.inTimespan(new Timespan(outsideStart, outsideEnd));
        List<Tweet> inTimespan = filter.getFilteredTweets();
        
        assertTrue("expected empty list", inTimespan.isEmpty());
    }

    @Test
    public void testInTimespanBoundary() {
        Instant testStart = Instant.parse("2016-02-17T10:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T11:00:00Z");
        Filter filter = new Filter(Arrays.asList(tweet1, tweet2));
        filter.inTimespan(new Timespan(testStart, testEnd));
        List<Tweet> inTimespan = filter.getFilteredTweets();
        
        assertEquals("expected list size", 1, inTimespan.size());
        assertTrue("expected list to contain tweet1", inTimespan.contains(tweet1));
    }

    @Test
    public void testContainingEmptyList() {
        Filter filter = new Filter(Arrays.asList());
        filter.containing(Arrays.asList("talk"));
        List<Tweet> containing = filter.getFilteredTweets();
        
        assertTrue("expected empty list", containing.isEmpty());
    }

    @Test
    public void testContainingNoMatches() {
        Filter filter = new Filter(Arrays.asList(tweet1, tweet2));
        filter.containing(Arrays.asList("unmatched"));
        List<Tweet> containing = filter.getFilteredTweets();
        
        assertTrue("expected empty list", containing.isEmpty());
    }

    @Test
    public void testContainingCaseInsensitivity() {
        Filter filter = new Filter(Arrays.asList(tweet1, tweet2));
        filter.containing(Arrays.asList("Talk"));
        List<Tweet> containing = filter.getFilteredTweets();
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweet1", containing.contains(tweet1));
    }

    @Test
    public void testContainingMultipleWords() {
        Filter filter = new Filter(Arrays.asList(tweet1, tweet2));
        
        // Legal variant: Searching for "reasonable" and "hype"
        filter.containing(Arrays.asList("reasonable", "hype"));
        List<Tweet> containing = filter.getFilteredTweets();
        
        // Expect both tweets to match as "reasonable" is in tweet1 and "hype" is in tweet2
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweet1", containing.contains(tweet1));
        assertTrue("expected list to contain tweet2", containing.contains(tweet2));
    }

}