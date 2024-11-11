package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;

/**
 * An implementation of Graph.
 */
public class ConcreteVerticesGraph implements Graph<String> {
    
    private final List<Vertex> vertices = new ArrayList<>();
    
    // Abstraction function:
    //   Represents a directed graph where each vertex has outgoing edges to other vertices.
    
    // Representation invariant:
    //   Each vertex in vertices has a unique label.
    
    // Safety from rep exposure:
    //   vertices is a private final field, and we return copies of mutable collections.
    
    public ConcreteVerticesGraph() {
        checkRep();
    }
    
    private void checkRep() {
        for (Vertex vertex : vertices) {
            assert vertex != null : "Vertices must not contain null elements";
        }
    }
    
    @Override
    public boolean add(String vertexLabel) {
        for (Vertex vertex : vertices) {
            if (vertex.getLabel().equals(vertexLabel)) return false;
        }
        vertices.add(new Vertex(vertexLabel));
        checkRep();
        return true;
    }
    
    @Override
    public int set(String source, String target, int weight) {
        if (weight < 0) throw new IllegalArgumentException("Weight cannot be negative");

        Vertex sourceVertex = findVertex(source);
        if (sourceVertex == null) {
            sourceVertex = new Vertex(source);
            vertices.add(sourceVertex);
        }

        Vertex targetVertex = findVertex(target);
        if (targetVertex == null) {
            targetVertex = new Vertex(target);
            vertices.add(targetVertex);
        }

        int previousWeight = sourceVertex.setEdge(target, weight);
        checkRep();
        return previousWeight;
    }

    private Vertex findVertex(String label) {
        for (Vertex vertex : vertices) {
            if (vertex.getLabel().equals(label)) {
                return vertex;
            }
        }
        return null;
    }
    
    @Override
    public boolean remove(String vertexLabel) {
        Vertex vertex = findVertex(vertexLabel);
        if (vertex == null) return false;

        vertices.remove(vertex);
        for (Vertex v : vertices) {
            v.removeEdge(vertexLabel);
        }
        checkRep();
        return true;
    }
    
    @Override
    public Set<String> vertices() {
        Set<String> labels = new HashSet<>();
        for (Vertex vertex : vertices) {
            labels.add(vertex.getLabel());
        }
        return labels;
    }
    
    @Override
    public Map<String, Integer> sources(String target) {
        Map<String, Integer> sources = new HashMap<>();
        for (Vertex vertex : vertices) {
            Integer weight = vertex.getEdgeWeight(target);
            if (weight != null) {
                sources.put(vertex.getLabel(), weight);
            }
        }
        return sources;
    }
    
    @Override
    public Map<String, Integer> targets(String source) {
        Vertex vertex = findVertex(source);
        if (vertex != null) {
            return vertex.getEdges();
        }
        return new HashMap<>();
    }
    
    @Override
    public String toString() {
        return "ConcreteVerticesGraph(vertices=" + vertices + ")";
    }
}

/**
 * Mutable class representing a vertex in a graph, with outgoing edges.
 */
class Vertex {
    private final String label;
    private final Map<String, Integer> edges = new HashMap<>();

    // Abstraction Function:
    //   Represents a vertex with a unique label and outgoing edges to other vertices with specified weights.

    // Representation Invariant:
    //   label is not null
    //   edge weights are >= 0

    // Safety from Rep Exposure:
    //   label and edges are private. We return copies of edges when necessary.

    public Vertex(String label) {
        this.label = label;
        checkRep();
    }

    private void checkRep() {
        assert label != null : "Vertex label must not be null";
        for (Integer weight : edges.values()) {
            assert weight >= 0 : "Edge weights must be non-negative";
        }
    }

    public String getLabel() {
        return label;
    }

    public int setEdge(String target, int weight) {
        if (weight == 0) {
            Integer previousWeight = edges.remove(target);
            return previousWeight == null ? 0 : previousWeight;
        } else {
            Integer previousWeight = edges.put(target, weight);
            return previousWeight == null ? 0 : previousWeight;
        }
    }


    public Integer getEdgeWeight(String target) {
        return edges.get(target);
    }

    public Map<String, Integer> getEdges() {
        return new HashMap<>(edges);
    }

    public void removeEdge(String target) {
        edges.remove(target);
    }

    @Override
    public String toString() {
        return "Vertex(" + label + "): " + edges;
    }
}
