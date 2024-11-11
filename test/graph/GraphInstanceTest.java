package graph;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Collections;
import java.util.Set;
import java.util.Map;

public abstract class GraphInstanceTest {
    
    // Testing strategy
    // - testInitialVerticesEmpty(): test that a new graph has no vertices
    // - testAddVertex(): add vertices and check if they exist in the graph
    // - testAddDuplicateVertex(): add a vertex that already exists and check no duplication
    // - testSetEdge(): add edges and check if they exist with correct weights
    // - testRemoveVertex(): remove a vertex and verify it no longer exists
    // - testSourcesAndTargets(): check sources and targets for directed edges

    public abstract Graph<String> emptyInstance();

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testInitialVerticesEmpty() {
        assertEquals("expected new graph to have no vertices", Collections.emptySet(), emptyInstance().vertices());
    }

    @Test
    public void testAddVertex() {
        Graph<String> graph = emptyInstance();
        assertTrue("expected vertex addition to succeed", graph.add("A"));
        assertEquals("expected vertex set to contain added vertex", Set.of("A"), graph.vertices());
    }

    @Test
    public void testAddDuplicateVertex() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        assertFalse("expected duplicate vertex addition to fail", graph.add("A"));
        assertEquals("expected vertex set to contain only one instance of vertex", Set.of("A"), graph.vertices());
    }

    @Test
    public void testSetEdge() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        
        int previousWeight = graph.set("A", "B", 5);
        assertEquals("expected previous weight to be zero for new edge", 0, previousWeight);
        
        previousWeight = graph.set("A", "B", 10);
        assertEquals("expected previous weight to be 5", 5, previousWeight);
        
        previousWeight = graph.set("A", "B", 0);
        assertEquals("expected previous weight to be 10 when removing edge", 10, previousWeight);
        assertTrue("expected edge to be removed", graph.targets("A").isEmpty());
    }

    @Test
    public void testRemoveVertex() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);

        assertTrue("expected vertex removal to succeed", graph.remove("A"));
        assertFalse("expected vertex to be removed from graph", graph.vertices().contains("A"));
        assertTrue("expected edges involving removed vertex to be removed", graph.sources("B").isEmpty());
    }

    @Test
    public void testSourcesAndTargets() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.add("C");
        
        graph.set("A", "B", 5);
        graph.set("C", "B", 3);
        
        Map<String, Integer> sources = graph.sources("B");
        assertEquals("expected two sources for vertex B", Map.of("A", 5, "C", 3), sources);

        Map<String, Integer> targets = graph.targets("A");
        assertEquals("expected one target for vertex A", Map.of("B", 5), targets);
    }
}
