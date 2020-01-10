package com.requirementsthesauri.service;

import com.franz.agraph.jena.AGGraph;
import com.franz.agraph.jena.AGModel;
import com.requirementsthesauri.model.Domain;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.json.*;
import java.io.ByteArrayOutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DomainService {

    Authentication authentication = new Authentication();
    String uriDom = "localhost:8080/requirementsThesauri/domains/";
    String uriReq = "localhost:8080/requirementsThesauri/requirements/";
    String dbr = "http://dbpedia.org/resource/";
    String schema = "http://schema.org/";

     public ResponseEntity<?> createDomain(List<Domain> domainsList) throws Exception {


         AGGraph graph = authentication.getConnectedDataRepository();
         AGModel model = new AGModel(graph);

         int TAM = domainsList.size();
         JsonArrayBuilder jsonArrayAdd = Json.createArrayBuilder();

         for(int i=0; i<TAM; i++) {
             Resource resource = model.createResource(uriDom + domainsList.get(i).getDomainID());

             //       skos:Concept
             model.add(resource, model.getProperty(model.getNsPrefixURI("rdf") + "type"), model.createResource(model.getNsPrefixURI("skos") + "Concept"));

             //       schema:url
             model.add(resource, model.getProperty(schema + "url"), resource);

             //       rdfs:label
             model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "label"), domainsList.get(i).getLabel());

             //       skos:prefLabel
             model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "prefLabel"), domainsList.get(i).getPrefLabel());

             //       skos:altLabel
             model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "altLabel"), domainsList.get(i).getAltLabel());

             //       skos:note
             model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "note"), domainsList.get(i).getDescription());

             //       owl:sameAs
             model.add(resource, model.getProperty(model.getNsPrefixURI("owl") + "sameAs"), model.createResource(dbr + domainsList.get(i).getLinkDbpedia()));

             //       skos:broader
             model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "broader"), model.createResource(uriDom + domainsList.get(i).getBroaderDomainID()));

             for (String domainID : domainsList.get(i).getNarrowerDomainID()) {
                 model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "narrower"), model.createResource(uriDom + domainID));
             }

             for (String reqID : domainsList.get(i).getNarrowerRequirementID()) {
                 model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "narrower"), model.createResource(uriReq + reqID));
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



}
