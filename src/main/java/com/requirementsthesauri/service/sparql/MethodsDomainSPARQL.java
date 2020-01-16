package com.requirementsthesauri.service.sparql;

import java.util.List;

public class MethodsDomainSPARQL {



    public String insertDomainSparql(String domainID, String label, String prefLabel, String altLabel, String description,
                                     String linkDbpedia, String broaderDomainID, List<String> narrowerDomainID, List<String> narrowerRequirementID) {
        String queryInsert = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX dbr: <http://dbpedia.org/resource/>\n" +
                "PREFIX schema: <http://schema.org/>\n" +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX dom: <localhost:8080/requirementsThesauri/domains/>\n" +
                "PREFIX req: <localhost:8080/requirementsThesauri/requirements/>\n" +
                "INSERT DATA\n" +
                "{\n" +
                "  dom:"+ domainID +" 	rdf:type		skos:Concept ;\n" +
                "                schema:url	dom:"+ domainID +" ;\n" +
                "                rdfs:label	\""+label+"\" ;\n" +
                "                skos:preLabel	\""+prefLabel+"\" ;\n" +
                "                skos:altLabel	\""+altLabel+"\" ;\n" +
                "                skos:note	\""+description+"\" ;\n" +
                "                owl:sameAs	dbr:"+linkDbpedia+" ;\n" +
                "                skos:broader	dom:"+broaderDomainID+" ;\n" +
                "                rdfs:subClassOf	dom:"+broaderDomainID+" ;\n";
        if(!narrowerDomainID.isEmpty()){
            for (String nd: narrowerDomainID){
                queryInsert = queryInsert + "                skos:narrower	dom:"+nd+" ;\n";
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

    public String getAllDomainsSparqlSelect() {
        String querySelect = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "SELECT ?domain \n" +
                "WHERE\n" +
                "{\n" +
                "?domain rdf:type skos:Concept .\n" +
                "}\n" +
                "";

        return querySelect;
    }

    public String getDomainSparqlSelectNarrower(String domainURI) {
        String querySelect = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX dom: <localhost:8080/requirementsThesauri/domains/>\n" +
                "SELECT DISTINCT ?narrowerDomainID \n" +
                "WHERE\n" +
                "{\n" +
                 "<"+domainURI+">	rdf:type		skos:Concept ;\n" +
                "                 skos:narrower  ?narrowerDomainID .\n" +
                "}\n" +
                "";

        return querySelect;
    }

    public String getDomainSparqlSelectBroader(String domainURI) {
        String querySelect = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX dom: <localhost:8080/requirementsThesauri/domains/>\n" +
                "SELECT DISTINCT ?broaderDomainID \n" +
                "WHERE\n" +
                "{\n" +
                "<"+domainURI+">	rdf:type		skos:Concept ;\n" +
                "                 skos:broader  ?broaderDomainID .\n" +
                "}\n" +
                "";

        return querySelect;
    }

    public String getDomainSparqlSelectSubClass(String domainURI) {
        String querySelect = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX dom: <localhost:8080/requirementsThesauri/domains/>\n" +
                "SELECT DISTINCT ?subClassOf \n" +
                "WHERE\n" +
                "{\n" +
                "<"+domainURI+">	rdf:type		skos:Concept ;\n" +
                "                 rdfs:subClassOf  ?subClassOf .\n" +
                "}\n" +
                "";

        return querySelect;
    }

    public String getDomainSparqlSelect(String domainURI) {
        String query = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX schema: <http://schema.org/>\n" +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX dom: <localhost:8080/requirementsThesauri/domains/>\n" +
                "SELECT ?url ?label ?prefLabel ?altLabel ?description" +
                " ?linkDbpedia ?broaderDomainID ?narrowerDomainID ?narrowerRequirementID \n" +
                "WHERE{\n" +
                "\n" +
                "<"+domainURI+">	rdf:type		skos:Concept ;\n" +
                "                schema:url	?url ;\n" +
                "                rdfs:label	?label ;\n" +
                "                skos:preLabel	?prefLabel ;\n" +
                "                skos:altLabel	?altLabel ;\n" +
                "                skos:note	?description ;\n" +
                "                owl:sameAs	?linkDbpedia ;\n" +
                "                skos:broader	?broaderDomainID ;\n " +
                "                skos:narrower  ?narrowerDomainID ;\n" +
                "                skos:narrower	?narrowerRequirementID .\n" +
                "  \n" +
                "}\n" +
                "";
        return query;
    }



    public String getDomainSparqlDescribe(String domainID) {
        String query = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX dom: <localhost:8080/requirementsThesauri/domains/>\n" +
                "DESCRIBE dom:" + domainID + "\n" +
                "WHERE\n" +
                "{\n" +
                "dom:" + domainID + " rdf:type skos:Concept  .\n" +
                "}\n" +
                "";

        return query;
    }

    public String updateDomainsSparql(String oldDomainID, String domainID, String label, String prefLabel, String altLabel, String description,
                                      String linkDbpedia, String broaderDomainID, List<String> narrowerDomainID, List<String> narrowerRequirementID) {
        String queryUpdate = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX dbr: <http://dbpedia.org/resource/>\n" +
                "PREFIX schema: <http://schema.org/>\n" +
                "PREFIX dom: <localhost:8080/requirementsThesauri/domains/>\n" +
                "PREFIX req: <localhost:8080/requirementsThesauri/requirements/>\n" +
                "\n" +
                "DELETE \n" +
                "	{ dom:"+oldDomainID+" ?p ?s }\n" +
                "WHERE\n" +
                "{ \n" +
                "  dom:"+oldDomainID+" ?p ?s;\n" +
                " 		rdf:type skos:Concept .\n" +
                "};\n" +
                "\n" +
                "INSERT DATA\n" +
                "{\n" +
                "  dom:"+ domainID +" 	rdf:type		skos:Concept ;\n" +
                "                schema:url	dom:"+ domainID +" ;\n" +
                "                rdfs:label	\""+label+"\" ;\n" +
                "                skos:preLabel	\""+prefLabel+"\" ;\n" +
                "                skos:altLabel	\""+altLabel+"\" ;\n" +
                "                skos:note	\""+description+"\" ;\n" +
                "                rdfs:seeAlso	dbr:"+linkDbpedia+" ;\n" +
                "                skos:broader	dom:"+broaderDomainID+" ;\n" ;
        if(!narrowerDomainID.isEmpty()){
            for (String nd: narrowerDomainID){
                queryUpdate = queryUpdate + "                skos:narrower	dom:"+nd+" ;\n";
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

    public String deleteDomainSparql1(String domainID){

        String queryDelete = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX dom: <localhost:8080/requirementsThesauri/domains/>\n" +
                "\n" +
                "DELETE \n" +
                "	{ dom:"+domainID+" ?p ?s }\n" +
                "WHERE\n" +
                "{ \n" +
                "  dom:"+domainID+" ?p ?s;\n" +
                " 		rdf:type skos:Concept .\n" +
                "};\n";

        return queryDelete;
    }

    public String deleteDomainSparql(String domainURI){

        String queryDelete = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX dom: <localhost:8080/requirementsThesauri/domains/>\n" +
                "\n" +
                "DELETE \n" +
                "	{   <"+domainURI+"> ?p ?s }\n" +
                "WHERE\n" +
                "{ \n" +
                "  <"+domainURI+"> ?p ?s;\n" +
                " 		rdf:type skos:Concept .\n" +
                "};\n";

        return queryDelete;
    }

}
