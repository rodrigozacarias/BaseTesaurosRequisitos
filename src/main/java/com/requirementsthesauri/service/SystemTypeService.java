package com.requirementsthesauri.service;

import com.franz.agraph.jena.*;
import com.requirementsthesauri.model.Domain;
import com.requirementsthesauri.model.SystemType;
import com.requirementsthesauri.service.sparql.MethodsSystemTypeSPARQL;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
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
        AGModel model = agUtils.getAGModel();

        int TAM = systemTypesList.size();
        JsonArrayBuilder jsonArrayAdd = Json.createArrayBuilder();

        for (int i = 0; i < TAM; i++) {
            Resource resource = model.createResource(agUtils.uriSys + systemTypesList.get(i).getSystemTypeID());

            //       skos:Concept
            model.add(resource, model.getProperty(model.getNsPrefixURI("rdf") + "type"), model.createResource(model.getNsPrefixURI("skos") + "Concept"));

            //       schema:url
            model.add(resource, model.getProperty(agUtils.schema + "url"), resource);

            //       rdfs:label
            model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "label"), systemTypesList.get(i).getLabel());

            //       skos:prefLabel
            model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "prefLabel"), systemTypesList.get(i).getPrefLabel());

            //       skos:altLabel
            model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "altLabel"), systemTypesList.get(i).getAltLabel());

            //       skos:note
            model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "note"), systemTypesList.get(i).getDescription());

            //       owl:sameAs
            model.add(resource, model.getProperty(model.getNsPrefixURI("owl") + "sameAs"), model.createResource(agUtils.dbr + systemTypesList.get(i).getLinkDbpedia()));

            //       skos:broader
            model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "broader"), model.createResource(agUtils.uriSys + systemTypesList.get(i).getBroaderSystemTypeID()));
            model.add(resource, model.getProperty(model.getNsPrefixURI("rdfs") + "subClassOf"), model.createResource(agUtils.uriSys + systemTypesList.get(i).getBroaderSystemTypeID()));


            for (String sysID : systemTypesList.get(i).getNarrowerSystemTypeID()) {
                model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "narrower"), model.createResource(agUtils.uriSys + sysID));
            }

            for (String reqID : systemTypesList.get(i).getNarrowerRequirementID()) {
                model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "narrower"), model.createResource(agUtils.uriReq + reqID));
            }

            jsonArrayAdd.add(resource.getURI());

        }


        authentication.conn.close();

        JsonArray ja = jsonArrayAdd.build();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JsonWriter writer = Json.createWriter(outputStream);
        writer.writeArray(ja);
        String output = new String(outputStream.toByteArray());
        return new ResponseEntity<>(output, HttpStatus.CREATED);
    }

    public ResponseEntity<?> getSystemType(String systemTypeID, String accept) throws Exception {

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


        String queryString = "Describe <" + agUtils.uriSys + systemTypeID + "> ?s ?p ?o ";

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

    public ResponseEntity<?> getAllSystemTypes(){
        authentication.getAuthentication();

        String querySelect = methodsSystemTypeSPARQL.getAllSystemTypesSparqlSelect();

        Query query = QueryFactory.create(querySelect);
        QueryExecution qexec = QueryExecutionFactory.sparqlService(agUtils.sparqlEndpoint, query);

        ResultSet results = qexec.execSelect();
        //return ResultSetFormatter.asText(results);

        JsonArrayBuilder jsonArrayAdd = Json.createArrayBuilder();
        String c = "systemTypes";
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

}
