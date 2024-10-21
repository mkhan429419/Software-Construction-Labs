package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.*;

import org.junit.Test;

public class SocialNetworkTest {

    /**
     * Ensures that assertions are enabled by checking if an assertion failure occurs.
     * This test only passes if the VM argument "-ea" (enable assertions) is active.
     */
    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // Assertion should fail if assertions are enabled.
    }

    /**
     * Tests the guessFollowsGraph() method with an empty list of tweets.
     * Expects the result to be an empty graph since no evidence exists.
     */
    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());
        assertTrue("Expected empty graph", followsGraph.isEmpty());
    }

    /**
     * Tests the guessFollowsGraph() method with a tweet that contains no mentions.
     * Expects an empty graph since no user is mentioned in the tweet.
     */
    @Test
    public void testGuessFollowsGraphNoMentions() {
        List<Tweet> tweets = Arrays.asList(
                new Tweet(1L, "aimen", "I love programming lol", Instant.now())
        );
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
        assertTrue("Expected empty graph", followsGraph.isEmpty());
    }

    /**
     * Tests the guessFollowsGraph() method with a tweet that contains a single mention.
     * Expects a graph where the author ("aimen") follows the mentioned user ("maheen").
     */
    @Test
    public void testGuessFollowsGraphSingleMention() {
        List<Tweet> tweets = Arrays.asList(
                new Tweet(1L, "aimen", "Hi @maheen!!!!!", Instant.now())
        );
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);

        assertEquals("Expected 1 follower", 1, followsGraph.size());
        assertTrue("Aimen should follow Maheen", followsGraph.get("aimen").contains("maheen"));
    }

    /**
     * Tests the guessFollowsGraph() method with a tweet containing multiple mentions.
     * Expects a graph where "aimen" follows both "maheen" and "hadiya".
     */
    @Test
    public void testGuessFollowsGraphMultipleMentions() {
        List<Tweet> tweets = Arrays.asList(
                new Tweet(1L, "aimen", "@maheen @hadiya did y'all see??", Instant.now())
        );
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);

        assertEquals("Expected 1 follower", 1, followsGraph.size());
        assertTrue(followsGraph.get("aimen").containsAll(Arrays.asList("maheen", "hadiya")));
    }

    /**
     * Tests the guessFollowsGraph() method with multiple tweets from the same author.
     * Expects a graph where the author ("aimen") follows all mentioned users.
     */
    @Test
    public void testGuessFollowsGraphMultipleTweetsSameAuthor() {
        List<Tweet> tweets = Arrays.asList(
                new Tweet(1L, "aimen", "ICU @maheen!!!", Instant.now()),
                new Tweet(2L, "aimen", "Daz crazy @hadiya", Instant.now())
        );
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);

        assertEquals("Expected 1 follower", 1, followsGraph.size());
        assertTrue(followsGraph.get("aimen").containsAll(Arrays.asList("maheen", "hadiya")));
    }

    /**
     * Tests the influencers() method with an empty graph.
     * Expects an empty list of influencers since no users exist in the graph.
     */
    @Test
    public void testInfluencersEmptyGraph() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        assertTrue("Expected empty list", influencers.isEmpty());
    }

    /**
     * Tests the influencers() method with a single user who has no followers.
     * Expects an empty list of influencers since the user is not followed by anyone.
     */
    @Test
    public void testInfluencersSingleUserNoFollowers() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("aimen", new HashSet<>());

        List<String> influencers = SocialNetwork.influencers(followsGraph);
        assertTrue("Expected empty list", influencers.isEmpty());
    }

    /**
     * Tests the influencers() method with a single user who is followed by one other user.
     * Expects the mentioned user ("maheen") to be identified as the top influencer.
     */
    @Test
    public void testInfluencersSingleInfluencer() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("aimen", new HashSet<>(Arrays.asList("maheen")));

        List<String> influencers = SocialNetwork.influencers(followsGraph);
        assertEquals("Expected one influencer", 1, influencers.size());
        assertEquals("maheen", influencers.get(0));
    }

    /**
     * Tests the influencers() method with multiple users and varying numbers of followers.
     * Expects "hadiya" to be the top influencer, followed by "maheen".
     */
    @Test
    public void testInfluencersMultipleInfluencers() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("aimen", new HashSet<>(Arrays.asList("maheen", "hadiya")));
        followsGraph.put("maheen", new HashSet<>(Arrays.asList("hadiya")));

        List<String> influencers = SocialNetwork.influencers(followsGraph);
        assertEquals("Expected two influencers", 2, influencers.size());
        assertEquals("hadiya", influencers.get(0));
        assertEquals("maheen", influencers.get(1));
    }

    /**
     * Tests the influencers() method when multiple users have the same number of followers.
     * Expects "maheen" to be identified as the influencer (order doesn't matter in a tie).
     */
    @Test
    public void testInfluencersEqualFollowers() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("aimen", new HashSet<>(Arrays.asList("maheen")));
        followsGraph.put("hadiya", new HashSet<>(Arrays.asList("maheen")));

        List<String> influencers = SocialNetwork.influencers(followsGraph);
        assertEquals("Expected one influencer", 1, influencers.size());
        assertEquals("maheen", influencers.get(0));
    }
}
