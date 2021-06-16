package se.kth.ArchiMate2Graph;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;
import org.opengroup.xsd.archimate._3.ElementType;

public class Transformer {
    public static Graph transform(org.opengroup.xsd.archimate._3.ModelType archi) {
        Graph transformedGraph = new DefaultGraph(archi.getNameGroup().get(0).getValue());

        for(ElementType element: archi.getElements().getElement()) {
            String id = element.getIdentifier();
            String name = element.getNameGroup().get(0).getValue();
            String type = element.getClass().getSimpleName();
            transformedGraph.addNode(id);
            transformedGraph.getNode(id).setAttribute("name",name);
            transformedGraph.getNode(id).setAttribute("type",type);
        }

        return transformedGraph;
    }
}
