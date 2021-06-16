package se.kth.ArchiMate2Graph;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;

public class Transformer {
    public static Graph transform(org.opengroup.xsd.archimate._3.ModelType archi) {
        Graph transformedGraph = new DefaultGraph(archi.getMetadata());

        return transformedGraph;
    }
}
