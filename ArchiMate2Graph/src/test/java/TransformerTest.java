import org.graphstream.stream.binary.ByteProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opengroup.xsd.archimate._3.ModelType;
import se.kth.ArchiMate2Graph.Transformer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransformerTest {
    private static File archiSurance = new File("src/test/resources/Archisurance & portfolios.xml");
    private static ModelType archiSuranceObject = null;

    @BeforeEach
    public void init() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ModelType.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            archiSuranceObject =  jaxbUnmarshaller.unmarshal(new StreamSource(archiSurance),ModelType.class).getValue();

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGraphName() {
        String processName = Transformer.transform(archiSuranceObject).getId();
        assertEquals("Archisurance",processName);
    }

    @Test
    public void testNumberElements() {
        int actualNumber = Transformer.transform(archiSuranceObject).getNodeCount();
        assertEquals(163, actualNumber);
    }

    @Test
    public void testNodeName() {
        String actualName = Transformer.transform(archiSuranceObject)
                .getNode("id-1882").getAttribute("name",String.class);
        assertEquals("BIBIT-Server",actualName);
    }

    @Test
    public void testNodeType() {
        String actualType = Transformer.transform(archiSuranceObject)
                .getNode("id-1882").getAttribute("type",String.class);
        assertEquals("Device",actualType);
    }

    @Test
    public void testNumberEdges() {
        int actualNumber = Transformer.transform(archiSuranceObject).getEdgeCount();
        assertEquals(236, actualNumber);
    }

    @Test
    public void testEdgeName() {
        String actualName = Transformer.transform(archiSuranceObject)
                .getEdge("id-1428").getAttribute("name",String.class);
        assertEquals("",actualName);
    }

    @Test
    public void testEdgeType() {
        String actualType = Transformer.transform(archiSuranceObject)
                .getEdge("id-1428").getAttribute("type",String.class);
        assertEquals("Serving",actualType);
    }

    @Test
    public void testEdgeEnds() {
        String sourceId = Transformer.transform(archiSuranceObject)
                .getEdge("id-1428").getSourceNode().getId();
        String targetId = Transformer.transform(archiSuranceObject)
                .getEdge("id-1428").getTargetNode().getId();
        assertAll ( () -> assertEquals("id-1414",sourceId),
                () -> assertEquals("id-1399",targetId)
        );
    }
}
