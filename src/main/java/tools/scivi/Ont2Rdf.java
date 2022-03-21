package tools.scivi;

import onto.Node;
import onto.Onto;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDFS;

public class Ont2Rdf {
    static Model convert(Onto ont) {
        Model model = ModelFactory.createDefaultModel();
        for (var node : ont.getNodes()) {
            Resource resource = model.createResource(node.getFQN());
            resource.addProperty(RDFS.label, node.getName());
            var attributes = node.getAttributes();
            attributes.forEach((k, v) -> {
                Property prop = model.createProperty(k);
                prop.addProperty(RDFS.label, k);
                model.add(resource, prop, model.createLiteral(v.toString()));
            });
        }

        for (var link : ont.getLinks()) {
            Node sourceNode = ont.getNodeByID(link.getSourceID());
            Resource sourceResource = model.getResource(sourceNode.getFQN());
            Node targetNode = ont.getNodeByID(link.getTargetID());
            Resource targetResource = model.getResource(targetNode.getFQN());
            Property prop = model.createProperty(link.getFQN());
            prop.addProperty(RDFS.label, link.getName());
            model.add(sourceResource, prop, targetResource);
        }
        return model;
    }
}
