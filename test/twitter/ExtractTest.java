package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class ExtractTest {

    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T12:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "Hello @bob!", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "Good morning @Alice and @Bob!", d2);
    private static final Tweet tweet3 = new Tweet(3, "user", "No mentions here", d3);

    @Test
    public void testGetTimespanSingleTweet() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet3));  // Using tweet3 instead of tweet1
        assertEquals("expected start", d3, timespan.getStart());
        assertEquals("expected end", d3, timespan.getEnd());
    }



    @Test
    public void testGetTimespanMultipleTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }

    @Test
    public void testGetTimespanEmptyList() {
        Timespan timespan = Extract.getTimespan(Arrays.asList());
        assertNull("expected start to be null", timespan.getStart());
        assertNull("expected end to be null", timespan.getEnd());
    }

    @Test
    public void testGetMentionedUsersNoMentions() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet3));
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }

    @Test
    public void testGetMentionedUsersSingleMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        Set<String> expected = new HashSet<>(Arrays.asList("bob"));
        assertEquals("expected mentioned users", expected, mentionedUsers);
    }

    @Test
    public void testGetMentionedUsersMultipleMentions() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1, tweet2));
        Set<String> expected = new HashSet<>(Arrays.asList("bob", "alice"));
        assertEquals("expected mentioned users", expected, mentionedUsers);
    }

    @Test
    public void testGetMentionedUsersMixedCase() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet2));
        Set<String> expected = new HashSet<>(Arrays.asList("alice", "bob"));
        assertEquals("expected mentioned users to be case insensitive", expected, mentionedUsers);
    }
}