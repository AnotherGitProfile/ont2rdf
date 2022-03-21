package tools.scivi;

import onto.Onto;
import org.apache.jena.fuseki.main.FusekiServer;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.DatasetFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.json.JSONException;

import java.io.*;

public class Main {

    private static void startEmbeddedSparqlServer(Model model) {
        Dataset ds = DatasetFactory.create(model);
        FusekiServer server = FusekiServer.create()
                .add("/ds", ds)
                .enableCors(true)
                .loopback(true)
                .verbose(true)
                .port(3000)
                .build();
        server.start();
        System.out.printf("Server started at %s", server.datasetURL("ds"));
    }

    private static void saveToFile(Model model) throws FileNotFoundException {
        RDFDataMgr.write(new FileOutputStream("./result.ttl"), model, Lang.TURTLE);
    }

    public static void main(String[] args) throws IOException, JSONException {
        if (args.length < 1) {
            System.err.println("Usage: <filename>");
            return;
        }
        File ontFile = new File(args[0]);
        Onto ont = new Onto(new FileInputStream(ontFile));

        Model model = Ont2Rdf.convert(ont);

        startEmbeddedSparqlServer(model);
    }
}
