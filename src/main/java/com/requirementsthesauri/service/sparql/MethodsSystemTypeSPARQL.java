package com.requirementsthesauri.service.sparql;

import java.util.List;

public class MethodsSystemTypeSPARQL {

   public String insertSystemTypeSparql(String systemTypeID, String label, String prefLabel, String altLabel, String description,
                                         String linkDbpedia, String broaderSystemTypeID, List<String> narrowerSystemTypeID, List<String> narrowerRequirementID) {
        String queryInsert = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX dbr: <http://dbpedia.org/resource/>\n" +
                "PREFIX schema: <http://schema.org/>\n" +
                "PREFIX sys: <localhost:8080/requirementsThesauri/systemTypes/>\n" +
                "PREFIX req: <localhost:8080/requirementsThesauri/requirements/>\n" +
                "INSERT DATA\n" +
                "{\n" +
                "  sys:"+ systemTypeID +" 	rdf:type		skos:Concept ;\n" +
                "                schema:url	sys:"+ systemTypeID +" ;\n" +
                "                rdfs:label	\""+label+"\" ;\n" +
                "                skos:preLabel	\""+prefLabel+"\" ;\n" +
                "                skos:altLabel	\""+altLabel+"\" ;\n" +
                "                skos:note	\""+description+"\" ;\n" +
                "                rdfs:seeAlso	dbr:"+linkDbpedia+" ;\n" +
                "                skos:broader	sys:"+broaderSystemTypeID+" ;\n" ;
        if(!narrowerSystemTypeID.isEmpty()){
            for (String ns: narrowerSystemTypeID){
                queryInsert = queryInsert + "                skos:narrower	sys:"+ns+" ;\n";
            }
        }
        if(!narrowerRequirementID.isEmpty()){
            for (String nr: narrowerRequirementID){

                queryInsert = queryInsert +  "                skos:narrower	req:"+nr+" ;\n";
            }
        }

        queryInsert = queryInsert + ".\n }";
        return queryInsert;

    }

    public String getAllSystemTypesSparqlSelect() {
        String querySelect = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "SELECT ?system \n" +
                "WHERE\n" +
                "{\n" +
                "?system rdf:type skos:Concept .\n" +
                "}\n" +
                "";

        return querySelect;
    }

    public String getSystemTypeSparqlSelectNarrower(String systemTypeID) {
        String querySelect = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX sys: <localhost:8080/requirementsThesauri/systemTypes/>\n" +
                "SELECT ?narrowerSystemTypeID \n" +
                "WHERE\n" +
                "{\n" +
                "  sys:" + systemTypeID + "	rdf:type		skos:Concept ;\n" +
                "                 skos:narrower  ?narrowerSystemTypeID .\n" +
                "}\n" +
                "";

        return querySelect;
    }

    public String getSystemTypeSparqlSelect(String systemTypeID) {
        String query = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX schema: <http://schema.org/>\n" +
                "PREFIX sys: <localhost:8080/requirementsThesauri/systemTypes/>\n" +
                "SELECT ?url ?label ?prefLabel ?altLabel ?description" +
                " ?linkDbpedia ?broaderSystemTypeID ?narrowerSystemTypeID ?narrowerRequirementID \n" +
                "WHERE{\n" +
                "\n" +
                "  sys:" + systemTypeID + "	rdf:type		skos:Concept ;\n" +
                "                schema:url	?url ;\n" +
                "                rdfs:label	?label ;\n" +
                "                skos:preLabel	?prefLabel ;\n" +
                "                skos:altLabel	?altLabel ;\n" +
                "                skos:note	?description ;\n" +
                "                rdfs:seeAlso	?linkDbpedia ;\n" +
                "                skos:broader	?broaderSystemTypeID ;\n " +
                "                skos:narrower  ?narrowerSystemTypeID ;\n" +
                "                skos:narrower	?narrowerRequirementID .\n" +
                "  \n" +
                "}\n" +
                "";
        return query;
    }

    public String getSystemTypeSparqlDescribe(String systemTypeID) {
        String query = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX sys: <localhost:8080/requirementsThesauri/systemTypes/>\n" +
                "DESCRIBE sys:" + systemTypeID + "\n" +
                "WHERE\n" +
                "{\n" +
                "sys:" + systemTypeID + " rdf:type skos:Concept  .\n" +
                "}\n" +
                "";

        return query;
    }

    public String updateSystemTypeSparql(String oldSystemTypeID, String systemTypeID, String label, String prefLabel, String altLabel, String description,
                                         String linkDbpedia, String broaderSystemTypeID, List<String> narrowerSystemTypeID, List<String> narrowerRequirementID) {
        String queryUpdate = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX dbr: <http://dbpedia.org/resource/>\n" +
                "PREFIX schema: <http://schema.org/>\n" +
                "PREFIX sys: <localhost:8080/requirementsThesauri/systemTypes/>\n" +
                "PREFIX req: <localhost:8080/requirementsThesauri/requirements/>\n" +
                "\n" +
                "DELETE \n" +
                "	{ sys:"+ oldSystemTypeID +" ?p ?s }\n" +
                "WHERE\n" +
                "{ \n" +
                "  sys:"+ oldSystemTypeID +" ?p ?s;\n" +
                " 		rdf:type skos:Concept .\n" +
                "};\n" +
                "\n" +
                "INSERT DATA\n" +
                "{\n" +
                "  sys:"+ oldSystemTypeID +" 	rdf:type		skos:Concept ;\n" +
                "                schema:url	sys:"+ systemTypeID +" ;\n" +
                "                rdfs:label	\""+label+"\" ;\n" +
                "                skos:preLabel	\""+prefLabel+"\" ;\n" +
                "                skos:altLabel	\""+altLabel+"\" ;\n" +
                "                skos:note	\""+description+"\" ;\n" +
                "                rdfs:seeAlso	dbr:"+linkDbpedia+" ;\n" +
                "                skos:broader	sys:"+broaderSystemTypeID+" ;\n" ;
        if(!narrowerSystemTypeID.isEmpty()){
            for (String ns: narrowerSystemTypeID){
                queryUpdate = queryUpdate + "                skos:narrower	sys:"+ns+" ;\n";
            }
        }
        if(!narrowerRequirementID.isEmpty()){
            for (String nr: narrowerRequirementID){

                queryUpdate = queryUpdate +  "                skos:narrower	req:"+nr+" ;\n";
            }
        }

        queryUpdate = queryUpdate + ".\n }";
        return queryUpdate;
    }

    public String deleteSystemTypeSparql(String systemTypeID){

        String queryDelete = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX sys: <localhost:8080/requirementsThesauri/systemTypes/>\n" +
                "\n" +
                "DELETE \n" +
                "	{ sys:"+systemTypeID+" ?p ?s }\n" +
                "WHERE\n" +
                "{ \n" +
                "  sys:"+systemTypeID+" ?p ?s;\n" +
                " 		rdf:type skos:Concept .\n" +
                "};\n";

        return queryDelete;
    }
}
