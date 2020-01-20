package com.requirementsthesauri.service;

import com.franz.agraph.jena.*;
import com.requirementsthesauri.model.SystemType;
import com.requirementsthesauri.model.SystemType;
import com.requirementsthesauri.model.SystemType;
import com.requirementsthesauri.service.sparql.MethodsSystemTypeSPARQL;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonWriter;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class SystemTypeService {

    Authentication authentication = new Authentication();
    MethodsSystemTypeSPARQL methodsSystemTypeSPARQL = new MethodsSystemTypeSPARQL();
    AGUtils agUtils = new AGUtils();

    public ResponseEntity<?> createSystemType(List<SystemType> systemTypesList) throws Exception {
        authentication.getAuthentication();

        int TAM = systemTypesList.size();
        JsonArrayBuilder jsonArrayAdd = Json.createArrayBuilder();
        String uri = "localhost:8080/requirementsThesauri/systemTypes/";

        for(int i=0; i<TAM; i++) {
            String systemTypeID = systemTypesList.get(i).getSystemTypeID();
            String label = systemTypesList.get(i).getLabel();
            String prefLabel = systemTypesList.get(i).getPrefLabel();
            String altLabel = systemTypesList.get(i).getAltLabel();
            String description = systemTypesList.get(i).getDescription();
            String linkDBpedia = systemTypesList.get(i).getLinkDbpedia();
            String broaderSystemTypeID = systemTypesList.get(i).getBroaderSystemTypeID();
            List<String> narrowerSystemTypeID = systemTypesList.get(i).getNarrowerSystemTypeID();
            List<String> narrowerRequirementID = systemTypesList.get(i).getNarrowerRequirementID();



            String queryUpdate = methodsSystemTypeSPARQL.insertSystemTypeSparql(systemTypeID, label, prefLabel, altLabel, description, linkDBpedia,
                    broaderSystemTypeID, narrowerSystemTypeID, narrowerRequirementID);

            UpdateRequest request = UpdateFactory.create(queryUpdate);
            UpdateProcessor up = UpdateExecutionFactory.createRemote(request, agUtils.sparqlEndpoint);
            up.execute();

            jsonArrayAdd.add(uri+systemTypeID);

        }
        JsonArray ja = jsonArrayAdd.build();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JsonWriter writer = Json.createWriter(outputStream);
        writer.writeArray(ja);
        String output = new String(outputStream.toByteArray());
        return new ResponseEntity<>(output, HttpStatus.CREATED);
    }

    public ResponseEntity<?> createSystemType1(List<SystemType> systemTypesList) throws Exception {
        authentication.getAuthentication();

        int TAM = systemTypesList.size();
        JsonArrayBuilder jsonArrayAdd = Json.createArrayBuilder();
        String uri = "localhost:8080/requirementsThesauri/systemTypes/";

        for(int i=0; i<TAM; i++) {
            String systemTypeID = systemTypesList.get(i).getSystemTypeID();
            String label = systemTypesList.get(i).getLabel();
            String prefLabel = systemTypesList.get(i).getPrefLabel();
            String altLabel = systemTypesList.get(i).getAltLabel();
            String description = systemTypesList.get(i).getDescription();
            String linkDBpedia = systemTypesList.get(i).getLinkDbpedia();
            String broaderSystemTypeID = systemTypesList.get(i).getBroaderSystemTypeID();
            List<String> narrowerSystemTypeID = systemTypesList.get(i).getNarrowerSystemTypeID();
            List<String> narrowerRequirementID = systemTypesList.get(i).getNarrowerRequirementID();



            String queryUpdate = methodsSystemTypeSPARQL.insertSystemTypeSparql1(systemTypeID, label, prefLabel, altLabel, description, linkDBpedia,
                    broaderSystemTypeID, narrowerSystemTypeID, narrowerRequirementID);

            UpdateRequest request = UpdateFactory.create(queryUpdate);
            UpdateProcessor up = UpdateExecutionFactory.createRemote(request, agUtils.sparqlEndpoint);
            up.execute();

            jsonArrayAdd.add(uri+systemTypeID);

        }
        JsonArray ja = jsonArrayAdd.build();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JsonWriter writer = Json.createWriter(outputStream);
        writer.writeArray(ja);
        String output = new String(outputStream.toByteArray());
        return new ResponseEntity<>(output, HttpStatus.CREATED);
//        AGModel model = agUtils.getAGModel();
//
//        int TAM = systemTypesList.size();
//        JsonArrayBuilder jsonArrayAdd = Json.createArrayBuilder();
//
//        for (int i = 0; i < TAM; i++) {
//            Resource resource = model.createResource(agUtils.uriSys + systemTypesList.get(i).getSystemTypeID());
//
//            //       skos:Concept
//            model.add(resource, model.getProperty(model.getNsPrefixURI("rdf") + "type"), model.createResource(model.getNsPrefixURI("skos") + "Concept"));
//
//            //       schema:url
//            model.add(resource, model.getProperty(agUtils.schema + "url"), resource);
//
//            //       rdfs:label
//            model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "label"), systemTypesList.get(i).getLabel());
//
//            //       skos:prefLabel
//            model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "prefLabel"), systemTypesList.get(i).getPrefLabel());
//
//            //       skos:altLabel
//            model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "altLabel"), systemTypesList.get(i).getAltLabel());
//
//            //       skos:note
//            model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "note"), systemTypesList.get(i).getDescription());
//
//            //       owl:sameAs
//            model.add(resource, model.getProperty(model.getNsPrefixURI("owl") + "sameAs"), model.createResource(agUtils.dbr + systemTypesList.get(i).getLinkDbpedia()));
//
//            //       skos:broader
//            model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "broader"), model.createResource(agUtils.uriSys + systemTypesList.get(i).getBroaderSystemTypeID()));
//            model.add(resource, model.getProperty(model.getNsPrefixURI("rdfs") + "subClassOf"), model.createResource(agUtils.uriSys + systemTypesList.get(i).getBroaderSystemTypeID()));
//
//
//            for (String sysID : systemTypesList.get(i).getNarrowerSystemTypeID()) {
//                model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "narrower"), model.createResource(agUtils.uriSys + sysID));
//            }
//
//            for (String reqID : systemTypesList.get(i).getNarrowerRequirementID()) {
//                model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "narrower"), model.createResource(agUtils.uriReq + reqID));
//            }
//
//            jsonArrayAdd.add(resource.getURI());
//
//        }
//
//
//        authentication.conn.close();
//
//        JsonArray ja = jsonArrayAdd.build();
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        JsonWriter writer = Json.createWriter(outputStream);
//        writer.writeArray(ja);
//        String output = new String(outputStream.toByteArray());
//        return new ResponseEntity<>(output, HttpStatus.CREATED);
    }

    public Model getSystemTypeModel(String systemTypeURI) throws Exception {

        authentication.getAuthentication();


        AGModel model = agUtils.getAGModel();


        Model fakeModel = ModelFactory.createDefaultModel();
        fakeModel.setNsPrefix("rdf", model.getNsPrefixURI("rdf"));
        fakeModel.setNsPrefix("rdfs", model.getNsPrefixURI("rdfs"));
        fakeModel.setNsPrefix("schema", agUtils.schema);
        fakeModel.setNsPrefix("skos", model.getNsPrefixURI("skos"));
        fakeModel.setNsPrefix("owl", model.getNsPrefixURI("owl"));
        fakeModel.setNsPrefix("dbr", agUtils.dbr);
        fakeModel.setNsPrefix("uriSys", agUtils.uriSys);
        fakeModel.setNsPrefix("uriReq", agUtils.uriReq);


        String queryString = "Describe <" + systemTypeURI + "> ?s ?p ?o ";

        AGReasoner reasoner = new AGReasoner();
        AGInfModel infmodel = new AGInfModel(reasoner, model);

        AGQuery query = AGQueryFactory.create(queryString);
        AGQueryExecution qe = AGQueryExecutionFactory.create(query, infmodel);


        Model results = qe.execDescribe();

        fakeModel.add(results);


        return fakeModel;

    }

    public ResponseEntity<?> getSystemTypeDescribe(String systemTypeID, String accept) throws Exception {

        Model fakeModel = getSystemTypeModel(agUtils.uriSys + systemTypeID);

        String format = agUtils.convertFromAcceptToFormat(accept);
        OutputStream stream = new ByteArrayOutputStream();
        if (format.isEmpty()){
            fakeModel.write(stream, "TURTLE");
        }else {
            fakeModel.write(stream, format);
        }
        return new ResponseEntity<>(stream.toString(), HttpStatus.OK);
    }

    public List<SystemType> getAllSystemTypes() throws Exception {
        authentication.getAuthentication();

        String querySelect = methodsSystemTypeSPARQL.getAllSystemTypesSparqlSelect();

        Query query = QueryFactory.create(querySelect);
        QueryExecution qexec = QueryExecutionFactory.sparqlService(agUtils.sparqlEndpoint, query);

        ResultSet results = qexec.execSelect();

        List<SystemType> systemTypes = new ArrayList<>();
        String c = "system";
        while(results.hasNext()) {
            try{
                String uri = results.nextSolution().getResource(c).getURI();
                if(uri.contains("systemTypes")) {

                    Model model = getSystemTypeModel(uri);

                    String qSelect = methodsSystemTypeSPARQL.getSystemTypeSparqlSelect(uri);
                    Query queryS = QueryFactory.create(qSelect);
                    QueryExecution qe = QueryExecutionFactory.createServiceRequest(agUtils.sparqlEndpoint, queryS);


                    QuerySolution solution =  qe.execSelect().next();

                   SystemType systemType = new SystemType();

                    systemType.setUrl(solution.getResource("url").toString());
                    systemType.setLabel(solution.getLiteral("label").toString());

                    qe.close();

                    systemTypes.add(systemType);



                }
            }catch(Exception e){
                System.out.println(e);

            }
        }
        return systemTypes;
//        //return ResultSetFormatter.asText(results);
//
//        JsonArrayBuilder jsonArrayAdd = Json.createArrayBuilder();
//        String c = "systemTypes";
//        while(results.hasNext()) {
//            jsonArrayAdd.add(results.nextSolution().getResource(c).getURI());
//        }
//        JsonArray ja = jsonArrayAdd.build();
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        JsonWriter writer = Json.createWriter(outputStream);
//        writer.writeArray(ja);
//        String output = new String(outputStream.toByteArray());
//
//        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    public void deleteSystemType(String systemTypeID) {
        authentication.getAuthentication();

        String deleteQuery = methodsSystemTypeSPARQL.deleteSystemTypeSparql(systemTypeID);


        UpdateRequest request = UpdateFactory.create(deleteQuery);
        UpdateProcessor up = UpdateExecutionFactory.createRemote(request, agUtils.sparqlEndpoint);
        up.execute();
    }

    public ResponseEntity<?> updateSystemType(String oldSystemTypeID, SystemType newSystemType) throws Exception {
        authentication.getAuthentication();

        deleteSystemType(oldSystemTypeID);
        List<SystemType> systemTypes = new ArrayList<>();
        systemTypes.add(newSystemType);

        return createSystemType(systemTypes);

    }

    public SystemType getSystemType(String systemTypeURI) throws Exception {

        Model model = getSystemTypeModel(systemTypeURI);

        SystemType systemType;

        try {
            String qSelect = methodsSystemTypeSPARQL.getSystemTypeSparqlSelect(systemTypeURI);
            Query sparql = QueryFactory.create(qSelect);
            QueryExecution qe = QueryExecutionFactory.create(sparql, model);

            QuerySolution solution= qe.execSelect().nextSolution();

            systemType = new SystemType();

            systemType.setUrl(solution.getResource("url").toString());
            systemType.setLabel(solution.getLiteral("label").toString());
            systemType.setPrefLabel(solution.getLiteral("prefLabel").toString());
            systemType.setAltLabel(solution.getLiteral("altLabel").toString());
            systemType.setDescription(solution.getLiteral("description").toString());
            systemType.setLinkDbpedia(solution.getResource("linkDbpedia").toString());




            NodeIterator nodes = model.listObjectsOfProperty(model.getProperty(model.getNsPrefixURI("skos") + "narrower"));

            List<String> narrowerSystemTypeID = new ArrayList<>();
            List<String> narrowerRequirementID = new ArrayList<>();
            List<String> broaderSystemType = new ArrayList<>();

            while (nodes.hasNext()){
                RDFNode node = nodes.nextNode();

                if(node.toString().contains("requirements/")) {
                    narrowerRequirementID.add(node.toString());
                }else {
                    narrowerSystemTypeID.add(node.toString());
                }
//                System.out.println(node.toString());

            }

            nodes = model.listObjectsOfProperty(model.getProperty(model.getNsPrefixURI("skos") + "broader"));

            while (nodes.hasNext()){
                RDFNode node = nodes.nextNode();
                broaderSystemType.add(node.toString());
            }

            systemType.setNarrowerSystemTypeID(narrowerSystemTypeID);
            systemType.setNarrowerRequirementID(narrowerRequirementID);
            systemType.setBroaderSystemTypes(broaderSystemType);



            return  systemType;
        }catch (Exception e){
            systemType = new SystemType();
            systemType.setLabel(systemTypeURI);
            systemType.setUrl(systemTypeURI);
            return systemType;
        }

    }

    public List<SystemType> getSystemTypeNarrowerOrBroader(List<String> systemTypeURI) throws Exception {

        List<SystemType> systemTypes = new ArrayList<>();
        SystemType systemType;
        for(String uri: systemTypeURI) {
            if (uri.contains("dbpedia")) {
                systemType = new SystemType();
                systemType.setLabel(uri);
                systemType.setUrl(uri);
                systemTypes.add(systemType);
            } else {
                systemType = getSystemType(uri);
                if(!systemType.getLabel().contains("localhost")) {
                    systemTypes.add(systemType);
                }
            }
        }
        return systemTypes;
    }

}
