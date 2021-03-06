package com.requirementsthesauri.service;

import com.franz.agraph.jena.*;
import com.requirementsthesauri.model.Domain;
import com.requirementsthesauri.model.Requirement;
import com.requirementsthesauri.service.sparql.MethodsRequirementSPARQL;
import com.sun.org.apache.regexp.internal.RE;
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

public class RequirementService {

    Authentication authentication = new Authentication();
    MethodsRequirementSPARQL methodsRequirementSPARQL = new MethodsRequirementSPARQL();
    AGUtils agUtils = new AGUtils();

    public ResponseEntity<?> createRequirement(List<Requirement> requirementsList){

        authentication.getAuthentication();

        int TAM = requirementsList.size();
        JsonArrayBuilder jsonArrayAdd = Json.createArrayBuilder();
        String uri = "localhost:8080/requirementsThesauri/requirements/";

        for(int i=0; i<TAM; i++) {
            String requirementID = requirementsList.get(i).getRequirementID();
            String label = requirementsList.get(i).getLabel();
            String language = requirementsList.get(i).getLanguage();
            String prefLabel = requirementsList.get(i).getPrefLabel();
            String altLabel = requirementsList.get(i).getAltLabel();
            String problem = requirementsList.get(i).getProblem();
            String context = requirementsList.get(i).getContext();
            String template = requirementsList.get(i).getTemplate();
            String example = requirementsList.get(i).getExample();
            String broaderRequirementTypeID = requirementsList.get(i).getBroaderRequirementTypeID();
            String broaderRequirementID = requirementsList.get(i).getBroaderRequirementID();
            List<String> broaderDomainID = requirementsList.get(i).getBroaderDomainID();
            List<String> broaderSystemTypeID = requirementsList.get(i).getBroaderSystemTypeID();
            List<String> narrowerRequirementID = requirementsList.get(i).getNarrowerRequirementID();



            String queryUpdate = methodsRequirementSPARQL.insertRequirementSparql(requirementID, label, language, prefLabel, altLabel,
                    problem, context, template, example, broaderRequirementTypeID,broaderRequirementID, broaderDomainID, broaderSystemTypeID,
                    narrowerRequirementID);

            UpdateRequest request = UpdateFactory.create(queryUpdate);
            UpdateProcessor up = UpdateExecutionFactory.createRemote(request, agUtils.sparqlEndpoint);
            up.execute();

            jsonArrayAdd.add(uri+requirementID);

        }
        JsonArray ja = jsonArrayAdd.build();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JsonWriter writer = Json.createWriter(outputStream);
        writer.writeArray(ja);
        String output = new String(outputStream.toByteArray());
        return new ResponseEntity<>(output, HttpStatus.CREATED);
    }

    public ResponseEntity<?> createRequirement1(List<Requirement> requirementsList){

        authentication.getAuthentication();

        int TAM = requirementsList.size();
        JsonArrayBuilder jsonArrayAdd = Json.createArrayBuilder();
        String uri = "localhost:8080/requirementsThesauri/requirements/";

        for(int i=0; i<TAM; i++) {
            String requirementID = requirementsList.get(i).getRequirementID();
            String label = requirementsList.get(i).getLabel();
            String language = requirementsList.get(i).getLanguage();
            String prefLabel = requirementsList.get(i).getPrefLabel();
            String altLabel = requirementsList.get(i).getAltLabel();
            String problem = requirementsList.get(i).getProblem();
            String context = requirementsList.get(i).getContext();
            String template = requirementsList.get(i).getTemplate();
            String example = requirementsList.get(i).getExample();
            String broaderRequirementTypeID = requirementsList.get(i).getBroaderRequirementTypeID();
            String broaderRequirementID = requirementsList.get(i).getBroaderRequirementID();
            List<String> broaderDomainID = requirementsList.get(i).getBroaderDomainID();
            List<String> broaderSystemTypeID = requirementsList.get(i).getBroaderSystemTypeID();
            List<String> narrowerRequirementID = requirementsList.get(i).getNarrowerRequirementID();



            String queryUpdate = methodsRequirementSPARQL.insertRequirementSparql1(requirementID, label, language, prefLabel, altLabel,
                    problem, context, template, example, broaderRequirementTypeID,broaderRequirementID, broaderDomainID, broaderSystemTypeID,
                    narrowerRequirementID);

            UpdateRequest request = UpdateFactory.create(queryUpdate);
            UpdateProcessor up = UpdateExecutionFactory.createRemote(request, agUtils.sparqlEndpoint);
            up.execute();

            jsonArrayAdd.add(uri+requirementID);

        }
        JsonArray ja = jsonArrayAdd.build();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JsonWriter writer = Json.createWriter(outputStream);
        writer.writeArray(ja);
        String output = new String(outputStream.toByteArray());
        return new ResponseEntity<>(output, HttpStatus.CREATED);
    }

//    public ResponseEntity<?> createRequirement(List<Requirement> requirementsList) throws Exception {
//        AGModel model = agUtils.getAGModel();
//
//        int TAM = requirementsList.size();
//        JsonArrayBuilder jsonArrayAdd = Json.createArrayBuilder();
//
//        for (int i = 0; i < TAM; i++) {
//            Resource resource = model.createResource(agUtils.uriReq + requirementsList.get(i).getRequirementID());
//
//            //       skos:Concept
//            model.add(resource, model.getProperty(model.getNsPrefixURI("rdf") + "type"), model.createResource(model.getNsPrefixURI("skos") + "Concept"));
//
//            //       schema:url
//            model.add(resource, model.getProperty(agUtils.schema + "url"), resource);
//
//            //       rdfs:label
//            model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "label"), requirementsList.get(i).getLabel());
//
//            //       skos:prefLabel
//            model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "prefLabel"), requirementsList.get(i).getPrefLabel());
//
//            //       skos:altLabel
//            model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "altLabel"), requirementsList.get(i).getAltLabel());
//
//            //       skos:note
//            model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "note"), requirementsList.get(i).getProblem());
//
//            //       skos:scopeNote
//            model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "scopeNote"), requirementsList.get(i).getContext());
//
//            //       skos:definition
//            model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "definition"), requirementsList.get(i).getTemplate());
//
//            //       skos:example
//            model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "example"), requirementsList.get(i).getExample());
//
//            //       skos:broader
//            model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "broader"), model.createResource(agUtils.uriRqt + requirementsList.get(i).getBroaderRequirementTypeID()));
//            model.add(resource, model.getProperty(model.getNsPrefixURI("rdfs") + "subClassOf"), model.createResource(agUtils.uriRqt + requirementsList.get(i).getBroaderRequirementTypeID()));
//
//            model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "broader"), model.createResource(agUtils.uriReq + requirementsList.get(i).getBroaderRequirementID()));
//            model.add(resource, model.getProperty(model.getNsPrefixURI("rdfs") + "subClassOf"), model.createResource(agUtils.uriReq + requirementsList.get(i).getBroaderRequirementID()));
//
//            for (String domainID : requirementsList.get(i).getBroaderDomainID()) {
//                model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "broader"), model.createResource(agUtils.uriDom + domainID));
//                model.add(resource, model.getProperty(model.getNsPrefixURI("rdfs") + "subClassOf"), model.createResource(agUtils.uriDom + domainID));
//            }
//
//            for (String systemTypeID : requirementsList.get(i).getBroaderSystemTypeID()) {
//                model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "broader"), model.createResource(agUtils.uriSys + systemTypeID));
//                model.add(resource, model.getProperty(model.getNsPrefixURI("rdfs") + "subClassOf"), model.createResource(agUtils.uriSys + systemTypeID));
//            }
//
//            for (String reqID : requirementsList.get(i).getNarrowerRequirementID()) {
//                model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "narrower"), model.createResource(agUtils.uriReq + reqID));
//            }
//
//            jsonArrayAdd.add(resource.getURI());
//        }
//        authentication.conn.close();
//
//        JsonArray ja = jsonArrayAdd.build();
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        JsonWriter writer = Json.createWriter(outputStream);
//        writer.writeArray(ja);
//        String output = new String(outputStream.toByteArray());
//        return new ResponseEntity<>(output, HttpStatus.CREATED);
//    }

    public Model getRequirementModel(String requirementID) throws Exception {
        authentication.getAuthentication();


        AGModel model = agUtils.getAGModel();


        Model fakeModel = ModelFactory.createDefaultModel();
        fakeModel.setNsPrefix("rdf", model.getNsPrefixURI("rdf"));
        fakeModel.setNsPrefix("rdfs", model.getNsPrefixURI("rdfs"));
        fakeModel.setNsPrefix("schema", agUtils.schema);
        fakeModel.setNsPrefix("skos", model.getNsPrefixURI("skos"));
        fakeModel.setNsPrefix("owl", model.getNsPrefixURI("owl"));
        fakeModel.setNsPrefix("dbr", agUtils.dbr);
        fakeModel.setNsPrefix("uriDom", agUtils.uriDom);
        fakeModel.setNsPrefix("uriReq", agUtils.uriReq);
        fakeModel.setNsPrefix("uriRqt", agUtils.uriRqt);
        fakeModel.setNsPrefix("uriSys", agUtils.uriSys);


        String queryString = "Describe <" + requirementID + "> ?s ?p ?o ";

        AGReasoner reasoner = new AGReasoner();
        AGInfModel infmodel = new AGInfModel(reasoner, model);

        AGQuery query = AGQueryFactory.create(queryString);
        AGQueryExecution qe = AGQueryExecutionFactory.create(query, infmodel);


        Model results = qe.execDescribe();

        fakeModel.add(results);

        return fakeModel;

    }

    public Requirement getRequirement(String requirementURI) throws Exception {
        Model model = getRequirementModel(requirementURI);

     


        Requirement requirement;
        try {

            String querySelect = methodsRequirementSPARQL.getRequirementSparqlSelect(requirementURI);
            Query sparql = QueryFactory.create(querySelect);
            QueryExecution qe = QueryExecutionFactory.create(sparql, model);

            requirement = new Requirement();

            QuerySolution soln= qe.execSelect().nextSolution();
            requirement.setUrl(soln.getResource("url").toString());
            requirement.setLabel(soln.getLiteral("label").toString());
            requirement.setLanguage(soln.getLiteral("language").toString());
            requirement.setPrefLabel(soln.getLiteral("prefLabel").toString());
            requirement.setAltLabel(soln.getLiteral("altLabel").toString());
            requirement.setProblem(soln.getLiteral("problem").toString());
            requirement.setContext(soln.getLiteral("context").toString());
            requirement.setTemplate(soln.getLiteral("template").toString());
            requirement.setExample(soln.getLiteral("example").toString());


            NodeIterator nodes = model.listObjectsOfProperty(model.getProperty(model.getNsPrefixURI("skos") + "broader"));

            List<String> broaderDomainID = new ArrayList<>();
            List<String>  broaderSystemTypeID = new ArrayList<>();
            List<String>  broaderRequirementTypes = new ArrayList<>();
            List<String>  broaderRequirements = new ArrayList<>();

            while(nodes.hasNext()) {
                String uri = nodes.nextNode().toString();
                if(uri.contains("domains")) {
                    broaderDomainID.add(uri);
                }else if(uri.contains("systemTypes")){
                    broaderSystemTypeID.add(uri);
                }else if(uri.contains("requirementTypes")) {
                   broaderRequirementTypes.add(uri);
                }else if(uri.contains("requirements/")) {
                    broaderRequirements.add(uri);
                }
            }

            nodes = model.listObjectsOfProperty(model.getProperty(model.getNsPrefixURI("skos") + "narrower"));

            List<String>  narrowerRequirementID = new ArrayList<>();

            while(nodes.hasNext()) {
                narrowerRequirementID.add(nodes.nextNode().toString());
            }

            requirement.setBroaderDomainID(broaderDomainID);
            requirement.setBroaderSystemTypeID(broaderSystemTypeID);
            requirement.setBroaderRequirementTypes(broaderRequirementTypes);
            requirement.setBroaderRequirements(broaderRequirements);
            requirement.setNarrowerRequirementID(narrowerRequirementID);

            return requirement;

        }catch (Exception e){

            requirement = new Requirement();
            requirement.setLabel(requirementURI);
            requirement.setUrl(requirementURI);
            return requirement;
        }
    }

    public List<Requirement> getRequirementNarrowerOrBroader(List<String> requirementURI) throws Exception {

        List<Requirement> requirements = new ArrayList<>();
        Requirement requirement;
        for(String uri: requirementURI) {
            if (uri.contains("dbpedia")) {
                requirement = new Requirement();
                requirement.setLabel(uri);
                requirement.setUrl(uri);
                requirements.add(requirement);
            } else {
                requirement = getRequirement(uri);
                if(!requirement.getLabel().contains("localhost")) {
                    requirements.add(requirement);
                }
            }
        }
        return requirements;
    }

    public List<Requirement> getRequirementNarrower(List<String> requirementURI) throws Exception {

        List<Requirement> requirements = new ArrayList<>();
        Requirement requirement;
        for(String uri: requirementURI) {
            requirement = getRequirement(uri);
            if(!requirement.getLabel().contains("localhost")) {
                requirements.add(requirement);
            }

        }
        return requirements;
    }

    public ResponseEntity<?> getRequirement1(String requirementID, String accept) throws Exception {
        authentication.getAuthentication();


        AGModel model = agUtils.getAGModel();


        Model fakeModel = ModelFactory.createDefaultModel();
        fakeModel.setNsPrefix("rdf", model.getNsPrefixURI("rdf"));
        fakeModel.setNsPrefix("rdfs", model.getNsPrefixURI("rdfs"));
        fakeModel.setNsPrefix("schema", agUtils.schema);
        fakeModel.setNsPrefix("skos", model.getNsPrefixURI("skos"));
        fakeModel.setNsPrefix("owl", model.getNsPrefixURI("owl"));
        fakeModel.setNsPrefix("dbr", agUtils.dbr);
        fakeModel.setNsPrefix("uriDom", agUtils.uriDom);
        fakeModel.setNsPrefix("uriReq", agUtils.uriReq);
        fakeModel.setNsPrefix("uriRqt", agUtils.uriRqt);
        fakeModel.setNsPrefix("uriSys", agUtils.uriSys);


        String queryString = "Describe <" + agUtils.uriReq + requirementID + "> ?s ?p ?o ";

        AGReasoner reasoner = new AGReasoner();
        AGInfModel infmodel = new AGInfModel(reasoner, model);

        AGQuery query = AGQueryFactory.create(queryString);
        AGQueryExecution qe = AGQueryExecutionFactory.create(query, infmodel);


        Model results = qe.execDescribe();

        fakeModel.add(results);


        String format = agUtils.convertFromAcceptToFormat(accept);
        OutputStream stream = new ByteArrayOutputStream();
        if (format.isEmpty()){
            fakeModel.write(stream, "TURTLE");
        }else {
            fakeModel.write(stream, format);
        }
        return new ResponseEntity<>(stream.toString(), HttpStatus.OK);

    }

    public ResponseEntity<?> getRequirementDescribe(String requirementID, String accept) throws Exception {

        Model fakeModel = getRequirementModel(agUtils.uriReq+ requirementID);

        String format = agUtils.convertFromAcceptToFormat(accept);
        OutputStream stream = new ByteArrayOutputStream();
        if (format.isEmpty()){
            fakeModel.write(stream, "TURTLE");
        }else {
            fakeModel.write(stream, format);
        }
        
        return new ResponseEntity<>(stream.toString(), HttpStatus.OK);
    }


    public ResponseEntity<?> getAllRequirements1(){
        authentication.getAuthentication();

        String querySelect = methodsRequirementSPARQL.getAllRequirementsSparqlSelect();

        Query query = QueryFactory.create(querySelect);
        QueryExecution qexec = QueryExecutionFactory.sparqlService(agUtils.sparqlEndpoint, query);

        ResultSet results = qexec.execSelect();
        //return ResultSetFormatter.asText(results);

        JsonArrayBuilder jsonArrayAdd = Json.createArrayBuilder();
        String c = "requirement";
        while(results.hasNext()) {
            String uri = results.nextSolution().getResource(c).getURI();
            if(uri.contains("requirements/")) {
                jsonArrayAdd.add(uri);
            }
        }
        JsonArray ja = jsonArrayAdd.build();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JsonWriter writer = Json.createWriter(outputStream);
        writer.writeArray(ja);
        String output = new String(outputStream.toByteArray());

        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    public List<Requirement> getAllRequirements() throws Exception {
        authentication.getAuthentication();

        String querySelect = methodsRequirementSPARQL.getAllRequirementsSparqlSelect();

        Query query = QueryFactory.create(querySelect);
        QueryExecution qexec = QueryExecutionFactory.sparqlService(agUtils.sparqlEndpoint, query);


        ResultSet results = qexec.execSelect();

        List<Requirement> requirements = new ArrayList<>();

        String c = "requirement";
        while (results.hasNext()) {
            String uri = results.nextSolution().getResource(c).getURI();
            if (uri.contains("requirements/")) {

                Model model = getRequirementModel(uri);

                String qSelect = methodsRequirementSPARQL.getRequirementSparqlSelect(uri);
                Query queryS = QueryFactory.create(qSelect);
                QueryExecution qe = QueryExecutionFactory.createServiceRequest(agUtils.sparqlEndpoint, queryS);


                QuerySolution solution = qe.execSelect().next();

                Requirement requirement = new Requirement();

                requirement.setUrl(solution.getResource("url").toString());
                requirement.setLabel(solution.getLiteral("label").toString());
//

                qe.close();

                requirements.add(requirement);



            }
        }
        return requirements;
    }

    public void deleteRequirement(String requirementURI) {
        authentication.getAuthentication();

        String deleteQuery = methodsRequirementSPARQL.deleteRequirementSparql(requirementURI);


        UpdateRequest request = UpdateFactory.create(deleteQuery);
        UpdateProcessor up = UpdateExecutionFactory.createRemote(request, agUtils.sparqlEndpoint);
        up.execute();
    }

    public void deleteRequirement1(String requirementID) {
        authentication.getAuthentication();

        String deleteQuery = methodsRequirementSPARQL.deleteRequirementSparql1(requirementID);


        UpdateRequest request = UpdateFactory.create(deleteQuery);
        UpdateProcessor up = UpdateExecutionFactory.createRemote(request, agUtils.sparqlEndpoint);
        up.execute();
    }

    public ResponseEntity<?> updateRequirement(String oldRequirementID, Requirement newRequirement) throws Exception {
        authentication.getAuthentication();

        deleteRequirement(oldRequirementID);
        List<Requirement> requirements = new ArrayList<>();
        requirements.add(newRequirement);

        return createRequirement1(requirements);

    }

}
