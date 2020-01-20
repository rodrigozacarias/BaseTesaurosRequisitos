package com.requirementsthesauri.service.sparql;

import java.util.List;

public class MethodsRequirementTypeSPARQL {

    public String insertRequirementTypeSparql(String requirementTypeID, String label, String prefLabel, String altLabel, String description,
                                     String linkDbpedia, String broaderRequirementTypeID, List<String> narrowerRequirementTypeID, List<String> narrowerRequirementID) {
        String queryInsert = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX dbr: <http://dbpedia.org/resource/>\n" +
                "PREFIX schema: <http://schema.org/>\n" +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX rqt: <localhost:8080/requirementsThesauri/requirementTypes/>\n" +
                "PREFIX req: <localhost:8080/requirementsThesauri/requirements/>\n" +
                "INSERT DATA\n" +
                "{\n" +
                "  rqt:" + requirementTypeID + " 	rdf:type		skos:Concept ;\n" +
                "                schema:url	rqt:" + requirementTypeID + " ;\n" +
                "                rdfs:label	\"" + label + "\" ;\n" +
                "                skos:preLabel	\"" + prefLabel + "\" ;\n" +
                "                skos:altLabel	\"" + altLabel + "\" ;\n" +
                "                skos:note	\"" + description + "\" ;\n" +
                "                owl:sameAs	<" + linkDbpedia + "> ;\n" ;
        if(broaderRequirementTypeID==null){
            queryInsert = queryInsert + "                skos:broader	rqt:"+broaderRequirementTypeID+" ;\n" +
                    "                rdfs:subClassOf	rqt:"+broaderRequirementTypeID+" ;\n";
        }else{
            queryInsert = queryInsert +   "                skos:broader	<" + broaderRequirementTypeID + "> ;\n" +
                    "                rdfs:subClassOf	<" + broaderRequirementTypeID + "> ;\n";
        }
        if (!narrowerRequirementTypeID.isEmpty()) {
            for (String nd: narrowerRequirementTypeID){
                if(nd==null){
                    queryInsert = queryInsert + "                skos:narrower	rqt:"+nd+" ;\n";
                }else{
                    queryInsert = queryInsert + "                skos:narrower	<" + nd + "> ;\n";
                }
            }
        }
        if (!narrowerRequirementID.isEmpty()) {
            for (String nr: narrowerRequirementID) {
                if (nr == null) {
                    queryInsert = queryInsert + "                skos:narrower	req:" + nr + " ;\n";
                } else {
                    queryInsert = queryInsert + "                skos:narrower	<" + nr + "> ;\n";
                }
            }
        }
        queryInsert = queryInsert + ".\n }";
        return queryInsert;

    }

    public String insertRequirementTypeSparql1(String requirementTypeID, String label, String prefLabel, String altLabel, String description,
                                         String linkDbpedia, String broaderRequirementTypeID, List<String> narrowerRequirementTypeID, List<String> narrowerRequirementID) {
        String queryInsert = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX dbr: <http://dbpedia.org/resource/>\n" +
                "PREFIX schema: <http://schema.org/>\n" +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX rqt: <localhost:8080/requirementsThesauri/requirementTypes/>\n" +
                "PREFIX req: <localhost:8080/requirementsThesauri/requirements/>\n" +
                "INSERT DATA\n" +
                "{\n" +
                "  rqt:"+ requirementTypeID +" 	rdf:type		skos:Concept ;\n" +
                "                schema:url	rqt:"+ requirementTypeID +" ;\n" +
                "                rdfs:label	\""+label+"\" ;\n" +
                "                skos:preLabel	\""+prefLabel+"\" ;\n" +
                "                skos:altLabel	\""+altLabel+"\" ;\n" +
                "                skos:note	\""+description+"\" ;\n" +
                "                owl:sameAs	dbr:"+linkDbpedia+" ;\n" +
                "                skos:broader	rqt:"+broaderRequirementTypeID+" ;\n" +
                "                rdfs:subClassOf	rqt:"+broaderRequirementTypeID+" ;\n";
        if(!narrowerRequirementTypeID.isEmpty()){
            for (String ns: narrowerRequirementTypeID){
                queryInsert = queryInsert + "                skos:narrower	rqt:"+ns+" ;\n";
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

    public String getAllRequirementTypesSparqlSelect() {
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

    public String getRequirementTypeSparqlSelectNarrower(String requirementTypeID) {
        String querySelect = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX rqt: <localhost:8080/requirementsThesauri/requirementTypes/>\n" +
                "SELECT ?narrowerRequirementTypeID \n" +
                "WHERE\n" +
                "{\n" +
                "  rqt:" + requirementTypeID + "	rdf:type		skos:Concept ;\n" +
                "                 skos:narrower  ?narrowerRequirementTypeID .\n" +
                "}\n" +
                "";

        return querySelect;
    }

    public String getRequirementTypeSparqlSelect(String requirementTypeURI) {
        String query = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX schema: <http://schema.org/>\n" +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX rqt: <localhost:8080/requirementsThesauri/requirementTypes/>\n" +
                "SELECT ?url ?label ?prefLabel ?altLabel ?description" +
                " ?linkDbpedia ?broaderRequirementTypeID ?narroweRequirementTypeID ?narrowerRequirementID \n" +
                "WHERE{\n" +
                "\n" +
                "<"+requirementTypeURI+">	rdf:type		skos:Concept ;\n" +
                "                schema:url	?url ;\n" +
                "                rdfs:label	?label ;\n" +
                "                skos:preLabel	?prefLabel ;\n" +
                "                skos:altLabel	?altLabel ;\n" +
                "                skos:note	?description ;\n" +
                "                owl:sameAs	?linkDbpedia ;\n" +
                "                skos:broader	?broaderRequirementTypeID ;\n " +
                "                skos:narrower  ?narrowerRequirementTypeID ;\n" +
                "                skos:narrower	?narrowerRequirementID .\n" +
                "  \n" +
                "}\n" +
                "";
        return query;
    }

    public String getRequirementTypeSparqlDescribe(String requirementTypeID) {
        String query = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX rqt: <localhost:8080/requirementsThesauri/requirementTypes/>\n" +
                "DESCRIBE rqt:" + requirementTypeID + "\n" +
                "WHERE\n" +
                "{\n" +
                "rqt:" + requirementTypeID + " rdf:type skos:Concept  .\n" +
                "}\n" +
                "";

        return query;
    }

    public String updateRequirementTypeSparql(String oldRequirementTypeID, String requirementTypeID, String label, String prefLabel, String altLabel, String description,
                                         String linkDbpedia, String broaderRequirementTypeID, List<String> narrowerRequirementTypeID, List<String> narrowerRequirementID) {
        String queryUpdate = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX dbr: <http://dbpedia.org/resource/>\n" +
                "PREFIX schema: <http://schema.org/>\n" +
                "PREFIX rqt: <localhost:8080/requirementsThesauri/requirementTypes/>\n" +
                "PREFIX req: <localhost:8080/requirementsThesauri/requirements/>\n" +
                "\n" +
                "DELETE \n" +
                "	{ rqt:"+ oldRequirementTypeID +" ?p ?s }\n" +
                "WHERE\n" +
                "{ \n" +
                "  rqt:"+ oldRequirementTypeID +" ?p ?s;\n" +
                " 		rdf:type skos:Concept .\n" +
                "};\n" +
                "\n" +
                "INSERT DATA\n" +
                "{\n" +
                "  rqt:"+ oldRequirementTypeID +" 	rdf:type		skos:Concept ;\n" +
                "                schema:url	rqt:"+ requirementTypeID +" ;\n" +
                "                rdfs:label	\""+label+"\" ;\n" +
                "                skos:preLabel	\""+prefLabel+"\" ;\n" +
                "                skos:altLabel	\""+altLabel+"\" ;\n" +
                "                skos:note	\""+description+"\" ;\n" +
                "                rdfs:seeAlso	dbr:"+linkDbpedia+" ;\n" +
                "                skos:broader	rqt:"+broaderRequirementTypeID+" ;\n" ;
        if(!narrowerRequirementTypeID.isEmpty()){
            for (String ns: narrowerRequirementTypeID){
                queryUpdate = queryUpdate + "                skos:narrower	rqt:"+ns+" ;\n";
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

    public String deleteRequirementTypeSparql1(String requirementTypeID){

        String queryDelete = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX rqt: <localhost:8080/requirementsThesauri/requirementTypes/>\n" +
                "\n" +
                "DELETE \n" +
                "	{ rqt:"+requirementTypeID+" ?p ?s }\n" +
                "WHERE\n" +
                "{ \n" +
                "  rqt:"+requirementTypeID+" ?p ?s;\n" +
                " 		rdf:type skos:Concept .\n" +
                "};\n";

        return queryDelete;
    }

    public String deleteRequirementTypeSparql(String requirementTypeURI){

        String queryDelete = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX dom: <localhost:8080/requirementsThesauri/requirementTypes/>\n" +
                "\n" +
                "DELETE \n" +
                "	{   <"+requirementTypeURI+"> ?p ?s }\n" +
                "WHERE\n" +
                "{ \n" +
                "  <"+requirementTypeURI+"> ?p ?s;\n" +
                " 		rdf:type skos:Concept .\n" +
                "};\n";

        return queryDelete;
    }
}
