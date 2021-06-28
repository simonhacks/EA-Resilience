package se.kth.ArchiMate2Graph;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.graph.implementations.Graphs;
import org.opengroup.xsd.archimate._3.ElementType;
import org.opengroup.xsd.archimate._3.RelationshipType;

public class Transformer {
    private static int nodeId = 300000;
    private static int edgeId = 500000;

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

    public static Graph postProcess(Graph graphRepresentation) {
        Graph enrichedGraph = Graphs.clone(graphRepresentation);

        //Add additional nodes
        for(Object o: enrichedGraph.edges().toArray()) {
            Edge edge = (Edge) o;


            switch (edge.getAttribute("type",String.class)) {
                case "Serving": //Applications talking directly to each other "Serving"
                    if ("ApplicationComponent".equals(edge.getSourceNode().getAttribute("type",String.class)) &&
                            "ApplicationComponent".equals(edge.getTargetNode().getAttribute("type",String.class)))
                    {
                        addNodeInBetween(enrichedGraph, edge, "Connection");
                    }
                    break;
                case "Association": //Systems talking over Network
                    switch (edge.getSourceNode().getAttribute("type",String.class)) {
                        case "Device":
                        case "Node":
                            if("CommunicationNetwork".equals(edge.getTargetNode().getAttribute("type",String.class))) {
                                addNodeInBetween(enrichedGraph,edge,"ApplicationComponent");
                            }
                            break;
                    }
                    switch (edge.getTargetNode().getAttribute("type",String.class)) {
                        case "Device":
                        case "Node":
                            if("CommunicationNetwork".equals(edge.getSourceNode().getAttribute("type",String.class))) {
                                addNodeInBetween(enrichedGraph,edge,"ApplicationComponent");
                            }
                            break;
                    }
                    break;
            }
        }

        return enrichedGraph;
    }

    private static void addNodeInBetween(Graph enrichedGraph, Edge edge, String nodeType) {
        //Create respective node
        String nodeIdString = String.valueOf(nodeId);
        enrichedGraph.addNode(nodeIdString);
        enrichedGraph.getNode(nodeIdString).setAttribute("name", nodeType + " " + nodeId);
        enrichedGraph.getNode(nodeIdString).setAttribute("type",nodeType);
        enrichedGraph.getNode(nodeIdString).setAttribute("id", nodeId);

        //Create edges
        String edgeIdString = String.valueOf(edgeId);
        enrichedGraph.addEdge(edgeIdString, edge.getSourceNode(), enrichedGraph.getNode(nodeIdString));
        enrichedGraph.getEdge(edgeIdString).setAttribute("name",null);
        enrichedGraph.getEdge(edgeIdString).setAttribute("type",null);
        enrichedGraph.getEdge(edgeIdString).setAttribute("id",edgeId);
        edgeId++;

        edgeIdString = String.valueOf(edgeId);
        enrichedGraph.addEdge(edgeIdString, edge.getTargetNode(), enrichedGraph.getNode(nodeIdString));
        enrichedGraph.getEdge(edgeIdString).setAttribute("name",null);
        enrichedGraph.getEdge(edgeIdString).setAttribute("type",null);
        enrichedGraph.getEdge(edgeIdString).setAttribute("id",edgeId);
        edgeId++;

        nodeId++;

        //remove original edge
        enrichedGraph.removeEdge(edge);
    }
}
