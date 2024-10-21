package twitter;

import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.time.Instant;



public class Extract {

    public static Timespan getTimespan(List<Tweet> tweets) {
        if (tweets.isEmpty()) {
            return new Timespan(null, null); // Assuming Timespan constructor handles nulls appropriately
        }
        
        Instant start = tweets.get(0).getTimestamp();
        Instant end = tweets.get(0).getTimestamp();
        
        for (Tweet tweet : tweets) {
            if (tweet.getTimestamp().isBefore(start)) {
                start = tweet.getTimestamp();
            }
            if (tweet.getTimestamp().isAfter(end)) {
                end = tweet.getTimestamp();
            }
        }
        
        return new Timespan(start, end);
    }

    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        Set<String> mentionedUsers = new HashSet<>();
        
        for (Tweet tweet : tweets) {
            String text = tweet.getText();
            String[] words = text.split("\\s+"); // Split by whitespace
            
            for (String word : words) {
                if (word.startsWith("@")) {
                    String username = word.substring(1).toLowerCase(); // Remove '@' and convert to lowercase
                    mentionedUsers.add(username); // Add to set (duplicates will be ignored)
                }
            }
        }
        
        return mentionedUsers;
    }
}
