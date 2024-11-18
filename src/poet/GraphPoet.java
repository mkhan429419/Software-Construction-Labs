package poet;

import graph.ConcreteEdgesGraph;
import graph.Graph;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

/**
 * A graph-based poetry generator.
 */
public class GraphPoet {

    private final Graph<String> graph = new ConcreteEdgesGraph();

    // Abstraction function:
    //   Represents a graph-based poet where the graph contains words as vertices
    //   and adjacency relationships (with weights) between words as edges.

    // Representation invariant:
    //   - Words in the graph are case-insensitive.
    //   - Edges are derived from corpus adjacency counts.

    // Safety from rep exposure:
    //   - The graph is encapsulated as a private final field.
    //   - No external access to the graph.

    /**
     * Create a new poet with the graph from corpus (as described above).
     *
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException {
        List<String> lines = Files.readAllLines(corpus.toPath());
        String text = String.join(" ", lines).toLowerCase();

        String[] words = text.split("\\s+");
        for (int i = 0; i < words.length - 1; i++) {
            String source = words[i];
            String target = words[i + 1];
            int weight = graph.targets(source).getOrDefault(target, 0) + 1;
            graph.set(source, target, weight);
        }
        checkRep();
    }

    /**
     * Generate a poem.
     *
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String input) {
        String[] words = input.split("\\s+");
        StringBuilder poem = new StringBuilder();

        for (int i = 0; i < words.length - 1; i++) {
            String word1 = words[i].toLowerCase();
            String word2 = words[i + 1].toLowerCase();

            String bridge = null;
            int maxWeight = 0;

            for (String candidate : graph.targets(word1).keySet()) {
                if (graph.targets(candidate).containsKey(word2)) {
                    int weight = graph.targets(word1).get(candidate) + graph.targets(candidate).get(word2);
                    if (weight > maxWeight) {
                        bridge = candidate;
                        maxWeight = weight;
                    }
                }
            }

            poem.append(words[i]).append(" ");
            if (bridge != null) {
                poem.append(bridge).append(" ");
            }
        }
        poem.append(words[words.length - 1]);

        checkRep();
        return poem.toString();
    }

    /**
     * Checks the representation invariant of the GraphPoet class.
     */
    private void checkRep() {
        // Ensure all edges in the graph are valid.
        for (String vertex : graph.vertices()) {
            for (String target : graph.targets(vertex).keySet()) {
                assert graph.vertices().contains(target) : "Target vertex must exist in the graph.";
            }
        }
    }

    @Override
    public String toString() {
        return "GraphPoet with graph: " + graph.toString();
    }
}
