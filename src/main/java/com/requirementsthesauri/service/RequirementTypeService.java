package com.requirementsthesauri.service;

import com.franz.agraph.jena.*;
import com.requirementsthesauri.model.RequirementType;
import com.requirementsthesauri.service.sparql.MethodsRequirementTypeSPARQL;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.NodeIterator;
import org.apache.jena.rdf.model.RDFNode;
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

public class RequirementTypeService {

    Authentication authentication = new Authentication();
    MethodsRequirementTypeSPARQL methodsRequirementTypeSPARQL = new MethodsRequirementTypeSPARQL();
    AGUtils agUtils = new AGUtils();

    public ResponseEntity<?> createRequirementType(List<RequirementType> requirementTypesList) throws Exception {

        authentication.getAuthentication();

        int TAM = requirementTypesList.size();
        JsonArrayBuilder jsonArrayAdd = Json.createArrayBuilder();
        String uri = "localhost:8080/requirementsThesauri/requirementTypes/";

        for (int i = 0; i < TAM; i++) {
            String requirementTypeID = requirementTypesList.get(i).getRequirementTypeID();
            String label = requirementTypesList.get(i).getLabel();
            String prefLabel = requirementTypesList.get(i).getPrefLabel();
            String altLabel = requirementTypesList.get(i).getAltLabel();
            String description = requirementTypesList.get(i).getDescription();
            String linkDBpedia = requirementTypesList.get(i).getLinkDbpedia();
            String broaderRequirementTypeID = requirementTypesList.get(i).getBroaderRequirementTypeID();
            List<String> narrowerRequirementTypeID = requirementTypesList.get(i).getNarrowerRequirementTypeID();
            List<String> narrowerRequirementID = requirementTypesList.get(i).getNarrowerRequirementID();


            String queryUpdate = methodsRequirementTypeSPARQL.insertRequirementTypeSparql(requirementTypeID, label, prefLabel, altLabel, description, linkDBpedia,
                    broaderRequirementTypeID, narrowerRequirementTypeID, narrowerRequirementID);

            UpdateRequest request = UpdateFactory.create(queryUpdate);
            UpdateProcessor up = UpdateExecutionFactory.createRemote(request, agUtils.sparqlEndpoint);
            up.execute();

            jsonArrayAdd.add(uri + requirementTypeID);

        }
        JsonArray ja = jsonArrayAdd.build();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JsonWriter writer = Json.createWriter(outputStream);
        writer.writeArray(ja);
        String output = new String(outputStream.toByteArray());
        return new ResponseEntity<>(output, HttpStatus.CREATED);
    }

    public ResponseEntity<?> createRequirementType1(List<RequirementType> requirementTypesList) throws Exception {

        authentication.getAuthentication();

        int TAM = requirementTypesList.size();
        JsonArrayBuilder jsonArrayAdd = Json.createArrayBuilder();
        String uri = "localhost:8080/requirementsThesauri/requirementTypes/";

        for(int i=0; i<TAM; i++) {
            String requirementTypeID = requirementTypesList.get(i).getRequirementTypeID();
            String label = requirementTypesList.get(i).getLabel();
            String prefLabel = requirementTypesList.get(i).getPrefLabel();
            String altLabel = requirementTypesList.get(i).getAltLabel();
            String description = requirementTypesList.get(i).getDescription();
            String linkDBpedia = requirementTypesList.get(i).getLinkDbpedia();
            String broaderRequirementTypeID = requirementTypesList.get(i).getBroaderRequirementTypeID();
            List<String> narrowerRequirementTypeID = requirementTypesList.get(i).getNarrowerRequirementTypeID();
            List<String> narrowerRequirementID = requirementTypesList.get(i).getNarrowerRequirementID();



            String queryUpdate = methodsRequirementTypeSPARQL.insertRequirementTypeSparql1(requirementTypeID, label, prefLabel, altLabel, description, linkDBpedia,
                    broaderRequirementTypeID, narrowerRequirementTypeID, narrowerRequirementID);

            UpdateRequest request = UpdateFactory.create(queryUpdate);
            UpdateProcessor up = UpdateExecutionFactory.createRemote(request, agUtils.sparqlEndpoint);
            up.execute();

            jsonArrayAdd.add(uri+requirementTypeID);

        }
        JsonArray ja = jsonArrayAdd.build();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JsonWriter writer = Json.createWriter(outputStream);
        writer.writeArray(ja);
        String output = new String(outputStream.toByteArray());
        return new ResponseEntity<>(output, HttpStatus.CREATED);

//        AGModel model = agUtils.getAGModel();
//
//        int TAM = requirementTypesList.size();
//        JsonArrayBuilder jsonArrayAdd = Json.createArrayBuilder();
//
//        for (int i = 0; i < TAM; i++) {
//            Resource resource = model.createResource(agUtils.uriRqt + requirementTypesList.get(i).getRequirementTypeID());
//
//            //       skos:Concept
//            model.add(resource, model.getProperty(model.getNsPrefixURI("rdf") + "type"), model.createResource(model.getNsPrefixURI("skos") + "Concept"));
//
//            //       schema:url
//            model.add(resource, model.getProperty(agUtils.schema + "url"), resource);
//
//            //       rdfs:label
//            model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "label"), requirementTypesList.get(i).getLabel());
//
//            //       skos:prefLabel
//            model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "prefLabel"), requirementTypesList.get(i).getPrefLabel());
//
//            //       skos:altLabel
//            model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "altLabel"), requirementTypesList.get(i).getAltLabel());
//
//            //       skos:note
//            model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "note"), requirementTypesList.get(i).getDescription());
//
//            //       owl:sameAs
//            model.add(resource, model.getProperty(model.getNsPrefixURI("owl") + "sameAs"), model.createResource(agUtils.dbr + requirementTypesList.get(i).getLinkDbpedia()));
//
//            //       skos:broader
//            model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "broader"), model.createResource(agUtils.uriRqt + requirementTypesList.get(i).getBroaderRequirementTypeID()));
//            model.add(resource, model.getProperty(model.getNsPrefixURI("rdfs") + "subClassOf"), model.createResource(agUtils.uriRqt + requirementTypesList.get(i).getBroaderRequirementTypeID()));
//
//            for (String rqtID : requirementTypesList.get(i).getNarrowerRequirementTypeID()) {
//                model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "narrower"), model.createResource(agUtils.uriRqt + rqtID));
//            }
//
//            for (String reqID : requirementTypesList.get(i).getNarrowerRequirementID()) {
//                model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "narrower"), model.createResource(agUtils.uriReq + reqID));
//            }
//
//            jsonArrayAdd.add(resource.getURI());
//
//        }
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

    public Model getRequirementTypeModel(String requirementTypeID) throws Exception {

        authentication.getAuthentication();


        AGModel model = agUtils.getAGModel();


        Model fakeModel = ModelFactory.createDefaultModel();
        fakeModel.setNsPrefix("rdf", model.getNsPrefixURI("rdf"));
        fakeModel.setNsPrefix("rdfs", model.getNsPrefixURI("rdfs"));
        fakeModel.setNsPrefix("schema", agUtils.schema);
        fakeModel.setNsPrefix("skos", model.getNsPrefixURI("skos"));
        fakeModel.setNsPrefix("owl", model.getNsPrefixURI("owl"));
        fakeModel.setNsPrefix("dbr", agUtils.dbr);
        fakeModel.setNsPrefix("uriRqt", agUtils.uriRqt);
        fakeModel.setNsPrefix("uriReq", agUtils.uriReq);


        String queryString = "Describe <" + requirementTypeID + "> ?s ?p ?o ";

        AGReasoner reasoner = new AGReasoner();
        AGInfModel infmodel = new AGInfModel(reasoner, model);

        AGQuery query = AGQueryFactory.create(queryString);
        AGQueryExecution qe = AGQueryExecutionFactory.create(query, infmodel);


        Model results = qe.execDescribe();

        fakeModel.add(results);
        
        return fakeModel;

    }

    public ResponseEntity<?> getRequirementTypeDescribe(String requirementTypeID, String accept) throws Exception {

        Model fakeModel = getRequirementTypeModel(agUtils.uriRqt + requirementTypeID);

        String format = agUtils.convertFromAcceptToFormat(accept);
        OutputStream stream = new ByteArrayOutputStream();
        if (format.isEmpty()){
            fakeModel.write(stream, "TURTLE");
        }else {
            fakeModel.write(stream, format);
        }
        return new ResponseEntity<>(stream.toString(), HttpStatus.OK);
    }

    public List<RequirementType> getAllRequirementTypes() throws Exception {
        authentication.getAuthentication();

        String querySelect = methodsRequirementTypeSPARQL.getAllRequirementTypesSparqlSelect();

        Query query = QueryFactory.create(querySelect);
        QueryExecution qexec = QueryExecutionFactory.sparqlService(agUtils.sparqlEndpoint, query);


        ResultSet results = qexec.execSelect();

        List<RequirementType> requirementTypes = new ArrayList<>();

        String c = "requirement";
        while(results.hasNext()) {
            String uri = results.nextSolution().getResource(c).getURI();
            if(uri.contains("requirementTypes")) {

                Model model = getRequirementTypeModel(uri);

                String qSelect = methodsRequirementTypeSPARQL.getRequirementTypeSparqlSelect(uri);
                Query queryS = QueryFactory.create(qSelect);
                QueryExecution qe = QueryExecutionFactory.createServiceRequest(agUtils.sparqlEndpoint, queryS);


                QuerySolution solution =  qe.execSelect().next();

                RequirementType requirementType = new RequirementType();

                requirementType.setUrl(solution.getResource("url").toString());
                requirementType.setLabel(solution.getLiteral("label").toString());

                qe.close();

                requirementTypes.add(requirementType);


            }
        }
        return requirementTypes;
    }

    public ResponseEntity<?> getAllRequirementTypes1(){
        authentication.getAuthentication();

        String querySelect = methodsRequirementTypeSPARQL.getAllRequirementTypesSparqlSelect();

        Query query = QueryFactory.create(querySelect);
        QueryExecution qexec = QueryExecutionFactory.sparqlService(agUtils.sparqlEndpoint, query);

        ResultSet results = qexec.execSelect();
        //return ResultSetFormatter.asText(results);

        JsonArrayBuilder jsonArrayAdd = Json.createArrayBuilder();
        String c = "requirementTypes";
        while(results.hasNext()) {
            jsonArrayAdd.add(results.nextSolution().getResource(c).getURI());
        }
        JsonArray ja = jsonArrayAdd.build();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JsonWriter writer = Json.createWriter(outputStream);
        writer.writeArray(ja);
        String output = new String(outputStream.toByteArray());

        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    public RequirementType getRequirementType(String requirementTypeURI) throws Exception {

        Model model = getRequirementTypeModel(requirementTypeURI);

        RequirementType requirementType;

        try {
            String qSelect = methodsRequirementTypeSPARQL.getRequirementTypeSparqlSelect(requirementTypeURI);
            Query sparql = QueryFactory.create(qSelect);
            QueryExecution qe = QueryExecutionFactory.create(sparql, model);

            QuerySolution solution= qe.execSelect().nextSolution();

            requirementType = new RequirementType();

            requirementType.setUrl(solution.getResource("url").toString());
            requirementType.setLabel(solution.getLiteral("label").toString());
            requirementType.setPrefLabel(solution.getLiteral("prefLabel").toString());
            requirementType.setAltLabel(solution.getLiteral("altLabel").toString());
            requirementType.setDescription(solution.getLiteral("description").toString());
            requirementType.setLinkDbpedia(solution.getResource("linkDbpedia").toString());




            NodeIterator nodes = model.listObjectsOfProperty(model.getProperty(model.getNsPrefixURI("skos") + "narrower"));

            List<String> narrowerRequirementTypeID = new ArrayList<>();
            List<String> narrowerRequirementID = new ArrayList<>();
            List<String> broaderRequirementType = new ArrayList<>();

            while (nodes.hasNext()){
                RDFNode node = nodes.nextNode();

                if(node.toString().contains("requirements/")) {
                    narrowerRequirementID.add(node.toString());
                }else {
                    narrowerRequirementTypeID.add(node.toString());
                }
//                System.out.println(node.toString());

            }

            nodes = model.listObjectsOfProperty(model.getProperty(model.getNsPrefixURI("skos") + "broader"));

            while (nodes.hasNext()){
                RDFNode node = nodes.nextNode();
                broaderRequirementType.add(node.toString());
            }

            requirementType.setNarrowerRequirementTypeID(narrowerRequirementTypeID);
            requirementType.setNarrowerRequirementID(narrowerRequirementID);
            requirementType.setBroaderRequirementTypes(broaderRequirementType);



            return  requirementType;
        }catch (Exception e){
            requirementType = new RequirementType();
            requirementType.setLabel(requirementTypeURI);
            requirementType.setUrl(requirementTypeURI);
            return requirementType;
        }

    }

    public List<RequirementType> getRequirementTypeNarrowerOrBroader(List<String> requirementTypeURI) throws Exception {

        List<RequirementType> requirementTypes = new ArrayList<>();
        RequirementType requirementType;
        for(String uri: requirementTypeURI) {
            if (uri.contains("dbpedia")) {
                requirementType = new RequirementType();
                requirementType.setLabel(uri);
                requirementType.setUrl(uri);
                requirementTypes.add(requirementType);
            } else {
                requirementType = getRequirementType(uri);
                if(!requirementType.getLabel().contains("localhost")) {
                    requirementTypes.add(requirementType);
                }
            }
        }
        return requirementTypes;
    }

    public void deleteRequirementType(String requirementTypeID) {
        authentication.getAuthentication();

        String deleteQuery = methodsRequirementTypeSPARQL.deleteRequirementTypeSparql(requirementTypeID);


        UpdateRequest request = UpdateFactory.create(deleteQuery);
        UpdateProcessor up = UpdateExecutionFactory.createRemote(request, agUtils.sparqlEndpoint);
        up.execute();
    }

    public void deleteRequirementType1(String requirementTypeID) {
        authentication.getAuthentication();

        String deleteQuery = methodsRequirementTypeSPARQL.deleteRequirementTypeSparql1(requirementTypeID);


        UpdateRequest request = UpdateFactory.create(deleteQuery);
        UpdateProcessor up = UpdateExecutionFactory.createRemote(request, agUtils.sparqlEndpoint);
        up.execute();
    }

    public ResponseEntity<?> updateRequirementType(String oldRequirementTypeID, RequirementType newRequirementType) throws Exception {
        authentication.getAuthentication();

        deleteRequirementType(oldRequirementTypeID);
        List<RequirementType> requirementTypes = new ArrayList<>();
        requirementTypes.add(newRequirementType);

        return createRequirementType1(requirementTypes);

    }

}
