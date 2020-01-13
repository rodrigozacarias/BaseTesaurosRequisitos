package com.requirementsthesauri.service;


import com.franz.agraph.jena.*;
import com.requirementsthesauri.model.Domain;
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

public class DomainService {

    Authentication authentication = new Authentication();
    AGUtils agUtils = new AGUtils();


    public ResponseEntity<?> createDomain(List<Domain> domainsList) throws Exception {

        AGModel model = agUtils.getAGModel();

        int TAM = domainsList.size();
        JsonArrayBuilder jsonArrayAdd = Json.createArrayBuilder();

        for (int i = 0; i < TAM; i++) {
            Resource resource = model.createResource(agUtils.uriDom + domainsList.get(i).getDomainID());

            //       skos:Concept
            model.add(resource, model.getProperty(model.getNsPrefixURI("rdf") + "type"), model.createResource(model.getNsPrefixURI("skos") + "Concept"));

            //       schema:url
            model.add(resource, model.getProperty(agUtils.schema + "url"), resource);

            //       rdfs:label
            model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "label"), domainsList.get(i).getLabel());

            //       skos:prefLabel
            model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "prefLabel"), domainsList.get(i).getPrefLabel());

            //       skos:altLabel
            model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "altLabel"), domainsList.get(i).getAltLabel());

            //       skos:note
            model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "note"), domainsList.get(i).getDescription());

            //       owl:sameAs
            model.add(resource, model.getProperty(model.getNsPrefixURI("owl") + "sameAs"), model.createResource(agUtils.dbr + domainsList.get(i).getLinkDbpedia()));

            //       skos:broader
            model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "broader"), model.createResource(agUtils.uriDom + domainsList.get(i).getBroaderDomainID()));
            model.add(resource, model.getProperty(model.getNsPrefixURI("rdfs") + "subClassOf"), model.createResource(agUtils.uriDom + domainsList.get(i).getBroaderDomainID()));


            for (String domainID : domainsList.get(i).getNarrowerDomainID()) {
                model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "narrower"), model.createResource(agUtils.uriDom + domainID));
            }

            for (String reqID : domainsList.get(i).getNarrowerRequirementID()) {
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

    public ResponseEntity<?> getDomain(String domainID, String accept) throws Exception {

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


            String queryString = "Describe <" + agUtils.uriDom + domainID + "> ?s ?p ?o ";

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

    public ResponseEntity<?> getAllDomains() throws Exception {
        authentication.getAuthentication();

        String querySelect = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "SELECT ?domain \n" +
                "WHERE\n" +
                "{\n" +
                "?domain rdf:type skos:Concept .\n" +
                "}\n" +
                "";

        Query query = QueryFactory.create(querySelect);
        QueryExecution qexec = QueryExecutionFactory.create(query, agUtils.getAGModel());

        ResultSet results = qexec.execSelect();

        JsonArrayBuilder jsonArrayAdd = Json.createArrayBuilder();
        String c = "domain";
        while(results.hasNext()) {
            String uri = results.nextSolution().getResource(c).getURI();
            if(uri.contains("domains")) {
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

    public void deleteDomain(String domainID) {
        authentication.getAuthentication();

        String deleteQuery = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
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


        UpdateRequest request = UpdateFactory.create(deleteQuery);
        UpdateProcessor up = UpdateExecutionFactory.createRemote(request, agUtils.sparqlEndpoint);
        up.execute();
    }

    public ResponseEntity<?> updateDomain(String oldDomainID, Domain newDomain) throws Exception {
        authentication.getAuthentication();

        deleteDomain(oldDomainID);
        List<Domain> domains = new ArrayList<>();
        domains.add(newDomain);

        return createDomain(domains);

    }

}


