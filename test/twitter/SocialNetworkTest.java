package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.*;

import org.junit.Test;

public class SocialNetworkTest {

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());
        assertTrue("Expected empty graph", followsGraph.isEmpty());
    }

    @Test
    public void testGuessFollowsGraphNoMentions() {
        List<Tweet> tweets = Arrays.asList(
                new Tweet(1L, "aimen", "I love programming lol", Instant.now())
        );
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
        assertTrue("Expected empty graph", followsGraph.isEmpty());
    }

    @Test
    public void testGuessFollowsGraphSingleMention() {
        List<Tweet> tweets = Arrays.asList(
                new Tweet(1L, "aimen", "Hi @maheen!!!!!", Instant.now())
        );
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);

        assertEquals("Expected 1 follower", 1, followsGraph.size());
        assertTrue("Aimen should follow Maheen", followsGraph.get("aimen").contains("maheen"));
    }

    @Test
    public void testGuessFollowsGraphMultipleMentions() {
        List<Tweet> tweets = Arrays.asList(
                new Tweet(1L, "aimen", "@maheen @hadiya did y'all see??", Instant.now())
        );
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);

        assertEquals("Expected 1 follower", 1, followsGraph.size());
        assertTrue(followsGraph.get("aimen").containsAll(Arrays.asList("maheen", "hadiya")));
    }

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

    @Test
    public void testInfluencersEmptyGraph() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        assertTrue("Expected empty list", influencers.isEmpty());
    }

    @Test
    public void testInfluencersSingleUserNoFollowers() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("aimen", new HashSet<>());

        List<String> influencers = SocialNetwork.influencers(followsGraph);
        assertTrue("Expected empty list", influencers.isEmpty());
    }

    @Test
    public void testInfluencersSingleInfluencer() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("aimen", new HashSet<>(Arrays.asList("maheen")));

        List<String> influencers = SocialNetwork.influencers(followsGraph);
        assertEquals("Expected one influencer", 1, influencers.size());
        assertEquals("maheen", influencers.get(0));
    }

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
