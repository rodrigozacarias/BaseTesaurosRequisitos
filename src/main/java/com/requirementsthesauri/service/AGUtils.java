package com.requirementsthesauri.service;

import com.franz.agraph.jena.AGGraph;
import com.franz.agraph.jena.AGModel;

import java.text.Normalizer;

public class AGUtils {

    Authentication authentication = new Authentication();

    String sparqlEndpoint = "http://127.0.0.1:10035/catalogs/system/repositories/requirements/sparql";
    String uriDom = "localhost:8080/requirementsThesauri/domains/";
    String uriReq = "localhost:8080/requirementsThesauri/requirements/";
    String uriRqt = "localhost:8080/requirementsThesauri/requirementTypes/";
    String uriSys = "localhost:8080/requirementsThesauri/systemTypes/";
    String dbr = "http://dbpedia.org/resource/";
    String schema = "http://schema.org/";

    public AGModel getAGModel() throws Exception {
        authentication.getAuthentication();

        AGGraph graph = authentication.getConnectedDataRepository();
        AGModel model = new AGModel(graph);
        return model;
    }

    public String convertFromAcceptToFormat (String accept){
        String format = "";
        switch (accept) {
            case "application/ld+json":
                format = "JSON-LD";
                break;
            case "application/n-triples":
                format = "N-Triples";
                break;
            case "application/rdf+xml":
                format = "RDF/XML";
                break;
            case "application/turtle":
                format = "TURTLE";
                break;
            case "application/rdf+json":
                format = "RDF/JSON";
                break;
        }
        return format;
    }

    public String removeAccents(String str) {
        str = Normalizer.normalize(str, Normalizer.Form.NFD);
        str = str.replaceAll("[^\\p{ASCII}]", "");
        return str;
    }

}
