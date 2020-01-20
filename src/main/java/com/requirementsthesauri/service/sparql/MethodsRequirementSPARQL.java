package com.requirementsthesauri.service.sparql;

import java.util.List;

public class MethodsRequirementSPARQL {

    public String insertRequirementSparql(String requirementID, String label, String language, String prefLabel, String altLabel,
                                           String problem, String context, String template, String example, String broaderRequirementTypeID,
                                           String broaderRequirementID, List<String> broaderDomainID, List<String> broaderSystemTypeID,
                                           List<String> narrowerRequirementID) {
        String queryInsert = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX schema: <http://schema.org/>\n" +
                "PREFIX dcmitype: <http://purl.org/dc/dcmitype/>\n" +
                "PREFIX dom: <localhost:8080/requirementsThesauri/domains/>\n" +
                "PREFIX req: <localhost:8080/requirementsThesauri/requirements/>\n" +
                "PREFIX sys: <localhost:8080/requirementsThesauri/systemTypes/>\n" +
                "PREFIX rqt: <localhost:8080/requirementsThesauri/requirementTypes/>\n" +
                "INSERT DATA\n" +
                "{\n" +
                "  req:"+ requirementID +" 	rdf:type		skos:Concept ;\n" +
                "                schema:url	req:"+ requirementID +" ;\n" +
                "                rdfs:label	\""+label+"\" ;\n" +
                "                dcmitype:language	\""+language+"\" ;\n" +
                "                skos:preLabel	\""+prefLabel+"\" ;\n" +
                "                skos:altLabel	\""+altLabel+"\" ;\n" +
                "                skos:note\""+problem+"\" ;\n" +
                "                skos:scopeNote	\""+context+"\" ;\n" +
                "                skos:definition	\""+template+"\" ;\n" +
                "                skos:example	\""+example+"\" ;\n";
        if(broaderRequirementTypeID==null){
            queryInsert = queryInsert +  "                skos:broader	rqt:"+broaderRequirementTypeID+" ;\n" +
                    "                rdfs:subClassOf	rqt:"+broaderRequirementTypeID+" ;\n";
        }else{
            queryInsert = queryInsert +   "                skos:broader	<" + broaderRequirementTypeID + "> ;\n" +
                    "                rdfs:subClassOf	<" + broaderRequirementTypeID + "> ;\n";
        }
        if(broaderRequirementID==null){
            queryInsert = queryInsert +   "                skos:broader	req:"+broaderRequirementID+" ;\n" +
                    "                rdfs:subClassOf	req:"+broaderRequirementID+" ;\n";
        }else{
            queryInsert = queryInsert +   "                skos:broader	<" + broaderRequirementID + "> ;\n" +
                    "                rdfs:subClassOf	<" + broaderRequirementID + "> ;\n";
        }
        if(!broaderDomainID.isEmpty()){
            for (String bd: broaderDomainID){
                if(bd==null) {
                    queryInsert = queryInsert + "                skos:broader	dom:" + bd + " ;\n";
                }else{
                    queryInsert = queryInsert + "                skos:broader	<" + bd + "> ;\n";
                }
            }
        }
        if(!broaderSystemTypeID.isEmpty()){
            for (String bs: broaderSystemTypeID){
                if(bs==null) {
                    queryInsert = queryInsert + "                skos:broader	sys:" + bs + " ;\n";
                }else{
                    queryInsert = queryInsert + "                skos:broader	<" + bs + "> ;\n";
                }
            }
        }
        if(!narrowerRequirementID.isEmpty()){
            for (String nr: narrowerRequirementID){
                if(nr==null) {
                    queryInsert = queryInsert + "                skos:narrower	req:" + nr + " ;\n";
                }else{
                    queryInsert = queryInsert + "                skos:narrower	<" + nr + "> ;\n";
                }
            }
        }

        queryInsert = queryInsert + ".\n }";
        return queryInsert;

    }

    public String insertRequirementSparql1(String requirementID, String label, String language, String prefLabel, String altLabel,
                                          String problem, String context, String template, String example, String broaderRequirementTypeID,
                                          String broaderRequirementID, List<String> broaderDomainID, List<String> broaderSystemTypeID,
                                          List<String> narrowerRequirementID) {
        String queryInsert = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX schema: <http://schema.org/>\n" +
                "PREFIX dcmitype: <http://purl.org/dc/dcmitype/>\n" +
                "PREFIX dom: <localhost:8080/requirementsThesauri/domains/>\n" +
                "PREFIX req: <localhost:8080/requirementsThesauri/requirements/>\n" +
                "PREFIX sys: <localhost:8080/requirementsThesauri/systemTypes/>\n" +
                "PREFIX rqt: <localhost:8080/requirementsThesauri/requirementTypes/>\n" +
                "INSERT DATA\n" +
                "{\n" +
                "  req:"+ requirementID +" 	rdf:type		skos:Concept ;\n" +
                "                schema:url	req:"+ requirementID +" ;\n" +
                "                rdfs:label	\""+label+"\" ;\n" +
                "                dcmitype:language	\""+language+"\" ;\n" +
                "                skos:preLabel	\""+prefLabel+"\" ;\n" +
                "                skos:altLabel	\""+altLabel+"\" ;\n" +
                "                skos:note\""+problem+"\" ;\n" +
                "                skos:scopeNote	\""+context+"\" ;\n" +
                "                skos:definition	\""+template+"\" ;\n" +
                "                skos:example	\""+example+"\" ;\n" +
                "                skos:broader	rqt:"+broaderRequirementTypeID+" ;\n" +
                "                skos:broader	req:"+broaderRequirementID+" ;\n" +
                "                rdfs:subClassOf	rqt:"+broaderRequirementTypeID+" ;\n" +
                "                rdfs:subClassOf	req:"+broaderRequirementID+" ;\n";
        if(!broaderDomainID.isEmpty()){
            for (String bd: broaderDomainID){
                queryInsert = queryInsert + "                skos:broader	dom:"+bd+" ;\n";
            }
        }
        if(!broaderSystemTypeID.isEmpty()){
            for (String bs: broaderSystemTypeID){
                queryInsert = queryInsert + "                skos:broader	sys:"+bs+" ;\n";
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

    public String getAllRequirementsSparqlSelect() {
        String querySelect = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "SELECT ?requirement \n" +
                "WHERE\n" +
                "{\n" +
                "?requirement rdf:type skos:Concept .\n" +
                "}\n" +
                "";

        return querySelect;
    }

    public String getRequirementSparqlSelect(String requirementURI) {
        String query = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX schema: <http://schema.org/>\n" +
                "PREFIX dcmitype: <http://purl.org/dc/dcmitype/>\n" +
                "PREFIX req: <localhost:8080/requirementsThesauri/requirements/>\n" +
                "SELECT ?url ?label ?language ?prefLabel ?altLabel ?problem ?context ?template ?example " +
                "\n" +
                "WHERE{\n" +
                "\n" +
                "<"+requirementURI+"> 	rdf:type		skos:Concept ;\n" +
                "                schema:url	?url ;\n" +
                "                rdfs:label	?label ;\n" +
                "                dcmitype:language	?language ;\n" +
                "                skos:preLabel	?prefLabel ;\n" +
                "                skos:altLabel	?altLabel ;\n" +
                "                skos:note	?problem ;\n" +
                "                skos:scopeNote	?context ;\n" +
                "                skos:definition	?template ;\n" +
                "                skos:example	?example .\n" +
                "  \n" +
                "}\n" +
                "";
        return query;
    }

    public String getRequirementSparqlSelectNarrower(String requirementID) {
        String querySelect = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX req: <localhost:8080/requirementsThesauri/requirements/>\n" +
                "SELECT ?narrower \n" +
                "WHERE\n" +
                "{\n" +
                "  req:" + requirementID + "	rdf:type		skos:Concept ;\n" +
                "                 skos:narrower  ?narrower .\n" +
                "}\n" +
                "";
        return querySelect;
    }
    public String getRequirementSparqlSelectBroader(String requirementID) {
        String querySelect = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX req: <localhost:8080/requirementsThesauri/requirements/>\n" +
                "SELECT ?broader \n" +
                "WHERE\n" +
                "{\n" +
                "  req:" + requirementID + "	rdf:type		skos:Concept ;\n" +
                "                 skos:broader  ?broader .\n" +
                "}\n" +
                "";

        return querySelect;
    }

    public String getRequirementSparqlDescribe(String requirementID) {
        String query = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX req: <localhost:8080/requirementsThesauri/requirements/>\n" +
                "DESCRIBE req:" + requirementID + "\n" +
                "WHERE\n" +
                "{\n" +
                "req:" + requirementID + " rdf:type skos:Concept  .\n" +
                "}\n" +
                "";

        return query;
    }

    public String updateRequirementSparql(String oldRequirementID, String requirementID, String label, String language, String prefLabel, String altLabel,
                                          String problem, String context, String template, String example, String broaderRequirementTypeID,
                                          String broaderRequirementID, List<String> broaderDomainID, List<String> broaderSystemTypeID,
                                          List<String> narrowerRequirementID) {
        String queryUpdate = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX schema: <http://schema.org/>\n" +
                "PREFIX dcmitype: <http://purl.org/dc/dcmitype/>\n" +
                "PREFIX dom: <localhost:8080/requirementsThesauri/domains/>\n" +
                "PREFIX req: <localhost:8080/requirementsThesauri/requirements/>\n" +
                "PREFIX sys: <localhost:8080/requirementsThesauri/systemTypes/>\n" +
                "PREFIX rqt: <localhost:8080/requirementsThesauri/requirementTypes/>\n" +
                "\n" +
                "DELETE \n" +
                "	{ req:"+oldRequirementID+" ?p ?s }\n" +
                "WHERE\n" +
                "{ \n" +
                "  req:"+oldRequirementID+" ?p ?s;\n" +
                " 		rdf:type skos:Concept .\n" +
                "};\n" +
                "\n" +
                "INSERT DATA\n" +
                "{\n" +
                "  req:"+ requirementID +" 	rdf:type		skos:Concept ;\n" +
                "                schema:url	req:"+ requirementID +" ;\n" +
                "                rdfs:label	\""+label+"\" ;\n" +
                "                dcmitype:language	\""+language+"\" ;\n" +
                "                skos:preLabel	\""+prefLabel+"\" ;\n" +
                "                skos:altLabel	\""+altLabel+"\" ;\n" +
                "                skos:note\""+problem+"\" ;\n" +
                "                skos:scopeNote	\""+context+"\" ;\n" +
                "                skos:definition	\""+template+"\" ;\n" +
                "                skos:example	\""+example+"\" ;\n" +
                "                skos:broader	rqt:"+broaderRequirementTypeID+" ;\n" +
                "                skos:broader	req:"+broaderRequirementID+" ;\n";
        if(!broaderDomainID.isEmpty()){
            for (String bd: broaderDomainID){
                queryUpdate = queryUpdate + "                skos:broader	dom:"+bd+" ;\n";
            }
        }
        if(!broaderSystemTypeID.isEmpty()){
            for (String bs: broaderSystemTypeID){
                queryUpdate = queryUpdate + "                skos:broader	sys:"+bs+" ;\n";
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

    public String deleteRequirementSparql1(String requirementID){

        String queryDelete = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX req: <localhost:8080/requirementsThesauri/requirements/>\n" +
                "\n" +
                "DELETE \n" +
                "	{ req:"+requirementID+" ?p ?s }\n" +
                "WHERE\n" +
                "{ \n" +
                "  req:"+requirementID+" ?p ?s;\n" +
                " 		rdf:type skos:Concept .\n" +
                "};\n";

        return queryDelete;
    }

    public String deleteRequirementSparql(String requirementURI) {

        String queryDelete = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX req: <localhost:8080/requirementsThesauri/requirements/>\n" +
                "\n" +
                "DELETE \n" +
                "	{ <" + requirementURI + "> ?p ?s }\n" +
                "WHERE\n" +
                "{ \n" +
                "  <" + requirementURI + "> ?p ?s;\n" +
                " 		rdf:type skos:Concept .\n" +
                "};\n";

        return queryDelete;
    }
}
