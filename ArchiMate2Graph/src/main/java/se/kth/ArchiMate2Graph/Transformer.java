package se.kth.ArchiMate2Graph;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;
import org.opengroup.xsd.archimate._3.ElementType;
import org.opengroup.xsd.archimate._3.RelationshipType;

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
            transformedGraph.getNode(id).setAttribute("id",Integer.parseInt(id.replaceAll("[^\\d.]", "")));
        }

        for(RelationshipType relationship: archi.getRelationships().getRelationship()) {
            String id = relationship.getIdentifier();
            String name = relationship.getNameGroup().get(0).getValue();
            String type = relationship.getClass().getSimpleName();
            String sourceId = ((ElementType)relationship.getSource()).getIdentifier();
            String targetId = ((ElementType)relationship.getTarget()).getIdentifier();

            transformedGraph.addEdge(id,sourceId,targetId,true);
            transformedGraph.getEdge(id).setAttribute("name",name);
            transformedGraph.getEdge(id).setAttribute("type",type);
            transformedGraph.getEdge(id).setAttribute("id",Integer.parseInt(id.replaceAll("[^\\d.]", "")));
        }

        return transformedGraph;
    }
}
