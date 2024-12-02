package expressivo;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    private final List<String> tokens;
    private int position;

    public Tokenizer(String input) {
        tokens = tokenize(input);
        position = 0;
    }

    private List<String> tokenize(String input) {
        List<String> tokens = new ArrayList<>();
        int i = 0;

        while (i < input.length()) {
            char ch = input.charAt(i);

            if (Character.isDigit(ch) || ch == '.') {
                int start = i;
                while (i < input.length() && (Character.isDigit(input.charAt(i)) || input.charAt(i) == '.')) {
                    i++;
                }
                tokens.add(input.substring(start, i));
            } else if (Character.isLetter(ch)) {
                int start = i;
                while (i < input.length() && Character.isLetterOrDigit(input.charAt(i))) {
                    i++;
                }
                tokens.add(input.substring(start, i));
            } else if ("()+*".indexOf(ch) != -1) {
                tokens.add(String.valueOf(ch));
                i++;
            } else {
                throw new IllegalArgumentException("Invalid character: " + ch);
            }
        }

        return tokens;
    }

    public boolean hasNext() {
        return position < tokens.size();
    }

    public String peek() {
        return hasNext() ? tokens.get(position) : null;
    }

    public String consume() {
        return hasNext() ? tokens.get(position++) : null;
    }

    public void consume(String expected) {
        if (!expected.equals(consume())) {
            throw new IllegalArgumentException("Expected " + expected);
        }
    }
}
