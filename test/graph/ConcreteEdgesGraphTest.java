package graph;

import static org.junit.Assert.*;
import org.junit.Test;

public class ConcreteEdgesGraphTest extends GraphInstanceTest {
    
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph();
    }

    // Additional tests specific to ConcreteEdgesGraph

    @Test
    public void testConcreteEdgesGraphToString() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);
        
        String expected = "ConcreteEdgesGraph(vertices=[A, B], edges=[Edge(A -> B, weight=5)])";
        assertEquals("expected specific toString output for ConcreteEdgesGraph", expected, graph.toString());
    }

    @Test
    public void testEdgeOperations() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
        Edge edge = new Edge("A", "B", 5);
        assertEquals("expected correct edge source", "A", edge.getSource());
        assertEquals("expected correct edge target", "B", edge.getTarget());
        assertEquals("expected correct edge weight", 5, edge.getWeight());
    }
}
