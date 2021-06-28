package se.kth.app;

import com.sap.kth.scadbuilder.ScadFactory;
import com.sap.kth.scadbuilder.output.ScadWriter;
import org.graphstream.graph.Graph;
import org.mal_lang.compiler.lib.CompilerException;
import org.opengroup.xsd.archimate._3.ModelType;
import se.kth.ArchiMate2Graph.Transformer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;

public class Starter {
    public static void main(String[] args) {
        File resources = new File("C:\\Users\\Simon\\IdeaProjects\\EA-Resilience\\App\\src\\main\\resources");
        File archiSurance = new File(resources.getAbsolutePath()+File.separator+"Archisurance & portfolios.xml");
        File mapping = new File(resources.getAbsolutePath()+File.separator+"mapping.xml");
        File mal = new File(resources.getAbsolutePath()+File.separator+"coreLang.mal");
        File scadFile = new File(resources.getAbsolutePath()+File.separator+"test.scad");

        try {
            //Load object from XML
            ModelType archiSuranceObject = null;

            JAXBContext jaxbContext = JAXBContext.newInstance(ModelType.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            archiSuranceObject =  jaxbUnmarshaller.unmarshal(new StreamSource(archiSurance),ModelType.class).getValue();


            //Transform to graph
            Graph archiGraph = Transformer.postProcess(Transformer.transform(archiSuranceObject));


            //Create sCAD file
            ScadFactory factory = new ScadFactory(archiGraph,mapping,mal);

            ScadWriter.createScad(scadFile,"test",
                    factory.getEomFile().toString(),
                    factory.getCmxFile().toString(),
                    factory.getMetaFile().toString()
            );


        } catch (JAXBException | IOException | CompilerException e) {
            e.printStackTrace();
        }
    }
}
