package graph;

import static org.junit.Assert.*;
import org.junit.Test;

public class ConcreteVerticesGraphTest extends GraphInstanceTest {
    
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph();
    }

    // Additional tests specific to ConcreteVerticesGraph
    
    @Test
    public void testConcreteVerticesGraphToString() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);
        
        String expected = "ConcreteVerticesGraph(vertices=[Vertex(A): {B=5}, Vertex(B): {}])";
        assertEquals("expected specific toString output for ConcreteVerticesGraph", expected, graph.toString());
    }

    @Test
    public void testVertexOperations() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        Vertex vertex = new Vertex("A");
        vertex.setEdge("B", 5);
        assertEquals("expected correct edge weight", Integer.valueOf(5), vertex.getEdgeWeight("B"));
        vertex.setEdge("B", 0); // should remove the edge
        assertNull("expected edge to be removed", vertex.getEdgeWeight("B"));
    }
}
