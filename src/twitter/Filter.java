package twitter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Filter {
    private List<Tweet> tweets;
    private String username;
    private List<Tweet> filteredTweets;

    // Constructor to initialize tweets
    public Filter(List<Tweet> tweets) {
        this.tweets = tweets;
        this.filteredTweets = new ArrayList<>();
    }

    /**
     * Find tweets written by a particular user.
     */
    public void writtenBy(String username) {
        this.username = username;
        filteredTweets = tweets.stream()
            .filter(tweet -> tweet.getAuthor().equalsIgnoreCase(username))
            .collect(Collectors.toList());
    }

    /**
     * Get the filtered tweets.
     * 
     * @return the list of tweets written by the specified user.
     */
    public List<Tweet> getFilteredTweets() {
        return filteredTweets;
    }

    /**
     * Find tweets that were sent during a particular timespan.
     * 
     * @param timespan the timespan
     */
    public void inTimespan(Timespan timespan) {
        filteredTweets = tweets.stream()
            .filter(tweet -> !tweet.getTimestamp().isBefore(timespan.getStart()) &&
                             !tweet.getTimestamp().isAfter(timespan.getEnd()))
            .collect(Collectors.toList());
    }

    /**
     * Find tweets that contain certain words.
     * 
     * @param words a list of words to search for in the tweets.
     */
    public void containing(List<String> words) {
        filteredTweets = tweets.stream()
            .filter(tweet -> words.stream().anyMatch(word ->
                tweet.getText().toLowerCase().contains(word.toLowerCase())))
            .collect(Collectors.toList());
    }
}
