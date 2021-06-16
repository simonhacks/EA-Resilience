import org.graphstream.stream.binary.ByteProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opengroup.xsd.archimate._3.ModelType;
import se.kth.ArchiMate2Graph.Transformer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

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
}
